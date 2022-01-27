export class ClientV2 {
  public clientCompanyId: number;
  public companyName = '';
  public active = true;
  public editedBy = '';
  public modifiedOn = new Date();
  public billingAddress: BillingAddressV2; 
  public shippingAddresses: ShippingAddressV2[]; 
  public clientContacts: ClientContactV2[]; 

  static copy(client: ClientV2): ClientV2 {
    let copyBillingAddress : BillingAddressV2;
    if ( client.billingAddress )
      copyBillingAddress = BillingAddressV2.copy(  client.billingAddress );

    let copyShippingAddresses : ShippingAddressV2[];
    if ( client.shippingAddresses )
      copyShippingAddresses = client.shippingAddresses.map(s => ShippingAddressV2.copy(s))
    else
      copyShippingAddresses = [];

    let copyContacts : ClientContactV2[];
      if ( client.clientContacts )
        copyContacts = client.clientContacts.map(c => ClientContactV2.copy(c))
      else
        copyContacts = [];

    return {
      ...client,
      billingAddress: copyBillingAddress,
      shippingAddresses: copyShippingAddresses,
      clientContacts: copyContacts
    } as ClientV2;
  }

  public constructor(init?: Partial<ClientV2>) { 
    this.billingAddress  = new BillingAddressV2();
    this.shippingAddresses = [new ShippingAddressV2()];
    this.clientContacts = [new ClientContactV2()];

    Object.assign(this, init);
  }
}



export class BillingAddressV2 {
  public addressId: number;
  public addressLine1 = '';
  public addressLine2 = '';
  public city = '';
  public province = '';
  public country = '';
  public postalCode = '';
  public editedBy = '';
  public modifiedOn = new Date();

  static copy( billingAddress: BillingAddressV2 ): BillingAddressV2 { return { ...billingAddress }; }

  constructor( init?: Partial<BillingAddressV2> ) { Object.assign(this, init); }
}

export class ShippingAddressV2 {
  public addressId: number;
  public locationName = '';
  public addressLine1 = '';
  public addressLine2 = '';
  public city = '';
  public province = '';
  public country = '';
  public postalCode = '';
  public editedBy = '';
  public modifiedOn = new Date();
  public active: boolean = true;

  static copy( shippingAddress: ShippingAddressV2 ): ShippingAddressV2 { return { ...shippingAddress }; }

  constructor( init?: Partial<ShippingAddressV2> ) { Object.assign(this, init); }
}

export class ClientContactV2 {
  public clientContactId: number;
  public name = '';
  public role = '';
  public email = '';
  public phone = '';
  public ext = '';
  public fax = '';
  public editedBy = '';
  public modifiedOn = new Date();
  public active: boolean = true;

  static copy( clientContact: ClientContactV2 ): ClientContactV2 { return { ...clientContact }; }

  constructor( init?: Partial<ClientContactV2> ) { Object.assign(this, init); }
}
