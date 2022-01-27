import { Address } from "./PurchaseOrderV2";

export class ItemSourceV2 {
  
  public dbid: number;
  public name: string;
  public type: string;
  public approvalType: string;
  public link: string;
  public billingAddress: Address = new Address();
  public createDate: Date;
  public modifiedOn: Date;
  public editedBy: string;
  public active: boolean = true;

  static copy(existing: ItemSourceV2): ItemSourceV2 {
    let copy : ItemSourceV2  = new ItemSourceV2();
    copy.dbid = existing.dbid;
    copy.name = existing.name;
    copy.type = existing.type;
    copy.approvalType = existing.approvalType;
    copy.link = existing.link;
    copy.billingAddress = existing.billingAddress;
    copy.createDate = existing.createDate;
    copy.modifiedOn = existing.modifiedOn;
    copy.editedBy = existing.editedBy;
    copy.active = existing.active;
    return copy;
  }

}