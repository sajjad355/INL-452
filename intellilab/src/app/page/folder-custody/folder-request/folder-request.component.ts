import { Component, OnInit } from '@angular/core';
import { RequestV2 } from '../../../shared/objects2/RequestV2';
import { UserV2 } from '../../../shared/objects2/UserV2';
import { TransferRequest2  } from '../../../shared/services2/TransferRequest2.service';
import { FolderLocationV2 } from '../../../shared/objects2/FolderLocationV2';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { AuthenticationService2 } from '../../../shared/services2/Authenticate2.service';
import * as lodash from "lodash";
import { MatDialog } from '@angular/material/dialog';
import { FolderDialogComponent } from '../folder-dialog/folder-dialog/folder-dialog.component';

@Component({
  selector: 'app-folder-request',
  templateUrl: './folder-request.component.html',
  styleUrls: ['../folder-custody.component.css', './folder-request.component.scss']
})
export class FolderRequestComponent implements OnInit {

  constructor(
    private TransferRequestService: TransferRequest2,
    private authenticationservice: AuthenticationService2,
    private dialog: MatDialog
  ) { }

  currentUser: UserV2;
  isLoading: boolean = false;
  search = { searchValue: "", active : true };
  // page: number = 1;
  // allCount: number = 4;
  // transferRequests: number = 0;
  // pendingAcknowledgements: number = 0;

  // requests: RequestV2[];
  // acknowledgements: RequestV2[];

  pendingRequests: RequestV2[] = [];
  acceptedRequests: RequestV2[] = []
  pageOfReq: number = 1
  pageOfAck: number = 1
  countOfReq: number = 4
  countOfAck: number = 4
  isReqLoading: boolean = false;
  isAckLoading: boolean = false;

  ngOnInit() {
    window.scrollTo(0, 0);
    this.getUserInfo()


  }

  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.currentUser = UserV2.copy(user)
        // console.log(`Current user for custodian system:`)
        // console.log(this.currentUser)
        this.getPendingRequest()
        this.getAccptedRequest()
      } )
    }
  }

  getPendingRequest(){
    this.isReqLoading = true;
    this.TransferRequestService.loadRequestByConditions(this.currentUser?.userId, 'requested', this.pageOfReq - 1).subscribe(requests => {
      // console.log(`Pending requests for (${this.currentUser?.name} | requested | ${this.pageOfReq})`)
      // console.log(requests)
      this.pendingRequests = requests
      this.countOfReq = requests.length
      this.isReqLoading = false
    })
  }

  getAccptedRequest(){
    this.isAckLoading = true
    this.TransferRequestService.loadRequestByConditions(this.currentUser?.userId, 'accepted', this.pageOfAck - 1).subscribe(requests => {
      // console.log(`Accepted requests for (${this.currentUser?.name} | accepted | ${this.pageOfAck})`)
      // console.log(requests)
      this.acceptedRequests = requests
      this.countOfAck = requests.length
      this.isAckLoading = false
    })
  }

  pageSwitch(nav: string, target: string){

    if(target === 'accepted'){
      if(nav === 'next' && this.countOfAck >= 10){
        this.pageOfAck = this.pageOfAck + 1
        this.getAccptedRequest()
      }

      if(nav === 'pre' && this.pageOfAck > 1){
        this.pageOfAck = this.pageOfAck - 1
        this.getAccptedRequest()
      }
      // console.log(`pageOfAck: ${this.pageOfAck}`)
    }else{

      if(nav === 'next' && this.countOfReq >= 10){
        this.pageOfReq = this.pageOfReq + 1
        this.getPendingRequest()
      }

      if(nav === 'pre' && this.pageOfReq > 1){
        this.pageOfReq = this.pageOfReq - 1
        this.getPendingRequest()
      }

    }
  }

  updateStatus(request, status) {
    let req = request;
    delete req.lastModified;
    delete req.requestInitiationTime;
    req.requestStatus = status;
    this.TransferRequestService.requestTransfer(req).then((response) => {
      if(status === 'accepted') {
        // alert('Folder transfer request has been accepted.');
        this.openDialog(`Folder transfer request from (${request.sender.name}) is accepted`)
      }
      else if(status === 'rejected') {
        // alert('Folder transfer request has been rejected.');
        this.openDialog(`Folder transfer request from (${request.sender.name}) is rejected`)
      }
      else if(status === 'acknowledged'){
        // alert('The custodian of the folder has been changed successfully.');
        this.openDialog(`Folder (${request.folder.title})'s custodianship is transfered to ${request.sender.name}`)
      }
      else {
        // alert('Folder transfer request has been '+status);
        this.openDialog(`Update folder (${request.folder.title})'s status with: ${status}`)
      }
      this.getPendingRequest()
      this.getAccptedRequest()
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
    });
  }

  openDialog(message: string) {
    let dialogRef = this.dialog.open(FolderDialogComponent, {panelClass: 'custodian-dialog-customized', data: {message: message}});
    dialogRef.afterClosed().subscribe(result =>{});
  }
  // isPending(requests, id) {
  //   if(!requests) return true;
  //   else {
  //     let numR = requests.filter(req => {
  //       req.requestStatus == 'requested' && req.requestedBy.userId == id
  //     })
  //     if(numR.length > 0) return false;
  //     else return true;
  //   }
  // }

  // isNotAck(requests, id) {
  //   if(!requests) return true;
  //   else {
  //     let numR = requests.filter(req => {
  //       req.requestStatus == 'accepted' && req.tobeCustodian.userId == id
  //     })
  //     if(numR.length > 0) return false;
  //     else return true;
  //   }
  // }

}
