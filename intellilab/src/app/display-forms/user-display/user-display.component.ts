import { Component, Input, Output, EventEmitter, OnInit, ViewChild, ElementRef, OnChanges, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserRoleV2, UserV2 } from '../../shared/objects2/UserV2';
import { UserRoleService2 } from '../../shared/services2/UserRole2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';

@Component({
  selector: 'app-user-display',
  templateUrl: './user-display.component.html',
  styleUrls: ['./user-display.component.css']
})
export class UserDisplayComponent implements OnInit, OnChanges {
  @Input() user: UserV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;
  @Output() updateUser = new EventEmitter<UserV2>();
  @Output() resetPassword = new EventEmitter<UserV2>();  
  @Output() cancel = new EventEmitter();


  inputWarning: string[] = [];
  roles : UserRoleV2[] = [];
  passwordResetValue : string = '';
  
  @ViewChild('resetPassword') resetPasswordElement : ElementRef;
  resetPasswordModal: any;

  constructor(private userRoleService: UserRoleService2,
    private errorService: ErrorService,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.userRoleService.loadUserRoles().subscribe(userRoles => {
      this.roles = userRoles;
    }, error => {
      ErrorUtil.handleHttpError(error);
      this.errorService.message$ = error.error;
    });
  }

  ngOnChanges(change: SimpleChanges) {
    if (change.user && this.user !== undefined) {
      this.reset();
    }
  }

  reset() {
    this.passwordResetValue = '';
    this.inputWarning = [];
  }

  validateInput() {
    if (this.user.name == undefined || !this.user.name || this.user.name.trim() == '') { 
      this.inputWarning.push('User name is required.'); 
    }

    if (this.user.email == undefined || !this.user.email || this.user.email.trim() == '') { 
      this.inputWarning.push('User email is required.'); 
    }

    if (!this.user.userRoles || this.user.userRoles.length == 0) { 
      this.inputWarning.push('Assign the user to at least one role.'); 
    }

    if (this.inputWarning.length != 0 ) {
      setTimeout(() => {
        this.inputWarning = [];
      }, 5000);
    } else {
      this.save();
    }
  }

  cancelUserUpdate() {
    this.cancel.emit();
  }

  save() {
    this.enableEdit = false;
    this.updateUser.emit( this.user );
  }

  addRole( role : UserRoleV2 ) {
    if ( !UserV2.hasRole( this.user, role.roleName ) ) {
      this.user.userRoles.push( role );
    }
  }

  removeRole( role : UserRoleV2 ) {
    const index = this.user.userRoles.findIndex( userRole => userRole.roleName == role.roleName );
    if ( index != -1 ) {
      this.user.userRoles.splice(index, 1);
    }
  }

  doesUserHaveRole( role : UserRoleV2 ) {
    return UserV2.hasRole( this.user, role.roleName );
  }

  requestResetPassword() {
    this.resetPasswordModal = this.modalService.open(this.resetPasswordElement, { backdrop: 'static', windowClass: 'reset-modal' });
  }

  performResetPassword() {
    this.user.password = this.passwordResetValue;
    this.resetPassword.emit(this.user); 
  }
}
