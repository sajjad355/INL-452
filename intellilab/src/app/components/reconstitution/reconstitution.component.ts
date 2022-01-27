import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { UserV2 } from '../../shared/objects2/UserV2';
import { InventoryV2, InventoryDetailV2 } from '../../shared/objects2/InventoryV2';

@Component({
  selector: 'app-reconstitution',
  templateUrl: './reconstitution.component.html',
  styleUrls: ['./reconstitution.component.css']
})
export class ReconstitutionComponent implements OnInit, OnChanges {
  @Input() reconInventory: InventoryV2;
  @Input() reconInventoryDetailId: number;
  @Output() update = new EventEmitter();
  @Output() cancel = new EventEmitter();

  displayInventory : InventoryV2;
  displayInventoryDetail : InventoryDetailV2;
  reconstitutionDetailList : InventoryDetailV2[] = [];
  unit: String[];
  volUnit: String[];
  massUnit: String[];
  otherUnit: String[];
  conunit: String[];
  locations: LocationV2[] = [];
  subLocations : LocationV2[] = [];
  dateFormat:string;
  dateFormatDisplay: string;
  today = new Date();
  dateoption: IAngularMyDpOptions = {
    // todayBtnTxt: 'Today',
    dateFormat: 'ddmmmyyyy',
    firstDayOfWeek: 'mo',
    sunHighlight: true,
    inline: false,
    disableUntil: {year: this.today.getFullYear(), month: this.today.getMonth()+1, day: this.today.getDate()-1}
   };
   currentUser:UserV2;
   requestedUnopenedCheckoutAmount : number = 1;
   inputWarning : string[] = [];
   expiryDateList : { date: { year: number, month: number, day: number } }[] = [];
   statusOptions=['Unopened','In-Use'];
   statusOptionList : string [] = [];

  constructor(private settingService: SettingService2,
              private locationService: LocationService2,
              private authService: AuthenticationService2) { }
  
  ngOnInit() {
    let getCurrentUser = this.authService.getCurrentUser()
    if (getCurrentUser !== undefined) {
      getCurrentUser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
    this.loadSetting();
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.reconInventory && this.reconInventory !== undefined) {
      this.displayInventory =  InventoryV2.copy(this.reconInventory);
      this.displayInventoryDetail = this.displayInventory.inventoryDetails.find( x => x.inventoryDetailId == this.reconInventoryDetailId );
    }
  }

  loadSetting() {
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateoption.dateFormat=this.dateFormat;
    // this.dateoption.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay )
    this.unit = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    this.massUnit = this.settingService.getSettingValuesAsArray(  'massUnit' );
    this.massUnit.forEach(m => { this.unit.push(m) })
    this.otherUnit = this.settingService.getSettingValuesAsArray( 'otherUnit' );
    this.otherUnit.forEach(o => { this.unit.push(o) })
    this.conunit = this.settingService.getSettingValuesAsArray( 'concentrationUnit' );
    this.locations = this.locationService.getAllLocations();
  }

  reconstituteItem() {
    // todo validate amount & create new InventoryDetailV2 and add to reconstitutionDetailList and add new date to e date list
    if ( !this.validateRequestedAmount( this.requestedUnopenedCheckoutAmount )) return;
    
    const e : Date = new Date();
    let eDate =  { date: {year : e.getFullYear(), month : e.getMonth() + 1, day: e.getDate() } };
    this.expiryDateList.push( eDate );

    this.statusOptionList.push( this.statusOptions[0]); // default to un opened
    
    let item : InventoryDetailV2 = InventoryDetailV2.copy( this.displayInventoryDetail );
    item.inventoryDetailId = -1;
    item.editedBy = this.currentUser.name;
    item.modifiedOn = new Date();
    item.expiryDate = e;
    item.amount =  this.requestedUnopenedCheckoutAmount;
    item.numberInUse = 0;
    item.reconstituted = true;
    this.reconstitutionDetailList.push( item );

  }

  changeExpiryDate( event : any, index : number ) {
      let d = event.formatted;
      if (d == undefined) return;
      this.reconstitutionDetailList[index].expiryDate = new Date(d);
  }

  removeReconstitutionItem(index : number) {
    this.reconstitutionDetailList.splice( index, 1 );
    this.expiryDateList.splice( index, 1);
    this.statusOptionList.splice( index, 1 );
  }

  setCheckoutAmount( requestedAmount : number ) {
    if ( !this.validateRequestedAmount( requestedAmount )) return;
    this.requestedUnopenedCheckoutAmount = requestedAmount;
  }

  validateRequestedAmount( requestedAmount : number ) : boolean {
    if ( requestedAmount > this.displayInventoryDetail.amount ) {
      this.inputWarning.push("Entered amount exceeds available amount.");
    }
    if ( requestedAmount == 0 ) {
      this.inputWarning.push("Entered amount must be greater than 0.");
    }

    if ( this.inputWarning.length > 0 ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return false;
    }
    else {
      return true;
    }
  }

  setItemCheckoutStatus( itemIndex : number, statusValue : string ) {
    this.statusOptionList[itemIndex] = statusValue;
    if ( statusValue == 'Unopened') {
      this.reconstitutionDetailList[itemIndex].amount = this.requestedUnopenedCheckoutAmount;
      this.reconstitutionDetailList[itemIndex].numberInUse = 0;
    }
    else if ( statusValue == 'In-Use') {
      this.reconstitutionDetailList[itemIndex].amount = 0;
      this.reconstitutionDetailList[itemIndex].numberInUse = this.requestedUnopenedCheckoutAmount;
    }
      
  }

  selectLocation( index : number, locationName : string ) {
    this.reconstitutionDetailList[index].location  = this.locations.find( x => x.locationName == locationName );
    if ( this.reconstitutionDetailList[index].location ) {
      this.subLocations = this.reconstitutionDetailList[index].location.subLocations;
    }
    else {
      this.subLocations = [];
    }
  }

  selectSubLocation(index : number, locationName : string) {
    if ( this.subLocations ) {
      this.reconstitutionDetailList[index].subLocation = this.subLocations.find( x => x.locationName == locationName );
   }
  }

  saveReconstitute(){

    if ( !this.lotNumbersUnique()) {
      this.inputWarning.push("All reconstituted items must have unique lot numbers");
    }
    this.reconstitutionDetailList.forEach(item => {
      if ( (!item.expiryDate) && !this.inputWarning.find( x => x == "Expiry date is required") )
        this.inputWarning.push( "Expiry date is required"); 
      if ( (!item.amount && !item.numberInUse) && !this.inputWarning.find( x => x == "Amount is required") )
        this.inputWarning.push( "Amount is required");
      if ( (!item.containerSize) && !this.inputWarning.find( x => x == "Unit size is required") )
        this.inputWarning.push( "Unit size is required"); 
      if ( (!item.unit) && !this.inputWarning.find( x => x == "Unit is required") )
        this.inputWarning.push( "Unit is required");  
      if ( (!item.concentration) && !this.inputWarning.find( x => x == "Concentration is required") )
        this.inputWarning.push( "Concentration is required");    
      if ( (!item.concentrationUnit) && !this.inputWarning.find( x => x == "Concentration Unit is required") )
        this.inputWarning.push( "Concentration Unit is required");
      if ( (!item.location) && !this.inputWarning.find( x => x == "Location is required") )
        this.inputWarning.push( "Location is required");    
    });

    if ( this.inputWarning.length > 0 ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return;
    }
    else {
      this.reconstitutionDetailList.forEach(item => {
        this.displayInventory.inventoryDetails.push( item );
        
      });
      let originalInvDetail : InventoryDetailV2 = this.displayInventory.inventoryDetails.find( x => x.inventoryDetailId == this.displayInventoryDetail.inventoryDetailId);
      originalInvDetail.amount -= this.requestedUnopenedCheckoutAmount;
      this.displayInventory.editedBy = this.currentUser.name;
      this.displayInventory.modifiedOn = new Date();
      this.update.emit( this.displayInventory ); 
    }
  }

  lotNumbersUnique() : boolean {
    let lotNumbers : string [] = [];
    let lotNumbersUnique : boolean = true;
    for( let i = 0 ; i < this.reconstitutionDetailList.length; i++ ) {
      let item : InventoryDetailV2 = this.reconstitutionDetailList[i];
      let lotNumber : string = item.lotNumber;
      let lotNumberIndex = lotNumbers.findIndex(x => x == lotNumber);
      if ( lotNumberIndex == -1 && this.displayInventoryDetail.lotNumber != lotNumber ) {
        lotNumbers.push( lotNumber );
      }
      else {
        lotNumbersUnique = false;
        break;   
      }  
    }
    return lotNumbersUnique;
  }

  cancelChange() {
    this.cancel.emit();
  }

  
  
}
