import { Component, OnInit } from '@angular/core';
import { RequestV2 } from '../../../shared/objects2/RequestV2';
import { UserV2 } from '../../../shared/objects2/UserV2';
import { TransferRequest2  } from '../../../shared/services2/TransferRequest2.service';
import { FolderLocationV2 } from '../../../shared/objects2/FolderLocationV2';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { AuthenticationService2 } from '../../../shared/services2/Authenticate2.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-folder-dashboard',
  templateUrl: './folder-dashboard.component.html',
  styleUrls: ['./folder-dashboard.component.css']
})
export class FolderDashboardComponent implements OnInit {

  constructor(
    private TransferRequestService: TransferRequest2,
    private router: Router,
    private authenticationservice: AuthenticationService2
  ) { }

  currentUser: UserV2;
  requests: RequestV2[];
  transferRequests: number = 0;
  pendingAcknowledgements: number = 0;
  acknowledgements: RequestV2[];

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
        this.countAck()
        this.countReq()
      } )
    }
  }

  countReq(){
    this.TransferRequestService.getAllTransfers(this.currentUser.userId, 'requested').subscribe(requests => {
      this.transferRequests = requests.length
      // console.log(`All my received requests (${this.transferRequests})`)
      // console.log(requests)
    })
  }

  countAck(){
    this.TransferRequestService.getAllTransfers(this.currentUser.userId, 'accepted').subscribe(requests => {
      this.pendingAcknowledgements = requests.length
      // console.log(`All my pending acknowledgements (${this.pendingAcknowledgements})`)
      // console.log(requests)
    })
  }

  goTo(screen) {
    this.router.navigate([screen]);
  }

}
