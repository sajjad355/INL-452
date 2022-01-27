import {
  Component,
  OnInit,
  Input,
  SimpleChanges,
  Output,
  EventEmitter
} from '@angular/core';
import { EquipmentV2 } from 'app/shared/objects2/EquipmentV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { SettingService2, DateFormatType } from '../../shared/services2/Setting2.service';
import { UserRoleV2, UserV2 } from '../../shared/objects2/UserV2';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { DomSanitizer } from '@angular/platform-browser';
import { OPERATION_NAMES } from '../../shared/models/Constants';

@Component({
  selector: "app-equipment-display",
  templateUrl: "./equipment-display.component.html",
  styleUrls: ["./equipment-display.component.css"],
})
export class EquipmentDisplayComponent implements OnInit {
  @Input() equipment: EquipmentV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;

  @Output() updateEquipment = new EventEmitter();
  @Output() cancel = new EventEmitter();

  myDatePickerOptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  dateFormat = '';
  dateFormatDisplay: string;

  lastMaintenancePerformedDate: { date: { year: number, month: number, day: number } };
  calibrationPerformed: { date: { year: number, month: number, day: number } };

  bool2str(bool) {
    return bool ? 'true' : 'false';
  }
  
  intervals = ['Daily', 'Weekly', 'Monthly', 'Yearly'];

  displayEquipment: EquipmentV2;

  inputWarning: string[] = [];
  hideWarning = true;

  currentuser: UserV2;
  currentusername: string = "";
  currentUserRoles: UserRoleV2[] = [];

  approveRejectComment: boolean = true;

  unit: String[];
  clonalities: string[];
  productTypes: string[];
  OPERATION_NAMES = OPERATION_NAMES;
  canReviewEquipment : boolean = false;

  constructor(
    private authenticationservice: AuthenticationService2,
    private settingService : SettingService2,
    private _sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    let getcurrentuser = this.authenticationservice.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then((_) => {
        this.currentuser = UserV2.copy(_);
        this.currentusername = this.currentuser.name;
        this.currentUserRoles = this.currentuser.userRoles;
        this.canReviewEquipment = UserV2.isAllowedToPerform( this.currentuser, OPERATION_NAMES.REVIEW_EQUIPMENT);
      });
    }
    this.loadSetting();
  }

  renderImage(string) {
    return this._sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,'+string);
  }

  triggerUpload() {
    document.getElementById('uploader').click();
  }

  removeImage() {
    this.displayEquipment.image = '';
  }

  handleInputChange(e) {
    var file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    var pattern = /image-*/;
    var reader = new FileReader();
    if (!file.type.match(pattern)) {
      alert('invalid format');
      return;
    }
    reader.onload = this._handleReaderLoaded.bind(this);
    reader.readAsDataURL(file);
  }

  _handleReaderLoaded(e) {
    let reader = e.target;
    this.displayEquipment.image = reader.result.split(',')[1];
  }

  OnDateChanged(event: any) {
    // this.displayEquipment.lastMaintenancePerformedDate = new Date(event.formatted);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEquipment.lastMaintenancePerformedDate = new Date(formatted);
    }
  }

  OnDateChanged2(event: any) {
    // this.displayEquipment.calibrationPerformed = new Date(event.formatted);
    let {date, jsDate, formatted, epoc} = event.singleDate;
    if (formatted !== '') {
      this.displayEquipment.calibrationPerformed = new Date(formatted);
    }
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.equipment && this.equipment !== undefined) {
      this.reset();
    }
  }

  loadSetting() {
    this.unit = [];
    let volUnit : string[] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unit.push(v) });
    let massUnit : string[] = this.settingService.getSettingValuesAsArray( 'massUnit' );
    massUnit.forEach(m => { this.unit.push(m) });
    let otherUnit : string[] = this.settingService.getSettingValuesAsArray(  'otherUnit' );
    otherUnit.forEach(o => { this.unit.push(o) });
    this.clonalities =  this.settingService.getSettingValuesAsArray( 'cloneType' );
    // this.equipmentTypes = this.settingService.getSettingValuesAsArray( 'productType' );
    this.dateFormat = this.settingService.getDateFormat(DateFormatType.DatePickerUsage);
    this.myDatePickerOptions.dateFormat = this.dateFormat;
    // this.myDatePickerOptions.editableDateField = false;
    this.dateFormatDisplay = this.settingService.getDateFormat(DateFormatType.UserDisplay);
  }

  reset() {
    // make a local copy of the equipment passed into us so any changes are not reflected outside of this page until save
    this.displayEquipment = EquipmentV2.copy(this.equipment);
    this.isNew ? "" : (this.enableEdit = false);
    if(this.equipment.calibrationPerformed && !this.isNew) {
      let cp = new Date(this.equipment.calibrationPerformed);
      this.calibrationPerformed = { date: { year: cp.getFullYear(), month: cp.getMonth() + 1, day: cp.getDate() }};
      // console.log(this.calibrationPerformed)
    }
    if(this.equipment.lastMaintenancePerformedDate && !this.isNew) {
      let lmp = new Date(this.equipment.lastMaintenancePerformedDate);
      this.lastMaintenancePerformedDate = { date: { year: lmp.getFullYear(), month: lmp.getMonth() + 1, day: lmp.getDate() }};
      // console.log(this.lastMaintenancePerformedDate)
    }
    if(this.isNew) {
      this.lastMaintenancePerformedDate = null;
      this.calibrationPerformed = null;
    }
  }

  validateuserinput() {
    this.inputWarning = [];

    if ( !this.displayEquipment.name || this.displayEquipment.name.trim() == "" ) {
      this.inputWarning.push("Equipment name can not be empty.");
    }

    if ( !this.displayEquipment.equipmentIdentifier || this.displayEquipment.equipmentIdentifier.trim() == "" ) {
      this.inputWarning.push("Equipment identifier can not be empty.");
    }

    if ( !this.displayEquipment.serial || this.displayEquipment.serial.trim() == "" ) {
      this.inputWarning.push("Equipment serial can not be empty.");
    }

    if ( !this.displayEquipment.manufacturer || this.displayEquipment.manufacturer.trim() == "" ) {
      this.inputWarning.push("Equipment manufacturer can not be empty.");
    }

    if ( !this.displayEquipment.model || this.displayEquipment.model.trim() == "" ) {
      this.inputWarning.push("Equipment model can not be empty.");
    }

    if ( !this.displayEquipment.calibrationScheduleInterval || this.displayEquipment.calibrationScheduleInterval.trim() == "" ) {
      this.inputWarning.push("Calibration schedule interval can not be empty.");
    }
 
    if ( !this.displayEquipment.maintenanceInterval || this.displayEquipment.maintenanceInterval.trim() == "" ) {
      this.inputWarning.push("Maintenance interval can not be empty.");
    }

    if ( !this.displayEquipment.locationBuilding || this.displayEquipment.locationBuilding.trim() == "" ) {
      this.inputWarning.push("Location building can not be empty.");
    }

    if (this.inputWarning.length > 0 ) {
      this.hideWarning = false;
      setTimeout( () => {
        this.hideWarning = true;
      }, 5000);
    }
    else {
      this.save();
    }

  }

  save() {
    this.displayEquipment.editedBy = this.currentusername;
    this.displayEquipment.modifiedOn = new Date();
    this.equipment = this.displayEquipment;
    this.enableEdit = false;
    this.updateEquipment.emit( this.displayEquipment );
    this.reset();
  }

  cancelequipment() {
    this.cancel.emit();
  }
}
