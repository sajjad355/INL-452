import {
  Component, EventEmitter, Input, OnInit, Output
} from '@angular/core';
import { ApplicationV2, ProductV2 } from 'app/shared/objects2/ProductV2';
import { Review } from 'app/shared/objects2/Review';
import { SalesItemService2 } from 'app/shared/services2/SalesItem2.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { SettingService2 } from '../../shared/services2/Setting2.service';

@Component({
  selector: "app-product-display",
  templateUrl: "./product-display.component.html",
  styleUrls: ["./product-display.component.scss"],
})
export class ProductDisplayComponent implements OnInit {
  @Input() product: ProductV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;

  @Output() updateProduct = new EventEmitter();
  @Output() cancel = new EventEmitter();

  inputWarning: string[] = [];
  hideWarning = true;

  currentuser: UserV2;
  currentusername: string = "";


  reviewRejectComment: boolean = true;
  approveRejectComment: boolean = true;

  unit: String[];
  clonalities: string[];
  productTypes: string[];
  OPERATION_NAMES = OPERATION_NAMES;
  canReviewProducts : boolean = false;

  constructor(
    private authenticationservice: AuthenticationService2,
    private settingService : SettingService2,
    private saleService: SalesItemService2
  ) {}

  ngOnInit() {
    let getcurrentuser = this.authenticationservice.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then((_) => {
        this.currentuser = UserV2.copy(_);
        this.currentusername = this.currentuser.name;
        this.canReviewProducts = UserV2.isAllowedToPerform( this.currentuser, OPERATION_NAMES.REVIEW_PRODUCTS );
      });
    }
    this.loadSetting();
  }

  loadSetting() {
    this.unit = [];
    let volUnit : string[] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unit.push(v) });
    let massUnit : string[] = this.settingService.getSettingValuesAsArray( 'massUnit' );
    massUnit.forEach(m => { this.unit.push(m) });
    let otherUnit : string[] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    otherUnit.forEach(o => { this.unit.push(o) });
    this.clonalities =  this.settingService.getSettingValuesAsArray( 'cloneType' );
    this.productTypes = this.settingService.getSettingValuesAsArray( 'productType' );
  }

  addNewApplication() {
    let application : ApplicationV2 = new ApplicationV2();
    application.editedBy = this.currentuser.name;
    application.modifiedOn = new Date();
    this.product.applications.push(
      application
    );
    this.enableEdit = true;
  }

  removeApplication(index: number) {
    if (index < 0) return;
    if (this.product.applications[index] == undefined) return;

    this.product.applications.splice(index, 1);
  }

  validateuserinput() {

    this.inputWarning = [];
    this.saleService.countCat(this.product.catalogNumber).subscribe(res => {
      if ( !this.product.name || this.product.name.trim() == "" ) {
        this.inputWarning.push("Product name can not be empty.");
      }

      if ( !this.product.catalogNumber || this.product.catalogNumber.trim() == "" ) {
        this.inputWarning.push("Product cataglog number can not be empty.");
      }

      if( this.product.catalogNumber && this.product.catalogNumber.trim() !== '' && res > 0){
        this.inputWarning.push('Catalog number already exists, please choose a different one.');
      }

      if ( !this.product.unitSize ) {
        this.inputWarning.push("Product unit size can not be empty.");
      }

      if ( !this.product.unit || this.product.unit.trim() == "" ) {
        this.inputWarning.push("Product unit can not be empty.");
      }

      if ( !this.product.unitPrice ) {
        this.inputWarning.push("Product unit price can not be empty.");
      }

      if ( !this.product.type || this.product.type.trim() == "" ) {
        this.inputWarning.push("Product type can not be empty.");
      }

      if (this.inputWarning.length > 0 ) {
        this.hideWarning = false;
        setTimeout( () => {
           this.hideWarning = true;
        }, 5000);
      }
      else {
        this.setimmune();
        this.save();
      }
    })
  }

  setimmune() {
    let immunogen = document.getElementById("specialinput").innerHTML;

    if (immunogen != undefined && immunogen != null && immunogen.trim() != "") {
      immunogen = immunogen.replace(/<br>/g, "").trim();

      immunogen = immunogen.replace(/<span([^>]*)>/g, "<span>");

      immunogen = immunogen.replace(/<i([^>]*)>/g, "<i>");

      immunogen = immunogen.replace(/<sub([^>]*)>/g, "<sub>");

      immunogen = immunogen.replace(/<span><\/span>/g, "");
      immunogen = immunogen.replace(/<i><\/i>/g, "");
      immunogen = immunogen.replace(/<sub><\/sub>/g, "");
      immunogen = immunogen.replace(/<span>/g, "");
      immunogen = immunogen.replace(/<\/span>/g, "");

      this.product.immunogen = immunogen;
    }


  }

  save() {
    this.product.editedBy = this.currentusername;
    this.product.modifiedOn = new Date();
    this.product.applications.forEach( (aApplication) =>  {
      aApplication.editedBy = this.currentusername;
      aApplication.modifiedOn = new Date();
    });
    this.inputWarning = [];
    this.enableEdit = false;
    this.updateProduct.emit( this.product );

  }

  reviewUpdated( review : Review   ) {
    this.product.editStatus = review.status;
    this.product.reviewedBy = review.reviewedBy;
    this.product.editStatusComment = review.reviewComment;
    this.product.editStatusTime = new Date();
    this.enableEdit = false;
    this.inputWarning = [];
    this.updateProduct.emit(this.product);
  }


  cancelproduct() {
    this.product = undefined;
    this.cancel.emit();
  }
}
