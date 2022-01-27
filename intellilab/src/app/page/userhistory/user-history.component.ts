import { Component, ElementRef, Input, OnInit, OnChanges, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { itemdetail } from '../../shared/objects/item';
import { OrderItemV2 } from '../../shared/objects2/OrderItemV2';
import { SettingService2, DateFormatType  } from '../../shared/services2/Setting2.service';
import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { UserHistoryService2 } from '../../shared/services2/UserHistory2.service';
import { UserHistoryV2 } from '../../shared/objects2/UserHistoryV2';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import * as lodash from "lodash";
import { ErrorService } from '../../page/error/error.service';



@Component({
  selector: 'app-user-history',
  providers: [AuthenticationService2,
              UserHistoryService2 ],

  templateUrl: './user-history.component.html',
  styleUrls: ['./user-history.component.scss']
})

export class UserHistoryComponent implements OnInit, OnChanges, AfterViewInit {



  initialSearch: boolean = false;
  displayedColumns: string[] = ['user', 'page', 'action', 'time', 'desc']
  dataSource = [];

  tryName = '';
  tryDays = 7;
  tryPage = 1;
  tryMessage;

  displayedUserHistories: UserHistoryV2[]= [];
  viewColumn: boolean[];
  columnList: string[];

  page: number;

  selectrequestorderitems: OrderItemV2[] = [];
  selectapprovedorderitems: OrderItemV2[] = [];
  receiveorderitem: OrderItemV2;
  requestapprove = false;
  requestmark = false;
  approverequestitemnames = '';
  markrequestitemnames = '';
  receiveitem: itemdetail;

  catalog: string;
  islockCat: boolean;
  currentuser: UserV2;
  receivedateoptions: IAngularMyDpOptions = { dateFormat: 'ddmmmyyyy' };
  receivedate: { date: { year: number, month: number, day: number } };
  tabs: string[] = ['1 Week', '1 Month', '6 Months'];
  currentTab = '1 Week';
  isLoading = false;
  periodCount: number;
  searchKey: string;

  newlot= false;
  showinuse= false;
  showunit= false;
  dateformat: { date: { year: number, month: number, day: number } };
  showorderitemslide: boolean;

  multiplerequestsmodel: any;
  orderitemmodal: any;
  receiveitemmodal: any;
  dateFormat: string;
  dateFormatDisplay: string;
  days: number;

  constructor(private settingService: SettingService2,
              private errorService: ErrorService,
              private authenticationservice: AuthenticationService2,
              private userHistoryService: UserHistoryService2) { }

  ngOnInit() {
    this.loadSettings();

    const getcurrentuser = this.authenticationservice.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);
      });
    }

    this.currentTab = '1 Week';
    this.page = 1;
    this.days = 7;
    this.searchKey = '';

    this.isLoading = true;
    this.loadPage();
    this.isLoading = false;

    this.viewColumn = [true, true, true, true, true];
    this.columnList = ['User', 'Page', 'Action', 'Time', 'Description'];


  }

  ngOnChanges() { }

  ngAfterViewInit() { }

  loadSettings() {
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
  }

  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
  }


  changeDays(days){
    this.days = days
  }


  dateStr(date){
    const newDate = new Date(date);

    // The month count starts with 0 (Jan), up to 11 (Dec).
    const month = newDate.getMonth() + 1;
    return newDate.getFullYear() + "/" + month + "/" + newDate.getDate();
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  compareBoolean(a, b, isAsc){
    return (a === true ? 1 : -1) * (isAsc ? 1 : -1);
  }

  sortData(sort){

    const data = lodash.cloneDeep(this.dataSource);
    if (!sort.active || sort.direction === '') {
      return;
    }

    this.dataSource = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'user': return this.compare(a.user, b.user, isAsc);
        case 'page': return this.compare(a.page, b.page, isAsc);
        case 'action': return this.compare(a.action, b.action, isAsc);
        case 'time': return this.compare(a.time, b.time, isAsc);
        default: return 0;
      }
    });
    // console.log("sorted array is: ");
    // console.log(this.dataSource);
  }
  loadPage() {
    this.isLoading = true;
    this.displayedUserHistories = [];

      this.userHistoryService.getAllUserHistories( this.days, this.page ).subscribe( userHistories => {
        if(userHistories.length === 0){
          console.log('Empty history list result')
          if(this.page > 1){
            this.page--
          }
          console.log(`Set page back to ${this.page}`)
        }else{
          this.dataSource = userHistories.map(history => {
            return {
              user: history.username,
              page: history.component,
              action: history.activity,
              time: this.dateStr(history.activityTime),
              desc: history.description
            }
          })
          console.log('Data Source: ')
          console.log(this.dataSource)
        }

      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });

    this.isLoading = false;
  }

  nextPage(){
    this.initialSearch = false
    this.page++
    if(this.searchKey.length === 0){
      this.loadPage()
    }else{
      this.searchByName()
    }

    console.log(`View History Page - ${this.page}`)
  }

  previousPage(){
    this.initialSearch = false
    if(this.page > 1){
      this.page--
      if(this.searchKey.length === 0){
        this.loadPage()
      }else{
        this.searchByName()
      }
      console.log(`View History Page - ${this.page}`)
    }

  }
  // loadPage() {
  //   this.isLoading = true;
  //   this.displayedUserHistories = [];
  //   if (this.searchKey.trim() == '') {
  //     this.userHistoryService.getAllUserHistories( this.days, this.page - 1 ).subscribe( userHistories => {
  //       this.displayedUserHistories = userHistories;
  //       this.dataSource = userHistories.map(history => {
  //         return {
  //           user: history.username,
  //           page: history.component,
  //           action: history.activity,
  //           time: this.dateStr(history.activityTime),
  //           desc: history.description
  //         }
  //       })
  //       console.log('Data Source: ')
  //       console.log(this.dataSource)
  //     }, error => {
  //       ErrorUtil.handleHttpError( error );
  //     });
  //   } else {
  //     this.userHistoryService.getSpecificUserHistory( this.searchKey, this.days, this.page - 1 ).subscribe( userHistories => {
  //       this.displayedUserHistories = userHistories.slice();
  //     }, error => {
  //       ErrorUtil.handleHttpError( error );
  //     });
  //   }
  //   this.isLoading = false;
  // }
  btnSearch(){
    this.page = 1
    this.initialSearch = true
    this.searchByName()
  }

  searchByName() {

    if ( this.searchKey.length > 0 ) {
        this.isLoading = true;
        this.userHistoryService.getSpecificUserHistory(this.searchKey.trim(), this.days, this.page).subscribe(userHistories => {
          if(userHistories.length > 0){
            this.dataSource = userHistories.map(history => {
              return {
                user: history.username,
                page: history.component,
                action: history.activity,
                time: this.dateStr(history.activityTime),
                desc: history.description
              }
            }, error => {
              ErrorUtil.handleHttpError( error );
              this.errorService.message$ = error.error;
          });
          }else{
            if(this.page > 1){
              this.page--
            }
            if(this.initialSearch){
              this.dataSource = []
            }
          }

        console.log(`Search user (${this.searchKey.trim()}) within ${this.days} for page ${this.page}`)
        console.log(this.dataSource)
        this.isLoading = false;
      })
    }else{
      this.loadPage()
    }
  }

  // onSearch(){
  //   this.userHistoryService.getAllUserHistories(this.tryDays, this.tryPage).subscribe(res => {
  //     this.tryMessage = res
  //     console.log(`User history(${this.tryDays}, ${this.tryPage}): `)
  //     console.log(this.tryMessage)
  //   })
  // }

  // onSearchByName(){
  //   this.userHistoryService.getSpecificUserHistory(this.tryName, this.tryDays, this.tryPage).subscribe(res => {
  //     this.tryMessage = res
  //     console.log(`User history(${this.tryName}, ${this.tryDays}, ${this.tryPage}): `)
  //     console.log(this.tryMessage)
  //   })
  // }
}
