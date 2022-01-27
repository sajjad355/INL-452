import { Component, OnInit, Input, SimpleChanges, Output, EventEmitter,  ViewChild, ElementRef  } from '@angular/core';
import { Chicken, Injection, Egg } from 'app/shared/objects/Chicken';
import { item } from 'app/shared/objects/item';
import { ChickenService } from '../../shared/services/chicken.service';
import { SettingService2, DateFormatType  } from '../../shared/services2/Setting2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { InjectionService } from '../../shared/services/injection.service';
import { EggService } from '../../shared/services/egg.service';
import { IAngularMyDpOptions } from 'angular-mydatepicker';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as Chart from 'chart.js';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import { InventoryService2 } from 'app/shared/services2/Inventory2.service';
import { ErrorService } from '../../page/error/error.service';



@Component({
  selector: 'app-chicken-display',
  providers: [ChickenService,
              EggService,
              InjectionService,
              InventoryService2],
  templateUrl: './chicken-display.component.html',
  styleUrls: ['./chicken-display.component.css']
})
export class ChickenDisplayComponent implements OnInit {
  @ViewChild('addbloodtiter') addbloodtiter: ElementRef;
  @Input() chicken: Chicken;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;
  @Input() linearChart: Chart;


  @Output() updateChicken = new EventEmitter();
  @Output() updateeditstatus = new EventEmitter();
  @Output() cancel = new EventEmitter();
  @Output() changeTab = new EventEmitter();
  @Output() selectrecipe = new EventEmitter();
  @Output() chickenimmune = new EventEmitter();
  @Output() chickenLatestTiter = new EventEmitter();


  displayChicken:Chicken;
  injections:Injection[]=[];
  eggs:Egg[]=[];
  updatedInjections:Injection[]=[];
  originalInjections:Injection[]=[];
  originalInjectdates: { date: { year: number, month: number, day: number } }[]=[];
  updatedEggs:Egg[]=[];

  finditem:boolean;
  itemfilterList: {name: string, supplier: string, cat: string}[];
  adjuvantfilterList: {name: string, supplier: string, cat: string}[];
  inventList: any[];
  onfocusIndex: number = -1;
  volumeunits:string[];
  projectNames : String[]=[];

  dateFormat:string;
  dateFormatDisplay: string;
  dateofbirthoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  injectdateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  collectiondateptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  firstlayeggdateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  lastlayeggdateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };

  dateofbirth: { date: { year: number, month: number, day: number } };
  injectdates: { date: { year: number, month: number, day: number } }[]=[];
  collectiondates: { date: { year: number, month: number, day: number } }[]=[];
  firstlayeggdates: { date: { year: number, month: number, day: number } }[]=[];
  lastlayeggdates: { date: { year: number, month: number, day: number } }[]=[];

  type: String[]=[];
  cloneLevel: String[]=[]
  clients: String[]=[]
  species: String[]=[]
  selectType: { id: number, name: string };
  selectClone: { id: number, name: string };
  isClientSpecific: boolean = false;
  selectClient: { id: number, name: string };
  selectSpecies: { id: number, name: string };
  kitMethod: String[]=[];
  kitStatus: String[]=[];
  vialdescription: String[]=[];
  unitList: String[]=[];
  customeKitMethod: string;
  customeKitStatus: string;
  kitsize=[];
  currentuser: UserV2;
  yolkTiterList : String[]=[];
  immustatusoptions: string[] =['Completed','Not Complete']
  addbloodtitermodal:any;

  injectionaddtiter:Injection;

  constructor(private invenService: InventoryService2,
              // private inventoryService: InventoryService,
              private errorService: ErrorService,
              private chickenService: ChickenService,
              private injectionService: InjectionService,
              private settingService: SettingService2,
              private eggService: EggService,
              private authenticationservice: AuthenticationService2,
              private modalService: NgbModal ) {
  }

  ngOnInit() {
    this.loadSetting();
    this.invenService.getAllItems().subscribe(invent => {
      this.inventList = invent;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
      });
    }
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.chicken && this.chicken !== undefined) {
      this.reset();
      this.updatedInjections=[];
      this.injectdates=[];

      let d;
      if(this.displayChicken.dateofbirth){
        d = new Date(this.displayChicken.dateofbirth);
        this.dateofbirth = { date: { year: d.getFullYear(), month: d.getMonth() + 1, day: d.getDate() } };
      }

      if (this.displayChicken.dbid != -1) {
        this.injectionService.loadInjectionBychickenid(this.displayChicken.chickenid).subscribe(injections=>{
          this.injections = injections.slice();
          this.updatedInjections = injections.slice();

          this.updatedInjections.sort(function(a, b) {
            let first = new Date(a.injectdate);
            let second = new Date(b.injectdate);
            return first>second ? 1 : first<second ? -1 : 0;
          });
          this.originalInjections=this.updatedInjections.slice();


          if(this.updatedInjections.length>0){
            this.updatedInjections.forEach((injection, index)=>{
              let i = new Date(injection.injectdate);
              this.injectdates[index] = { date: { year: i.getFullYear(), month: i.getMonth() + 1, day: i.getDate() } };
            });

            this.originalInjectdates=this.injectdates.slice();
          }
        });

        this.eggService.loadEggBychickenid(this.displayChicken.chickenid).subscribe(eggs=>{
          this.displayChicken.totalegg='0';
          eggs.forEach((egg, index)=>{
            this.displayChicken.totalegg=(Number(this.displayChicken.totalegg) + Number(egg.amount)).toString();
          });
          this.chickenService.updateChickenInfo(this.displayChicken);
        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });

      }
      else{
        let inject=new Injection(-1, '', 'Freund’s Adjuvant, Complete', null, '0', '400', 'µg','1:1','Not Complete', this.currentuser.name, new Date());
        this.updatedInjections.push(inject);
        inject=new Injection(-1, '', 'Freund’s Adjuvant, Incomplete', null, '14', '200', 'µg','','Not Complete', this.currentuser.name, new Date());
        this.updatedInjections.push(inject);
        inject=new Injection(-1, '', 'Freund’s Adjuvant, Incomplete', null, '28', '200', 'µg','','Not Complete', this.currentuser.name, new Date());
        this.updatedInjections.push(inject);
        inject=new Injection(-1, '', 'Freund’s Adjuvant, Incomplete', null, '42', '200', 'µg','','Not Complete', this.currentuser.name, new Date());
        this.updatedInjections.push(inject);
        inject=new Injection(-1, '', 'Freund’s Adjuvant, Incomplete', null, '56', '200', 'µg','','Not Complete', this.currentuser.name, new Date());
        this.updatedInjections.push(inject);

      }

    }
    //load all the item table and recipe table data
  }



  loadSetting(){
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateofbirthoptions.dateFormat=this.dateFormat;
    // this.dateofbirthoptions.editableDateField = false;
    this.injectdateoptions.dateFormat=this.dateFormat;
    // this.injectdateoptions.editableDateField = false;
    this.collectiondateptions.dateFormat=this.dateFormat;
    // this.collectiondateptions.editableDateField = false;
    this.firstlayeggdateoptions.dateFormat=this.dateFormat;
    // this.firstlayeggdateoptions.editableDateField = false;
    this.lastlayeggdateoptions.dateFormat=this.dateFormat;
    // this.lastlayeggdateoptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.unitList = [];
    let vUnit =  this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    vUnit.forEach(v => { this.unitList.push(v) });
    let massUnit : String[] = this.settingService.getSettingValuesAsArray(  'massUnit' );
    massUnit.forEach(m => { this.unitList.push(m) })
    let otherUnit : String[] = this.settingService.getSettingValuesAsArray( 'otherUnit' );
    otherUnit.forEach(o => { this.unitList.push(o) });
    this.projectNames = this.settingService.getSettingValuesAsArray( 'chickenProjectNames' );
    this.yolkTiterList = this.settingService.getSettingValuesAsArray( 'eggInventoryTiter' );
  }

  setchickenid(chickenid) {
    this.updatedInjections.forEach(injection=>{
      injection.chickenid=chickenid;
    });
  }

  reset() {
    this.displayChicken = Chicken.newChiken(this.chicken);
    this.isNew ? '' : this.enableEdit = false;
    this.linearChart=undefined;
  }


  removeinjection(index: number) {
    this.updatedInjections.splice(index, 1);
    this.injectdates.splice(index, 1);
  }

  removecollection(index: number) {
    this.updatedEggs.splice(index, 1);
}


  adjuvantFilter(injection:Injection, key: string) {
    if (key == undefined) return;
    this.finditem = true;
    this.adjuvantfilterList = [];
    if (key.trim() == '') {
      this.adjuvantfilterList = [];
      injection.adjuvant = '';
    }
    else {
      this.inventList.forEach(item => {
        if (item.name.toUpperCase().includes(key.toUpperCase())) {
          this.adjuvantfilterList.push({ name: item.name, supplier: item.supplier, cat: item.cat});
        }
      });
    }

    if (this.adjuvantfilterList.length < 1 && key.trim() != '') {
      injection.adjuvant = '';
      this.finditem = false;
      setTimeout(() => this.finditem = true, 5000);
    }
    else {
      this.finditem = true;
    }
  }


  selectAdjuvant(injection:Injection, select: { name: string, supplier: string, cat: string }) {
    injection.adjuvant = select.name;
    this.adjuvantfilterList = undefined;
  }

  addNewInjection() {
    this.enableEdit = true;
    if (this.displayChicken.chickenid) {
      this.updatedInjections.push(new Injection(-1, this.displayChicken.chickenid, '', new Date(), '','','','','Not Complete', this.currentuser.name, new Date()));
    }
    else{
      this.updatedInjections.push(new Injection(-1, '', '', new Date(), '','','','','Not Complete', this.currentuser.name, new Date()));
    }
  }

  validateuserinput(): boolean {
    if(!this.displayChicken.chickenid || this.displayChicken.chickenid.trim()==''){ return false;}

    if(!this.displayChicken.projectname || this.displayChicken.projectname.trim()==''){return false;}
    if(!this.displayChicken.immunogen || this.displayChicken.immunogen.trim()==''){
      return false;
    }
    if(!this.displayChicken.chickenstatus || this.displayChicken.chickenstatus.trim()==''){
      return false;
    }
    return true;
  }

  //save whole kit information include kit components
  save() {
    //search all the kits to see if the catalog number and name are unique
    if (this.validateuserinput()) {
      this.displayChicken.editby=this.currentuser.name;
      this.displayChicken.modify=new Date();
      let chickenid=this.displayChicken.chickenid
      let idarray=chickenid.split('-');
      let sequence= parseFloat(idarray[0]+'.'+idarray[1]) ;

      this.chicken = this.displayChicken;
      let complete=true;
      let i;
      for(i=0;i<this.updatedInjections.length;i++){
        if(this.updatedInjections[i].complete!=='Completed'){
          complete=false;
          break;
        }
      }

      if(complete==true){this.chicken.immunstatus='Completed';}

      if(this.chicken.dbid==-1){
        let injectonpromises=[];
        let sequence= parseFloat((this.chicken.chickenid.split('-')[0]+'.'+this.chicken.chickenid.split('-')[1]));
        this.chicken.sequence=sequence;
        this.chickenService.addChicken(this.chicken).then(_=>{


          this.chicken.dbid=_['dbid'];
          if(this.updatedInjections.length>0){
            this.updatedInjections.forEach(injection=>{
              injectonpromises.push(this.injectionService.addInjection(injection));
              if(injection.bloodtiter && this.chicken.titerdate<injection.injectdate){
                this.chicken.latesttiter=injection.bloodtiter;
              }
            });

            Promise.all(injectonpromises).then(_ => {
              this.enableEdit = false;
              this.isNew=false;
              this.updateChicken.emit(this.chicken);
              this.reset();
            }).catch( error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
            });
          }
          else{
              this.enableEdit = false;
              this.isNew=false;
              this.updateChicken.emit(this.chicken);
              this.reset();
          }
        }).catch( error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
      }
      else{
        let injectonpromises=[];
        this.chickenService.updateChickenInfo(this.chicken).then(()=>{

          this.injectionService.loadInjectionBychickenid(this.chicken.chickenid).subscribe(originalinjections=>{
            if(originalinjections.length>0 || this.updatedInjections.length>0){
              this.updatedInjections.forEach(injection=>{
                if(injection.dbid==-1){
                  injectonpromises.push(this.injectionService.addInjection(injection));
                }
                if(injection.bloodtiter && this.chicken.titerdate<injection.injectdate){
                  this.chicken.latesttiter=injection.bloodtiter;
                }
              });
              originalinjections.forEach(injection=>{
                let index = this.updatedInjections.findIndex(x=>x.dbid==injection.dbid);
                if(index==-1){
                  injectonpromises.push(this.injectionService.deleteInjection(injection));
                }
                else if(JSON.stringify(injection) != JSON.stringify(this.updatedInjections[index])){
                  injectonpromises.push(this.injectionService.updateSingleInjection(this.updatedInjections[index]));
                }
              });
            }
            Promise.all(injectonpromises).then(_ => {
              this.enableEdit = false;
              this.isNew=false;
              this.updateChicken.emit(this.chicken);
              this.reset();
            }).catch( error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
            });
          }, error => {
            ErrorUtil.handleHttpError( error );
            this.errorService.message$ = error.error;
          });
        }).catch( error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
      }
    }
  }

  cancelEdit(){
    this.reset();
    this.updatedInjections=this.originalInjections.slice();
    this.injectdates=this.originalInjectdates.slice();
    this.cancel.emit();
  }

  changedatebirth(event) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayChicken.dateofbirth = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayChicken.dateofbirth = new Date(formatted);
    }
  }

  changeinjectiondate(event, injection:Injection, index:number) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // injection.injectdate = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      injection.injectdate = new Date(formatted);
    }

    if(index==0){
      injection.daysafterimmune='0';
    }
    else{
      if(this.updatedInjections[0].injectdate!=undefined && this.updatedInjections[0].injectdate!=null && injection.injectdate!=undefined && injection.injectdate!=null){
        injection.daysafterimmune=Math.ceil((( new Date(injection.injectdate).getTime() - new Date(this.updatedInjections[0].injectdate).getTime() )/(1000*60*60*24)  )).toString() ;
      }
    }
  }



  changecollectiondate(event, collection:Egg) {
    let d = event.formatted;
    if (d == undefined) return;
    collection.collectiondate = new Date(d);
  }

  changefirstlayeggdate(event, collection:Egg) {
    let d = event.formatted;
    if (d == undefined) return;
    collection.firstlayeggdate = new Date(d);
  }

  changelastlayeggdate(event, collection:Egg) {
    let d = event.formatted;
    if (d == undefined) return;
    collection.lastlayeggdate = new Date(d);
  }


  checkprojectname(name:string){
    if(name.toUpperCase().includes('Project Name')){
      this.displayChicken.projectname='';
    }
    else{
      this.displayChicken.projectname=name;
    }
  }

  checklocation(collection: Egg, location:string){
    if(location.toUpperCase().includes('LOCAION')){
      collection.location='';
    }
    else{
      collection.location=location;
    }
  }

  checkcollectionsublocation(collection:Egg, sublocation:string){
    if(sublocation.toUpperCase().includes('SUBLOCAION')){
      collection.sublocation='';
    }
    else{
      collection.sublocation=sublocation;
    }
  }

  completeimmune(injection:Injection, chicken:Chicken){
    injection.complete='Completed';
    this.injectionService.updateSingleInjection(injection).then(result=>{
      let injectionindex=this.updatedInjections.findIndex(x=>x.dbid==injection.dbid);
      this.updatedInjections[injectionindex]=injection;

      let complete=true;
      let i;
      for(i=0;i<this.updatedInjections.length;i++){
        if(this.updatedInjections[i].complete!=='Completed'){
          complete=false;
          break;
        }
      }

      if(complete==true){
        chicken.immunstatus='Completed';
        this.chickenService.updateChickenInfo(chicken).then(result=>{
          this.chickenimmune.emit(chicken);
        }).catch( error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
       });
      }
    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  addbloodtiterfunction(injection:Injection){
    this.injectionaddtiter=new Injection(injection.dbid,injection.chickenid, injection.adjuvant,injection.injectdate,injection.daysafterimmune,injection.drugamount,injection.unit,injection.bloodtiter,injection.complete, this.currentuser.name, new Date());
    this.addbloodtitermodal=this.modalService.open(this.addbloodtiter, { backdrop: "static", size: 'lg' })
  }

  savebloodtiter(injection:Injection){
    this.addbloodtitermodal.close();
    this.injectionService.updateSingleInjection(injection).then(result=>{

      let injectionindex=this.updatedInjections.findIndex(x=>x.dbid==injection.dbid);
      if(injectionindex!=-1){
        this.updatedInjections[injectionindex]=injection;
      }

      if(this.displayChicken.titerdate<injection.injectdate){
        this.displayChicken.latesttiter=injection.bloodtiter;
        if(this.displayChicken.dbid!=-1){
          this.chickenService.updateChickenInfo(this.displayChicken).then(()=>{
            this.chickenLatestTiter.emit(this.displayChicken);
          }).catch( error => {
            ErrorUtil.handleHttpError( error );
            this.errorService.message$ = error.error;
          });
        }
      }
    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  canceladdbloodtiter(){
    this.addbloodtitermodal.close();
    this.injectionaddtiter=undefined;
  }



  justfortest(n:number){
    if(n<0){
      return -1;
    }
    else{
      return n;
    }
  }



}
