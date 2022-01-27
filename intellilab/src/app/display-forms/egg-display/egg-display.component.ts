import { Component, OnInit, Input,  SimpleChanges, Output, EventEmitter } from '@angular/core';
import { Injection, Egg } from 'app/shared/objects/Chicken';
import { item } from 'app/shared/objects/item';
import { ChickenService } from '../../shared/services/chicken.service';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { InjectionService } from '../../shared/services/injection.service';
import { EggService } from '../../shared/services/egg.service';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import { InventoryService2 } from 'app/shared/services2/Inventory2.service';
import { ErrorService } from '../../page/error/error.service';



@Component({
  selector: 'app-egg-display',
  templateUrl: './egg-display.component.html',
  providers: [InventoryService2,
              ChickenService,
              EggService],
  styleUrls: ['./egg-display.component.css']
})
export class EggDisplayComponent implements OnInit {

  @Input() egg: Egg;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;
  @Output() updateEgg = new EventEmitter();
  @Output() cancel = new EventEmitter();

  currentuser: UserV2;
  displayEgg:Egg;
  injections:Injection[]=[];
  inventList: any[];
  firstdaysafterimmune='';
  lastdaysafterimmune='';
  collectiondateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  firstlayeggdateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  lastlayeggdateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  frozendateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  collectiondate: { date: { year: number, month: number, day: number } };
  firstlayeggdate: { date: { year: number, month: number, day: number } };
  lastlayeggdate: { date: { year: number, month: number, day: number } };
  frozendate: { date: { year: number, month: number, day: number } };
  originaleggamount=0;
  yolktiterlist : string[]=[];
  locations: LocationV2[] = [];
  subLocations: LocationV2[] = [];
  dateFormat: string;
  dateFormatDisplay: string;

  constructor(private invenService: InventoryService2,
             private chickenService: ChickenService,
             private errorService: ErrorService,
             private injectionService: InjectionService,
             private settingService: SettingService2,
             private locationService: LocationService2,
             private eggService: EggService,
             private authenticationservice: AuthenticationService2) {
  }

  ngOnInit() {
    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
      });
    }
    this.loadSetting();
    this.invenService.getAllItems().subscribe(invent => {
      this.inventList = invent
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.egg && this.egg !== undefined) {
      this.originaleggamount=Number(this.egg.amount);

      if(this.egg.sublocation){
        let target : LocationV2 = this.locations.find(x => x.locationName == this.egg.location);
        this.showLocationSublocations(target.locationName);
      }

      if(this.egg.collectiondate){
        let c = new Date(this.egg.collectiondate)
        this.collectiondate = { date: { year: c.getFullYear(), month: c.getMonth() + 1, day:c.getDate() } };
      }
      if(this.egg.firstlayeggdate){
        let f = new Date(this.egg.firstlayeggdate)
        this.firstlayeggdate = { date: { year: f.getFullYear(), month: f.getMonth() + 1, day:f.getDate() } };
      }
      if(this.egg.lastlayeggdate){
        let l = new Date(this.egg.lastlayeggdate)
        this.lastlayeggdate = { date: { year: l.getFullYear(), month: l.getMonth() + 1, day:l.getDate() } };
      }
      if(this.egg.frozendate){
        let fr = new Date(this.egg.frozendate)
        this.frozendate = { date: { year: fr.getFullYear(), month: fr.getMonth() + 1, day:fr.getDate() } };
      }
      this.reset();
    }

  }

  loadSetting(){

    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.collectiondateoptions.dateFormat=this.dateFormat;
    // this.collectiondateoptions.editableDateField = false;
    this.firstlayeggdateoptions.dateFormat=this.dateFormat;
    // this.firstlayeggdateoptions.editableDateField = false;
    this.lastlayeggdateoptions.dateFormat=this.dateFormat;
    // this.lastlayeggdateoptions.editableDateField = false;
    this.frozendateoptions.dateFormat=this.dateFormat;
    // this.frozendateoptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.locations = this.locationService.getAllLocations();
    this.yolktiterlist = this.settingService.getSettingValuesAsArray( 'eggInventoryTiter' );
    this.showLocationSublocations(this.locations[0].locationName);
  }



  // make sure sublocation display is changed as location is updated
  showLocationSublocations(locationName: string) {
    this.subLocations = [];
    let target = this.locations.find(x => x.locationName == locationName);
    if (target == undefined) return;

    target.subLocations.forEach( aSubLocation =>{
      this.subLocations.push( aSubLocation );
    });
  }



  reset() {
    this.displayEgg = Egg.newEgg(this.egg);
    this.isNew ? '' : this.enableEdit = false;
  }



  caldaysafterimmune(){
    this.firstdaysafterimmune=Math.ceil((( new Date(this.displayEgg.firstlayeggdate).getTime()   - new Date(this.displayEgg.firstinjectdate).getTime() )/(1000*60*60*24)  )).toString() ;
    this.lastdaysafterimmune=Math.ceil(((new Date(this.displayEgg.lastlayeggdate).getTime()   - new Date(this.displayEgg.firstinjectdate).getTime() )/(1000*60*60*24)  )).toString() ;

    if(this.firstdaysafterimmune == this.lastdaysafterimmune){
      this.displayEgg.daysafterimmune=this.firstdaysafterimmune;
    }
    else{
      this.displayEgg.daysafterimmune=this.firstdaysafterimmune+'-'+this.lastdaysafterimmune;
    }


  }

  validateuserinput(): boolean {
    if(!this.displayEgg.amount || this.displayEgg.amount.trim()=='' || this.displayEgg.amount.trim()=='0'){return false;}

    if(!this.displayEgg.firstlayeggdate){return false;}
    if(!this.displayEgg.lastlayeggdate){return false;}
    if(!this.displayEgg.location){return false;}
    if(this.displayEgg.eggpart && this.displayEgg.eggpart.trim()=='Whole Egg'){
      if(!this.displayEgg.collectiondate){return false;}
    }
    else if(this.displayEgg.eggpart && this.displayEgg.eggpart.trim()=='Yolk'){
      if(!this.displayEgg.frozendate){return false;}
    }

    return true;
  }

  //save whole kit information include kit components
  save() {
    //search all the kits to see if the catalog number and name are unique
    if (this.validateuserinput()) {
      this.injectionService.loadInjectionBychickenid(this.displayEgg.chickenid).subscribe(injections=>{
        if(!this.displayEgg.firstinjectdate){
          this.displayEgg.firstinjectdate=injections[0].injectdate;
        }

        this.caldaysafterimmune();
        this.egg = this.displayEgg;
        this.enableEdit = false;
        this.isNew=false;

        this.chickenService.getChickenByChickenId(this.egg.chickenid).subscribe(chicken=>{
          if(this.displayEgg.dbid==-1){
            this.eggService.addEgg(this.egg).then(_=>{
              this.egg.dbid=_['dbid'];
              chicken.totalegg= (Number(chicken.totalegg)+Number(this.egg.amount)).toString();
              this.chickenService.updateChickenInfo(chicken).then(result=>{
                this.updateEgg.emit({egg:this.egg,chicken:chicken} );
              }).catch( error => {
                ErrorUtil.handleHttpError( error );
                this.errorService.message$ = error.error;
              });
            }).catch( error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
            });
          }
          else{
            this.eggService.updateSingleEgg(this.egg).then(()=>{
              chicken.totalegg= (Number(chicken.totalegg)-this.originaleggamount+Number(this.egg.amount)).toString();
              this.chickenService.updateChickenInfo(chicken).then(result=>{
                this.updateEgg.emit({egg:this.egg,chicken:chicken} );
              }).catch( error => {
                ErrorUtil.handleHttpError( error );
                this.errorService.message$ = error.error;
              });
            }).catch( error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
            });
          }

        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });

      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }

  cancelEdit(){
    this.reset();
    this.cancel.emit();
  }


  changecollectiondate(event) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayEgg.collectiondate = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEgg.collectiondate = new Date(formatted);
    }
  }

  changefirstlayeggdate(event) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayEgg.firstlayeggdate = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEgg.firstlayeggdate = new Date(formatted);
    }
  }

  changelastlayeggdate(event) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayEgg.lastlayeggdate = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEgg.lastlayeggdate = new Date(formatted);
    }
  }

  changefrozendate(event){
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayEgg.frozendate = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEgg.frozendate = new Date(formatted);
    }
  }


}
