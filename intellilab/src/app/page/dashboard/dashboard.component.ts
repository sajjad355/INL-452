import { Component, ElementRef, EventEmitter, OnInit, Output } from '@angular/core';
import { SopV2 } from 'app/shared/objects2/SopV2';
import { SopService2 } from 'app/shared/services2/Sop2.service';
import { ANIMATIONS, OPERATION_NAMES } from '../../shared/models/Constants';
import { Enum } from '../../shared/objects/Enum';
import { KitV2 } from '../../shared/objects2/KitV2';
import { QuoteV2 } from '../../shared/objects2/QuoteV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { KitService2 } from '../../shared/services2/Kit2.service';
import { QuoteService2 } from '../../shared/services2/Quote2.service';
import { ErrorUtil } from '../../shared/util/ErrorUtil';

enum SOP { none, approve }
enum KIT { none, review, approve }

@Component({
  selector: 'app-dashboard',
  providers: [KitService2, QuoteService2],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  animations: ANIMATIONS
})

export class DashboardComponent implements OnInit{
  @Output() routeMain = new EventEmitter();

  isQuoteLoading: boolean = false
  isSopLoading: boolean = false
  isKitLoading: boolean = false

  expSinceAndIn: number = 14;
  allQuotes = [];
  expiringQuotes = [];
  expiredQuotes = [];
  expiringQuotePanel: any
  expiredQuotePanel: any

  user: UserV2;
  timeNow: number;

  // For use in html
  _array = Array;
  TABLE = Enum.getDashboardRouteDestinations();
  SOP = SOP;
  KIT = KIT;

  showInventory = false;
  showImmunizingSchedule = false;
  showSOP = {show: false, type: SOP.none};
  showKIT = {show: false, type: KIT.none};
  showQuotes = false;
  showInvoice = false;

  // Route variables
  animateTable = this.TABLE.none;
  animateRow = -1;

  // Quotes variables
  quotes: QuoteV2[] = [];
  numQuotes: number;
  quotesReminderModel: QuoteV2[]; // sorted list of quotes: not complete and have valid expiry date
  quotesReminderTitle = 'Expire Soon';
  quotesReminderCurr: QuoteV2[] = []; // For UI
  quotesExpired: QuoteV2[] = []; // For UI, sorted list of expired quotes
  quotesRCollapsed = true;
  quotesECollapsed = true;

  // SOP variables
  sopsToApprove: SopV2[] = []; // For UI
  sopsRCollapsed = true;
  sopsACollapsed = true;

  // KIT variables
  kits: KitV2[] = [];
  kitsToReview: KitV2[] = []; // For UI
  kitsToApprove: KitV2[] = []; // For UI
  kitsRCollapsed = true;
  kitsACollapsed = true;

  constructor(private kitService: KitService2,
    private sopService: SopService2,
    private quoteService: QuoteService2,
    private auth: AuthenticationService2,
    private hostElement: ElementRef) { }

  ngOnInit () {
    this.timeNow = Date.now();

    // Check user rights to determine which views to activate
    this.enableViews().then(() => {
      // For each active view, load data and populate table/view
      if (this.showQuotes === true) {
        this.loadExpiredAndExpiringQuotes(this.expSinceAndIn, this.expSinceAndIn);
      }
      if (this.showSOP.show === true) {
        this.loadSOPData();
      }
      if (this.showKIT.show === true) {
        this.getKitsToReview().then(() => { this.displayKITView(); }).catch(error => {
          this.isKitLoading = false
          ErrorUtil.handleHttpError(error);
        });
      }
    });
  }

  // Animate row if user holds mouse down
  // If animation completes, route user to different view
  onClickTableData(table: number, dataRow: number) {
    this.animateTable = table;
    this.animateRow = dataRow;
  }

   /*************************/
   /*      Permissions      */
   /*************************/

   private enableViews() {
     return new Promise<void>((resolve) => {

       // Set default/lowest permission
       this.showInventory = true;
       this.showImmunizingSchedule = true;
       this.showSOP.show = false;
       this.showKIT.show = false;
       this.showQuotes = false;
       this.showInvoice = false;

       this.auth.getCurrentUser().then(_ => {
         this.user = UserV2.copy(_);

         if ( this.user.userRoles ) {
           this.showInventory = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_INVENTORY);
           this.showImmunizingSchedule = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_IMMUNIZING_SCHEDULES);
           this.showSOP.show = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_SOPS);
           if (UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.REVIEW_SOPS)) { this.showSOP.type = SOP.approve; }
           this.showKIT.show = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_KITS);
           if (UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.REVIEW_KITS)) { this.showKIT.type = KIT.review; }
           this.showQuotes = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_SALES);
           this.showInvoice = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_SALES);
         }
         resolve();
       });
     });
   }


   /*************************/
   /*       KIT View        */
   /*************************/

  /*************************/
  /*      Permissions      */
  /*************************/



  /*************************/
  /*       KIT View        */
  /*************************/

  private getKitsToReview() {
    this.isKitLoading = true
    return new Promise<void>((resolve) => {
      this.kitService.getKitsToReview().toPromise().then(kits => {
        this.kits = kits;
        resolve();
        this.isKitLoading = false
      });
    });
  }


  private displayKITView() {
    const reviewModel = [];
    this.kits.forEach(kit => {
       reviewModel.push(kit);
    });
    this.kitsToReview = reviewModel;
  }


  /*************************/
  /*       SOP View        */
  /*************************/

  private loadSOPData() {
    this.isSopLoading = true
    this.sopService.loadSopsInStatus( 'Entered' ).subscribe( sops => {
      this.sopsToApprove = sops;
      this.isSopLoading = false
    }, error => {
      this.isSopLoading = false
      ErrorUtil.handleHttpError( error );
    });
  }


  /*************************/
  /*      Quotes View      */
  /*************************/

  private loadExpiredAndExpiringQuotes(expired, expiring) {
    this.isQuoteLoading = true
    this.quoteService.loadExpiredAndExpiring(expired, expiring).toPromise().then(quotes => {
      // console.log(`Quotes for expired and expiring: expired(${expired}) | expiring(${expiring})`)
      // console.log(quotes)
      this.expiringQuotes = [];
      this.expiredQuotes = [];
      if(quotes.length > 0) {
        quotes.forEach((quote:any) => {
          if(new Date(quote.expirationDate).getTime() - new Date().getTime() < 0){
            // console.log(`-->(EXP:${new Date(quote.expirationDate).getTime()} - TODAY:${new Date().getTime()} = ${new Date(quote.expirationDate).getTime() - new Date().getTime()})`)
            // console.log(`-->Quote is expired within last ${expired} days`)
            // console.log(`-->expirationDate: ${quote.expirationDate}`)
            this.expiredQuotes.push(quote);
          }
          else{
            // console.log(`-->(EXP:${new Date(quote.expirationDate).getTime()} - TODAY:${new Date().getTime()})`)
            // console.log(`-->Quote is going to be expired within ${expiring} days`)
            // console.log(`-->expirationDate: ${quote.expirationDate}`)
            this.expiringQuotes.push(quote);
          }
        })
      }

      // if(this.expiringQuotes.length === 0){
      //   console.log('close expiring panel for empty list')
      //   this.togglePanel(this.expiringQuotePanel, 'expiring')
      // }

      // if(this.expiredQuotes.length === 0){
      //   console.log('close expired panel for empty list')
      //   this.togglePanel(this.expiredQuotePanel, 'expired')
      // }
      this.quotesRCollapsed = true
      this.quotesECollapsed = true

      this.isQuoteLoading = false
    }).catch(error => {
      this.isQuoteLoading = false
      ErrorUtil.handleHttpError(error);
    });
  }

  showQuotesReminder(expireInDays: number) {
    this.expSinceAndIn = expireInDays;
    this.loadExpiredAndExpiringQuotes(expireInDays, expireInDays);
  }

  // input_showQuotesReminder(event) {
  //   if (event && event.target && event.target.value && !isNaN(+event.target.value)) {
  //     this.expSinceAndIn = event.target.value;
  //     this.loadExpiredAndExpiringQuotes(event.target.value, event.target.value);
  //   }
  // }

  quoteSearch(timeSpan){
    this.loadExpiredAndExpiringQuotes(timeSpan, timeSpan);
  }

  togglePanel(panel, subType){
    switch(subType){
      case 'expiring':
        if(this.expiringQuotes.length === 0){
          this.quotesRCollapsed = true
        }else{
          this.quotesRCollapsed = !this.quotesRCollapsed
        }
        if(!this.quotesRCollapsed){
          panel.style.transition = 'all ease-out 0.3s'
          panel.style.height = 340 + 'px'
          panel.style.padding = 5 + 'px'
          panel.style['padding-left'] = 15 + 'px'
        }else{
          panel.style.transition = 'all ease-in 0.3s'
          panel.style.height = 0 + 'px'
          panel.style.padding = 0 + 'px'
        }
        break;

      case 'expired':
        if(this.expiredQuotes.length === 0){
          this.quotesECollapsed = true
        }else{
          this.quotesECollapsed = !this.quotesECollapsed
        }
        if(!this.quotesECollapsed){
          panel.style.padding = 5 + 'px'
          panel.style['padding-left'] = 15 + 'px'
          panel.style.transition = 'all ease-out 0.3s'
          panel.style.height = 340 + 'px'
        }else{
          panel.style.transition = 'all ease-in 0.3s'
          panel.style.height = 0 + 'px'
          panel.style.padding = 0 + 'px'
        }
        break;

       case 'sop':
        this.sopsACollapsed = !this.sopsACollapsed
        if(!this.sopsACollapsed){
          panel.style.padding = 5 + 'px'
          panel.style['padding-left'] = 15 + 'px'
          panel.style.transition = 'all ease-out 0.3s'
          panel.style.height = 340 + 'px'
        }else{
          panel.style.transition = 'all ease-in 0.3s'
          panel.style.height = 0 + 'px'
          panel.style.padding = 0 + 'px'
        }
        break;

        case 'kit':
        this.kitsACollapsed = !this.kitsACollapsed
        if(!this.kitsACollapsed){
          panel.style.padding = 5 + 'px'
          panel.style['padding-left'] = 15 + 'px'
          panel.style.transition = 'all ease-out 0.3s'
          panel.style.height = 340 + 'px'
        }else{
          panel.style.transition = 'all ease-in 0.3s'
          panel.style.height = 0 + 'px'
          panel.style.padding = 0 + 'px'
        }
        break;
    }

  }

  // Funtions for UI control
  getQuotePanelRef(expiringQuotePanel, expiredQuotePanel){
    this.expiringQuotePanel = expiringQuotePanel
    this.expiredQuotePanel = expiredQuotePanel
  }
}
