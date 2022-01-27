import { Chicken, Injection, Egg, toggleEgg } from '../../shared/objects/Chicken';
import { Component, OnInit, Output, EventEmitter, Input, OnChanges, SimpleChanges, ViewChild, ElementRef, ComponentFactoryResolver } from '@angular/core';
import { ChickenService } from 'app/shared/services/chicken.service';
import { EggService } from 'app/shared/services/egg.service';
import { InjectionService } from 'app/shared/services/injection.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as XLSX from 'xlsx'
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { SettingService2 } from '../../shared/services2/Setting2.service';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { LocationV2 } from 'app/shared/objects2/LocationV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { item, itemdetail } from '../../shared/objects/item';
import { ItemdetailService } from '../../shared/services/itemdetail.service';
import * as Chart from 'chart.js'
import { Eggtransfer } from '../../shared/objects/eggtransfer';
import { EggtransferService } from '../../shared/services/eggtransfer.service';
import { take } from 'rxjs/operators';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { InventoryService2 } from 'app/shared/services2/Inventory2.service';
import { ErrorService } from '../../page/error/error.service';



@Component({
  selector: 'app-egginventory',
  providers: [ChickenService,
              EggService,
              InjectionService,
              InventoryService2,
              ItemdetailService,
              EggtransferService],
  templateUrl: './egginventory.component.html',
  styleUrls: ['./egginventory.component.css'],
  host: {
    '(document:click)': 'onClick($event) //noinspection UnresolvedVariable',
  }
})

export class EgginventoryComponent implements OnInit, OnChanges {
  @ViewChild('chickenDisplay') chickenDisplay: ElementRef;
  @ViewChild('confirmation') confirmation: ElementRef;
  @ViewChild('checkAmount') checkAmount: ElementRef;
  @ViewChild('titergraph') titergraph: ElementRef;
  @ViewChild('chart') chart: ElementRef;

  @Input() isShowChicken: boolean;
  @Input() chicken: Chicken;
  @Input() isShowEgg: boolean;
  @Input() egg: Egg;
  // @Input() showegg: Egg;

  @Output() cleanView = new EventEmitter();

  @Output() sendout: EventEmitter<any> = new EventEmitter();
  @Output() sendorder = new EventEmitter();
  @Output() opennewitem = new EventEmitter();
  @Output() opennewitemdetail = new EventEmitter();

  //general varables


  isLoading: boolean;
  confirmationmessage: string = '';
  toconfirm: string = '';

  searchKeyword='';
  addeggchicken:Chicken;

  //chicken variables
  newChicken:Chicken;
  displayChicken: Chicken;
  isNewChicken:boolean;
  enableEditChicken = false;
  chickenCount=0;
  chickenviewColumn: boolean[];
  chickencolumnList: string[];
  editseperateegg:boolean;

  selectcheckegg:Egg;
  remainamount:string;
  reducevalue:string='';
  amountalert=false;
  opencheckmodal:any;


  frozendateoptions: IAngularMyDpOptions = {
    dateFormat: 'ddmmmyyyy',
    // editableDateField : false
  };
  frozendate: { date: { year: number, month: number, day: number } }[]=[];
  locations: LocationV2[] = [];
  subLocations: LocationV2[] = [];

  eggseperatemodal:any;

  displayEgg:Egg;
  eggs: Egg[]=[];
  newEgg:Egg;
  isNewEgg:boolean;
  enableEditEgg = false;
  subcolspan: number;
  selectWholeEggs: toggleEgg[];


  chickenWithEggs: { chick: Chicken, show: boolean, eggs: Egg[] }[];
  displayChickens: { chick: Chicken, show: boolean, eggs: Egg[] }[];

  importfile;
  message: string;
  page: number;
  searchKey: string;
  colCount: number;
  currentuser: UserV2;
  yolktiterlist : string[]=[];


  checkoutoption:string;

  antibodylist:any[]=[];
  antibodyselectlist:any[];
  antibodyname:string;
  shownoresult=true;
  isSelect=false;


  linearChart:any;
  LinearChartOptions={
    scaleShowVerticalLines:false,
    responsive:true
  }

  titerChart:Chart;
  chartOptions={
    scaleShowVerticalLines:false,
    responsive:true
  }

  immunogengraph='';
  openchartmodal:any;
  chartLabels=[]
  chartData=[];


  constructor(private modalService: NgbModal,
    private errorService: ErrorService,
              private chickenService: ChickenService,
              private eggService: EggService,
              private injectionService: InjectionService,
              private settingService: SettingService2,
              private locationService: LocationService2,
              private authenticationservice: AuthenticationService2,
              // private inventservice: InventoryService,
              private inventservice: InventoryService2,
              private itemdetailservice: ItemdetailService,
              private eggtransferservice: EggtransferService) {}

  onClick(event) {

    if (event !== undefined && event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('chickenId')) !== undefined) {
      let itempath = event.path.find(x => x.className.includes('chickenId'));
      let chickenindex=itempath.id;
      this.openChicken(this.displayChickens[chickenindex].chick);
    }
    else if (event !== undefined && event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('eggpart')) !== undefined) {
      let itempath = event.path.find(x => x.className.includes('eggpart'));
      let totalindex = itempath.id.split(' ');
      let chickenindex = totalindex[0];
      let eggindex = totalindex[1];
      if (chickenindex == -1 || chickenindex >= this.displayChickens.length || this.displayChickens[chickenindex] == undefined || this.displayChickens[chickenindex].chick == undefined) return;
      this.showEggDetail(this.displayChickens[chickenindex].eggs[eggindex]);
    }
    else if (event !== undefined && event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('amount')) !== undefined) {
      let itempath = event.path.find(x => x.className.includes('amount'));
      let totalindex = itempath.id.split(' ');
      let chickenindex = totalindex[0];
      let eggindex = totalindex[1];
      if (chickenindex == -1 || chickenindex >= this.displayChickens.length || this.displayChickens[chickenindex] == undefined || this.displayChickens[chickenindex].chick == undefined) return;
      this.selectcheckegg=this.displayChickens[chickenindex].eggs[eggindex];
      this.remainamount=this.selectcheckegg.amount;
      this.checkoutoption=undefined;
      this.opencheckmodal=this.modalService.open(this.checkAmount, { backdrop: "static", size: 'lg' })
    }
    else if (event.path !== undefined && event.path.find(x => x.innerText !== undefined && x.innerText.trim() == 'New Chicken') !== undefined) {
      this.CreateNew()
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.innerText !== undefined && x.innerText.trim() == 'Titer Graph') !== undefined) {
      this.OpenTiterGraphModal();
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.innerText !== undefined && x.innerText.trim() == 'Egg Seperation') !== undefined) {
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.innerText !== undefined && x.innerText.trim() == 'Add Eggs') !== undefined) {
      let itempath = event.path.find(x => x.className.includes('addegg'));
      let chickenindex=itempath.id;
      this.addegg(this.displayChickens[chickenindex].chick);
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'slide') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'eggsep') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'checkoutbox') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'mydpicon icon-mydpcalendar') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'btnpicker btnpickerenabled') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'form-control selectstyle') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'sucrose') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'selection ng-untouched ng-pristine ng-valid') !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('form-control inputstyle') ) !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className == 'btn btn-primary save' ) !== undefined){
      return;
    }
    else if (event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('modal') ) !== undefined){
      return;
    }
    else if (event !== undefined && event.path !== undefined && event.path.find(x => x.className !== undefined && x.className.includes('showbutton')) !== undefined){
      return;
    }
    else {
      if (!this.isNewChicken) {
        this.resetOpenChicken();
        this.resetegg();
        this.isShowEgg=false;
        this.isShowChicken=false;
        return;
      }
      else{
        return;
      }
    }
  }


  ngOnInit() {

    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
      });
    }
    this.isLoading = true;
    this.searchKey = '';
    this.page = 1;
    this.loadSetting();
    this.chickenService.getCount().subscribe(c => {
      this.chickenCount = c;
      this.loadChickenPage();
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

    this.chickenviewColumn = [true, true, true, true, true,  true, true, true, true];
    this.chickencolumnList = ['Chicken Id',  'Immunogen', 'Date of Birth', 'Egg In Stock', 'Egg Used',  'Latest Titer', 'Projet Name', 'Immunization Status', 'Chicken Status'];
    this.subcolspan = 2;
    this.chickencolumnList.forEach(v => {
      if (v) this.subcolspan++;
    })

    this.inventservice.getAllItems().subscribe(invents => {
      invents.forEach(invent=>{
        if(invent.type=='Antibody' && invent.supplier=='Somru BioScience Inc.'){
          this.antibodylist.push(invent)
        }
      })
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });


  }

  ngOnChanges(change: SimpleChanges) {
    if (change.isShowChicken && this.isShowChicken && this.chicken !== undefined) {
      if (!change.showchicken.isFirstChange()) {
        this.displayChicken = this.chicken;
      }
    }

  }

  open(content) {
    this.modalService.open(content, { backdrop: "static", size: 'lg' }).result
      .then();
  }

  loadSetting(){
    this.locations = this.locationService.getAllLocations();
    this.yolktiterlist = this.settingService.getSettingValuesAsArray( 'eggInventoryTiter' );
  }


  loadChickenPage() {
    this.isLoading = true;
    this.chickenWithEggs=[];
    this.chickenService.loadChickenWEggsPage(this.page - 1).subscribe(cwe => {
      cwe.forEach(mix=>{
        mix.eggs.sort(function(a, b) {
          if (a.collectiondate < b.collectiondate) return -1;
          if (a.collectiondate > b.collectiondate) return 1;
          return 0;
        });
      this.chickenWithEggs.push({ chick: mix.chick, show: false, eggs: mix.eggs });
      });
    this.displayChickens = this.chickenWithEggs.slice();
    this.isLoading = false;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }




  openChicken(c: Chicken) {
    if(!c|| !c.dbid){return;}
    let getChicken = this.chickenService.getChickenByDbid(c.dbid).pipe(take(1));
    if(!getChicken){return;}
    getChicken.subscribe(chicken=>{
      this.isShowChicken = true;
      this.displayChicken = chicken;
      this.isShowEgg = false;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  searchChicken(key: string) {
    if (key == undefined || key == '') {
      this.searchKeyword = '';
      this.chickenService.getCount().subscribe(c => {
      this.chickenCount = c;
        this.page = 1;
        this.loadChickenPage();
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
    else if (key !== this.searchKeyword) {

      this.searchKeyword = key;
      this.chickenWithEggs = [];
      this.chickenService.searchChickenbyChickenId(this.searchKeyword, this.page - 1).subscribe(cwe => {
        cwe.forEach(chickwithegg => {
          chickwithegg.eggs.sort(function(a, b) {
            if (a.collectiondate < b.collectiondate) return -1;
            if (a.collectiondate > b.collectiondate) return 1;
            return 0;
          });
          this.chickenWithEggs.push({ chick: chickwithegg.chick, show: false, eggs: chickwithegg.eggs });
        });
        this.displayChickens = this.chickenWithEggs.slice();
        this.chickenService.searchChickenbyChickenIdCount(this.searchKeyword).subscribe(c => {
          this.chickenCount = c;
          this.isLoading=false;
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


  CreateNew(){
      this.newChicken = new Chicken(-1, '', new Date(), '', '', 'Active', '0', '0', '0', '', null, '',null,0, 'Active');
      this.isNewChicken=true;
      this.enableEditChicken = true;
      this.displayChicken = this.newChicken;
      this.isShowChicken = true;
  }

  saveChicken(chicken: Chicken) {
    if (chicken == undefined) return;
    let index = this.displayChickens.findIndex(x=>x.chick.dbid==chicken.dbid)

    if(index!=-1){
      this.displayChickens[index].chick=chicken;
      this.resetOpenChicken();
    }
    else{
      this.resetOpenChicken();
      this.loadChickenPage();
    }

  }

  resetOpenChicken() {
    this.enableEditChicken = false;
    this.isNewChicken = false;
    this.selectWholeEggs=[];
    this.isShowChicken = false;
  }

  resetegg(){
    this.enableEditEgg = false;
    this.isNewEgg = false;
    this.isShowEgg = false;
  }


  toggleViewCol(index: number) {
      this.chickenviewColumn[index] = !this.chickenviewColumn[index];
      this.subcolspan = 2;
      this.chickenviewColumn.forEach(v => {
        if (v) this.subcolspan++;
      })
  }

  openconfirmation(message) {
    let messages = message.split(' ');
    if (messages[0] == 'delete') {
      this.toconfirm = 'delete';
      if(messages[1] == 'chicken'){
        this.confirmationmessage = 'Are you sure you want to delete this chicken?';
      }
      else{
        this.confirmationmessage = 'Are you sure you want to delete this egg?';
      }

      this.modalService.open(this.confirmation, { backdrop: "static" }).result.then(result => { });
    }
  }

  action() {

    if (this.toconfirm == 'delete') {
      if(this.confirmationmessage.includes('chicken')){
        this.deleteChicken();
        this.isShowChicken = false;
      }
      else{
        this.deleteEgg();
        this.isShowEgg = false;
      }

    }
  }

  saveEgg(event){
    let chickenindex = this.displayChickens.findIndex(x=>x.chick.chickenid==event.egg.chickenid);
    if(chickenindex!=-1){
      this.displayChickens[chickenindex].chick=event.chicken;
      let eggindex = this.displayChickens[chickenindex].eggs.findIndex(x=>x.dbid==event.egg.dbid)
      if(eggindex!=-1){
        this.displayChickens[chickenindex].eggs[eggindex]=event.egg;
        this.displayChickens[chickenindex].show=true;
      }
      else{
        this.displayChickens[chickenindex].eggs.push(event.egg);
        this.displayChickens[chickenindex].show=true;
      }

    }

    this.enableEditEgg = false;
    this.resetegg();

  }

  deleteEgg(){
    let chickenid=this.displayEgg.chickenid
    this.eggService.deleteEgg(this.displayEgg).then(()=>{
      this.chickenService.getChickenByChickenId(chickenid).subscribe(chicken=>{

          let chickenindex = this.displayChickens.findIndex(x => x.chick.chickenid == this.displayEgg.chickenid);
          if (chickenindex != -1) {
            this.displayChickens[chickenindex].chick=chicken;
            let index = this.displayChickens[chickenindex].eggs.findIndex(x => x.dbid == this.displayEgg.dbid);
            this.displayChickens[chickenindex].eggs.splice(index, 1);
          }

          this.displayEgg = undefined;
          this.isShowEgg = false;
          this.selectWholeEggs=[];


      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });

    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  changeaddsucrose(egg:toggleEgg){
    if(egg.addsucrose!='Yes'){
      egg.addsucrose='Yes';
    }
    else{
      egg.addsucrose='No'
    }
  }





  deleteChicken() {
    if (this.displayChicken == undefined || this.displayChicken.dbid == -1) { return; }
    let chickendbid = this.displayChicken.dbid;

    this.chickenService.deleteChicken(this.displayChicken).then(result=>{

      this.displayChicken = undefined;
      this.isShowChicken = false;
      let index = this.displayChickens.findIndex(x=>x.chick.dbid==chickendbid);
      if(index!=-1){
        this.displayChickens.splice(index,1);
      }

    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  addegg(chicken: Chicken) {
    if (chicken == undefined) return;
    this.isShowChicken = false;
    this.addeggchicken=chicken;
    this.injectionService.loadInjectionBychickenid(chicken.chickenid).subscribe(injections=>{
      let injecttionlist:Injection[]=[];
      if(injections.length>0){
        injections.forEach(injection=>{
          injecttionlist.push(new Injection(injection.dbid, injection.chickenid, injection.adjuvant, injection.injectdate, injection.daysafterimmune,injection.drugamount, injection.unit, injection.bloodtiter, injection.complete, this.currentuser.name, new Date()))
        })

        injecttionlist.sort(function(a, b) {
          let first = new Date(a.injectdate);
          let second = new Date(b.injectdate);
          return first>second ? 1 : first<second ? -1 : 0;
      });



        this.displayEgg = new Egg(-1, chicken.chickenid, chicken.immunogen, 'Whole Egg', new Date(), new Date(injecttionlist[0].injectdate), '', new Date(), new Date(), '', '', '', null, '', '', this.currentuser.name, new Date());
        this.displayEgg.daysafterimmune=Math.floor( ((this.displayEgg.collectiondate.getTime()   - this.displayEgg.firstinjectdate.getTime() )/(1000*60*60*24)  )) .toString();
        this.isShowEgg = true;
        this.enableEditEgg = true;
        this.isNewEgg = true;
      }
      else{
        return;
      }

    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  showEggDetail(e){
    this.displayEgg = e;
    this.isShowEgg = true;
    this.enableEditEgg = true;
    this.isShowChicken = false;
    this.enableEditEgg=false;
  }

  toggleSelectEgg(egg:Egg){

    if (egg == undefined || egg.dbid == undefined || egg.dbid < 0) return;
    if (this.selectWholeEggs == undefined) {
      this.selectWholeEggs = [];
    }

    let index = this.selectWholeEggs.findIndex(x => x.dbid == egg.dbid)

    if (index == -1) {
      this.selectWholeEggs.push(
        new toggleEgg(egg.dbid, egg.chickenid, egg.immunogen,
          egg.eggpart, egg.collectiondate, egg.firstinjectdate,
          egg.daysafterimmune, egg.firstlayeggdate, egg.lastlayeggdate,
          egg.amount, '', '',new Date(), egg.addsucrose, egg.titer, '', ''));
    }
    else {

      this.selectWholeEggs.splice(index, 1);
    }


  }

  checkEggSelect(egg:Egg): boolean{
    if (egg == undefined) return false;

    if (this.selectWholeEggs == undefined || this.selectWholeEggs.length < 1) return false;

    if (this.selectWholeEggs.find(x => x.dbid == egg.dbid) == undefined) return false;

    return true;
  }

  eggseperation(content){
    this.editseperateegg=true;
    this.eggseperatemodal=this.modalService.open(content, { backdrop: "static", size: 'lg' });
  }

  validateEggSeperation(){
    let validate=true;
    let index;
    for(index=0;index<this.selectWholeEggs.length; index++){
      if(!this.selectWholeEggs[index].consumeeggnumber ||
          this.selectWholeEggs[index].consumeeggnumber.trim()=='0' ||
          Number( this.selectWholeEggs[index].consumeeggnumber) == NaN) {
        this.message = 'Number of Egg(s) seperated is a required numeric field.';
        validate= false;
        break;
      }
      else if(  Number(this.selectWholeEggs[index].consumeeggnumber.trim())>Number(this.selectWholeEggs[index].amount.trim())){
        this.message = 'Number of Egg(s) seperated should not be more than number egg(s) in stock: (' +
          this.selectWholeEggs[index].amount.trim() + ')';
        validate= false;
        break;
      }

      if(!this.selectWholeEggs[index].saveyolknumber ||
          this.selectWholeEggs[index].saveyolknumber.trim()=='0' ||
          Number( this.selectWholeEggs[index].saveyolknumber) == NaN) {
        this.message = 'Number of Yolk(s) saved is a required numeric field.';
        validate= false;
        break;
      }
      else if(Number(this.selectWholeEggs[index].saveyolknumber.trim())>Number(this.selectWholeEggs[index].consumeeggnumber.trim())){
        this.message = 'Number of yolk saved should be not more than Number of Seperated Eggs (' +
          this.selectWholeEggs[index].consumeeggnumber + ')';
        validate= false;
      }

      if(!this.frozendate[index] ){
        this.message = 'Frozen Date is required.';
        validate= false;
        break;
      }

      if(!this.selectWholeEggs[index].location || this.selectWholeEggs[index].location.trim()=='' ){
        this.message = 'Location is required.';
        validate= false;
        break;
      }
    }
    if ( this.message ) setTimeout(_ => { this.message = undefined }, 5000);
    return validate;
  }

  saveYolk(){
    if(this.validateEggSeperation()){
      let allpromises=[];
      let start=false;

      this.selectWholeEggs.forEach((selectegg,eggindex)=>{

        this.eggService.getEggWithDbid(selectegg.dbid).subscribe(wholeegg=>{
          wholeegg.amount = (Number(selectegg.amount)-Number(selectegg.consumeeggnumber)).toString();

          this.eggService.updateSingleEgg(wholeegg).then(result=>{
            let newyolk;
            if(selectegg.addsucrose=='Yes'){
              newyolk= new Egg(-1, selectegg.chickenid, selectegg.immunogen, 'Yolk', selectegg.collectiondate,selectegg.firstinjectdate,selectegg.daysafterimmune,selectegg.firstlayeggdate, selectegg.lastlayeggdate, selectegg.saveyolknumber,selectegg.location,selectegg.sublocation, selectegg.frozendate,selectegg.addsucrose,selectegg.titer, this.currentuser.name, new Date());
            }
            else{
              newyolk= new Egg(-1, selectegg.chickenid, selectegg.immunogen, 'Yolk', selectegg.collectiondate,selectegg.firstinjectdate,selectegg.daysafterimmune,selectegg.firstlayeggdate, selectegg.lastlayeggdate, selectegg.saveyolknumber,selectegg.location,selectegg.sublocation, selectegg.frozendate,'No',selectegg.titer, this.currentuser.name, new Date());
            }

            this.eggService.addEgg(newyolk).then(_=>{

              let neweggtransfer = new Eggtransfer(-1, selectegg.chickenid, selectegg.dbid.toString(), 'Egg Seperation', selectegg.consumeeggnumber, 'Egg', _['dbid'], this.currentuser.name, new Date() );
              this.eggtransferservice.addEggtransfer(neweggtransfer);
              this.chickenService.getChickenByChickenId(selectegg.chickenid).subscribe(chicken=>{

                chicken.totalegg= (Number(chicken.totalegg)-Number(selectegg.consumeeggnumber)+Number(selectegg.saveyolknumber)).toString();

                allpromises.push(this.chickenService.updateChickenInfo(chicken));
                if(selectegg.titer && selectegg.titer.trim()!='' && selectegg.titer.trim()!='0'){
                  if(chicken.titerdate<selectegg.collectiondate){
                    chicken.latesttiter=selectegg.titer;

                  }

                }

                if(eggindex==this.selectWholeEggs.length-1){
                  Promise.all(allpromises).then(result=>{

                    this.canceleggseperate();
                    this.chickenService.getCount().subscribe(c => {
                      this.chickenCount = c;
                      this.loadChickenPage();
                    })



                  })

                }
              }, error => {
                ErrorUtil.handleHttpError( error );
                this.errorService.message$ = error.error;
              });

            }).catch( error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
            });

          }).catch( error => {
            ErrorUtil.handleHttpError( error );
            this.errorService.message$ = error.error;
          });
        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });

      });
    }
  }

  canceleggseperate(){
    this.eggseperatemodal.close();
    this.selectWholeEggs=[];
    this.subLocations = [];
    this.searchKeyword='';
  }

  OpenTiterGraphModal(){
    this.immunogengraph='';
    this.openchartmodal=this.modalService.open(this.titergraph, { backdrop: "static", size: 'lg' });
  }

  cancelGraph(){
    this.openchartmodal.close();
    this.immunogengraph='';
  }



  generateTiterDataGraphFromImmunogen(){
    this.titerChart=undefined;
    this.chartLabels=[]
    this.chartData=[];
    let alltiterdata: { days: number, log: number, type: string}[]=[];
    let egggroup:Egg[]=[];
    let antibodygroup:itemdetail[]=[];

    if(this.immunogengraph.trim()==''){return;}

    let immunogenresult=this.chickenService.getChickenByImmunogen(this.immunogengraph.trim());
    if(immunogenresult!=undefined){
      immunogenresult.subscribe(chickens=>{

        chickens.forEach((chicken, index)=>{
          alltiterdata=[];
          this.injectionService.loadInjectionBychickenid(chicken.chickenid).subscribe(injections=>{
            injections.forEach((injection, injectionindex)=>{
              if(injectionindex==0){
                let log=Math.log(1);
                alltiterdata.push({ days: 0, log: log, type: 'Serum'});
              }
              else{
                if(injection.bloodtiter!=undefined && injection.bloodtiter!=null && injection.bloodtiter.trim()!=''){
                  let position = injection.bloodtiter.indexOf(':');
                  let titer=injection.bloodtiter.substring(position+1)
                  titer=titer.replace(/,/g,'')
                  let titernumber=Number(titer)
                  let log=Math.log10(titernumber);
                  alltiterdata.push({ days: Number(injection.daysafterimmune), log: log, type: 'Serum'});
                }

              }

            })

            this.eggService.loadEggBychickenid(chicken.chickenid).subscribe(eggs=>{
              eggs.forEach(egg=>{
                if(egg.amount!=undefined && egg.amount!=null && egg.amount.trim()!='' && egg.amount.trim()!='0'){
                  egggroup.push(egg);
                }
                if(egg.titer!=undefined && egg.titer!=null && egg.titer.trim()!=''){

                  let position = egg.titer.indexOf(':');
                  let titer=egg.titer.substring(position+1)
                  titer=titer.replace(/,/g,'')

                  let titernumber=Number(titer)
                  let log=Math.log10(titernumber);

                  let days;
                  if(egg.daysafterimmune.includes('-')){
                    days=egg.daysafterimmune.split('-')[1];
                  }
                  else{
                    days=egg.daysafterimmune;
                  }
                  let daysafterimmune=egg.daysafterimmune.split('-')[1]
                  alltiterdata.push({ days: Number(days), log: log, type: egg.eggpart});
                }
              })

              //get all the eggtransfer purify antibody of this chickenid from eggtransfer table
              this.eggtransferservice.getEggtransfersByChickenId(chicken.chickenid).subscribe(eggtransfers=>{
                let eggpurifyarray:Eggtransfer[]=[];
                eggtransfers.forEach(eggtransfer=>{
                  if(eggtransfer.action=='Purify Antibody'){
                    eggpurifyarray.push(eggtransfer);
                  }
                });

                if(eggpurifyarray.length>0){
                  eggpurifyarray.forEach(eggpurify=>{
                    if(eggpurify.destinationtable=='Item'){
                      this.itemdetailservice.loadItemDetailByItemDbid(Number(eggpurify.destinationdbid)).subscribe(antibodies=>{
                        antibodies.forEach(antibody=>{
                          if(  (antibody.amount!=undefined && antibody.amount!=null && antibody.amount.trim()!='' && antibody.amount!='0') ||
                          (antibody.inuse!=undefined && antibody.inuse!=null && antibody.inuse.trim()!='' && antibody.inuse!='0')  ){
                            antibodygroup.push(antibody);
                          }
                        });
                      })
                    }
                  })

                }


              })


              alltiterdata.sort((a, b) => (a.days > b.days) ? 1 : -1)
              alltiterdata.forEach(data=>{
                this.chartLabels.push(data.type+' '+data.days+' '+'days')
                this.chartData.push(data.log);
              })



              this.titerChart=new Chart('canvas', {
                type:'line',
                data:{
                  labels:this.chartLabels,
                  datasets:[
                    {
                      data:this.chartData,
                      borderColor: '#3cba9f',
                      fill:false
                    },
                  ]
                },
                options:{
                  responsive:true,
                  title:{
                    display:true,
                    text:'Titer Linear Chart'
                  },
                  legend:{
                    display:false
                  },
                  tooltips: {
                    enabled:false,
                    mode: 'index',
                    intersect: true,
                    callbacks:{
                                          },
                    custom: function(tooltipModel) {

                        // Tooltip Element
                        var tooltipEl = document.getElementById('chartjs-tooltip');
                        tooltipEl.innerHTML = '<table></table>';
                        //document.body.appendChild(tooltipEl);

                        // Create element on first render
                        if (!tooltipEl) {

                          var tooltipEl = document.getElementById('chartjs-tooltip');
                          tooltipEl = document.createElement('div');
                          tooltipEl.id = 'chartjs-tooltip';
                          tooltipEl.innerHTML = '<table></table>';
                          document.body.appendChild(tooltipEl);
                        }

                        // Hide if no tooltip
                        if (tooltipModel.opacity === 0) {
                            tooltipEl.style.opacity = '0';
                            return;
                        }

                        if (!tooltipModel.body) {
                          tooltipEl.style.opacity = '0';
                          return;
                      }

                        // Set caret Position
                        tooltipEl.classList.remove('above', 'below', 'no-transform');
                        if (tooltipModel.yAlign) {
                            tooltipEl.classList.add(tooltipModel.yAlign);
                        } else {
                            tooltipEl.classList.add('no-transform');
                        }

                        function getBody(bodyItem) {
                            return bodyItem.lines;
                        }

                        // Set Text
                        if (tooltipModel.body) {

                          let currentdays=Number(tooltipModel.title[0].trim().split(' ')[1]);


                          let titer='1:1';
                          let titernumber=Number(tooltipModel.body[0].lines[0]);

                          let i;
                          for(i=0;i<titernumber;i++){
                            titer=titer+'0';
                          }
                          tooltipModel.title[1]='Titer: '+titer;


                          let wholeeggnumber=0;
                          let yolknumber=0;
                          let antibodynumber=0;

                          tooltipModel.body=[];

                          let currentindex=alltiterdata.findIndex(x=>x.days==currentdays);
                          let preciousdays=alltiterdata[currentindex-1].days;
                          tooltipModel.title[0]='From '+preciousdays+' to '+currentdays+' days';
                          egggroup.forEach(egg=>{
                            if(egg.amount==undefined || egg.amount==null){
                              egg.amount='0';
                            }
                            let eggdays=egg.daysafterimmune;
                            if(eggdays.includes('-')){
                              eggdays=eggdays.split('-')[1];
                            }
                            if(egg.eggpart=='Whole Egg' && Number(eggdays)<=currentdays &&  Number(eggdays)>preciousdays  ){
                              wholeeggnumber=wholeeggnumber+Number(egg.amount);
                            }
                            if(egg.eggpart=='Yolk' && Number(eggdays)<=currentdays &&  Number(eggdays)>preciousdays  ){
                              yolknumber=yolknumber+Number(egg.amount);
                            }
                          });
                          antibodygroup.forEach(antibody=>{
                            let antibodydays=Number(antibody.columnnumber.split('-')[1]);
                            if(antibodydays<=currentdays && antibodydays>preciousdays){
                              antibodynumber=antibodynumber+Number(antibody.amount)+Number(antibody.inuse);
                            }
                          });
                          //tooltipModel.body.push('<i _ngcontent-c6="" aria-hidden="true" class="fa fa-plus"></i>'+" "+'Whole Egg: '+wholeeggnumber);
                          //tooltipModel.body.push('<img src="../../../assets/images/AntibodyIcon.ico" width="15" height="15">'+" "+'Antibdoy: '+antibodynumber);

                          // tooltipModel.body = '';

                          tooltipModel.body[0].before.push('<img src="../../../assets/images/EggIcon.ico" width="15" height="15">'+" "+'Whole Egg: '+wholeeggnumber);
                          tooltipModel.body[0].before.push('<img src="../../../assets/images/YolkIcon.ico" width="15" height="15">'+" "+'Yolk: '+yolknumber);
                          tooltipModel.body[0].before.push('<img src="../../../assets/images/AntibodyIcon2.ico" width="15" height="15">'+" "+'Antibdoy: '+antibodynumber);



                              var titleLines = tooltipModel.title || [];
                             //var bodyLines = tooltipModel.body.map(getBody);
                              var bodyLines = tooltipModel.body || [];

                              var innerHtml = '<thead>';

                              titleLines.forEach(function(title) {
                                  innerHtml += '<tr><th>' + title + '</th></tr>';
                              });
                              innerHtml += '</thead><tbody>';

                              bodyLines.forEach(function(body, i) {
                                  // var colors = tooltipModel.labelColors[0];
                                  var style = 'background:' + tooltipModel.backgroundColor;
                                  style += '; border-color:' + tooltipModel.borderColor;
                                  style += '; border-width: 2px';
                                  var span = '<span style="' + style + '"></span>';
                                  innerHtml += '<tr><td>' + span + body + '</td></tr>';
                              });
                              innerHtml += '</tbody>';



                              var tableRoot = tooltipEl.querySelector('table');

                              tableRoot.innerHTML = innerHtml;
                          }

                        // `this` will be the overall tooltip
                        var position = this._chart.canvas.getBoundingClientRect();

                        // Display, position, and set styles for font
                        tooltipEl.style.opacity = '1';
                        tooltipEl.style.position = 'absolute';
                        //tooltipEl.style.left = position.left + window.pageXOffset + tooltipModel.caretX + 'px';
                        tooltipEl.style.left = window.pageXOffset + tooltipModel.caretX + 'px';
                        tooltipEl.style.top = position.top + window.pageYOffset + tooltipModel.caretY+5 + 'px';
                        tooltipEl.style.fontFamily = tooltipModel._bodyFontFamily;
                        tooltipEl.style.fontSize = tooltipModel.bodyFontSize + 'px';
                        tooltipEl.style.fontStyle = tooltipModel._bodyFontStyle;
                        tooltipEl.style.padding = tooltipModel.yPadding + 'px ' + tooltipModel.xPadding + 'px';
                        tooltipEl.style.pointerEvents = 'none';
                        tooltipEl.style.height = '110px';


                    }
                  },
                  hover: {
                    mode: 'dataset',
                    intersect: false
                  },
                  events: ['click','mousemove'],
                  scales:{
                    xAxes:[{
                      display:true,
                      scaleLabel:{
                        display:true,
                        labelString:'Days After First Immunization',
                        fontStyle: "bold"
                      },
                    }],

                    yAxes:[{
                      display:true,
                      scaleLabel:{
                        display:true,
                        labelString:'Log10 of Titers',
                        fontStyle: "bold"
                      }
                    }],
                  }
                }

              });



            })

          })

        })

      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });

    }
  }


  showdetail(event){
    var canvas = <HTMLCanvasElement> document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
  }



  checklocation(selectegg:toggleEgg, location:string){
    if(location.toUpperCase().includes('LOCAION')){
      selectegg.location='';
    }
    else{
      selectegg.location=location;
    }
  }

  checksublocation(selectegg:toggleEgg, sublocation:string){
    if(sublocation.toUpperCase().includes('SUBLOCAION')){
      selectegg.sublocation='';
    }
    else{
      selectegg.sublocation=sublocation;
    }
  }



  showSubLocation(l: string) {
    this.subLocations = [];
    let target = this.locations.find(x => x.locationName == l);
    if (target == undefined) return;
    target.subLocations.forEach(sl => {
      this.subLocations.push(sl);
    });
  }


  changefrozendate(selectegg:toggleEgg, event, index:number){
    // let d = event.formatted;
    // if (d == undefined) return;
    // selectegg.frozendate = new Date(d);
    // let e = new Date(selectegg.frozendate);
    // this.frozendate[index] = { date: { year: e.getFullYear(), month: e.getMonth() + 1, day: e.getDate() } };

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted == undefined) return;
    selectegg.frozendate = new Date(formatted);
    let e = new Date(selectegg.frozendate);
    this.frozendate[index] = { date: { year: e.getFullYear(), month: e.getMonth() + 1, day: e.getDate() } };
  }

  resetcheck(){
    this.selectcheckegg=undefined;
    this.amountalert=false;
    this.remainamount=undefined;
    this.reducevalue='';
    this.checkoutoption=undefined;
    this.antibodyselectlist=undefined;
    this.antibodyname=undefined;
    this.shownoresult=true;
  }

  changeremain(){
    this.amountalert=false;
    this.remainamount=this.selectcheckegg.amount
    if(Number(this.reducevalue)<=Number(this.selectcheckegg.amount)){
      this.remainamount=(Number(this.remainamount)-Number(this.reducevalue)).toString();
    }
    else{
      this.amountalert=true;
      this.reducevalue='';
      this.remainamount=this.selectcheckegg.amount
    }

  }

  filterantibodyname(key: string) {
    if (key == undefined) return;
    this.shownoresult=true;
    this.isSelect=false;
    this.antibodyselectlist = [];
    if(key.trim()==''){
      this.antibodyselectlist = [];
    }
    else{
      this.antibodylist.forEach((item, i) => {
        if (item.name.toUpperCase().includes(key.toUpperCase())  ) {
          this.antibodyselectlist.push(item);
        }
      });
    }
  }


  selectantibody(antibody: item) {
    if (antibody == undefined) return;
    this.antibodyname=antibody.name;
    this.antibodyselectlist=undefined;
    this.isSelect=true;
  }

  validcheckout(){
    let pass=true;

    this.reducevalue= this.reducevalue.toString().trim();
    if(this.checkoutoption==undefined){
      pass=false;
    }

    if(this.reducevalue==undefined || this.reducevalue=='0' || this.reducevalue==''){
      pass=false;
    }


    if(this.checkoutoption=='Purify Antibody' && (this.antibodyname==undefined || this.antibodyname.trim()=='') ){
      pass=false;
    }



    return pass;

  }


  savecheckout(){
    if(this.validcheckout()==false){
      return;
    }


    let name=this.antibodyname;

    let option=this.checkoutoption;

    if(option=='Discard Eggs'){
      this.selectcheckegg.amount=this.remainamount;
      this.eggService.updateSingleEgg(this.selectcheckegg).then(result=>{
        let neweggtransfer = new Eggtransfer(-1, this.selectcheckegg.chickenid, this.selectcheckegg.dbid.toString(), 'Discard Eggs', this.reducevalue, 'Egg', '', this.currentuser.name, new Date() );
        this.eggtransferservice.addEggtransfer(neweggtransfer);


        this.chickenService.getChickenByChickenId(this.selectcheckegg.chickenid).subscribe(chicken=>{
          chicken.totalegg=(Number(chicken.totalegg)-Number(this.reducevalue)).toString();
          chicken.eggdiscard=(Number(chicken.eggdiscard)+Number(this.reducevalue)).toString();

          this.chickenService.updateChickenInfo(chicken).then(result=>{
            let chickenindex=this.displayChickens.findIndex(x=>x.chick.chickenid==this.selectcheckegg.chickenid)
            if(chickenindex!=-1){
              this.displayChickens[chickenindex].chick=chicken;
              let eggindex= this.displayChickens[chickenindex].eggs.findIndex(x=>x.dbid==this.selectcheckegg.dbid);
              if(eggindex!=-1){
                this.displayChickens[chickenindex].eggs[eggindex].amount=this.selectcheckegg.amount;
                this.displayChickens[chickenindex].show=true;
              }
            }

            this.cancelcheckout();
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
    else{
      let useamount= this.reducevalue;
      this.selectcheckegg.amount=this.remainamount;
      let chickenid = this.selectcheckegg.chickenid;
      let eggdbid = this.selectcheckegg.dbid;
      this.eggService.updateSingleEgg(this.selectcheckegg).then(result=>{

        this.chickenService.getChickenByChickenId(this.selectcheckegg.chickenid).subscribe(chicken=>{
          chicken.totalegg=(Number(chicken.totalegg)-Number(this.reducevalue)).toString();
          chicken.eggused=(Number(chicken.eggused)+Number(this.reducevalue)).toString();

          this.chickenService.updateChickenInfo(chicken).then(result=>{
            let chickenindex=this.displayChickens.findIndex(x=>x.chick.chickenid==this.selectcheckegg.chickenid)
            if(chickenindex!=-1){
              this.displayChickens[chickenindex].chick=chicken;
              let eggindex= this.displayChickens[chickenindex].eggs.findIndex(x=>x.dbid==this.selectcheckegg.dbid);
              if(eggindex!=-1){
                this.displayChickens[chickenindex].eggs[eggindex].amount=this.selectcheckegg.amount;
                this.displayChickens[chickenindex].show=true;
              }
            }

            this.cancelcheckout();
            let itemindex=this.antibodylist.findIndex(x=>x.name==name)
            let selectitem;
            if(itemindex!=-1 && this.isSelect==true){
              selectitem=this.antibodylist[itemindex];
              this.itemdetailservice.loadItemDetailByItemDbid(selectitem.dbid).subscribe(itemdetails=>{
                let newitemdetail;
                if(itemdetails.length>0){
                  newitemdetail=itemdetail.newitemdetail(itemdetails[itemdetails.length-1])
                }
                else{
                  newitemdetail=new itemdetail(-1, selectitem.dbid, selectitem.itemcategory, selectitem.name, selectitem.type, selectitem.supplier, '', '', new Date(), new Date(), null, '', '', '', '','',     '','','','',null,'','','','','','','','','',       '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', new Date(), '', '', '', '', '', '', '', '', '', 'No', new Date(),this.currentuser.name );
                }
                let neweggtransfer = new Eggtransfer(-1, chickenid, eggdbid.toString(), 'Purify Antibody', useamount, 'Itemdetail', '', this.currentuser.name, new Date() );
                this.opennewitemdetail.emit({eggtransfer:neweggtransfer, itemdetail:newitemdetail})
                //this.opennewitemdetail.emit(newitemdetail)
              }, error => {
                ErrorUtil.handleHttpError( error );
                this.errorService.message$ = error.error;
              });


            }
            else if(itemindex==-1 && this.isSelect==false){
              let createnewitem:item;
              createnewitem=new item(-1, '', '','', name, 'Antibody', true, '', 'Somru BioScience Inc.', 'Somru BioScience Inc.','', '','','', '', '', new Date(),this.currentuser.name, '');
              let neweggtransfer = new Eggtransfer(-1, chickenid, eggdbid.toString(), 'Purify Antibody', useamount, 'Item', '', this.currentuser.name, new Date() );
              this.opennewitem.emit({eggtransfer:neweggtransfer, item:createnewitem})
              //this.opennewitem.emit(createnewitem)
            }




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

  cancelcheckout(){
    this.resetcheck();
    this.opencheckmodal.close();
  }

  changestatus(chickenindex){
    let status=this.displayChickens[chickenindex].chick.chickenstatus;

    if(status && status=='Active'){
      this.displayChickens[chickenindex].chick.chickenstatus='Sacrificed';
    }
    else if(status && status=='Sacrificed'){
      this.displayChickens[chickenindex].chick.chickenstatus='Active';
    }

    this.chickenService.updateChickenInfo(this.displayChickens[chickenindex].chick).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  updateChickenLatestTiter(event:Chicken){
    let chickenindex = this.displayChickens.findIndex(x => x.chick.chickenid == event.chickenid);
    if (chickenindex != -1) {
      this.displayChickens[chickenindex].chick=event;
    }
  }



  rightclick(event:any, egg:Egg){
    event.preventDefault();
    document.getElementById("rightclickbutton").style.left=(event.clientX+20)+"px";
    document.getElementById("rightclickbutton").style.top=event.clientY+"px";
    this.displayEgg=egg;
  }

  hideAndDelete(){
    document.getElementById("rightclickbutton").style.left="-200px";
    document.getElementById("rightclickbutton").style.top="-200px";
    this.deleteEgg();
  }

  hiderightclick(){
    document.getElementById("rightclickbutton").style.left="-200px";
    document.getElementById("rightclickbutton").style.top="-200px";
  }

  updatechickenimmune(chicken:Chicken){
    let chickenindex=this.displayChickens.findIndex(x=>x.chick.dbid==chicken.dbid)
    if(chickenindex!=-1){
      this.displayChickens[chickenindex].chick=chicken;
    }
  }



  getfile(event) {
    if (event.target.files && event.target.files[0]) {
      this.importfile = event.target.files[0];
    }
  }

  import() {
    let that = this;
    this.isLoading = true;
    var reader = new FileReader();
    var filedata;
    var workbook;
    var XL_row_object;
    var self = this;
    let allpromises=[];

    reader.onload = function (event) {
      var data = reader.result;
      workbook = XLSX.read(data, { type: 'binary' });
      let sheetnumber = workbook.SheetNames.length;
      let allchickens=[];
      self.chickenService.loadChicken().subscribe(chickens=>{
        allchickens =  chickens.slice();
      }, error => {
        ErrorUtil.handleHttpError( error );
        that.errorService.message$ = error.error;
      });



      let sheetindex=0;
      self.readeachsheet(sheetindex, workbook, sheetnumber);

    }

    reader.readAsBinaryString(this.importfile);

  }

  readeachsheet(sheetindex:number, workbook:any, sheetnumber:number){
    if(sheetindex<sheetnumber){
      let XL_row_object;
      let sheetname= workbook.SheetNames[sheetindex];
      XL_row_object = XLSX.utils.sheet_to_json(workbook.Sheets[sheetname]);
      let rownumber=XL_row_object.length;

      let rowindex=0;
      let chicken;
      let chickenid=sheetname.substr(0,sheetname.indexOf(' '));
      let idarray=chickenid.split('-');
      let sequence= parseFloat(idarray[0]+'.'+idarray[1]) ;
      let immunogen=sheetname.substr(sheetname.indexOf(' ')+1);
      chicken =  new Chicken(-1, chickenid, null, immunogen,'', '', '0','0', '0', '', null, this.currentuser.name, new Date(),sequence, 'Active' );
      let injections=[];
      let promises=[];

      let inject=new Injection(-1, chickenid, 'Freund’s Adjuvant, Complete', null, '0', '400', 'µg','1:1', 'Not Complete', this.currentuser.name, new Date());
      injections.push(inject);
      inject=new Injection(-1, chickenid, 'Freund’s Adjuvant, Incomplete', null, '14', '200', 'µg','', 'Not Complete', this.currentuser.name, new Date());
      injections.push(inject);
      inject=new Injection(-1, chickenid, 'Freund’s Adjuvant, Incomplete', null, '28', '200', 'µg','', 'Not Complete', this.currentuser.name, new Date());
      injections.push(inject);
      inject=new Injection(-1, chickenid, 'Freund’s Adjuvant, Incomplete', null, '42', '200', 'µg','', 'Not Complete', this.currentuser.name, new Date());
      injections.push(inject);
      inject=new Injection(-1, chickenid, 'Freund’s Adjuvant, Incomplete', null, '56', '200', 'µg','', 'Not Complete', this.currentuser.name, new Date());
      injections.push(inject);

      this.chickenService.addChicken(chicken).then(_=>{
        chicken.dbid=_['dbid'];
        injections.forEach(injection=>{
          promises.push(this.injectionService.addInjection(injection))

        })

        Promise.all(promises).then(()=>{
          this.readeachrow(rowindex, XL_row_object,rownumber, chicken);

          this.readeachsheet(sheetindex+1, workbook, sheetnumber);
        });




      })

    }
    else{
      setTimeout(()=>{
        this.page = 1;
        this.chickenService.getCount().subscribe(c=>{
          this.chickenCount = c;
          this.loadChickenPage();
        })

      }, 2000);
    }

  }




  readeachrow(index: number, XL_row_object: any, rownumber:number, chicken:Chicken) {
    if (index < rownumber) {
      let newegg = new Egg(-1,chicken.chickenid,chicken.immunogen,  '',  null, null, '', null, null, '', '', '', null, '', '',  this.currentuser.name, new Date())

      if (XL_row_object[index]['Egg/Yolk']) { newegg.eggpart = XL_row_object[index]['Egg/Yolk'].trim() };
      if (XL_row_object[index]['Entered by']) { newegg.editby = XL_row_object[index]['Entered by'].trim() }
      if (XL_row_object[index]['Location']) { newegg.location = XL_row_object[index]['Location'].trim()}
      if (XL_row_object[index]['Amount in Stock']) {
        newegg.amount = XL_row_object[index]['Amount in Stock'].trim();
        chicken.totalegg=(Number(chicken.totalegg)+Number(newegg.amount)).toString();

      }
      if (XL_row_object[index]['Days after Immunization']) { newegg.daysafterimmune = XL_row_object[index]['Days after Immunization'].trim()}
      if (XL_row_object[index]['Frozen Date']) { newegg.frozendate = new Date(XL_row_object[index]['Frozen Date']) }
      if (XL_row_object[index]['Add Sucrose']) { newegg.addsucrose = XL_row_object[index]['Add Sucrose'] }
      if (XL_row_object[index]['Egg Laying Dates']) {
        let layeggdates = XL_row_object[index]['Egg Laying Dates'].trim();
        let dates= layeggdates.split('-');
        newegg.firstlayeggdate=new Date(dates[0]);
        newegg.lastlayeggdate=new Date(dates[1]);
        newegg.collectiondate=newegg.lastlayeggdate;
      };

      //console.log(newegg);

      this.eggService.addEgg(newegg).then(result=>{
        this.readeachrow(index+1, XL_row_object, rownumber, chicken);
      }).catch( error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
    else{
      if(Number(chicken.totalegg)>0){
        this.chickenService.updateChickenInfo(chicken).then(result=>{
          return;
        }).catch( error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
      }
      else{
        return;
      }
    }
  }





}

