import { UserV2 } from './UserV2';
import { LocationV2 } from './LocationV2';
import { ClientV2 } from './ClientV2';

/*
An instance of InventoryV2 is an *item*.
Currently, we support the handling of the following item types:
- Solutions
- Matrices
- Chemicals
- Drugs
- Consumables
- Molecular
- Cell Cultures
- Kits
- Commercial Proteins ((un)conjugated proteins, enzymes, and unconjugated antibodies)
- In-House Proteins (conjugated proteins and (un)conjugated antibodies)
 */
export class InventoryV2 {
  public inventoryId: number;
  public editedBy: string;
  public modifiedOn: Date;
  public type: string;
  public name: string;
  public active: boolean;
  public unit: string;
  public quantityThreshold: number;  
  public unitPrice: number;
  public category: string;
  public manufacturer: string;
  public inventoryDetails: InventoryDetailV2[] = [];
  public amount: number;
  public numberInUse: number;
  public oldInventoryId: number;
  public catalogNumber: string;
  public containerSize: number;
  public supplierLink: string;
  public manufacturerLink: string;
  public supplierCatalogNumber: string;
  public supplier: string;
  public subtype: string;
  public chemicalAbstractsServiceNumber: string;
  public molecularWeight: number;
  public tradeName: string;
  public compoundName: string;
  public drugType: string;
  public packDenomination: number;
  public denominationUnit: string;
  public molarChallengeRatio: string;
  public conjugationType: string;
  public checkoutDenomination: number;
  public clonality: string;
  public host: string;
  public comment: string;

  static copy(item: InventoryV2): InventoryV2 {
    let detailsCopy: InventoryDetailV2[];
    if ( item.inventoryDetails ) {
      detailsCopy = item.inventoryDetails.map(detail => InventoryDetailV2.copy(detail));
    } else {
      detailsCopy = [];
    }
    return {
      ...item,
      inventoryDetails: detailsCopy
    } as InventoryV2;
  }

  public constructor(init?: Partial<InventoryV2>) {
    Object.assign(this, init);
  }
}

export class InventoryDetailV2  {
  public inventoryDetailId: number;
  public name : string;
  public active : boolean;
  public amount: number;
  public affinityPurificationPreparationDate: Date;
  public columnPreparationNumber: string;
  public comment: string;
  public concentration: string;
  public concentrationUnit: string;
  public modifiedOn: Date;
  public editedBy: string;
  public expiryDate: Date;
  public lotNumber: string;
  public receivedDate: Date;
  public iggDepletion: string;
  public numberInUse: number;
  public projectNumber: string;
  public purification: string;
  public receiveUser: UserV2;
  public reserve: boolean;
  public retestDate: Date;
  public unit: string;
  public containerSize: number;
  public columnUsage: string;
  public volume: number;
  public volumeUnit: string;
  public location: LocationV2;
  public subLocation: LocationV2;
  public immunizationCycleDay: number;
  public titerAssay: string;
  public titer: number;
  public drugLotType: string;
  public modelNumber: string;
  public equipmentNumber: string;
  public referenceLotNumber: string;
  public storeTemperature: string;
  public purity : string;
  public batchNumber: string;
  public reconstituted : boolean;
  public hazardLevel : string;
  public checkoutPurpose : string;
  public inventoryDetailVials: InventoryDetailVialV2[] = [];
  public reserveForClient: ClientV2;
  public meetsAcceptanceCriteria: boolean = false;

  static copy(inventoryDetails: InventoryDetailV2): InventoryDetailV2 {
    let detailVialsCopy: InventoryDetailVialV2[];
    if ( inventoryDetails.inventoryDetailVials ) {
      detailVialsCopy = inventoryDetails.inventoryDetailVials.map
        (detailVial => InventoryDetailVialV2.copy(detailVial));
    } else {
      detailVialsCopy = [];
    }
    let copyReserveForClient : ClientV2;
    if ( inventoryDetails.reserveForClient )
      copyReserveForClient = ClientV2.copy(inventoryDetails.reserveForClient); 
    return {
      ...inventoryDetails,
      inventoryDetailVials: detailVialsCopy,
      reserveForClient: copyReserveForClient
    } as InventoryDetailV2;
  }

  public constructor(init?: Partial<InventoryDetailV2>) {
    Object.assign(this, init);
  }
}

export class InventoryDetailVialV2 {
  public inventoryDetailVialId: number;
  public vialNumber: string;
  public volume: number;
  public unit: string;
  public status: string;
  public modifyOn: Date;
  public editedBy: string;
  public location: LocationV2;
  public subLocation: LocationV2;
  public checkoutPurpose : string;
    
  static copy(inventoryDetailVial: InventoryDetailVialV2): InventoryDetailVialV2 {
    return {
      ...inventoryDetailVial
    } as InventoryDetailVialV2;
  }

  public constructor(init?: Partial<InventoryDetailVialV2>) { Object.assign(this, init); }
}


