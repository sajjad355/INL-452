import { Component, OnInit } from '@angular/core';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { Location } from '@angular/common';
import { UserV2 } from '../../shared/objects2/UserV2';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Params, Router } from '@angular/router';

@Component({
  selector: 'app-folder-custody',
  templateUrl: './folder-custody.component.html',
  styleUrls: ['./folder-custody.component.css']
})

export class FolderCustodyComponent implements OnInit {
  currentUser: any;
  name;
  roles;
  user: UserV2;
  constructor(
    private authenticationService: AuthenticationService2,
    private location: Location,
    private auth: AuthenticationService2,
    private router: Router,
    private titleService: Title
  ) {}

  goBack() {
    this.location.back();
  }

  ngOnInit() {
    // this.titleService.setTitle('Somru Folder Custodian Management');
    const getCurrentUser = this.authenticationService.getCurrentUser();
    if (getCurrentUser !== undefined) {
      getCurrentUser.then(_ => {
        this.currentUser = UserV2.copy(_);
        this.name = this.currentUser.name;
        this.roles = this.currentUser.userRoles;
      });
    }
    this.checkLoggedIn();
  }

  checkLoggedIn() {
    const c = this.auth.getCurrentUser();
    if ( c !== undefined ) {
      c.then( aUser => {
        this.user = UserV2.copy(aUser);
      });
    } else {
      this.router.navigate(['/login']).then(() => {});
    }
  }

  signout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
