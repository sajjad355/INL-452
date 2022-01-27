import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { ClientV2, ClientContactV2, ShippingAddressV2 } from '../../shared/objects2/ClientV2';
import { ClientService2 } from '../../shared/services2/Client2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserV2 } from '../../shared/objects2/UserV2';

@Component({
  selector: 'app-client-display',
  templateUrl: './client-display.component.html',
  styleUrls: ['./client-display.component.css']
})
export class ClientDisplayComponent implements OnInit, OnChanges {
  @Input() selectedClient: ClientV2;
  @Input() isNew : boolean;
  @Input() canManageClients : boolean;
  @Output() cancel = new EventEmitter();
  @Output() updateClient = new EventEmitter();


  editableClient: ClientV2;
  currentUser: UserV2;
  billingAndShippingAreEqual = false;
  companyNameAlreadyExists : boolean;
  inputWarning: string[] = [];
  

  constructor(private clientService: ClientService2,
              private modalService: NgbModal,
              private authenticationService: AuthenticationService2) { }

  ngOnInit() {
    const getCurrentUser = this.authenticationService.getCurrentUser();
    if ( getCurrentUser !== undefined ) {
      getCurrentUser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
  }

  ngOnChanges ( changes: SimpleChanges ) {
    if ( changes.selectedClient && this.selectedClient !== undefined ) {
      this.reset();
      this.editableClient = ClientV2.copy(this.selectedClient);
    }
    if ( this.isNew ) {
      this.editableClient = new ClientV2();
      this.billingAndShippingAreEqual = true;
    }
    
  }

  newShippingAddress() {
    if (this.editableClient.shippingAddresses == undefined) { this.editableClient.shippingAddresses = []; }
    this.editableClient.shippingAddresses.push(new ShippingAddressV2({ locationName: this.editableClient.companyName }));
  }

  

  setShippingAddressInactive( addressId : number ) {
    if(this.editableClient.shippingAddresses) {
      this.editableClient.shippingAddresses.filter(x => x.addressId == addressId)[0].active = false;
    }
  }

  newContact() {
    if (this.editableClient.clientContacts == undefined) { this.editableClient.clientContacts = []; }
    this.editableClient.clientContacts.push(new ClientContactV2());
  }


  setContactInactive( clientContactId : number ) {
    if ( this.editableClient.shippingAddresses ) {
      this.editableClient.clientContacts.filter(x => x.clientContactId == clientContactId)[0].active = false;
    }
  }

  save() {
    if ( this.billingAndShippingAreEqual ) { 
      this.setShippingEqualToBilling(); 
    }
    if (!this.validateInput()) {
      return;
    }
    this.editableClient.editedBy = this.currentUser.name;
    this.editableClient.modifiedOn = new Date();
    this.updateClient.emit(this.editableClient);
  }

  setCompanyName( companyName : string ) {
    this.editableClient.companyName = companyName;
    this.companyNameAlreadyExists = false;
    this.clientService.searchExactNameMatch( companyName ).subscribe(result => {
      for ( let i = 0 ; i < result.length; i++ ) {
        let client : ClientV2 = result[i];
        if ( client.clientCompanyId != this.editableClient.clientCompanyId ) {
          this.companyNameAlreadyExists = true;
          this.inputWarning.push('Company name already in use.');
          setTimeout( () => {
            this.inputWarning = [];
          }, 5000 );
          break;
        }
      }      
    });  
  }

  

  validateInput(): boolean {
    this.inputWarning = [];
    if ( this.companyNameAlreadyExists ) {
      this.inputWarning.push('Company name already in use.');
    }  
    if (!this.editableClient.companyName ) {
      this.inputWarning.push('Company name missing');
    }
    if (!this.editableClient.billingAddress) {
      this.inputWarning.push('Billing Address required');
    } else {
      if (!this.editableClient.billingAddress.addressLine1 ) {
        this.inputWarning.push('Billing Address Address Line 1 required');
      }
      if (!this.editableClient.billingAddress.city ) {
        this.inputWarning.push('Billing Address City required');
      }
      if (!this.editableClient.billingAddress.country ) {
        this.inputWarning.push('Billing Address Country required');
      }
    }


    if( !this.billingAndShippingAreEqual ) {
      let hasActiveShippingAddress = this.editableClient.shippingAddresses.find(address => address.active);
      if ( !this.editableClient.shippingAddresses || this.editableClient.shippingAddresses.length == 0 || !hasActiveShippingAddress) {
        this.inputWarning.push('At least 1 Shipping Address is required');
      }
      else {
        this.editableClient.shippingAddresses.forEach(shippingAddress => {
          if ( (!shippingAddress.locationName) && ( this.inputWarning.findIndex(x => x == 'Shipping Address Location Name required') == -1)){
            this.inputWarning.push('Shipping Address Location Name required');
          }
          if ( (!shippingAddress.addressLine1) && ( this.inputWarning.findIndex(x => x == 'Shipping Address Address Line 1 required.') == -1)){
            this.inputWarning.push('Shipping Address Address Line 1 required.');
          } 
          if ( (!shippingAddress.city) && ( this.inputWarning.findIndex(x => x == 'Shipping Address City required.') == -1)){
            this.inputWarning.push('Shipping Address City required.');
          }   
          if ( (!shippingAddress.country) && ( this.inputWarning.findIndex(x => x == 'Shipping Address Country required.') == -1)){
            this.inputWarning.push('Shipping Address Country required.');
          }

        });
      }
    }

    let hasActiveContactAddress = this.editableClient.clientContacts.find(address => address.active);
    if ( !this.editableClient.clientContacts || this.editableClient.clientContacts.length == 0 || !hasActiveContactAddress) {
      this.inputWarning.push('At least 1 Contact is required');
    }
    else {
      this.editableClient.clientContacts.forEach( contact => {
        if(contact.active) {
          if ((!contact.name) && ( this.inputWarning.findIndex(x => x == 'Contact name required.') == -1)){
            this.inputWarning.push('Contact name required.');
          } 
          if ((!contact.role) && ( this.inputWarning.findIndex(x => x == 'Contact role required.') == -1)){
            this.inputWarning.push('Contact role required.');
          } 
          if ((contact.email.match(/^$|[a-zA-Z0-9]+[_\-.]*[a-zA-Z0-9]*@\D\S+\.\w+/) == undefined) && 
              (this.inputWarning.findIndex(x => x == 'Contact Invalid email address.') == -1)){
            this.inputWarning.push('Contact Invalid email address.');
          }
        }
      });
    }

    setTimeout( () => {
      this.inputWarning = [];
    }, 5000 );

    return this.inputWarning.length == 0;
           
  }

  

  cancelEdit() {
    this.cancel.emit();
  }

  open(content) {
    this.modalService.open(content, { backdrop: 'static', size: 'lg' }).result.then(() => { this.save(); });
  }

  setShippingEqualToBilling() {
    this.editableClient.shippingAddresses = [];
    this.editableClient.shippingAddresses.push( new ShippingAddressV2({
      locationName: this.editableClient.companyName,
      addressLine1: this.editableClient.billingAddress.addressLine1,
      addressLine2: this.editableClient.billingAddress.addressLine2,
      city: this.editableClient.billingAddress.city,
      province: this.editableClient.billingAddress.province,
      country: this.editableClient.billingAddress.country,
      postalCode: this.editableClient.billingAddress.postalCode,
      editedBy: this.currentUser.name,
      modifiedOn: new Date()
    }));
  }

  reset() {
    this.editableClient = undefined;
    this.billingAndShippingAreEqual = false;
    this.companyNameAlreadyExists = false;
    this.inputWarning = [];
  }

  filterInactiveContacts(){
    return this.editableClient.clientContacts.filter(x => x.active == true);
  }

  filterInactiveShippingAddresses(){
    return this.editableClient.shippingAddresses.filter(x => x.active == true);
  }

 
}
