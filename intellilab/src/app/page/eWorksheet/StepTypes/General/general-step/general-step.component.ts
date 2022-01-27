import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { WorksheetDesignService } from '../../../../../shared/services/eWorksheet.service';
import {BaseComponent} from '../../AbstractStep/base-step'
import * as lodash from "lodash";
import { AlertService } from 'app/page/eWorksheet/_alert';
@Component({
  selector: 'app-general-step',
  templateUrl: './general-step.component.html',
  styleUrls: ['./general-step.component.css']
})
export class GeneralStepComponent extends BaseComponent implements OnInit {

  bufferTitle: string;
  stepTitle: string;
  hasMaterial: boolean = true;

  constructor(ws_service: WorksheetDesignService, private cd: ChangeDetectorRef, public alertService: AlertService) {
    super(ws_service, alertService);
   }

  ngOnInit(): void {
    super.ngOnInit();
    switch(this.stepType){
      case  'Blocking':
        this.bufferTitle = 'Blocking Buffer'
        this.stepTitle = 'Blocking Solution Preparation';
        this.hasMaterial = false;
        break;

      case  'Capture':
        this.bufferTitle = 'Capture Buffer'
        this.stepTitle = 'Capture Solution Preparation';
        break;

      case  'Coating':
        this.bufferTitle = 'Coating Buffer'
        this.stepTitle = 'Coating Solution Preparation';
        break;

      case  'Detection':
        this.bufferTitle = 'Detection Buffer'
        this.stepTitle = 'Detection';
        break;

      case  'Test':
        this.bufferTitle = 'Sample Buffer'
        this.stepTitle = 'Test Solution Preparation';
        break;
    }

    //prepopulate step configuration for edit mode, the edit mode is not defined by code, instead such a mode is determined by logic
    //when the "editData" input is defined, then this washing component will be set with "design", and the edit mode is kind of like a
    //special version of "design" mode
    if(this.editData){

      //load operation 1 values
      this.form.patchValue({buffer: this.editData.buffer});
      this.form.patchValue({supplier: this.editData.supplier});

      //load operation 2 values
      if(this.stepType !== 'Blocking'){
        this.subStepArray = lodash.cloneDeep(this.editData.subStepArray);
        const lastItemIndex = this.editData.subStepArray.length - 1
        this.indexGenerator = this.editData.subStepArray[lastItemIndex].stepIndex + 1;
        console.log(`Init index generator value for sub material step in ${this.stepType} is:`  + this.indexGenerator);
      }

      // this.subStepInitData = lodash.cloneDeep(this.subStepArray);

      //load operation 3 values
      this.form.patchValue({dispenseValue: this.editData.dispenseValue});
      this.form.patchValue({dispenseUnit: this.editData.dispenseUnit});

      this.dispenseUnit_copy = this.form.value.dispenseUnit;

      //load peripherals values
      this.bufferId = this.editData.bufferId;
      this.itemId_copy = this.bufferId;
    }


    //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({ bufferLot_user: "?" });
    }

    //prepopulate user input values for experiment mode
    if(this.mode === "experiment"){
      this.form.patchValue({ bufferLot_user: this.editData.bufferLot_user });
      this.form.patchValue({dispenseCheck: this.editData.dispenseCheck});
      this.SDC = this.form.value.dispenseCheck;


      if(this.editData.bufferId){
        //load lot number list content for blockingBufferLot_user
        this.loadLotList(this.editData.bufferId, 'bufferLotNumList');
        }else{
          this.bufferLotNumList = [];
          console.log("buffer lot number list content is initialized (no itemId): ");
          console.log(this.bufferLotNumList);
        }

      //initialize isExcluded's value
      this.isExcluded = this.editData.isExcluded

    }
    if(this.reviewData){
      this.isExcluded = this.reviewData.isExcluded;
      this.SDC = this.reviewData.dispenseCheck;
    }

    this.formatTime();
    if(this.mode !== 'review'){
      this.updateInfo();
    }

    this.cd.detectChanges();

  } // End of ngOnInit



}
