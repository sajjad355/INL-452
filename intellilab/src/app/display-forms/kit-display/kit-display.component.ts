import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { KitComponentV2, KitV2 } from 'app/shared/objects2/KitV2';
import { Review } from 'app/shared/objects2/Review';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
import { SalesItemService2 } from 'app/shared/services2/SalesItem2.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { item } from '../../shared/objects/item';
import { UserV2 } from '../../shared/objects2/UserV2';
import { SettingService2 } from '../../shared/services2/Setting2.service';

@Component({
  selector: 'app-kit-display',
  templateUrl: './kit-display.component.html',
  styleUrls: ['./kit-display.component.scss']
})
export class KitDisplayComponent implements OnInit {
  @Input() kit: KitV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;
  @Input() close: () => void;

  @Output() saveKit = new EventEmitter();

  kitMethod: string[]
  kitStatus: string[]
  vialDescription: string[]
  unitList: string[];
  clients: string[];
  kitSize: string[];
  inputWarning: string[] = [];
  hideWarning = true;
  currentUser: UserV2;
  canReviewKits : boolean = false;
  OPERATION_NAMES = OPERATION_NAMES;
  dateFormatDisplay: string;

  constructor(
    private authenticationservice: AuthenticationService2,
    private settingService: SettingService2,
    private saleService: SalesItemService2
  ) { }

  ngOnInit() {
    this.loadSetting()
    this.setCurrentUser()
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.isNew) {
      this.enableEdit = changes.isNew.currentValue
    }
  }

  loadSetting() {
    this.clients = this.settingService.getSettingValuesAsArray( 'partnerClients' );
    this.unitList = [];
    let volUnit : string[] = this.settingService.getSettingValuesAsArray(  'volumeUnit' );
    volUnit.forEach(v => { this.unitList.push(v) });
    let massUnit : string[] = this.settingService.getSettingValuesAsArray( 'massUnit' );
    massUnit.forEach(m => { this.unitList.push(m) });
    let otherUnit : string[] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    otherUnit.forEach(o => { this.unitList.push(o) });
    this.kitStatus = this.settingService.getSettingValuesAsArray( 'kitStatus' );
    this.kitMethod = this.settingService.getSettingValuesAsArray( 'kitMethod' );
    this.vialDescription  = this.settingService.getSettingValuesAsArray( 'kitVialList' );
    this.kitSize = this.settingService.getSettingValuesAsArray( 'kitPackSize' );
  }

  async setCurrentUser() {
    this.currentUser = await this.authenticationservice.getCurrentUser()
    this.canReviewKits = UserV2.isAllowedToPerform(this.currentUser, this.OPERATION_NAMES.REVIEW_KITS);
  }

  removeKitComponent(index: number) {
    if (index < this.kit.kitComponents.length - 1) {
      this.kit.kitComponents.splice( index, 1);
    }
  }


  addNewComponent() {
    if ( !this.kit.catalogNumber ) {
      this.inputWarning.push( 'Please add kit catalog number before adding any components!');
      this.hideWarning = false;
      setTimeout(() => this.hideWarning = true, 5000);
      return;
    }
    let component : KitComponentV2 = new KitComponentV2();
    component.name = '';
    component.catalogNumber = this.kit.catalogNumber + '-' + (this.kit.kitComponents.length + 1);
    component.modifiedOn = new Date();
    component.editedBy = this.currentUser.name;
    this.kit.kitComponents.push( component );
  }

  setKitMethod(method:string){
    if(method.toUpperCase().includes('SELECT')){
      this.kit.method='';
    }
    else{
      this.kit.method=method;
    }
  }

  setKitClient(client:string){
    if(client.toUpperCase().includes('SELECT')){
      this.kit.client ='';
    }
    else{
      this.kit.client = client;
    }
  }

  setKitStatus(status:string){
    if(status.toUpperCase().includes('SELECT')){
      this.kit.status='';
    }
    else{
      this.kit.status = status;
    }
  }

  setKitComponentUnit( unit : string , i : number ) {
    if(unit.toUpperCase().includes('SELECT')){
      this.kit.kitComponents[i].unit = '';
    }
    else {
      this.kit.kitComponents[i].unit = unit;
    }
  }


  validateUserInput() {

    this.saleService.countCat(this.kit.catalogNumber).subscribe(res => {
      this.hideWarning = true;
      this.inputWarning = [];

      //test for miss informatin for catalog number, name, method, kitsize, kit staus, kit price and at least one kit component
      if (!this.kit.catalogNumber) {
        this.inputWarning.push('Catalog number is required.');
      }
      if(this.kit.catalogNumber && this.kit.catalogNumber.trim() !== '' && res > 0){
        this.inputWarning.push('Catalog number already exists, please choose a different one.');
      }
      if (!this.kit.name) {
        this.inputWarning.push('Kit name is required.');
      }
      if (!this.kit.method) {
        this.inputWarning.push('Kit method is required.');
      }
      if (!this.kit.packSize) {
        this.inputWarning.push('Kit pack size is required.');
      }
      if (!this.kit.status) {
        this.inputWarning.push('Kit status is required.');
      }
      if (!this.kit.unitPrice) {
        this.inputWarning.push('Kit price is required.');
      }

      this.kit.kitComponents.forEach( kc => {
        if ( (!kc.name ) && !this.inputWarning.includes( 'Kit Component Name is required') )
          this.inputWarning.push( 'Kit Component Name is required');
        if ( (!kc.catalogNumber ) && !this.inputWarning.includes( 'Kit Component Catalog # is required') )
          this.inputWarning.push( 'Kit Component Catalog # is required');
        if ( (!kc.reagent ) && !this.inputWarning.includes( 'Kit Component Reagent is required') )
          this.inputWarning.push( 'Kit Component Reagent is required');
        if ( (!kc.amount ) && !this.inputWarning.includes( 'Kit Component amount is required') )
          this.inputWarning.push( 'Kit Component amount is required');
        if ( (!kc.unit ) && !this.inputWarning.includes( 'Kit Component unit is required') )
          this.inputWarning.push( 'Kit Component unit is required');
        if ( (!kc.unit ) && !this.inputWarning.includes( 'Kit Component packaging is required') )
          this.inputWarning.push( 'Kit Component packaging is required');
      });

      if ( this.inputWarning.length > 0 ) {
        this.hideWarning = false;
        setTimeout(() => this.hideWarning = true, 5000);
        return;
      }
      else {
        this.save();
      }
    })
  }

  updateReview( review : Review   ) {
    this.kit.editStatus = review.status;
    this.kit.reviewedBy = review.reviewedBy;
    this.kit.editStatusComment = review.reviewComment;
    this.enableEdit = false;
    this.inputWarning = [];
    this.saveKit.emit(this.kit);
  }

  // event handler called from item search
  setSelectedItem( selectedItem : item, itemIndex : number ) {
    if (itemIndex == undefined || itemIndex < 0 || itemIndex >= this.kit.kitComponents.length) return;
    if (this.kit.kitComponents[itemIndex] == undefined) return;
    this.kit.kitComponents[itemIndex].reagent = selectedItem.name;
  }

  //save whole kit information include kit components
  save() {
    this.kit.modifiedOn = new Date();
    this.kit.editedBy = this.currentUser.name;
    this.enableEdit = false;
    this.saveKit.emit( this.kit );
  }
}
