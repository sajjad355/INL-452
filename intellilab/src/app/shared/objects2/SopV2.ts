export class SopV2 {
  public sopId: number;
  public active: boolean;
  public sopIdentifier: string;
  public name: string;
  public volume: string;
  public unit: string;
  public description: string;
  public direction: string;
  public enteredBy: string;
  public enteredTime: Date = new Date();
  public reviewedBy: string;
  public editStatus = 'Entered';
  public editStatusComment: string;
  public editStatusTime: Date;
  public modifiedOn: Date = new Date();
  public editedBy: string;

  public components : SopComponentV2[] = [];
  public sopLinks : SopLinkV2[] = [];


  public constructor(init?: Partial<SopV2>) { Object.assign(this, init); }

  static copy(sop: SopV2): SopV2 {
    let copyComponents : SopComponentV2[];
    if ( sop.components )
      copyComponents = sop.components.map(s => SopComponentV2.copy(s))
    else
      copyComponents = [];

    let copySopLinks : SopLinkV2[];
      if ( sop.sopLinks )
        copySopLinks = sop.sopLinks.map(sl => SopLinkV2.copy(sl))
      else
        copySopLinks = [];  

    return {
      ...sop,
      components: copyComponents,
      sopLinks: copySopLinks
    } as SopV2;
  }   
}

export class SopComponentV2 {
  public componentId: number;
  public itemName: string;
  public requiredQuantity: number;
  public requiredUnit: string;
  public vendor: string;
  public catalogNumber: string;
  public supplierCatalogNumber: string;
  public editedBy: string;
  public modifiedOn: Date;

  public constructor(init?: Partial<SopComponentV2>) { Object.assign(this, init); }

  static copy(sopComponent: SopComponentV2): SopComponentV2 {
    return {
      ...sopComponent
    } as SopComponentV2;
  }

}

export class SopLinkV2 {
  public id: number;
  public requiredQuantity: number;
  public requiredUnit: number;
  public editedBy: string;
  public modifiedOn: Date;
  public referencedSop : SopV2;

  public constructor(init?: Partial<SopLinkV2>) { Object.assign(this, init); }


  static copy(sopLink: SopLinkV2): SopLinkV2 {
    let copySopReference : SopV2;
    if ( sopLink.referencedSop )
      copySopReference = SopV2.copy(sopLink.referencedSop )
    else
      copySopReference = new SopV2;
    return {
      ...sopLink,
      referencedSop: copySopReference
    } as SopLinkV2;
  }

  
}
