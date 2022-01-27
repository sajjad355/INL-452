import { Component, OnInit } from '@angular/core';
import { FolderV2 } from '../../../shared/objects2/FolderV2';
import { OperationV2, UserRoleV2, UserV2 } from '../../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../../shared/services2/Authenticate2.service';
import { OPERATION_NAMES } from '../../../shared/models/Constants';
import { Folder2  } from '../../../shared/services2/Folder2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserService2 } from '../../../shared/services2/User2.service';

@Component({
  selector: 'app-folder-list',
  templateUrl: './folder-list.component.html',
  styleUrls: ['../folder-custody.component.css']
})
export class FolderListComponent implements OnInit {

  constructor(
    private folderService: Folder2,
    private userService: UserService2,
    private authenticationservice: AuthenticationService2
  ) { }

  search = { searchValue: "", active : true, param: "title" };
  searchBy: string = 'Folder Title';
  allCount: number = 0;
  page: number = 1;
  showDetails: boolean = false;
  isNewFolder: boolean = false;
  showFolder: FolderV2;
  currentuser: UserV2;
  canManageFolders: boolean = false;
  canEditFolder: boolean = false;
  employeeList: UserV2[] = [];

  folders: FolderV2[] = []

  ngOnInit() {
    window.scrollTo(0, 0);
    this.checkIsManager();
    this.loadPage();
    this.countFolder();
    this.getUsers();
  }

  checkIsManager() {
    let c = this.authenticationservice.getCurrentUser();
    if(c !== undefined) {
      c.then(_ => {
        this.currentuser = UserV2.copy(_);
        this.canManageFolders = false;
        this.canEditFolder = false;
        if (this.currentuser && this.currentuser.userRoles) {
          this.canEditFolder = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.MANAGE_FOLDERS);
          this.canManageFolders = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.MANAGE_FOLDERS);
        }
      });
    }
  }

  loadPage() {
    this.folderService.loadFoldersByPage(this.page - 1).subscribe(folders => {
      this.folders = folders;
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  countFolder() {
    this.folderService.getCount().subscribe(res => {
      this.allCount = res;
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  getUsers() {
    this.userService.loadUsers().subscribe(users => {
      this.employeeList = users;
    }, error => {
      
    });
  }

  searchFolder() {
    if (this.search.searchValue == undefined || this.search.searchValue == '') {
      this.page = 1;
      this.loadPage();
      this.countFolder();
    }
    else {
      this.folderService.searchFolderBy(this.search.searchValue, this.search.param).subscribe(result => {
        if(this.search.param === 'barcode' && result.slice().length === 1) {
          this.viewDetails(result[0]);
        }
        else {
          this.folders = result.slice();
          this.allCount = this.folders.length;  
        }
      }, error => {
        ErrorUtil.handleHttpError( error );
      });
    }
  }
  
  viewDetails(folder) {
    this.showDetails = true;
    this.isNewFolder = false;
    this.showFolder = folder;
  }

  saveFolder() {

  }

  reset() {
    this.loadPage();
    this.countFolder();
    this.showDetails = false;
  }

  newFolder() {
    this.showFolder = new FolderV2;
    this.isNewFolder = true;
    this.showDetails = true;
  }

}
