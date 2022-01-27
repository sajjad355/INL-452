import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Enum } from 'app/shared/objects/Enum';
import { DateFormatType, SettingService2 } from 'app/shared/services2/Setting2.service';
import { ANIMATIONS } from '../../../../shared/models/Constants';
import { Chicken, Injection } from '../../../../shared/objects/Chicken';
import { ChickenService } from '../../../../shared/services/chicken.service';
import { InjectionService } from '../../../../shared/services/injection.service';


@Component({
  selector: 'dashboard-immunization',
  providers: [ChickenService,
              InjectionService],
  templateUrl: './immunization.component.html',
  styleUrls: ['../../dashboard.component.scss'],
  animations: ANIMATIONS
})
export class ImmunizationComponent implements OnInit {

  @Output() routeDash = new EventEmitter();
  @Input() timeNow: number;

  expSinceAndIn: number = 7;

  // For use in html
  TABLE = Enum.getDashboardRouteDestinations();
  _array = Array;

  // Route variables
  animateTable = this.TABLE.none;
  animateRow = -1;

  /***************** Data Models *****************/
  chickens: Chicken[] = []; // chickens[x] corresponds to the chicken with ID x (or null).
  chickenStructure: [Chicken[]] = [[]]; // chickenStructure[9][2] will return Chicken with ID 9-2
  chickenInvalidID: Chicken[] = []; // unordered list of chickens that don't have ID with number-number
  injections: Injection[] = []; // unordered list of all injections
  // Sorted list of incomplete injections where the associated chicken is still alive
  injectionReminder: {injection: Injection, chicken: Chicken, displayMessage: string}[];

  // For UI: current view
  injectionReminderCurr: {injection: Injection, chicken: Chicken, displayMessage: string}[];
  injectionCollapsed = true;
  injectionTitle = 'Immunization Reminder';
  dateFormat= '';
  dateFormatDisplay: string;

  static isValidChickenID(chickenID: string): boolean {
    const idArray = chickenID.split('-');
    return !(idArray.length !== 2 || idArray[0] == '' || idArray[1] == '' || isNaN(+idArray[0]) || isNaN(+idArray[1]));
  }

   constructor(private chickenService: ChickenService,
               private injectionService: InjectionService,
               private settingService: SettingService2) { }

   ngOnInit() {
     this.loadSetting();
      this.loadImmunizingData().then(_ => {
         this.createImmunizingModel();
         this.showImmunizingReminder(7);
      });
   }

   loadSetting() {
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
  }

   /*************************/
   /*    Immunizing View    */
   /*************************/

   // Retrieve all Injection and Chicken data
   // this.injections is an unordered list
   // this.chickens[x] corresponds to the chicken with ID x (or null).
   private loadImmunizingData() {
     return new Promise<void>((resolve) => {
       const promise1 = this.injectionService.loadInjection().toPromise();
       const promise2 = this.chickenService.loadChicken().toPromise();

       Promise.all([promise1, promise2]).then(_ => {
         const injections = _[0];
         const chickens = _[1];
         this.injections = injections;

         for (let i = 0; i < chickens.length; i++) {
           const chicken: Chicken = chickens[i];

           while (chicken.dbid > this.chickens.length) {
             this.chickens.push(null); // Add empty elements as needed
           }
           this.chickens.push(chicken);
         }
         this.createChickenStructure();
         resolve();
       });
     });
   }

   private createImmunizingModel() {
    //  alert(Date.now())
     const dayOfTheMonthNow: number = new Date(this.timeNow).getDate();
     const dayOfTomorrow: number = new Date(this.timeNow + 1000 * 60 * 60 * 24).getDate();
     const model: {injection: Injection, chicken: Chicken, displayMessage: string}[] = [];

     this.injections.forEach(injection => {
       let message: string = null;
       const dayOfInjection = new Date(injection.injectdate).getDate();

       if (injection.complete === 'Completed') {
         // Ignore injections that have been completed
       } else if (injection.complete !== 'Not Complete') {
         this.logError('Chicken Injection has invalid status', injection);
       } else {
         // Make sure the associated chicken is valid
         const chicken = this.getChickenFromInjection(injection);
         if (chicken == null) {return; } // Error is logged in getChickenFromInjection()

         // Make sure chicken is alive
         if (chicken.chickenstatus == 'Sacrificed') {
           return;
         } else if (chicken.chickenstatus != 'Active') {
           this.logError('Chicken neither Sacrificed nor Active', chicken);
           return;
         }
         // Add injection to list, and set a message to display
         if (this.timeNow > +injection.injectdate) {
           message = 'Injection Overdue';

           // Make sure injection is really overdue, or if it can still be done today
           if (this.timeNow - 1000 * 60 * 60 * 24 < +injection.injectdate) {
             if (dayOfTheMonthNow == dayOfInjection) {
               message = 'Today';
             }
           }
         } else if (dayOfTheMonthNow == dayOfInjection) {
           message = 'Today';
         } else if (dayOfTomorrow == dayOfInjection) {
           message = 'Tomorrow';
         }
         model.push({injection: injection, chicken: chicken, displayMessage: message});
       }
     });
     // Sort by importance
     model.sort(function(a, b) {
       return (a.injection.injectdate < b.injection.injectdate) ? -1 : 1;
     });
     this.injectionReminder = model;
   }

   public showImmunizingReminder(reminderThresholdInDays: number) {
    this.expSinceAndIn = reminderThresholdInDays;
    this.injectionTitle = 'Immunization Reminder (' + reminderThresholdInDays + ' days)';

    const reminderThreshold: number = reminderThresholdInDays * 1000 * 60 * 60 * 24;
    this.injectionReminderCurr = [];
    const result = [];

    this.injectionReminder.map((inject:any) => {
      if((inject.injection.injectdate + reminderThreshold) - Date.now() > 0) {
        result.push(inject)
      }
    })
    this.injectionReminderCurr = result;

    //  for (let i = 0; i < this.injectionReminder.length; i++) {
    //    const injection = this.injectionReminder[i].injection;
    //    const timeTillInjection = +injection.injectdate - this.timeNow;
    //    // Add to list until Injection is not within threshold
    //    if (timeTillInjection > reminderThreshold) {
    //      break;
    //    }
    //    this.injectionReminderCurr.push(this.injectionReminder[i]);
    //    result.push(this.injectionReminder[i]);
    //  }
    //  this.injectionReminderCurr = result;
   }

   public input_showImmunizingReminder(event) {
     if (event && event.target && event.target.value && !isNaN(+event.target.value)) {
        this.expSinceAndIn = event.target.value;
       this.showImmunizingReminder(+event.target.value);
     }
   }

   private createChickenStructure() {
     const chickens = this.chickens;
     const structure: [Chicken[]] = [[]];
     for (let i = 0; i < chickens.length; i++) {

       // Validate ChickenID
       const chicken = chickens[i];
       if (chicken == null) {continue; }
       if (chicken.chickenid == null) {this.logError('Missing ChickenID', chicken); continue; }
       if (!ImmunizationComponent.isValidChickenID(chicken.chickenid)) {
         this.chickenInvalidID.push(chicken);
         continue;
       }

       // Create Structure
       const idArray = chicken.chickenid.split('-');
       const num1 = +idArray[0];
       const num2 = +idArray[1];
       while (num1 > structure.length) {
         structure.push([]);
       }
       if (num1 == structure.length) {
         const innerArray = [];
         while (num2 > innerArray.length) {
           innerArray.push(null);
         }
         innerArray.push(chicken);
         structure.push(innerArray);
       } else {
         const innerArray = structure[num1];
         while (num2 > innerArray.length) {
           innerArray.push(null);
         }
         if (num2 == innerArray.length) {
           innerArray.push(chicken);
         } else {
           innerArray[num2] = chicken;
         }
       }
     }
     // Complete
     this.chickenStructure = structure;
   }

   private getChickenFromInjection(injection: Injection): Chicken {
     const id = injection.chickenid;
     if (id == null) {this.logError('Missing ChickenID', injection); return null; }

     if (ImmunizationComponent.isValidChickenID(id)) {
       const idArray = id.split('-');
       if (this.chickenStructure.length > +idArray[0] && this.chickenStructure[+idArray[0]][+idArray[1]]) {
        return this.chickenStructure[+idArray[0]][+idArray[1]];
       }
     } else {
       const chicken = this.chickenInvalidID.find(_chicken => _chicken.chickenid === id);
       if (chicken != undefined) {
         return chicken;
       }
     }
     this.logError('Could not find Chicken with ChickenID:' + id, injection);
     return null;
   }

   // This following 2 functions are called by parent component
   cancelRoute() {
     this.animateTable = this.TABLE.none;
   }
   getData(table: number, row: number): any {
     switch (table) {
       case this.TABLE.imm_reminder:
         return this.injectionReminder[row].injection;
       default:
         return null;
     }
   }

   // Animate row if user holds mouse down
   // If animation completes, route user to different view
  onClickTableData(table: number, dataRow: number) {
     this.animateTable = table;
     this.animateRow = dataRow;
   }

  onAnimationDone(event: any) {
     if (event.fromState != false || event.toState != true) { return; }
     if (this.animateTable == this.TABLE.none) { return; }

     // Animation fully played, route user to different view
     this.route(this.animateTable, this.animateRow);
   }

   // Let Dashboard component handle the routing
   private route(fromTable: number, row: number) {
     this.routeDash.emit({fromTable: fromTable, row: row});
   }

   private logError(errorMsg: string, obj1: any= null, obj2: any= null) {
     errorMsg = '[Db-Imm] ' + errorMsg;
     if (obj2) { console.log(errorMsg, obj1, obj2);
     } else if (obj1) { console.log(errorMsg, obj1);
     } else { console.log(errorMsg); }
   }
}
