import { Component, ElementRef, OnChanges, OnInit, ViewChild  } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OperationV2, UserRoleV2, UserV2 } from '../../shared/objects2/UserV2';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { UserService2 } from '../../shared/services2/User2.service';
import { UserRoleService2 } from '../../shared/services2/UserRole2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { OperationService2 } from '../../shared/services2/Operation2.service';
import * as lodash from "lodash";
import { ErrorService } from '../../page/error/error.service';

@Component({
  selector: 'app-manage-users',
  providers: [ NgbModal],
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.scss']
})

export class ManageUsersComponent implements OnInit, OnChanges {

  displayedColumns: string[] = ['name', 'roles', 'email', 'invalidLogin', 'status']
  dataSource = [];
  totalPageNum: number = 0;
  currentPageNum: number = 0;
  searchResults = [];
  currentUserRoles: UserRoleV2[] = [];
  updatedUserRoles: UserRoleV2[] = [];
  roleNames: string[] = [];
  operationNames = [];
  noUserManagementPermission: boolean;
  permissionsSaved = false;
  isLoading: boolean;
  selectedUser: UserV2;
  shouldDisplayUser: boolean;
  isNewUser: boolean;
  editingEnabled: boolean;
  message: string;


  toConfirm: string;
  confirmationMessage: string;
  currentUser: UserV2;

  canManageUsers: boolean;


  constructor(
    private errorService: ErrorService,
    private modalService: NgbModal,
    private userService: UserService2,
    private userRoleService: UserRoleService2,
    private operationService: OperationService2,
    private authenticationService: AuthenticationService2
  ) {
  }

  ngOnInit() {
    this.authenticationService.getCurrentUser().then(user => {
        this.currentUser = UserV2.copy(user);

        this.canManageUsers = false;
        if (this.currentUser && this.currentUser.userRoles) {
          this.canManageUsers = UserV2.isAllowedToPerform(this.currentUser, OPERATION_NAMES.MANAGE_USERS);
        }
    });


    this.loadPage();
    this.userRoleService.loadUserRoles().subscribe(userRoles => {
      this.currentUserRoles = userRoles;
      this.currentUserRoles.forEach(userRole => this.roleNames.push(userRole.roleName));
    }, error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });
    this.operationService.loadOperations().subscribe(operations => {
      operations.forEach(operation => this.operationNames.push({ id: operation.operationId, name: operation.operationName}));
      console.log("operationNames array data: ")
      console.log(this.operationNames)
    }, error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });
  }

  ngOnChanges() { }

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
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'roles': return this.compare(a.roles[0], b.roles[0], isAsc);
        case 'email': return this.compare(a.email, b.email, isAsc);
        case 'invalidLogin': return this.compare(a.invalidLogin, b.invalidLogin, isAsc);
        case 'status': return this.compare(a.status, b.status, isAsc);
        default: return 0;
      }
    });

  }

  loadPage() {
    this.reset();
    this.isLoading = true;
    this.userService.loadUsers().subscribe(users => {
      if(users.length <= 10){
        this.totalPageNum = 1
      }else{
        const remainder = users.length % 10
        this.totalPageNum = ((users.length - remainder) / 10) + 1
      }

      this.searchResults = users.map(user => {
        let ob = {
          name: user.name,
          roles: [],
          email: user.email,
          invalidLogin: user.invalidlogincount,
          status: user.active,
          userId: user.userId
        }
        if(user.userRoles.length > 0){
          user.userRoles.forEach(role => {
            ob.roles.push(role.roleName)
          })
        }

        return ob
      });
      console.log('Formatted User list: ')
      console.log(this.searchResults)
      this.splitPage(0)
    }, error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });
    this.isLoading = false;
  }

  splitPage(pageNum){
    const startAt = pageNum * 10;
    const endAt = startAt + 10;
    this.dataSource = this.searchResults.slice(startAt, endAt)
  }

  previousPage(){
    if(this.currentPageNum > 0){
      this.currentPageNum--
      this.splitPage(this.currentPageNum)
      console.log(`Move left to page ${this.currentPageNum}`)
    }else{
      console.log('new page is out of range')
    }
  }

  nextPage(){
    if(this.currentPageNum < (this.totalPageNum - 1)){
      this.currentPageNum++
      this.splitPage(this.currentPageNum)
      console.log(`Move right to page ${this.currentPageNum}`)
    }else{
      console.log('new page is out of range')
    }
  }

  search(searchTerm: string) {
    if (searchTerm == '') {
      this.loadPage();
      return;
    } // Load all users if search term is blank
    this.isLoading = true;
    this.userService.search(searchTerm).subscribe(users => {
      // this.searchResults = users;
      if(users.length <= 10){
        this.totalPageNum = 1
      }else{
        const remainder = users.length % 10
        this.totalPageNum = ((users.length - remainder) / 10) + 1
      }


      this.searchResults = users.map(user => {
        let ob = {
          name: user.name,
          roles: [],
          email: user.email,
          invalidLogin: user.invalidlogincount,
          status: user.active,
          userId: user.userId
        }
        if(user.userRoles.length > 0){
          user.userRoles.forEach(role => {
            ob.roles.push(role.roleName)
          })
        }

        return ob
      });
      console.log('Searched User list: ')
      console.log(this.searchResults)
      this.splitPage(0)
      this.isLoading = false;
    }, error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });

  }

  addNewUser() {
    this.selectedUser = new UserV2();
    this.selectedUser.active = true;
    this.editingEnabled = true;
    this.shouldDisplayUser = true;
    this.isNewUser = true;
  }

  reset() {
    this.editingEnabled = false;
    this.shouldDisplayUser = false;
    this.selectedUser = undefined;
    this.isNewUser = false;
  }

  saveUser(aUser: UserV2) {
    aUser.editedBy = this.currentUser.name;
    aUser.modifiedOn = new Date();
    this.userService.saveUser(aUser).then(() => {
      this.loadPage();
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });
  }

  resetPassword( aUser : UserV2 ) {
    aUser.editedBy = this.currentUser.name;
    aUser.modifiedOn = new Date();
    this.userService.resetUserPassword(aUser).then(() => {
      this.reset();
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });

  }

  loadUser( userId : number ) {
    this.isLoading = true;
    this.userService.get( userId ).subscribe( user => {
      this.selectedUser = UserV2.copy( user );
      this.shouldDisplayUser = true;
      this.isLoading = false;
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  openPermissionsModal(modal) {
    this.modalService.open
    (modal, {
      windowClass: 'permission-modal',
      scrollable: true
    }).result.then(() => {});
  }

  savePermissions() {
    const rolesArray = this.updatedUserRoles.filter((role, index, self) =>
      index === self.findIndex((t) => (
        t.userRoleId === role.userRoleId && t.roleName === role.roleName
      ))
    )
    if(rolesArray.length > 0) {
      rolesArray.forEach( role => this.userRoleService.updateUserRole(role).then(() => {}) );
      // this.currentUserRoles = rolesArray.map ( role => UserRoleV2.copy(role) );
      this.modalService.dismissAll();
      this.permissionsSaved = true;
    }
  }

  hasOperation(roleName: string, operationName: string) {
    const selectedUserRole = this.currentUserRoles[this.currentUserRoles.findIndex(role => role.roleName == roleName)];
    return selectedUserRole.operations.some(operation => operation.operationName == operationName);
  }

  updatePrivilege(roleName: string, operationName: string, operationId: number) {
    const updatedUserRole = this.currentUserRoles.find(role => role.roleName == roleName);
    if (updatedUserRole != undefined) {
      const updatedOperationIndex = updatedUserRole.operations.findIndex(operation => operation.operationName == operationName);
      if (updatedOperationIndex != -1) {
        updatedUserRole.operations.splice(updatedOperationIndex, 1);
      } else {
        updatedUserRole.operations.push(new OperationV2({ operationId: operationId, operationName: operationName }));
      }
      this.updatedUserRoles.push(updatedUserRole);
    }
  }

  closePermissionsModal() {
    this.noUserManagementPermission = false;
    this.updatedUserRoles = [];
  }


}
