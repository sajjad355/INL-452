import { Component, OnInit, Input, EventEmitter, Output, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { WorksheetDesignService } from 'app/shared/services/eWorksheet.service';
import { StepTimerComponent } from 'app/page/eWorksheet/Timer/step-timer.component';

@Component({
  selector: 'app-sub-incubation',
  templateUrl: './sub-incubation.component.html',
  styleUrls: ['./sub-incubation.component.css']
})
export class SubIncubationComponent implements OnInit {
  @ViewChild('timer_view')  timer_view : StepTimerComponent;
  panelTime;
  isTimerRunning: boolean = false;
  showPanelTime:boolean = false;
  isTimeOverDue: boolean = false;
  hidePanelText: boolean = false;
  isStepComplete: boolean = false;

  // isExcluded: number = 0;
  timerComplete: boolean = false;
  hideTimerRecord: boolean = true;
  incubateTimeUnit_copy;
  timeUnits: string[];
  form: FormGroup;
  disableTimerPanel: boolean = true;
  timer = {
    timeValueSet: null,
    timeUnitSet: null,
    timerStartDate: null,
    timerValue: null,
    complete: false,
    formattedTime: null,
    overdue: false
  }
  @Input()  parentConfig;
  @Input()  editData;
  @Input()  reviewData;
  @Input()  displayIndex: number;
  @Input()  mode: string = 'design';
  @Input()  index: number;
  @Input()  dataType: string;
  @Output() userData = new EventEmitter();

  constructor(public ws_service: WorksheetDesignService, private cd: ChangeDetectorRef) { }

  ngOnInit() {
    // this.parentConfig.incubateIndex = this.displayIndex;


    console.log(`Incubation ${this.parentConfig.displayIndex} mode: ${this.mode}` )
    console.log(`Incubation ${this.parentConfig.displayIndex} parentConfig: `)
    console.log(this.parentConfig)

    this.timeUnits = this.ws_service.getTimeUnits();

    this.form = new FormGroup({

      "incubateAtValue": new FormControl(null, { validators: [Validators.required] }),
      "incubateTimeValue": new FormControl(null, { validators: [Validators.required] }),
      "incubateTimeUnit": new FormControl(null, { validators: [Validators.required] }),
      "incubateShakingValue": new FormControl(null, { validators: [Validators.required] }),

    });

    if(this.editData){
      this.form.patchValue({incubateAtValue: this.editData.incubateAtValue});
      this.form.patchValue({incubateTimeValue: this.editData.incubateTimeValue});
      this.form.patchValue({incubateTimeUnit: this.editData.incubateTimeUnit});
      this.form.patchValue({incubateShakingValue: this.editData.incubateShakingValue});

      if(this.mode === 'experiment' && this.editData.timer){
        this.timer.timeValueSet = this.editData.incubateTimeValue
        this.timer.timeUnitSet = this.editData.incubateTimeUnit
        this.timer.timerStartDate = this.editData.timer.timerStartDate
        this.timer.timerValue = this.editData.timer.timerValue
        this.timer.complete = this.editData.timer.complete
        this.timer.formattedTime = this.editData.timer.formattedTime
        this.timer.overdue = false
      }

      if( this.editData.incubateAtValue &&
          this.editData.incubateTimeValue &&
          this.editData.incubateTimeUnit &&
          // the below condition will be evaluted as false if incubateShakingValue is 0
          //because the system will consider a 0 as false, so we need to specify such a speical case for when incubateShaking is 0
          (this.editData.incubateShakingValue || this.editData.incubateShakingValue === 0)){
            this.disableTimerPanel = false;
          }else{this.disableTimerPanel = true;}

    }

    if(this.mode === 'review'){
      this.disableTimerPanel = true;
    }

  }//End of ngOnInit

  updateInfo(){

    let config;
    if(this.mode === 'design'){
        config = {
          incubateIndex: this.editData.incubateIndex,
          valid: !this.form.invalid,
          incubateAtValue: this.form.value.incubateAtValue,
          incubateTimeValue: this.form.value.incubateTimeValue,
          incubateTimeUnit: this.form.value.incubateTimeUnit,
          incubateShakingValue: this.form.value.incubateShakingValue
        }
    }

    if(this.mode === 'experiment'){
      if(!this.form.invalid){
        this.configTimer();
      }
      config={
        incubateIndex: this.editData.incubateIndex,
        valid: !this.form.invalid && this.timer.complete,
        // timerValue: this.timer.timerValue,
        // timerStartDate: this.timer.timerStartDate,
        // timerComplete: this.timer.complete,
        incubateAtValue: this.form.value.incubateAtValue,
        incubateTimeValue: this.form.value.incubateTimeValue,
        incubateTimeUnit: this.form.value.incubateTimeUnit,
        incubateShakingValue: this.form.value.incubateShakingValue,
        timer: this.timer
      }
    }
      this.userData.emit(config)
      if(this.mode === 'experiment'){
        this.disableTimerPanel = this.form.invalid
        console.log("disable timer panel value: " + this.disableTimerPanel)
      }


  }

  onChangeUnit(event){
    this.form.patchValue({incubateTimeUnit: event.option});
    this.updateInfo();
  }

  updateTimerConfig(){
    if(!this.disableTimerPanel && !this.timer.complete && this.mode === 'experiment'){
      this.cd.detectChanges();
      this.timer_view.setReceiveTime(this.form.value.incubateTimeValue);
      this.timer_view.setReceiveTimeUnit(this.form.value.incubateTimeUnit);
    }
  }

  configTimer(){
    if(this.form.value.incubateTimeValue){
      this.timer.timeValueSet = this.form.value.incubateTimeValue
    }

    if(this.form.value.incubateTimeUnit){
      this.timer.timeUnitSet = this.form.value.incubateTimeUnit
    }
  }

  saveTime(event){
    console.log("timer event: ");
    console.log(event);
    this.timer.timerValue = event.timerValue;
    this.timer.complete = true;
    this.formatTime();
    this.updateInfo();
  }



  formatTime(){
    if(this.timer.timerValue > 0){
      this.timer.formattedTime = this.ws_service.formatTimerValue(this.timer.timerValue);
    }
  }



  displayEvent(){
    this.hidePanelText = !this.hidePanelText;
    console.log("panel expansion event content:")
    console.log(event);
  }



  notifyTime(event){
    this.isTimerRunning = true;
    this.panelTime = event.timeValue;
    this.showPanelTime = event.display;
    this.isTimeOverDue = event.overdue;
    if(this.isTimeOverDue !== this.timer.overdue){
      this.timer.overdue = event.overdue;
      this.updateInfo();
    }

  }



  saveTimerDate(event){
    this.timer.timerStartDate = event.startDate;

    console.log(`Incubation ${this.displayIndex} timer launches on ${this.timer.timerStartDate}`);
    this.updateInfo();
  }



  // changeTimerRunning(event){
  //   // this.activeTimer.emit({stepIndex: this.stepIndex, activeTimer: event.isRunning, isOverDue: event.isOverDue})
  //   if(event.isComplete && event.isComplete === true){
  //     this.timer.complete = true;
  //   }
  // }



  // checkTimerComplete(){
  //   if(this.mode === 'experiment' && this.editData.timer !== undefined && this.editData.timer === 0){
  //     this.timerComplete = false;
  //   }else{
  //     this.timerComplete = true;
  //   }
  //   console.log("check timer complete status: " + this.timerComplete)

  // }



  // checkTimerHide(){
  //   if(this.mode === 'experiment' || (this.mode === 'review' && this.dataType === 'instance')){
  //     this.hideTimerRecord = false;
  //   }else{
  //     this.hideTimerRecord = true;
  //   }
  // }


}
