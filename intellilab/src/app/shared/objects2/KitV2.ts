import { SalesItemV2 } from './SalesItemV2';

export class KitV2 extends SalesItemV2 {
  public description: string;
  public client: string;
  public molecular: string;
  public biosimilar: string;
  public method: string;
  public status: string;
  public enteredBy: string;
  public enteredTime: Date;
  public modifiedOn: Date;
  public editedBy: string;
  public comment: string;
  public oldKitId: number;

  public kitComponents : KitComponentV2[] = [];

  public constructor(init?: Partial<KitV2>) { 
    super();
    Object.assign(this, init); 
  }

  static copy(kit: KitV2): KitV2 {
    let copyComponents : KitComponentV2[];
    if ( kit.kitComponents )
      copyComponents = kit.kitComponents.map(k => KitComponentV2.copy(k))
    else
      copyComponents = [];

    return {
      ...kit,
      kitComponents: copyComponents 
    } as KitV2;
  }   
}

export class KitComponentV2 {
  public kitComponentId : number;
  public name : string;
  public catalogNumber : string;
  public reagent : string;
  public amount : string;
  public unit : string;
  public packaging : string;
  public editedBy : string;
  public modifiedOn : Date;

  public constructor(init?: Partial<KitComponentV2>) { Object.assign(this, init); }

  static copy(kitComponent: KitComponentV2): KitComponentV2 {
    return {
      ...kitComponent
    } as KitComponentV2;
  }
}
