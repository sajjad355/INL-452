import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { UserV2 } from '../../shared/objects2/UserV2';
import { INVENTORY_ITEM_TYPES, COMMERCIAL_TYPES, IN_HOUSE_TYPES, VIAL_DETAIL_STATUS, DRUG_LOT_TYPES, HAZARD_LEVELS } from '../../shared/models/Constants';
import { InventoryV2, InventoryDetailV2, InventoryDetailVialV2 } from '../../shared/objects2/InventoryV2';
import { UserService2 } from 'app/shared/services2/User2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { ClientV2 } from '../../shared/objects2/ClientV2';
import { ErrorService } from '../../page/error/error.service';


@Component({
  providers: [DatePipe],
  selector: 'app-item-detail-display',
  templateUrl: './item-detail-display.component.html',
  styleUrls: ['./item-detail-display.component.scss']
})

export class ItemDetailDisplayComponent implements OnInit, OnChanges {
  @Input() selectedItem: InventoryV2;
  @Input() selectedItemDetail: InventoryDetailV2;
  @Input() enableEdit: boolean;
  @Output() updateItem = new EventEmitter();
  @Output() cancel = new EventEmitter();
  @ViewChild('checkoutVialItem') checkoutVialItem: ElementRef;
  @ViewChild('vialCheckoutHistory') vialCheckoutHistory: ElementRef;
  @ViewChild('inputAmount') inputAmount: ElementRef;

  itemDetailModal: any;
  unit: string[] = [];
  volUnits : string[] = []
  itemCategory: string[];
  displayedItemDetail: InventoryDetailV2;
  inputWarning: string[] = [];

  itemTypes: string[] = [];
  drugTypes: string[] = [];
  currentUser: UserV2;
  INVENTORY_ITEM_TYPES = INVENTORY_ITEM_TYPES;
  COMMERCIAL_TYPES = COMMERCIAL_TYPES;
  IN_HOUSE_TYPES = IN_HOUSE_TYPES;
  VIAL_DETAIL_STATUS = VIAL_DETAIL_STATUS;
  clonalities : string[] = [];
  conjugationTypes : string[] = [];
  projectNumbers : string[] = [];
  concentrationUnits : string[] = [];
  commercialSubTypes = [
    COMMERCIAL_TYPES.UNCONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.CONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.ENZYME,
    COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY,
    COMMERCIAL_TYPES.CONJUGATED_ANTIBODY
  ];
  vialDetailStatusTypes = [
    VIAL_DETAIL_STATUS.INUSE,
    VIAL_DETAIL_STATUS.UNOPENED
  ];
  drugLotTypes = [
    DRUG_LOT_TYPES.MANUFACTURER_STOCK,
    DRUG_LOT_TYPES.INHOUSE_ALIQUOT
  ];
  hazardLevels = [
    HAZARD_LEVELS.UNKNOWN,
    HAZARD_LEVELS.LOW,
    HAZARD_LEVELS.MEDIUM,
    HAZARD_LEVELS.HIGH,
  ];

  users : UserV2[] = [];
  locations: LocationV2[] = [];
  subLocations: LocationV2[] = [];
  vialSubLocations : any[] = [];
  myDatePickerOptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  receiveDate : { date: { year: number, month: number, day: number } };
  expiryDate  : { date: { year: number, month: number, day: number } };
  retestDate  : { date: { year: number, month: number, day: number } };
  apPrepDate  : { date: { year: number, month: number, day: number } };
  dateFormat : string = '';
  dateFormatDisplay: string = '';

  checkedVialId : number = undefined;
  vialCheckoutPurpose : string = '';

  constructor(private modalService: NgbModal,
              private errorService: ErrorService,
              private settingService: SettingService2,
              private userService : UserService2,
              private locationService : LocationService2,
              private authenticationService: AuthenticationService2) {}

  ngOnInit() {
    let getcurrentuser = this.authenticationService.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
    this.loadSetting();
    this.userService.loadUsers().subscribe( result => {
      this.users = result;
    },  error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  ngOnChanges(change: SimpleChanges) {
    if (change.selectedItemDetail && this.selectedItemDetail !== undefined) {
      this.reset();
    }
  }

  loadSetting() {
    this.unit = [];
    this.volUnits = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    this.volUnits.forEach(v => { this.unit.push(v) });
    let massUnits : string [] = this.settingService.getSettingValuesAsArray(  'massUnit' );
    massUnits.forEach(m => { this.unit.push(m) });
    let specialUnits : string [] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    specialUnits.forEach(o => { this.unit.push(o) });
    this.itemTypes = this.settingService.getSettingValuesAsArray( 'inventoryItemType' );
    this.itemCategory = this.settingService.getSettingValuesAsArray( 'inventoryCategory' );
    this.drugTypes = this.settingService.getSettingValuesAsArray( 'drugType' );
    this.clonalities = this.settingService.getSettingValuesAsArray( 'cloneType' );
    this.conjugationTypes = this.settingService.getSettingValuesAsArray( 'conjugationType' );
    this.projectNumbers = this.settingService.getSettingValuesAsArray( 'ordersProjectList' );
    this.concentrationUnits = this.settingService.getSettingValuesAsArray( 'concentrationUnit' );
    this.locations = this.locationService.getAllLocations();
    this.dateFormat = this.settingService.getDateFormat(DateFormatType.DatePickerUsage);
    this.dateFormatDisplay = this.settingService.getDateFormat(DateFormatType.UserDisplay);
    this.myDatePickerOptions.dateFormat = this.dateFormat;
    // this.myDatePickerOptions.editableDateField = false;
  }

  setRecieveUser( receiveUser : UserV2 ) {
    this.displayedItemDetail.receiveUser = receiveUser;
  }

  showLocationSublocations(locationName: string) {
    this.subLocations = [];
    let target = this.locations.find(x => x.locationName == locationName);
    if (target == undefined) return;

    target.subLocations.forEach( aSubLocation =>{
      this.subLocations.push( aSubLocation );
    });
  }

  showVialLocationSublocations(locationName: string, index : number) {
    this.vialSubLocations[index] = [];
    let target = this.locations.find(x => x.locationName == locationName);
    if (target == undefined) return;
    target.subLocations.forEach( aSubLocation =>{
      this.vialSubLocations[index].push( aSubLocation );
    });
  }



  updateItemLocation( locationName : string ) {
    let target = this.locations.find(x => x.locationName == locationName);
    if (target == undefined) return;
    this.displayedItemDetail.location = target;
    this.displayedItemDetail.subLocation = undefined;
    this.showLocationSublocations( locationName );
  }

  updateItemSublocation( locationName : string ) {
    let target = this.subLocations.find(x => x.locationName == locationName);
    if (target == undefined) return;
    this.displayedItemDetail.subLocation = target;
  }

  updateVialLocation( index : number, locationName : string ) {
    let target = this.locations.find(x => x.locationName == locationName);
    if (target == undefined) return;
    this.displayedItemDetail.inventoryDetailVials[index].location = target;
    this.displayedItemDetail.inventoryDetailVials[index].subLocation = undefined;
    this.showVialLocationSublocations( locationName, index );
  }

  updateVialSublocation( index : number, locationName : string ) {
    let target = this.vialSubLocations[index].find(x => x.locationName == locationName);
    if (target == undefined) return;
    this.displayedItemDetail.inventoryDetailVials[index].subLocation = target;
  }



  checkInput() {
    switch(this.selectedItem.type) {

      case INVENTORY_ITEM_TYPES.MATRIX: {
         this.checkMatrixInput();
         break;
      }
      case INVENTORY_ITEM_TYPES.ANTI_SERA: {
        this.checkAntiSeraInput();
        break;
      }
      case INVENTORY_ITEM_TYPES.DRUG: {
        this.checkDrugInput();
        break;
      }
      case INVENTORY_ITEM_TYPES.CONSUMABLE: {
        this.checkConsumableInput();
        break;
      }
      case INVENTORY_ITEM_TYPES.CHEMICAL: {
        this.checkChemicalInput();
        break;
      }
      default: {
        this.genericInputCheck();
        break;
      }
    }

    if ( this.inputWarning.length > 0 ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
    }
    else {
      this.saveItemDetail();
    }
  }



  checkVialInput() {
    this.displayedItemDetail.inventoryDetailVials.forEach(vial => {
      let msg : string = this.inputWarning.find( x => x == 'Vial Volume is required' );
      if ( !msg && !vial.volume ) {
        this.inputWarning.push('Vial Volume is required');
      }
      msg = this.inputWarning.find( x => x == 'Vial Unit is required' );
      if ( !msg && !vial.unit ) {
        this.inputWarning.push('Vial Unit is required');
      }
      msg = this.inputWarning.find( x => x == 'Vial Status is required' );
      if ( !msg && !vial.status ) {
        this.inputWarning.push('Vial Status is required');
      }
    });
  }

  checkMatrixInput() {
    if ( !this.displayedItemDetail.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItemDetail.lotNumber ) {
      this.inputWarning.push('Lot # is required');
    }
    if ( isNaN(this.displayedItemDetail.amount) ) {
      this.inputWarning.push('Amount is required');
    }
    if ( !this.displayedItemDetail.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItemDetail.receivedDate ) {
      this.inputWarning.push('Receive date is required');
    }
     this.checkVialInput();
  }

  genericInputCheck() {
    if ( !this.displayedItemDetail.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItemDetail.lotNumber ) {
      this.inputWarning.push('Lot # is required');
    }
    if ( isNaN(this.displayedItemDetail.amount) ) {
      this.inputWarning.push('Amount is required');
    }
    if ( !this.displayedItemDetail.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItemDetail.location ) {
      this.inputWarning.push('Location is required');
    }
    if ( !this.displayedItemDetail.receivedDate ) {
      this.inputWarning.push('Receive date is required');
    }
  }



  checkAntiSeraInput() {
    if ( !this.displayedItemDetail.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItemDetail.lotNumber ) {
      this.inputWarning.push('Lot #/Bleed Date is required');
    }
    if ( isNaN(this.displayedItemDetail.amount) ) {
      this.inputWarning.push('Amount is required');
    }
    if ( !this.displayedItemDetail.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItemDetail.immunizationCycleDay ) {
      this.inputWarning.push('Immunization Cycle day is required');
    }
    if ( !this.displayedItemDetail.titer ) {
      this.inputWarning.push('Titer is required');
    }
    if ( !this.displayedItemDetail.titerAssay ) {
      this.inputWarning.push('Titer Assay is required');
    }
    if ( !this.displayedItemDetail.receivedDate ) {
      this.inputWarning.push('Receive date is required');
    }
    this.checkVialInput();
  }

  checkDrugInput() {
    if ( !this.displayedItemDetail.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItemDetail.lotNumber ) {
      this.inputWarning.push('Lot # is required');
    }
    if ( isNaN(this.displayedItemDetail.amount) ) {
      this.inputWarning.push('Quantity is required');
    }
    if ( !this.displayedItemDetail.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItemDetail.location ) {
      this.inputWarning.push('Location is required');
    }
    if ( !this.displayedItemDetail.receivedDate ) {
      this.inputWarning.push('Receive date is required');
    }
    if ( !this.displayedItemDetail.drugLotType ) {
      this.inputWarning.push('Drug Lot Type is required');
    }
    if ( !this.displayedItemDetail.containerSize ) {
      this.inputWarning.push('Amount/Container is required');
    }
  }

  checkConsumableInput() {
    if ( !this.displayedItemDetail.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItemDetail.lotNumber ) {
      this.inputWarning.push('Lot # is required');
    }
    if ( isNaN(this.displayedItemDetail.amount) ) {
      this.inputWarning.push('Amount is required');
    }
    if ( !this.displayedItemDetail.containerSize ) {
      this.inputWarning.push('Order Count is required');
    }
    if ( !this.displayedItemDetail.unit ) {
      this.inputWarning.push('Order Unit is required');
    }
    if ( !this.displayedItemDetail.location ) {
      this.inputWarning.push('Location is required');
    }
    if ( !this.displayedItemDetail.receivedDate ) {
      this.inputWarning.push('Receive date is required');
    }
  }

  checkChemicalInput() {
    this.genericInputCheck();
    if ( !this.displayedItemDetail.hazardLevel ) {
      this.inputWarning.push('Hazard Level is required');
    }
  }

  saveItemDetail() {
    // remove the detail element from the inventory item if it exists and then add regardless
    let index : number = this.selectedItem.inventoryDetails.findIndex( x => x.inventoryDetailId == this.displayedItemDetail.inventoryDetailId );
    if ( index != -1 ) {
      this.selectedItem.inventoryDetails.splice( index, 1 );
    }
    this.selectedItem.inventoryDetails.push( this.displayedItemDetail );
    this.selectedItem.editedBy = this.currentUser.name;
    this.selectedItem.modifiedOn = new Date();
    this.updateItem.emit( this.selectedItem );
  }

  reset() {
    this.displayedItemDetail = InventoryDetailV2.copy(this.selectedItemDetail);
    if(this.displayedItemDetail.location){
      this.showLocationSublocations(this.displayedItemDetail.location.locationName);
    }
    else {
      this.subLocations = [];
    }

    if ( !this.displayedItemDetail.receiveUser ) this.displayedItemDetail.receiveUser = this.currentUser;

    if ( this.displayedItemDetail.receivedDate ) {
      const c = new Date(this.displayedItemDetail.receivedDate);
      this.receiveDate = { date: { year: c.getFullYear(), month: c.getMonth() + 1, day: c.getDate() } };
    }
    else {
      this.receiveDate = undefined;
    }
    if ( this.displayedItemDetail.expiryDate ) {
      const e = new Date(this.displayedItemDetail.expiryDate);
      this.expiryDate = { date: { year: e.getFullYear(), month: e.getMonth() + 1, day: e.getDate() } };
    }
    else {
      this.expiryDate = undefined;
    }
    if ( this.displayedItemDetail.retestDate ) {
      const r = new Date(this.displayedItemDetail.retestDate);
      this.retestDate = { date: { year: r.getFullYear(), month: r.getMonth() + 1, day: r.getDate() } };
    }
    else {
      this.retestDate = undefined;
    }
    if ( this.displayedItemDetail.affinityPurificationPreparationDate ) {
      const a = new Date(this.displayedItemDetail.affinityPurificationPreparationDate);
      this.apPrepDate = { date: { year: a.getFullYear(), month: a.getMonth() + 1, day: a.getDate() } };
    }
    else {
      this.apPrepDate = undefined;
    }
    for ( let vialIndex = 0 ; vialIndex < this.displayedItemDetail.inventoryDetailVials.length; vialIndex++) {
      let vial : InventoryDetailVialV2 =  this.displayedItemDetail.inventoryDetailVials[vialIndex];
      if ( vial.location ) {
        this.showVialLocationSublocations(vial.location.locationName, vialIndex);
      }
    }
  }

  cancelItemDetail(){
    this.cancel.emit();
  }

  changeReceiveDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayedItemDetail.receivedDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayedItemDetail.receivedDate = new Date(formatted);
    }
  }

  changeExpiryDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayedItemDetail.expiryDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayedItemDetail.expiryDate = new Date(formatted);
    }
  }

  changeRetestDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayedItemDetail.retestDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayedItemDetail.retestDate = new Date(formatted);
    }
  }

  changeAPPrepDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.displayedItemDetail.affinityPurificationPreparationDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayedItemDetail.affinityPurificationPreparationDate = new Date(formatted);
    }
  }

  addVialDetail() {
    if ( !this.displayedItemDetail.amount  ) {
      this.inputWarning.push(  'Amount must be entered before adding any vials' );
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return;
    }
    if ( this.displayedItemDetail.inventoryDetailVials.length == this.displayedItemDetail.amount  ) {
      this.inputWarning.push(  'Addtiional vials cannot be added unless amount is increased' );
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return;
    }
    let vialDetail : InventoryDetailVialV2 = new InventoryDetailVialV2();
    vialDetail.inventoryDetailVialId = -1;
    vialDetail.editedBy = this.currentUser.name;
    vialDetail.modifyOn = new Date();
    vialDetail.status =  VIAL_DETAIL_STATUS.UNOPENED;
    let vialNumberText : string = '' + (this.displayedItemDetail.inventoryDetailVials.length + 1);
    if ( this.displayedItemDetail.amount ) {
      vialNumberText += '/' + this.displayedItemDetail.amount;
    }
    vialDetail.vialNumber = vialNumberText;
    let emptySubLocationList: LocationV2[] = [];
    this.vialSubLocations.push( emptySubLocationList );
    if ( this.displayedItemDetail.location ) {
      vialDetail.location = this.displayedItemDetail.location;
      let newVialIndex : number = this.displayedItemDetail.inventoryDetailVials.length;
      this.showVialLocationSublocations( vialDetail.location.locationName, newVialIndex );
    }
    if ( this.displayedItemDetail.subLocation ) {
      vialDetail.subLocation = this.displayedItemDetail.subLocation;
    }
    this.displayedItemDetail.inventoryDetailVials.push( vialDetail );
  }

  removeVialDetail(index : number) {
    if ( this.displayedItemDetail.inventoryDetailVials.length < 1 ) { return; }
    this.displayedItemDetail.inventoryDetailVials.splice( index, 1 );
    this.vialSubLocations.splice( index, 1 );
    this.renumberVialDetails();
  }

  updateAmount( amount : number) {
    if( amount < this.displayedItemDetail.inventoryDetailVials.length ) {
      this.inputAmount.nativeElement.value = this.displayedItemDetail.amount;
      this.inputWarning.push(  'Amount cannot be decreased to less than the # of existing vials' );
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return;
    }
    this.displayedItemDetail.amount = amount;
    this.renumberVialDetails();
  }

  renumberVialDetails() {
    let vialNumberTextSuffix : string = '/' + this.displayedItemDetail.amount;
    for ( let i = 0 ; i < this.displayedItemDetail.inventoryDetailVials.length ; i++ ) {
      let vialNumberText = '' + (i + 1) +  vialNumberTextSuffix;
      this.displayedItemDetail.inventoryDetailVials[i].vialNumber = vialNumberText;
    }
  }

  vialVolumeChanged( index : number, volume : number ) {
    this.displayedItemDetail.inventoryDetailVials[index].volume = volume;
  }

  vialVolumeUnitChanged( index : number, unit : string ) {
    this.displayedItemDetail.inventoryDetailVials[index].unit = unit;
  }

  vialStatusChanged( index : number, status : string ) {
    this.displayedItemDetail.inventoryDetailVials[index].status = status;
  }

  toggleVialForCheckout(vialId : number) {
    if ( vialId == this.checkedVialId )
      this.checkedVialId = undefined;
    else
      this.checkedVialId = vialId;
  }

  checkoutVials() {
    if ( this.checkedVialId == undefined ) {
      this.inputWarning.push(  'No vials selected for checkout' );
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return;
    }
    this.itemDetailModal = this.modalService.open(this.checkoutVialItem, { backdrop: 'static', size: 'sm' });
  }

  updateItemFromVialCheckout( item : any ) {
    this.updateItem.emit( item );
  }

  viewVialCheckoutHistory( index : number ) {
    this.vialCheckoutPurpose = this.displayedItemDetail.inventoryDetailVials[index].checkoutPurpose;
    this.itemDetailModal = this.modalService.open(this.vialCheckoutHistory, { backdrop: 'static', size: 'sm' });
  }

  // event handler called from client search
  selectClientForItem( selectedClient : ClientV2) {
    this.displayedItemDetail.reserveForClient = selectedClient;
  }

  toggleReserve() {
    if ( this.displayedItemDetail.reserve ) {
      this.displayedItemDetail.reserve = false;
      this.displayedItemDetail.reserveForClient = undefined;
    }
    else {
      this.displayedItemDetail.reserve = true;
    }
  }


}
