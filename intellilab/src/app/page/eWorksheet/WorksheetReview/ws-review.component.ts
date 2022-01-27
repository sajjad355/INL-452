import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { WorksheetDesignService } from '../../../shared/services/eWorksheet.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import * as lodash from "lodash";
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { MatDialog } from '@angular/material/dialog';
import { AssignmentDialogComponent } from '../AssignmentDialog/assignment-dialog/assignment-dialog.component';
import { AlertService } from '../_alert';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
@Component({
  selector: 'app-ws-review',
  templateUrl: './ws-review.component.html',
  styleUrls: ['./ws-review.component.scss', '../FreeComponents/stepper/stepper.component.scss']
})
export class WsReviewComponent implements OnInit, OnDestroy {
  isLoading: boolean = false
  privilege = {
    design: false,
    experiment: false,
    review: false
  }
  stepperIndex = 0;
  screenLock = false;
  fileUrl: string;
  userId: number = 0;
  scientist: string = null;

// the parentId array is used to track if an assignment is already initialized in the assignemnt list array
  parentIdList = [];

  //the assginment list array is used for storing all the assignments that a user has created on the review page
  assignmentList = [];

  analystIndex: number = 0;
  analystList = [];
  // userlist: User[];
  userlist;
  userNameList: string[];
  currentDate:Date;
  designList;
  tableList = [];
  mode:string;
  templateId:number;
  template;
  steps;
  dataType = 'design';
  displayedColumns: string[] = ['studyNum', 'wsNum', 'name', 'type', 'objective','author', 'status', 'id', 'star']
  dataSource;
  pageIndex: number = 0;
  pageSize: number = 10;
  sizeOption = [5, 10, 25, 50]
  // Default sorting state
  sortState = {
    active: "studyNum",
    direction: "asc"
  }

  buttonSetting =
    {
      name: "Create New Design",
      routerLink: "/ws_design",
      routerLinkActive: "mat-accent"
    }

    alertOptions={
      autoClose: true,
      keepAfterRouteChange: false
    }

  designListSub: Subscription;
  designDetailSub: Subscription;
  deleteSub:Subscription;
  userListSub: Subscription;

  constructor(private ws_design_service: WorksheetDesignService,
    public route: ActivatedRoute,
    private router: Router,
    private authenticationservice: AuthenticationService2,
    private dialog: MatDialog,
    private alertService: AlertService) { }



  ngOnInit(): void {

    this.getUserInfo();
    this.currentDate = new Date();
    this.route.paramMap.subscribe((paramMap: ParamMap) => {
      if (paramMap.has("templateId")) {
        this.mode = "review";
        this.templateId = +paramMap.get("templateId");

        this.designDetailSub = this.ws_design_service.getDesignById(this.templateId).subscribe(res => {
            console.log("response from get template details by ID")
            console.log(res);
            this.template = this.ws_design_service.dataFormatReceive(res, "design");
            console.log(`templte ${this.templateId}'s step data:`);
            this.steps = this.template.stepArray;
          console.log(this.steps);
          });


      } else {
        this.mode = "preview";
        this.getDesignList();
      }
    });

  }// End of ngOnInit

  setLoading(status){
    this.isLoading = status
  }

  refreshPage(){
    let options;
    options = this.alertOptions;
    options.id = 'refresh-info';
    this.getDesignList();
    this.alertService.info(`Refreshed worksheet design list, and retrieved ${this.designList.length} ${(this.designList.length > 0) ? 'results':'result'}`, options)
  }

  getDesignList(){
    this.setLoading(true)
    this.designListSub = this.ws_design_service.getDesignList().subscribe(res => {
      this.setLoading(false)
      console.log("the response message from getting summary");
          console.log(res);
          this.designList = res.summaries;
          console.log("Design list data: ");
          console.log(this.designList);
          this.initTableList();
    })
  }


  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.scientist = user.name;
        this.userId = user.userId;
        console.log("user info for worksheet review: ");
        console.log(user);

       this.privilege = this.ws_design_service.accessControl(user)
       if(!this.privilege.design){
         if(this.privilege.experiment){
          console.log('User does not have access to eWorksheet desgin, renavigate to experiment section')
          this.router.navigate(['/ws_experiment/analyst', this.userId])
         }else if(this.privilege.review){
          this.router.navigate(['/ws_experiment/scientist', this.userId])
         }else{
          this.router.navigate(['./main'])
         }

       }

      } )
    }
  }

  sortData(sort){
    this.sortState.active = sort.active;
    this.sortState.direction = sort.direction;

    const data = lodash.cloneDeep(this.dataSource);
    if (!sort.active || sort.direction === '') {
      return;
    }

    this.dataSource = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        // case 'position': return this.compare(a.position, b.position, isAsc);
        case 'wsNum': return this.compare(a.wsNum, b.wsNum, isAsc);
        // case 'exeNum': return this.compare(a.exeNum, b.exeNum, isAsc);
        case 'studyNum': return this.compare(a.studyNum, b.studyNum, isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'type': return this.compare(a.type, b.type, isAsc);
        case 'objective': return this.compare(a.objective, b.objective, isAsc);
        case 'author': return this.compare(a.author, b.author, isAsc);
        case 'status': return this.compareBoolean(a.status, b.status, isAsc);
        case 'id': return this.compare(a.id, b.id, isAsc);
        case 'star': return this.compareBoolean(a.star, b.star, isAsc);
        default: return 0;
      }
    });
    // console.log("sorted array is: ");
    // console.log(this.dataSource);
  }


  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  compareBoolean(a, b, isAsc){
    return (a === true ? 1 : -1) * (isAsc ? 1 : -1);
  }

  onPageChange(pageData: PageEvent) {
    this.pageIndex = pageData.pageIndex;
    this.pageSize = pageData.pageSize;
    console.log("Page change event:");
    console.log(pageData);
    this.splitPageList(this.pageIndex, this.pageSize)

  }

  splitPageList(pageIndex = 0, pageSize = 10){
    const startAt = pageIndex * pageSize;
    const endAt = startAt + pageSize;
    this.dataSource = this.tableList.slice(startAt, endAt);
    this.sortData(this.sortState);
  }

  initTableList(){
    const tempList = this.designList.map((design, index) => {
      return{
        // position: index + 1,
        wsNum: design.wsNum || null,
        // exeNum: design.exeNum || 0,
        studyNum: design.studyNum,
        name: design.templateName,
        type: design.exType,
        objective: design.exObjective,
        author: design.scientist,
        status: design.finalized === 1 ? true : false,
        star: design.templateName === 'design copy' ? true : false,
        id: design.templateId
      }
    })

    this.tableList = lodash.cloneDeep(tempList);
    this.splitPageList(this.pageIndex, this.pageSize)
    console.log("Table list: ");
    console.log(this.tableList);
  }

  makeCopy(parentId){
  let parentTemplate;
  this.ws_design_service.getDesignById(parentId).subscribe(res => {
      parentTemplate = this.ws_design_service.dataFormatReceive(res, "design");
      parentTemplate.wsNum = '(copy)';
      parentTemplate.exeNum = 0;
      parentTemplate.studyNum = '(copy)';
      parentTemplate.templateId = -1;
      parentTemplate.templateName = "design copy";
      parentTemplate.parentId = parentId;
      parentTemplate.userId = this.userId;
      parentTemplate.scientist = this.scientist;
      const copyDate = new Date();
      console.log("copy date:")
      console.log(new Date(copyDate));
      parentTemplate.creationDate = copyDate.getTime();
      parentTemplate.saveDate = copyDate.getTime();
      parentTemplate.finalizationDate = 0;
      parentTemplate.finalized = false;
      parentTemplate.version = -1;

      // Reset dbid for each step
      parentTemplate.stepArray.forEach(step => {

        // Reset each step's dbid to -1
        step.dbid = -1

        // Reset each sub material's dbid to -1, for each step
        if(step.stepData.subStepArray){
          if(step.stepData.subStepArray.length > 0){
            step.stepData.subStepArray.forEach(subMaterial => {
              subMaterial.stepData.dbid = -1

              // Reset each sub dilution's dbid to -1, for each sub material
              if(subMaterial.stepData.subStepArray.length > 0){
                subMaterial.stepData.subStepArray.forEach(subDilution => {
                  subDilution.stepData.dbid = -1
                })
              }
            })
          }
        }
      })

      console.log("the copied template config:");
    console.log(parentTemplate);

    // set dbid for all files to -1 for backend process
    if(parentTemplate.files.length > 0){
      parentTemplate.files.forEach(file => file.dbid = -1);
      console.log("file dbids are initialized with value -1 for the copied template design")
    }


    this.ws_design_service.addDesign(parentTemplate).subscribe(res => {
      this.ws_design_service.getDesignList().subscribe(res => {this.designList = res.summaries; this.initTableList();});
    });
  })

  }

  deleteDesign(templateId){
   this.deleteSub = this.ws_design_service.deleteDesignById(templateId).subscribe(res => {
      console.log("response message from template deletion");
    console.log(res);

    this.ws_design_service.getDesignList().subscribe(res => {this.designList = res.summaries; this.initTableList();});
    });
  }



addAnalyst(panelId){

    this.analystIndex++;
    this.assignmentList.forEach(assignment => {
      if(assignment.parentId === panelId){
        const insertIndex = assignment.analystList.length;
        assignment.analystList[insertIndex] = {
          analystId: null,
          analystIndex: this.analystIndex,
          listContent: this.userNameList,
          option: null
     };
      }
    })

}

updateAnalyst(event){
  this.assignmentList.forEach(assignment => {
    if(assignment.parentId === event.parentId){
      assignment.analystList.forEach(analyst => {
        if(analyst.analystIndex === event.index){
          analyst.option = event.option;
          const strArray = event.option.split(':');
          analyst.analystId = +strArray[0].trim();

          console.log(`analyst array for assignment ${event.parentId} is updated:`);
          console.log(assignment.analystList);
        }
      })
    }
  })
}

removeAnalyst(event){

  this.assignmentList.forEach(assignment => {
    if(assignment.parentId === event.parentId){
      const filterArray = assignment.analystList.filter(analyst => analyst.analystIndex !== event.index)
      assignment.analystList = [...filterArray];
      console.log(`Analyst is removed from assignment ${event.parentId}` );
      console.log( assignment.analystList)
    }
  })


}

createNewAssignment(panelId, templateId){
if(!this.parentIdList.includes(panelId)){
  // this.parentIdList.push(templateId);
  this.parentIdList.push(panelId);

  const newAssignment = {
    parentId: panelId,
    analystList:[],
    templateId: templateId
  }

  this.assignmentList[panelId] = newAssignment;

  // this.assignmentList.push(newAssignment);
  console.log("new assignment is created: ");
  console.log(this.assignmentList)
  // this.addAnalyst(templateId);
}
//initialize an analyst drop down box for newly created assignment
this.addAnalyst(panelId);
}


onSearchTable(event){
  const keyword = event.searchData;
  const filterList = this.tableList.filter(element => element.name.trim().toLowerCase().includes(keyword.trim().toLowerCase()));
  if(filterList.length > 0){
    this.tableList = lodash.cloneDeep(filterList);
    this.splitPageList(0, this.pageSize)
  }else{
    this.alertService.warn('No matching results', this.alertOptions)
  }

}

onCancelSearch(event){
  this.initTableList();
}

openAssignment(config){
  console.log("assignment config: ");
  console.log(config);
  const assignmentConfig = {
    templateId: config.id,
    studyNum: config.studyNum,
    templateName: config.name,
    exType: config.type,
    exObjective: config.objective,
    scientist: config.author
  }
  this.dialog.open(AssignmentDialogComponent, {data: assignmentConfig})
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

toHome(){
  this.ws_design_service.navToHome()
}

  ngOnDestroy() {
    if(this.designDetailSub){
      this.designDetailSub.unsubscribe();
    }
    if(this.designListSub){
      this.designListSub.unsubscribe();
    }
    if(this.deleteSub){
      this.deleteSub.unsubscribe();
    }
    if(this.userListSub){
      this.userListSub.unsubscribe();
    }


  }

}
