import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { item } from '../../shared/objects/item';
import { SopV2, SopComponentV2, SopLinkV2 } from 'app/shared/objects2/SopV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { SettingService2 } from '../../shared/services2/Setting2.service';
import { Review } from 'app/shared/objects2/Review';
import { UserRoleV2, UserV2 } from '../../shared/objects2/UserV2';
import { OPERATION_NAMES } from '../../shared/models/Constants';

@Component({
  selector: 'app-sop-display',
  templateUrl: './sop-display.component.html',
  styleUrls: ['./sop-display.component.scss']
})
export class SopDisplayComponent implements OnInit, OnChanges {
  @Input() sop: SopV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;

  @Output() updateSop = new EventEmitter();
  @Output() cancel = new EventEmitter();

  inputWarning: string[] = [];
  hideWarning = true;
  finditem = true;

  currentuser: UserV2;
  currentusername: string = '';
  currentUserRoles: UserRoleV2[] = [];
  unit: string[];
  OPERATION_NAMES = OPERATION_NAMES;
  canReviewSops : boolean = false;


  constructor(private authenticationservice: AuthenticationService2,
              private settingService: SettingService2) {
  }

  ngOnInit() {
    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
        this.currentusername = this.currentuser.name;
        this.currentUserRoles = this.currentuser.userRoles;
        this.canReviewSops = UserV2.isAllowedToPerform( this.currentuser, OPERATION_NAMES.REVIEW_PRODUCTS );
      });
    }
    this.loadSetting();
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.sop && this.sop !== undefined) {
      this.reset()
    }
  }

  loadSetting() {
    this.unit = [];
    let volUnit : string[] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unit.push(v) });
    let massUnit : string[] = this.settingService.getSettingValuesAsArray( 'massUnit' );
    massUnit.forEach(m => { this.unit.push(m) });
    let otherUnit : string[] = this.settingService.getSettingValuesAsArray(   'otherUnit' );
    otherUnit.forEach(o => { this.unit.push(o) });
  }

  addNewComponent() {
    let component : SopComponentV2  = new SopComponentV2();
    component.editedBy = this.currentuser.name;
    component.modifiedOn = new Date();
    component.itemName = '';
    this.sop.components.push( component );
  }

  addNewSopLink() {
    let sopLink : SopLinkV2 = new SopLinkV2();
    sopLink.editedBy = this.currentuser.name;
    sopLink.modifiedOn = new Date();
    sopLink.referencedSop = new SopV2();
    sopLink.referencedSop.name = '';
    this.sop.sopLinks.push( sopLink );
  }



  // event handler called from sop search
  setReferencedSop( selectedSop : SopV2, sopIndex : number ) {
    if (sopIndex == undefined || sopIndex < 0 || sopIndex >= this.sop.sopLinks.length) return;
    if (this.sop.sopLinks[sopIndex] == undefined) return;
    this.sop.sopLinks[sopIndex].referencedSop = selectedSop;
  }

  // event handler called from item search
  setSelectedItem( selectedItem : item, itemIndex : number ) {
    if (itemIndex == undefined || itemIndex < 0 || itemIndex >= this.sop.components.length) return;
    if (this.sop.components[itemIndex] == undefined) return;
    this.sop.components[itemIndex].itemName = selectedItem.name;
    this.sop.components[itemIndex].vendor = selectedItem.manufacturer;
    this.sop.components[itemIndex].catalogNumber = selectedItem.cat;
    this.sop.components[itemIndex].supplierCatalogNumber = selectedItem.suppliercat;
  }




  removeSopLink(index: number) {
    if (index < 0) return;
    if (this.sop.sopLinks[index] == undefined) return;
    this.sop.sopLinks.splice(index, 1);
  }

  removeComponent(index: number) {
    if (index < 0) return;
    if (this.sop.components[index] == undefined) return;
    this.sop.components.splice(index, 1);
  }

  reset() {
    this.isNew ? '' : this.enableEdit = false;
  }

  validateUserInput() {
    this.hideWarning = true;
    this.inputWarning = [];



    if ( !this.sop.sopIdentifier ) {
      this.inputWarning.push('SOP Id can not be empty.');
    }
    if ( !this.sop.name ) {
      this.inputWarning.push('SOP name can not be empty.');
    }
    if ( !this.sop.volume ) {
      this.inputWarning.push('SOP volume can not be empty or 0.');
    }
    if (!this.sop.unit ) {
      this.inputWarning.push('SOP unit can not be empty.');
    }
    if ( !this.sop.direction ) {
      this.inputWarning.push('SOP Direction can not be empty.');
    }
    if ( (this.sop.components.length) < 1 && (this.sop.sopLinks.length < 1) ) {
      this.inputWarning.push('An SOP should at least have 1 or more components or 1 or more referenced SOPS.');
    }
    this.sop.components.forEach(component => {
      if (!component.itemName )  {
        this.inputWarning.push('A component must have a name.');
      }
      if (!component.requiredQuantity ) {
        this.inputWarning.push('A component must have an amount.');
      }
      if (!component.requiredUnit) {
        this.inputWarning.push('A component must have a unit.');
      }
    });
    this.sop.sopLinks.forEach( sopLink => {
      if ( !sopLink.requiredQuantity ) {
        this.inputWarning.push('A referenced SOP must have an amount.');
      }
      if ( !sopLink.requiredUnit ) {
        this.inputWarning.push('A referenced SOP must have a unit.');
      }
      if ( !sopLink.referencedSop.name  ) {
        this.inputWarning.push('A referenced SOP must indicate the associated SOP.');
      }
    });

    if ( this.inputWarning.length > 0 ) {

      this.hideWarning = false;
      setTimeout(() => this.hideWarning = true, 5000);
      return;
    }
    else {

      this.save();
    }

  }

  setSopUnit(sopUnit: string) {
    if (sopUnit.toUpperCase().includes('PLEASE SELECT')) {
      this.sop.unit = '';
    }
    else {
      this.sop.unit = sopUnit;
    }
  }



  save() {
    this.sop.editedBy = this.currentusername;
    this.sop.modifiedOn = new Date();
    this.enableEdit = false;
    this.updateSop.emit( this.sop );
    this.reset();
  }

  reviewUpdated( review : Review   ) {
    this.sop.editStatus = review.status;
    this.sop.reviewedBy = review.reviewedBy;
    this.sop.editStatusComment = review.reviewComment;
    this.enableEdit = false;
    this.inputWarning = [];
    this.updateSop.emit(this.sop);
    this.reset();
  }


  cancelSop() {
    this.cancel.emit();
  }

  setRequiredUnit(sopLink: SopLinkV2, value: string) {
    sopLink.requiredUnit = Number(value);
  }
}
