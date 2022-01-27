import { Component, OnInit, OnDestroy, ChangeDetectorRef, ViewChild } from '@angular/core';
import { ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Location, LocationStrategy, PathLocationStrategy} from '@angular/common';
// import { AuthenticationService } from 'src/app/Authenticate.Service';
// import { User } from 'src/app/Objects/User';
import { Subscription } from 'rxjs/internal/Subscription';
import { WorksheetDesignService } from '../../../shared/services/eWorksheet.service';
import * as lodash from "lodash";
import { PageEvent } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { FileUploadComponent } from '../FileUpload/file-upload/file-upload.component';
import { SignatureComponent } from '../Signature/signature/signature.component';
import { AlertMessageComponent } from '../alert-message/alert-message.component';
import { AlertService } from '../_alert';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
// import { G_DialogComponent } from 'app/page/dialog/dialog.component';
import { DialogComponent } from '../Dialog/dialog.component';
@Component({
  selector: 'app-ws-experiment',
  templateUrl: './ws-experiment.component.html',
  styleUrls: ['./ws-experiment.component.scss', '../FreeComponents/stepper/stepper.component.scss']
})
export class WsExperimentComponent implements OnInit, OnDestroy {
  @ViewChild('platemap_view_experiment')  platemap_view_experiment : FileUploadComponent;
  @ViewChild('result_view_experiment')  result_view_experiment : FileUploadComponent;
  @ViewChild('platemap_view_review')  platemap_view_review : FileUploadComponent;
  @ViewChild('result_view_review')  result_view_review : FileUploadComponent;

  isLoading: boolean = false
  loadingCounter: number = 0
  loadingCheck: number = 0

  addSub: Subscription;
  getDesignListSub: Subscription;
  getAssignmentListSub: Subscription;
  getCompleteListSub: Subscription;
  getIncompleteListSub: Subscription;
  getApprovedListSub: Subscription;
  getRejectedListSub: Subscription;
  designDetailSub: Subscription;
  instanceDetailSub: Subscription;
  updateInstanceSub: Subscription;

  privilege = {
    design: false,
    experiment: false,
    review: false
  }

  worksheetConfig = {
      worksheetId: null,
      worksheetName: null,
      templateId: null,
      exObjective: null,
      exType: null,
      analystId:  null,
      analystName:  null,
      reviewerName:  null,
      reviewerId:  null,
      startDate:  null,
      saveDate: null,
      completionDate:  null,
      reviewDate:  null,
      status:  null,
      overallComment: null,
      designComment: null,
      stepArray: null,
      files: [],
      results: []
  };

  alertOptions={
    autoClose: true,
    keepAfterRouteChange: false
  }


  client: string;
  subIndex: number = 0;
  steps;
  isNewWorksheet:boolean = true;
  initDate:Date;
  tabIndex: number = 0;
  enableComment:boolean = true;
  showComment:boolean = false;
  dataType = 'instance';
  hideOverallComment: boolean = true;
  forceSave: boolean = false;
  timerMessageArray = [];
  initData_files = [];
  initData_results = [];

  // stepInitData;

  // mock assignment data
  assignmentList;
  incompleteList;
  completeList;
  approvedList;
  rejectedList;
  mode;
  worksheetStatus:string;
  readMode:boolean = false;
  loginUser = {
    id: 0,
    name: null,
  }
  operator: string;
  toolBarTitle: string = "Hello";

  // Variables needed for table displays
  displayedColumns_design: string[] = ['studyNum', 'wsNum', 'name', 'type',  'objective','author','assignDate', 'id'];
  displayedColumns_complete: string[] = ['studyNum', 'wsNum', 'exVersion', 'name', 'type',  'objective', 'analyst', 'completionDate', 'id'];
  displayedColumns_incomplete: string[] = ['studyNum', 'wsNum', 'exVersion', 'name', 'type',  'objective', 'analyst', 'saveDate', 'id'];
  displayedColumns_approved: string[] = ['studyNum', 'wsNum', 'exVersion', 'name', 'type',  'objective', 'reviewer', 'reviewDate', 'id'];
  displayedColumns_rejected: string[] = ['studyNum', 'wsNum', 'exVersion', 'name', 'type',  'objective', 'reviewer', 'reviewDate', 'id'];
  tableList_design = [];
  tableList_complete = [];
  tableList_incomplete = [];
  tableList_approved = [];
  tableList_rejected = [];
  dataSource_design;
  dataSource_complete;
  dataSource_incomplete;
  dataSource_approved;
  dataSource_rejected;
  pageIndex_design: number = 0;
  pageIndex_complete: number = 0;
  pageIndex_incomplete: number = 0;
  pageIndex_approved: number = 0;
  pageIndex_rejected: number = 0;
  pageSize_design: number = 10;
  pageSize_complete: number = 10;
  pageSize_incomplete: number = 10;
  pageSize_approved: number = 10;
  pageSize_rejected: number = 10;
  sizeOption = [5, 10, 25, 50]
  sortState_design = {
    active: "studyNum",
    direction: "asc"
  }
  sortState_complete = {
    active: "studyNum",
    direction: "asc"
  }
  sortState_incomplete = {
    active: "studyNum",
    direction: "asc"
  }
  sortState_approved = {
    active: "studyNum",
    direction: "asc"
  }
  sortState_rejected = {
    active: "studyNum",
    direction: "asc"
  }

  alert= {
    title: 'Alert Title',
    alerts: [],
    comment: 'Alert Comment'
  }

  stepperIndex = 0;
  screenLock = false;



  constructor(private route: ActivatedRoute,
    private location: Location,
    private ws_design_service: WorksheetDesignService,
    private router: Router,
    private dialog: MatDialog,
    private alertDialog: MatDialog,
    private cd: ChangeDetectorRef,
    private authenticationservice: AuthenticationService2,
    private alertService: AlertService) { }

  ngOnInit(): void {
    this.getUserInfo();
    this.initDate = new Date();

    this.route.queryParamMap.subscribe(queryParams => {
      this.tabIndex = +queryParams.get("tabIndex");
      console.log("tab index is: " + this.tabIndex)
    })

    this.route.paramMap.subscribe((paramMap: ParamMap) => {

      //condition for rendering analyst users' langing pages
      if (paramMap.has("analystId")) {
        this.setLoadingCheck(4)
        //init default search bar client for analyst users
        this.client = 'design';
        this.operator = "analyst";
        this.mode = "preview";
        // the getUserInfo() will initialize both user ID and name when this component is launched
        // this.loginUser.id = +paramMap.get("analystId");
        this.toolBarTitle = "Worksheets"

        // call API to get all assignments for user with loginUser.id
        // The reason why the API call below grabs user ID from url is because the getUserInfo() has some delay to get user ID from the database
        this.getAssignmentListById(+paramMap.get("analystId"))




        // call API to get all incomplete template designs for login user
        this.getIncompleteListById();



         // call API to get all approved worksheet instances for login user
         this.getApprovedListById()


        // call API to get all rejected worksheet instances for login user
        this.getRejectedListById()


    }

      //condition for rendering scientist users' langing pages
      else if(paramMap.has("scientistId")){
        this.setLoadingCheck(3)
        //init default search bar client for scientists users
        this.client = 'complete'
        this.operator = "scientist";
        this.mode = "preview";
        this.toolBarTitle = "Submitted Assignments"

        // call API to get all complete worksheet instances for review
        this.getCompleteList();


        // call API to get all approved worksheet instances for read
        this.getApprovedList();


        // call API to get all rejected worksheet instances for read
        this.getRejectedList();



      }else if(paramMap.has("templateId")){
        this.operator = "analyst";
        this.mode = "experiment";
        this.dataType = 'design'
        const templateId = +paramMap.get("templateId");
        this.createWorksheet(templateId);
        this.toolBarTitle = "Worksheet Execution";

      }

      //condition for users to import worksheet instances from the database for either edit or review
      else if (paramMap.has("worksheetId")){
        // let readMode:boolean;
        this.route.queryParamMap.subscribe(queryParams => {
          this.operator = queryParams.get("operator");
          this.enableComment = queryParams.get("isReviewed") === 'true'? false:true;
          this.readMode = queryParams.get("isReviewed") === 'true'? true:false;
        })
        const worksheetId = +paramMap.get("worksheetId");
        this.importWorksheet(worksheetId);
        switch(this.operator){
          case "analyst":
            if(this.readMode){
              this.mode = "review";
              this.toolBarTitle = "Worksheet Review";
            }

            if(!this.readMode){
              this.mode = "experiment";
              this.toolBarTitle = "Worksheet Editing"
            }

            break;
          case "scientist":
            this.mode = "review";
            this.toolBarTitle = "Worksheet Review";
            break;
        }
      }

    });

    console.log("mode property is: " + this.mode);


    // this.checkOveralComment();
    this.tabChange({index: this.tabIndex});

  } // End of ngOnInit function

  setLoading(status){
    this.isLoading = status
  }

  setLoadingCounter(num){
    this.loadingCounter = num
  }

  setLoadingCheck(num){
    this.loadingCheck = num
    this.checkLoading()
  }

  checkLoading(){
    if(this.loadingCheck <= this.loadingCounter){
      this.setLoading(false)
    }else{
      this.setLoading(true)
    }
  }

  refreshPage(page){
    let options;
    options = this.alertOptions;
    options.id = 'refresh-info';

    switch(page){
      case 'complete-review':
        this.getCompleteList();
        this.alertService.info(
          `Refreshed review list, and retrieved ${this.completeList.length} ${(this.completeList.length > 1)? 'results':'result'}`, options)
        break;

      case 'approved-review':
        this.getApprovedList();
        this.alertService.info(
          `Refreshed approved worksheet list, and retrieved ${this.approvedList.length} ${(this.approvedList.length > 1)? 'results':'result'}`,
          options)
        break;

      case 'rejected-review':
        this.getRejectedList();
        this.alertService.info(
          `Refreshed rejected worksheet list, and retrieved ${this.rejectedList.length} ${(this.rejectedList.length > 1)? 'results':'result'}`,
          options)
        break;

      case 'assignment':
        this.getAssignmentListById(this.loginUser.id);
        this.alertService.info(
          `Refreshed assignment list, and retrieved ${this.assignmentList.length} ${(this.assignmentList.length > 1)? 'results':'result'}`,
          options)
        break;

      case 'incomplete':
        this.getIncompleteListById();
        this.alertService.info(
          `Refreshed Incomplete worksheet list, and retrieved ${this.incompleteList.length} ${(this.incompleteList.length > 1)? 'results':'result'}`,
          options)
        break;

      case 'approved-ex':
        this.getApprovedListById();
        this.alertService.info(
          `Refreshed approved worksheet list, and retrieved ${this.approvedList.length} ${(this.approvedList.length > 1)? 'results':'result'}`,
          options)
        break;

      case 'rejected-ex':
        this.getRejectedListById();
        this.alertService.info(
          `Refreshed rejected worksheet list, and retrieved ${this.rejectedList.length} ${(this.rejectedList.length > 1)? 'results':'result'}`,
          options)
        break;
    }
  }

  getAssignmentListById(analystId){
    this.getAssignmentListSub = this.ws_design_service.getAssignmentList(analystId).subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log(`Original assignment list is(user ${this.loginUser.id}):`)
      console.log(res.assignments)
      this.assignmentList = this.uniqueAssignmentList(res.assignments)
      this.assignmentList.forEach(assignment => {
        assignment.dateStr = this.dateFormat(assignment.assignDate);
      })
      this.initTableList('design', this.assignmentList);
      console.log(`Unique assignment list is(user ${this.loginUser.id}):`);
      console.log(this.assignmentList);
    })
  }

  getIncompleteListById(){
    this.getIncompleteListSub = this.ws_design_service.getIncompleteList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get in-progress list");
      console.log(res);
      // this.incompleteList = res.summaries;
      this.incompleteList = res.summaries.filter(worksheet => worksheet.analystId === this.loginUser.id);

      this.incompleteList.forEach(assignment => {
        assignment.dateStr = this.dateFormat(assignment.saveDate);
      })

      console.log("incomplete list content: ");
      console.log(this.incompleteList)
      this.initTableList('incomplete', this.incompleteList)
    })
  }

  getApprovedListById(){
    this.getApprovedListSub = this.ws_design_service.getApprovedList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get approved list");
      console.log(res);

      this.approvedList = res.summaries.filter(worksheet => worksheet.analystId = this.loginUser.id);

      this.approvedList.forEach(instance => {
        instance.dateStr = this.dateFormat(instance.reviewDate);
      })
      console.log("approved list content after filter for analyst:");
      console.log(this.approvedList);
      this.initTableList('approved', this.approvedList)
    })
  }

  getApprovedList(){
    this.getApprovedListSub = this.ws_design_service.getApprovedList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get approved list");
      console.log(res);
      this.approvedList = res.summaries;

      this.approvedList.forEach(instance => {
        instance.dateStr = this.dateFormat(instance.reviewDate);
      })
      this.initTableList('approved', this.approvedList)
    })
  }

  getRejectedListById(){
    this.getRejectedListSub = this.ws_design_service.getRejectedList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get rejected list");
      console.log(res);
      this.rejectedList = res.summaries.filter(worksheet => worksheet.analystId = this.loginUser.id);

      this.rejectedList.forEach(instance => {
        instance.dateStr = this.dateFormat(instance.reviewDate);
      })
      console.log("rejected list content after filter for analyst")
      console.log(this.rejectedList)
      this.initTableList('rejected', this.rejectedList)
    })
  }

  getRejectedList(){
    this.getRejectedListSub = this.ws_design_service.getRejectedList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get rejected list");
      console.log(res);
      this.rejectedList = res.summaries;

      this.rejectedList.forEach(instance => {
        instance.dateStr = this.dateFormat(instance.reviewDate);
      })
      this.initTableList('rejected', this.rejectedList)
    })
  }


  getCompleteList(){
    this.getCompleteListSub = this.ws_design_service.getCompleteList().subscribe(res => {
      this.loadingCounter++
      this.checkLoading()
      console.log("response payload from calling get complete list");
      console.log(res);
      this.completeList = res.summaries;

      this.completeList.forEach(instance => {
        instance.dateStr = this.dateFormat(instance.completionDate);
      })

      this.initTableList('complete', this.completeList)
      console.log("complete list content: ");
      console.log(this.completeList)
    })
  }

  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.loginUser.name = user.name;
        this.loginUser.id = user.userId;
        console.log("worksheet experiment user info: ");
        console.log(user);

        this.privilege = this.ws_design_service.accessControl(user)
      } )
    }

  }

  sortData(sort, tabType){
    let data;
    switch(tabType){
      case 'design':
        this.sortState_design.active = sort.active;
        this.sortState_design.direction = sort.direction;
        data = lodash.cloneDeep(this.dataSource_design);
        break;

      case 'complete':
        this.sortState_complete.active = sort.active;
        this.sortState_complete.direction = sort.direction;
        data = lodash.cloneDeep(this.dataSource_complete);
        break;

      case 'incomplete':
        this.sortState_incomplete.active = sort.active;
        this.sortState_incomplete.direction = sort.direction;
        data = lodash.cloneDeep(this.dataSource_incomplete);
        break;

      case 'approved':
        this.sortState_approved.active = sort.active;
        this.sortState_approved.direction = sort.direction;
        data = lodash.cloneDeep(this.dataSource_approved);
        break;

      case 'rejected':
        this.sortState_rejected.active = sort.active;
        this.sortState_rejected.direction = sort.direction;
        data = lodash.cloneDeep(this.dataSource_rejected);
        break;
    }

    if (!sort.active || sort.direction === '') {
      return;
    }

    const tempData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        // case 'position': return this.compare(a.position, b.position, isAsc);
        // case 'assayNum': return this.compare(a.assayNum, b.assayNum, isAsc);
        case 'studyNum': return this.compare(a.studyNum, b.studyNum, isAsc);
        case 'wsNum': return this.compare(a.wsNum, b.wsNum, isAsc);
        // case 'exeNum': return this.compare(a.exeNum, b.exeNum, isAsc);
        case 'exVersion': return this.compare(a.exVersion, b.exVersion, isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'type': return this.compare(a.type, b.type, isAsc);
        case 'objective': return this.compare(a.objective, b.objective, isAsc);
        case 'author': return this.compare(a.author, b.author, isAsc);
        case 'analyst': return this.compare(a.analyst, b.analyst, isAsc);
        case 'reviewer': return this.compare(a.reviewer, b.reviewer, isAsc);
        case 'assignDate': return this.compare(a.assignDate, b.assignDate, isAsc);
        case 'completionDate': return this.compare(a.completionDate, b.completionDate, isAsc);
        case 'saveDate': return this.compare(a.saveDate, b.saveDate, isAsc);
        case 'reviewDate': return this.compare(a.reviewDate, b.reviewDate, isAsc);
        default: return 0;
      }
    });

    switch(tabType){
      case 'design':
        this.dataSource_design = tempData;
        break;

      case 'complete':
        this.dataSource_complete = tempData;
        break;

      case 'incomplete':
        this.dataSource_incomplete = tempData;
        break;

      case 'approved':
        this.dataSource_approved = tempData;
        break;

      case 'rejected':
        this.dataSource_rejected = tempData;
        break;
    }
  }


  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  compareBoolean(a, b, isAsc){
    return (a === true ? 1 : -1) * (isAsc ? 1 : -1);
  }

  onPageChange(pageData: PageEvent, tabType) {
    switch(tabType){
      case 'design':
        this.pageIndex_design = pageData.pageIndex
        this.pageSize_design = pageData.pageSize
        this.splitPageList(this.pageIndex_design, this.pageSize_design, tabType);
        break;

      case 'complete':
        this.pageIndex_complete = pageData.pageIndex
        this.pageSize_complete = pageData.pageSize
        this.splitPageList(this.pageIndex_complete, this.pageSize_complete, tabType);
        break;

      case 'incomplete':
        this.pageIndex_incomplete = pageData.pageIndex
        this.pageSize_incomplete = pageData.pageSize
        this.splitPageList(this.pageIndex_incomplete, this.pageSize_incomplete, tabType);
        break;

      case 'approved':
        this.pageIndex_approved = pageData.pageIndex
        this.pageSize_approved = pageData.pageSize
        this.splitPageList(this.pageIndex_approved, this.pageSize_approved, tabType);
        break;

      case 'rejected':
        this.pageIndex_rejected = pageData.pageIndex
        this.pageSize_rejected = pageData.pageSize
        this.splitPageList(this.pageIndex_rejected, this.pageSize_rejected, tabType);
        break;
    }

  }

  splitPageList(pageIndex = 0, pageSize = 10, tabType){
    const startAt = pageIndex * pageSize;
    const endAt = startAt + pageSize;
    switch(tabType){
      case 'design':
        this.dataSource_design = this.tableList_design.slice(startAt, endAt);
        this.sortData(this.sortState_design, tabType);
        break;

      case 'complete':
        this.dataSource_complete = this.tableList_complete.slice(startAt, endAt);
        this.sortData(this.sortState_complete, tabType);
        break;

      case 'incomplete':
        this.dataSource_incomplete = this.tableList_incomplete.slice(startAt, endAt);
        this.sortData(this.sortState_incomplete, tabType);
        break;

      case 'approved':
        this.dataSource_approved = this.tableList_approved.slice(startAt, endAt);
        this.sortData(this.sortState_approved, tabType);
        break;

      case 'rejected':
        this.dataSource_rejected = this.tableList_rejected.slice(startAt, endAt);
        this.sortData(this.sortState_rejected, tabType);
        break;
    }

  }

  initTableList(tabType, list){
    let tempList

    switch(tabType){
      case 'design':
        tempList = list.map((design, index) => {
          return{
            // position: index + 1,
            name: design.templateName,
            studyNum: design.studyNum,
            wsNum: design.wsNum || null,
            // exeNum: design.exeNum,
            type: design.exType,
            objective: design.exObjective,
            author: design.scientist,
            assignDate: design.dateStr,
            id: design.templateId
          }
        })
        this.tableList_design = lodash.cloneDeep(tempList);
        this.splitPageList(this.pageIndex_design, this.pageSize_design, tabType)
        break;

      case 'complete':
        tempList = list.map((instance, index) => {
          return{
            // position: index + 1,
            // assayNum: instance.worksheetId,
            name: instance.worksheetName,
            studyNum: instance.studyNum,
            wsNum: instance.wsNum || null,
            exVersion: instance.exVersion,
            type: instance.exType,
            objective: instance.exObjective,
            analyst: instance.analystName,
            completionDate: instance.completionDate,
            dateStr: instance.dateStr,
            id: instance.worksheetId
          }
        })
        this.tableList_complete = lodash.cloneDeep(tempList);
        this.splitPageList(this.pageIndex_complete, this.pageSize_complete, tabType)
        break;

      case 'incomplete':
        tempList = list.map((instance, index) => {
          return{
            // position: index + 1,
            // assayNum: instance.worksheetId,
            name: instance.worksheetName,
            studyNum: instance.studyNum,
            wsNum: instance.wsNum || null,
            exVersion: instance.exVersion,
            type: instance.exType,
            objective: instance.exObjective,
            analyst: instance.analystName,
            saveDate: instance.saveDate,
            dateStr: instance.dateStr,
            id: instance.worksheetId
          }
        })
        this.tableList_incomplete = lodash.cloneDeep(tempList);
        this.splitPageList(this.pageIndex_incomplete, this.pageSize_incomplete, tabType)
        break;

      case 'approved':
        tempList = list.map((instance, index) => {
          return{
            // position: index + 1,
            // assayNum: instance.worksheetId,
            name: instance.worksheetName,
            studyNum: instance.studyNum,
            wsNum: instance.wsNum || null,
            exVersion: instance.exVersion,
            type: instance.exType,
            objective: instance.exObjective,
            reviewer: instance.reviewerName,
            reviewDate: instance.reviewDate,
            dateStr: instance.dateStr,
            id: instance.worksheetId
          }
        })
        this.tableList_approved = lodash.cloneDeep(tempList);
        this.splitPageList(this.pageIndex_approved, this.pageSize_approved, tabType)
        break;

      case 'rejected':
        tempList = list.map((instance, index) => {
          return{
            // position: index + 1,
            // assayNum: instance.worksheetId,
            name: instance.worksheetName,
            studyNum: instance.studyNum,
            wsNum: instance.wsNum || null,
            exVersion: instance.exVersion,
            type: instance.exType,
            objective: instance.exObjective,
            reviewer: instance.reviewerName,
            reviewDate: instance.reviewDate,
            dateStr: instance.dateStr,
            id: instance.worksheetId
          }
        })
        this.tableList_rejected = lodash.cloneDeep(tempList);
        this.splitPageList(this.pageIndex_rejected, this.pageSize_rejected, tabType)
        break;
    }

  }

  dateFormat(date){
    const newDate = new Date(date);

    // The month count starts with 0 (Jan), up to 11 (Dec).
    const month = newDate.getMonth() + 1;
    return newDate.getFullYear() + "/" + month + "/" + newDate.getDate();
  }

  checkOveralComment(){
    if(this.readMode){
      if(this.worksheetConfig.overallComment){
        this.hideOverallComment = false;
      }

    }else{
      if(this.mode === 'review' && this.dataType === 'instance'){
        this.hideOverallComment = false;
      }
    }
  }


  createWorksheet(templateId){
    this.designDetailSub = this.ws_design_service.getDesignById(templateId).subscribe(res => {
      console.log(`response from get template details by ID (${templateId})`)
      console.log(res);
      this.worksheetConfig = this.ws_design_service.dataFormatReceive(res, "instance");


      //prepopulate date for worksheet configuration
      this.worksheetConfig.worksheetId = -1;
      this.worksheetConfig.templateId = templateId;
      this.worksheetConfig.analystId = this.loginUser.id;
      this.worksheetConfig.analystName = this.loginUser.name;
      this.worksheetConfig.startDate = this.initDate.getTime();
      this.worksheetConfig.saveDate = null;
      this.isNewWorksheet = true;
        console.log("worksheet configuration after initialization: ");
        console.log(this.worksheetConfig);

      this.steps = lodash.cloneDeep(this.worksheetConfig.stepArray);
      if(this.worksheetConfig.files.length > 0){
        this.initData_files = lodash.cloneDeep(this.worksheetConfig.files);
        this.cd.detectChanges();
        this.platemap_view_experiment.initFileData(this.initData_files);
      }

      this.initSteps(this.steps, "create");
    });


  }

  importWorksheet(worksheetId){
    this.instanceDetailSub = this.ws_design_service.getInstanceById(worksheetId).subscribe(res => {
      console.log("response from calling GET worksheet instance API:");
      console.log(res);
      this.worksheetConfig = this.ws_design_service.dataFormatReceive(res, "instance");
      console.log("API instance data after reformat:");
      console.log(this.worksheetConfig);
      //need to be deleted later on
    // this.worksheetConfig.designComment = 'design comment for review';


      this.worksheetConfig.analystId = this.loginUser.id;
      this.worksheetConfig.analystName = this.loginUser.name;

      this.initData_files = lodash.cloneDeep(this.worksheetConfig.files);
      this.initData_results = lodash.cloneDeep(this.worksheetConfig.results);

      this.cd.detectChanges();
      // // Pass in the file data payload for file upload component's initialization
      if(this.mode === 'experiment'){
        if(this.initData_files.length > 0){
          this.platemap_view_experiment.initFileData(this.initData_files);
        }
        if(this.initData_results.length > 0){
          this.result_view_experiment.initFileData(this.initData_results);
        }

      }


      // this.worksheetConfig.saveDate = null;
      this.isNewWorksheet = false;

      this.steps = this.worksheetConfig.stepArray;
      this.initSteps(this.steps, "import");

      this.checkOveralComment();

    })


  }


  initSteps(steps, mode){
    let orderIndex = 0;
    const tempArr = lodash.cloneDeep(steps);
    console.log("!!!!! ----->Incoming step array for initSteps function:");
    console.log(tempArr)

    if(mode === 'create'){
      //add stepIndex for each step
    steps.forEach(step => {
      // orderIndex++;
      step.dbid = -1;
      // step.stepIndex = orderIndex;
      step.stepValid = false;
      // step.activeTimer = false;
      step.commentData = {
        experimentComment: null,
        reviewComment: null
      }
    })
    this.resetSubMaterialDbid(steps);
    console.log("Reset dbid for sub materials and sub dilutions to -1")
    }

    if(mode === 'import'){
       //add stepIndex for each step
    steps.forEach(step => {
      // step.dbid = step.dbid;



      // orderIndex++;
      // step.stepIndex = orderIndex;
    })
    }

    console.log(`worksheet with template ID ${this.worksheetConfig.templateId}'s step data after ordered:`);
    console.log(steps);
    // this.initSubStepArray(steps);

    this.cd.detectChanges();
    console.log("!!!!!!! refresh view")
  }


  // Reset sub material dbid to -1 for new worksheet instance
  resetSubMaterialDbid(steps){
    steps.forEach(step => {
      if(step.stepData.subStepArray){
        if(step.stepData.subStepArray.length > 0){
          step.stepData.subStepArray.forEach(subMaterial => {
            subMaterial.stepData.dbid = -1
            if(subMaterial.stepData.subStepArray.length > 0){
              this.resetSubDilutionDbid(subMaterial.stepData.subStepArray)
            }
          })
        }
      }
    })
  }

  // Reset sub dilution dbid to -1 for new worksheet instance
  resetSubDilutionDbid(steps){
    steps.forEach(subDilution => {
      subDilution.stepData.dbid = -1
    })
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
  }

  // activeTimer(event){
  //   this.steps.forEach(step => {
  //     if(step.stepIndex === event.stepIndex){
  //       step.activeTimer = event.activeTimer
  //       step.isOverDue = event.isOverDue;
  //       if(step.activeTimer === true){
  //         console.log(`${step.stepType} timer is active.`)
  //       }else{
  //         console.log(`${step.stepType} timer is deactivated.`)
  //       }
  //     }
  //   })
  // }

  dataReceiver(userData){
    console.log('!!!!!! -------> dataReceiver incoming parameter:' )
    console.log(userData)
    this.steps.forEach(step => {
      if(step.stepIndex === userData.stepIndex){
        if(userData.stepData){
          step.stepData = userData.stepData
        }

        step.stepValid = userData.valid
      }

    })
    console.log("step configuration is updated: ");
    console.log(this.steps);
  }


  checkSave(exit = true){

    let saveValid = true;
    this.steps.forEach((step,index) => {
      if(step.stepData.incubationArray && step.stepData.isExcluded === 0){
        step.stepData.incubationArray.forEach(incubation => {
          if(incubation.overdue){
            step.stepValid = false;
            saveValid = false;
            this.timerMessageArray.push(`Step ${index + 1} (${step.stepType})'s timer for incubation ${incubation.index} is overdue`)
          }
        })

      }
    })

    if(saveValid || this.forceSave){
      this.onSave(exit);
    }else{
      this.openDialog("timer");
    }
  }

  onSave(exit = true){
    const saveDate = new Date();
    let incomplete = false;

    this.worksheetConfig.saveDate = saveDate.getTime();
    let stepMessage: string = "";

      this.steps.forEach((step, index) => {
        if(step.stepValid){
          stepMessage = stepMessage + ` Step ${index + 1} (${step.stepType}) is: COMPLETE. \n\n`
          this.alert.alerts.push({message: `Step ${index + 1} (${step.stepType}) is: COMPLETE`, color: '#33cd52'})
        }
        else{
          incomplete = true;
          stepMessage = stepMessage + ` Step ${index + 1} (${step.stepType}) is: NOT COMPLETE ! \n\n`
          this.alert.alerts.push({message: `Step ${index + 1} (${step.stepType}) is: NOT COMPLETE`, color: '#f44336'})
        }


      })

      if(incomplete){
          this.tabIndex = 1;
          this.worksheetConfig.status = "incomplete"
          this.alert.title = 'Worksheet is saved';
          this.alert.comment = 'Incomplete worksheet is saved.'

      }else{
        this.worksheetConfig.status = "complete"
        this.worksheetConfig.completionDate = saveDate.getTime();
        this.alert.title = 'Worksheet is saved'
        this.alert.comment = 'Worksheet is complete, and is submitted for review.'
      }


      if(!exit && this.worksheetConfig.status === 'incomplete'){
        this.alert.title = 'steps are saved'
        this.alert.comment = 'System saves this worksheet\'s all steps.'
      }

      if(this.worksheetConfig.status === 'complete'){
        exit = true
      }


  //filter out the unnecessary information from each step
  const leanStepArray = this.steps.map(step => {
    return {
      dbid: step.dbid,
      stepType: step.stepType,
      stepData: step.stepData,
      commentData: step.commentData
    }
  })

  this.worksheetConfig.stepArray = lodash.cloneDeep(leanStepArray);


  if(this.isNewWorksheet){

    console.log("details of the saved worksheet instance: ");
    console.log(this.worksheetConfig);
    this.addSub = this.ws_design_service.addInstance(this.worksheetConfig).subscribe(res => {
      console.log("response from backend (add instance): ");
      console.log(res);
      // On page update for manipulate worksheet data structure for later worksheet update
      this.onPageUpdate(res.worksheetId);
      this.updateRunTime(this.worksheetConfig.templateId, exit)
      // this.isNewWorksheet = false;
      // this.openAlertWindow(this.alert);
      // if(exit){
      //   this.router.navigate(["/ws_experiment/analyst", this.loginUser.id], { queryParams: { tabIndex: this.tabIndex } } );
      // }
    })


  }else{
    console.log(`updating worksheet ${this.worksheetConfig.worksheetId}:`);
    this.updateInstanceSub = this.ws_design_service.updateInstance(this.worksheetConfig.worksheetId, this.worksheetConfig).subscribe(res =>{
      console.log("response from API for update worksheet instance:");
      console.log(res);
      this.openAlertWindow(this.alert);
      if(exit){
        this.navToExperiment()
        // this.router.navigate(["/ws_experiment/analyst", this.loginUser.id], { queryParams: { tabIndex: this.tabIndex } } );
      }
    })

  }



}

onPageUpdate(worksheetId){
  const ID = +worksheetId;

  if(ID) {
    console.log("On page update for generated worksheet ID: " + ID)
    this.importWorksheet(ID)
  }else{
    console.log('Error: Fail to read generated worksheet ID for on page update')
  }
}

// update parent tempalte's execution number
updateRunTime(templateId, exit){

  this.setNew(false);
  this.openAlertWindow(this.alert);

  this.getDesignListSub = this.ws_design_service.getDesignList().subscribe(res => {
    console.log('Response of the API call for getting design list in function updateRunTime()')
    console.log(res.summaries)

    if(res.summaries.length > 0){
      const assignment = res.summaries.find(assignment => assignment.templateId === templateId)

      if(assignment){
        console.log('the found assignment object')
        console.log(assignment)
        this.ws_design_service.updateRunNum(templateId, assignment.exeNum, 'incre')
      }else{
        console.log(`Error: no design found for template ID ${templateId}`)
      }
    }else{
      console.log('design list is empty in function updateRunTime()')
    }

    if(exit){
      this.navToExperiment()
    }

  })

}

navToExperiment(){
  this.router.navigate(["/ws_experiment/analyst", this.loginUser.id], { queryParams: { tabIndex: this.tabIndex } } );
}

toHome(){
  this.ws_design_service.navToHome()
}

setNew(status){
  this.isNewWorksheet = status
}



changeRoute(worksheetId, operator, isReviewed){
    this.router.navigate(["/ws_experiment/worksheet", worksheetId], { queryParams: { operator: operator, isReviewed: isReviewed} });
}

updateStepComment(event){
  this.steps.forEach(step => {
    if(step.stepIndex === event.stepIndex){

      if(event.commentType === 'experiment'){
        step.commentData.experimentComment = event.stepComment
      }

      if(event.commentType === 'review'){
        step.commentData.reviewComment = event.stepComment
      }

      console.log("worksheet comment is udpated:");
      console.log(this.steps);

    }

  })
}

finishReview(reviewDecision){
  if(reviewDecision === 'approved'){
    this.worksheetConfig.status = "approved";
    this.tabIndex = 1;

  }else{
    this.worksheetConfig.status = "rejected"
    this.tabIndex = 2;

  }

  // config reviewed worksheet for updating the database
  const reviewDate = new Date();
  this.worksheetConfig.reviewDate = reviewDate.getTime();
  //fixed reviewer ID for development
  this.worksheetConfig.reviewerId = this.loginUser.id;
  this.worksheetConfig.reviewerName = this.loginUser.name;

  //filter out the unnecessary information from each step
  const leanStepArray = this.steps.map(step => {
    return {
      dbid: step.dbid,
      stepType: step.stepType,
      stepData: step.stepData,
      commentData: step.commentData
    }
  })

this.worksheetConfig.stepArray = lodash.cloneDeep(leanStepArray);

this.updateInstanceSub = this.ws_design_service.updateInstance(this.worksheetConfig.worksheetId, this.worksheetConfig).subscribe(res =>{
  console.log(`response from API for ${reviewDecision} worksheet instance:`);
  console.log(res);
  this.router.navigate(["/ws_experiment/scientist", 1], { queryParams: { tabIndex: this.tabIndex } } );
})

}

excludeStep(event){
  this.steps.forEach(step => {
    if(step.stepIndex === event.stepIndex){
     step.stepData.isExcluded = event.isExcluded;


     if(event.isExcluded === 1){
      step.stepValid = true;
     }
     // we will not know if step.stepValid is true or false when event.isExcluded is 0 because suppose users finish a step and then skip the finished step, and cancel the skip, after all, the scenario will be isExcluded is 0 but stepValid is true. However, in other cases, if users skip an empty skip, and cancel the skip, then the scenario will be isExcluded is 0 and stepValid is false. Forunately, we can use each step component's own function (updateInfo()) to update step stepValid when users cancel a skip
    }

  })
  console.log("step configuration is updated: ");
  console.log(this.steps);
}

updateOverallComment(event){
  this.worksheetConfig.overallComment = event.comment;
  console.log("worksheet's overall comment is updated: ");
  console.log(this.worksheetConfig.overallComment);
}

cancelOverallComment(){
  // this.showComment = false;

  this.worksheetConfig.overallComment = null;

  console.log("worksheet's overall comment is canceled: ");
  console.log(this.worksheetConfig.overallComment);
}

  ngOnDestroy() {
    if(this.designDetailSub){
      this.designDetailSub.unsubscribe();
    }
    if(this.getAssignmentListSub){
      this.getAssignmentListSub.unsubscribe();
    }
    if(this.addSub){
      this.addSub.unsubscribe();
    }
    if(this.instanceDetailSub){
      this.instanceDetailSub.unsubscribe()
    }
    if(this.getCompleteListSub){
      this.getCompleteListSub.unsubscribe()
    }
    if(this.getIncompleteListSub){
      this.getIncompleteListSub.unsubscribe()
    }
    if(this.updateInstanceSub){
      this.updateInstanceSub.unsubscribe()
    }
    if(this.getApprovedListSub){
      this.getApprovedListSub.unsubscribe()
    }
    if(this.getRejectedListSub){
      this.getRejectedListSub.unsubscribe();
    }
    if(this.getDesignListSub){
      this.getDesignListSub.unsubscribe()
    }
  }

  tabChange(event){

    console.log("tab change event");
    console.log(event);
    if(this.operator === 'analyst'){
      switch(event.index){
        case 0:
          this.client = 'design';
          break;
        case 1:
          this.client = 'incomplete';
          break;
        case 2:
          this.client = 'approved';
          break;
        case 3:
          this.client = 'rejected';
          break;
      }
    }else{
      switch(event.index){
        case 0:
          this.client = 'complete';
          break;
        case 1:
          this.client = 'approved';
          break;
        case 2:
          this.client = 'rejected';
          break;
      }
    }

  }

  onSearchTable(event){
    // this.onCancelSearch(event.client);
    const tabType = event.client;
    const keyword = event.searchData;
    let filterList;
    switch(tabType){
      case 'design':
        filterList = this.tableList_design.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
        if(filterList.length > 0){

          this.tableList_design = lodash.cloneDeep(filterList);
          this.splitPageList(0, this.pageSize_design, tabType);

        }else{
          this.alertService.warn('No matching results', this.alertOptions)
        }
        break;

      case 'complete':
        filterList = this.tableList_complete.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
        if(filterList.length > 0){
          this.tableList_complete = lodash.cloneDeep(filterList);
          this.splitPageList(0, this.pageSize_complete, tabType)
        }else{
          this.alertService.warn('No matching results', this.alertOptions)
        }
        break;

      case 'incomplete':
        filterList = this.tableList_incomplete.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
        if(filterList.length > 0){
          this.tableList_incomplete = lodash.cloneDeep(filterList);
          this.splitPageList(0, this.pageSize_incomplete, tabType)
        }else{
          this.alertService.warn('No matching results', this.alertOptions)
        }
        break;

      case 'approved':
        filterList = this.tableList_approved.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
        if(filterList.length > 0){
          this.tableList_approved = lodash.cloneDeep(filterList);
          this.splitPageList(0, this.pageSize_approved, tabType)
        }else{
          this.alertService.warn('No matching results', this.alertOptions)
        }
        break;

      case 'rejected':
        filterList = this.tableList_rejected.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
        if(filterList.length > 0){
          this.tableList_rejected = lodash.cloneDeep(filterList);
          this.splitPageList(0, this.pageSize_rejected, tabType)
        }else{
          this.alertService.warn('No matching results', this.alertOptions)
        }
        break;
    }
  }

  onCancelSearch(event){
    switch(event.client){
      case 'design':
        this.initTableList(event.client, this.assignmentList)
        break;

      case 'complete':
        this.initTableList(event.client, this.completeList)
        break;

      case 'incomplete':
        this.initTableList(event.client, this.incompleteList)
        break;

      case 'approved':
        this.initTableList(event.client, this.approvedList)
        break;

      case 'rejected':
        this.initTableList(event.client, this.rejectedList)
        break;
    }
  }


  // Functions for confirmation dialog
  openDialog(confirmType) {
    let message;

    switch(confirmType){
      case 'approved':
        message = `Do you want to approve worksheet "${this.worksheetConfig.worksheetName}" ?`
        break;

      case 'rejected':
        message = `Do you want to reject worksheet "${this.worksheetConfig.worksheetName}" ?`
        break;

      case 'timer':
        message = "";
        break;
    }

    let dialogRef = this.dialog.open(DialogComponent, {panelClass: 'dialog-customized', data: {message: message, timerMessageArray: this.timerMessageArray}});
    this.timerMessageArray = [];
    dialogRef.afterClosed().subscribe(result => this.checkConfirm(result, confirmType));
  }

  checkConfirm(result, confirmType) {
    console.log(`(${confirmType})dialog value is: ${result}`);
    if (result === true) {
      switch(confirmType){
        case 'approved':
        case 'rejected':
          this.openSignature(confirmType);
          break;

        case 'timer':
          this.forceSave = true;
          this.onSave()
          break;
      }

    }
  }

  openSignature(confirmType){
    let signatureSub = this.dialog.open(SignatureComponent, { panelClass: 'dialog-customized' });
    signatureSub.afterClosed().subscribe(result => {
      console.log("review signature dialog after close: ");
      console.log(result)

      if(result && result.user_valid){
        this.finishReview(confirmType)
        console.log("Review decision is: " + confirmType);
      }else{
        console.log("Invalid signature.")
      }
    });
  }
  // End of dialog functions


  // Functions for alert dialog
  openAlertWindow(message){
    if(!message){message = this.alert}
    let dialogRef = this.alertDialog.open(AlertMessageComponent, {panelClass: 'dialog-1rem',data:message})
    dialogRef.afterClosed().subscribe(res => this.alert.alerts = []);
  }
  // End of alert functions

  // Functions for stepper UI
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
  // Ens of stepper UI functions


  onFileChange(event){
    this.worksheetConfig.files = lodash.cloneDeep(event.files);
    console.log("worksheet updates file array:");
    console.log(this.worksheetConfig.files)
  }

  onResultChange(event){
    this.worksheetConfig.results = lodash.cloneDeep(event.files);
    console.log("worksheet updates result array:");
    console.log(this.worksheetConfig.results)
  }

  uniqueAssignmentList(orginalAssignmentList){
    const filteredArr = orginalAssignmentList.reduce((acc, current) => {
      const found = acc.find(design => design.templateId === current.templateId);
      if (!found) {
        return acc.concat([current]);
      } else {
        return acc;
      }
    }, []);
    // console.log('Unique asssignment list array: ');
    // console.log(filteredArr)
    return lodash.cloneDeep(filteredArr)
  }




}
