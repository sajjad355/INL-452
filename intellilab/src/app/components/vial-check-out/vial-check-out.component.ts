import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { UserV2 } from '../../shared/objects2/UserV2';
import { InventoryV2, InventoryDetailV2, InventoryDetailVialV2 } from '../../shared/objects2/InventoryV2';
import { CHECKOUT_TYPE, VIAL_DETAIL_STATUS } from '../../shared/models/Constants';
import { DatePipe } from '@angular/common';

@Component({
  providers: [DatePipe],
  selector: 'app-vial-check-out',
  templateUrl: './vial-check-out.component.html',
  styleUrls: ['./vial-check-out.component.css']
})
export class VialCheckOutComponent implements OnInit {

  @Input() checkoutInventory: InventoryV2;
  @Input() checkoutInventoryDetailId: number;
  @Input() checkoutInventoryDetailVialId: number;

  @Output() update = new EventEmitter();
  @Output() cancel = new EventEmitter();

  displayInventory : InventoryV2;
  displayInventoryDetail : InventoryDetailV2;
  displayInventoryDetailVial : InventoryDetailVialV2;
  locations: LocationV2[] = [];
  subLocations : LocationV2[] = [];
  currentUser:UserV2;
  requestedCheckoutAmount : number = 1;
  CHECKOUT_TYPE = CHECKOUT_TYPE;
  VIAL_DETAIL_STATUS = VIAL_DETAIL_STATUS;
  checkoutLocation : LocationV2 = undefined;
  checkoutSublocation : LocationV2 = undefined;
  inputWarning : string[] = [];
  currentCheckoutPurpose : string = '';
  checkoutType : string = '';
  dateFormatDisplay: string = '';

  constructor(public datepipe: DatePipe,
              private locationService: LocationService2,
              private settingService : SettingService2,
              private authService: AuthenticationService2) { }

  ngOnInit() {
    let getCurrentUser = this.authService.getCurrentUser()
    if (getCurrentUser !== undefined) {
      getCurrentUser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
    this.loadSetting();
    this.displayInventory =  InventoryV2.copy(this.checkoutInventory);
    this.displayInventoryDetail = this.displayInventory.inventoryDetails.find( x => x.inventoryDetailId == this.checkoutInventoryDetailId );
    if ( this.displayInventoryDetail ) {
      this.displayInventoryDetailVial = this.displayInventoryDetail.inventoryDetailVials.find( x => x.inventoryDetailVialId == this.checkoutInventoryDetailVialId);
    }
  }

  loadSetting() {
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay )
    this.locations = this.locationService.getAllLocations();
  }

  validateCheckoutAmt( checkoutAmt : number ) {
    if ( !(this.displayInventoryDetailVial.volume ) || (checkoutAmt > this.displayInventoryDetailVial.volume) ) {
      this.inputWarning.push("Entered amount exceeds available amount.");
    }
    if ( checkoutAmt == 0 ) {
      this.inputWarning.push("Entered amount must be greater than 0.");
    }

  }

  setCheckoutAmount(checkoutAmt : number ) {
    this.requestedCheckoutAmount = checkoutAmt;
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
    this.validateCheckoutAmt( this.requestedCheckoutAmount );
    if ( this.checkoutType == CHECKOUT_TYPE.UNOPENED ) {
      if ( !this.checkoutLocation ) {
        this.inputWarning.push("Location is required.");
      }
    }
    if ( !this.currentCheckoutPurpose ) {
      this.inputWarning.push("Current checkout purpose is required.");
    }
    if ( this.inputWarning.length > 0 ) {
      setTimeout(_ => { this.inputWarning = []; }, 5000);
    }
    else {
      if ( this.checkoutType == CHECKOUT_TYPE.UNOPENED) {
        this.displayInventoryDetailVial.volume -= this.requestedCheckoutAmount;
        // amount being the # of vials which needs to be incremented as we are adding one
        this.displayInventoryDetail.amount += 1;
        let vialDetail : InventoryDetailVialV2 = new InventoryDetailVialV2();
        vialDetail.inventoryDetailVialId = -1;
        vialDetail.editedBy = this.currentUser.name;
        vialDetail.modifyOn = new Date();
        vialDetail.status =  VIAL_DETAIL_STATUS.UNOPENED;
        vialDetail.volume = this.requestedCheckoutAmount;
        vialDetail.unit = this.displayInventoryDetailVial.unit;
        let vialNumberText : string = '' + (this.displayInventoryDetail.inventoryDetailVials.length + 1);
        if ( this.displayInventoryDetail.amount ) {
          vialNumberText += '/' + this.displayInventoryDetail.amount;
        }
        vialDetail.vialNumber = vialNumberText;
        vialDetail.location = this.checkoutLocation;
        if ( this.checkoutSublocation ) {
          vialDetail.subLocation = this.checkoutSublocation;
        }
        this.updateCheckoutPurpose( vialDetail );
        this.displayInventoryDetail.inventoryDetailVials.push( vialDetail );
        this.renumberVialDetails();
        this.displayInventory.editedBy = this.currentUser.name;
        this.displayInventory.modifiedOn = new Date();
        this.update.emit( this.displayInventory );
      }
      else if ( this.checkoutType ==  CHECKOUT_TYPE.INUSE) {
        this.displayInventoryDetailVial.volume -= this.requestedCheckoutAmount;
        this.displayInventoryDetailVial.modifyOn = new Date();
        this.displayInventoryDetailVial.editedBy = this.currentUser.name;
        this.updateCheckoutPurpose( undefined ); // no new inventory to be created in this scenario
        this.displayInventory.editedBy = this.currentUser.name;
        this.displayInventory.modifiedOn = new Date();
        let tempData: any = this.displayInventory
        tempData.vialCheckOut = this.requestedCheckoutAmount
        tempData.vialId = this.checkoutInventoryDetailId
        console.log(`Senter's inventory detail ID: ${this.checkoutInventoryDetailId}`)
        this.update.emit( tempData );
      }
    }

  }

  renumberVialDetails() {
    let vialNumberTextSuffix : string = '/' + this.displayInventoryDetail.amount;
    for ( let i = 0 ; i < this.displayInventoryDetail.inventoryDetailVials.length ; i++ ) {
      let vialNumberText = '' + (i + 1) +  vialNumberTextSuffix;
      this.displayInventoryDetail.inventoryDetailVials[i].vialNumber = vialNumberText;
    }
  }

  cancelCheckout() {
    this.cancel.emit();
  }

  // set checkout purpose of inventory that will be created as a result of checkout to the current checkout purpose
  // value. For the existing inventory that is being checked out, add to the checkout purpose with the current reason
  updateCheckoutPurpose( newInventoryDetailVial : InventoryDetailVialV2) {
    let today = new Date();
    let cdate : string = this.datepipe.transform(today, this.dateFormatDisplay);
    let cp = this.currentCheckoutPurpose + " (User: " + this.currentUser.name +
                                            ", Checkout Date: " + cdate +
                                            ", Checkout Type: " + this.checkoutType +
                                            ", Checkout Amount: " + this.requestedCheckoutAmount +
                                            ")" + '\n';
    if ( newInventoryDetailVial ) newInventoryDetailVial.checkoutPurpose = cp;
    if ( this.displayInventoryDetailVial.checkoutPurpose )
      this.displayInventoryDetailVial.checkoutPurpose += cp;
    else
      this.displayInventoryDetailVial.checkoutPurpose = cp;
  }

}
