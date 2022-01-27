import { OrderItemV2 } from './OrderItemV2';

export class Address {
  public addressId: number;
  public addressLine1: string = '';
  public addressLine2: string = '';
  public city: string = '';
  public province: string = '';
  public country: string = '';
  public postalCode: string = '';
  public editedBy: string = '';
  public locationName: string = '';
  public modifiedOn: Date = new Date();
}

export class PurchaseOrderV2 {
  purchaseOrderId: number;
  supplier: string = '';
  salesPerson: string = '';
  quoteNumber: string = '';
  requisitioner: string = '';
  shipVia: string = '';
  paymentTerms: string = '';
  shippingTerms: string = '';
  editedBy: string = '';
  modifiedOn: Date = new Date();
  billingAddress = new Address();
  shippingAddress = new Address();
  orderItemArray: OrderItemV2[]
}