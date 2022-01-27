import { SalesItemV2 } from './SalesItemV2';

export class  ProductV2 extends SalesItemV2 {
  public type: string;
  public description: string;  
  public unit: string;
  public clonality: string;
  public host: string;
  public isotype: string;
  public immunogen: string;
  public purification: string;
  public buffer: string;
  public specificity: string;
  public reconstitution: string;
  public storage: string;
  public unitSize: number;
  public comment: string;
  public enteredBy: string;
  public enteredTime: Date;
  public editedBy: string;
  public modifiedOn: Date;
  public oldProductId : number;

  public applications : ApplicationV2[] = [];

  public constructor(init?: Partial<ProductV2>) { 
    super();
    Object.assign(this, init); 
  }

  static copy(product: ProductV2): ProductV2 {
    let copyApplications : ApplicationV2[];
    if ( product.applications )
      copyApplications = product.applications.map(a => ApplicationV2.copy(a))
    else
    copyApplications = [];

    return {
      ...product,
      applications: copyApplications 
    } as ProductV2;
  }   

  
}

export class ApplicationV2 {
  public applicationId : number;
  public purpose : string;
  public recommendedConcentration : string;
  public comment : string;
  public note : string;
  public editedBy : string;
  public modifiedOn : Date;

  public constructor(init?: Partial<ApplicationV2>) { 
    Object.assign(this, init); 
  }

  static copy( existing : ApplicationV2 ): ApplicationV2 {

    return {
      ...existing 
    } as ApplicationV2;
  }   

}