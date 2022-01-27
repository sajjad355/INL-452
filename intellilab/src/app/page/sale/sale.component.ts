import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { InvoiceV2 } from '../../shared/objects2/InvoiceV2';
import { QuoteV2 } from '../../shared/objects2/QuoteV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { InvoiceService2 } from '../../shared/services2/Invoice2.service';
import { QuoteService2 } from '../../shared/services2/Quote2.service';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';

class SearchCriteria {
  searchValue : string;
  active : boolean;
}

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.scss']//,
  //host: {
    //'(document:click)': 'onClick($event)',
  //}
})

export class SaleComponent implements OnInit {
  quotes: QuoteV2[] = [];
  displayQuote : QuoteV2;
  displayInvoice : InvoiceV2;
  search : SearchCriteria = { searchValue: "", active : true };
  invoices: InvoiceV2[];
  tabs: string[] = ['Quote', 'Invoice'];

  currentTab: string = 'Quote';
  isNew: boolean;

  page: number = 1
  showEditPage: boolean = false;

  isLoading: boolean = true
  count: number;
  currentuser: UserV2;
  @ViewChild('changeVersionNumberOption') changeVersionNumberOption: ElementRef;
  changeVersionNumberModal:any;
  versionNumber=0;
  dateFormat: any;
  dateFormatDisplay: string;

  canManageSales: boolean;

  // added selectItem cause it is used in template but wasn't initialized before
  selectItem: any;

  constructor(
    private quoteService: QuoteService2,
    private errorService: ErrorService,
    private invoiceService: InvoiceService2,
    private authenticationservice: AuthenticationService2,
    private settingService : SettingService2,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.handleParam()
    this.loadSetting();

    let getcurrentuser = this.authenticationservice.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);

        this.canManageSales = false;
        if (this.currentuser && this.currentuser.userRoles) {
          this.canManageSales = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.MANAGE_SALES);
        }
      });
    }

    this.reset();
    if (this.currentTab == 'Quote'){
      this.quoteService.getCount(this.search.active).subscribe(c => {
        this.count = c;
        this.loadQuotePage();
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }

    else if (this.currentTab == 'Invoice'){
      this.invoiceService.getCount(this.search.active).subscribe(c => {
        this.count = c;
        this.loadInvoicePage();
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }

  handleParam() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")
      const inventoryId = Number(idParam)

      inventoryId !== 0 && this.loadItem(inventoryId)
    })
  }

  loadSetting(){
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
  }

  loadQuotePage() {
    this.isLoading = true;
    this.quoteService.loadQuotesByPage(this.page - 1, this.search.active ).subscribe(q => {
      this.quotes = q;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isLoading = false;
  }

  loadInvoicePage() {
    this.isLoading = true;
    this.invoiceService.loadInvoicesByPage(this.page - 1, this.search.active).subscribe(i => {
      this.invoices = i;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isLoading = false;
  }

  loadItem( quoteId : number ) {
    this.isLoading = true;
    this.quoteService.get(quoteId).subscribe(q => {
      this.displayQuote = q;
      this.showEditPage = true;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isLoading = false;
  }

  loadInvoice( invoiceId : number ) {
    this.isLoading = true;
    this.invoiceService.get(invoiceId).subscribe(i => {
      this.displayInvoice = i;
      this.showEditPage = true;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isLoading = false;
  }

  reset() {
    this.currentTab = 'Quote';

    this.isNew = false;
    this.showEditPage = false;

    this.displayQuote = undefined;
    this.displayInvoice = undefined;
    this.search.active = true;
    this.search.searchValue = '';
  }

  changeTab(tab: string) {
    if(this.isNew) return;
    this.currentTab = tab;
    this.displayQuote = undefined;
    this.displayInvoice = undefined;

    this.isNew = false;
    this.showEditPage = false;
    this.page = 1;
    this.isLoading = true;
    this.search.searchValue = '';
    this.search.active = true;
    if (this.currentTab == 'Quote'){
      this.quoteService.getCount(this.search.active).subscribe(c => {
        this.count = c;
        this.loadQuotePage();
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }

    else if (this.currentTab == 'Invoice'){
      this.invoiceService.getCount(this.search.active).subscribe(c => {
        this.count = c;
        this.loadInvoicePage();
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
    this.isLoading = false;
  }

  createNewQuote() {
    this.isLoading = true;
    if (this.count < 1) {
      let defaultStartingQuoteNumber = "7000";
      this.displayQuote = this.getNewQuote( defaultStartingQuoteNumber );
      this.isNew = true;
      this.showEditPage = true;
      this.isLoading = false;
    }
    else {
      this.quoteService.getNextQuoteNumber().subscribe(nextQuoteNumber => {
        this.displayQuote = this.getNewQuote( nextQuoteNumber.toString() );
        this.isNew = true;
        this.showEditPage = true;
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
        this.isLoading = false;
      });
    }
  }

  createNewInvoice() {
    this.isLoading = true;
    if (this.count < 1) {
      let defaultStartingInvoiceNumber = "7000";
      this.displayInvoice = this.getNewInvoice( defaultStartingInvoiceNumber );
      this.isNew = true;
      this.showEditPage = true;
      this.isLoading = false;
    }
    else {
      this.invoiceService.getNextInvoiceNumber().subscribe(nextInvoiceNumber => {
        this.displayInvoice = this.getNewInvoice( nextInvoiceNumber.toString() );
        this.isNew = true;
        this.showEditPage = true;
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
        this.isLoading = false;
      });
    }
  }





  // newItem() {
  //   this.isLoading = true;
  //   if (this.currentTab == 'Quote') {
  //     let today = new Date();
  //     let today30dlater = today.getTime() + 60 * (24 * 60 * 60 * 1000);

  //     if (this.count < 1) {
  //       let defaultStartingQuoteNumber = "7000";
  //       this.displayQuote = this.getNewQuote( defaultStartingQuoteNumber );
  //       this.isNew = true;
  //       this.showEditPage = true;
  //       this.isLoading = false;
  //     }
  //     else {
  //       this.quoteService.getNextQuoteNumber().subscribe(nextQuoteNumber => {
  //         this.displayQuote = this.getNewQuote( nextQuoteNumber.toString() );
  //         this.isNew = true;
  //         this.showEditPage = true;
  //         this.isLoading = false;
  //       }, error => {
  //         ErrorUtil.handleHttpError( error );
  // this.showError(error);
  //       });
  //     }
  //   }
  //   else if (this.currentTab == 'Invoice') {
      // let today = new Date();
      // let id = 0;
      // if (this.count < 1) {
      //   id = 7000;
      //   this.selectItem = new Invoice(-1, id, today, undefined, -1, 1, today, '', '', '', '', '', '', 'FedEx', 'Prepayment', 'EXW',0, 0, 0,'No','Product invoice', '', this.currentuser.name, new Date(), 'USD', 1, -1);

      //   this.isNew = true;
      //   this.showEditPage = true;
      //   this.isLoading = false;
      // } // add to setting to keep track of the number sequence
      // else {
      //   this.invoiceservice.getLatest().subscribe(last => {
      //     if (last !== undefined) {
      //       id = last.invoiceNum + 1;
      //       this.selectItem = new Invoice(-1, id, today, undefined, -1, 1, today, '', '', '', '', '', '', 'FedEx', 'Prepayment', 'EXW', 0, 0, 0,'No','Product invoice', '', this.currentuser.name, new Date(), 'USD', 1, -1);

      //       this.isNew = true;
      //       this.showEditPage = true;
      //       this.isLoading = false;
      //     }
      //   })
      // }
  //     alert( 'todo');
  //   }

  // }

  getNewQuote( quoteNumber : string ) : QuoteV2 {
    let aNewQuote : QuoteV2 = new QuoteV2();
    let today = new Date();
    let today30dlater = today.getTime() + 30 * (24 * 60 * 60 * 1000);
    aNewQuote.quoteNumber = quoteNumber;
    aNewQuote.active = true;
    aNewQuote.createdDate = today;
    aNewQuote.expirationDate = new Date( today30dlater );
    aNewQuote.paymentType = 'Prepayment';
    aNewQuote.revision = 1;
    aNewQuote.revisionDate = today;
    aNewQuote.shippingFee = 0;
    aNewQuote.handlingFee = 0;
    aNewQuote.tax = 0;
    aNewQuote.taxRate = 'No Tax';
    aNewQuote.complete = false;
    aNewQuote.note = null;
    aNewQuote.editedBy = this.currentuser.name;
    aNewQuote.modifiedOn = today;
    aNewQuote.currency = 'USD';
    aNewQuote.currencyRate = 1;
    return aNewQuote;
  }

  getNewInvoice( invoiceNumber : string ) : InvoiceV2 {
    let aNewInvoice : InvoiceV2 = new InvoiceV2();
    let today = new Date();
    aNewInvoice.invoiceNumber = invoiceNumber;
    aNewInvoice.active = true;
    aNewInvoice.dateCreated = today;
    aNewInvoice.paymentType = 'Prepayment';
    aNewInvoice.revision = 1;
    aNewInvoice.revisionDate = today;
    aNewInvoice.courier = 'Fedex';
    aNewInvoice.shippingTerm = 'EXW';
    aNewInvoice.shippingFee = 0;
    aNewInvoice.handlingFee = 0;
    aNewInvoice.tax = 0;
    aNewInvoice.proforma = false;
    aNewInvoice.purchaseOrderNumber = null;
    aNewInvoice.active = true;
    aNewInvoice.type = 'Product Invoice';
    aNewInvoice.note = null;
    aNewInvoice.editedBy = this.currentuser.name;
    aNewInvoice.modifiedOn = today;
    aNewInvoice.currency = 'USD';
    aNewInvoice.currencyRate = 1;
    return aNewInvoice;
  }

  searchSales() {
    if(this.isNew) return;
    this.isLoading = true;

    if (!this.search.searchValue) {
      this.page = 1;
      this.currentTab == 'Quote' ? this.loadQuotePage() :  this.loadInvoicePage();
      return;
    }

    if (this.currentTab == 'Quote') {
      this.quoteService.searchPageable(this.search.searchValue,  this.page - 1, this.search.active).subscribe(result => {
        this.quotes = result;
        this.quoteService.searchQuoteCount( this.search.searchValue, this.search.active ).subscribe( c => {
          this.count = c;
        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
    else if (this.currentTab == 'Invoice') {
      this.invoiceService.searchPageable(this.search.searchValue, this.page - 1,this.search.active).subscribe(result => {
        this.invoices = result;
        this.invoiceService.searchQuoteCount( this.search.searchValue, this.search.active ).subscribe( c => {
          this.count = c;
        }, error => {
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }


  cancelEdit() {
    this.displayInvoice = undefined;
    this.displayQuote = undefined;
    this.isNew = false;
    this.showEditPage = false;

  }

  saveInvoice( invoice : InvoiceV2 ) {
    this.invoiceService.save( invoice ).then( savedInvoice => {
      invoice.invoiceId = (savedInvoice as InvoiceV2).invoiceId;
      this.cancelEdit();
      this.searchSales();
   }).catch( err => {
     ErrorUtil.handleHttpError( err );
     this.errorService.message$ = err.error;
   });
  }




  saveQuote( quote : QuoteV2 ) {
    this.quoteService.save( quote ).then( savedQuote => {
       quote.quoteId = (savedQuote as QuoteV2).quoteId;
       this.cancelEdit();
       this.searchSales();
    }).catch( err => {
      ErrorUtil.handleHttpError( err );
      this.errorService.message$ = err.error;
    });
  }


}
