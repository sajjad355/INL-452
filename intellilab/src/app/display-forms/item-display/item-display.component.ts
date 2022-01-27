import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { SettingService2 } from '../../shared/services2/Setting2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { UserV2 } from '../../shared/objects2/UserV2';
import { ItemSourceV2 } from '../../shared/objects2/ItemSourceV2';
import { INVENTORY_ITEM_TYPES, COMMERCIAL_TYPES, IN_HOUSE_TYPES } from '../../shared/models/Constants';
import { InventoryV2 } from '../../shared/objects2/InventoryV2';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { FileElement } from 'app/file-management-system/file-explorer/model/file-element';
import { Subject, Subscription } from 'rxjs';


@Component({
  selector: 'app-item-display',
  templateUrl: './item-display.component.html',
  styleUrls: ['./item-display.component.scss']
})

export class ItemDisplayComponent implements OnInit, OnChanges {
  @Input() selectedItem: InventoryV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;


  @Output() updateItem = new EventEmitter();
  @Output() cancel = new EventEmitter();

  unit: string[];
  itemCategory: string[];
  displayedItem: InventoryV2;
  inputWarning: string[] = [];
  itemTypes: string[] = [];
  drugTypes: string[] = [];
  currentUser: UserV2;
  INVENTORY_ITEM_TYPES = INVENTORY_ITEM_TYPES;
  COMMERCIAL_TYPES = COMMERCIAL_TYPES;
  IN_HOUSE_TYPES = IN_HOUSE_TYPES;
  clonalities : string[] = [];
  conjugationTypes : string[] = [];
  commercialSubTypes = [
    COMMERCIAL_TYPES.UNCONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.CONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.ENZYME,
    COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY,
    COMMERCIAL_TYPES.CONJUGATED_ANTIBODY
  ];




  constructor(private settingService: SettingService2,
              private authenticationService: AuthenticationService2,
              public attService: AttachmentService) {}

  ngOnInit() {
    let getcurrentuser = this.authenticationService.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
    this.loadSetting();
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.selectedItem && this.selectedItem !== undefined) {
      this.reset();
    }
  }

  loadSetting() {
    this.unit = [];
    let volUnit : string [] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unit.push(v) });
    let massUnit : string [] = this.settingService.getSettingValuesAsArray(  'massUnit' );
    massUnit.forEach(m => { this.unit.push(m) });
    let specialUnits : string [] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    specialUnits.forEach(o => { this.unit.push(o) });
    this.itemTypes = this.settingService.getSettingValuesAsArray( 'inventoryItemType' );
    this.itemCategory = this.settingService.getSettingValuesAsArray( 'inventoryCategory' );
    this.drugTypes = this.settingService.getSettingValuesAsArray( 'drugType' );
    this.clonalities = this.settingService.getSettingValuesAsArray( 'cloneType' );
    this.conjugationTypes = this.settingService.getSettingValuesAsArray( 'conjugationType' );
  }

  
  // event handler called when a supplier is selected
  selectSupplier(selectedSupplier: ItemSourceV2) {
    if(selectedSupplier) {
      this.displayedItem.supplier = selectedSupplier.name;
      this.displayedItem.supplierLink = selectedSupplier.link;
    }
    else {
      this.displayedItem.supplier = '';
      this.displayedItem.supplierLink = '';    
    }
  }

  // event handler called when a manufacturer is selected
  selectManufacturer(selectedManufacturer: ItemSourceV2) {
    if(selectedManufacturer) {
      this.displayedItem.manufacturer = selectedManufacturer.name;
      this.displayedItem.manufacturerLink = selectedManufacturer.link;
    }
    else {
      this.displayedItem.manufacturer = '';
      this.displayedItem.manufacturerLink = '';
    }
  }

  checkInput() {
    switch(this.displayedItem.type) {
      case INVENTORY_ITEM_TYPES.MATRIX:
      case INVENTORY_ITEM_TYPES.SOLUTION: {
         this.checkSolutionOrMatrixInput();
         break;
      }
      case INVENTORY_ITEM_TYPES.CHEMICAL: {
        this.checkChemicalInput();
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
      case INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN: {
        if ( this.displayedItem.subtype == COMMERCIAL_TYPES.CONJUGATED_ANTIBODY ) {
          this.checkCommercialConjugatedAntibodyInput();
          break;
        }
        else if ( this.displayedItem.subtype == COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY ) {
          this.checkCommercialUnconjugatedAntibodyInput();
          break;
        }
        else if ( this.displayedItem.subtype == COMMERCIAL_TYPES.CONJUGATED_PROTEIN ) {
          this.checkCommercialConjugatedProteinInput();
          break;
        }
        else {
          this.checkCommercialUnconjugatedProteinAndEnzymeInput();
          break;
        }
      }
      case INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN: {
        if ( this.displayedItem.subtype == COMMERCIAL_TYPES.CONJUGATED_ANTIBODY ) {
          this.checkInhouseConjugatedAntibodyInput();
          break;
        }
        else if ( this.displayedItem.subtype == COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY ) {
          this.checkInhouseUnconjugatedAntibodyInput();
          break;
        }
        else {
          this.checkInhouseConjugatedProteinInput();
          break;
        }
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
      this.saveItem();
    }
  }

  checkSolutionOrMatrixInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
  }

  checkChemicalInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItem.chemicalAbstractsServiceNumber ) {
      this.inputWarning.push('CAS # is required');
    }
    if ( !this.displayedItem.molecularWeight ) {
      this.inputWarning.push('Molecular weight is required');
    }
  }

  checkAntiSeraInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItem.compoundName ) {
      this.inputWarning.push('Compund name is required');
    }
    if ( !this.displayedItem.tradeName ) {
      this.inputWarning.push('Trade name is required');
    }
  }

  checkDrugInput() {
    if ( !this.displayedItem.compoundName ) {
      this.inputWarning.push('Compound name is required');
    }
    if ( !this.displayedItem.tradeName ) {
      this.inputWarning.push('Trade name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItem.catalogNumber ) {
      this.inputWarning.push('Drug Identification # is required');
    }
    if ( !this.displayedItem.drugType ) {
      this.inputWarning.push('Drug Type is required');
    }

    if( this.inputWarning.length == 0) {
      this.displayedItem.name = this.displayedItem.compoundName;
    }
  }

  checkConsumableInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.containerSize ) {
      this.inputWarning.push(' Order Count is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Order Size is required');
    }
    if ( !this.displayedItem.packDenomination ) {
      this.inputWarning.push('Pack Denomination is required');
    }
    if ( !this.displayedItem.denominationUnit ) {
      this.inputWarning.push('Denomination Unit is required');
    }
    if ( !this.displayedItem.checkoutDenomination ) {
      this.inputWarning.push('Checkout Denomination is required');
    }
  }

  checkCommercialConjugatedAntibodyInput() {
    if ( !this.displayedItem.host ) {
      this.inputWarning.push('Host is required');
    }
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Antigen is required');
    }
    if ( !this.displayedItem.clonality ) {
      this.inputWarning.push('Clonality is required');
    }
    if ( !this.displayedItem.conjugationType ) {
      this.inputWarning.push('Conjugation Type is required');
    }
  }

  checkCommercialUnconjugatedAntibodyInput() {
    if ( !this.displayedItem.host ) {
      this.inputWarning.push('Host is required');
    }
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Antigen is required');
    }
    if ( !this.displayedItem.clonality ) {
      this.inputWarning.push('Clonality is required');
    }
  }

  checkCommercialConjugatedProteinInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItem.conjugationType ) {
      this.inputWarning.push('Conjugation Type is required');
    }
  }

  checkCommercialUnconjugatedProteinAndEnzymeInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
  }

  checkInhouseConjugatedAntibodyInput() {
    if ( !this.displayedItem.host ) {
      this.inputWarning.push('Host is required');
    }
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Antigen is required');
    }
    if ( !this.displayedItem.clonality ) {
      this.inputWarning.push('Clonality is required');
    }
    if ( !this.displayedItem.conjugationType ) {
      this.inputWarning.push('Conjugation Type is required');
    }
    if ( !this.displayedItem.molarChallengeRatio ) {
      this.inputWarning.push('MCR is required');
    }
  }

  checkInhouseUnconjugatedAntibodyInput() {
    if ( !this.displayedItem.host ) {
      this.inputWarning.push('Host is required');
    }
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Antigen is required');
    }
    if ( !this.displayedItem.clonality ) {
      this.inputWarning.push('Clonality is required');
    }
  }

  checkInhouseConjugatedProteinInput() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
    if ( !this.displayedItem.conjugationType ) {
      this.inputWarning.push('Conjugation Type is required');
    }
    if ( !this.displayedItem.molarChallengeRatio ) {
      this.inputWarning.push('MCR is required');
    }
  }

  genericInputCheck() {
    if ( !this.displayedItem.name ) {
      this.inputWarning.push('Name is required');
    }
    if ( !this.displayedItem.unit ) {
      this.inputWarning.push('Unit is required');
    }
  }

  saveItem() {
    this.displayedItem.editedBy = this.currentUser.name;
    this.displayedItem.modifiedOn = new Date();
    this.updateItem.emit( this.displayedItem );
  }

  reset() {
    this.displayedItem = InventoryV2.copy(this.selectedItem);
  }

  cancelItem(){
    this.cancel.emit();
  }


}
