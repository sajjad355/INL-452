import { Injectable } from '@angular/core';
import { UrlService } from '../services/url.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRoleV2 } from '../objects2/UserV2';

@Injectable({
  providedIn: 'root',
})
export class UserRoleService2 {
  url: string = UrlService.URL + `/userRoleV2/`;

  constructor(private http: HttpClient) { }

  public loadUserRoles(): Observable<UserRoleV2[]> {
    return this.http.get<UserRoleV2[]>(this.url + 'all/');
  }

  public updateUserRole(userRole: UserRoleV2): Promise<Object> {
    if (userRole == undefined) { return; }

    return this.http
      .put(this.url + 'update/' + userRole.userRoleId + '/',
        JSON.stringify(userRole)
      )
      .toPromise();
  }
}
