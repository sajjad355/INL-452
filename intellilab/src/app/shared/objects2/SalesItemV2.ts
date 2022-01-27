export class SalesItemV2 {
  public salesItemId : number;
  public name : string;
  public catalogNumber : string; 
  public active: boolean;
  public unitPrice: number;
  public editStatus : string;
  public reviewedBy : string;
  public editStatusComment : string;
  public editStatusTime : Date;
  public packSize : string;
  
  // simply serves as parent class of kit and product
  public constructor(init?: Partial<SalesItemV2>) { 
    Object.assign(this, init); 
  }
  
  static copy(salesItem: SalesItemV2): SalesItemV2 {
    return {
      ...salesItem
    } as SalesItemV2;
  }
}   