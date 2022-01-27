import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { WorksheetDesignService } from 'app/shared/services/eWorksheet.service';
import { AlertService } from '../../_alert';
import { BaseComponent } from '../AbstractStep/base-step';
import * as lodash from "lodash";
import { FileUploadComponent } from '../../FileUpload/file-upload/file-upload.component';

@Component({
  selector: 'app-calibrator',
  templateUrl: './calibrator.component.html',
  styleUrls: ['./calibrator.component.css']
})
export class CalibratorComponent extends BaseComponent implements OnInit {

  @ViewChild('fileupload') fileupload_view  : FileUploadComponent;
  stepFiles = [];
  files_base64 = [];
  fileNum = 0;

  sectionLabel;
  materialLabel;
  supplierLabel;

  constructor(ws_service: WorksheetDesignService, private cd: ChangeDetectorRef, public alertService: AlertService) {
    super(ws_service, alertService);
   }

  ngOnInit(): void {
    super.ngOnInit();

    switch(this.stepType){
      case  'Calibrator Preparation':
        this.sectionLabel = 'Calibrator'
        this.materialLabel = 'Calibrator Name'
        this.supplierLabel = 'Calibrator Supplier'
        break;

      case  'QC Preparation':
        this.sectionLabel = 'QC'
        this.materialLabel = 'QC Name'
        this.supplierLabel = 'QC Supplier'
        break;

      case  'Sample Dilution':
        this.sectionLabel = 'Sample'
        this.materialLabel = 'Diluent Name'
        this.supplierLabel = 'Diluent Supplier'
        break;

      case  'MRD':
        this.sectionLabel = 'MRD'
        this.materialLabel = 'Diluent Name'
        this.supplierLabel = 'Diluent Supplier'
        break;
    }

     //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({ materialLot_user: "?" });
      this.form.patchValue({ files: [] })
      this.fileNum = this.form.value.files.length
    }

    //prepopulate user input values for experiment mode
    if(this.mode === "experiment"){

        this.form.patchValue({ materialLot_user: this.editData.materialLot_user});
        this.form.patchValue({ files: this.editData.files});
        this.fileNum = this.form.value.files.length

        this.files_base64 = lodash.cloneDeep(this.editData.files)
        if(this.files_base64.length > 0 && this.fileupload_view){
          this.fileupload_view.initFileData(this.files_base64);
        }


        if(this.editData.materialId){
          //load lot number list content for blockingBufferLot_user
          this.loadLotList(this.editData.materialId, 'materialLotNumList');
          }else{
            this.materialLotNumList = [];
            console.log("material lot number list content is initialized (no itemId): ");
            console.log(this.materialLotNumList);
          }

        //initialize isExcluded's value
        this.isExcluded = this.editData.isExcluded
    }

    if(this.mode === 'review'){
      this.fileNum = this.reviewData.files.length
    }

    //prepopulate step configuration for edit mode, the edit mode is not defined by code, instead such a mode is determined by logic
    //when the "editData" input is defined, then this component will be set with "design", and the edit mode is kind of like a
    //special version of "design" mode
    if(this.editData){

      //load operation 1 values
      this.form.patchValue({material: this.editData.material});
      this.form.patchValue({supplier: this.editData.supplier});


      //load peripherals values
      this.materialId = this.editData.materialId;
      this.itemId_copy = this.materialId;

    }

    if(this.reviewData){
      this.isExcluded = this.reviewData.isExcluded;
      this.files_base64 = lodash.cloneDeep(this.reviewData.files)
    }

    if(this.mode !== 'review'){
      this.updateInfo();
    }

    this.cd.detectChanges();

  } // End of ngOnInit

  onFileChange(event){
    this.stepFiles = event.files;
    this.form.patchValue({ files: lodash.cloneDeep(event.files)});
    this.fileNum = this.form.value.files.length
    console.log(this.stepType.split(' ')[0] + " updates file array:");
    console.log(this.form.value.files)
    this.updateInfo();
  }
}
