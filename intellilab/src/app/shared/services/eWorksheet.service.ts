import { Injectable} from '@angular/core';
import { Subject } from "rxjs";
import * as lodash from "lodash";
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubIncubationComponent } from 'app/page/eWorksheet/StepTypes/SubIncubation/sub-incubation/sub-incubation.component';
import { InventoryService2 } from '../services2/Inventory2.service';
import { UrlService } from './url.service';
import { InitService } from '../services2/Init.service';
import {v4 as uuidv4} from 'uuid';

// const BASE_URL = 'http://localhost:8080';
const BASE_URL = UrlService.URL;
const BACKEND_URL = BASE_URL + '/worksheetdesign';
const INSTANCE_URL = BASE_URL + '/worksheetinstance';



@Injectable({providedIn:"root"})
export class WorksheetDesignService {

  inventoryList = [];
  itemDetailList = [];


  // private gramUnits = ['mg', 'μg', 'ng'];
  private literUnits = ['mL', 'µL']
  private concentrationUnits = ['mg/mL', 'µg/mL', 'ng/mL', 'pg/mL'];
  private timeUnits: string[] = ['hr', 'min', 'sec']
  private experimentTypes = ['Method Development', 'Sample Analysis', 'Validation', 'Other'];

stepTypeUpdated = new Subject<{updatedSteps: any}>();
steps:{mode:string, stepIndex:number, stepListContent:string[],stepType:any}[];
inventoryLoader = new Subject<{inventoryListCopy: any}>();
// Development variables
currentDate:Date;
// End of development variables


constructor(private router: Router, private http: HttpClient, public inven_service: InventoryService2, public initeService: InitService){
  this.steps = [];

  //Mock data for development
  this.currentDate = new Date();

  //End of mock data
}

updateRunNum(templateId, exeNum, operation){
  const body = {
    exeNum: exeNum
  }

  if(operation === 'incre'){
    body.exeNum = body.exeNum + 1
  }else if(exeNum > 0){
    body.exeNum = body.exeNum - 1
  }
  const headers = this.createHeader()
  // let  headers = new  HttpHeaders().set('Content-Type', 'application/json');
  // headers.append("Authorization", 'Bearer '+sessionStorage.getItem('jwt'))
  this.http.put(BACKEND_URL + '/exeNum/update/' + templateId, JSON.stringify(body), { headers }).subscribe(res => {
    console.log(`Run time is updated for template ${templateId}: ${body.exeNum}`);
    console.log(`Response message for updating run time for template ${templateId}`)
    console.log(res)
  })

}

getLiterUnits(){
  return lodash.cloneDeep(this.literUnits);
}

getConcentrationUnits(){
  return lodash.cloneDeep(this.concentrationUnits);
}

getTimeUnits(){
  return lodash.cloneDeep(this.timeUnits);
}

getExperimentTypes(){
  return lodash.cloneDeep(this.experimentTypes);
}

// Development functions

createHeader(){
  let debugBreadCrumb = uuidv4();
  console.log( "DEBUG_BREADCRUMB", debugBreadCrumb );
  let header = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set("Authorization", 'Bearer '+sessionStorage.getItem('jwt'))
  .set("DEBUG_BREADCRUMB", debugBreadCrumb );
  return header
}

deleteDesignById(templateId){
  console.log(`template ${templateId} is deleted.`)
  const headers = this.createHeader()
  console.log('Headers for deleting a worksheet design: ')
  console.log(headers)
  return this.http.delete(BACKEND_URL + "/delete/" + templateId, { headers });
}

  getDesignList(){
    const headers = this.createHeader()
    return this.http.get<{message:string, summaries:any}>(BACKEND_URL + '/summary', { headers });
  }


  getAssignmentList(userId){
    const headers = this.createHeader()
    return this.http.get<{message:string, assignments:any}>(BACKEND_URL + `/getAssignedTemplateDesigns?analystId=${userId}`, { headers });
  }

  getIncompleteList(){
    const headers = this.createHeader()
    return this.http.get<{message:string, summaries:any}>(INSTANCE_URL + '/incomplete_summary', { headers });
  }

  getCompleteList(){
    const headers = this.createHeader()
    return this.http.get<{message:string, summaries:any}>(INSTANCE_URL + '/complete_summary', { headers });
  }

  getApprovedList(){
    const headers = this.createHeader()
    return this.http.get<{message:string, summaries:any}>(INSTANCE_URL + '/approved_summary', { headers });
  }

  getRejectedList(){
    const headers = this.createHeader()
    return this.http.get<{message:string, summaries:any}>(INSTANCE_URL + '/rejected_summary', { headers });
  }

  getDesignById(Id){
    console.log("Get edit Id: " + Id);
    const headers = this.createHeader()
    return this.http.get(BACKEND_URL + "/specificworksheet/" + Id, { headers });
  }

  getInstanceById(worksheetId){
    console.log("Get instance Id: " + worksheetId);
    const headers = this.createHeader()
    return this.http.get(INSTANCE_URL + "/specificinstance/" + worksheetId, { headers });
  }

  addAssignment(assignment){
    const headers = this.createHeader()
    console.log("assignment is created with url: " + BACKEND_URL + '/assignAnalyst');
    return this.http.post(BACKEND_URL + '/assignAnalyst', JSON.stringify(assignment), { headers });
  }


  addDesign(designConfig){
    let sendData;
    sendData = this.dataFormatSend(designConfig, "design");
    const headers = this.createHeader()
    console.log("Headers for adding new worksheet design: ")
    console.log(headers)
    return this.http.post(BACKEND_URL + '/addworksheet', JSON.stringify(sendData), { headers });
  }

  addInstance(instanceConfig){
    let sendData;
    sendData = this.dataFormatSend(instanceConfig, 'instance');
    const headers = this.createHeader()
    return this.http.post<{message: String, worksheetId: String}>(INSTANCE_URL + '/addworksheetinstance', JSON.stringify(sendData), { headers });
  }

  updateDesign(templateId,designConfig){
    let sendData;
    sendData = this.dataFormatSend(designConfig, "design");
    console.log("design is updated with url: " + BACKEND_URL + '/update/' + templateId);
    const headers = this.createHeader()
    return this.http.put(BACKEND_URL + '/update/' + templateId, JSON.stringify(sendData), { headers });
  }

  updateInstance(worksheetId, worksheetConfig){
    let sendData;
    sendData = this.dataFormatSend(worksheetConfig, "instance");
    console.log("instance is updated with url: " + INSTANCE_URL + '/update/' + worksheetId);
    const headers = this.createHeader()
    return this.http.put(INSTANCE_URL + '/update/' + worksheetId, JSON.stringify(sendData), { headers });
  }

// End of Development functions



addStep(mode:string, stepIndex:number, stepListContent:string[]){

  this.steps.push({
    mode: mode,
    stepIndex: stepIndex,
    stepListContent: stepListContent,
    stepType:null
  });
  console.log('step added: ');
  console.log(this.steps)
  return this.steps;
}

removeStep(removeIndex){
  const filterArray = this.steps.filter(step => step.stepIndex !== removeIndex)
    this.steps = [...filterArray];
    console.log('step removed: ' );
    console.log( this.steps)
    this.stepTypeUpdated.next({updatedSteps: this.steps});
    return this.steps;
}

update(stepIndex, stepType){
  const updatedStepArray = this.steps.map(step => {
    if(step.stepIndex === stepIndex){
      return{
        mode: step.mode,
        stepIndex: step.stepIndex,
        stepListContent: step.stepListContent,
        stepType : stepType
      }}
      else{
        return step;
      }


    }

  )

  this.steps = [...updatedStepArray];


  console.log('step array updated');
  console.log(this.steps);
  this.stepTypeUpdated.next({updatedSteps: this.steps});
  return this.steps;

}

updateStepType(updatedStepArray){
this.steps = [...updatedStepArray];
this.stepTypeUpdated.next({updatedSteps: this.steps});

}




getStepTypeUpdateListener(){
  return this.stepTypeUpdated.asObservable();
}



dataFormatSend(configData, mode){
  // const receivedDesign = lodash.cloneDeep(configData)
  console.log("original worksheet config:");
  console.log(configData)
  let sendData;
  let finalizationStatus;

  if(mode === 'design'){
    if(configData.finalized){
      finalizationStatus = 1
    }else{
      finalizationStatus = 0
    }
    sendData = {
      wsNum: configData.wsNum,
      exeNum: configData.exeNum,
      studyNum: configData.studyNum,
      templateName: configData.templateName,
      templateId: configData.templateId,
      exObjective: configData.exObjective,
      exType: configData.exType,
      creationDate: configData.creationDate,
      finalizationDate: configData.finalizationDate,
      saveDate: configData.saveDate,
      finalized: finalizationStatus,
      scientist: configData.scientist,
      userId: configData.userId,
      parentId: configData.parentId,
      version: configData.version,
      designComment: configData.designComment,
      files: configData.files,
      steps:[]
    }
  }

  if(mode === 'instance'){
    sendData = {
      // exVersion: configData.exVersion,
      worksheetId: configData.worksheetId,
      studyNum: configData.studyNum,
      templateId: configData.templateId,
      exObjective: configData.exObjective,
      exType: configData.exType,
      analystId: configData.analystId,
      reviewerId: configData.reviewerId,
      startDate: configData.startDate,
      saveDate: configData.saveDate,
      completionDate: configData.completionDate,
      reviewDate: configData.reviewDate,
      status: configData.status,
      overallComment: configData.overallComment,
      designComment: configData.designComment,
      results: configData.results,
      steps:[]
    }
  }


let steps = [];
  steps = configData.stepArray.map(step => {

    if(step.stepData.incubationArray){
      step.stepData.incubationArray.forEach(incubation => {
        // incubation.dbid = -1;
        incubation.valid = incubation.valid ? 1 : 0;
        if(incubation.timer){
          incubation.timeValueSet = incubation.timer.timeValueSet;
          incubation.timeUnitSet = incubation.timer.timeUnitSet;
          incubation.timerStartDate = incubation.timer.timerStartDate;
          incubation.timerValue = incubation.timer.timerValue;
          incubation.complete = incubation.timer.complete? 1 : 0;
          incubation.formattedTime = incubation.timer.formattedTime;
        }
      })
    }

    let stepConfig;
    switch(step.stepType){
      // 11 attributes in total
        case "Blocking":
          stepConfig = {
            type:"blocking",
            dbid: step.dbid,
            //operation 1 input values
            blockingBuffer: step.stepData.buffer,
            blockingBufferSupplier: step.stepData.supplier,

            //operation 2 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 3 input values
            incubationArray: step.stepData.incubationArray,

            bufferId: step.stepData.bufferId,



          };
          if(mode === 'instance'){
            stepConfig.blockingBufferLot_user = step.stepData.bufferLot_user
            stepConfig.type = "blocking instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }

        break;

        case "Capture":

          stepConfig={
          type:"capture",
          dbid: step.dbid,
          //operation 1 input values
          captureBuffer: step.stepData.buffer,
          captureBufferSupplier: step.stepData.supplier,

          //operation 2 input values
          subStepArray: null,

          //operation 3 input values
          dispenseValue: step.stepData.dispenseValue,
          dispenseUnit : step.stepData.dispenseUnit,
          dispenseCheck: step.stepData.dispenseCheck,

          //operation 4 input values
          incubationArray: step.stepData.incubationArray,

          bufferId: step.stepData.bufferId,



          }
          console.log("Format sub steps for Capture:")
          stepConfig.subStepArray = this.formatSubStepData(step.stepData.subStepArray, 'capture send', 'send');
          if(mode === 'instance'){
             stepConfig.captureBufferLot_user = step.stepData.bufferLot_user;
             stepConfig.type = "capture instance";
             stepConfig.experimentComment = step.commentData.experimentComment
             stepConfig.reviewComment = step.commentData.reviewComment
             stepConfig.isExcluded = step.stepData.isExcluded
             stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Coating":
          // 20 attributes in total
          stepConfig={
            type:"coating",
            dbid: step.dbid,
            //operation 1 input values
            coatingBuffer: step.stepData.buffer,
            coatingBufferSupplier: step.stepData.supplier,

            //operation 2 input values
            subStepArray: null,

            //operation 6 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 7 input values
            incubationArray: step.stepData.incubationArray,

            bufferId: step.stepData.bufferId,



          }
          console.log("Format sub steps for Coating:")
          stepConfig.subStepArray = this.formatSubStepData(step.stepData.subStepArray, 'coating send', 'send');
          if(mode === 'instance'){
            stepConfig.coatingBufferLot_user = step.stepData.bufferLot_user;
            stepConfig.type = "coating instance";
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Detection":

          stepConfig={
            type:"detection",
            dbid: step.dbid,
            //operation 1 input values
            detectionBuffer: step.stepData.buffer,
            detectionBufferSupplier: step.stepData.supplier,

            //operation 2 input values
            subStepArray: null,

            //operation 6 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 7 input values
            incubationArray: step.stepData.incubationArray,

            bufferId: step.stepData.bufferId,



          }
          console.log("Format sub steps for Detection:")
          stepConfig.subStepArray = this.formatSubStepData(step.stepData.subStepArray, 'detection send', 'send');
          if(mode === 'instance'){
            stepConfig.detectionBufferLot_user = step.stepData.bufferLot_user;
            stepConfig.type = "detection instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Stop":

          stepConfig={
            type:"stop",
            dbid: step.dbid,
            //operation 1 input values
            stopSolution: step.stepData.material,
            stopSolutionSupplier: step.stepData.supplier,

            //operation 2 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 3 input values
            readAtValue_1: step.stepData.readAtValue_1,
            readAtValue_2: step.stepData.readAtValue_2,

            materialId: step.stepData.materialId,



          }
          if(mode === 'instance'){
            stepConfig.stopSolutionLot_user = step.stepData.materialLot_user;
            stepConfig.type = "stop instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Substrate":
          // 10 attributes in total
          stepConfig={
            type:"substrate",
            dbid: step.dbid,
             //operation 1 input values
            substrate: step.stepData.material,
            substrateSupplier: step.stepData.supplier,

            //operation 2 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 3 input values
            incubationArray: step.stepData.incubationArray,

            materialId: step.stepData.materialId,


          }
          if(mode === 'instance'){
            stepConfig.substrateLot_user = step.stepData.materialLot_user;
            stepConfig.type = "substrate instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Test":

          stepConfig={
          type:"test",
          dbid: step.dbid,
          //operation 1 input values
          sampleBuffer: step.stepData.buffer,
          sampleBufferSupplier: step.stepData.supplier,

          //operation 2 input values
          subStepArray: null,

          //operation 6 input values
          dispenseValue: step.stepData.dispenseValue,
          dispenseUnit : step.stepData.dispenseUnit,
          dispenseCheck: step.stepData.dispenseCheck,

          //operation 7 input values
          incubationArray: step.stepData.incubationArray,

          bufferId: step.stepData.bufferId,



          }
          console.log("Format sub steps for Testing:")
          stepConfig.subStepArray = this.formatSubStepData(step.stepData.subStepArray, 'testing send', 'send');
          if(mode === 'instance'){
            stepConfig.sampleBufferLot_user = step.stepData.bufferLot_user;
            stepConfig.type = "test instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;

        case "Washing":
          stepConfig={
            type:"wash",
            dbid: step.dbid,
            //operation 1 input values
            washingBuffer: step.stepData.buffer,
            washingBufferSupplier: step.stepData.supplier,

            //operation 2 input values
            dispenseValue: step.stepData.dispenseValue,
            dispenseUnit : step.stepData.dispenseUnit,
            dispenseCheck: step.stepData.dispenseCheck,

            //operation 3 input values
            washTime: step.stepData.washTime,
            washValue: step.stepData.washValue,
            washUnit: step.stepData.washUnit,

            bufferId: step.stepData.bufferId,



          }
          if(mode === 'instance'){
            stepConfig.washingBufferLot_user = step.stepData.bufferLot_user;
            stepConfig.pbst = step.stepData.PBST;
            stepConfig.type = "wash instance"
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            stepConfig.equipments = step.stepData.equipments
          }
        break;


        case  "Calibrator Preparation":
        case  "Sample Dilution":
        case  "QC Preparation":
        case  "MRD":
          stepConfig={
            type: step.stepType.toLowerCase(),
            dbid: step.dbid,
            //operation 1 input values
            material: step.stepData.material,
            supplier: step.stepData.supplier,

            //operation 2 input values
            // files: step.stepData.files,

            materialId: step.stepData.materialId,

            //dumy data for Imran to work on the backend easier, and may need to be deleted later on
            dispenseValue: 123,
            dispenseUnit: "ml",


          }
          if(mode === 'instance'){
            stepConfig.materialLot_user = step.stepData.materialLot_user;
            stepConfig.files = step.stepData.files;
            stepConfig.type = `${stepConfig.type} instance`
            stepConfig.experimentComment = step.commentData.experimentComment
            stepConfig.reviewComment = step.commentData.reviewComment
            stepConfig.isExcluded = step.stepData.isExcluded
            // stepConfig.equipments = step.stepData.equipments
          }
        break;

    }
    return stepConfig;
  })
sendData.steps = lodash.cloneDeep(steps);
console.log(`formatted sending ${mode} data is:` );
console.log(sendData);
console.log("JSON format sent data for testing API:")
console.log(JSON.stringify(sendData));
return sendData;

}

dataFormatReceive(receiveData, mode){
  console.log("JSON format received data for testing API:")
  console.log(JSON.stringify(receiveData));
  // console.log("receive data for format receive function: ");
  // console.log(receiveData);

  // variable for assigning step index
  let index = 0

  console.log("mode for formatting receive data: " + mode)
  const dataCopy = lodash.cloneDeep(receiveData);
  let stepConfig = dataCopy.steps.map(step => {

    // Incrementing index varible by 1
    index++;
    let config;
    switch(step.type){
        case "blocking":
        case "blocking instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Blocking",
            stepData:{
              //operation 1 input values
            buffer: step.blockingBuffer,
            supplier: step.blockingBufferSupplier,
            bufferLot_user: step.blockingBufferLot_user || null,

              //operation 2 input values
            dispenseValue: step.dispenseValue,
            dispenseUnit : step.dispenseUnit,
            dispenseCheck: step.dispenseCheck || false,

            isExcluded: step.isExcluded || 0,
            bufferId: step.bufferId || null,

            equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
        break;


        case "capture":
        case "capture instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Capture",
            stepData:{
              //operation 1 input values
         buffer: step.captureBuffer,
         supplier: step.captureBufferSupplier,
         bufferLot_user: step.captureBufferLot_user || null,

         //operation 2 input values
         subStepArray: null,


          //operation 3 input values
          dispenseValue: step.dispenseValue,
          dispenseUnit : step.dispenseUnit,
          dispenseCheck: step.dispenseCheck || false,

            isExcluded: step.isExcluded || 0,
            bufferId: step.bufferId || null,

            equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
          config.stepData.subStepArray = this.formatSubStepData(step.subStepArray, 'capture receive', 'receive')
        break;


        case "coating":
        case "coating instance":

          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Coating",
            stepData:{
               //operation 1 input values
          buffer: step.coatingBuffer,
          supplier: step.coatingBufferSupplier,
          bufferLot_user: step.coatingBufferLot_user || null,

          //operation 2 input values
          subStepArray: null,

          //operation 3 input values
          dispenseValue: step.dispenseValue,
          dispenseUnit : step.dispenseUnit,
          dispenseCheck: step.dispenseCheck || false,

          isExcluded: step.isExcluded || 0,
          bufferId: step.bufferId || null,

          equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
          config.stepData.subStepArray = this.formatSubStepData(step.subStepArray, 'coating receive', 'receive')
        break;


        case "detection":
        case "detection instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Detection",
            stepData:{
              //operation 1 input values
         buffer: step.detectionBuffer,
         supplier: step.detectionBufferSupplier,
         bufferLot_user: step.detectionBufferLot_user || null,

         //operation 2 input values
         subStepArray: null,

          //operation 6 input values
          dispenseValue: step.dispenseValue,
          dispenseUnit : step.dispenseUnit,
          dispenseCheck: step.dispenseCheck || false,

          isExcluded: step.isExcluded || 0,
          bufferId: step.bufferId || null,

          equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
          config.stepData.subStepArray = this.formatSubStepData(step.subStepArray, 'detection receive', 'receive')
        break;


        case "stop":
        case "stop instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Stop",
            stepData:{
              //operation 1 input values
              material: step.stopSolution,
              supplier: step.stopSolutionSupplier,
              materialLot_user: step.stopSolutionLot_user || null,

            //operation 2 input values
            dispenseValue: step.dispenseValue,
            dispenseUnit : step.dispenseUnit,
            dispenseCheck: step.dispenseCheck || false,

              //operation 3 input values
              readAtValue_1: step.readAtValue_1,
              readAtValue_2: step.readAtValue_2,

              isExcluded: step.isExcluded || 0,
              materialId: step.materialId || null,

              equipments: step.equipments || [],
            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
        break;


        case "substrate":
        case "substrate instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Substrate",
            stepData:{
              //operation 1 input values
              material: step.substrate,
              supplier: step.substrateSupplier,
              materialLot_user: step.substrateLot_user || null,

              //operation 2 input values
              dispenseValue: step.dispenseValue,
              dispenseUnit : step.dispenseUnit,
              dispenseCheck: step.dispenseCheck || false,

              isExcluded: step.isExcluded || 0,
              materialId: step.materialId || null,

              equipments: step.equipments || [],
            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
        break;


        case "test":
        case "test instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Test",
            stepData:{
              //operation 1 input values
              buffer: step.sampleBuffer,
              supplier: step.sampleBufferSupplier,
              bufferLot_user: step.sampleBufferLot_user || null,

              //operation 2 input values
              subStepArray: null,

                //operation 6 input values
                dispenseValue: step.dispenseValue,
                dispenseUnit : step.dispenseUnit,
                dispenseCheck: step.dispenseCheck || false,

                isExcluded: step.isExcluded || 0,
                bufferId: step.bufferId || null,

                equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
          config.stepData.subStepArray = this.formatSubStepData(step.subStepArray, 'testing receive', 'receive')
        break;


        case "wash":
        case "wash instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,
            stepType: "Washing",
            stepData:{
              //operation 1 input values
              buffer: step.washingBuffer,
              supplier: step.washingBufferSupplier,
              bufferLot_user: step.washingBufferLot_user || null,

              //operation 2 input values
                dispenseValue: step.dispenseValue,
                dispenseUnit : step.dispenseUnit,
                dispenseCheck: step.dispenseCheck || false,

                //operation 3 input values
              washTime: step.washTime,
              washValue: step.washValue,
              washUnit: step.washUnit,

                //operation 4 input values
              PBST: step.pbst || null,

              isExcluded: step.isExcluded || 0,
              bufferId: step.bufferId || null,

              equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }
          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          config.deviation.severity = step.severity || null
          config.deviation.deviationDetails = step.deviationDetails || null
        break;



        case "calibrator preparation":
        case "qc preparation":
        case "sample dilution":
        case "mrd":
        case "calibrator preparation instance":
        case "qc preparation instance":
        case "sample dilution instance":
        case "mrd instance":
          config = {
            stepIndex: index,
            dbid: step.dbid || null,

            stepData:{
              //operation 1 input values
              material: step.material,
              supplier: step.supplier,
              materialLot_user: step.materialLot_user || null,
              files: step.files || [],

              //operation 2 input values
              isExcluded: step.isExcluded || 0,
              materialId: step.materialId || null,

              // equipments: step.equipments || [],

            },
            commentData:{
              experimentComment:null,
              reviewComment:null
            },
            deviation:{
              severity: null,
              deviationDetails: null
            }
          }


          switch(step.type){
            case  "qc preparation":
            case  "qc preparation instance":
              config.stepType = "QC Preparation"
              break;

            case  "calibrator preparation":
            case  "calibrator preparation instance":
              config.stepType = "Calibrator Preparation"
              break;

            case  "sample dilution":
            case  "sample dilution instance":
              config.stepType = "Sample Dilution"
              break;

            case  "mrd":
            case  "mrd instance":
              config.stepType = "MRD"
              break;


          }

          config.commentData.experimentComment = step.experimentComment || null
          config.commentData.reviewComment = step.reviewComment || null
          // config.deviation.severity = step.severity || null
          // config.deviation.deviationDetails = step.deviationDetails || null
        break;

    }

    // wrap subincubation data
    let tempIncubationArray = [];
    if(step.incubationArray){
      step.incubationArray.forEach(incubation => {
        let config = {
          incubateAtValue: null,
          incubateShakingValue: null,
          incubateTimeUnit: null,
          incubateTimeValue: null,
          incubateIndex: null,
          valid: null,
          timer: null
        };
        config.incubateAtValue = incubation.incubateAtValue;
        config.incubateShakingValue = incubation.incubateShakingValue;
        config.incubateTimeUnit = incubation.incubateTimeUnit;
        config.incubateTimeValue = incubation.incubateTimeValue;
        config.incubateIndex = incubation.incubateIndex;
        // if(mode === 'instance'){
        //   config.valid = (incubation.valid && incubation.timer.complete) ? true : false;
        //   config.timer = incubation.timer;
        // }else{
        //   config.valid = incubation.valid? true : false;
        // }
        if(mode === 'instance'){
          config.valid = (incubation.valid === 1 && incubation.complete) ? true : false;
        }else{
          config.valid = incubation.valid === 1? true : false;
        }

        if( incubation.timeValueSet||
            incubation.timeUnitSet ||
            incubation.timerStartDate ||
            incubation.timerValue ||
            incubation.complete ||
            incubation.formattedTime ||
            mode === 'instance'){

              let timer = {
                timeValueSet: incubation.incubateTimeValue,
                timeUnitSet: incubation.incubateTimeUnit,
                timerStartDate: incubation.timerStartDate || null,
                timerValue: incubation.timerValue || null,
                complete: incubation.complete? true : false,
                formattedTime: incubation.formattedTime || null

              };

              config.timer = timer;
        }
        tempIncubationArray.push(config);

      })
    }

    config.stepData.incubationArray = tempIncubationArray;


    // console.log("config.incubationArray: ");
    // console.log(config.incubationArray)
    return config;
  })



  let finalizationStatus;
  if(receiveData.finalized === 1){
    finalizationStatus = true
  }else{
    finalizationStatus = false
  }

  let worksheet
  if(mode === 'design'){
    worksheet = {
      wsNum: receiveData.wsNum || null,
      exeNum: receiveData.exeNum || 0,
      studyNum: receiveData.studyNum,
      templateId: receiveData.templateId || null,
      templateName: receiveData.templateName,
      exObjective: receiveData.exObjective,
      exType: receiveData.exType,
      creationDate: receiveData.creationDate,
      finalizationDate: receiveData.finalizationDate,
      saveDate: receiveData.saveDate,
      finalized: finalizationStatus,
      scientist: receiveData.scientist,
      userId: receiveData.userId,
      parentId: receiveData.parentId,
      version:receiveData.version,
      designComment: receiveData.designComment,
      stepArray:stepConfig || [],
      files: receiveData.files || []
    }
  }
  if(mode === 'instance'){
    worksheet = {
      // exVersion: receiveData.exVersion || receiveData.exeNum + 1 || null,
      exVersion: receiveData.exVersion || null,
      worksheetId: receiveData.worksheetId || null,
      worksheetName: receiveData.worksheetName || receiveData.templateName,
      studyNum: receiveData.studyNum,
      templateId: receiveData.templateId || null,
      exObjective: receiveData.exObjective || null,
      exType: receiveData.exType || null,
      analystId: receiveData.analystId || null,
      analystName: receiveData.analystName || null,
      reviewerName: receiveData.reviewerName || null,
      reviewerId: receiveData.reviewerId || null,
      startDate: receiveData.startDate || null,
      saveDate: receiveData.saveDate,
      completionDate: receiveData.completionDate || null,
      reviewDate: receiveData.reviewDate || null,
      status: receiveData.status || "new",
      overallComment: receiveData.overallComment || null,
      designComment: receiveData.designComment || null,
      stepArray:stepConfig || [],
      files: receiveData.files || [],
      results: receiveData.results || []
    }
  }
console.log('returned worksheet object from service function:')
console.log(worksheet)
return worksheet;
}




public formatTimerValue(v) {
  const hours = Math.floor(Math.floor(v / 60) / 60);
  const formattedHours = "" + (hours > 9 ? hours : "0" + hours);
  const minutes = Math.floor(v / 60) % 60;
  const formattedMinutes = "" + (minutes > 9 ? minutes : "0" + minutes);
  const seconds = v % 60;
  const formattedSeconds = "" + (seconds > 9 ? seconds : "0" + seconds);


  return `${formattedHours}:${formattedMinutes}:${formattedSeconds}`;
}

// initInventoryData(){
//   let tempList;
//   if(this.inventoryList.length === 0){

//     this.inven_service.getAllItems().subscribe(res => {
//         console.log("API for get inventory list data: ");
//         console.log(res);

//         this.inventoryList = res.map(item => {
//           return {
//             itemId: item.inventoryId,
//             itemName: item.name,
//             itemSupplier: item.supplier,
//             itemCatNum: item.catalogNumber,
//             itemInfo: item.catalogNumber + '/' + item.name
//           }
//         })

//         const inventoryDataCopy = lodash.cloneDeep(this.inventoryList);
//         this.inventoryLoader.next({inventoryListCopy: inventoryDataCopy})
//     })

//     }else{
//       const inventoryDataCopy = lodash.cloneDeep(this.inventoryList);
//       this.inventoryLoader.next({inventoryListCopy: inventoryDataCopy})
//     }
//   }

initInventoryData(){
  if(sessionStorage.getItem('jwt')){
    console.log('Retrived JWT successfully')
  }else{
    console.log('Failed to retrieve JWT token')
  }

    this.inven_service.getAllItems().subscribe(res => {
        console.log("API for get inventory list data: ");
        console.log(res);

        this.inventoryList = res.map(item => {
          return {
            itemId: item.inventoryId,
            itemName: item.name,
            itemSupplier: item.supplier,
            itemCatNum: item.catalogNumber,
            itemInfo: item.catalogNumber + '/' + item.name
          }
        })
        this.inventoryLoader.next({inventoryListCopy: this.inventoryList})
    })
  }

  getInventoryList(){
    const inventoryCopyData = lodash.cloneDeep(this.inventoryList);
    return inventoryCopyData;
  }

  searchItemDetail(itemId){
    // let headers = new HttpHeaders().set("Authorization", 'Bearer '+sessionStorage.getItem('jwt'));
    const headers = this.createHeader()
    return this.http.get<any>(BASE_URL + '/inventoryV2/get/' + itemId, {headers});
  }


  getInventoryLoader(){
    return this.inventoryLoader.asObservable();
  }

  formatSubStepData(array, mode, useCase){

    // system will lose the pointers for sub step arrays when there is too much data in a worksheet instance
    const subStepArray = lodash.cloneDeep(array)
    if(useCase === 'send'){
      let filterArray;
      filterArray = subStepArray.map((subMaterial, index) => {
        // subMaterial.stepData.dbid = -1;
        console.log(`(${mode}) reformatted for submaterial ${index}`)
        const subDilutionData = subMaterial.stepData.subStepArray.map((subDilution, index) => {
          // subDilution.stepData.dbid = -1;
          console.log(`(${mode}) sub dilution ${index} formatted (${subDilution.stepData ? 'valid' : 'undefined'})`)
          return subDilution.stepData
        });
        console.log(`sub material ${index}'s array length for sub dilutions is: ${subDilutionData.length}`)
        subMaterial.stepData.subStepArray = subDilutionData;
        return subMaterial.stepData;
      });

      return filterArray;
    }

    if(useCase === 'receive'){
      subStepArray.forEach(subMaterial => {
       const subDilutionArray = subMaterial.subStepArray.map((subDilution,index) => {
         return {
          stepData: subDilution,
          stepIndex: index
         }
       })
       subMaterial.subStepArray = subDilutionArray;
      })
      // console.log("SUB STEP PROTOTYPE:");
      // console.log(subStepArray);

      let materialArray = [];
      subStepArray.forEach((subMaterial, index) => {
        materialArray.push({stepData: subMaterial, stepIndex: index})
      } )
      console.log("filter array for formatting sub steps for receiving: ");
      console.log(materialArray);
      return lodash.cloneDeep(materialArray);
    }

  }

  unifyConcentration(concentration, unit){
    let newConcentration;

    switch(unit){
      case 'mg/mL':
        newConcentration = concentration * 1000 * 1000 * 1000
        break;

      case  'µg/mL':
        newConcentration = concentration * 1000 * 1000
        break;

      case  'ng/mL':
        newConcentration = concentration * 1000
        break;

      case  'pg/mL':
        newConcentration = concentration
        break;
    }

    return newConcentration
  }


  navToHome(){
    this.router.navigate(['./main'])
  }

  accessControl(userInfo){
    let privilege = {
      design: false,
      experiment: false,
      review: false
    }
    if(userInfo && userInfo.userRoles[0].operations.length > 0){
      userInfo.userRoles[0].operations.forEach(operation => {
        if(operation.operationName === 'Edit Worksheet Design'){
          privilege.design = true
        }

        if(operation.operationName === 'Execute Worksheet Instance'){
          privilege.experiment = true
        }

        if(operation.operationName === 'Review Worksheets'){
          privilege.review = true
        }
      })

      console.log(`eWorksheet privileges for user: ${userInfo.name}`)
      console.log(privilege)
      return privilege
    }else{
      console.log('Invalid user info parameter for access control function')
      return privilege
    }
  }

}//End of the file



