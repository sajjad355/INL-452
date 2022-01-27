import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { ProductService2 } from 'app/shared/services2/Product2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { image } from '../../shared/objects/Image';
import { ApplicationV2, ProductV2 } from '../../shared/objects2/ProductV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';
declare var jsPDF: any

class SearchCriteria {
  searchValue : string;
  active : boolean;
}

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  @Input() isShowProduct: boolean;
  @Input() displayProduct: ProductV2;
  @Output() resetView = new EventEmitter();

  search : SearchCriteria = { searchValue: "", active : true };
  products: ProductV2[] = [];
  allCount: number;
  isNewProduct: boolean;
  enableEditProduct: boolean;
  newProduct: ProductV2;

  isLoading: boolean;
  page: number;
  currentuser: UserV2;
  message: string;
  viewColumn: boolean[];
  columnList: string[];
  toconfirm: string = '';
  sopModal: any;
  importfile;
  productSafety: String[] = [];
  applicationNote: String[] = [];
  productapplicationsforpdf: ApplicationV2[] = [];
  canManageProducts : boolean=false;

  companyHeader: string;
  companyPhone: string;
  companyFax: string;
  companyEmail: string;
  companyAddress1: string;
  companyAddress2: string;
  companyAddress3: string;
  companyWebsite: string;
  dateFormatDisplay: string;

  constructor(
    private modalService: NgbModal,
    private router: Router,
    private productService: ProductService2,
    private authenticationservice: AuthenticationService2,
    private settingService: SettingService2,
    public attService: AttachmentService,
    private errorService: ErrorService,
    private route: ActivatedRoute
  ) {}

  async ngOnInit() {
    this.viewColumn = [true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false ];
    this.columnList = ['Cat#', 'Name', 'Type', 'Size', 'Price(USD)', 'Edit Status', 'Host', 'Clonality', 'Isotype', 'Immunogen', 'Description', 'Purification', 'Buffer', 'Old Cat', 'Specificity', 'Storage', 'Safety', 'Comment', 'Pack Size', 'Active'];
    this.page = 1;

    this.isLoading = true;
    this.loadPage();
    await this.loadUser()
    this.handleParam()
    this.loadSetting()
    this.isLoading = false;
  }

  handleParam() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")

      if (idParam === 'new') {
        this.createNewProduct();
        return
      }

      const productId = Number(idParam)
      if (productId !== 0) this.showProduct(productId)
    })
  }

  loadSetting(){
    this.productSafety = this.settingService.getSettingValuesAsArray( 'productSafetyWarning' );
    this.applicationNote = this.settingService.getSettingValuesAsArray( 'applicationNote' );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.companyAddress1 = this.settingService.getSettingValuesAsArray( 'companyAddressLine1' )[0];
    this.companyAddress2 = this.settingService.getSettingValuesAsArray( 'companyAddressLine2' )[0];
    this.companyAddress3 = this.settingService.getSettingValuesAsArray( 'companyAddressLine3' )[0];
    this.companyPhone = this.settingService.getSettingValuesAsArray( 'companyPhone' )[0];
    this.companyEmail  = this.settingService.getSettingValuesAsArray( 'companyEmail' )[0];
    this.companyWebsite = this.settingService.getSettingValuesAsArray( 'companyWebsite' )[0];
    this.companyFax = this.settingService.getSettingValuesAsArray( 'companyFax' )[0];
  }

  async loadUser() {
    const user = await this.authenticationservice.getCurrentUser()
    this.currentuser = UserV2.copy(user)

    this.canManageProducts = false

    if (this.currentuser && this.currentuser.userRoles) {
      this.canManageProducts = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.MANAGE_PRODUCTS);      
    }
  }

  changeDisplayedProduct(aProduct : ProductV2){
    this.products=[];
    this.products.push(aProduct);
    this.isLoading = false;
  }

  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
  }

  loadPage() {
    this.isLoading = true;
    this.productService.loadProductByPage(this.page - 1, this.search.active).subscribe(products => {
      // this.products = products.slice();
      this.products = products
      console.log(`List of products(page: ${this.page})`)
      console.log(this.products)
      this.productService.countAllActive(this.search.active).subscribe( count => {
        this.allCount = count;
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

  showProduct(salesItemId: number ) {
    this.productService.get(salesItemId).subscribe( p => {
      this.displayProduct = p;
      this.productapplicationsforpdf = this.displayProduct.applications;
      this.attService.init('Product', 'productId-' + salesItemId)
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isShowProduct = true;
  }

  open(content, type?: string) { // pass modal type
    //default can not edit
    this.enableEditProduct = false;
    this.isShowProduct = true;

    //tell it is a new recipe
    if (type == 'nr') {
      //if a new recipe, then create a new recipe and assign to displayRecipe
      this.isNewProduct = true;
      this.createNewProduct();
      this.displayProduct = this.newProduct;
    }

    this.sopModal = this.modalService.open(content, { backdrop: "static", size: 'lg' });
    this.sopModal.result.then((result) => {
      this.enableEditProduct = false;
      this.isNewProduct = false;
      this.reset();
    }, () => {
      //close with X
      this.reset();
    });
  }

  createNewProduct() {
    this.newProduct = new ProductV2();
    this.newProduct.editedBy = this.currentuser.name;
    this.newProduct.modifiedOn = new Date();
    this.newProduct.enteredBy = this.currentuser.name;
    this.newProduct.enteredTime = new Date();
    this.newProduct.editStatus = 'Entered';
    this.enableEditProduct = true;
    this.isShowProduct = true;
    this.displayProduct = this.newProduct;
    this.isNewProduct = true;
  }

  reset() {
    this.router.navigate(['/main/products'])
  }

  searchProduct() {
    this.isLoading = true;
    if (this.search.searchValue == undefined || this.search.searchValue == '') {
      this.page = 1;
      this.loadPage();
      this.isLoading = false;
    }
    else {
      this.productService.searchProductPageable(this.search.searchValue, this.search.active, this.page - 1).subscribe(result => {
        this.products = result.slice();
        this.productService.searchProductCount(this.search.searchValue, this.search.active).subscribe(count => {
          this.allCount = count;
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



  saveProduct( product: ProductV2 ) {
    if (!product) return;
    this.productService.save(product).then( p => {
      this.isShowProduct = false;
      this.enableEditProduct = false;
      this.loadPage();
    }).catch( error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
    });
  }


  //wrote in autotable change style
  generatePDF(product: ProductV2) {
    var doc = new jsPDF();
    let imgData = image.logo;
    let width = doc.internal.pageSize.getWidth();
    let height = doc.internal.pageSize.getHeight();
    let pagemargin = {
      left: 25,
      right: 25,
      top: 20,
      bottom: 10
    }
    let applications : ApplicationV2[] = this.displayProduct.applications;
    doc.addImage(imgData, 'JPEG', 30, 15, 50, 18);
    let applicationnote = this.applicationNote;
    doc.setDrawColor(2, 133, 186);
    doc.setLineWidth(0.8);
    doc.line(85, 10, 85, 40);
    doc.setFontSize(8);
    doc.setTextColor(2, 133, 186);
    let companyAddressLines1And2 : string = '';
    if ( this.companyAddress1 ) companyAddressLines1And2 = this.companyAddress1;
    if ( this.companyAddress2 ) companyAddressLines1And2+= ', ' + this.companyAddress2;
    let companyAddressLine3 : string = '';
    if ( this.companyAddress3 ) companyAddressLine3 = this.companyAddress3;
    doc.text(companyAddressLines1And2, 88, 18);
    doc.text(companyAddressLine3, 88, 22);
    let companyPhoneAndFax : string = '';
    if ( this.companyPhone ) companyPhoneAndFax = this.companyPhone;
    if ( this.companyFax ) companyPhoneAndFax+= ', ' + this.companyFax;
    let companyWebsite : string = '';
    if ( this.companyWebsite ) companyWebsite = this.companyWebsite;
    doc.text(companyPhoneAndFax, 88, 26);
    doc.text(companyWebsite, 88, 30);

    doc.setFontSize(18);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    doc.text(this.displayProduct.name + ', ' + this.displayProduct.unitSize + ' ' + this.displayProduct.unit, width - pagemargin.right, 55, { align: 'right' });

    doc.setFontSize(12);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    let cattext = 'Catalogue Number: ' + this.displayProduct.catalogNumber;
    doc.text(cattext, width - pagemargin.right, 61, { align: 'right' });

    doc.setFontSize(19);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    doc.text('Product Specifications Data Sheet', pagemargin.left, 75, { align: 'left' });

    let datalist = [];
    let header = ['',''];
    if (this.displayProduct.description && this.displayProduct.description.trim() != '') {
      let datarow = [];
      datarow.push('Description', this.displayProduct.description);
      datalist.push(datarow);
    }

    if (this.displayProduct.unitSize && this.displayProduct.unit ) {
      let datarow = [];
      datarow.push('Quantity', this.displayProduct.unitSize + ' ' + this.displayProduct.unit);
      datalist.push(datarow);
    }

    if (this.displayProduct.immunogen && this.displayProduct.immunogen.trim() != '') {
      let datarow = [];
      datarow.push('Immunogen', this.displayProduct.immunogen);
      datalist.push(datarow);
    }

    if (this.displayProduct.purification && this.displayProduct.purification.trim() != '') {
      let datarow = [];
      datarow.push('Purification', this.displayProduct.purification);
      datalist.push(datarow);
    }

    if (this.displayProduct.buffer && this.displayProduct.buffer.trim() != '') {
      let datarow = [];
      datarow.push('Buffer', this.displayProduct.buffer);
      datalist.push(datarow);
    }

    if (this.displayProduct.specificity && this.displayProduct.specificity.trim() != '') {
      let datarow = [];
      datarow.push('Specificity', this.displayProduct.specificity);
      datalist.push(datarow);
    }

    if (this.displayProduct.reconstitution && this.displayProduct.reconstitution.trim() != '') {
      let datarow = [];
      datarow.push('Reconstitution', this.displayProduct.reconstitution);
      datalist.push(datarow);
    }

    if (this.displayProduct.storage && this.displayProduct.storage.trim() != '') {
      let datarow = [];
      datarow.push('Storage', this.displayProduct.storage);
      datalist.push(datarow);
    }

    let nextpointer = 80;

    if (applications.length > 0) {
      let datarow = [];
      let applicationlist = [];

      datarow.push('Applications', []);
      datalist.push(datarow);
    }

    let safety = this.productSafety[0];
    let datarow = [];
    datarow.push('Safety/' + '\n' + 'Precautions', safety);
    datalist.push(datarow);


    let rowheight, contentpadding, leftcolumnwidth, rightcolumnwidth, wordlineheight, ypoint, xpoint;
    let applicationwidth, apwholewidth, apcellwidth, apcellheight;

    doc.autoTable(header, datalist, {
      theme: 'grid',
      showHeader: 'false',
      styles: {
        fillColor: [255, 255, 255],
        fontSize: 12,
        font: 'times',
        textColor: [0, 0, 0],
        lineColor: [173, 216, 230],
        overflow: 'linebreak'
      },
      headerStyles: {
        lineWidth: 0.05,
        fillColor: [255, 255, 255],
        fontSize: 12,
        font: 'times',
        fontStyle: 'bold',
        textColor: [0, 0, 0],
        lineColor: [173, 216, 230],
        overflow: 'linebreak'
      },
      columnStyles: {
        0: {
          fontStyle: 'bold',
          halign: 'left',
          columnWidth: 'wrap',
          fillColor: [255, 255, 255],
          valign: 'top'
        },
        1: {
          halign: 'left',
          columnWidth: 'auto',
          fillColor: [255, 255, 255],
          valign: 'top'
        }

      },
      margin: { left: pagemargin.left, right: pagemargin.right },
      startY: nextpointer, // false (indicates margin top value) or a number
      pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      tableWidth: Math.floor(width - pagemargin.left - pagemargin.right), // 'auto', 'wrap' or a number,
      tableLineColor: [173, 216, 230], // number, array (see color section below)
      tableLineWidth: 0.05,
      // showHeader: 'never',
      drawRow: function (row, data) {
        if (row.index == '0') {
          rowheight = row.height;
          leftcolumnwidth = row.cells[0].width;
          rightcolumnwidth = row.cells[1].width;
          contentpadding = row.cells[1].styles.cellPadding
          wordlineheight = rowheight - contentpadding * 2;
          xpoint = row.cells[1].textPos.x;
        }
        nextpointer = nextpointer + rowheight;

        if (row.raw[0] == 'Applications') {
          row.height = 11 + 11 * applications.length + 11;
        }

        if (row.raw[0] == 'Immunogen' && (row.raw[1].includes('<i>') || row.raw[1].includes('<sub>'))) {
          let cellpadding=row.cells[1].styles.cellPadding;
          //let wordlineheight=row.height-2*cellpadding;
          let secondcellwidth=row.cells['1'].width;
          let secondcelltext='';
          row.cells[1].text.forEach(line=>{
            secondcelltext=secondcelltext+line.trim();
          });
          secondcelltext = secondcelltext.replace(/<i>/g, "");
          secondcelltext = secondcelltext.replace(/<\/i>/g, "");
          secondcelltext = secondcelltext.replace(/<sub>/g, "");
          secondcelltext = secondcelltext.replace(/<\/sub>/g, "");
          secondcelltext = secondcelltext.replace(/\n|\r/g, "");
          secondcelltext = secondcelltext.trim();
          let textarray=doc.splitTextToSize(secondcelltext, 133.69702882407407 - 2 * cellpadding);
          let cellheight= (textarray.length + 1) * contentpadding + textarray.length * wordlineheight;
          row.height = cellheight;

        }

      },
      drawCell: function (cell, data) {
        if (data.row.raw[0] && data.row.raw[0] == 'Description' && cell.text != 'Description') {
          doc.setFontStyle('bold');
        }
        if (data.row.raw[0] && data.row.raw[0] == 'Applications' && cell.text == 'Applications') {
          applicationwidth = cell.width;
        }

        if (data.row.raw[0] && data.row.raw[0] == 'Immunogen' && cell.text != 'Immunogen' && (cell.raw.includes('<i>') || cell.raw.includes('<sub>'))) {
          doc.setDrawColor(173, 216, 230);
          doc.setFillColor(255, 255, 255)
          let celltext = '';
          cell.text.forEach(line => {
            celltext = celltext + ' ' + line;
          })

          let celltexttest = cell.text.toString();
          let iwords = [];
          let subwords = [];
          while (celltexttest.includes('<i>')) {
            let index1 = celltexttest.indexOf('<i>');
            let index2 = celltexttest.indexOf('</i>');
            let iword = celltexttest.substring(index1 + 3, index2)
            iwords.push(iword);
            celltexttest = celltexttest.substring(0, index1) + celltexttest.substring(index2 + 4, celltexttest.length)
          }

          while (celltexttest.includes('<sub>')) {
            let index1 = celltexttest.indexOf('<sub>');
            let index2 = celltexttest.indexOf('</sub>');
            let subword = celltexttest.substring(index1 + 5, index2)
            subwords.push(subword);
            celltexttest = celltexttest.substring(0, index1) + celltexttest.substring(index2 + 6, celltexttest.length)
          }

          celltext = celltext.replace(/<i>/g, "");
          celltext = celltext.replace(/<\/i>/g, "");
          celltext = celltext.replace(/<sub>/g, "");
          celltext = celltext.replace(/<\/sub>/g, "");
          let textarray = doc.splitTextToSize(celltext, cell.width - 2 * contentpadding)

          doc.setFontSize(12);
          doc.setTextColor(0, 0, 0);
          doc.setFont('times', 'normal');

          textarray.forEach((line, index) => {
            let xpointer, ypointer;
            if (index == 0) {
              xpointer = cell.x + contentpadding;
              ypointer = cell.y + (index + 1) * 3 * contentpadding + index * wordlineheight
            }
            else if (index > 0) {
              xpointer = cell.x + contentpadding;
              ypointer = cell.y + (index + 1) * 2 * contentpadding + index * wordlineheight
            }

            if (line.includes('E. coli')) {
              line = line.replace('E. coli', 'E.coli')
            }
            if (line.includes('S. frugiperda')) {
              line = line.replace('S. frugiperda', 'S.frugiperda')
            }
            let words = line.trim().split(' ')

            words.forEach(word => {
              if (word.includes('E.coli')) {
                word = word.replace('E.coli', 'E. coli');
              }
              if (word.includes('S.frugiperda')) {
                word = word.replace('S.frugiperda', 'S. frugiperda');
              }
              if (iwords.findIndex(x => x == word) != -1) {
                doc.setFontStyle('italic');
                doc.text(word, xpointer, ypointer)
                xpointer = xpointer + doc.getTextWidth(word) + contentpadding
              }

              else if (word.includes('VEGF')) {
                let vindex = word.indexOf('VEGF')
                doc.setFontSize(12);
                let firstpart = word.substring(0, index + 4)
                doc.text(firstpart, xpointer, ypointer)
                xpointer = xpointer + doc.getTextWidth(firstpart)
                let secondpart = word.substring(index + 4, index + 7)
                doc.setFontSize(6);
                doc.text(secondpart, xpointer, ypointer)
                xpointer = xpointer + doc.getTextWidth(secondpart) + contentpadding


              }
              else {
                doc.setFontSize(12);
                doc.setFontStyle('normal');
                doc.text(word, xpointer, ypointer)
                xpointer = xpointer + doc.getTextWidth(word) + contentpadding
              }
            })

          })

          return false;

        }

        if (data.row.raw[0] && data.row.raw[0] == 'Applications' && cell.text != 'Applications') {
          apwholewidth = width - pagemargin.left - pagemargin.right - applicationwidth;
          cell.width = apwholewidth;
          apcellwidth = apwholewidth / 3;
          apcellheight = data.row.height / (applications.length + 2);


          doc.setDrawColor(173, 216, 230);
          doc.setFillColor(255, 255, 255)
          doc.rect(cell.x, cell.y, apcellwidth - 2, apcellheight, 'FD');
          doc.setFontSize(12);
          doc.setTextColor(0, 0, 0);
          doc.setFont('times', 'bold');
          doc.text('Purpose', cell.x + contentpadding, cell.y + 5, { align: 'left', valign: 'top' })

          doc.setDrawColor(173, 216, 230);
          doc.setFillColor(255, 255, 255)
          doc.rect(cell.x + apcellwidth - 2, cell.y, apcellwidth - 2, apcellheight, 'FD');
          doc.setFontSize(12);
          doc.setTextColor(0, 0, 0);
          doc.setFont('times', 'bold');
          doc.text('Recommended' + '\n' + 'Concentration', cell.x + contentpadding + apcellwidth - 2, cell.y + 5, { align: 'left', valign: 'top' })

          doc.setDrawColor(173, 216, 230);
          doc.setFillColor(255, 255, 255)
          doc.rect(cell.x + apcellwidth + apcellwidth - 4, cell.y, apcellwidth + 4, apcellheight, 'FD');
          doc.setFontSize(12);
          doc.setTextColor(0, 0, 0);
          doc.setFont('times', 'bold');
          doc.text('Comment', cell.x + contentpadding + apcellwidth + apcellwidth - 4, cell.y + 5, { align: 'left', valign: 'top' })

          let lastlinepoint;
          applications.forEach((application, index) => {
            doc.setDrawColor(173, 216, 230);
            doc.setFillColor(255, 255, 255)
            doc.rect(cell.x, cell.y + apcellheight * (index + 1), apcellwidth - 2, apcellheight, 'FD');
            doc.setFontSize(11);
            doc.setTextColor(0, 0, 0);
            doc.setFont('times', 'normal');
            doc.text(application.purpose, cell.x + contentpadding, cell.y + 5 + apcellheight * (index + 1), { align: 'left', valign: 'top' })

            doc.setDrawColor(173, 216, 230);
            doc.setFillColor(255, 255, 255)
            doc.rect(cell.x + apcellwidth - 2, cell.y + apcellheight * (index + 1), apcellwidth - 2, apcellheight, 'FD');
            doc.setFontSize(11);
            doc.setTextColor(0, 0, 0);
            doc.setFont('times', 'normal');
            doc.text(application.recommendedConcentration, cell.x + contentpadding + apcellwidth - 2, cell.y + 5 + apcellheight * (index + 1), { align: 'left', valign: 'top' })

            doc.setDrawColor(173, 216, 230);
            doc.setFillColor(255, 255, 255)
            doc.rect(cell.x + apcellwidth + apcellwidth - 4, cell.y + apcellheight * (index + 1), apcellwidth + 4, apcellheight, 'FD');
            doc.setFontSize(11);
            doc.setTextColor(0, 0, 0);
            doc.setFont('times', 'normal');
            doc.text(application.comment, cell.x + contentpadding + apcellwidth + apcellwidth - 4, cell.y + 5 + apcellheight * (index + 1), { align: 'left', valign: 'top' })
            lastlinepoint = cell.y + apcellheight * (index + 1);
          });


          let note = applicationnote[0];
          let note2 = doc.splitTextToSize(note, apcellwidth * 3 - 10);
          doc.setDrawColor(173, 216, 230);
          doc.setFillColor(255, 255, 255)
          doc.rect(cell.x, lastlinepoint + apcellheight, apcellwidth * 3, apcellheight, 'FD');
          doc.setFontSize(12);
          doc.setTextColor(0, 0, 0);
          doc.setFont('times', 'italic');
          doc.text(note2, cell.x + contentpadding, lastlinepoint + apcellheight + 5, { align: 'left', valign: 'top' })

          return false;

        }

        if (data.row.raw[0] && data.row.raw[0] == 'Specificity' && cell.text != 'Specificity') {
          let xpointer = cell.x + contentpadding;
          let ypointer = cell.y + 3 * contentpadding;
          cell.text.forEach((line, lindex) => {
            if (lindex > 0) {
              ypointer = ypointer + 0.3 * contentpadding + wordlineheight
              xpointer = cell.x + contentpadding;
            }
            else {
              xpointer = cell.x + contentpadding;
              ypointer = ypointer + lindex * wordlineheight;
            }


            let eachline = line;
            let words = eachline.split(' ');
            words.forEach(word => {
              if (word.includes('VEGF')) {
                let index = word.indexOf('VEGF');
                if (word.trim() != 'VEGF' && word.trim() != 'VEGF,' && word.trim() != 'VEGF.') {
                  doc.setFontSize(12);
                  doc.setFontStyle('normal');
                  let firstpart = word.substring(0, index + 4)
                  doc.text(firstpart, xpointer, ypointer)
                  xpointer = xpointer + doc.getTextWidth(firstpart)
                  doc.setFontSize(6);
                  let secondpart = word.substring(index + 4, index + 7)
                  doc.text(secondpart, xpointer, ypointer);
                  let thirdpart = word.substring(index + 7, word.length)
                  xpointer = xpointer + doc.getTextWidth(firstpart)
                  doc.setFontSize(12);
                  doc.text(thirdpart, xpointer + 0.5 * contentpadding, ypointer);
                }
                else {
                  doc.setFontSize(12);
                  doc.setFontStyle('normal');
                  doc.text(word, xpointer, ypointer)
                  xpointer = xpointer + doc.getTextWidth(word) + 0.5 * contentpadding
                }
              }
              else {
                doc.setFontSize(12);
                doc.setFontStyle('normal');
                doc.text(word, xpointer, ypointer)
                xpointer = xpointer + doc.getTextWidth(word) + 0.5 * contentpadding
              }
            }

            )
          })
          return false;
        }

      }
    })

    doc.setFontSize(12);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    doc.text('Precautions: Use normal precautions for handing of blood derived products.', width / 2, height - pagemargin.bottom - 8, { align: 'center', valign: 'top' });

    doc.setFontSize(18);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    doc.text('FOR RESEARCH USE ONLY', width / 2, height - pagemargin.bottom, { align: 'center', valign: 'top' });

    doc.save(product.name + '.pdf');


  }




}


