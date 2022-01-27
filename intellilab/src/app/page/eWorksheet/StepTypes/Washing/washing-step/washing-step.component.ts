import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { AlertService } from 'app/page/eWorksheet/_alert';
import { WorksheetDesignService } from '../../../../../shared/services/eWorksheet.service';
import {BaseComponent} from '../../AbstractStep/base-step'

@Component({
  selector: 'app-washing-step',
  templateUrl: './washing-step.component.html',
  styleUrls: ['./washing-step.component.css']
})
export class WashingStepComponent extends BaseComponent implements OnInit {

programNames = ['PBST', 'Others'];

  constructor(ws_service: WorksheetDesignService, private cd: ChangeDetectorRef, public alertService: AlertService) {
    super(ws_service, alertService);
   }

  ngOnInit(): void {
    super.ngOnInit();

     //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({ bufferLot_user: "?" });
      this.form.patchValue({PBST: "?"})
    }

    //prepopulate user input values for experiment mode
    if(this.mode === "experiment"){
        this.form.patchValue({ bufferLot_user: this.editData.bufferLot_user});
        this.form.patchValue({dispenseCheck: this.editData.dispenseCheck});
        this.SDC = this.form.value.dispenseCheck;
        this.form.patchValue({PBST: this.editData.PBST});

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

    //prepopulate step configuration for edit mode, the edit mode is not defined by code, instead such a mode is determined by logic
    //when the "editData" input is defined, then this washing component will be set with "design", and the edit mode is kind of like a
    //special version of "design" mode
    if(this.editData){

      //load operation 1 values
      this.form.patchValue({buffer: this.editData.buffer});
      this.form.patchValue({supplier: this.editData.supplier});

      //load operation 2 values
      this.form.patchValue({dispenseValue: this.editData.dispenseValue});
      this.form.patchValue({dispenseUnit: this.editData.dispenseUnit});
      this.dispenseUnit_copy = this.form.value.dispenseUnit;

      //load operation 3 values
      this.form.patchValue({washTime: this.editData.washTime});
      this.form.patchValue({washValue: this.editData.washValue});
      this.form.patchValue({washUnit: this.editData.washUnit});
      this.washUnit_copy = this.form.value.washUnit;

      //load peripherals values
      this.bufferId = this.editData.bufferId;
      this.itemId_copy = this.bufferId;

    }

    if(this.reviewData){
      this.isExcluded = this.reviewData.isExcluded;
      this.SDC = this.reviewData.dispenseCheck;
    }

    if(this.mode !== 'review'){
      this.updateInfo();
    }

    this.cd.detectChanges();

  } // End of ngOnInit

}
