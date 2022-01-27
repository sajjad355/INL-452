import { Component, Input, Output, OnInit, OnChanges, SimpleChanges, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InvoiceV2, InvoiceLineItemV2, PrintedInvoice } from '../../shared/objects2/InvoiceV2';
import { ClientV2, ShippingAddressV2, ClientContactV2, BillingAddressV2 } from '../../shared/objects2/ClientV2';
import { QuoteV2 } from '../../shared/objects2/QuoteV2';
import { SettingV2, SettingValueV2 } from '../../shared/objects2/SettingV2';
import { SalesItemV2 } from '../../shared/objects2/SalesItemV2';
import { ProductV2 } from '../../shared/objects2/ProductV2';
import { QuoteService2 } from '../../shared/services2/Quote2.service';
import { image } from '../../shared/objects/Image';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { UserV2 } from '../../shared/objects2/UserV2';
import { INVOICE_TYPE } from '../../shared/models/Constants';
import { DatePipe } from '@angular/common';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import * as lodash from "lodash";


declare var jsPDF: any;
@Component({
  providers: [DatePipe],
  selector: 'app-invoice-display',
  templateUrl: './invoice-display.component.html',
  styleUrls: ['./invoice-display.component.scss']
})
export class InvoiceDisplayComponent implements OnInit, OnChanges {
  @Input() invoice: InvoiceV2;
  @Input() isNew: boolean;
  @Input() canManageSales: boolean;
  @Output() updateInvoice = new EventEmitter();
  @Output() cancel = new EventEmitter();

  @ViewChild('changeVersionNumberOption') changeVersionNumberOption: ElementRef;
  @ViewChild('includeQuoteDetailsOnInvoice') includeQuoteDetailsOnInvoice: ElementRef;
  changeVersionNumberModal:any;
  includeQuoteDetailsOnInvoiceModal: any;

  editingEnabled: boolean;
  originalInvoice : InvoiceV2;
  inputWarning: string[] = [];
  hideWarning = true;
  note: string;
  isLoading: boolean;
  currentUser : UserV2;
  currencyOptions= ['USD', 'CAD', 'EURO'];
  currencySigns= ['$', '$', '€'];
  versionNumber : number;
  myDatePickerOptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  createdDate : { date: { year: number, month: number, day: number } };

  // values retrieved from il_setting/il_setting_value
  paymentTypes: string[] = [];
  incoTerm: string[] = [];
  taxRates: SettingV2;
  invoiceOptions: string[] = [
    INVOICE_TYPE.PRODUCT_INVOICE,
    INVOICE_TYPE.SERVICE_INVOICE,
    INVOICE_TYPE.COMMERCIAL_INVOICE
  ];
  companyHeader: string[];
  companyPhone: string[];
  companyEmail: string[];
  companyHST: string[];
  companyAddress1: string[];
  companyAddress2: string[];
  companyAddress3: string[];
  companyCountry: string[];
  dateFormat = '';
  dateFormatDisplay: string;
  ourSalesRep: string;
  paymentInfo: string;



  constructor(private modalService: NgbModal,
              private quoteService: QuoteService2,
              private settingService: SettingService2,
              private datePipe: DatePipe,
              private authenticationService: AuthenticationService2
           ) { }

  ngOnInit() {
    let getcurrentuser = this.authenticationService.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
    this.loadSetting();
  }



  ngOnChanges(change: SimpleChanges) {
    if (change.invoice && this.invoice !== undefined) {
      this.isLoading = true;
      this.reset();
    }
  }

  ngDoCheck() {
    if (this.isLoading && !this.isNew ) {
        this.isLoading = false;
    }
  }

  loadSetting() {
    this.paymentTypes =  this.settingService.getSettingValuesAsArray( 'paymentTerms' );
    this.incoTerm = this.settingService.getSettingValuesAsArray( 'incoTerm' );
    this.taxRates = this.settingService.getSetting( 'tax' );
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.myDatePickerOptions.dateFormat=this.dateFormat;
    // this.myDatePickerOptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.companyHeader = this.settingService.getSettingValuesAsArray( 'companyName' );
    this.companyAddress1 = this.settingService.getSettingValuesAsArray( 'companyAddressLine1' );
    this.companyAddress2 = this.settingService.getSettingValuesAsArray( 'companyAddressLine2' );
    this.companyAddress3 = this.settingService.getSettingValuesAsArray( 'companyAddressLine3' );
    this.companyPhone = this.settingService.getSettingValuesAsArray( 'companyPhone' );
    this.companyEmail  = this.settingService.getSettingValuesAsArray( 'companyEmail' );
    this.companyHST = this.settingService.getSettingValuesAsArray( 'companyHST' );
    this.companyCountry = this.settingService.getSettingValuesAsArray( 'companyCountry' );

    const salesRepNameSetting: string[] = this.settingService.getSettingValuesAsArray( 'companySalesRepName' );
    if ( salesRepNameSetting ) { this.ourSalesRep = salesRepNameSetting[0]; } else { this.ourSalesRep = 'Not configured (companySalesRepName)' ; }
    const companyPaymentInfo : string[] = this.settingService.getSettingValuesAsArray( 'companyPaymentInfo');
    if ( companyPaymentInfo ) { this.paymentInfo = companyPaymentInfo[0]; } else { this.paymentInfo = 'Payment Info not configured (companyPaymentInfo)'; }

  }

  selectClient( client : ClientV2 ) {
    this.invoice.clientCompany = client;
  }

  selectContact(contact : ClientContactV2) {
    this.invoice.clientContact = contact;
  }

  selectBillingAddress(billingAddress : BillingAddressV2 ) {
    this.invoice.billingAddress = billingAddress;
  }

  selectShippingAddress(shippingAddress : ShippingAddressV2) {
    this.invoice.shippingAddress = shippingAddress;
  }

  selectShippingAttention(shippingAttention : string) {
    this.invoice.shippingAttention = shippingAttention;
  }

  selectBillingAttention(billingAttention : string ) {
    this.invoice.billingAttention = billingAttention;
  }

  selectQuote(quote : QuoteV2 ) {
    this.invoice.quote = quote;
    this.copyQuoteDetailsToInvoice();
    // this.includeQuoteDetailsOnInvoiceModal = this.modalService.open(this.includeQuoteDetailsOnInvoice, { backdrop: "static", size: 'lg' });
  }

  setLineItemSalesItem( index: number, salesItem : SalesItemV2 ) {
    if (!this.invoice || !this.invoice.lineItems) return;
    this.invoice.lineItems[index].salesItem = salesItem;
    this.invoice.lineItems[index].catalogNumber = salesItem.catalogNumber;
    this.invoice.lineItems[index].name = salesItem.name;
    this.invoice.lineItems[index].price = salesItem.unitPrice;
    this.invoice.lineItems[index].size = salesItem.packSize;
    let quantity: number = this.invoice.lineItems[index].itemQuantity;
    if ( isNaN( quantity ) ) { quantity = 1; }
    let discount: number = this.invoice.lineItems[index].itemDiscount;
    if ( isNaN( discount ) ) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    const totalPrice: number = discountPercentage * ( this.invoice.lineItems[index].price * quantity );
    this.invoice.lineItems[index].totalPrice = Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
  }


  open(content, modalName: string) {
    if (modalName == 'pdf') {
      this.note = '';
    }

    this.modalService.open(content).result.then((result) => {
    }, (reason) => {
    });
  }

  removeLine(index: number) {
    if (!this.invoice || !this.invoice.lineItems || !(this.invoice.lineItems.length > 0)) { return; }
    this.invoice.lineItems.splice(index, 1);
  }

  addInvoiceLine() {
    let lineItem : InvoiceLineItemV2 = new InvoiceLineItemV2();
    lineItem.editby = this.currentUser.name;
    lineItem.modifiedOn = new Date();
    lineItem.price = 0;
    lineItem.totalPrice = 0;
    lineItem.itemDiscount = 0;
    lineItem.itemQuantity = 1;
    lineItem.catalogNumber = '';
    lineItem.footNote = '';
    this.invoice.lineItems.push( lineItem );
  }




  copyQuoteDetailsToInvoice() {
    if ( !this.invoice || !this.invoice.quote ) return;

    if ( this.invoice.quote.clientCompany ) {
      this.invoice.clientCompany = ClientV2.copy( this.invoice.quote.clientCompany );
      this.invoice.billingAddress = this.invoice.clientCompany.billingAddress;
    }
    if ( this.invoice.quote.clientContact ) {
      this.invoice.clientContact = ClientContactV2.copy( this.invoice.quote.clientContact );
      this.invoice.billingAttention = this.invoice.clientContact.name;
      this.invoice.shippingAttention = this.invoice.clientContact.name;
    }
    if ( this.invoice.quote.shippingAddress )
      this.invoice.shippingAddress = ShippingAddressV2.copy( this.invoice.quote.shippingAddress );

    this.invoice.paymentType = this.invoice.quote.paymentType;
    this.invoice.shippingFee = this.invoice.quote.shippingFee;
    this.invoice.handlingFee = this.invoice.quote.handlingFee;
    this.invoice.tax = this.invoice.quote.tax;
    this.invoice.taxRate = this.invoice.quote.taxRate;
    this.invoice.revision = this.invoice.quote.revision;
    this.invoice.revisionDate = this.invoice.quote.revisionDate;

    this.invoice.quote.lineItems.forEach(lineItem => {
      let invLineItem : InvoiceLineItemV2 = new InvoiceLineItemV2();
      invLineItem.catalogNumber = lineItem.catalogNumber;
      invLineItem.editby = this.currentUser.name;
      invLineItem.footNote = lineItem.footNote;
      invLineItem.itemDiscount = lineItem.itemDiscount;
      invLineItem.itemQuantity = lineItem.itemQuantity;
      invLineItem.modifiedOn = new Date();
      invLineItem.name = lineItem.name;
      invLineItem.price = lineItem.price;
      invLineItem.salesItem = lineItem.salesItem;
      invLineItem.size = lineItem.size;
      invLineItem.totalPrice = lineItem.totalPrice;
      this.invoice.lineItems.push( invLineItem );
    });
  }



  setTax() {

    if (this.invoice.taxRate ) {
      let taxRateSetting : SettingValueV2 = this.taxRates.settingValues.find( x => x.value == this.invoice.taxRate );
      if ( taxRateSetting ) {
        try {
         let rate = parseInt( taxRateSetting.attribute );
         rate = rate / 100;
         let total = 0;
         this.invoice.lineItems.forEach(line => {
           let discountRate : number = 1 - (line.itemDiscount / 100);
           total += +((line.price * line.itemQuantity) * discountRate).toFixed(2);
         });
         this.invoice.tax = (Number(total) +
                             Number(this.invoice.handlingFee) +
                             Number(this.invoice.shippingFee)) * rate;

        }
        catch ( error ) {
          this.invoice.tax = 0;
          alert( 'Unable to calculate tax - tax rate settings not correctly configured - contact support');
        }
      }
    }
    else {
      this.invoice.tax = 0;
    }
  }

  setDiscount(lineIndex: number, discount: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;
    if (isNaN(discount)) discount = 0;
    if (discount < 0) discount = Math.abs(discount);
    this.invoice.lineItems[lineIndex].itemDiscount = discount;
    this.setTax();
  }

  discountChanged(lineIndex: number, discount: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    if (isNaN(discount)) { discount = 0; }
    if (discount < 0) { discount = Math.abs(discount); }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    let price: number = this.invoice.lineItems[lineIndex].price;
    if ( isNaN( price ) ) { price = 0; }
    let quantity: number = this.invoice.lineItems[lineIndex].itemQuantity;
    if ( isNaN( quantity )) { quantity = 1; }
    this.invoice.lineItems[lineIndex].itemDiscount = discount;
    const totalPrice: number = +(discountPercentage * ( price * quantity )).toFixed(2);
    this.invoice.lineItems[lineIndex].totalPrice = Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.setTax();
  }



  setHandlingFee(fee: number) {
    if (isNaN(fee)) {
      this.invoice.handlingFee = 0;
      return;
    }
    this.invoice.handlingFee = Number(fee);
  }

  setShippingFee(fee: number) {
    if (isNaN(fee)) {
      this.invoice.shippingFee = 0;
      return;
    }
    this.invoice.shippingFee = Number(fee);
  }

  setQuantity(lineIndex: number, amount: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    if (isNaN(amount)) amount = 1;
    if (amount < 1) amount = 1;
    this.invoice.lineItems[lineIndex].itemQuantity = amount;
    this.setTax();
  }

  quantityChanged(lineIndex: number, quantity: number) {
    if (!this.invoice ||
       !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    if (isNaN(quantity)) { quantity = 1; }
    if (quantity < 1) { quantity = 1; }
    let price: number = this.invoice.lineItems[lineIndex].price;
    if ( isNaN( price )) { price = 0; }
    let discount: number = this.invoice.lineItems[lineIndex].itemDiscount;
    if ( isNaN( discount )) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    this.invoice.lineItems[lineIndex].itemQuantity = quantity;
    const totalPrice: number = +(discountPercentage * ( price * quantity )).toFixed(2);
    this.invoice.lineItems[lineIndex].totalPrice =  Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.setTax();
  }

  totalPriceChanged(lineIndex: number, totalPrice: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    if (isNaN(totalPrice)) { totalPrice = 0; }
    if (totalPrice < 0) { totalPrice = Math.abs(totalPrice); }
    let price: number = this.invoice.lineItems[lineIndex].price;
    if ( isNaN( price )) { price = 0; }
    let quantity: number = this.invoice.lineItems[lineIndex].itemQuantity;
    if ( isNaN( quantity )) { quantity = 0; }
    const calculatedTotal =  ( price * quantity );
    const discountedAmount = Math.abs( calculatedTotal - totalPrice );
    const discountPercentage = discountedAmount / calculatedTotal  * 100;
    this.invoice.lineItems[lineIndex].totalPrice = totalPrice;
    this.invoice.lineItems[lineIndex].itemDiscount = Math.floor( discountPercentage * 100 ) / 100; // round to 2 decimals
    this.setTax();
  }

  unitPriceChanged( lineIndex: number, unitPrice: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    this.invoice.lineItems[lineIndex].price = unitPrice;
    let quantity = this.invoice.lineItems[lineIndex].itemQuantity;
    if (isNaN(quantity)) { quantity = 1; }
    if (quantity < 1) { quantity = 1; }
    let discount: number = this.invoice.lineItems[lineIndex].itemDiscount;
    if ( isNaN( discount )) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    const totalPrice: number = +(discountPercentage * ( unitPrice * quantity )).toFixed(2);
    this.invoice.lineItems[lineIndex].totalPrice =  Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.setTax();

  }

  changeCreatedDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.invoice.dateCreated = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.invoice.dateCreated = new Date(formatted);
    }
  }

  checkPay(pay: string) {
    if (pay == undefined || pay.trim() == '') {
      return;
    }

    this.paymentInfo = pay;
  }

  checkFootnote(note: string, lineIndex: number) {
    if (!this.invoice ||
        !this.invoice.lineItems ||
        this.invoice.lineItems.length < 1 ) return;

    if (note == undefined) { note = ''; }
    if (note.length > 255) { note = note.substr(0, 255); }
    this.invoice.lineItems[lineIndex].footNote = note;
  }

  validateString(val: string): boolean { return !(val == undefined || val.trim() == ''); }

  validate() {
    this.inputWarning = [];

    if (isNaN(parseInt(this.invoice.invoiceNumber))) {
      this.inputWarning.push( 'Invoice Number must be a valid number' );
    }
    if (!this.validateString(this.invoice.billingAttention)) {
      this.inputWarning.push( 'Billing Contact is required' );
    }
    if (!this.validateString(this.invoice.shippingAttention)) {
      this.inputWarning.push( 'Shipping Contact is required' );
    }
    if (!(this.invoice.billingAddress)) {
      this.inputWarning.push( 'Billing Address is required' );
    }
    if (!(this.invoice.shippingAddress)) {
      this.inputWarning.push( 'Shipping Address is required' );
    }
    if (!this.validateString(this.invoice.purchaseOrderNumber)) {
      this.inputWarning.push( 'PO Number is required' );
    }
    if (!(this.invoice.clientContact)) {
      this.inputWarning.push( 'Client Contact is required' );
    }
    if (!this.validateString(this.invoice.courier)) {
      this.inputWarning.push( 'Courier is required' );
    }
    if (!this.validateString(this.invoice.paymentType)) {
      this.inputWarning.push( 'Payment Type is required' );
    }
    if (!this.validateString(this.invoice.shippingTerm)) {
      this.inputWarning.push( 'Shipping Term is required' );
    }
    if (this.invoice.lineItems == undefined || this.invoice.lineItems.length < 1) {
      this.inputWarning.push( 'Please add at least one item to the invoice' );
    }
    if (isNaN(this.invoice.handlingFee)) {
      this.inputWarning.push( 'Handling fee must be a valid numeric value' );
    }
    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
      if (isNaN(this.invoice.numberPackages) || this.invoice.numberPackages < 1) {
        this.inputWarning.push( 'Number of packages is required and most be a positive number' );
      }
    }
    this.validateLineItems();

    if (this.inputWarning.length > 0 ) {
      this.hideWarning = false;
      setTimeout( () => {
        this.hideWarning = true;
      }, 5000);
      return false;
    }
    else {
      return true;
    }


  }

  validateLineItems() {
    this.invoice.lineItems.forEach(line => {
      if (!this.validateString(line.name)) {
        this.inputWarning.push( 'Must specify item for every invoice line item' );
      }
      if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
        if (!this.validateString(line.harmonizedSystemCode)) {
          this.inputWarning.push( 'All commercial invoice line items require an HS Code');
        }
        if (isNaN(line.packageWeight) || !(line.packageWeight > 0) ) {
          this.inputWarning.push( 'All commercial invoice line items require a positive, numeric package weight' );
        }
      }
    });
  }


  reset() {
    this.originalInvoice = InvoiceV2.copy( this.invoice );
    this.versionNumber = this.invoice.revision;
    const c = new Date(this.invoice.dateCreated);
    this.createdDate = { date: { year: c.getFullYear(), month: c.getMonth() + 1, day: c.getDate() } };
    if (this.isNew) {
      this.editingEnabled = true;
    } else {
      this.editingEnabled = false;
    }
    this.isLoading = false;
  }

  saveClicked() {
    if (!this.validate()) {
      return;
    }
    if (this.isNew) {
      this.save();
    }
    else {
      this.changeVersionNumberModal=this.modalService.open(this.changeVersionNumberOption, { backdrop: "static", size: 'lg' });
    }
  }

  revertVersionAndSave() {
    this.invoice.revision = this.versionNumber;
    this.save();
  }

  save() {
    this.setTax();
    this.invoice.editedBy = this.currentUser.name;
    this.invoice.modifiedOn = new Date();
    this.invoice.revisionDate = new Date();
    this.isNew = false;
    this.editingEnabled = false;
    this.updateInvoice.emit(this.invoice);
  }

  cancelEdit() {
    this.reset();
    this.editingEnabled = false;
    this.cancel.emit();
    this.originalInvoice = undefined;
  }


  getCompanyAddress() : string {
    let companyAddress : string = '';
    if ( this.companyAddress1.length == 1) { companyAddress += this.companyAddress1[0].trim() + "\n"; }
    if ( this.companyAddress2.length == 1) { companyAddress += this.companyAddress2[0].trim() + "\n"; }
    if ( this.companyAddress3.length == 1) { companyAddress += this.companyAddress3[0].trim() + "\n"; }
    if ( this.companyPhone.length == 1)    { companyAddress += this.companyPhone[0].trim() + "\n"; }
    if ( this.companyEmail.length == 1) { companyAddress += this.companyEmail[0].trim() + "\n"; }
    if ( this.companyHST.length == 1) { companyAddress += this.companyHST[0].trim() + "\n"; }
    return companyAddress;
  }

  getShippingAddress(sa: ShippingAddressV2) {
    let shippingAddress: string;
    shippingAddress = sa.locationName.trim() + '\n' + sa.addressLine1.trim() + '\n';
    if (sa.addressLine2 !== undefined && sa.addressLine2.trim() !== '') { shippingAddress += sa.addressLine2.trim() + '\n'; }
    shippingAddress += sa.city.trim() + '';
    if (sa.province !== undefined && sa.province.trim() !== '') { shippingAddress += ',' + sa.province.trim(); }
    shippingAddress += '\n' + sa.country.trim();
    if (sa.postalCode !== undefined && sa.postalCode.trim() !== '') { shippingAddress += '  ' + sa.postalCode.trim() + '\n'; }
    return shippingAddress;
  }

  getFormattedAddress(c: ClientV2) {
    let billingAddress: string;
    billingAddress = c.companyName.trim() + '\n' + c.billingAddress.addressLine1.trim() + '\n';
    if (c.billingAddress.addressLine2 !== undefined && c.billingAddress.addressLine2.trim() !== '') {
      billingAddress += c.billingAddress.addressLine2.trim() + '\n';
    }
    billingAddress += c.billingAddress.city.trim() + '';
    if (c.billingAddress.province !== undefined && c.billingAddress.province.trim() !== '') {
      billingAddress += ',' + c.billingAddress.province.trim();
    }
    billingAddress += '\n' + c.billingAddress.country.trim();
    if (c.billingAddress.postalCode !== undefined && c.billingAddress.postalCode.trim() !== '') {
      billingAddress += '  ' + c.billingAddress.postalCode.trim() + '\n';
    }
    return billingAddress;
  }

  async arrayCopy(originalArr){
    return await lodash.cloneDeep(originalArr)
  }

  getPDF() {
    const arrayCopy = this.arrayCopy
    const doc = new jsPDF();
    const imgData = image.logo;
    const width = doc.internal.pageSize.getWidth();
    const height = doc.internal.pageSize.getHeight();
    const margin = 5;
    const emptyHead = [{ title: '', dataKey: 'header' }, { title: '', dataKey: 'content' }];
    doc.addImage(imgData, 'JPEG', margin, 10, 60, 23);
    const headMargin = (((width - (margin * 2)) / 3) * 2) + margin;
    const modificationDate = this.datePipe.transform(this.invoice.revisionDate, this.dateFormatDisplay);
    const creationDate = this.datePipe.transform(this.invoice.dateCreated, this.dateFormatDisplay);
    const headData = [];
    if (this.invoice.proforma ) { headData.push({ header: 'PROFORMA', content: '' }); }

    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
      headData.push({ header: 'COMMERCIAL INVOICE #', content: this.invoice.invoiceNumber });
    }
    else {
      headData.push({ header: 'INVOICE #', content: this.invoice.invoiceNumber });
    }
    headData.push({ header: 'Version #', content: this.invoice.revision });
    if (this.invoice.revision > 1) {
      headData.push({ header: 'Modify Date:', content: modificationDate });
    }
    headData.push({ header: 'Date Created', content:  creationDate});

    doc.autoTable(emptyHead, headData, {
      theme: 'plain',
      styles: {
        cellPadding: 0,
        fontSize: 9,
        textColor: [0, 132, 183]
      },
      headerStyles: {
        fontStyle: 'normal',
        fontSize: 0
      },
      columnStyles: {
        header: {
          fontStyle: 'bold'
        }
      },
      startY: 13, // false (indicates margin top value) or a number
      pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      tableWidth: (width - (2 * margin)) / 3, // 'auto', 'wrap' or a number,
      tableLineColor: 200, // number, array (see color section below)
      tableLineWidth: 0,
      showHeader: 'never',
      margin: headMargin,
    });

    doc.setDrawColor(151, 202, 65);
    doc.lines([[width - (2 * margin), 0]], margin, 35);
    let linePointer = 36;
    let nextPointer = 36;

    const col1Margin = margin;
    const col2Margin = margin + (width - (2 * margin)) / 3;
    const col3Margin = margin + 2 * ((width - (2 * margin)) / 3);

    let clientBillingAddress = '';
    if ( this.invoice.clientCompany )
      clientBillingAddress  = this.getFormattedAddress( this.invoice.clientCompany );

    let clientShippingAddress = '';
    if ( this.invoice.shippingAddress )
      clientShippingAddress = this.getShippingAddress( this.invoice.shippingAddress );

    let companyAddress : string = this.getCompanyAddress();

    doc.autoTable(['Bill To:'], [
      [clientBillingAddress],
      ['ATTN: ' + this.invoice.billingAttention]
    ], {
        theme: 'plain',
        styles: {
          fontSize: 9,
          textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col1Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2));
        }
      });


    const shipInfo: string[][] = [
      [clientShippingAddress],
      ['ATTN: ' + this.invoice.shippingAttention + "\n"]
    ];
    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE) {
      let destinationCountry = 'N/A';
      if ( this.invoice.billingAddress.country ) destinationCountry = this.invoice.billingAddress.country
      shipInfo.push( ['Ultimate Destination: ' + destinationCountry.toUpperCase() ] )
    }

    doc.autoTable(['Ship To:'], shipInfo, {
        theme: 'plain',
        styles: {
          fontSize: 9,
          textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col2Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2));
          if (row.raw[0].startsWith( "Ultimate Destination:") ) {
            row.cells[0].styles.fontStyle = 'bold';
          }
        }
      });


    const companyAddressInfo : string[][] = [ [companyAddress]];
    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
      let shipFromCountry : string = "N/A";
      if (this.companyCountry ) shipFromCountry = this.companyCountry[0];
      companyAddressInfo.push( ['Shipped from: ' + shipFromCountry.toUpperCase() ]  )
    }

    doc.autoTable(
        [ this.companyHeader + ' Info:'], companyAddressInfo,
        {
          theme: 'plain',
          styles: {
            fontSize: 9,
            textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col3Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2));
          if (row.raw[0].startsWith( "Shipped from:") ) {
            row.cells[0].styles.fontStyle = 'bold';
          }
        }
      });



    let salesRep = this.ourSalesRep;
    linePointer = nextPointer + 10;
    const head = ['SALESPERSON', 'P.O. NUMBER', 'REQUISITIONER', 'SHIP VIA', 'PAYMENT TERMS', 'SHIPPING TERMS'];
    const data = [
      [salesRep,
      this.invoice.purchaseOrderNumber,
      this.invoice.clientContact.name,
      this.invoice.courier,
      this.invoice.paymentType,
      this.invoice.shippingTerm]
    ];

    doc.autoTable(head, data, {
      theme: 'plain',
      styles: {
        halign: 'left',
        valign: 'middle',
        font: 'times',
        fontSize: 9,
        textColor: 0,
        overflow: 'linebreak',
      },
      headerStyles: {
        fillColor: [224, 234, 207]
      },
      // Properties
      startY: linePointer, // false (indicates margin top value) or a number
      pageBreak: 'auto', // 'auto', 'avoid' or 'always'
      tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
      tableLineColor: [151, 202, 65], // number, array (see color section below)
      tableLineWidth: 0.1,
      margin: margin,
      drawRow: function (row, data) {
        linePointer = row.y + row.height;
      }
    });

    const invoiceData: PrintedInvoice = this.getDataFromLink();
    const header: { title: string, dataKey: string }[] = [];


    if (this.invoice.type == INVOICE_TYPE.SERVICE_INVOICE ){
      // service invoice
      header.push({ title: 'Description', dataKey: 'description' });
      header.push({ title: 'Qty', dataKey: 'quantity' });
      header.push({ title: 'List Price', dataKey: 'listPrice' });
      if (invoiceData.hasDiscount) {
        header.push({ title: 'Disc.', dataKey: 'discount' });
        header.push({ title: 'Your Price', dataKey: 'adjustedPrice' });
      }
      header.push({ title: 'Extended', dataKey: 'totalPrice' });
    }
    else if (this.invoice.type == INVOICE_TYPE.PRODUCT_INVOICE ) {
      header.push( { title: 'Catalog #', dataKey: 'category' } );
      header.push( { title: 'Name', dataKey: 'description' });
      header.push( { title: 'Qty', dataKey: 'quantity' } );
      if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
        header.push( { title: '~ Weight (lbs)', dataKey: 'packageWeight' } );
      }
      header.push( { title: 'List Price', dataKey: 'listPrice' } );
      if (invoiceData.hasDiscount) {
        header.push( { title: 'Disc.', dataKey: 'discount' });
        header.push( { title: 'Your Price', dataKey: 'adjustedPrice' } );
      }
      header.push( { title: 'Extended', dataKey: 'totalPrice' });
    }
    else if (this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
      header.push( { title: 'Catalog #', dataKey: 'category' } );
      header.push( { title: 'Name', dataKey: 'description' });
      header.push( { title: 'Qty', dataKey: 'quantity' } );
      header.push( { title: '~ Weight (lbs)', dataKey: 'packageWeight' } );
      header.push( { title: 'Value Per Unit (USD)', dataKey: 'adjustedPrice' } );
      header.push( { title: 'Value (USD)', dataKey: 'totalPrice' });
    }





    doc.autoTable(header, invoiceData.data, {
      theme: 'grid', // 'striped', 'grid' or 'plain'
      styles: {
        halign: 'center',
        valign: 'middle',
        font: 'times',
        fontSize: 9,
        textColor: 0,
        overflow: 'linebreak',
        lineColor: [151, 202, 65],
        fillColor: 255
      },
      columnStyles: {
        quantity: { columnWidth: 10, halign: 'center' },
        description: { columnWidth: 'auto', halign: 'left', fillColor: false },
        discount: { columnWidth: 20, halign: 'center' },
        category: { columnWidth: 30, halign: 'center' },
        listPrice: { columnWidth: 20, halign: 'center' },
        totalPrice: { columnWidth: 25, halign: 'center' },
        adjustedPrice: { columnWidth: 20, halign: 'center' },
        packageWeight: { columnWidth: 20, halign: 'center' },
      },
      headerStyles: {
        fillColor: [151, 202, 65]
      },
      startY: linePointer, // false (indicates margin top value) or a number
      pageBreak: 'auto', // 'auto', 'avoid' or 'always'
      tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
      tableLineColor: [151, 202, 65], // number, array (see color section below)
      tableLineWidth: 0,
      margin: margin,
      drawRow: function (row, data) {
        linePointer = row.y + row.height;

        if (row.cells.adjustedPrice) {
          row.cells.adjustedPrice.styles.columnWidth = 'wrap';
          row.cells.adjustedPrice.styles.halign = 'right';
        }
        if (row.cells.discount) {
          row.cells.discount.styles.columnWidth = 'wrap';
          row.cells.discount.styles.halign = 'center';
        }
      }
    });

    isNaN(this.invoice.tax) ? this.invoice.tax = 0 : '';
    isNaN(this.invoice.handlingFee) ? this.invoice.handlingFee = 0 : '';

    const totalHead = [{ title: '', dataKey: 'text' }, { title: '', dataKey: 'amount' }];
    const totalData = [];
    let rowcount = 0;


    if (invoiceData.hasDiscount && (this.invoice.type != INVOICE_TYPE.COMMERCIAL_INVOICE)) {
      totalData.push({ text: 'List Total Price:', amount: '$' + this.formatMoney(invoiceData.totalListPrice) });
      totalData.push({ text: 'Discount Total:', amount: '$' + this.formatMoney(invoiceData.totalDiscount) });
      rowcount += 2;
    }
    totalData.push({ text: 'Subtotal:', amount: '$' + this.formatMoney(invoiceData.totalPrice) });

    const estimatedPrefix = (this.invoice.proforma ) ? 'Estimated ' : '';
    let shippingAndHandlingText: string;
    let shippingAndHandlingFee: number;
    // Specifically looking for if the shipping and handling fees are undefined instead of checking the populated status based
    // on the variable value which could misdirect the logic of the conditional statement below
    if (this.invoice.handlingFee != undefined && this.invoice.shippingFee != undefined) {
      shippingAndHandlingText = estimatedPrefix + 'Shipping and Handling Fee:';
      shippingAndHandlingFee = this.invoice.handlingFee + this.invoice.shippingFee;
    }
    if (this.invoice.handlingFee != undefined  && (this.invoice.shippingFee == undefined || this.invoice.shippingFee == 0) ) {
      shippingAndHandlingText = estimatedPrefix + 'Handling Fee:';
      shippingAndHandlingFee = this.invoice.handlingFee;
    }
    if (this.invoice.shippingFee != undefined  && (this.invoice.handlingFee == undefined || this.invoice.handlingFee == 0) ) {
      shippingAndHandlingText = estimatedPrefix + 'Shipping Fee:';
      shippingAndHandlingFee = this.invoice.shippingFee;
    }
    if ( shippingAndHandlingFee ) {
      totalData.push({ text: shippingAndHandlingText,
                      amount: '$' + this.formatMoney(shippingAndHandlingFee) });
      rowcount += 1;
    }


    if (this.invoice.tax !== 0) {
      totalData.push({ text: 'Tax:',
                      amount: '$' + this.formatMoney(this.invoice.tax) });
      rowcount += 1;
    }
    const invoiceAmt = invoiceData.totalPrice +
                    this.invoice.handlingFee +
                    this.invoice.shippingFee  +
                    this.invoice.tax;
    totalData.push({ text: 'Total (USD):',
                    amount: '$' + this.formatMoney( invoiceAmt )});
    rowcount += 1;

    if (this.invoice.currency && this.invoice.currency != 'USD' && this.invoice.currencyRate) {
      const currencyIndex = this.currencyOptions.indexOf(this.invoice.currency);
      totalData.push({ text: 'Currency Rate (' + this.invoice.currency + '):',
                      amount: this.formatMoney(this.invoice.currencyRate) });
      rowcount += 1;
      totalData.push({ text: 'Total (' + this.invoice.currency + '):',
                      amount: this.currencySigns[currencyIndex]
                              + this.formatMoney( invoiceAmt * this.invoice.currencyRate  ) });
      rowcount += 1;
    }

    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE) {
      totalData.push({ text: 'Approximate Total Commodity Weight (lb):', amount: invoiceData.totalPackageWeight });
      rowcount += 1;
      totalData.push({ text: 'Total Number of Packages:', amount: invoiceData.numberPackages });
      rowcount += 1;
    }


    doc.autoTable(totalHead, totalData, {
      theme: 'plain',
      styles: {
        halign: 'right',
        valign: 'middle',
        font: 'times',
        fontSize: 9,
        textColor: 0,
        overflow: 'linebreak',
      },
      columnStyles: {
        text: {
          columnWidth: 2 * ((width - (2 * margin)) / 3),
          cellPadding: 0
        },
        amount: {
          columnWidth: ((width - (2 * margin)) / 3)
        }
      },
      // Properties
      startY: linePointer, // false (indicates margin top value) or a number
      pageBreak: 'auto', // 'auto', 'avoid' or 'always'
      tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
      tableLineColor: [151, 202, 65], // number, array (see color section below)
      tableLineWidth: 0.1,
      showHeader: 'never',
      margin: margin,
      drawRow: function (row, data) {
        linePointer = row.y + row.height;
        if (row.raw.text.startsWith( "Total (") ) {
          row.cells.text.styles.fontStyle = 'bold';
          row.cells.amount.styles.fontStyle = 'bold';
        }
        if (row.index % 2 == 0) {
          row.cells.text.styles.fillColor = [224, 234, 207];
          row.cells.amount.styles.fillColor = [224, 234, 207];
        }
      }
    });


    doc.setFontSize(8);
    linePointer += 8;
    if (this.invoice.note !== null && this.invoice.note.trim() !== '') {
      doc.setTextColor(0, 132, 183);
      doc.setFontStyle('bold');
      doc.text('Please Note: ', margin, linePointer);
      doc.setFontSize(8);
      doc.setFontStyle('italic');
      const n = doc.splitTextToSize(this.invoice.note, 150);
      doc.text(n, margin + 30, linePointer);
      linePointer += (5 * n.length) - 1;
    }

    if ( (height - linePointer)  < 50 ) {
      doc.addPage();
      linePointer = 15;
    }
    doc.setTextColor(100);
    doc.setFontStyle('bold');
    doc.text('Payment Terms: ', margin, linePointer);

    doc.setFontSize(8);
    doc.setFontStyle('normal');
    doc.text(this.invoice.paymentType, margin + 30, linePointer);

    if ( this.invoice.type == INVOICE_TYPE.COMMERCIAL_INVOICE ) {
      linePointer+= 10;
      // const salesRepHead : string[] = ['This information is true and correct.'];
      // const salesRepData : string[][] = [['Signature: ' + salesRep],
      //                                    ['Title: Account Manager'],
      //                                    ['Date: ' + creationDate ]];

      // doc.autoTable(salesRepHead, salesRepData, {
      //   theme: 'plain',
      //   styles: {
      //     halign: 'left',
      //     valign: 'middle',
      //     font: 'times',
      //     fontSize: 9,
      //     textColor: 0,
      //     overflow: 'linebreak',
      //   },
      //   headerStyles: {
      //     fillColor: [224, 234, 207]
      //   },
      //   // Properties
      //   startY: linePointer, // false (indicates margin top value) or a number
      //   pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      //   tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
      //   tableLineColor: [151, 202, 65], // number, array (see color section below)
      //   tableLineWidth: 0.1,
      //   margin: margin,
      //   drawRow: function (row, data) {
      //     linePointer = row.y + row.height;
      //   }
      // });
      //doc.setDrawColor(151, 202, 65);
      linePointer += 5;
      doc.line( margin, linePointer, width - (2 * margin), linePointer);
      linePointer += 5;
      doc.text('This information is true and correct.', margin, linePointer);
      linePointer += 5;
      doc.text('Signature: ' + salesRep, margin, linePointer);
      linePointer += 5;
      doc.text('Title: Account Manager', margin, linePointer);
      linePointer += 5;
      doc.text('Date: ' + creationDate, margin, linePointer);
      linePointer += 5;
      var img = new Image();
      img.src = 'assets/images/accountRepESig.png';
      doc.addImage(img, 'png', margin, linePointer, 50, 10);
      linePointer += 10;
      doc.line( margin, linePointer, width - (2 * margin), linePointer);

    }
    else {
      linePointer += 5;
      doc.setFontSize(9);
      doc.setFontStyle('bold');
      doc.text('Make all payments to: ', margin, linePointer);
      linePointer += 4;
      doc.setFontSize(8);
      doc.setFontStyle('normal');
      const payLines = this.paymentInfo.split('\n');
      payLines.forEach(line => {
        const lt = doc.splitTextToSize(line, width - (2 * margin));
        doc.text(lt, margin, linePointer);
        linePointer += (4 * lt.length) - ((lt.length - 1));
      });

      const footerHead = [''];
      const footerData = [
        ['Accounts not paid within 30 days of the date of the invoice are subject to a 5% monthly finance charge.\n If you have any questions concerning this invoice, please contact our sales team. sales@somrubioscience.com'],
        ['THANK YOU FOR YOUR BUSINESS!']
      ];
      doc.autoTable(footerHead, footerData, {
        theme: 'plain', // 'striped', 'grid' or 'plain'
        styles: {
          halign: 'center',
          valign: 'middle',
          font: 'times',
          fontSize: 9,
          overflow: 'linebreak',
          fillColor: false,
          textColor: 0,
          fontStyle: 'italic'
        },
        // Properties
        showHeader: 'never',
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: width - (4 * margin), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: [0, margin],
        drawCell: function (cell, data) {
          const rows = data.table.rows;
          if (data.row.index == rows.length - 1) {
            doc.setFontStyle('bold');
            doc.setFontSize(10);
          }
        }
      });
    }
    this.assignPageNum(doc);
    doc.save( this.companyHeader + 'Invoice ' + this.invoice.invoiceNumber + ' .pdf');
  }


  assignPageNum(doc: any) {
    const totalPages = doc.internal.getNumberOfPages();
    const timestamp = new Date().toString();
    for (let i = 1; i <= totalPages; i++) {
      doc.setPage(i);
      doc.setFontStyle('bold');
      doc.text(200, 295, i + ' of ' + totalPages);
    }
  }



  getDataFromLink(): PrintedInvoice {
    if (!this.invoice || !this.invoice.lineItems) { return; }
    const printedInvoice: PrintedInvoice = new PrintedInvoice();

    let totalDiscount = 0;
    let totalPrice = 0;
    let totalListPrice = 0;
    let hasDiscCount = 0;
    let totalPackageWeight = 0;
    this.invoice.lineItems.forEach(line => {
      if (line.itemDiscount > 0) { hasDiscCount++; }
      const discountAmount = line.price * (line.itemDiscount / 100);
      totalDiscount += discountAmount * line.itemQuantity;
      totalListPrice += line.price * line.itemQuantity;
      totalPrice += line.totalPrice;
      if ( line.packageWeight ) totalPackageWeight += line.packageWeight;
      const adjustedPrice = line.price * (1 - (line.itemDiscount / 100));
      let lineDescription : string = 'Name: ' + line.name;

      // size field on PDF:
      // for Product - take from unit/unitSize
      // for Kit take from packSize that has been copied over to line.size
      let unitSize = line.salesItem['unitSize'];
      let unit = line.salesItem['unit'];
      if ( unit && unitSize ) {
        lineDescription += '\nSize: ' + unitSize + ' ' + unit;
      }
      else {
        if ( (line.size != undefined) && (line.size.trim().length > 0) )
          lineDescription += '\nSize: ' + line.size;
      }

      if ( (line.footNote != undefined) && (line.footNote.trim().length > 0) )
        lineDescription += '\nNote: ' + line.footNote;
      if ( (line.harmonizedSystemCode != undefined) && (line.harmonizedSystemCode.trim().length > 0) )
        lineDescription += '\nHS Code: ' + line.harmonizedSystemCode;





      printedInvoice.data.push({
        category: line.catalogNumber,
        description: lineDescription,
        quantity: line.itemQuantity,
        packageWeight : (line.packageWeight !== undefined ? line.packageWeight : 0),
        listPrice: '$' + this.formatMoney(line.price),
        discount: line.itemDiscount + '%',
        adjustedPrice: '$' + this.formatMoney(adjustedPrice),
        totalPrice: '$' + this.formatMoney(line.totalPrice)
      });
    });

    printedInvoice.totalPrice = totalPrice;
    printedInvoice.totalDiscount = totalDiscount;
    printedInvoice.totalListPrice = totalListPrice;
    printedInvoice.hasDiscount = hasDiscCount > 0;
    printedInvoice.totalPackageWeight = totalPackageWeight;
    printedInvoice.numberPackages = this.invoice.numberPackages;

    return printedInvoice;
  }



  formatMoney(val: number): string {
    if ( !val ) { return '0.0'; }
    let format = val.toFixed(2);
    format = format.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    return format;
  }


}
