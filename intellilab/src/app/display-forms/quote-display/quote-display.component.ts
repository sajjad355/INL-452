import { Component, Input, Output, OnInit, OnChanges, SimpleChanges, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { DatePipe } from '@angular/common';
import { StringUtils } from 'app/shared/util/StringUtils';
import { SalesItemV2 } from '../../shared/objects2/SalesItemV2';
import { QuoteV2, QuoteLineItemV2, PrintedQuote } from '../../shared/objects2/QuoteV2';
import { image } from '../../shared/objects/Image';
import { UserV2 } from '../../shared/objects2/UserV2';
import { SettingV2, SettingValueV2 } from '../../shared/objects2/SettingV2';
import { ClientContactV2, ClientV2, ShippingAddressV2 } from '../../shared/objects2/ClientV2';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';




declare var jsPDF: any;


@Component({
  providers: [DatePipe,
              AuthenticationService2],
  selector: 'app-quote-display',
  templateUrl: './quote-display.component.html',
  styleUrls: ['./quote-display.component.scss']
})
export class QuoteDisplayComponent implements OnInit, OnChanges {
  @Input() quote: QuoteV2;
  @Input() isNew: boolean;
  @Input() canManageSales: boolean;
  @Output() updateQuote = new EventEmitter();
  @Output() cancel = new EventEmitter();

  originalQuote : QuoteV2;
  myDatePickerOptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  createdDate : { date: { year: number, month: number, day: number } };
  expirationDate : { date: { year: number, month: number, day: number } };
  paymentTypes: string[];
  ourSalesRep: string;
  message: string;
  termAndCond: string;
  isInnoProgram: boolean;
  note: string;
  isLoading: boolean;
  editingEnabled: boolean;
  currentUser: UserV2;

  includeFeeAndTax: boolean;

  currencyOptions = ['USD', 'CAD', 'EURO'];
  currencySigns = ['$', '$', '€'];
  companyHeader: string[];
  companyPhone: string[];
  companyEmail:  string[];
  companyHST: string[];
  companyAddress1: string[];
  companyAddress2: string[];
  companyAddress3: string[];
  taxRates: SettingV2;

  changeVersionNumberModal:any;
  @ViewChild('changeVersionNumberOption') changeVersionNumberOption: ElementRef;
  versionNumber : number;

  dateFormat = '';
  dateFormatDisplay: string;

  constructor(
    private modalService: NgbModal,
    private settingService: SettingService2,
    private datePipe: DatePipe,
    private authenticationService: AuthenticationService2
  ) { }

  ngOnInit() {
    this.loadCurrentUser();
    this.loadSetting();
  }

  loadCurrentUser() {
    let getcurrentuser = this.authenticationService.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentUser = UserV2.copy(_);
      });
    }
  }

  loadSetting() {
    this.paymentTypes = this.settingService.getSettingValuesAsArray('paymentTerms');
    this.taxRates = this.settingService.getSetting( 'tax' );
    this.dateFormat = this.settingService.getDateFormat(DateFormatType.DatePickerUsage);
    this.myDatePickerOptions.dateFormat = this.dateFormat;
    // this.myDatePickerOptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat(DateFormatType.UserDisplay);
    this.companyHeader = this.settingService.getSettingValuesAsArray('companyName');
    this.companyPhone = this.settingService.getSettingValuesAsArray('companyPhone');
    this.companyEmail = this.settingService.getSettingValuesAsArray('companyEmail');
    this.companyHST = this.settingService.getSettingValuesAsArray('companyHST');
    this.companyAddress1 = this.settingService.getSettingValuesAsArray('companyAddressLine1');
    this.companyAddress2 = this.settingService.getSettingValuesAsArray('companyAddressLine2');
    this.companyAddress3 = this.settingService.getSettingValuesAsArray('companyAddressLine3');
    const salesRepNameSetting: string[] = this.settingService.getSettingValuesAsArray('companySalesRepName');
    if (salesRepNameSetting) {
      this.ourSalesRep = salesRepNameSetting[0];
    } else {
      this.ourSalesRep = 'Not configured';
    }
  }


  ngOnChanges(change: SimpleChanges) {
    if (change.quote && this.quote !== undefined) {
      this.reset();
    }
  }

  ngOnDestroy() {
  }

  open(content, modalName: string) {
    if (modalName == 'pdf') {
      const tac = new TermsAndCondition(this.quote.paymentType);
      this.termAndCond = tac.tac;
      this.isInnoProgram = false;
    }

    this.modalService.open(content).result.then((result) => {
    }, (reason) => {
    });
  }

  reset() {
    this.originalQuote = QuoteV2.copy( this.quote );
    if (this.isNew) {
      this.editingEnabled = true;
      this.isLoading = false;
    } else {
      this.editingEnabled = false;
      if (this.quote.shippingFee != 0 || this.quote.handlingFee != 0) {
        this.includeFeeAndTax = true;
      }
    }
    this.versionNumber = this.quote.revision;
    const c = new Date(this.quote.createdDate);
    const e = new Date(this.quote.expirationDate);
    this.createdDate = { date: { year: c.getFullYear(), month: c.getMonth() + 1, day: c.getDate() } };
    this.expirationDate = { date: { year: e.getFullYear(), month: e.getMonth() + 1, day: e.getDate() } };
  }


  removeLine(index: number) {
    if ( this.quote.lineItems.length < 1 ) { return; }
    this.quote.lineItems.splice(index, 1);
  }

  addQuoteLine() {
    let lineItem : QuoteLineItemV2 = new QuoteLineItemV2();
    lineItem.editby = this.currentUser.name;
    lineItem.modifiedOn = new Date();
    lineItem.price = 0;
    lineItem.totalPrice = 0;
    lineItem.itemDiscount = 0;
    lineItem.itemQuantity = 1;
    lineItem.catalogNumber = '';
    lineItem.footNote = '';
    this.quote.lineItems.push( lineItem );
  }

  setLineItemSalesItem( index: number, salesItem : SalesItemV2 ) {
    if (!this.quote || !this.quote.lineItems) return;
    this.quote.lineItems[index].salesItem = salesItem;
    this.quote.lineItems[index].catalogNumber = salesItem.catalogNumber;
    this.quote.lineItems[index].name = salesItem.name;
    this.quote.lineItems[index].price = salesItem.unitPrice;
    this.quote.lineItems[index].size = salesItem.packSize;
    let quantity: number = this.quote.lineItems[index].itemQuantity;
    if ( isNaN( quantity ) ) { quantity = 1; }
    let discount: number = this.quote.lineItems[index].itemDiscount;
    if ( isNaN( discount ) ) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    const totalPrice: number = discountPercentage * ( this.quote.lineItems[index].price * quantity );
    this.quote.lineItems[index].totalPrice = Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
  }

  updateLineItemMetaData(linkIndex: number){
    if(this.quote && this.quote.lineItems.length > 0){
      this.quote.lineItems[linkIndex].editby = this.currentUser.name;
      this.quote.lineItems[linkIndex].modifiedOn = new Date();
      console.log(`Line item (${linkIndex})'s metadata is updated`)
      console.log(this.quote.lineItems[linkIndex])
    }
  }

  discountChanged(linkIndex: number, discount: string) {
    if ( isNaN(Number(discount)) ) { discount = '0'; }
    if ( Number(discount) < 0 ) { discount = Math.abs(Number(discount)).toString(); }
    const discountPercentage: number = Number(discount) > 100 ? (Number(discount) / 100) :  (1 - Number(discount) / 100 );
    let price: number = this.quote.lineItems[linkIndex].price;
    if ( isNaN( price ) ) { price = 0; }
    let quantity: number = this.quote.lineItems[linkIndex].itemQuantity;
    if ( isNaN( quantity ) ) { quantity = 1; }
    this.quote.lineItems[linkIndex].itemDiscount = Number(discount);
    const totalPrice: number = +(discountPercentage * ( price * quantity )).toFixed(2);
    this.quote.lineItems[linkIndex].totalPrice = Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.updateLineItemMetaData(linkIndex)
  }

  totalPriceChanged(linkIndex: number, totalPrice: string) {
    if ( isNaN(Number(totalPrice)) ) { totalPrice = '0'; }
    if ( Number(totalPrice) < 0 ) { totalPrice = Math.abs(Number(totalPrice)).toString(); }
    let price: number = this.quote.lineItems[linkIndex].price;
    if ( isNaN( price ) ) { price = 0; }
    let quantity: number = this.quote.lineItems[linkIndex].itemQuantity;
    if ( isNaN( quantity ) ) { quantity = 0; }
    const calculatedTotal =  ( price * quantity );
    const discountedAmount = Math.abs( calculatedTotal - Number(totalPrice) );
    const discountPercentage = discountedAmount / calculatedTotal  * 100;
    this.quote.lineItems[linkIndex].totalPrice = Number(totalPrice);
    this.quote.lineItems[linkIndex].itemDiscount = Math.floor( discountPercentage * 100 ) / 100; // round to 2 decimals
    this.updateLineItemMetaData(linkIndex)
  }

  quantityChanged(linkIndex: number, quantity: string) {
    if ( isNaN(Number(quantity)) ) { quantity = '1'; }
    if ( Number(quantity) < 1 ) { quantity = '1'; }
    let price: number = this.quote.lineItems[linkIndex].price;
    if ( isNaN( price ) ) { price = 0; }
    let discount: number = this.quote.lineItems[linkIndex].itemDiscount;
    if ( isNaN( discount ) ) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    this.quote.lineItems[linkIndex].itemQuantity = Number(quantity);
    const totalPrice: number = +(discountPercentage * ( price * Number(quantity) )).toFixed(2);
    this.quote.lineItems[linkIndex].totalPrice =  Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.updateLineItemMetaData(linkIndex)
  }

  unitPriceChanged( lineIndex: number, unitPrice: number) {
    if (!this.quote ||
        !this.quote.lineItems ||
         this.quote.lineItems.length < 1 ) return;

    this.quote.lineItems[lineIndex].price = unitPrice;
    let quantity = this.quote.lineItems[lineIndex].itemQuantity;
    if (isNaN(quantity)) { quantity = 1; }
    if (quantity < 1) { quantity = 1; }
    let discount: number = this.quote.lineItems[lineIndex].itemDiscount;
    if ( isNaN( discount )) { discount = 0; }
    const discountPercentage: number = discount > 100 ? (discount / 100) :  (1 - discount / 100 );
    const totalPrice: number = +(discountPercentage * ( unitPrice * quantity )).toFixed(2);
    this.quote.lineItems[lineIndex].totalPrice =  Math.floor( totalPrice * 100 ) / 100; // round to 2 decimals
    this.updateLineItemMetaData(lineIndex)
  }

  setComplete() {
    this.quote.complete = true;
    this.save();
  }


  setFootnote(note: string, linkIndex: number) {
    if ( linkIndex == undefined || linkIndex < 0 ) { return; }
    if ( this.quote.lineItems[linkIndex] == undefined ) { return; }
    if ( note == undefined ) { note = ''; }
    if ( note.length > 500 ) { note = note.substr(0, 500); }
    this.quote.lineItems[linkIndex].footNote = note;
  }

  checkTermsAndConditions(tac: string) {
    if (tac == undefined || tac.trim() == '') {
      return;
    }
    this.termAndCond = tac;
  }

  changeCreatedDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.quote.createdDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.quote.createdDate = new Date(formatted);
    }
  }

  changeExpirationDate(event : any) {
    // let d = event.formatted;
    // if (d == undefined) return;
    // this.quote.expirationDate = new Date(d);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.quote.expirationDate = new Date(formatted);
    }
  }

  validate(): boolean {
    if (!this.quote.clientCompany || !this.quote.clientContact ) {
      this.message = 'Please make sure you select a Company and Contact';
      setTimeout(_ => { this.message = undefined; }, 3000);
      return false;
    }

    if (this.quote.createdDate >= this.quote.expirationDate) {
      this.message = 'Please make sure you have the expiry date set later than create date';
      setTimeout(_ => { this.message = undefined; }, 3000);
      return false;
    }

    if (!this.quote.lineItems || this.quote.lineItems.length == 0) {
      this.message = 'Please add at least one item to the quote';
      setTimeout(_ => { this.message = undefined; }, 3000);
      return false;
    } else {
      // validate all selected links have been properly added by clicking on a returned value rather than typing value
      for (let i = 0 ; i < this.quote.lineItems.length ; i++ ) {
        if ( !this.quote.lineItems[i].catalogNumber ) {
          this.message =  'Item # ' + (i + 1) + ' was not properly added to the quote.';
          setTimeout(_ => { this.message = undefined; }, 3000);
          return false;
        }
      }
    }

    if ( this.includeFeeAndTax == undefined ) {
      this.message = 'Please select include shipping, handling and tax?';
      setTimeout(_ => { this.message = undefined; }, 3000);
      return false;
    }

    return true;
  }

  saveClicked() {
    if ( !this.validate() ) { return; }
    if (this.isNew) {
      this.save();
    }
    else {
      this.changeVersionNumberModal=this.modalService.open(this.changeVersionNumberOption, { backdrop: "static", size: 'lg' });
    }
  }


  revertVersionAndSave() {
    this.quote.revision = this.versionNumber;
    this.save();
  }

  save() {
    this.quote.editedBy = this.currentUser.name;
    this.quote.modifiedOn = new Date();
    this.quote.revisionDate = new Date();
    this.isNew = false;
    this.editingEnabled = false;
    this.updateQuote.emit(this.quote);
  }

  cancelEdit() {
    this.reset();
    this.editingEnabled = false;
    this.cancel.emit();
  }

  selectClientForQuote( client : ClientV2 ) {
    this.quote.clientCompany = client;
  }

  selectContactForQuote( contact : ClientContactV2 ) {
    this.quote.clientContact = contact;
  }

  selectShippingAddressForQuote( shippingAddress : ShippingAddressV2 ) {
    this.quote.shippingAddress = shippingAddress;
  }


  setTax() {
    if (this.quote.taxRate ) {
      let taxRateSetting : SettingValueV2 = this.taxRates.settingValues.find( x => x.value == this.quote.taxRate );
      if ( taxRateSetting ) {
        try {
        let rate = parseInt( taxRateSetting.attribute );
        rate = rate / 100;
        let total = 0;
        this.quote.lineItems.forEach(line => {
          let discountRate : number = 1 - (line.itemDiscount / 100);
          total += +((line.price * line.itemQuantity) * discountRate).toFixed(2);
        });
        this.quote.tax = (Number(total) +
                          Number(this.quote.handlingFee) +
                          Number(this.quote.shippingFee)) * rate;
        }
        catch ( error ) {
          this.quote.tax = 0;
          alert( 'Unable to calculate tax - tax rate settings not correctly configured - contact support');
        }
      }
    }
    else {
      this.quote.tax = 0;
    }
  }

  splitString(information): string[] {
    const wordsArray = information.split(' ');
    let eachLine = '';
    const companyArray = [];
    wordsArray.forEach( (word, index) => {
      if (index % 4 != 0 || index == 0) {
        eachLine = eachLine + word + ' ';
      } else if (index % 3 == 0 && index != 0) {
        eachLine = eachLine + word;
      } else {
        companyArray.push(eachLine);
        eachLine = '';
        eachLine = eachLine + word + ' ';
      }
    }  );

    companyArray.push(eachLine);
    return companyArray;
  }



  getQuotePDF() {
    const doc = new jsPDF();
    const imgData = image.logo;
    const width = doc.internal.pageSize.getWidth();
    const height = doc.internal.pageSize.getHeight();
    const margin = 5;
    // let tablewidth=Math.floor((width - (2 * margin)) / 3);
    const emptyHead = [{title: '', dataKey: 'header'}, {title: '', dataKey: 'content'}];
    doc.addImage(imgData, 'JPEG', margin, 10, 60, 23);
    const headMargin = (((width - (margin * 2)) / 3) * 2) + margin;
    const ed = new Date(this.quote.expirationDate);
    const date = this.datePipe.transform(this.quote.expirationDate, this.dateFormatDisplay);
    const today = new Date();
    const month = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    const headData = [
      {header: 'QUOTE #', content: this.quote.quoteNumber},
      {header: 'Version #', content: this.quote.revision},
      {header: 'EXPIRY DATE: ', content: date},
      {header: 'QUOTE STATUS: ', content: this.quote.complete ? 'Completed' : today > ed ? 'Expired' : 'Active'}
    ];
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
      pageBreak: 'auto', // 'auto', 'avoid' or 'always'
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
    let clientAddress = '';
    const clientTable = [[this.quote.clientContact ? this.quote.clientContact.name : 'No defined contact']];
    if (this.quote.clientContact && StringUtils.IsDefinedAndNotEqualsTerms(this.quote.clientContact.role, '', '-')) {
      clientTable.push([this.quote.clientContact.role]);
    }

    if (this.quote.shippingAddress) {
      if (this.quote.shippingAddress.addressLine1) {
        clientAddress = this.quote.shippingAddress.addressLine1.trim();
      }
      if (StringUtils.IsDefinedAndNotEqualsTerms(this.quote.shippingAddress.addressLine2, '', '-')) {
        clientAddress += '\n' + this.quote.shippingAddress.addressLine2.trim();
      }

      if (this.quote.shippingAddress.city) {
        clientAddress += '\n' + this.quote.shippingAddress.city.trim();
      }

      if (StringUtils.IsDefinedAndNotEqualsTerms(this.quote.shippingAddress.province, '', '-')) {
        clientAddress += ', ' + this.quote.shippingAddress.province.trim();
      }

      if (this.quote.shippingAddress.country) {
        clientAddress += '\n' + this.quote.shippingAddress.country.trim();
      }

      if (StringUtils.IsDefinedAndNotEqualsTerms(this.quote.shippingAddress.postalCode, '', '-')) {
        clientAddress += '   ' + this.quote.shippingAddress.postalCode.trim();
      }

    }

    clientTable.push([this.quote.clientCompany.companyName]);
    clientTable.push([clientAddress]);
    if (this.quote.clientContact && StringUtils.IsDefinedAndNotEqualsTerms(this.quote.clientContact.phone, '')) {
      clientTable.push(['Phone: ' + this.quote.clientContact.phone]);
    }
    if ( this.quote.clientContact && StringUtils.IsDefinedAndNotEqualsTerms( this.quote.clientContact.email, '' )) {
      clientTable.push(['Email: ' + this.quote.clientContact.email]);
    }
    doc.autoTable(['Requisitioner Information:'], clientTable, {
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
      pageBreak: 'auto', // 'auto', 'avoid' or 'always'
      tableWidth: Math.floor((width - (2 * margin)) / 3) + 10, // 'auto', 'wrap' or a number,
      tableLineColor: 200, // number, array (see color section below)
      tableLineWidth: 0,
      margin: col1Margin,
      drawRow: function (row, data) {
        nextPointer = Math.max(nextPointer, row.y + (row.height / 2));
      }
    });

    const headerPlusInfo: string[] = [ this.companyHeader[0] + ' Info:'];

    doc.autoTable(
      headerPlusInfo,
      [
        this.companyAddress1,
        this.companyAddress2,
        this.companyAddress3,
        this.companyPhone,
        this.companyEmail,
        this.companyHST,
      ],
      {
        theme: 'plain',
        styles: {
          fontSize: 9,
          textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold',
        },
        bodyStyles: {
          cellPadding: 0,
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - 2 * margin) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col2Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + row.height / 2);
        },
      }
    );



    const cd = new Date(this.quote.createdDate);
    const rvd = new Date(this.quote.revisionDate);
    doc.autoTable(['Quote Details:'], [
      ['Quote Date: ' + cd.getDate() + ' ' + month[cd.getMonth()] + ' ' + cd.getFullYear()],
      ['Revision Date: ' + rvd.getDate() + ' ' + month[rvd.getMonth()] + ' ' + rvd.getFullYear()],
      ['Issue By: ' + this.ourSalesRep]
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
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3) - 10, // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col3Margin + 10,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2));
        }
    });
    linePointer += 20;

    doc.autoTable(['Contact Info:'], [
      ['Sale Rep.: ' + this.ourSalesRep],
      this.companyEmail
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
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col3Margin + 10,
        drawRow: function (row, data) {
          linePointer = row.y + (row.height / 2);
        }
    });

    linePointer = nextPointer + 10;


    const quoteData: PrintedQuote = this.getDataFromLink();
    const header: { title: string, dataKey: string }[] = [];
    header.push( { title: 'Catalog #', dataKey: 'category' } );
    header.push( { title: 'Name', dataKey: 'description' });
    header.push( { title: 'Qty', dataKey: 'quantity' });
    header.push( { title: 'List Price', dataKey: 'listPrice' });
    if (quoteData.hasDiscount ) { header.push( { title: 'Disc.', dataKey: 'discount' } ); }
    header.push( { title: 'Price', dataKey: 'adjustedPrice' });
    header.push( { title: 'Extended', dataKey: 'totalPrice' });


    quoteData.data.forEach(quoteDataElement => {
      if (quoteDataElement.description) {
        quoteDataElement.description = quoteDataElement.description.replace(/\n/g, '');
        quoteDataElement.description = quoteDataElement.description.replace(/Size/g, '\n\nSize');
        quoteDataElement.description = quoteDataElement.description.replace(/Note/g, '\nNote');
      }
    });

    doc.autoTable(header, quoteData.data, {
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
        quantity: { columnWidth: 20, halign: 'center' },
        description: { columnWidth: 'auto', halign: 'left', fillColor: false },
        discount: { columnWidth: 20, halign: 'center' },
        category: { columnWidth: 35, halign: 'center' },
        listPrice: { columnWidth: 20, halign: 'center' },
        totalPrice: { columnWidth: 25, halign: 'center' },
        adjustedPrice: { columnWidth: 20, halign: 'center' },
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
      drawHeaderRow: function (row, data) {
        row.cells.quantity.text = 'Qty';
        row.cells.quantity.styles.halign = 'center';
        row.cells.listPrice.styles.halign = 'center';
        row.cells.totalPrice.styles.halign = 'center';
        row.cells.description.styles.halign = 'left';
        row.cells.category.styles.halign = 'center';
        if (quoteData.hasDiscount) {
          row.cells.discount.styles.halign = 'center';
          row.cells.adjustedPrice.styles.halign = 'center';
        }
      },
      drawRow: function (row, data) {
        linePointer = row.y + row.height;

        if (row.cells.adjustedPrice !== undefined) {
          row.cells.adjustedPrice.styles.columnWidth = 'wrap';
          row.cells.adjustedPrice.styles.halign = 'center';
        }

        if (row.cells.discount !== undefined) {
          row.cells.discount.styles.columnWidth = 'wrap';
          row.cells.discount.styles.halign = 'center';
        }
      }
    });

    const totalHead = [{ title: '', dataKey: 'text' }, { title: '', dataKey: 'amount' }];
    const totalData = [];
    let rowcount = 0;

    if (quoteData.hasDiscount) {
      totalData.push({ text: 'List Total Price:', amount: '$' +   this.formatMoney(quoteData.totalListPrice) });
      totalData.push({ text: 'Discount Total:', amount: '$' + this.formatMoney(quoteData.totalDiscount) });
      rowcount += 2;
    }
    totalData.push({ text: 'Subtotal:', amount: '$' + this.formatMoney(quoteData.totalPrice) });

      if ( this.quote.handlingFee != null && this.quote.handlingFee != 0 && this.quote.shippingFee != null && this.quote.shippingFee != 0 ) {
        totalData.push({ text: 'Estimated Shipping and Handling Fee:', amount: '$' + this.formatMoney(this.quote.handlingFee + this.quote.shippingFee) });
        rowcount += 1;
      } else if ( this.quote.handlingFee != null && this.quote.handlingFee != 0 && (this.quote.shippingFee == null || this.quote.shippingFee == 0) ) {
        totalData.push({ text: 'Estimated Handling Fee:', amount: '$' + this.formatMoney(this.quote.handlingFee) });
        rowcount += 1;
      } else if ( this.quote.shippingFee != null && this.quote.shippingFee != 0 && (this.quote.handlingFee == null || this.quote.handlingFee == 0) ) {
        totalData.push({ text: 'Estimated Shipping Fee:', amount: '$' + this.formatMoney(this.quote.shippingFee) });
        rowcount += 1;
      } else {
        totalData.push({ text: 'Estimated Shipping and Handling Fee:', amount: 'TBD' });
        rowcount += 1;
      }



    if (this.quote.tax != undefined && this.quote.tax != null && this.quote.tax !== 0) {
      totalData.push({ text: 'Tax:', amount: '$' + this.formatMoney(this.quote.tax) });
      totalData.push({ text: 'Total (USD):', amount: '$' + this.formatMoney(quoteData.totalListPrice + this.quote.handlingFee + this.quote.shippingFee  + this.quote.tax) });
      rowcount += 2;

      if (this.quote.currency && this.quote.currency != 'USD' && this.quote.currencyRate) {
        const currencyIndex = this.currencyOptions.indexOf(this.quote.currency);
        totalData.push({ text: 'Currency Rate (' + this.quote.currency + '):',
                         amount: this.formatMoney(this.quote.currencyRate) });
        rowcount += 2;
        totalData.push({ text: 'Total (' + this.quote.currency + '):',
                         amount: this.currencySigns[currencyIndex] +
                           this.formatMoney( (quoteData.totalPrice +
                                             this.quote.handlingFee +
                                             this.quote.shippingFee  +
                                             this.quote.tax) * this.quote.currencyRate  ) });
        rowcount += 2;
      }


    } else {
      totalData.push({ text: 'Total (USD):',
                       amount: '$' + this.formatMoney(
                           quoteData.totalPrice +
                           this.quote.handlingFee +
                           this.quote.shippingFee) });
      rowcount += 1;
      if (this.quote.currency && this.quote.currency != 'USD' && this.quote.currencyRate) {
        const currencyIndex = this.currencyOptions.indexOf(this.quote.currency);
        totalData.push({ text: 'Currency Rate (' + this.quote.currency + '):',
                         amount: this.formatMoney(this.quote.currencyRate) });
        rowcount += 1;
        totalData.push({ text: 'Total (' + this.quote.currency + '):',
                         amount: this.currencySigns[currencyIndex] +
                           this.formatMoney((quoteData.totalPrice +
                                             this.quote.handlingFee +
                                             this.quote.shippingFee) * this.quote.currencyRate  ) });
        rowcount += 1;
      }
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
        if (row.index % 2 == 0) {
          row.cells.text.styles.fillColor = [224, 234, 207];
          row.cells.amount.styles.fillColor = [224, 234, 207];
        }
      }
    });




    doc.setFontSize(9);
    linePointer += 8;
    if (this.quote.note !== null && this.quote.note.trim() !== '') {
      doc.setTextColor(0, 132, 183);
      doc.setFontStyle('bold');
      doc.text('Please Note: ', margin, linePointer);
      doc.setFontSize(8);
      doc.setFontStyle('italic');
      const n = doc.splitTextToSize(this.quote.note, 150);
      doc.text(n, margin + 30, linePointer);
      linePointer += (5 * n.length) - 1;
      doc.setTextColor(255);
      doc.text('a ', margin, linePointer);
      linePointer += 3;
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
    doc.text(this.quote.paymentType, margin + 30, linePointer);

    linePointer += 5;

    doc.setFontSize(9);
    doc.setFontStyle('bold');
    doc.text('Terms and Conditions: ', margin, linePointer);
    linePointer += 5;

    doc.setFontSize(8);
    doc.setFontStyle('normal');
    if (this.isInnoProgram) {
      doc.text('- Innovator program data sharing discount.', margin, linePointer);
      linePointer += 4;
    }

    const lineTAC = this.termAndCond.split('\n');
    lineTAC.forEach(line => {
      const lt = doc.splitTextToSize(line, width - (2 * margin));
      doc.text(lt, margin, linePointer);
      linePointer += (4 * lt.length) - (lt.length - 1);
    });

    const footerHead = ['If you have any questions concerning this price quote, please contact our sales team. sales@somrubioscience.com'];
    const footerData = [['THANK YOU FOR YOUR BUSINESS!']];
    doc.autoTable(footerHead, footerData, {
      theme: 'plain', // 'striped', 'grid' or 'plain'
      styles: {
        halign: 'center',
        valign: 'middle',
        font: 'times',
        fontSize: 9,
        overflow: 'linebreak',
        fillColor: false,
        textColor: 0
      },
      headerStyles: {
        fontStyle: 'italic',
      },
      bodyStyles: {
        fontStyle: 'bold',
        fontSize: 10
      },
      // Properties
      startY: linePointer, // false (indicates margin top value) or a number
      pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
      tableLineColor: 200, // number, array (see color section below)
      tableLineWidth: 0,
      margin: margin
    });
    this.assignPageNum(doc);
    doc.save( this.companyHeader + 'Quote ' + this.quote.quoteNumber + ' .pdf');

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

  getDataFromLink(): PrintedQuote {
    if (!this.quote || !this.quote.lineItems) { return; }
    const printedQuote: PrintedQuote = new PrintedQuote();

    let totalDiscount = 0;
    let totalPrice = 0;
    let totalListPrice = 0;
    let hasDiscCount = 0;
    this.quote.lineItems.forEach(line => {
      if (line.itemDiscount > 0) { hasDiscCount++; }
      const discountAmount = line.price * (line.itemDiscount / 100);
      totalDiscount += discountAmount * line.itemQuantity;
      totalListPrice += line.price * line.itemQuantity;
      totalPrice += line.totalPrice;
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

      printedQuote.data.push({
        category: line.catalogNumber,
        description: lineDescription,
        quantity: line.itemQuantity,
        listPrice: '$' + this.formatMoney(line.price),
        discount: line.itemDiscount + '%',
        adjustedPrice: '$' + this.formatMoney(adjustedPrice),
        totalPrice: '$' + this.formatMoney(line.totalPrice) });
    });

    printedQuote.totalPrice = totalPrice;
    printedQuote.totalDiscount = totalDiscount;
    printedQuote.totalListPrice = totalListPrice;
    printedQuote.hasDiscount = hasDiscCount > 0;

    return printedQuote;


  }


  formatMoney(val: number): string {
    if ( !val ) { return '0.0'; }
    let format = val.toFixed(2);
    format = format.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    return format;
  }




}

export class TermsAndCondition {
  public tac: string;

  constructor(paymentType: string) {
    if (paymentType == 'Prepayment') {
      this.tac =
     `      - Product will be shipped in 5-7 business day(s) after receiving prepayment.
      - The listed discount(s) and/or prices will be applied when the quote number is provided at time of order.
      - All products must be ordered collectively under one purchase order number for the discount to apply.
      - Applicable taxes, import duties and delivery charges are additional unless stated otherwise.
      - Shipping via FedEx unless otherwise requested. All goods ship via Incoterms EXW - Exworks.
      - Please see the following link for the full details of our sales agreement. http://somrubioscience.com/page/standard-terms-and-conditions-of-sale`;
    } else {
      this.tac =
     `      - Product will be shipped in 5-7 business day(s) after receiving PO.
      - The listed discount(s) and/or prices will be applied when the quote number is provided at time of order.
      - All products must be ordered collectively under one purchase order number for the discount to apply.
      - Applicable taxes, import duties and delivery charges are additional unless stated otherwise.
      - Shipping via FedEx unless otherwise requested. All goods ship via Incoterms EXW - Exworks.
      - Please see the following link for the full details of our sales agreement. http://somrubioscience.com/page/standard-terms-and-conditions-of-sale`;
    }



  }




}
