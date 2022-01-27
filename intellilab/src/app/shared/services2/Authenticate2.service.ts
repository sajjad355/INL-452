import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserV2 } from '../objects2/UserV2';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthenticationService2 {
  url: string = UrlService.URL + `/loginV2/`;
  user: UserV2;

  constructor(private http: HttpClient) {
    // localStorage.setItem('stayLoggedIn', 'n');
  }

  // Checks to see if we can access user's id
  private hasId(): boolean {
    return (
      sessionStorage.getItem('user.userId') !== null
    );
  }

  

  public requestResetPassword(email: string) {
    return this.http.get<UserV2>(this.url + `requestResetPassword?email=${email}`  );
  }

  public verifyResetPassToken(token: string): Observable<Object> {
    return this.http.get<Object>(this.url + `verifyToken/${token}/`);
  }

  getCurrentUser() {
    return new Promise<UserV2>((resolve, reject) => {
      if (sessionStorage.getItem('user') == null) {
        this.logout();
        reject();
      } else {
        resolve(JSON.parse(sessionStorage.getItem('user')));
      }
    });
  }

  isLogin(): boolean {
    return this.hasId();
  }

  login(user: UserV2) {
    let credentials = {
      username: user.email,
      password: user.password
    }
    if (user == undefined) { return; }

    return new Promise((resolve, reject) => {
      this.http
        .post<UserV2>(this.url + 'authenticate', JSON.stringify(credentials))
        .toPromise()
        .then((loggedInUser) => {
          if (loggedInUser != null) {
            sessionStorage.setItem('user.userId', loggedInUser.userId.toString());
            sessionStorage.setItem('jwt', loggedInUser['jwt'].toString());
            delete loggedInUser['jwt'];
            sessionStorage.setItem('user', JSON.stringify(loggedInUser));
            resolve('login');
          } else {
            reject('Invalid login attempt');
            localStorage.clear();
            sessionStorage.clear();
          }
        })
        .catch(() => {
          console.log( 'Error occured on login attempt');
          reject('Invalid login attempt');
          localStorage.clear();
          sessionStorage.clear();
        });
    });
  }

  // meant to be used by esignature component - check that returned result is not undefined
  verifyCredential(email : string, password: string){ 
    let credentials = {
      username: email,
      password: password
    }
    return this.http.post<UserV2>(this.url + "authenticate", JSON.stringify(credentials));
  }

  update(user: UserV2): Promise<any> {
    if (user == undefined) { return; }
    return this.http
      .put(this.url + 'update/' + user.userId + '/', JSON.stringify(user))
      .toPromise().then(() => {
        if ( parseInt(sessionStorage.getItem('user.userId'), 10) == user.userId ) {
          sessionStorage.setItem('user', JSON.stringify(user));
        }
      });
  }


  updatePassword(token: string, userId: number, password: string): Promise<any> {
    return this.http.put<UserV2>(this.url + `updatePassword/${userId}/${password}`, null)
      .toPromise().then(user => user);
  }

  updateInvalidLoginCount(token: string, userId: number, count: number): Promise<any> {
    return this.http
      .put(this.url + `updateInvalidLoginCount/${userId}/${count}`, null)
      .toPromise().then(() => {
        if ( parseInt(sessionStorage.getItem('user.userId'), 10) == userId ) {
          sessionStorage.setItem('user',
            JSON.stringify({ ...JSON.parse(sessionStorage.getItem('user')), invalidlogincount: count } as UserV2)
          );
        }
      });
  }

  updateStatus(token: string, userId: number, status: boolean): Promise<any> {
    return this.http
      .put(this.url + `updateStatus/${userId}/${status}`, null)
      .toPromise().then(() => {
        if ( parseInt(sessionStorage.getItem('user.userId'), 10) == userId ) {
          sessionStorage.setItem('user',
            JSON.stringify({ ...JSON.parse(sessionStorage.getItem('user')), active: status } as UserV2)
          );
        }
      });
  }

  logout() {
    this.user = undefined;
    localStorage.clear();
    sessionStorage.clear();
  }
}
