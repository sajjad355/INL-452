import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { WorksheetDesignService } from '../../../shared/services/eWorksheet.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import * as lodash from "lodash";
import { Subscription } from 'rxjs';
import { FileUploadComponent } from '../FileUpload/file-upload/file-upload.component';
import { AlertService } from '../_alert';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
import { SettingService2 } from 'app/shared/services2/Setting2.service';
import { StepCommentComponent } from '../StepComment/step-comment.component';
import { InitService } from 'app/shared/services2/Init.service';

@Component({
  selector: 'app-ws-design-entry',
  templateUrl: './ws-design-entry.component.html',
  styleUrls: ['./ws-design-entry.component.scss', '../FreeComponents/stepper/stepper.component.scss']
})
export class WsDesignEntryComponent implements OnInit, OnDestroy {

  @ViewChild('fileupload') fileupload_view  : FileUploadComponent;
  @ViewChild('designComment_view') dc_view : StepCommentComponent;

  isLoading: boolean = false
  privilege = {
    design: false,
    experiment: false,
    review: false
  }
  stepperIndex = 0;
  screenLock = false;
  subIndex: number = 0;
  tabIndex:number = 0;
  loadStepDetail:boolean = false;
  isAlreadyLoad:boolean = false;
  inventoryList = [];
  isCopy:boolean = false;
  mode:string = 'design';
  // listContent = ['Blocking', 'Capture', 'Coating', 'Detection', 'Stop', 'Substrate', 'Test', 'Washing'];
  // Chang name "Test" to "Sample Preparation"
  listContent = ['Calibrator Preparation', 'Sample Preparation', 'Sample Dilution', 'QC Preparation', 'MRD', 'Blocking', 'Capture', 'Coating', 'Detection', 'Stop', 'Substrate', 'Washing'];
  stepCounter = 0;
  stepArrayIndex = 0;
  steps = [];
  stepInitData = [];
  initDate:Date;
  saveDate:Date;
  newDesign: boolean = true;
  dataType = 'design';
  infoTab_complete: boolean = false;
  selectionTab_complete: boolean =false;
  configTab_complete: boolean = false;
  labelIndex: number = 0;
  displayLabel: boolean = true;
  ex_types;
  stepConfiguration;
  files_base64 = [];

  designConfig = {
    wsNum: null,
    exeNum: null,
    designComment: null,
    studyNum:null,
    templateName:null,
    templateId:null,
    exType: null,
    exObjective: null,
    parentId:null,
    userId:0,
    scientist: null,
    creationDate: null,
    saveDate:null,
    finalizationDate: null,
    finalized:null,
    version: null,
    stepArray: null,
    files: []
  }

  headerButtonList = [
    {
      name: "Review/Copy",
      routerLink: "/ws_review_copy",
      routerLinkActive: "mat-accent"
    }
  ]

  alertOptions={
    autoClose: true,
    keepAfterRouteChange: false
  }

  designSub: Subscription;
  addSub: Subscription;
  updateSub: Subscription;

  hideBackButton = true;

  tempTypes = []

  constructor(private ws_design_service: WorksheetDesignService,
    public route: ActivatedRoute,
    private router: Router,
    private authenticationservice: AuthenticationService2,
    private alertService: AlertService,
    private settingService: SettingService2) { }


  ngOnInit(): void {
    this.setLoading(true)

    this.getUserInfo();
    const typeList =  this.ws_design_service.getExperimentTypes();
    // const typeList = this.settingService.getSettingValuesAsArray('experimentType') || this.ws_design_service.getExperimentTypes();

    // Sort options by name length for experiment types
    this.ex_types = typeList.sort(function(a, b){
      // ASC  -> a.length - b.length
      // DESC -> b.length - a.length
      return b.length - a.length;
    });





    this.ws_design_service.getInventoryLoader().subscribe(data => {
      this.setLoading(false)
      this.inventoryList = data.inventoryListCopy;
      console.log('Inventory data is loaded for worksheet')
      // console.log("inventory list is: ");
      // console.log(this.inventoryList);
    })

    // Save a copy a inventory data in the service file for implementing auto-complete's logic
    this.ws_design_service.initInventoryData();

    // Initialize basic information for template design creation, no save date, and template name configed below
    this.initDate = new Date();
    this.designConfig.creationDate = this.initDate.getTime();
    this.designConfig.finalizationDate = 0;
    this.designConfig.finalized = false;
    this.designConfig.templateId = -1;
    this.designConfig.parentId = -1;
    this.designConfig.version = -1;
    this.designConfig.exeNum = 0;

    this.route.paramMap.subscribe((paramMap: ParamMap) => {
      if (paramMap.has("templateId")) {
        this.mode = "design";
        this.designConfig.templateId = +paramMap.get("templateId");
        this.newDesign = false;
        this.hideBackButton = false;
        let template;
       this.designSub = this.ws_design_service.getDesignById(this.designConfig.templateId).subscribe(res => {

          console.log("response from get template details by ID")
          console.log(res);
          template = this.ws_design_service.dataFormatReceive(res, "design");
          console.log("incoming template data after reformat: ");
          console.log(template);

          //prepopulate date for template configuration
        this.designConfig.designComment = template.designComment
        this.designConfig.studyNum = template.studyNum;
        this.designConfig.templateName = template.templateName;
        if(this.designConfig.templateName === 'design copy'){
          this.isCopy = true;
        }
        this.designConfig.exObjective = template.exObjective;
        this.designConfig.exType = template.exType;
        this.designConfig.parentId = template.parentId;
        this.designConfig.creationDate = template.creationDate;
        this.initDate = new Date(this.designConfig.creationDate);
        this.designConfig.finalized = template.finalized;
        this.designConfig.version = template.version;
        this.designConfig.files = template.files;
        this.designConfig.wsNum = template.wsNum;
        this.designConfig.exeNum = template.exeNum;
        this.files_base64 = lodash.cloneDeep(template.files);


          if(template.files.length > 0){
            this.fileupload_view.initFileData(template.files);
          }

          if(template.stepArray.length > 0){
            this.steps = [...template.stepArray]

          //add stepIndex for each step
          this.steps.forEach(step => {
            this.stepArrayIndex++;
            this.stepCounter++;
            step.stepIndex = this.stepArrayIndex;
            step.stepListContent = this.listContent;
            step.stepValid = true;
          })
          // this.initSubStepArray(this.steps);
          console.log(`templte ${this.designConfig.templateId}'s step data:`);
          console.log(this.steps);

          }else{
            console.log("incoming step array is empty");
          }


        //Confit tab styling while this component is initialized
        this.checkInfoTab();
        this.checkSelectionTab();
        this.checkConfigTab();
        // need to initialize the desgin comment by using view control since there is some delay for Angular to detect the value for designConfig.designComment
        if(this.designConfig.designComment){
          this.dc_view.initComment(this.designConfig.designComment)
          console.log("dc_view control is triggered to call initComment()")
        }
        });



      }// End of the first if inside of route subscription


      console.log("designConfig data: ");
      console.log(this.designConfig);
    });// End of route subscription

  } // End of ngOnInit function

  setLoading(status){
    this.isLoading = status
  }

  dateFormat(date){
    const newDate = new Date(date);

    // The month count starts with 0 (Jan), up to 11 (Dec).
    const month = newDate.getMonth() + 1;
    return newDate.getFullYear() + "/" + month + "/" + newDate.getDate();
  }

  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.designConfig.scientist = user.name;
        this.designConfig.userId = user.userId;
        console.log("current author user info: ");
        console.log(user);

        this.privilege = this.ws_design_service.accessControl(user)
      } )
    }
  }


  copyStepData(){
    this.stepInitData = lodash.cloneDeep(this.steps);
    console.log("step init data is updated.")
  }

  initSubStepArray(steps){
    steps.forEach(step => {
      if(step.stepData.subStepArray && step.stepData.subStepArray.length > 0){

            step.stepData.subStepArray.forEach(subMaterial => {
            this.subIndex++;
            subMaterial.stepIndex = this.subIndex;

            if(subMaterial.stepData.subStepArray.length > 0){
              subMaterial.stepData.subStepArray.forEach(subDilution => {
                this.subIndex++;
                subDilution.stepIndex = this.subIndex;
              })
            }

          })

      }
    })
    this.copyStepData();
  }

  addStep(){
    this.stepCounter++;
    this.stepArrayIndex++;
    this.steps.push({
    //  mode: this.mode,
    dbid: -1,
    stepIndex: this.stepArrayIndex,
    stepListContent: this.listContent,
    stepType: null,
    isAlreadyLoad: false,
    stepValid: false
  });

    console.log('step added: ');
    console.log(this.steps);
    // this.copyStepData();
    this.checkSelectionTab()
  }


  removeStep(event){
    this.displayLabel = false;
    this.stepCounter--;
    const filterArray = this.steps.filter(step => step.stepIndex !== event.index)
    this.steps = [...filterArray];
    console.log('step removed: ' );
    console.log( this.steps)
    // this.copyStepData();
    this.checkSelectionTab()
  }


  updateStep(event){
    this.labelIndex = event.index;
    this.displayLabel = true;
    const updatedStepArray = this.steps.map((step,index) => {
      if(step.stepIndex === event.index){
        this.updateStepperIndex(index);
        console.log(`Selected stepper index updated: ${this.stepperIndex}`)
        return{
          // mode: step.mode,

          // dbid will be reset to -1 whenever users update a step's step type
          dbid: -1,
          stepIndex: step.stepIndex,
          stepListContent: step.stepListContent,
          stepType : event.option,
          isAlreadyLoad: false,
          stepData: null,
          stepValid: step.stepValid
        }}
        else{
          return step;
        }

      }

    )

    this.steps = [...updatedStepArray];
    console.log('step array updated');
    console.log(this.steps);

    // this.copyStepData();
    this.checkSelectionTab()
  }

  stepMove(event){
    this.displayLabel = true;
    this.labelIndex = event.stepIndex
    const targetStepIndex = this.steps.findIndex(step => step.stepIndex == event.stepIndex);
    if(event.stepMove === "UP" && targetStepIndex > 0){
      const dataPre = this.steps[targetStepIndex - 1]
      this.steps[targetStepIndex - 1] = this.steps[targetStepIndex];
      this.steps[targetStepIndex] = dataPre
      // this.labelIndex = targetStepIndex - 1;
      this.updateStepperIndex(targetStepIndex - 1)
      console.log(`Selected stepper index updated: ${this.stepperIndex}`)

    }else if(event.stepMove === "DOWN" && targetStepIndex < this.steps.length - 1){
      const dataPos = this.steps[targetStepIndex + 1]
      this.steps[targetStepIndex + 1] = this.steps[targetStepIndex];
      this.steps[targetStepIndex] = dataPos
      // this.labelIndex = targetStepIndex + 1;
      this.updateStepperIndex(targetStepIndex + 1)
      console.log(`Selected stepper index updated: ${this.stepperIndex}`)
    }

    console.log('step order updated');
    console.log(this.steps);
    // this.copyStepData();
  }

  dataReceiver(userData){
    this.steps.forEach((step, index) => {
      if(step.stepIndex === userData.stepIndex){
        if(userData.stepData){
          step.stepData = userData.stepData
        }

        step.stepValid = userData.valid;
        this.stepInitData[index].stepValid = userData.valid;
        console.log(`step data is updated for ${step.stepType}: `);
        console.log(step);

      }

    })
    this.checkConfigTab();
  }

  onSave(){

    let allStepsDone = true;

    let proceed = true;
    // the save function will only allow users to modify creation date once the user is creating a fresh new worksheet design
    if(this.newDesign){
      if(this.initDate){
        this.designConfig.creationDate = this.initDate.getTime();
      }else{
        this.designConfig.creationDate = null;
      }
    }

    this.saveDate = new Date();
    this.designConfig.saveDate = this.saveDate.getTime();
    // this.stepConfiguration = this.steps;

    proceed = this.checkDesignTitle(this.designConfig);

    let stepMessage: string = "";
    if(!this.designConfig.finalized){
      this.steps.forEach((step, index) => {
        if(step.stepValid){
          stepMessage = stepMessage + ` Step ${index + 1} (${step.stepType}) is: COMPLETE. \n\n`
        }
        else{
          proceed = false;
          allStepsDone = false;
          stepMessage = stepMessage + ` Step ${index + 1} (${step.stepType}) is: NOT COMPLETE ! \n\n`
        }


      })


      if(!allStepsDone){
        let options;
        options = this.alertOptions;
        this.alertService.warn('Please complete all steps before saving', options)
      }

    }



if(proceed){

  //filter out the unnecessary information from each step
  this.designConfig.stepArray = this.steps.map(step => {
    return {
      dbid: step.dbid,
      stepType: step.stepType,
      stepData: step.stepData
    }
  })


  if(this.newDesign){

    let options;
    options = this.alertOptions;
    options.id = 'success'
    this.alertService.success(`New worksheet design (${this.designConfig.templateName}) is saved`, options)

    this.addSub = this.ws_design_service.addDesign(this.designConfig).subscribe(res => {
          console.log("response from backend: ");
          console.log(res);
          this.router.navigate(["/ws_review_copy"]);
      })

  }else{

    let options;
    options = this.alertOptions;
    options.id = 'success'
    this.alertService.success(`Worksheet design (${this.designConfig.templateName}) is updated`, options)

    this.updateSub = this.ws_design_service.updateDesign(this.designConfig.templateId, this.designConfig).subscribe(res => {
          console.log("response from updating template design: ");
          console.log(res);
          this.router.navigate(["/ws_review_copy"]);
        })
  }

  }

}

checkDesignTitle(designInfo){
  let valid = true;
  if(!designInfo.studyNum){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'studyNum'
    this.alertService.warn('Study number is missing', options)
  }

  if(!designInfo.wsNum){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'wsNum'
    this.alertService.warn('Worksheet number is missing', options)
  }

  if(!designInfo.templateName){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'drug-name'
    this.alertService.warn('Drug name is missing', options)
  }

  if(!designInfo.scientist){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'author-name'
    this.alertService.warn('Author name is missing', options)
  }

  if(!designInfo.exObjective){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'ex-type'
    this.alertService.warn('Experiment objective is missing', options)
  }

  if(!designInfo.exType){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'ex-ob'
    this.alertService.warn('Experiment type is missing', options)
  }

  if(!designInfo.creationDate){
    valid = false;
    let options;
    options = this.alertOptions;
    options.id = 'date'
    this.alertService.warn('Creation date is missing', options)
  }


  return valid;
}




  onFinalize(){

    let allStepsDone = true;

    let incomplete = false;

    incomplete = !(this.checkDesignTitle(this.designConfig))

    let stepMessage: string = "";
    this.steps.forEach((step, index) => {
      if(!step.stepValid){
        incomplete = true
        allStepsDone = false;
        stepMessage =  stepMessage + ` Step ${index + 1} (${step.stepType}) is: NOT COMPLETE ! \n`
      }

      // alertMessage = alertMessage + stepMessage + "\n"

    })

    if(!incomplete){
      let finalizationDate = new Date();
      this.designConfig.finalizationDate = finalizationDate.getTime();
      this.designConfig.finalized = true;

      let options;
      options = this.alertOptions;
      options.keepAfterRouteChange = true;
      options.id = 'success'
      this.alertService.success(`Finalization for worksheet design (${this.designConfig.templateName}) is successful`, options)
      this.onSave();
    }

    if(!allStepsDone){
      let options;
      options = this.alertOptions;
      this.alertService.warn('Please complete all steps', options)
    }

    console.log(this.designConfig);

  }

  tabChange(event){
    console.log("active tab is changed:");
    console.log(event);
    this.tabIndex = event.index;
    if(this.tabIndex === 2){
      this.copyStepData()
      this.loadStepDetail = true;
      this.steps.forEach(step => {
        step.isAlreadyLoad = true;
      })

      this.stepInitData.forEach(step => {
        step.isAlreadyLoad = true;
      })

      // if(this.designConfig.files.length > 0){
      //   this.fileupload_view.initFileData(lodash.cloneDeep(this.designConfig.files))
      // }
    }else{
      this.loadStepDetail = false;
    }
    console.log("loadStepDetail Value: " + this.loadStepDetail)

  }


  changeDate(event){
    console.log("creation date is changed: ")
    console.log(event.value);
  }

  checkInfoTab(){
    if( this.designConfig.templateName &&
        this.designConfig.scientist &&
        this.initDate &&
        this.designConfig.exObjective &&
        this.designConfig.exType &&
        this.designConfig.studyNum &&
        this.designConfig.wsNum){
      this.infoTab_complete = true;
    }else{
      this.infoTab_complete = false;
    }
  }

  checkSelectionTab(){
    let assumption = true;
    if(this.steps.length > 0){
      this.steps.forEach(step => {
        if(!step.stepType){
          assumption = false;
        }
      })
    }else{
      assumption = false;
    }

    this.selectionTab_complete = assumption;
  }

  checkConfigTab(){
    let assumption = true;
    if(this.steps.length > 0){
      this.steps.forEach(step => {
        if(step.stepValid === false){
          assumption = false
        }
      })
    }else{
      assumption = false
    }

    this.configTab_complete = assumption;
  }


  onFileChange(event){
    this.designConfig.files = lodash.cloneDeep(event.files);
    console.log("template updates file array:");
    console.log(this.designConfig.files)
  }

  updateStepperIndex(index){
    this.stepperIndex = index;
  }

  rollerListener(event){
    if(event.roll === 'left'){
      if(this.stepperIndex >= 1){
        this.stepperIndex--
      }
    }else{
      if(this.stepperIndex < this.steps.length - 1){
        this.stepperIndex++
      }
    }
  }

  disableScreen(){
    this.screenLock = true;
  }

  enableScreen(){
    this.screenLock = false;
  }

  updateComment(event){
    if(event.comment){
      this.designConfig.designComment = event.comment
      console.log(`Design comment is updated: ${this.designConfig.designComment}`)
    }else{
      console.log('Design comment failed to update')
    }
  }

  cancelComment(event){
    this.designConfig.designComment = null
    console.log("Design comment is cancelled")
  }

  toHome(){
    this.ws_design_service.navToHome()
  }

  ngOnDestroy() {
    if(this.designSub){
      this.designSub.unsubscribe();
    }
    if(this.addSub){
      this.addSub.unsubscribe();
    }
    if(this.updateSub){
      this.updateSub.unsubscribe();
    }


  }

}
