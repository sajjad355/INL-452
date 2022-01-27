import { Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild, Output, EventEmitter } from '@angular/core';
import { EquipmentV2 } from '../../shared/objects2/EquipmentV2';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EquipmentService2 } from 'app/shared/services2/Equipment2.service';
import { image } from '../../shared/objects/Image';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
declare var jsPDF: any
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { ErrorService } from '../../page/error/error.service';
import * as lodash from "lodash";

class SearchCriteria {
  searchValue : string;
  active : boolean;
}

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.scss']
})
export class EquipmentComponent implements OnInit {
  @Input() isShowEquipment: boolean;
  @Input() displayEquipment: EquipmentV2;
  @Output() resetView = new EventEmitter();


  @ViewChild('confirmation') confirmation: ElementRef;
  @ViewChild('showActivityLog') showActivityLog: ElementRef;

  search : SearchCriteria = { searchValue: "", active : true };
  equipments: EquipmentV2[] = [];
  allCount: number;
  isNewEquipment: boolean;
  enableEditEquipment: boolean;
  newEquipment: EquipmentV2;
  searchKey: string;
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
  // productapplicationsforpdf: ApplicationV2[] = [];
  canManageEquipent : boolean = false;

  companyHeader: string;
  companyPhone: string;
  companyFax: string;
  companyEmail: string;
  companyAddress1: string;
  companyAddress2: string;
  companyAddress3: string;
  companyWebsite: string;

  dateFormatDisplay: string;

  openActivityModal: any;

  constructor(private modalService: NgbModal,
              private router: Router,
              private EquipmentService: EquipmentService2,
              private authenticationservice: AuthenticationService2,
              private settingService: SettingService2,
              private _sanitizer: DomSanitizer,
              private errorService: ErrorService) { }

  renderImage(string) {
    return this._sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,'+string);
  }

  ngOnInit() {
    this.checkIsManager();
    this.loadSetting();

    this.viewColumn = [true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, true];
    this.columnList = ['Serial', 'Image', 'Name', 'Manufacturer', 'Model', 'Comment', 'Calibration Performed', 'Calibration Schedule Interval', 'Maintenance Interval', 'Last Maintenance Performed Date', 'Building', 'Unit', 'Room', 'Non. Routine Maint. Comment', 'Is Calibration Due', 'Is Maintanence Due', 'Is Active'];
    this.page = 1;
    this.searchKey = '';
    if(this.displayEquipment != undefined) {
      this.changeDisplayedProduct(this.displayEquipment);
    }
    else {
      this.isLoading = true;
      this.allCount = this.EquipmentService.getCount();
      // alert(this.allCount)
      this.loadPage();
      this.isLoading = false;
    }

    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
      });
    }
  }

  openActivityLog() {
    this.openActivityModal = this.modalService.open(this.showActivityLog, { backdrop: "static", size: 'lg' })
  }

  sortActivityLog(log) {
    let sortedArray = log.sort(function(x, y) {
      return y.modifiedOn - x.modifiedOn;
    })
    return sortedArray;
  }

  loadSetting(){
    // this.equipmentsafety = this.settingService.getSettingValuesAsArray( 'productSafetyWarning' );
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

  checkIsManager(){
    let c = this.authenticationservice.getCurrentUser();
    if(c !== undefined){
      c.then(_ => {
        this.currentuser = UserV2.copy(_);

        this.canManageEquipent = false;
        if (this.currentuser && this.currentuser.userRoles) {
          this.canManageEquipent = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.MANAGE_EQUIPMENT);
        }
      });
    }

  }

  ngOnChanges(change: SimpleChanges) {
    // this.reset();
    if (change.displayEquipment && this.displayEquipment && this.displayEquipment !== undefined) {
      if (!change.displayEquipment.isFirstChange()) {
        this.changeDisplayedProduct(this.displayEquipment);
        this.allCount=1;
      }
    }

  }

  changeDisplayedProduct(aProduct : EquipmentV2){
    this.equipments=[];
    this.equipments.push(aProduct);
    this.isLoading = false;
  }

  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
  }

  loadPage() {
    this.isLoading = true;
    this.equipments = []
    this.EquipmentService.loadEquipmentByPage(this.page - 1).subscribe(equipments => {
      const temp = equipments

      // Find all the equipments that meet the condition for "both due"
      temp.forEach(equip => {
        if(equip.maintanenceDue && equip.calibrationDue){
          this.equipments.push(equip)
        }
      })

      // Find all the equipments that meet the condition for "calibration due"
      temp.forEach(equip => {
        if(!equip.maintanenceDue && equip.calibrationDue){
          this.equipments.push(equip)
        }
      })

      // Find all the equipments that meet the condition for "maintenence due"
      temp.forEach(equip => {
        if(equip.maintanenceDue && !equip.calibrationDue){
          this.equipments.push(equip)
        }
      })

      // Find all the equipments that meet the condition for "neither due"
      temp.forEach(equip => {
        if(!equip.maintanenceDue && !equip.calibrationDue){
          this.equipments.push(equip)
        }
      })

      this.allCount = this.EquipmentService.getCount();
      // alert(this.allCount)
      this.isLoading = false;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  showEquipment(equipmentId: number ) {
    this.EquipmentService.get(equipmentId).subscribe( p => {
      this.displayEquipment = p;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
    this.isShowEquipment = true;
  }

  open(content, type?: string) { // pass modal type
    //default can not edit
    this.enableEditEquipment = false;
    this.isShowEquipment = true;

    //tell it is a new recipe
    if (type == 'nr') {
      //if a new recipe, then create a new recipe and assign to displayRecipe
      this.isNewEquipment = true;
      this.createNewEquipment();
      this.displayEquipment = this.newEquipment;
    }

    this.sopModal = this.modalService.open(content, { backdrop: "static", size: 'lg' });
    this.sopModal.result.then((result) => {
      this.enableEditEquipment = false;
      this.isNewEquipment = false;
      this.reset();
    }, () => {
      //close with X
      this.reset();
    });
  }

  createNewEquipment() {
    this.newEquipment = new EquipmentV2();
    this.enableEditEquipment = true;
    this.isShowEquipment = true;
    this.displayEquipment = this.newEquipment;
    this.isNewEquipment = true;
  }

  reset() {
    this.enableEditEquipment = false;
    this.isShowEquipment = false;
    this.displayEquipment = undefined;
    this.resetView.emit();
    this.isNewEquipment = false;
    this.page = 1;
    this.searchKey = '';
  }

  searchEquipment() {
    this.isLoading = true;
    if (this.search.searchValue == undefined || this.search.searchValue == '') {
      this.allCount = this.EquipmentService.getCount();
      this.page = 1;
      this.loadPage();
      this.isLoading = false;
    }
    else {
      this.EquipmentService.searchEquipmentPageable(this.search.searchValue, this.search.active, this.page - 1).subscribe(result => {
        this.equipments = result.slice();
        this.EquipmentService.searchEquipmentCount(this.search.searchValue, this.search.active).subscribe(count => {
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

  saveEquipment( equipment: EquipmentV2 ) {
    if (!equipment) return;
    this.EquipmentService.save(equipment).then( p => {
      this.displayEquipment = p as EquipmentV2;
      this.isShowEquipment = false;
      this.enableEditEquipment = false;
      this.loadPage();
    }).catch( error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
    });
  }

  resetrecipedisplay(p: EquipmentV2) {
    this.displayEquipment = p;
    this.enableEditEquipment = false;
    this.isNewEquipment = false;
  }

  getfile(event) {
    if (event.target.files && event.target.files[0]) {
      this.importfile = event.target.files[0];
    }
  }

  generatePDF(equipment: EquipmentV2) {
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
    // let applications : ApplicationV2[] = this.displayEquipment.applications;
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
    doc.text(this.displayEquipment.name, 20, 55, { align: 'left' });

    // doc.setFontSize(12);
    // doc.setTextColor(0, 0, 0);
    // doc.setFont('times', 'bold');
    // let cattext = 'Serial Number: ' + this.displayEquipment.serial;
    // doc.text(cattext, width - pagemargin.right, 61, { align: 'right' });



    this.displayEquipment.image &&
    doc.addImage('data:image/png;base64,'+this.displayEquipment.image, 'png', 20, 60, 30, 30);

    doc.setFontSize(17);
    doc.setTextColor(0, 0, 0);
    doc.setFont('times', 'bold');
    doc.text('Equipment Specifications Data Sheet', 20, this.displayEquipment.image ? 105 : 65, { align: 'left' });

    let datalist = [];
    let header = ['',''];
    let datarow = [];

    if (this.displayEquipment.hasOwnProperty('active')) {
      let datarow = [];
      datarow.push('Active', this.displayEquipment.active ? 'Yes' : 'No');
      datalist.push(datarow);
    }

    if (this.displayEquipment.hasOwnProperty('qualified')) {
      let datarow = [];
      datarow.push('Qualified', this.displayEquipment.qualified ? 'Yes' : 'No');
      datalist.push(datarow);
    }

    if (this.displayEquipment.equipmentIdentifier) {
      let datarow = [];
      datarow.push('Identifier', this.displayEquipment.equipmentIdentifier);
      datalist.push(datarow);
    }

    if (this.displayEquipment.serial) {
      let datarow = [];
      datarow.push('Serial No.', this.displayEquipment.serial);
      datalist.push(datarow);
    }

    if (this.displayEquipment.manufacturer) {
      let datarow = [];
      datarow.push('Manufacturer', this.displayEquipment.manufacturer);
      datalist.push(datarow);
    }

    if (this.displayEquipment.model) {
      let datarow = [];
      datarow.push('Model', this.displayEquipment.model);
      datalist.push(datarow);
    }

    if (this.displayEquipment.calibrationPerformed) {
      let datarow = [];
      datarow.push('Calibration Performed', new Date(this.displayEquipment.calibrationPerformed).getDate()+'-'+new Date(+this.displayEquipment.calibrationPerformed).getMonth()+'-'+new Date(this.displayEquipment.calibrationPerformed).getFullYear());
      datalist.push(datarow);
    }

    if (this.displayEquipment.hasOwnProperty('calibrationDue')) {
      let datarow = [];
      datarow.push('Calibration Due', this.displayEquipment.calibrationDue ? 'Yes' : 'No');
      datalist.push(datarow);
    }

    if (this.displayEquipment.lastMaintenancePerformedDate) {
      let datarow = [];
      datarow.push('Last Maintenance Performed', new Date(this.displayEquipment.lastMaintenancePerformedDate).getDate()+'-'+new Date(+this.displayEquipment.lastMaintenancePerformedDate).getMonth()+'-'+new Date(this.displayEquipment.lastMaintenancePerformedDate).getFullYear());
      datalist.push(datarow);
    }

    if (this.displayEquipment.hasOwnProperty('maintanenceDue')) {
      let datarow = [];
      datarow.push('Maintenance Due', this.displayEquipment.maintanenceDue ? 'Yes' : 'No');
      datalist.push(datarow);
    }

    // if (this.displayEquipment.calibrationScheduleInterval) {
    //   let datarow = [];
    //   datarow.push('Calibration Schedule Interval', this.displayEquipment.calibrationScheduleInterval);
    //   datalist.push(datarow);
    // }

    // if (this.displayEquipment.maintenanceInterval) {
    //   let datarow = [];
    //   datarow.push('Maintenance Interval', this.displayEquipment.maintenanceInterval);
    //   datalist.push(datarow);
    // }

    if (this.displayEquipment.locationBuilding) {
      let datarow = [];
      datarow.push('Building', this.displayEquipment.locationBuilding);
      datalist.push(datarow);
    }

    if (this.displayEquipment.locationUnit) {
      let datarow = [];
      datarow.push('Unit', this.displayEquipment.locationUnit);
      datalist.push(datarow);
    }

    if (this.displayEquipment.locationRoom) {
      let datarow = [];
      datarow.push('Room', this.displayEquipment.locationRoom);
      datalist.push(datarow);
    }

    if (this.displayEquipment.nonRoutineMaintenanceComment) {
      let datarow = [];
      datarow.push('Non-Routine Maint. Comment', this.displayEquipment.nonRoutineMaintenanceComment);
      datalist.push(datarow);
    }

    if (this.displayEquipment.comment) {
      let datarow = [];
      datarow.push('Comment', this.displayEquipment.comment);
      datalist.push(datarow);
    }
    let nextpointer = this.displayEquipment.image ? 110 : 70;

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
      margin: { left: 20, right: 20 },
      startY: nextpointer, // false (indicates margin top value) or a number
      pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      tableWidth: Math.floor(width - 40), // 'auto', 'wrap' or a number,
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
      },
      drawCell: function (cell, data) {
        // if (data.row.raw[0] && data.row.raw[0] == 'Description' && cell.text != 'Description') {
        //   doc.setFontStyle('bold');
        // }
        // if (data.row.raw[0] && data.row.raw[0] == 'Applications' && cell.text == 'Applications') {
        //   applicationwidth = cell.width;
        // }
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

    doc.save(equipment.name + '.pdf');
  }
}
