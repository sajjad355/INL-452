import { Component, Input, Output, EventEmitter, OnChanges, OnInit, SimpleChanges  } from '@angular/core';
import { ClientV2 } from '../../shared/objects2/ClientV2';
import { SimpleClientV2 } from '../../shared/objects2/SimpleClientV2';
import { ClientService2 } from '../../shared/services2/Client2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';



@Component({
  selector: 'app-simple-client-search',
  templateUrl: './simple-client-search.component.html',
  styleUrls: ['./simple-client-search.component.css']
})
export class SimpleClientSearchComponent implements OnInit, OnChanges {
  // required input fields
  @Input()  currentClient: ClientV2;


  @Output() clientSelected = new EventEmitter();


  originalClient : ClientV2;

  clientSearchResults : SimpleClientV2[] = [];
  findClient : boolean = true;
  message: string;

  constructor(private errorService: ErrorService, private clientService : ClientService2) {}

  ngOnInit() {
    this.reset();
  }

  ngOnChanges(change: SimpleChanges) {
    // update the input properties if they are changed externally
    if( change.currentClient && change.currentClient.currentValue ) {
       this.currentClient = change.currentClient.currentValue;
       this.originalClient = ClientV2.copy( this.currentClient );
    }
  }

  reset() {
    this.message = '';
    if ( !this.currentClient ) this.currentClient = new ClientV2();
    this.clientSearchResults = [];
  }


  chooseClientFromList( client : SimpleClientV2 ) {
    if ( !client ) return;
    this.clientService.get( client.clientCompanyId ).subscribe(result => {
      this.currentClient = result;
      this.clientSelected.emit( this.currentClient );
    }, error => {
        ErrorUtil.handleHttpError( error );

        this.currentClient = this.originalClient;
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

}
