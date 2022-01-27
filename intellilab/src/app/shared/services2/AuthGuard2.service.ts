import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthenticationService2} from './Authenticate2.service';

@Injectable()
export class AuthGuardService2 implements CanActivate {
  constructor(private auth: AuthenticationService2, private router: Router) { };

  canActivate() {
    if (this.auth.isLogin()) {
      return true;
    } else {
      this.router.navigate(['/login']).then(() => { });
      return false;
    }
  }
}
