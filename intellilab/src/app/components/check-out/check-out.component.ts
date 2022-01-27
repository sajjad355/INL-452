import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges, ElementRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { InventoryService2 } from '../../shared/services2/Inventory2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import { InventoryV2, InventoryDetailV2 } from '../../shared/objects2/InventoryV2';
import { DatePipe } from '@angular/common';
import { CHECKOUT_TYPE, CHECKOUT_PURPOSE  } from '../../shared/models/Constants';
import { ErrorService } from '../../page/error/error.service';


@Component({
  providers: [DatePipe],
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.css']
})
export class CheckOutComponent implements OnInit, OnChanges {
  
  @Input() checkoutInventory: InventoryV2;
  @Input() checkoutInventoryDetailId: number;
  @Output() update = new EventEmitter();
  @Output() cancel = new EventEmitter();

  @ViewChild('itemReservedConfirmation', {static: false}) itemReservedConfirmation: ElementRef;
  itemReservedModal : any;

  displayInventory : InventoryV2;
  displayInventoryDetail : InventoryDetailV2;
  unit: String[];
  volUnit: String[];
  massUnit: String[];
  otherUnit: String[];
  conunit: String[];
  locations: LocationV2[] = [];
  subLocations : LocationV2[] = [];
  dateFormatDisplay: string;
  currentUser:UserV2;
  checkoutType : string = '';
  checkoutPurpose : string = '';
  requestedCheckoutAmount : number = 0;
  checkoutLocation : LocationV2 = undefined;
  checkoutSublocation : LocationV2 = undefined;
  currentCheckoutPurposeReason : string = '';
  CHECKOUT_TYPE = CHECKOUT_TYPE;
  CHECKOUT_PURPOSE = CHECKOUT_PURPOSE; 
  overrideReserved : boolean = false;
   
  inputWarning : string[] = [];
   

  constructor(public datepipe: DatePipe,
              private settingService: SettingService2,
              private locationService: LocationService2,
              private authService: AuthenticationService2,
              private inventoryService: InventoryService2,
              private modalService: NgbModal,
              private errorService: ErrorService) { }
  
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
    if (change.checkoutInventory && this.checkoutInventory !== undefined) {
      this.reset();
    }
  }

  reset() {
    this.displayInventory =  InventoryV2.copy(this.checkoutInventory);
    this.displayInventoryDetail = this.displayInventory.inventoryDetails.find( x => x.inventoryDetailId == this.checkoutInventoryDetailId );
    this.overrideReserved = false; 
  }


  loadSetting() {
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay )
    this.unit = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    this.massUnit = this.settingService.getSettingValuesAsArray(  'massUnit' );
    this.massUnit.forEach(m => { this.unit.push(m) })
    this.otherUnit = this.settingService.getSettingValuesAsArray( 'otherUnit' );
    this.otherUnit.forEach(o => { this.unit.push(o) })
    this.conunit = this.settingService.getSettingValuesAsArray( 'concentrationUnit' );
    this.locations = this.locationService.getAllLocations();
    
  }

  

  validate() {
    this.inputWarning = [];

    if ( !this.overrideReserved && this.displayInventoryDetail.reserve ) {
      this.itemReservedModal = this.modalService.open(this.itemReservedConfirmation, { backdrop: "static", size: 'lg' });
      return false;
    }

    if ( !this.currentCheckoutPurposeReason ) {
      this.inputWarning.push("Current checkout purpose is required.");
    } 
    
    if ( this.requestedCheckoutAmount <= 0 ) {
      this.inputWarning.push("Check out amount must be greater than 0");
    }

    if ( this.checkoutType ==  CHECKOUT_TYPE.UNOPENED) {
      if ( !(this.displayInventoryDetail.amount ) || (this.requestedCheckoutAmount > this.displayInventoryDetail.amount) ) {
        this.inputWarning.push("Entered amount exceeds available amount.");
      }
      if ( (this.checkoutPurpose == CHECKOUT_PURPOSE.MOVE) && (!this.checkoutLocation)) {
        this.inputWarning.push("Checkout location is required.");
      }
    }
    else if ( this.checkoutType ==  CHECKOUT_TYPE.INUSE) {
      if ( !(this.displayInventoryDetail.numberInUse) || (this.requestedCheckoutAmount > this.displayInventoryDetail.numberInUse) ) {
        this.inputWarning.push("Entered amount exceeds available amount.");
      }
    }
    

    if ( this.inputWarning.length > 0 ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
      return false;
    }
    else {
      return true;
    }

  }

  selectLocation( locationName : string ) {
    this.checkoutLocation = this.locations.find( x => x.locationName == locationName );
    if ( this.checkoutLocation ) {
      this.subLocations = this.checkoutLocation.subLocations;
    }
    else {
      this.subLocations = [];
    }
  }

  selectSubLocation( locationName : string ) {
    if ( this.subLocations ) {
       this.checkoutSublocation = this.subLocations.find( x => x.locationName == locationName );
    }
  }

  saveCheckout() {
    
    if ( !this.validate() ) {
      return;
    }
    
    let locationId : number = 0;
    if ( this.checkoutLocation && this.checkoutLocation.locationId ) locationId = this.checkoutLocation.locationId;
    let sublocationId : number = 0;
    if ( this.checkoutSublocation && this.checkoutSublocation.locationId) sublocationId = this.checkoutSublocation.locationId;

    
    this.inventoryService.checkout( 
        this.displayInventory,
        this.displayInventoryDetail.inventoryDetailId,
        this.checkoutType,        
        this.checkoutPurpose,         
        this.currentUser.name,        
        this.requestedCheckoutAmount, 
        locationId, 
        sublocationId, 
        this.getCheckoutPurpose()   
      ).then( savedItem => {
        this.update.emit();
    }).catch( err => {
      ErrorUtil.handleHttpError( err );
      this.errorService.message$ = err.error;
    });
    
  }

 
  cancelCheckout() {
    this.reset();
    this.cancel.emit();
  }

  

  getCheckoutPurpose() : string {
    let today = new Date();
    let cdate : string = this.datepipe.transform(today, this.dateFormatDisplay);
    let cp = this.currentCheckoutPurposeReason + " (User: " + this.currentUser.name + 
                                           ", Checkout Date: " + cdate + 
                                           ", Checkout Type: " + this.checkoutType + 
                                           ", Checkout Purpose: " + this.checkoutPurpose + 
                                           ", Checkout Amount: " + this.requestedCheckoutAmount +
                                           ")" + '\n';
    return cp; 
  }

  

  checkCanItemBeCheckedOut() {
    let hasQtyRemaining = this.displayInventoryDetail.amount > 0 || this.displayInventoryDetail.numberInUse > 0;
    return (hasQtyRemaining);
  }

  

}
