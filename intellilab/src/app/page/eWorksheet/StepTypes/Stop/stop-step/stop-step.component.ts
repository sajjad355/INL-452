import { Component, OnInit, Input, EventEmitter, Output, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { AlertService } from 'app/page/eWorksheet/_alert';
import { WorksheetDesignService } from '../../../../../shared/services/eWorksheet.service';
import {BaseComponent} from '../../AbstractStep/base-step'

@Component({
  selector: 'app-stop-step',
  templateUrl: './stop-step.component.html',
  styleUrls: ['./stop-step.component.css']
})
export class StopStepComponent extends BaseComponent implements OnInit {

  constructor(ws_service: WorksheetDesignService, private cd: ChangeDetectorRef, public alertService: AlertService) {
    super(ws_service, alertService);
   }

  ngOnInit(): void {
    super.ngOnInit();

     //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({ materialLot_user: "?" });
    }

    //prepopulate user input values for experiment mode
    if(this.mode === "experiment"){
      this.form.patchValue({ materialLot_user: this.editData.materialLot_user });
      this.form.patchValue({dispenseCheck: this.editData.dispenseCheck});
      this.SDC = this.form.value.dispenseCheck;

      if(this.editData.materialId){
        //load lot number list content for captureMaterialLot_user
        this.loadLotList(this.editData.materialId, 'materialLotNumList');
      }else{
        this.materialLotNumList = [];
        console.log("material lot number list content is initialized (no itemId): ");
        console.log(this.materialLotNumList);
      }

      //initialize isExcluded's value
      this.isExcluded = this.editData.isExcluded
    }

    //prepopulate step configuration for edit mode, the edit mode is not defined by code, instead such a mode is determined by logic
    //when the "editData" input is defined, then this washing component will be set with "design", and the edit mode is kind of like a
    //special version of "design" mode
    if(this.editData){

      //load operation 1 values
      this.form.patchValue({material: this.editData.material});
      this.form.patchValue({supplier: this.editData.supplier});

      //load operation 2 values
      this.form.patchValue({dispenseValue: this.editData.dispenseValue});
      this.form.patchValue({dispenseUnit: this.editData.dispenseUnit});
      this.dispenseUnit_copy = this.form.value.dispenseUnit;

      //load operation 3 values
      this.form.patchValue({readAtValue_1: this.editData.readAtValue_1});
      this.form.patchValue({readAtValue_2: this.editData.readAtValue_2});

      //load peripherals values
      this.materialId = this.editData.materialId;
      this.itemId_copy = this.materialId;

    }

    if(this.reviewData){
      this.isExcluded = this.reviewData.isExcluded;
      this.SDC = this.reviewData.dispenseCheck;
    }

    if(this.mode !== 'review'){
      this.updateInfo();
    }

    this.cd.detectChanges();
  }// End of ngOnInit

}
