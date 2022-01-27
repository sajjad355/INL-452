import { Component, Input, Output, EventEmitter, OnChanges, OnInit, SimpleChanges  } from '@angular/core';
import { ClientV2, ClientContactV2, ShippingAddressV2, BillingAddressV2 } from '../../shared/objects2/ClientV2';
import { SimpleClientV2 } from '../../shared/objects2/SimpleClientV2';
import { ClientService2 } from '../../shared/services2/Client2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';


@Component({
  selector: 'app-client-search',
  templateUrl: './client-search.component.html',
  styleUrls: ['./client-search.component.css']
})
export class ClientSearchComponent implements OnInit, OnChanges {
  // required input fields
  @Input()  currentClient: ClientV2;
  @Input()  currentContact: ClientContactV2;
  @Input()  currentShippingAddress: ShippingAddressV2;
  @Input()  editingEnabled : boolean;
   
  @Input()  useOptionalFields : boolean;
  // optional - only used if above value set to true
  @Input()  currentBillingAddress: BillingAddressV2;
  @Input()  currentBillingAttention : string;
  @Input()  currentShippingAttention : string;
  
  @Output() clientSelected = new EventEmitter();
  @Output() contactSelected = new EventEmitter();
  @Output() shippingAddressSelected = new EventEmitter();
  @Output() billingAddressSelected = new EventEmitter();
  @Output() shippingAttentionSelected = new EventEmitter();
  @Output() billingAttentionSelected = new EventEmitter();
  

  originalClient : ClientV2;
  originalContact: ClientContactV2;
  originalShippingAddress : ShippingAddressV2;
  originalBillingAddress : BillingAddressV2;
  originalBillingAttention : string;
  originalShippingAttention : string;

  clientSearchResults : SimpleClientV2[] = [];
  findClient : boolean = true;
  message: string;

  constructor(
              private clientService : ClientService2,
              private errorService: ErrorService
            ) {}

  ngOnInit() {
    this.reset();
  }

  ngOnChanges(change: SimpleChanges) {
    // update the input properties if they are changed externally
    if( change.currentClient && change.currentClient.currentValue ) {
       this.currentClient = change.currentClient.currentValue; 
       this.originalClient = ClientV2.copy( this.currentClient );
    }
    if( change.currentContact && change.currentContact.currentValue ) {
      this.currentContact = change.currentContact.currentValue; 
      this.originalContact = ClientContactV2.copy( this.currentContact );
    }    
    if( change.currentShippingAddress && change.currentShippingAddress.currentValue ) {
      this.currentShippingAddress = change.currentShippingAddress.currentValue; 
      this.originalShippingAddress = ShippingAddressV2.copy( this.currentShippingAddress );
    } 
    if ( this.useOptionalFields ) {
      if( change.currentBillingAddress && change.currentBillingAddress.currentValue ) {
        this.currentBillingAddress = change.currentBillingAddress.currentValue; 
        this.originalBillingAddress = BillingAddressV2.copy( this.currentBillingAddress );
      } 
      if( change.currentBillingAttention && change.currentBillingAttention.currentValue ) {
        this.currentBillingAttention = change.currentBillingAttention.currentValue; 
        this.originalBillingAttention = this.currentBillingAttention;
      }
      if( change.currentShippingAttention && change.currentShippingAttention.currentValue ) {
        this.currentShippingAttention = change.currentShippingAttention.currentValue; 
        this.originalShippingAttention = this.currentShippingAttention
      }
    }
  }

  reset() {
    this.message = '';
    if ( !this.currentClient ) this.currentClient = new ClientV2();
    if ( !this.currentContact ) this.currentContact = new ClientContactV2();
    if ( !this.currentShippingAddress ) this.currentShippingAddress = new ShippingAddressV2();
    this.originalClient = ClientV2.copy( this.currentClient );
    this.originalContact = ClientContactV2.copy( this.currentContact );
    this.originalShippingAddress = ShippingAddressV2.copy( this.currentShippingAddress );
    if ( this.useOptionalFields ) {
      if ( this.currentBillingAddress )
        this.originalBillingAddress = BillingAddressV2.copy( this.currentBillingAddress );
      if ( this.currentBillingAttention )
        this.originalBillingAttention = this.currentBillingAttention;
      if ( this.currentBillingAddress )
        this.originalShippingAttention = this.currentShippingAttention;

    }
     
    this.clientSearchResults = [];
  }


  chooseClientFromList( client : SimpleClientV2 ) {
    if ( !client ) return;
    this.clientService.get( client.clientCompanyId ).subscribe(result => {
      this.currentClient = result;
      this.clientSelected.emit( this.currentClient );
      this.currentContact = this.currentClient.clientContacts? this.currentClient.clientContacts[0] : undefined; 
      this.currentShippingAddress = this.currentClient.shippingAddresses? this.currentClient.shippingAddresses[0] : undefined;
      this.contactSelected.emit( this.currentContact );
      this.shippingAddressSelected.emit( this.currentShippingAddress );
      if ( this.useOptionalFields  )
      {
        this.currentBillingAddress = this.currentClient.billingAddress;
        this.billingAddressSelected.emit( this.currentBillingAddress );
      }
    }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
        this.currentClient = this.originalClient;
        this.currentContact = this.originalContact;
        this.currentShippingAddress = this.originalShippingAddress;
    });
    this.clientSearchResults = [];
  }

  searchClient() {
    if (!this.currentClient) return;
    this.clientSearchResults = [];
    this.findClient = true;
    this.clientService.search( this.currentClient.companyName, true ).subscribe(result => {
      this.clientSearchResults = result;
      if (this.clientSearchResults.length < 1) {
        this.currentClient = ClientV2.copy( this.originalClient );
        this.findClient = false;
        setTimeout(() => this.findClient = true, 5000);
      }
    }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
    });
  }

  setContact( clientContactId : number ) {
    if ( !this.currentClient) return;
    if ( clientContactId == -1) {
      this.message = 'Invalid Client Contact selection';
      setTimeout(_ => { this.message = undefined; }, 5000);
      return;
    }
    this.currentContact = this.currentClient.clientContacts.find( x => x.clientContactId = clientContactId );
    this.contactSelected.emit( this.currentContact );
  }

  setShippingAddress( shippingAddressId : number ) {
    if ( !this.currentClient ) return;
    if ( shippingAddressId == -1 ) {
      this.message = 'Invalid Shipping Address selection';
      setTimeout(_ => { this.message = undefined; }, 5000);
      return;
    }
    this.currentShippingAddress = this.currentClient.shippingAddresses.find( x => x.addressId = shippingAddressId );
    this.shippingAddressSelected.emit( this.currentShippingAddress );
  }

  setBillingAttention( billingAttention : string ) {
    if ( billingAttention == '-1') {
      this.message = 'Invalid Billing Contact selection';
      setTimeout(_ => { this.message = undefined; }, 5000);
      return;
    }
    this.currentBillingAttention = billingAttention;
    this.billingAttentionSelected.emit( this.currentBillingAttention );
  }

  setShippingAttention( shippingAttention : string ) {
    if ( shippingAttention == '-1') {
      this.message = 'Invalid Shipping Contact selection';
      setTimeout(_ => { this.message = undefined; }, 5000);
      return;
    }
    this.currentShippingAttention = shippingAttention;
    this.shippingAttentionSelected.emit( this.currentShippingAttention );
  }

  getBillingAddress() : string {
    let ba : string = '';
    if ( this.currentBillingAddress ) {
      if ( this.currentBillingAddress.addressLine1 ) ba += this.currentBillingAddress.addressLine1 + '\n';
      if ( this.currentBillingAddress.addressLine2 ) ba += this.currentBillingAddress.addressLine2 + '\n';
      if ( this.currentBillingAddress.city ) ba += this.currentBillingAddress.city + '\n';
      if ( this.currentBillingAddress.province ) ba += this.currentBillingAddress.province + '\n';
      if ( this.currentBillingAddress.postalCode ) ba += this.currentBillingAddress.postalCode + '\n';
      if ( this.currentBillingAddress.country ) ba += this.currentBillingAddress.country + '\n';
    }
    return ba;
  }

  

  



}
