import { Component, Input, Output, EventEmitter } from '@angular/core';
import { QuoteV2 } from '../../shared/objects2/QuoteV2';
import { QuoteService2 } from '../../shared/services2/Quote2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';


@Component({
  selector: 'app-quote-search',
  templateUrl: './quote-search.component.html',
  styleUrls: ['./quote-search.component.css']
})
export class QuoteSearchComponent  {

  @Input()  currentQuote: QuoteV2;
  @Output() quoteSelected = new EventEmitter();
  quoteNumber : string = '';
  quoteSearchResults : QuoteV2[] = [];
  findQuote : boolean = true;
  originalQuote : QuoteV2;

  constructor(private errorService: ErrorService, private quoteService : QuoteService2) {}

  ngOnInit() {
    this.originalQuote = this.currentQuote;
    if ( this.currentQuote ) {
      this.quoteNumber = this.currentQuote.quoteNumber;
    }
  }

  

  selectQuote( quoteId : number ) {
    // note that we have to go back to the database now with a get call for the specific quote
    // as the search only retrieves enough information to allow user to make a selection but the
    // caller may require all of the details of the quote
    this.quoteService.get( quoteId ).subscribe( q => {
      this.currentQuote = q;
      this.quoteNumber = this.currentQuote.quoteNumber;
      this.quoteSelected.emit( this.currentQuote );
      this.quoteSearchResults = [];
    });
    
  }

  searchQuote() {
    if (!this.quoteNumber) return;
    this.quoteSearchResults = [];
    this.findQuote = true;
    let activeQuotesOnly : boolean = true;
    let completeQuotesOnly : boolean = false;
    this.quoteService.search( this.quoteNumber, activeQuotesOnly, completeQuotesOnly ).subscribe(result => {
      
      this.quoteSearchResults = result;
      if (this.quoteSearchResults.length < 1) {
          this.currentQuote = this.originalQuote;
          this.findQuote = false;
          setTimeout(() => this.findQuote = true, 5000);
        }
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }

  

}