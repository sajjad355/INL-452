// a leaner version of Client.ts mean for coms with the backend involving a full list of client info
// rather than a single client and all it's details
export class SimpleClientV2 {
  public clientCompanyId: number;
  public companyName: string;
  public active: boolean;
  public contacts: SimpleContactV2[] = [];

  static SimpleClientV2( existing: SimpleClientV2 ): SimpleClientV2 {
    const copy: SimpleClientV2  = new SimpleClientV2();
    copy.clientCompanyId = existing.clientCompanyId;
    copy.companyName = existing.companyName;
    copy.active = existing.active;

    if ( existing.contacts ) {
      existing.contacts.forEach(contact => {
        const aSimpleContactV2Copy: SimpleContactV2 = SimpleContactV2.copySimpleContactV2( contact );
        copy.contacts.push( aSimpleContactV2Copy );
      });
    }

    
    return copy;
  }

  constructor() {}
}

export class SimpleContactV2 {
    public name: string;
    public email: string;
    public phone: string;

    static copySimpleContactV2( existing: SimpleContactV2 ): SimpleContactV2 {
      const copy: SimpleContactV2  = new SimpleContactV2();
      copy.name = existing.name;
      copy.email = existing.email;
      copy.phone = existing.phone;
      return copy;
    }

    constructor() {}
}
