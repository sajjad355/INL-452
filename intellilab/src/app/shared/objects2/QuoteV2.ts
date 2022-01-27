import { ClientV2, ShippingAddressV2, ClientContactV2 } from './ClientV2';
import { SalesItemV2 } from './SalesItemV2';
import { KitV2 } from './KitV2';
import { ProductV2 } from './ProductV2';
import { InventoryV2 } from './InventoryV2';

export class QuoteV2 {
  public quoteId : number;
  public currency : string;
  public active : boolean;
  public currencyRate : number;
  public quoteNumber : string;
  public createdDate : Date;
  public expirationDate : Date;
  public paymentType : string;
  public revision: number;
  public revisionDate: Date;
  public shippingFee: number;
  public handlingFee : number;
  public tax : number;
  public taxRate : string;
  public complete : boolean;
  public note: string;
  public editedBy: string;
  public modifiedOn: Date;
  public oldQuoteId: number;
  public expired : boolean;
  public clientCompany : ClientV2;
  public clientContact : ClientContactV2;
  public shippingAddress : ShippingAddressV2;
  public lineItems : QuoteLineItemV2[] = [];

  public constructor(init?: Partial<QuoteV2>) { 
    Object.assign(this, init); 
  }

  static copy(quote: QuoteV2): QuoteV2 {
    let copyClientCompany : ClientV2;
    if ( quote.clientCompany )
      copyClientCompany = ClientV2.copy(quote.clientCompany);

    let copyClientContact : ClientContactV2;
    if ( quote.clientContact )
    copyClientContact = ClientContactV2.copy(quote.clientContact);

    let copyShippingAddress : ShippingAddressV2;
    if ( quote.shippingAddress )
    copyShippingAddress = ShippingAddressV2.copy(quote.shippingAddress);

    let copyLineItems : QuoteLineItemV2[];
    if ( quote.lineItems )
      copyLineItems = quote.lineItems.map(li => QuoteLineItemV2.copy(li))
    else
      copyLineItems = [];

    return {
      ...quote,
      clientCompany: copyClientCompany,
      clientContact: copyClientContact,
      shippingAddress : copyShippingAddress,
      lineItems : copyLineItems
    } as QuoteV2;
  }   

}

export class QuoteLineItemV2 {
  public quoteLineItemId : number;
  public catalogNumber: string;
  public name: string;
  public price : number;
  public size: string;
  public itemDiscount: number;
  public itemQuantity : number;
  public totalPrice : number;
  public footNote : string;
  public editby : string;
  public modifiedOn : Date;
  public salesItem : SalesItemV2;

  public constructor(init?: Partial<QuoteLineItemV2>) { 
    Object.assign(this, init); 
  }

  static copy(quoteLineItem: QuoteLineItemV2): QuoteLineItemV2 {
    let copySalesItem : SalesItemV2;
    if ( quoteLineItem.salesItem instanceof KitV2 )
      copySalesItem = KitV2.copy(quoteLineItem.salesItem as KitV2);
    else if ( quoteLineItem.salesItem instanceof ProductV2 )
      copySalesItem = ProductV2.copy(quoteLineItem.salesItem as ProductV2);
    

    return {
      ...quoteLineItem,
      salesItem : copySalesItem
    } as QuoteLineItemV2;

  }
}

export class PrintedQuoteLine {
  category: string;
  description: string;
  quantity: number;
  // these seemingly numeric types are defined as string to hold the $
  listPrice: string; 
  discount: string;
  adjustedPrice: string;
  totalPrice: string;  
}

export class PrintedQuote {
  totalDiscount : number;
  totalPrice : number;
  totalListPrice : number;
  hasDiscount : boolean;
  data : PrintedQuoteLine[] = [];
}

