import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { AlertService } from 'app/page/eWorksheet/_alert';
import { WorksheetDesignService } from '../../../../../shared/services/eWorksheet.service';
// import { HIGH_CONTRAST_MODE_ACTIVE_CSS_CLASS } from '@angular/cdk/a11y/high-contrast-mode/high-contrast-mode-detector';


@Component({
  selector: 'app-sub-dilution',
  templateUrl: './sub-dilution.component.html',
  styleUrls: ['./sub-dilution.component.css']
})
export class SubDilutionComponent implements OnInit {
  form: FormGroup;
  concentrationUnits: string[];
  literUnits: string[];
  material: string;
  initUpdate: boolean = false;
  stockConcentrationUnit_copy;
  finalConcentrationUnit_copy;
  volume_userUnit_copy;
  dbid: number;

  roundRecord = {
    material:{
      isRound: false,
      originalValue: null,
      roundValue: null,
      message: null,
    },

    buffer:{
      isRound: false,
      originalValue: null,
      roundValue: null,
      message: null
    }
  }

  alertOptions={
    autoClose: true,
    keepAfterRouteChange: false
  }



  @Input() displayIndex: number;
  @Input() dataType = "design";
  @Input() mode: string = 'design';
  @Input() stepIndex: number;
  @Input() editData;
  @Input() reviewData;
  @Input() materialLabelName;
  @Input() dynamic_alertId = 'undefined';
  @Output() userData = new EventEmitter();
  @Output() subStepLock = new EventEmitter();
  constructor(public ws_service: WorksheetDesignService, public alertService: AlertService) { }

  ngOnInit(): void {
    // console.log("Sub dilution mode: " + this.mode);
    // console.log("Sub dilution datatype: " + this.dataType);
    // if(this.reviewData){
    //   console.log("review data for sub dilution step is: ");
    //   console.log(this.reviewData);
    // }
    // if(this.editData){
    //   console.log("edit data for sub dilution step is: ");
    //   console.log(this.editData);
    // }



    this.literUnits = this.ws_service.getLiterUnits();
    this.concentrationUnits = this.ws_service.getConcentrationUnits();

    this.form = new FormGroup({

      //operation 1 form control variables
      "stockConcentration": new FormControl(null, { validators: [Validators.required] }),
      "stockConcentrationUnit": new FormControl(null, { validators: [Validators.required] }),

      //operation 2 form control variables
      "finalConcentration": new FormControl(null, { validators: [Validators.required] }),
      "finalConcentrationUnit": new FormControl(null, { validators: [Validators.required] }),

      //operation 3 form control variables
      "volume_user": new FormControl(null, { validators: [Validators.required] }),
      "volume_userUnit": new FormControl(null, { validators: [Validators.required] }),

      //operation 4 form control variables
      "bufferVolume_sys": new FormControl(null, { validators: [Validators.required] }),
      "materialVolume_sys": new FormControl(null, { validators: [Validators.required] }),

    });


    //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({ volume_user: "?" });
      this.form.patchValue({ volume_userUnit: "?" });
      this.form.patchValue({ bufferVolume_sys: "?" });
      this.form.patchValue({ materialVolume_sys: "?" });
    }

    //prepopulate user input values for experiment mode
    if(this.mode === 'experiment'){
      this.form.patchValue({ volume_user: this.editData.volume_user });
      this.form.patchValue({ volume_userUnit: this.editData.volume_userUnit });
      this.volume_userUnit_copy = this.form.value.volume_userUnit;
      this.form.patchValue({ bufferVolume_sys: this.editData.bufferVolume_sys });
      this.form.patchValue({ materialVolume_sys: this.editData.materialVolume_sys });
    }

    if(this.editData){

      this.dbid = this.editData.dbid;

      //load operation 1 values
      this.form.patchValue({stockConcentration: this.editData.stockConcentration});
      this.form.patchValue({stockConcentrationUnit: this.editData.stockConcentrationUnit});
      this.stockConcentrationUnit_copy = this.form.value.stockConcentrationUnit;

      //load operation 2 values
      this.form.patchValue({finalConcentration: this.editData.finalConcentration});
      this.form.patchValue({finalConcentrationUnit: this.editData.finalConcentrationUnit});
      this.finalConcentrationUnit_copy = this.form.value.finalConcentrationUnit;

      //load peripherals values
      // this.buffer = this.editData.buffer;
    }

    //set the value of initUpdate to true when this component is initialized so then the updateInfo() will only update this component's step validity without updating the component's step data, therefore; avoid infinite loop during component initialization
    this.initUpdate = true;

    if(this.mode !== 'review'){
      this.updateInfo();
    }

  }

  onChangeUnit(event){
    switch(event.targetUnit){
      case  'stockConcentrationUnit':
        this.form.patchValue({stockConcentrationUnit: event.option});
        if(this.mode === 'experiment'){this.checkCalculationParams();}
        break;

      case  'finalConcentrationUnit':
        this.form.patchValue({finalConcentrationUnit: event.option});
        if(this.mode === 'experiment'){this.checkCalculationParams();}
        this.checkSubStepLock();
        break;

      case  'volume_userUnit':
        this.form.patchValue({volume_userUnit: event.option});
        if(this.mode === 'experiment'){this.checkCalculationParams();}
        break;
    }
    this.updateInfo();
  }


  updateInfo(){
    this.foldCheck()

    let stepConfig;

    if(this.mode === 'experiment'){
      stepConfig = {

        dbid: this.dbid,

        //operation 1 input values
        stockConcentration: this.form.value.stockConcentration,
        stockConcentrationUnit: this.form.value.stockConcentrationUnit,

        //operation 2 input values
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,

        //operation 3 input values
        volume_user: this.form.value.volume_user,
        volume_userUnit: this.form.value.volume_userUnit,

        //operation 4 input values
        bufferVolume_sys: this.form.value.bufferVolume_sys,
        materialVolume_sys: this.form.value.materialVolume_sys,

        //peripherals values
        // material: this.material,
        buffer: this.editData.buffer
      }
    }

    if(this.mode === 'design'){
      stepConfig = {

        dbid: this.dbid,

        //operation 1 input values
        stockConcentration: this.form.value.stockConcentration,
        stockConcentrationUnit: this.form.value.stockConcentrationUnit,

        //operation 2 input values
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,

        //peripherals values
        // material: this.material,
        buffer: this.editData.buffer
      }
    }




    if(this.form.invalid){

      if(this.initUpdate){
        this.userData.emit({
          stepIndex: this.stepIndex,
          stepValid: false
        });
        console.log("Initialize sub dilution validity: false");
      }else{
        console.log("sub dilution content is updated")
        this.userData.emit({
          stepIndex: this.stepIndex,
          stepData: stepConfig,
          stepValid: false
        });
      }

    }else{

      if(this.initUpdate){
        this.userData.emit({
          stepIndex: this.stepIndex,
          stepValid: true,
        });
        console.log("Initialize sub dilution validity: true");
      }else{
        console.log("Sub dilution content is all configured")
        this.userData.emit({
          stepIndex: this.stepIndex,
          stepData: stepConfig,
          stepValid: true,
        });
      }

    }
    this.initUpdate = false;
  }

  // parameter C1 is material stock concentration's value,
  // parameter C2 is final concentration's value,
  // parameter V2 is final volume (volume needed property)'s value, this function call will return the value for V1 with
  // the mathematical formula C1V1 = C2V2, for example 25,000 µl/mL X V1 = 250 µl/mL X 500 µl, so V1 is 5 µl
  sysCalculator(C1, C2, V2): number{

    // first of first, this function will transfer all incoming parameters with consistent unit for later calculation
    // both C1 and C2 will end up with µg/mL
    let uniC1;

    // will end up with µg/ml
    let uniC2;

    // will end up with µl
    let uniV2;


    // transfer C1 unit
    switch(this.form.value.stockConcentrationUnit){
      case "mg/mL":
        uniC1 = C1 * 1000;
        break;

      case "ng/mL":
        uniC1 = C1 / 1000;
        break;

      case "pg/mL":
        uniC1 = C1 / 1000000;
        break;

        // for µg/ml
      default:
        uniC1 = C1;
        break;
    }

    // transfer C2 unit
    switch(this.form.value.finalConcentrationUnit){
      case "mg/mL":
        uniC2 = C2 * 1000;
        break;

      case "ng/mL":
        uniC2 = C2 / 1000;
        break;

      case "pg/mL":
        uniC2 = C2 / 1000000;
        break;

        // for µg/ml
      default:
        uniC2 = C2;
        break;
    }


    // transfer V2 unit
    switch(this.form.value.volume_userUnit){
      case "mL":
        uniV2 = V2 * 1000;
        break;

        // for µl
      default:
        uniV2 = V2;
        break;
    }

    const V1 = uniC2 * uniV2 / uniC1;
    console.log(`system calculation formula for sub dilution step is: ${uniC1} µg/mL * V1 = ${uniC2} µg/mL * ${uniV2} µl. V1 is ${V1} µL`);

    return V1;
  }

  clearRoundRecord(target){
    switch(target){
      case 'material':
        this.roundRecord.material = {
          isRound: false,
          originalValue: null,
          roundValue: null,
          message: null,
        }
        break;

      case 'buffer':
        this.roundRecord.buffer = {
          isRound: false,
          originalValue: null,
          roundValue: null,
          message: null
        }
        break;
    }

  }

  round(beforeRoundValue, decimal:number){

    let num = 0

    // See if the coming value has any decimal part
        // '10.0' % 1; // returns 0
        // 10 % 1; // returns 0
        // '10.5' % 1; // returns 0.5
        // 10.5 % 1; // returns 0.5

    if(beforeRoundValue % 1 != 0){
      switch (decimal){
        case 0:
          this.roundRecord.material = {
            isRound: true,
            originalValue: beforeRoundValue,
            // keep whole number only
            roundValue: Math.round(beforeRoundValue),
            message: null
          }

          this.roundRecord.material.message = `System rounds this volume from ${beforeRoundValue} to ${this.roundRecord.material.roundValue}`
          num = this.roundRecord.material.roundValue
          break;

        case 3:
          // Check to see if there are at least 4 decimals for proceeding the following round process
          if((beforeRoundValue * 1000) % 1 != 0){
            this.roundRecord.buffer = {
              isRound: true,
              originalValue: beforeRoundValue,
              // keep no more than 3 decimals
              roundValue: Math.round((beforeRoundValue + Number.EPSILON) * 1000) / 1000,
              message: null
            }

            this.roundRecord.buffer.message = `System rounds this volume from ${beforeRoundValue} to ${this.roundRecord.buffer.roundValue}`
            num = this.roundRecord.buffer.roundValue
          }else{
            num = beforeRoundValue
          }

          break;
      }

      console.log('Round function is executed for ' + decimal + 'precision:');
      console.log(this.roundRecord)
    }else{
      console.log("Round function receives whole integer")
      num = beforeRoundValue
    }

    console.log('Round function is executed for ' + decimal + 'precision:');
    console.log(this.roundRecord)
    return num
  }

  leastVolumeCheck(){
    let unit = this.form.value.volume_userUnit
    let volume = this.form.value.volume_user

    if(unit && volume){
      const check = this.leastPipeCheck(volume, unit)

      if(check){
        return true
      }else{
        this.notifyVolume()
      }
    }

    this.clearRoundRecord('material')
    this.clearRoundRecord('buffer')
    this.form.patchValue({ materialVolume_sys: null });
    this.form.patchValue({ bufferVolume_sys: null });
    return false
  }

  notifyVolume(){
    // Clear out input fields when users provide invalid parameters for system calculation
    this.form.patchValue({ volume_user: null });
    console.log(`The least pipette volume is 10 µL`)

    // Config alert message
    let option;
    option = this.alertOptions
    option.autoClose = false
    option.id = `pipette-check-dilution-${this.dynamic_alertId}`
    this.alertService.warn(`Dilution ${this.displayIndex + 1}: The least volume required for pipette is 10 µL`, option)
  }

  notifySysResult(){
    // Clear out input values for "volume needed", and the 2 calculation results if either of those value is less then 10 µL
    this.form.patchValue({volume_user: null})
    this.form.patchValue({ materialVolume_sys: null });
    this.form.patchValue({ bufferVolume_sys: null });

    // Config alert message
    let option;
    option = this.alertOptions
    option.autoClose = false
    option.id = `pipette-check-dilution-${this.dynamic_alertId}`
    this.alertService.warn(`Dilution ${this.displayIndex + 1}: Calculation results are less than 10 µL, please provide a larger volume required`, option)
  }

  sysResultCheck(r1, r2){
    let materialV = r1
    let bufferV = r2
    if((materialV && bufferV) || materialV === 0 || bufferV === 0){
     const cm = this.leastPipeCheck(materialV, 'µL')
     const cb = this.leastPipeCheck(bufferV, 'µL')
     if(!cm || !cb){
      this.notifySysResult()
      this.clearRoundRecord('material')
      this.clearRoundRecord('buffer')
     }
    }else{
      console.log('Error: Function sysResultCheck() needs more parameters')
    }
  }

  // true: >= 10 µL
  leastPipeCheck(volume, unit){
    if(unit && volume){
      switch(unit){
        case 'mL':
          volume = volume * 1000
          break;

        case 'µL':
          break;
      }

      if(volume >= 10){return true}
      else{return false}


    }else{
      console.log('Function leastPipeCheck() needs more parameters')

    }
  }


  checkCalculationParams(){
    let volumeValid = true
    if(this.mode === 'experiment'){
      volumeValid = this.leastVolumeCheck();
      console.log('least volume check value: ' + volumeValid)
    }

    if(!volumeValid){
      console.log('Invalid volume required for system calculation for sub dilution')
      this.updateInfo()
      return
    }else{
      // Clear out the round record whenever the system recalculates volumes for material and buffer
    this.clearRoundRecord('material');
    this.clearRoundRecord('buffer');

    if(this.mode == 'experiment'){
      if( this.form.value.stockConcentration &&
          this.form.value.stockConcentrationUnit &&
          this.form.value.finalConcentration &&
          this.form.value.finalConcentrationUnit &&
          this.form.value.volume_user &&
          this.form.value.volume_userUnit
        ){
         const V1 = this.sysCalculator(this.form.value.stockConcentration, this.form.value.finalConcentration, this.form.value.volume_user);

         // Round V1's value with only whole integer
         const V1_round = this.round(V1, 0)
         this.form.patchValue({ materialVolume_sys: V1_round });

         let bufferVolume
         if(this.form.value.volume_userUnit == 'mL'){
            bufferVolume = this.form.value.volume_user * 1000 - V1;
         }
         if(this.form.value.volume_userUnit == 'µL'){
            bufferVolume = this.form.value.volume_user - V1;
         }

         // Round bufferVolume with no more that 3 decimals
         const V2_round = this.round(bufferVolume, 3)

         this.form.patchValue({ bufferVolume_sys: V2_round });

         this.sysResultCheck(V1_round, V2_round)
         this.updateInfo();
        }
    }
    }

  }

  checkSubStepLock(){
    // console.log("finalConcentration value: " + this.form.value.finalConcentration)
    // console.log("finalConcentrationUnit value: " + this.form.value.finalConcentrationUnit)
    if(this.form.get("finalConcentration").valid && this.form.get("finalConcentrationUnit").valid){
      this.subStepLock.emit({
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,
        stepLock: false
      })

      console.log("sub dilution step lock status is updated: " + false);
    }else{
      this.subStepLock.emit({
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,
        stepLock: true
      })

      console.log("sub dilution step lock status is updated: " + true);
    }


  }


  foldCheck(){
    if(this.form.value.stockConcentration &&
        this.form.value.stockConcentrationUnit &&
        this.form.value.finalConcentration &&
        this.form.value.finalConcentrationUnit){
          let s = this.form.value.stockConcentration
          let su = this.form.value.stockConcentrationUnit
          let f = this.form.value.finalConcentration
          let fu = this.form.value.finalConcentrationUnit

          s = this.ws_service.unifyConcentration(s, su)
          f = this.ws_service.unifyConcentration(f, fu)

          if(s/f > 100){
            this.form.patchValue({ finalConcentration: null });
            console.log(`Warning: dilution >100 fold, final concentration is reset by system`)
            let option;
            option = this.alertOptions
            option.autoClose = false
            option.id = `fold-check-solution-${this.dynamic_alertId}`
            this.alertService.warn(`Solution ${this.displayIndex + 1}: dilution cannot be greater than 100 fold`, option)
          }else if(s/f < 1){
            this.form.patchValue({ finalConcentration: null });
            console.log(`Error: dilution <1 fold, final concentration is reset by system`)
            let option;
            option = this.alertOptions
            option.id = `fold-check-solution-${this.dynamic_alertId}`
            this.alertService.warn(`Solution ${this.displayIndex + 1}: invalid dilution operation`, option)
          }else{
            console.log('valid dilution fold: ' + s/f)
          }

        }else{
          console.log('fold check in inactive')
        }
  }


}
