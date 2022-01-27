import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Input, Output, EventEmitter, OnInit, Directive } from '@angular/core';
import { WorksheetDesignService } from '../../../../shared/services/eWorksheet.service'
import * as lodash from "lodash";
import { AlertService } from '../../_alert';

@Directive()
export abstract class BaseComponent implements OnInit{

  form: FormGroup;
  literUnits: string[];
  concentrationUnits: string[];
  timeUnits: string[];
  showComment:boolean = false;
  // hideTimerRecord: boolean = true;
  // timerComplete: boolean = false;
  timerStartDate: number;
  timerValue:number = 0;
  formattedTimerValue:string;
  bufferId;
  bufferLotNumList = null;
  materialId;
  itemId_copy = null;
  materialLotNumList = null;
  isExcluded: number = 0;
  deviationColor: string = 'white';
  subStepInitData = [];
  subStepArray = [];
  subIncubationArray = [];
  // incubationInitData = [];
  incubationIndex = 0;
  indexGenerator: number = 0;
  initUpdate: boolean = false;
  isStepComplete: boolean = false;
  supplier_database = null;
  supplier_exist = true;
  lotNum_database = null;
  lotNum_exist = true
  toolTipMessage = 'Input value does not exist in the database'
  itemExpired = false;
  showEquipment: boolean = false;
  enableIncubation: boolean = false;
  stepConfig;
  equipments = [];
  dispenseUnit_copy;
  washUnit_copy;
  incubateTimeUnit_copy;
  SDC = false //Solution Dispensing Completed

  alertOptions={
    autoClose: true,
    keepAfterRouteChange: false
  }

  subMaterialHeader;
  addSubMaterialBtnName;
  removeSubMaterialBtnName;


  // variables for showing timer value when collapse the timer expansion panel
  hidePanelText: boolean = false;
  panelTime;
  showPanelTime:boolean = false;
  isTimeOverDue: boolean = false;



  @Input() stepType: string;
  @Input() inventoryList = [];
  @Input() enableComment:boolean = true;
  @Input() commentData = {
    experimentComment: null,
    reviewComment: null
  };
  @Input()  deviationData;
  @Input()  editData;
  @Input()  reviewData;
  @Input()  dataType: string;
  @Input()  mode: string;
  @Input()  stepIndex:number;
  @Output() userData = new EventEmitter();
  @Output() comment = new EventEmitter();
  @Output() excludeStep = new EventEmitter();
  // @Output() activeTimer = new EventEmitter();


  constructor(public ws_service: WorksheetDesignService, public alertService: AlertService) {}

  ngOnInit(): void{
    this.checkExcluded();
    console.log(`${this.stepType}'s stepIndex is: ${this.stepIndex}`)
    this.initUpdate = true;
    this.literUnits = this.ws_service.getLiterUnits();
    this.concentrationUnits = this.ws_service.getConcentrationUnits();
    this.timeUnits = this.ws_service.getTimeUnits();

    console.log("Mode Type: " + this.mode)
    console.log("Data Type:" + this.dataType);

    if(this.mode == "review"){
      this.equipments = this.reviewData.equipments;
      console.log("review data for step:");
      console.log(this.reviewData);
    }
    if(this.editData){
      if(this.mode === 'experiment'){
        this.equipments = this.editData.equipments;

      }
      console.log(`edit data for ${this.stepType}:` );
      console.log(this.editData);
    }else{
      console.log("create new step");
    }

    if(this.commentData.experimentComment && this.mode === 'experiment'){
      this.showComment = true;
    }

    if(this.deviationData){
      this.deviationColor = this.setDeviationColor(this.deviationData.severity);
    }

    switch(this.stepType){
      case  'Blocking':
      case  'Capture':
      case  'Coating':
      case  'Detection':
      case  'Test':
        this.form = new FormGroup({
          //operation 1 form control variables
          "buffer": new FormControl(null, { validators: [Validators.required,]}),
          "supplier": new FormControl(null, { validators: [Validators.required] }),
          "bufferLot_user": new FormControl(null, { validators: [Validators.required] }),

          //operation 3 form control variables
          "dispenseValue": new FormControl(null, { validators: [Validators.required] }),
          "dispenseUnit" : new FormControl(null, { validators: [Validators.required] }),
          "dispenseCheck" : new FormControl(false),

        });
        if(!this.editData && !this.reviewData){
          this.initSubMaterialArray();
        }
        break;

      case  'Substrate':
        this.form = new FormGroup({
          //operation 1 form control variables
          "material": new FormControl(null, { validators: [Validators.required,]}),
          "supplier": new FormControl(null, { validators: [Validators.required] }),
          "materialLot_user": new FormControl(null, { validators: [Validators.required] }),

          //operation 3 form control variables
          "dispenseValue": new FormControl(null, { validators: [Validators.required] }),
          "dispenseUnit" : new FormControl(null, { validators: [Validators.required] }),
          "dispenseCheck" : new FormControl(false),

        });
        break;

      case  'Stop':
        this.form = new FormGroup({

          //operation 1 form control variables
          "material": new FormControl(null, { validators: [Validators.required,]}),
          "supplier": new FormControl(null, { validators: [Validators.required] }),
          "materialLot_user": new FormControl(null, { validators: [Validators.required] }),

          //operation 2 form control variables
          "dispenseValue": new FormControl(null, { validators: [Validators.required] }),
          "dispenseUnit" : new FormControl(null, { validators: [Validators.required] }),
          "dispenseCheck" : new FormControl(false),

          //operation 3 form control variables
          "readAtValue_1": new FormControl(null, { validators: [Validators.required] }),
          "readAtValue_2": new FormControl(null, { validators: [Validators.required] }),


        });
        break;

      case  'Washing':
        this.form = new FormGroup({
          //operation 1 form control variables
         "buffer": new FormControl(null, { validators: [Validators.required,]}),
         "supplier": new FormControl(null, { validators: [Validators.required] }),
         "bufferLot_user": new FormControl(null, { validators: [Validators.required] }),

          //operation 2 form control variables
         "dispenseValue": new FormControl(null, { validators: [Validators.required] }),
         "dispenseUnit" : new FormControl(null, { validators: [Validators.required] }),
         "dispenseCheck" : new FormControl(false),

          //operation 3 form control variables
         "washTime": new FormControl(null, { validators: [Validators.required] }),
         "washValue": new FormControl(null, { validators: [Validators.required] }),
         "washUnit": new FormControl(null, { validators: [Validators.required] }),

         //operation 4 form control variables
         "PBST": new FormControl(null, { validators: [Validators.required] }),
       });
        break;

      case  'Calibrator Preparation':
      case  'Sample Dilution':
      case  'QC Preparation':
      case  'MRD':
        this.form = new FormGroup({
          //operation 1 form control variables
         "material": new FormControl(null, { validators: [Validators.required]}),
         "supplier": new FormControl(null, { validators: [Validators.required] }),
         "materialLot_user": new FormControl(null, { validators: [Validators.required] }),
         "files": new FormControl(null),
        //  "files": new FormControl(null, { validators: [Validators.required] }),
       });
        break;


      default:
       console.log("stepType value is: " + this.stepType);

    }
    // this.checkTimerComplete();
    // this.checkTimerHide()

    if(this.mode !== 'review'){
      switch(this.stepType){
        case  'Blocking':
        case  'Capture':
        case  'Coating':
        case  'Detection':
        case  'Test':
        case  'Substrate':
          if(this.subIncubationArray.length === 0){
            this.incubationIndex++;
            let incubationConfig = {
              // dbid: -1,
              incubateIndex: this.incubationIndex,
              valid: false,
              incubateAtValue: null,
              incubateTimeValue: null,
              incubateTimeUnit: null,
              incubateShakingValue: null,
            }
            this.subIncubationArray.push(incubationConfig);
            // this.incubationInitData.push(incubationConfig);
          }
          if(this.editData && this.editData.incubationArray.length > 0){
            this.subIncubationArray = lodash.cloneDeep(this.editData.incubationArray)
            // this.subIncubationArray = this.editData.incubationArray;
            this.incubationIndex = this.subIncubationArray[this.subIncubationArray.length - 1].incubateIndex + 1;
            console.log("Initialize incubation index with value: " + this.incubationIndex);
          }
        break;
      }
    }else{
      this.subIncubationArray = lodash.cloneDeep(this.reviewData.incubationArray || [])
    }

    // set up the header name for sub materials in different steps
    switch(this.stepType){
      case 'Capture':
        this.subMaterialHeader = 'Capture Reagent'
        this.addSubMaterialBtnName = 'Add New Reagent'
        this.removeSubMaterialBtnName = 'Remove Reagent'
        break;

      case 'Coating':
        this.subMaterialHeader = 'Coating Protein'
        this.addSubMaterialBtnName = 'Add New Protein'
        this.removeSubMaterialBtnName = 'Remove Protein'
        break;

      case 'Detection':
        this.subMaterialHeader = 'Detection Reagent'
        this.addSubMaterialBtnName = 'Add New Reagent'
        this.removeSubMaterialBtnName = 'Remove Reagent'
        break;

      case 'Test':
        this.subMaterialHeader = 'Material'
        this.addSubMaterialBtnName = 'Add New Material'
        this.removeSubMaterialBtnName = 'Remove Material'
        break;

      default:
        this.subMaterialHeader = 'Material'
        this.addSubMaterialBtnName = 'Add New Material'
        this.removeSubMaterialBtnName = 'Remove Material'

    }



  } // End of ngOnInit





  checkExcluded(){
    if(this.mode === 'experiment'){
      if(this.editData){
        this.isExcluded = this.editData.isExcluded || 0
        console.log(`isExcluded property for ${this.stepType} is initialized with value: ${this.isExcluded}`)
      }
    }
  }







  // Section 1: functions for input warning
    initWarn(event){
      if(event.itemSupplier !== "" && event.itemSupplier !=="?"){
        this.supplier_database = event.itemSupplier
      }else{
        this.supplier_database = null
      }

      if(event.itemId){
        this.materialId = event.itemId;
        this.bufferId = event.itemId;
        this.itemId_copy = event.itemId;
        console.log("Get item ID for materialId or bufferId recovery from autocomplete input initialization: " + this.itemId_copy)
      }
      this.checkInputExist()
    }



    checkInputExist(){
      if(this.form.value.material || this.form.value.buffer){
            if(this.supplier_database){
              if(this.supplier_database.trim() === this.form.value.supplier.trim()){
                this.supplier_exist = true
              }else{
                this.supplier_exist = false
                this.materialId = null;
                this.bufferId = null;
                console.log("item ID is cleared for supplier change (both materialId and bufferId)")
              }
            }else{
              this.supplier_exist = false
              this.materialId = null;
              this.bufferId = null;
              console.log("item ID is cleared for supplier change (both materialId and bufferId)")
            }
      }

      if(!this.form.value.supplier){
        this.supplier_exist = true
      }

      // check to see if the system should recover materialId or bufferId for the current item name
      if(this.supplier_exist){
        this.materialId = this.itemId_copy;
        this.bufferId = this.itemId_copy;
        console.log("item ID is recovered with value: " + this.materialId + "(materialId) + " + this.bufferId + "(bufferId)")
      }
      this.updateInfo();
    }



    resetTrackedVariables(){
      this.supplier_database = null;
      this.supplier_exist = true
    }
  // End of Section 1


  // Section 3: functions for step comment
    updateComment(event){
      const comment = {
        stepIndex: this.stepIndex,
        commentType: event.commentType,
        stepComment: event.comment
      }


      this.comment.emit(comment);
      console.log("step comment is updated: ");
      console.log(comment);
    }




    cancelComment(event){
      this.showComment = false;

      const comment = {
        stepIndex: this.stepIndex,
        commentType: event.commentType,
        stepComment: null
      }

      this.comment.emit(comment);
      console.log("step comment is canceled: ");
      console.log(comment);
    }

  // End of Section 3













  // Section 4: functions for timer (at parent level)
    saveTime(event){
      console.log("timer event: ");
      console.log(event);
      this.timerValue = event.timerValue;
      // this.timerComplete = true;
      this.formatTime();
      this.updateInfo();
    }



    formatTime(){
      if(this.timerValue > 0){
        this.formattedTimerValue = this.ws_service.formatTimerValue(this.timerValue);
      }
    }



    displayEvent(){
      this.hidePanelText = !this.hidePanelText;
      console.log("panel expansion event content:")
      console.log(event);
    }



    notifyTime(event){
      this.panelTime = event.timeValue;
      this.showPanelTime = event.display;
      this.isTimeOverDue = event.overdue;
    }



    saveTimerDate(event){
      this.timerStartDate = event.startDate;

      console.log(`${this.stepType} timer launches on ${this.timerStartDate}`);
      this.updateInfo();
    }



  // Section 5: functions for input's autocomplete features at parent level

    autoUpdate(event, target){

      if(target == 'item'){
        this.resetTrackedVariables();
        switch(event.controllerName){

          case "buffer":
            if(event.itemId){
              this.bufferId = event.itemId;
              this.itemId_copy = this.bufferId;
              console.log("bufferId is updated: " + this.bufferId);
            }else{
              this.bufferId = null;
              console.log("bufferId is updated: " + this.bufferId);
            }

            this.form.patchValue({buffer: event.selection});

            if(event.itemSupplier !== "" && event.itemSupplier !=="?"){
              this.form.patchValue({supplier: event.itemSupplier});
            }else{
              this.form.patchValue({supplier: null});
            }
            this.supplier_database = this.form.value.supplier;
            this.detectBufferChange(event.selection);

          break;

          case "material":
            if(event.itemId){
              this.materialId = event.itemId;
              this.itemId_copy = this.materialId;
              console.log("materialId is updated: " + this.materialId);
            }else{
              this.materialId = null;
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
        console.log("item expired value: " + this.itemExpired);
        switch(event.controllerName){
          case "bufferLot_user":
            this.form.patchValue({bufferLot_user: event.selection});
            break;

          case "materialLot_user":
            this.form.patchValue({materialLot_user: event.selection});
            break;
        }
      }


      this.updateInfo();
    }








    loadLotList(itemId, target){
      this.ws_service.searchItemDetail(itemId).subscribe(res => {
        console.log(`searching results for item detail API response (${target}): `);
        console.log(res);
        let tempList = [];
        if(target == 'bufferLotNumList'){
          tempList = res.inventoryDetails.map(item => {
            if(item.lotnumber !== ""){
              return {
                lotnumber: item.lotNumber,
                expiredate: item.expiryDate
              }
            }
          })


          if((tempList.length == 1 && tempList[0] == undefined) || tempList.length == 0){
            this.bufferLotNumList = [];
          }

          if(tempList.length > 1 || tempList[0] !== undefined){
            this.bufferLotNumList = tempList.filter(lotNum => lotNum !== undefined)
          }

          console.log("buffer lot number list content is initialized: ");
          console.log(this.bufferLotNumList);

        }

        if(target == 'materialLotNumList'){
          tempList = res.inventoryDetails.map(item => {
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
    }

  // End of Section 5














  // Section 6: functions for sub material features at parent level
    initSubMaterialArray(){
      this.indexGenerator++;
      let materialConfig = {
        stepIndex: this.indexGenerator,
        stepValid: false,

        stepData: {

          dbid: -1,

          //operation 1 input values
          material: null,
          supplier: null,
          stockConcentration: null,
          stockConcentrationUnit: null,
          // materialLot_user: null,

          //operation 2 input values
          finalConcentration: null,
          finalConcentrationUnit: null,

          //operation 3 input values
          volume_user: null,
          volume_userUnit: null,

          //operation 5 input values
          bufferVolume_sys: null,
          materialVolume_sys: null,

          //peripherals values
          buffer: this.form.value.buffer,

          subStepArray: []
        }
      }
      this.subStepArray.push(materialConfig);
      console.log("Sub material array is initialized.")

    }


    addSubMaterial(){

      this.indexGenerator++;
      let materialConfig = {
        stepIndex: this.indexGenerator,
        stepValid: false,

        stepData: {

          dbid: -1,

          //operation 1 input values
          material: null,
          supplier: null,
          stockConcentration: null,
          stockConcentrationUnit: null,
          // materialLot_user: null,

          //operation 2 input values
          finalConcentration: null,
          finalConcentrationUnit: null,

          //operation 3 input values
          volume_user: null,
          volume_userUnit: null,

          //operation 5 input values
          bufferVolume_sys: null,
          materialVolume_sys: null,

          //peripherals values
          buffer: this.form.value.buffer,

          subStepArray: []
        }
      }

      if(this.form.value.buffer){

        this.subStepInitData.push(materialConfig);
        this.subStepArray.push(materialConfig);
        console.log("sub material step is added: ");
        console.log(materialConfig);
      }else{
        let options;
        options = this.alertOptions;
        options.id = 'miss-buffer'
        this.alertService.warn('Please provide a buffer name first', options)
        // window.alert("\n - Please fill buffer name first.")
      }


      this.updateInfo();
      // this.indexGenerator++;
    }




    removeSubMaterial(){
      // this.subStepInitData.pop();
      if(this.subStepArray.length > 1){
        this.subStepArray.pop();
        console.log("sub material array removes one item: ");
        console.log(this.subStepArray);
        this.updateInfo();
      }

    }




    updateSubMaterial(event){
      console.log("event body for updating sub material: ");
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


      console.log('sub material step array is updated');
      console.log(this.subStepArray);
      this.updateInfo();
    }

  // End of Section 6















  // Section 7: front-end functions for worksheet deviation process
    setDeviationColor(serverityLevel){
      let color;
      switch(serverityLevel){
        case 'H':
          color = 'red'
          break;

        case 'M':
          color = '#FB8C00'
          break;

        case 'L':
          color = 'green'
          break;

        default:
          color = 'white'
          break;
      }

      if(this.reviewData.isExcluded === 1){
        color = 'white'
      }

      return color;
    }
  // End of Section 7















  // Section 8: functions for input value update at step level
    onChangeUnit(event){
      switch(event.targetUnit){
        case  'dispenseUnit':
          this.form.patchValue({dispenseUnit: event.option});
          break;

        case  'washUnit':
          this.form.patchValue({washUnit: event.option});
          break;

        case  'incubateTimeUnit':
          this.form.patchValue({incubateTimeUnit: event.option});
          break;
      }
      this.updateInfo();
    }




    skipStep(){
      if(this.isExcluded === 0){
        this.isExcluded = 1;
      }else{
        this.isExcluded = 0;
      }

      console.log("isExcluded's new value is: " + this.isExcluded);


      if(this.isExcluded === 0){
        this.updateInfo();
      }else{
        this.isStepComplete = true;
        this.excludeStep.emit({
          stepIndex: this.stepIndex,
          isExcluded: this.isExcluded
        })
      }

    }




    detectBufferChange(bufferName){
      this.subStepArray.forEach(subMaterial => {
        if(subMaterial.stepData.subStepArray.length > 0)
        subMaterial.stepData.subStepArray.forEach(subDilution => {
          subDilution.stepData.buffer = bufferName
        })

        subMaterial.stepData.buffer = bufferName;
      })

    }






  updateInfo(){

    if(this.mode === "experiment"){
      switch(this.stepType){
        case  'Blocking':
        case  'Capture':
        case  'Coating':
        case  'Detection':
        case  'Test':
          this.stepConfig = {

            //operation 1 input values
            buffer: this.form.value.buffer,
            supplier: this.form.value.supplier,
            bufferLot_user: this.form.value.bufferLot_user,

            //operation 3 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 4 input values
            incubationArray: this.subIncubationArray,

            //peripherals values
            bufferId: this.bufferId,
            isExcluded: this.isExcluded
          }
          break;

        case  'Substrate':
          this.stepConfig = {

            //operation 1 input values
            material: this.form.value.material,
            supplier: this.form.value.supplier,
            materialLot_user: this.form.value.materialLot_user,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            incubationArray: this.subIncubationArray,

            //peripherals values
            materialId: this.materialId,
            isExcluded: this.isExcluded

          }
          break;

        case  'Stop':
          this.stepConfig = {

            //operation 1 input values
            material: this.form.value.material,
            supplier: this.form.value.supplier,
            materialLot_user: this.form.value.materialLot_user,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            readAtValue_1: this.form.value.readAtValue_1,
            readAtValue_2: this.form.value.readAtValue_2,

            //peripherals values
            materialId: this.materialId,
            isExcluded: this.isExcluded

          }
          break;

        case 'Washing':
          this.stepConfig = {

            //operation 1 input values
            buffer: this.form.value.buffer,
            supplier: this.form.value.supplier,
            bufferLot_user: this.form.value.bufferLot_user,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit: this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            washTime: this.form.value.washTime,
            washValue: this.form.value.washValue,
            washUnit: this.form.value.washUnit,

            PBST: this.form.value.PBST,

            //peripherals values
            bufferId: this.bufferId,
            isExcluded: this.isExcluded
        }
          break;

        case  'Calibrator Preparation':
        case  'Sample Dilution':
        case  'QC Preparation':
        case  'MRD':
          this.stepConfig = {

            //operation 1 input values
            material: this.form.value.material,
            supplier: this.form.value.supplier,
            materialLot_user: this.form.value.materialLot_user,

            //operation 2 input values
            files: this.form.value.files,

            //peripherals values
            materialId: this.materialId,
            isExcluded: this.isExcluded

        }
          break;
      }

      this.stepConfig.equipments = this.equipments;
    } //End of if for condition === experiment


    if(this.mode === "design"){

      switch(this.stepType){
        case  'Blocking':
        case  'Capture':
        case  'Coating':
        case  'Detection':
        case  'Test':
            // no lot number values and volume

            this.stepConfig = {

            //operation 1 input values
            buffer: this.form.value.buffer,
            supplier: this.form.value.supplier,

            //operation 6 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 7 input values
            incubationArray: this.subIncubationArray,

            //peripherals values
            bufferId: this.bufferId,

          }
          break;

        case  'Substrate':
          // no lot number value

          this.stepConfig = {

            //operation 1 input values
            material: this.form.value.material,
            supplier: this.form.value.supplier,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            incubationArray: this.subIncubationArray,

            //peripherals values
            materialId: this.materialId,

          }
          break;

        case  'Stop':
          // no lot number value

          this.stepConfig = {

            //operation 1 input values
            material: this.form.value.material,
            supplier: this.form.value.supplier,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit : this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            readAtValue_1: this.form.value.readAtValue_1,
            readAtValue_2: this.form.value.readAtValue_2,

            //peripherals values
            materialId: this.materialId,
          }
          break;

        case 'Washing':
          this.stepConfig = {
            //operation 1 input values
            buffer: this.form.value.buffer,
            supplier: this.form.value.supplier,

            //operation 2 input values
            dispenseValue: this.form.value.dispenseValue,
            dispenseUnit: this.form.value.dispenseUnit,
            dispenseCheck: this.form.value.dispenseCheck,

            //operation 3 input values
            washTime: this.form.value.washTime,
            washValue: this.form.value.washValue,
            washUnit: this.form.value.washUnit,

            //peripherals values
            bufferId: this.bufferId,
            }
          break;

          case  'Calibrator Preparation':
          case  'Sample Dilution':
          case  'QC Preparation':
          case  'MRD':
            this.stepConfig = {

              //operation 1 input values
              material: this.form.value.material,
              supplier: this.form.value.supplier,

              //peripherals values
              materialId: this.materialId,

          }
            break;
      }


    }


    let overallSubStepValid = true;

    switch(this.stepType){
        case  'Capture':
        case  'Coating':
        case  'Detection':
        case  'Test':
          this.stepConfig.subStepArray = this.subStepArray;

          this.subStepArray.forEach(subStep => {
            if(subStep.stepValid === false){
              overallSubStepValid = false;
            }
          })
          break;
    }

    let overallSubIncubationValid = true;
    switch(this.stepType){
      case  'Blocking':
      case  'Capture':
      case  'Coating':
      case  'Detection':
      case  'Test':
      case  'Substrate':
        this.subIncubationArray.forEach(incubation => {
          if(!incubation.valid){
            overallSubIncubationValid = false
          }
        })
      break;
    }

    if(this.mode === 'experiment'){
      if(!this.form.invalid && overallSubStepValid){
        this.enableIncubation = true;
      }else{
        this.enableIncubation = false;
      }
    }else{
      this.enableIncubation = true;
    }

    console.log(`overallSubIncubationValid for ${this.stepType}: ${overallSubIncubationValid}`)
    console.log(`overallSubStepValid for ${this.stepType}: ${overallSubStepValid}`)

    console.log(`Form validity for step ${this.stepType}: ${!this.form.invalid}`)
    console.log(this.form)

    if((this.form.invalid && this.isExcluded === 0) || (!overallSubStepValid && this.isExcluded === 0) || (!overallSubIncubationValid && this.isExcluded === 0)){
      this.isStepComplete = false;
        if(this.initUpdate){
        this.userData.emit({
        stepIndex: this.stepIndex,
        valid: false,
        // valid: this.mode === 'experiment' && this.editData && this.editData.isExcluded === 1 ? true : false
      });
      console.log(`Initialize ${this.stepType} step validity: false`)
    }else{
      console.log(`${this.stepType} step content is updated`);
      this.userData.emit({
        stepIndex: this.stepIndex,
        stepData: this.stepConfig,
        valid: false,
        // valid: this.mode === 'experiment' && this.editData && this.editData.isExcluded === 1 ? true : false
      });
      }

    }else{
      this.isStepComplete = true
      if(this.initUpdate){
        this.userData.emit({
          stepIndex: this.stepIndex,
          valid: true
        });
        console.log(`Initialize ${this.stepType} step validity: true`)
      }else{
        console.log(`${this.stepType} step is all configured`);
        this.userData.emit({
          stepIndex: this.stepIndex,
          stepData: this.stepConfig,
          valid: true,
        });
      }

    }
    this.initUpdate = false;

  }

  // End of Section 8



  // Section 9: functions for sub incubation
  addSubIncubation(){
    this.incubationIndex++;
    let incubationConfig = {
      // dbid: -1,
      incubateIndex: this.incubationIndex,
      valid: false,
      incubateAtValue: null,
      incubateTimeValue: null,
      incubateTimeUnit: null,
      incubateShakingValue: null,
    }
    this.subIncubationArray.push(incubationConfig);
    console.log("Add new sub incubation:");
    console.log(this.subIncubationArray);
    this.updateInfo();
  }

  removeSubIncubation(){
    if(this.subIncubationArray.length > 1){
      const lastIndex = this.subIncubationArray.length - 1
      if(this.subIncubationArray[lastIndex].timer){
        if(!this.subIncubationArray[lastIndex].timer.complete){
          this.subIncubationArray.pop();
        }
      }else{
        this.subIncubationArray.pop();
      }

      console.log("sub incubation array removes one item: ");
      console.log(this.subIncubationArray);
      this.updateInfo();
    }

  }

  updateSubIncubation(event){
    console.log("event body for updating sub incubation: ");
    console.log(event);
    this.subIncubationArray.forEach(incubation => {
      if(incubation.incubateIndex === event.incubateIndex){
          // incubation.index = event.index
          incubation.valid = event.valid
          incubation.incubateAtValue = event.incubateAtValue
          incubation.incubateTimeValue = event.incubateTimeValue
          incubation.incubateTimeUnit = event.incubateTimeUnit
          incubation.incubateShakingValue = event.incubateShakingValue

          if(event.timer){
            incubation.timer = event.timer
          }


        }

      }

    )


    console.log('sub incubation array is updated');
    console.log(this.subIncubationArray);
    this.updateInfo();
  }
  // End of Section 9

  // Section 10: functions for equipments
  updateEquip(event){

    if(event.equipments){
      this.equipments = event.equipments
    }else{
      this.equipments = []
    }
    console.log(`Equipment list for step ${this.stepType} is updated: `);
    console.log(this.equipments);
    this.updateInfo();
  }
  // End of Section 10


  // Section 11: functions for plate dispensing check
  toggleSDC(){
    const temp = !this.SDC
    this.form.patchValue({dispenseCheck: temp});
    this.SDC = temp
    console.log(`dispenseCheck: ${this.form.value.dispenseCheck}`)
    this.updateInfo()
  }








































}
// End of component
