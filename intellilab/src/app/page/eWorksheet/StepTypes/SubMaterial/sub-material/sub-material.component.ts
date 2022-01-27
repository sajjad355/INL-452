import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { WorksheetDesignService } from '../../../../../shared/services/eWorksheet.service';
import * as lodash from "lodash";
import { DropDownListComponent } from '../../../DropDownList/drop-down-list.component';
import { AlertService } from 'app/page/eWorksheet/_alert';

@Component({
  selector: 'app-sub-material',
  templateUrl: './sub-material.component.html',
  styleUrls: ['./sub-material.component.css']
})
export class SubMaterialComponent implements OnInit {
  @ViewChild('stockConcentrationUnit')  stockConcentrationUnit_view : DropDownListComponent;

  form: FormGroup;
  concentrationUnits: string[];
  literUnits: string[];
  materialId = null;
  materialId_copy = null;
  materialLotNumList = null;
  subStepArray = [];
  // buffer: string;
  parentConcentration: number;
  parentConcentrationUnit: string;
  lockSubDilution: boolean = false;
  indexGenerator: number = 0;
  showSubStep: boolean = false;
  stockDataArray = [];
  dbid: number;

  supplier_database = null;
  supplier_exist = true
  stockConcentration_database = null;
  stockConcentration_exist = true;
  stockConcentrationUnit_database = null;
  stockConcentrationUnit_exist = true;
  toolTipMessage = 'Input value does not exist in the database'
  itemExpired: boolean = false;

  stockConcentrationUnit_copy;
  finalConcentrationUnit_copy;
  volume_userUnit_copy;

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



  materialLabelName;
  itemNameLabel;
  itemSupplierLabel;
  dilutionLabel;
  addDilutionBtnName;
  removeDilutionBtnName;
  @Input() stepType: string;
  @Input() displayIndex: number;
  @Input() inventoryList = [];
  @Input()  editData;
  @Input()  reviewData;
  @Input()  dataType;
  @Input()  mode: string;
  @Input()  stepIndex:number;
  @Input()  dynamic_alertId = 'undefined';
  @Output() userData = new EventEmitter();
  constructor(public ws_service: WorksheetDesignService, private cd: ChangeDetectorRef, public alertService: AlertService) { }

  ngOnInit(): void {

    // Set up the label name for material according to the parent/control class's stepType
    if(this.stepType){
      switch(this.stepType){
        case 'Capture':
          this.materialLabelName = 'Capture Reagent'
          this.itemNameLabel = 'Reagent Name'
          this.itemSupplierLabel = 'Reagent Supplier'
          this.dilutionLabel = 'Solution Concentration'
          this.addDilutionBtnName = 'Make New Solution Concentration'
          this.removeDilutionBtnName = 'Remove Solution Concentration'
          break;

        case 'Coating':
          this.materialLabelName = 'Coating Protein'
          this.itemNameLabel = 'Protein Name'
          this.itemSupplierLabel = 'Protein Supplier'
          this.dilutionLabel = 'Coating Concentration'
          this.addDilutionBtnName = 'Make New Concentration'
          this.removeDilutionBtnName = 'Remove Solution Concentration'
          break;

        case 'Detection':
          this.materialLabelName = 'Detection Reagent'
          this.itemNameLabel = 'Reagent Name'
          this.itemSupplierLabel = 'Reagent Supplier'
          this.dilutionLabel = 'Solution Concentration'
          this.addDilutionBtnName = 'Make New Solution Concentration'
          this.removeDilutionBtnName = 'Remove Solution Concentration'
          break;

        case 'Test':
          this.materialLabelName = 'Material'
          this.itemNameLabel = 'Material Name'
          this.itemSupplierLabel = 'Material Supplier'
          this.dilutionLabel = 'Solution Concentration'
          this.addDilutionBtnName = 'Make New Solution Concentration'
          this.removeDilutionBtnName = 'Remove Solution Concentration'
          break;

        default:
          this.materialLabelName = 'Material'
          this.itemNameLabel = 'Material Name'
          this.itemSupplierLabel = 'Material Supplier'
          this.dilutionLabel = 'Solution Concentration'
          this.addDilutionBtnName = 'Make New Solution Concentration'
          this.removeDilutionBtnName = 'Remove Solution Concentration'

      }
    }else{
      console.log('!Missing incoming stepType value for setting up step labelling!')
    }

    console.log("SUB MATERIAL stepIndex: " + this.stepIndex)
    this.literUnits = this.ws_service.getLiterUnits();
    this.concentrationUnits = this.ws_service.getConcentrationUnits();

    console.log(`-------> ${this.stepType} ${this.stepIndex} dynamic alert ID: ${this.dynamic_alertId}`)
    this.form = new FormGroup({

      //operation 1 form control variables
      "material": new FormControl(null, { validators: [Validators.required] }),
      "supplier": new FormControl(null, { validators: [Validators.required] }),
      "stockConcentration": new FormControl(null, { validators: [Validators.required] }),
      "stockConcentrationUnit": new FormControl(null, { validators: [Validators.required] }),
      "materialLot_user": new FormControl(null, { validators: [Validators.required] }),

      //operation 2 form control variables
      "finalConcentration": new FormControl(null, { validators: [Validators.required] }),
      "finalConcentrationUnit": new FormControl(null, { validators: [Validators.required] }),

      //operation 3 form control variables
      "volume_user": new FormControl(null, { validators: [Validators.required] }),
      "volume_userUnit": new FormControl(null, { validators: [Validators.required] }),

      //operation 5 form control variables
      "bufferVolume_sys": new FormControl(null, { validators: [Validators.required] }),
      "materialVolume_sys": new FormControl(null, { validators: [Validators.required] }),

    });

    //prepopulate user input values for design mode
    if(this.mode === "design"){
      this.form.patchValue({  materialLot_user: "?" });
      this.form.patchValue({  volume_user: "?" });
      this.form.patchValue({  volume_userUnit: "?" });
      this.form.patchValue({  bufferVolume_sys: "?" });
      this.form.patchValue({  materialVolume_sys: "?" });
    }


    //prepopulate user input values for experiment mode
    if(this.mode === "experiment"){
      this.form.patchValue({ materialLot_user: this.editData.materialLot_user });
      this.form.patchValue({ volume_user: this.editData.volume_user });
      this.form.patchValue({ volume_userUnit: this.editData.volume_userUnit });
      this.volume_userUnit_copy = this.form.value.volume_userUnit
      this.form.patchValue({ bufferVolume_sys: this.editData.bufferVolume_sys});
      this.form.patchValue({ materialVolume_sys: this.editData.materialVolume_sys});

      if(this.editData.materialId){
        //load lot number list content for captureMaterialLot_user
        this.loadLotList(this.editData.materialId, 'materialLotNumList');
      }else{
        this.materialLotNumList = [];
        console.log("material lot number list content is initialized (no itemId): ");
        console.log(this.materialLotNumList);
      }

    }


    if(this.editData){
      this.dbid = this.editData.dbid;

      //load operation 1 values
      this.form.patchValue({material: this.editData.material});
      this.form.patchValue({supplier: this.editData.supplier});
      this.form.patchValue({stockConcentration: this.editData.stockConcentration});
      this.form.patchValue({stockConcentrationUnit: this.editData.stockConcentrationUnit});
      this.stockConcentrationUnit_copy = this.form.value.stockConcentrationUnit

      //load operation 2 values
      this.form.patchValue({finalConcentration: this.editData.finalConcentration});
      this.form.patchValue({finalConcentrationUnit: this.editData.finalConcentrationUnit});
      this.finalConcentrationUnit_copy = this.form.value.finalConcentrationUnit

      //load peripherals values
      // this.buffer = this.editData.buffer;
      this.materialId = this.editData.materialId;
      this.subStepArray = this.editData.subStepArray;

      if(this.subStepArray.length > 0){
        const lastItemIndex = this.subStepArray.length - 1
        this.indexGenerator = this.subStepArray[lastItemIndex].stepIndex + 1;
        console.log("init index generator value for sub dilution step is: " + this.indexGenerator);
      }

    }

    this.displaySubStepCheck()
    this.checkInitValid();
    if(this.subStepArray.length > 0){
      const index = this.subStepArray.length - 1;
      this.parentConcentration = this.subStepArray[index].stepData.finalConcentration;
      this.parentConcentrationUnit = this.subStepArray[index].stepData.finalConcentrationUnit;
    }

    this.cd.detectChanges();
    this.materialId_copy = this.materialId;
  } // End of ngOnInit

  initWarn(event){
    if(event.itemSupplier !== "" && event.itemSupplier !=="?"){
      this.supplier_database = event.itemSupplier
    }else{
      this.supplier_database = null
    }

    if(event.itemId){
      this.materialId_copy = event.itemId;
      this.materialId = event.itemId;
      console.log("Get materialId value from autocomplete intialization: " + this.materialId_copy);
      let concentration;
      let concentrationUnit;
      this.ws_service.searchItemDetail(event.itemId).subscribe(res => {
        res.inventoryDetails.forEach(item => {

          if(item.concentration &&
              (
                item.concentrationUnit === 'mg/mL' ||
                item.concentrationUnit === 'µg/mL' ||
                item.concentrationUnit === 'ng/mL' ||
                item.concentrationUnit === 'pg/mL'
              )
            ){
              this.stockDataArray.push({concentration: item.concentration, concentrationUnit: item.concentrationUnit})
            }
        })

        if(this.stockDataArray.length > 0){
          concentration = this.stockDataArray[0].concentration;
          concentrationUnit = this.stockDataArray[0].concentrationUnit;

          this.stockConcentration_database = concentration;
          this.stockConcentrationUnit_database = concentrationUnit;
          this.stockDataArray = [];
        }else{
          this.stockConcentration_database = null;
          this.stockConcentrationUnit_database = null;
        }

        this.checkInputExist('supplier')
        this.checkInputExist('stockConcentration')
        this.checkInputExist('stockConcentrationUnit')
      })//End of search item detail API call's subscription

    }else{
      this.stockConcentration_database = null;
      this.stockConcentrationUnit_database = null;
      this.checkInputExist('supplier')
      this.checkInputExist('stockConcentration')
      this.checkInputExist('stockConcentrationUnit')
    }
  }

  onChangeUnit(event){
    switch(event.targetUnit){
      case  'stockConcentrationUnit':
        this.form.patchValue({stockConcentrationUnit: event.option});
        if(this.mode === 'experiment'){this.checkCalculationParams();}
        this.checkInputExist('stockConcentrationUnit')
        break;

      case  'finalConcentrationUnit':
        this.form.patchValue({finalConcentrationUnit: event.option});
        this.displaySubStepCheck();
        break;

      case  'volume_userUnit':
        this.form.patchValue({volume_userUnit: event.option});
        if(this.mode === 'experiment'){this.checkCalculationParams();}
        break;
    }
    this.updateInfo();
  }

  checkInitValid(){
    let overallSubStepValid = true;
    this.subStepArray.forEach(subStep => {
      if(subStep.stepValid === false){
        overallSubStepValid = false;
      }
    })

    if(this.form.invalid || !overallSubStepValid){
      console.log("Sub material initial validity is false.");

      this.userData.emit({
        stepIndex: this.stepIndex,
        stepValid: false
      });
    }else{
      console.log("Sub material initial validity is true: ");
      this.userData.emit({
        stepIndex: this.stepIndex,
        stepValid: true,
      });
    }
  }

  checkInputExist(inputType){

    if(this.form.value.material){
      switch(inputType){
        case  'supplier':
          if(this.supplier_database){
            if(this.supplier_database.trim() === this.form.value.supplier.trim()){
              this.supplier_exist = true
              // this.materialId = this.materialId_copy
              // console.log("materialId value is recoverd with value: " + this.materialId);
            }else{
              this.supplier_exist = false
              this.materialId = null;
              console.log("materialId is cleared for supplier change")
            }
          }else{
            this.supplier_exist = false
            this.materialId = null;
            console.log("materialId is cleared for supplier change")
          }
          break;

        case  'stockConcentration':
          if(this.stockConcentration_database){
            if(+this.stockConcentration_database === +this.form.value.stockConcentration){
              this.stockConcentration_exist = true
            }else{
              this.stockConcentration_exist = false
              this.materialId = null;
              console.log("materialId is cleared for stock concentration change")
            }
          }else{
            this.stockConcentration_exist = false
            this.materialId = null;
            console.log("materialId is cleared for stock concentration change")
          }
          break;

        case  'stockConcentrationUnit':
          if(this.stockConcentrationUnit_database){
            if(this.stockConcentrationUnit_database === this.form.value.stockConcentrationUnit){
              this.stockConcentrationUnit_exist = true
            }else{
              this.stockConcentrationUnit_exist = false
              this.materialId = null;
              console.log("materialId is cleared for stock concentration unit change")
            }
          }else{
            this.stockConcentrationUnit_exist = false
            this.materialId = null;
            console.log("materialId is cleared for stock concentration unit change")
          }
          break;
      }
    }

    if(!this.form.value.supplier){
      this.supplier_exist = true
    }
    if(!this.form.value.stockConcentration){
      this.stockConcentration_exist = true
    }
    if(!this.form.value.stockConcentrationUnit){
      this.stockConcentrationUnit_exist = true
    }

    // check to see if the system should recover materialId's value
    if(this.supplier_exist && this.stockConcentration_exist && this.stockConcentrationUnit_exist){
      this.materialId = this.materialId_copy
      console.log("materialId value is recoverd with value: " + this.materialId);
    }
    this.updateInfo();
  }


  updateInfo(){
    this.foldCheck()

    let stepConfig;
    if(this.mode === "experiment"){

      stepConfig = {

        dbid: this.dbid,

        //operation 1 input values
        material: this.form.value.material,
        supplier: this.form.value.supplier,
        stockConcentration: this.form.value.stockConcentration,
        stockConcentrationUnit: this.form.value.stockConcentrationUnit,
        materialLot_user: this.form.value.materialLot_user,

        //operation 2 input values
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,

        //operation 3 input values
        volume_user: this.form.value.volume_user,
        volume_userUnit: this.form.value.volume_userUnit,

        //operation 5 input values
        bufferVolume_sys: this.form.value.bufferVolume_sys,
        materialVolume_sys: this.form.value.materialVolume_sys,

        //peripherals values
        buffer: this.editData.buffer,
        materialId: this.materialId,
        subStepArray: this.subStepArray
      }

    }

    if(this.mode === "design"){

      stepConfig = {

        dbid: this.dbid,

        //operation 1 input values
        material: this.form.value.material,
        supplier: this.form.value.supplier,
        stockConcentration: this.form.value.stockConcentration,
        stockConcentrationUnit: this.form.value.stockConcentrationUnit,

        //operation 2 input values
        finalConcentration: this.form.value.finalConcentration,
        finalConcentrationUnit: this.form.value.finalConcentrationUnit,

        //peripherals values
        buffer: this.editData.buffer,
        materialId: this.materialId,
        subStepArray: this.subStepArray
      }
    }

    let overallSubStepValid = true;
    this.subStepArray.forEach(subStep => {
      if(subStep.stepValid === false){
        overallSubStepValid = false;
      }
    })

    if(this.form.invalid || !overallSubStepValid){
      console.log("Please complete the sub material step");
      console.log(stepConfig)
      this.userData.emit({
        stepIndex: this.stepIndex,
        stepData: stepConfig,
        stepValid: false
      });
    }else{
      console.log("Sub material data: ");
      console.log(stepConfig);
      this.userData.emit({
        stepIndex: this.stepIndex,
        stepData: stepConfig,
        stepValid: true,
      });
    }

  }

  addSubDilution(){
    this.indexGenerator++;
    let dilutionConfig = {
      stepIndex: this.indexGenerator,
      stepValid: false,
      stepData: {
        dbid: -1,
        stockConcentration: this.parentConcentration || this.form.value.finalConcentration,
        stockConcentrationUnit: this.parentConcentrationUnit || this.form.value.finalConcentrationUnit,
        finalConcentration: null,
        finalConcentrationUnit:null,
        volume_user: null,
        volume_userUnit: null,
        bufferVolume_sys: null,
        materialVolume_sys: null,
        buffer: this.editData.buffer
      }
    }

    this.subStepArray.push(dilutionConfig);
    // console.log("sub material step is added: ");
    // console.log(dilutionConfig);

    this.lockSubDilution = true;
    this.updateInfo();
    // this.indexGenerator++;
  }

  removeSubDilution(){
    this.subStepArray.pop();
    this.lockSubDilution = false;

    // update the parent concentration and its unit wheneven users remove a sub dilution from the array
    const index = this.subStepArray.length - 1;
    if(index >= 0){
      this.parentConcentration = this.subStepArray[index].stepData.finalConcentration;
      this.parentConcentrationUnit = this.subStepArray[index].stepData.finalConcentrationUnit;
    }else{
      this.parentConcentration = this.form.value.finalConcentration;
      this.parentConcentrationUnit = this.form.value.finalConcentrationUnit;
    }

    this.updateInfo();
  }

  updateSubDilution(event){
    console.log("event body for updating sub dilution: ");
    console.log(event);
    this.subStepArray.forEach(step => {
      if(step.stepIndex === event.stepIndex){

          step.stepValid = event.stepValid
          if(event.stepData){
            step.stepData = event.stepData
          }

        }

      }

    )


    // console.log('sub dilution step array is updated');
    // console.log(this.subStepArray);
    this.updateInfo();
  }

  lockModifier(event){
    // console.log("sub dilution lock status is changed: " + event.stepLock);
    this.lockSubDilution = event.stepLock;
    this.parentConcentration = event.finalConcentration;
    this.parentConcentrationUnit = event.finalConcentrationUnit;
  }

  displaySubStepCheck(){
    if(this.form.value.finalConcentration && this.form.value.finalConcentrationUnit){
      this.showSubStep = true;
      this.lockSubDilution = false;
    }else{
      this.showSubStep = false
      this.subStepArray = []
      // empty the tracked parent concentration and its unit
      this.parentConcentration = null,
      this.parentConcentrationUnit = null
    }

    // console.log("showSubStep value is: " + this.showSubStep)
  }

  loadLotList(itemId, target){
    this.ws_service.searchItemDetail(itemId).subscribe(res => {
      console.log(`searching results for item detail API response (${target}): `);
      console.log(res);

      if(target == 'materialLotNumList'){
        const tempList = res.inventoryDetails.map(item => {
          if(item.lotnumber !== ""){
            return {
              lotnumber: item.lotNumber,
              expiredate: item.expiryDate
            }
          }
        })

        if((tempList.length == 1 && tempList[0] == undefined) || tempList.length == 0){
          this.materialLotNumList = [];
        }

        if(tempList.length > 1 || tempList[0] !== undefined){
          this.materialLotNumList = tempList.filter(lotNum => lotNum !== undefined)
        }

        console.log("material lot number list content is initialized: ");
        console.log(this.materialLotNumList);

      }

    })

      console.log("stock concentration data array: ");
      console.log(this.stockDataArray)
  }

  resetTrackedVariables(){
    this.supplier_database = null;
    this.supplier_exist = true
    this.stockConcentration_database = null;
    this.stockConcentration_exist = true;
    this.stockConcentrationUnit_database = null;
    this.stockConcentrationUnit_exist = true;
  }


  autoUpdate(event, target){

    let concentration;
    let concentrationUnit;
    if(target == 'item'){
      this.resetTrackedVariables();
      switch(event.controllerName){

        case "material":
          if(event.itemId){
            this.materialId = event.itemId
            console.log("materialId is updated: " + this.materialId);
            this.ws_service.searchItemDetail(this.materialId).subscribe(res => {
              console.log(`searching results for item detail API response (${target}): `);
              console.log(res);

              res.inventoryDetails.forEach(item => {

                if(item.concentration &&
                    (
                      item.concentrationUnit === 'mg/mL' ||
                      item.concentrationUnit === 'µg/mL' ||
                      item.concentrationUnit === 'ng/mL' ||
                      item.concentrationUnit === 'pg/mL'
                    )
                  ){
                    this.stockDataArray.push({concentration: item.concentration, concentrationUnit: item.concentrationUnit})
                  }

              })
              console.log("stock data array: ");
              console.log(this.stockDataArray);

              if(this.stockDataArray.length > 0){
                concentration = this.stockDataArray[0].concentration;
                concentrationUnit = this.stockDataArray[0].concentrationUnit;

                this.form.patchValue({stockConcentration: concentration});
                this.form.patchValue({stockConcentrationUnit: concentrationUnit});
                this.stockConcentrationUnit_view.setUnit(this.form.value.stockConcentrationUnit);
                this.stockDataArray = [];
                console.log("stockConcentration value: " + this.form.value.stockConcentration)
                console.log("stockConcentrationUnit value: " + this.form.value.stockConcentrationUnit)
                this.updateInfo();
              }else{
                this.form.patchValue({stockConcentration: null});
                this.form.patchValue({stockConcentrationUnit: null});
                this.stockConcentrationUnit_view.setUnit(null);
              }
              this.stockConcentration_database = this.form.value.stockConcentration;
              this.stockConcentrationUnit_database = this.form.value.stockConcentrationUnit;
            })//End of search item detail API call's subscription

          }else{
            this.materialId = null;
            this.form.patchValue({stockConcentration: null});
            this.form.patchValue({stockConcentrationUnit: null});
            this.stockConcentrationUnit_view.setUnit(null);
            console.log("materialId is updated: " + this.materialId);
          }

          this.form.patchValue({material: event.selection});

          if(event.itemSupplier !== "" && event.itemSupplier !=="?"){
            this.form.patchValue({supplier: event.itemSupplier});
          }else{
            this.form.patchValue({supplier: null});
          }
          this.supplier_database = this.form.value.supplier;
        break;
      }
    }

    if(target == 'lotNum'){
      this.itemExpired = event.expired;
      switch(event.controllerName){
        case "materialLot_user":
          this.form.patchValue({materialLot_user: event.selection});
          break;
      }
    }

    this.updateInfo();
    this.materialId_copy = this.materialId;
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

        // for µg/mL
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
    console.log(`system calculation formula for sub material step is: ${uniC1} µg/mL * V1 = ${uniC2} µg/mL * ${uniV2} µl. V1 is ${V1} µL`);

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
    option.id = `pipette-check-material-${this.dynamic_alertId}`
    this.alertService.warn(`Material ${this.displayIndex}: The least volume required for pipette is 10 µL`, option)
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
    option.id = `pipette-check-material-${this.dynamic_alertId}`
    this.alertService.warn(`Material ${this.displayIndex}: Calculation results are less than 10 µL, please provide a larger volume required`, option)
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

    console.log('least volume check value: ' + this.leastVolumeCheck())

    if(!this.leastVolumeCheck()){
      console.log('Invalid volume required for system calculation for sub material')
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

  addDilutionNotification(){
    let options;
        options = this.alertOptions;
        options.id = 'dilution-alert'
        this.alertService.warn('Please fill out the current dilution before adding a new one', options)
    // window.alert("\n\n - Please first config the current dilution before adding a new one.")
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
            option.id = `fold-check-material-${this.dynamic_alertId}`
            this.alertService.warn(`${this.materialLabelName} ${this.displayIndex}: dilution cannot be greater than 100 fold`, option)
          }else if(s/f < 1){
            this.form.patchValue({ finalConcentration: null });
            console.log(`Error: dilution <1 fold, final concentration is reset by system`)
            let option;
            option = this.alertOptions
            option.id = `fold-check-material-${this.dynamic_alertId}`
            this.alertService.warn(`${this.materialLabelName} ${this.displayIndex}: invalid dilution operation`, option)
          }else{
            console.log('valid dilution fold: ' + s/f)
          }

        }else{
          console.log('fold check in inactive')
        }
  }



}
