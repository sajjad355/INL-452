import { Component, OnInit, Input, ViewChild, Output, ElementRef, EventEmitter } from '@angular/core';
import { FolderV2 } from '../../../shared/objects2/FolderV2';
import { Router } from '@angular/router';
import { FolderLocation2  } from '../../../shared/services2/FolderLocation2.service';
import { Folder2  } from '../../../shared/services2/Folder2.service';
import { FolderLocationV2 } from '../../../shared/objects2/FolderLocationV2';
import { RequestV2 } from '../../../shared/objects2/RequestV2';
import { OperationV2, UserRoleV2, UserV2 } from '../../../shared/objects2/UserV2';
import { SettingService2, DateFormatType  } from '../../../shared/services2/Setting2.service';
import { UserService2 } from '../../../shared/services2/User2.service';
import { image } from '../../../shared/objects/Image';
import { AuthenticationService2 } from '../../../shared/services2/Authenticate2.service';
// import { OPERATION_NAMES } from '../../../shared/models/Constants';
import { OPERATION_NAMES } from '../../../shared/models/Constants';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { TransferRequest2 } from 'app/shared/services2/TransferRequest2.service';
import { MatDialog } from '@angular/material/dialog';
import { FolderDialogComponent } from '../folder-dialog/folder-dialog/folder-dialog.component';
declare var jsPDF: any
declare var require: any
var JsBarcode = require('jsbarcode');
// require('jsbarcode');

@Component({
  selector: 'app-folder-details',
  templateUrl: './folder-details.component.html',
  styleUrls: ['../folder-custody.component.css', './folder-details.component.scss']
})
export class FolderDetailsComponent implements OnInit {
  // @ViewChild('scanBarcode', {static: false} ) scanBarcode: ElementRef;
  @ViewChild("scanBarcode", {static: false} ) scanBarcode;

  @Input() folder: FolderV2;
  @Input() canEdit: boolean;
  @Input() isNew: boolean;

  @Output() updateFolder = new EventEmitter();
  @Output() viewFolder = new EventEmitter();
  @Output() cancel = new EventEmitter();

  currentUser: UserV2;
  userRequest: RequestV2[] = []
  employeeList: UserV2[] = [];
  managerList: UserV2[] = [];
  edit: boolean = false;
  barcode: string = '';

  inputWarning = [];
  hideWarning = true;
  isRequestPending: boolean = false;
  isAckPending: boolean = false;
  isRequesting: boolean = false;
  title: string;
  userList: UserV2[] = [];
  isScanning: boolean = false;
  // canManageFolders: boolean = false;

  locations: FolderLocationV2[];
  transferRequest: RequestV2;

  numberOfFolder: number = 1;
  transferNote: string;

  constructor(
    private router: Router,
    private userService: UserService2,
    private folderLocationService: FolderLocation2,
    private folderService: Folder2,
    private settingService: SettingService2,
    private authenticationservice: AuthenticationService2,
    private TransferRequestService: TransferRequest2,
    private dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.getUserInfo()
    window.scrollTo(0, 0);
    this.title = this.folder.title;
    this.getUsers();
    this.getLocations();

    // console.log('Init folder object')
    // console.log(this.folder)
  }

  openDialog(message: string) {
    let dialogRef = this.dialog.open(FolderDialogComponent, {panelClass: 'custodian-dialog-customized', data: {message: message}});
    dialogRef.afterClosed().subscribe(result =>{});
  }

  getMyReqs(){
    this.TransferRequestService.loadRequests(this.currentUser?.userId).subscribe(requests => {
      this.userRequest = requests.filter(request => request.requestStatus === 'requested')
      // console.log(`Pending requests for: ${this.currentUser.name}`)
      // console.log(this.userRequest)

      this.userRequest.forEach(request => {
        if(request.folder.dbid === this.folder.dbid){
          this.isRequestPending = true
        }
      })
    })
  }

  getMyAcks(){
    this.TransferRequestService.getAllTransfers(this.currentUser.userId, 'accepted').subscribe(requests => {
      // console.log(`Pending acknowledgements for: ${this.currentUser.name}`)
      // console.log(requests)
      requests.forEach(request => {
        if(request.folder.dbid === this.folder.dbid){
          this.isAckPending = true
        }
      })
    })
  }

  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.currentUser = UserV2.copy(user)
        // console.log(`Current user for custodian system:`)
        // console.log(this.currentUser)
        this.getMyReqs()
        this.getMyAcks()
      } )
    }
  }

  ngAfterViewInit()	{
    if(this.folder.dbid) {
      let divDOM = document.getElementById("barcode");
      let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      svg.setAttribute('jsbarcode-format', 'code128')
      svg.setAttribute('jsbarcode-value', this.folder.barcode)
      svg.setAttribute('jsbarcode-width', '2')
      svg.setAttribute('jsbarcode-height', '80')
      svg.setAttribute('jsbarcode-fontsize', '20')
      svg.className.baseVal = "barcode";
      document.getElementById("barcode") && divDOM.appendChild(svg);
      JsBarcode(".barcode").init();
    }
  }

  showScanner() {
    this.isScanning = !this.isScanning;
    if(this.isScanning) this.scanBarcode.nativeElement.focus();
  }

  searchFolderLocation() {
    this.folderLocationService.searchFolderLocationBy(this.barcode, 'barcode').subscribe(result => {
      if(result.slice().length === 1) {
        this.folder.location = result[0];
        // this.barcode = result[0]['title'];
        this.isScanning = false;
      }
      else {
        this.folder.location = undefined;
        // alert('Folder location not found!')
        this.openDialog(`Cannot find location for folder (${this.folder.title})`)
      }
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  getLocations() {
    this.folderLocationService.loadLocationsByPage(0).subscribe(locations => {
      this.locations = locations;
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  setEmployee(userId) {
    let employee = this.employeeList.find(user => user.userId == userId);
    if(employee) this.folder.custodian = employee;
  }

  setProjectManager(userId) {
    let manager = this.managerList.find(user => user.userId == userId);
    if(manager) this.folder.projectManager = manager;
  }

  setLocation(locationId) {
    let location = this.locations.find(location => location.dbid == locationId);
    if(location) this.folder.location = location;
  }

  canRequest() {
    if(!this.isRequesting) {
      if(this.folder.custodian.userId !== this.currentUser?.userId) return true;
      else return false;
    }
    else return false;
  }

  // checkIsManager() {
  //   let c = this.authenticationservice.getcurrentUser();
  //   if(c !== undefined){
  //     c.then(_ => {
  //       this.currentUser = UserV2.copy(_);
  //       this.canManageFolders = false;
  //       if (this.currentUser && this.currentUser.userRoles) {
  //         this.canManageFolders = UserV2.isAllowedToPerform(this.currentUser, OPERATION_NAMES.MANAGE_FOLDERS);
  //       }
  //     });
  //   }
  // }

  hide() {
    this.cancel.emit();
  }

  promptEdit() {
    this.edit === true ? this.edit = false : this.edit = true;
  }

  getUsers() {
    this.userService.loadUsers().subscribe(users => {
      this.employeeList = users;
      users.map(user => {
        if(UserV2.isAllowedToPerform(user, OPERATION_NAMES.MANAGE_FOLDERS)) {
          this.managerList.push(user)
        }
      })
    }, error => {

    });
  }

  promptRequest() {
    this.isRequesting = true;
  }

  requestCustodianship() {
    let transferRequest = {
      note: this.transferNote ? this.transferNote : 'No Comment',
      folder: this.folder,
      // requestedBy: this.folder.custodian,
      // tobeCustodian: this.currentUser,
      receiver: this.folder.custodian,
      sender: this.currentUser,
      requestStatus: 'requested'
    }
    this.folderService.requestTransfer(transferRequest).then((response) => {
      if(response) {
        // alert('Custodianship has been requested.')
        this.openDialog(`Request of custodianship for folder (${this.folder.title}) has been sent to ${this.folder.custodian.name}`)
        this.hide();
      }
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
    });

  }

  generatePDF(folder: FolderV2) {
    window.print();
    window.open('', '_parent', '');
    window.close();
  }

  validateuserinput() {
    this.inputWarning = [];

    if ( !this.folder.title || this.folder.title.trim() == "" ) {
      this.inputWarning.push("Folder title can not be empty.");
    }

    if ( this.folder.numberOfFolder < 1 ) {
      this.inputWarning.push("Number of folder can not be less than 1");
    }

    // if ( !this.folder.studyNumber || this.folder.studyNumber.trim() == "" ) {
    //   this.inputWarning.push("Study number can not be empty.");
    // }

    if ( !this.folder.projectManager ) {
      this.inputWarning.push("Please choose project manager");
    }

    if ( !this.folder.location ) {
      this.inputWarning.push("Please choose folder location");
    }

    if (this.inputWarning.length > 0 ) {
      this.hideWarning = false;
      setTimeout( () => {
        this.hideWarning = true;
      }, 5000);
    }
    else {
      this.saveFolder();
    }
  }

  saveFolder() {
    let c = this.authenticationservice.getCurrentUser();
    if(c !== undefined) {
      if(this.edit) {
        this.folder.numberOfFolder = this.numberOfFolder;
        delete this.folder['creationTime'];
        delete this.folder['modificationTime'];
        this.folderService.saveFolder(this.folder).then((folders) => {
          this.edit = false;
          let newFolders = folders as FolderLocationV2[];
          if(newFolders.length === 1) {
            this.viewFolder.emit(newFolders[0]);
            setTimeout(() => this.renderBarcode(newFolders[0]), 100);
          }
          else this.downloadBarcodes(newFolders);
        }).catch(error => {
          ErrorUtil.handleHttpError(error);
        });
      }
      else {
        c.then(_ => {
          this.currentUser = UserV2.copy(_);
          this.folder.custodian = this.currentUser;
          this.folder.numberOfFolder = this.numberOfFolder;
          this.folderService.saveFolder(this.folder).then((folders) => {
            let newFolders = folders as FolderLocationV2[];
            if(newFolders.length === 1) {
              this.viewFolder.emit(newFolders[0]);
              setTimeout(() => this.renderBarcode(newFolders[0]), 100);
            }
            else this.downloadBarcodes(newFolders);
          }).catch(error => {
            ErrorUtil.handleHttpError(error);
          });
        });
      }
    }
    else {
      // alert('Something went wrong!')
      this.openDialog('Fail to create new folder')
    }
  }

  renderBarcode(folder) {
    let divDOM = document.getElementById("barcode");
    let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute('jsbarcode-format', 'code128')
    svg.setAttribute('jsbarcode-value', folder.barcode)
    svg.setAttribute('jsbarcode-width', '2')
    svg.setAttribute('jsbarcode-height', '80')
    svg.setAttribute('jsbarcode-fontsize', '20')
    svg.className.baseVal = "barcode";
    document.getElementById("barcode") && divDOM.appendChild(svg);
    JsBarcode(".barcode").init();
  }

  downloadBarcodes(folders) {
    let that = this;
    var disp_setting="toolbar=yes,location=no,";
    disp_setting+="directories=yes,menubar=yes,";
    disp_setting+="scrollbars=yes,width=650, height=600, left=100, top=25";

    var docprint = window.open("","",disp_setting);
    docprint.document.open();
    docprint.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"');
    docprint.document.write('"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">');
    docprint.document.write('<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">');
    docprint.document.write('<head><title>Print Location Barcode</title>');
    docprint.document.write('<style type="text/css">body{ margin: 0; } p { padding: 0; margin: 10px 0 10px 10px; font-size: 18px; font-family: sans-serif; }</style>');
    docprint.document.write('</head><body onLoad="self.print()">');

    for(let i = 0; i < folders.length; i++) {
      let aaa = document.createElement('div');
      aaa.innerHTML = '';
      aaa.id = "barcode"+i;

      var element = document.getElementById("barcode");
      element.appendChild(aaa);

      let divDOM = document.getElementById("barcode"+i);
      let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      svg.setAttribute('jsbarcode-format', 'code128')
      svg.setAttribute('jsbarcode-value', folders[i].barcode)
      svg.setAttribute('jsbarcode-width', '2')
      svg.setAttribute('jsbarcode-height', '80')
      svg.setAttribute('jsbarcode-fontsize', '20')
      svg.className.baseVal = "barcode";
      document.getElementById("barcode"+i) && divDOM.appendChild(svg);
      JsBarcode(".barcode").init();
    }

    setTimeout(() => {
      for(let i = 0; i < folders.length; i++) {
        var locationName = "<p>"+folders[i].title+"</p>";
        var barcode = document.getElementById("barcode"+i).innerHTML;
        docprint.document.write(locationName);
        docprint.document.write(barcode);
      }
      docprint.document.write('</body></html>');
      docprint.document.close();
      docprint.focus();
      that.hide()
    }, 1000);
  }

}
