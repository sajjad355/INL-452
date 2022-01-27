import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { IAngularMyDpOptions } from 'angular-mydatepicker';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';
import { COMMERCIAL_TYPES, INVENTORY_ITEM_TYPES, IN_HOUSE_TYPES, ORDER_STATUS } from '../../shared/models/Constants';
import { ClientV2 } from '../../shared/objects2/ClientV2';
import { InventoryV2 } from '../../shared/objects2/InventoryV2';
import { ItemSourceV2 } from '../../shared/objects2/ItemSourceV2';
import { OrderItemV2 } from '../../shared/objects2/OrderItemV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';



@Component({
  selector: 'app-orderitem-display',
  templateUrl: './order-item-display.component.html',
  styleUrls: ['./order-item-display.component.css'],
})
export class OrderItemDisplayComponent implements OnInit, OnChanges {

  @Input() orderItem: OrderItemV2;
  @Input() editingEnabled: boolean;
  @Output() saveOrder = new EventEmitter();
  @Output() closeOrder = new EventEmitter();

  reserveCheck: boolean = false;
  displayOrderItem : OrderItemV2;
  unit: string[]= [];
  projectNumbers: string[]= [];
  itemTypes: string[]= [];
  itemSubTypes : string[] = [];
  commercialSubTypes : string[] = [
    COMMERCIAL_TYPES.UNCONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.CONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.ENZYME,
    COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY,
    COMMERCIAL_TYPES.CONJUGATED_ANTIBODY
  ];
  inhouseProteinSubTypes : string[] = [
    IN_HOUSE_TYPES.CONJUGATED_PROTEIN,
    IN_HOUSE_TYPES.UNCONJUGATED_ANTIBODY,
    IN_HOUSE_TYPES.CONJUGATED_ANTIBODY
  ];
  itemCategories: string[]= [];
  today = new Date();
  etaOptions: IAngularMyDpOptions = {
    // todayBtnTxt: 'Today',
    dateFormat: 'ddmmmyyyy',
    firstDayOfWeek: 'mo',
    sunHighlight: true,
    inline: false,
    disableUntil: {year: this.today.getFullYear(), month: this.today.getMonth() + 1, day: this.today.getDate() - 1}
   };
  eta: { date: { year: number, month: number, day: number } };
  notReceivedOrderItems: OrderItemV2[];
  repeatOrderItems: OrderItemV2[]= [];
  currentUser : UserV2;
  dateFormat= '';
  dateFormatDisplay: string;
  itemNotInInventory : boolean = false;
  inputWarning: string[] = [];
  ORDER_STATUS = ORDER_STATUS;

  currencyOptions= ['USD', 'CAD', 'EURO', 'GBP'];
  currencySigns= ['$', '$', '€', '£'];

  constructor( private settingService: SettingService2,
               private errorService: ErrorService,
               private authenticationService: AuthenticationService2) { }


  ngOnInit() {
    this.authenticationService.getCurrentUser().then( currentUser => {
      this.currentUser = UserV2.copy(currentUser);
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.loadSetting();
    this.loadEta();

    this.commercialSubTypes = [
      COMMERCIAL_TYPES.UNCONJUGATED_PROTEIN,
      COMMERCIAL_TYPES.CONJUGATED_PROTEIN,
      COMMERCIAL_TYPES.ENZYME,
      COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY,
      COMMERCIAL_TYPES.CONJUGATED_ANTIBODY
    ];

    if(this.orderItem && this.orderItem.reserveForClient){
      this.reserveCheck = true
    }else{
      this.reserveCheck = false
    }
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.orderItem && this.orderItem !== undefined) {
      this.reset();
    }
  }

  reset() {
    this.displayOrderItem = OrderItemV2.copy( this.orderItem );
    this.populateItemSubType();
    this.inputWarning = [];
  }

  getCurrency(displayOrderItem: OrderItemV2) {
    if(displayOrderItem.unitPrice !== null) {
      if(displayOrderItem.currency !== null) return '(' + displayOrderItem.currency + ')';
      else return '(USD)';
    }
  }

  loadEta() {
    let r : Date;
    if (this.orderItem.eta) {
      r = new Date(this.orderItem.eta);
    } else {
      r = new Date();
    }
    this.eta = { date: { year: r.getFullYear(), month: r.getMonth() + 1, day: r.getDate() } };
  }

  loadSetting() {
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.etaOptions.dateFormat = this.dateFormat;
    // this.etaOptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.unit = [];
    const volUnit: string[] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unit.push(v); });
    const massUnit: string[] = this.settingService.getSettingValuesAsArray(  'massUnit' );
    massUnit.forEach(m => { this.unit.push(m); });
    const otherUnit: string[] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    otherUnit.forEach(o => { this.unit.push(o); });
    this.projectNumbers = this.settingService.getSettingValuesAsArray( 'ordersProjectList' );
    this.itemTypes = this.settingService.getSettingValuesAsArray( 'inventoryItemType' );
    this.itemCategories = this.settingService.getSettingValuesAsArray( 'inventoryCategory' );
  }

  validate() : boolean {
    if(this.reserveCheck && !this.displayOrderItem.reserveForClient){
      this.inputWarning.push('Client for reservation cannot be empty')
    }
    if (!this.displayOrderItem.name ) {
      this.inputWarning.push( "Name is required");
    }
    if (!this.displayOrderItem.catalogNumber) {
      this.inputWarning.push( "Manufacturer Catalog # is required");
    }
    if (!this.displayOrderItem.type) {
      this.inputWarning.push( "Type is required");
    }
    else {
      if ( (this.displayOrderItem.type == INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN) && (!this.displayOrderItem.subtype)) {
        this.inputWarning.push( "Sub Type is required when type is " + INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN);
      }
      else if ( (this.displayOrderItem.type == INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN) && (!this.displayOrderItem.subtype)) {
        this.inputWarning.push( "Sub Type is required when type is " + INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN);
      }
    }
    if (!this.displayOrderItem.supplier && !this.displayOrderItem.manufacturer) {
      this.inputWarning.push( "Either Supplier or Manufacturer is required");
    }
    if (!this.displayOrderItem.amount) {
      this.inputWarning.push( "Amount is required");
    }
    if (!this.displayOrderItem.unit) {
      this.inputWarning.push( "Unit is required");
    }
    if (!this.displayOrderItem.containerSize) {
      this.inputWarning.push( "Container Size is required");
    }
    // if (!this.displayOrderItem.unitPrice) {
    //   this.inputWarning.push( "Unit Price is required");
    // }
    if (!this.displayOrderItem.project) {
      this.inputWarning.push( "Project is required");
    }
    return this.inputWarning.length == 0;
  }


  save(){
    if ( !this.validate() ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
    }
    else {
      this.saveOrder.emit([this.displayOrderItem]);
      this.editingEnabled = false;
    }
  }



  changeUrgentStatus() {
    this.orderItem.urgent = !this.orderItem.urgent;
  }


  cancelUpdate() {
    this.closeOrder.emit();
  }

  changeETA(event : any, orderItem: OrderItemV2) {
    // const d = event.formatted;
    // if ( d == undefined ) { return; }
    // orderItem.eta = new Date(d);

    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      orderItem.eta = new Date(formatted);
    }
  }


  changeReserveStatus() {
    this.reserveCheck = !this.reserveCheck
    if(!this.reserveCheck){
      this.displayOrderItem.reserveForClient = null
    }
  }

  // event handler called from client search
  selectClientForOrder( selectedClient : ClientV2) {
    this.displayOrderItem.reserveForClient = selectedClient;
  }

  // event handler called when a supplier is selected
  selectSupplier(selectedSupplier: ItemSourceV2) {
    if(selectedSupplier) {
      this.displayOrderItem.supplier = selectedSupplier.name;
    }
    else {
      this.displayOrderItem.supplier = '';
    }
  }

  // event handler called when a manufacturer is selected
  selectManufacturer(selectedManufacturer: ItemSourceV2) {
    if(selectedManufacturer) {
      this.displayOrderItem.manufacturer = selectedManufacturer.name;
    }
    else {
      this.displayOrderItem.manufacturer = '';
    }
  }

  // event handler called from item search
  setSelectedItem( selectedItem : InventoryV2 ) {
    if (!this.displayOrderItem) return;
    this.displayOrderItem.catalogNumber = selectedItem.catalogNumber;
    this.displayOrderItem.name = selectedItem.name;
    this.displayOrderItem.supplierCatalogNumber = selectedItem.supplierCatalogNumber;
    this.displayOrderItem.supplier = selectedItem.supplier;
    this.displayOrderItem.manufacturer = selectedItem.manufacturer;
    this.displayOrderItem.containerSize = selectedItem.containerSize;
    this.displayOrderItem.unit = selectedItem.unit;
    this.displayOrderItem.category = selectedItem.category;
    this.displayOrderItem.type = selectedItem.type;
    this.onItemTypeChange(); // force item type event to fire to change item type and sub type
    if ( selectedItem.subtype) this.displayOrderItem.subtype = selectedItem.subtype;
  }

  onItemTypeChange() {
    if ( !this.displayOrderItem ) return;
    this.itemSubTypes.splice( 0, this.itemSubTypes.length );
    this.displayOrderItem.subtype = undefined;
    this.populateItemSubType();
  }

  populateItemSubType() {
    const currentItemType = this.displayOrderItem.type;
    if ( currentItemType == INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN ) {
      // add commercial sub types to list of displayed sub types
      this.itemSubTypes.push( ...this.commercialSubTypes );
    }
    else if ( currentItemType == INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN ) {
      this.itemSubTypes.push( ...this.inhouseProteinSubTypes );
    }
  }
}
