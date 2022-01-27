import { ClientV2, ShippingAddressV2, ClientContactV2, BillingAddressV2 } from './ClientV2';
import { SalesItemV2 } from './SalesItemV2';
import { QuoteV2 } from './QuoteV2';
import { KitV2 } from './KitV2';
import { ProductV2 } from './ProductV2';
import { InventoryV2 } from './InventoryV2';


export class InvoiceV2 {
   public invoiceId : number;
   public shippingAttention : string;
   public active : boolean;
   public currency : string;
   public currencyRate : number;
   public invoiceNumber : string;
   public dateCreated : Date;
   public quote : QuoteV2;
   public revision : number;
   public revisionDate : Date;
   public billingAttention : string;
   public purchaseOrderNumber : string;
   public courier : string;
   public paymentType : string;
   public shippingTerm : string;
   public shippingFee : number;
   public handlingFee : number;
   public tax : number;
   public taxRate : string;
   public proforma : boolean;
   public type : string;
   public note : string;
   public editedBy : string;
   public modifiedOn : Date;
   public oldInvoiceId : number;
   public numberPackages : number;
   public clientCompany  : ClientV2;
   public clientContact : ClientContactV2;
   public billingAddress : BillingAddressV2;
   public shippingAddress :  ShippingAddressV2;
   public lineItems : InvoiceLineItemV2[] = [];

   
   public constructor(init?: Partial<InvoiceV2>) { 
    Object.assign(this, init); 
  }

  static copy(invoice: InvoiceV2): InvoiceV2 {
    let copyClientCompany : ClientV2;
    if ( invoice.clientCompany )
      copyClientCompany = ClientV2.copy(invoice.clientCompany);

    let copyClientContact : ClientContactV2;
      if ( invoice.clientContact )
      copyClientContact = ClientContactV2.copy(invoice.clientContact);  

    let copyShippingAddress : ShippingAddressV2;
    if ( invoice.shippingAddress )
    copyShippingAddress = ShippingAddressV2.copy(invoice.shippingAddress);

    let copyLineItems : InvoiceLineItemV2[];
    if ( invoice.lineItems )
      copyLineItems = invoice.lineItems.map(li => InvoiceLineItemV2.copy(li))
    else
      copyLineItems = [];

    let copyQuote : QuoteV2;
    if ( invoice.quote )
      copyQuote = QuoteV2.copy(invoice.quote);
    

    return {
      ...invoice,
      clientCompany: copyClientCompany,
      clientContact: copyClientContact,
      shippingAddress : copyShippingAddress,
      quote : copyQuote,
      lineItems : copyLineItems
    } as InvoiceV2;
  }   
}


export class InvoiceLineItemV2 {
  public invoiceLineItemId : number;
  public catalogNumber: string;
  public name: string;
  public price : number;
  public size: string;
  public itemDiscount: number;
  public itemQuantity : number;
  public totalPrice : number;
  public footNote : string;
  public harmonizedSystemCode : string;
  public packageWeight : number;
  public editby : string;
  public modifiedOn : Date;
  public salesItem : SalesItemV2;

  public constructor(init?: Partial<InvoiceLineItemV2>) { 
    Object.assign(this, init); 
  }

  static copy(invoiceLineItem: InvoiceLineItemV2): InvoiceLineItemV2 {
    let copySalesItem : SalesItemV2;
    if ( invoiceLineItem.salesItem instanceof KitV2 )
      copySalesItem = KitV2.copy(invoiceLineItem.salesItem as KitV2);
    else if ( invoiceLineItem.salesItem instanceof ProductV2 )
      copySalesItem = ProductV2.copy(invoiceLineItem.salesItem as ProductV2);
     

    return {
      ...invoiceLineItem,
      salesItem : copySalesItem
    } as InvoiceLineItemV2;

  }

 
}

export class PrintedInvoiceLine {
  category: string;
  description: string;
  quantity: number;
  packageWeight : number;
  // these seemingly numeric types are defined as string to hold the $
  listPrice: string; 
  discount: string;
  adjustedPrice: string;
  totalPrice: string;  
}

export class PrintedInvoice {
  totalDiscount : number;
  totalPrice : number;
  totalListPrice : number;
  totalPackageWeight: number;
  numberPackages : number;
  hasDiscount : boolean;
  data : PrintedInvoiceLine[] = [];
}