import { Component, OnInit} from '@angular/core';
import { ClientV2 } from '../../shared/objects2/ClientV2';
import { ClientService2 } from '../../shared/services2/Client2.service';
import * as XLSX from 'xlsx';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { SimpleClientV2, SimpleContactV2 } from '../../shared/objects2/SimpleClientV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { ErrorService } from '../../page/error/error.service';

class SearchCriteria {
  key: string;
  active: boolean;
}

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})

export class ClientComponent implements OnInit {
  currentUser: UserV2;
  displayedClients: SimpleClientV2[] = [];
  displayedContacts: SimpleContactV2[][] = [];
  selectedClient: ClientV2;
  isNew = false;
  showClient: boolean;
  hasHeader: boolean;
  searchCriteria: SearchCriteria = { key: '', active: true };
  isLoading: boolean;
  page: number;
  viewColumn: boolean[];
  columnList: string[];
  count: number;

  canManageClients: boolean;


  constructor(private clientService: ClientService2,
    private errorService: ErrorService,
              private authenticationService: AuthenticationService2) { }

  ngOnInit() {
    this.isLoading = true;
    const getCurrentUser = this.authenticationService.getCurrentUser();
    if (getCurrentUser !== undefined) {
      getCurrentUser.then(_ => {
        this.currentUser = UserV2.copy(_);
        this.canManageClients = false;
        if (this.currentUser && this.currentUser.userRoles) {
          this.canManageClients = UserV2.isAllowedToPerform(this.currentUser, OPERATION_NAMES.MANAGE_CLIENTS);
        }
      });
    }
    this.page = 1;
    this.loadPage();
    this.viewColumn = [true, true, true, true];
    this.columnList = ['Company', 'Contact'];
    this.displayedContacts = [];
    this.page = 1;
    this.searchCriteria.key = '';
    this.showClient = false;
    this.count = 0;
    this.hasHeader = true;
    this.isLoading = false;
  } 

  newClient() {
    this.isNew = true;
    this.selectedClient = new ClientV2();
    this.showClient = true;
  }

  cancelEdit() {
    this.selectedClient = undefined;
    this.isNew = false;
    this.showClient = false;
  }

  saveClient(client: ClientV2) {
    this.isLoading = true;
    if (client != undefined) {
      if (client.clientContacts === undefined) {
        client.clientContacts = [];
      }
      if (client.shippingAddresses === undefined) {
        client.shippingAddresses = [];
      }
      this.clientService.save(client).then(c => {
        this.isNew = false;
        this.selectedClient = undefined;
        this.showClient = false;
        this.search();
        this.isLoading = false;
      }).catch( err => {
        this.isLoading = false;
        ErrorUtil.handleHttpError( err );
        this.errorService.message$ = err.error;
      });
    }
  }

  loadClient( clientCompanyId : number ) {
    this.clientService.get(clientCompanyId).subscribe( client => {
      this.selectedClient = client;
      this.showClient = true;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  search() {
    this.isLoading = true;
    if ( this.searchCriteria.key == '' ) {
      this.loadPage();
    }
    else  {
      this.clientService.searchPageable(
        this.searchCriteria.key, this.searchCriteria.active, this.page - 1).subscribe(result => {
        this.displayedClients = result;
        this.clientService.searchCount(this.searchCriteria.key, this.searchCriteria.active).subscribe(count => {
          this.count = count;
        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
        this.isLoading = false;
      }, error => {
        this.isLoading = false;
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }

  loadPage() {
    this.isLoading = true;
    this.displayedClients = [];
    this.clientService.getAllClientsByPage(this.page - 1,this.searchCriteria.active).subscribe(clients => {
      this.displayedClients = clients;
      this.clientService.getClientCount(this.searchCriteria.active).subscribe(c => {
         this.count = c;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
      this.isLoading = false;
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }


  loadFile(event) { alert( 'This function has been deprecated. Are you sure you meant to invoke it? Contact support for more details.'); }

  getDataArrayFromXML(worksheet: XLSX.WorkSheet): { type: string, value: any }[][] {
    if (worksheet == undefined) { return; }
    const result: { type: string, value: any }[][] = [];
    const range = XLSX.utils.decode_range(worksheet['!ref']);
    for (let rowNum = range.s.r; rowNum <= range.e.r; rowNum++) {
      const row = [];
      for (let colNum = range.s.c; colNum <= range.e.c; colNum++) {
        const nextCell = worksheet[XLSX.utils.encode_cell({ r: rowNum, c: colNum })];
        if (nextCell === undefined) {
          row.push({ type: 's', value: '' });
        } else {
          let val = nextCell.w;
          if (nextCell.t == 'd') {
            const date = new Date(nextCell.w);
            const dom = date.getDate();
            val = date.getMonth + '/' + (dom < 10 ? '0' + dom : dom) + '/' + date.getFullYear();
          }
          row.push({ type: nextCell.t, value: val });

        }
        // row.push(nextCell.w)
      }
      let isAllEmpty = true;
      row.forEach(val => { if (val.value !== '') { isAllEmpty = false; } });

      if (!isAllEmpty) { result.push(row); }
    }
    return result;
  }
}
