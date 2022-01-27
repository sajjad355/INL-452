import { UserV2 } from '../objects2/UserV2';
import { UrlService } from 'app/shared/services/url.service';
import { SearchUtils } from '../util/SearchUtil';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService2 {
  url: string = UrlService.URL + `/userV2/`;


  constructor(private http: HttpClient) { }

  public loadUsers(): Observable<UserV2[]> {
    return this.http.get<UserV2[]>(this.url + 'all/');
  }

  public search(searchTerm: string): Observable<UserV2[]> {
    const st: string = SearchUtils.ReplaceSearchTerms(searchTerm);
    return this.http.get<UserV2[]>(this.url + 'search/' + st);
  }

  public saveUser(u: UserV2): Promise<Object> {
    if (u == undefined) { return; }
    return this.http
      .post(this.url + 'save/', JSON.stringify(u))
      .toPromise();
  }

  public resetUserPassword(u: UserV2): Promise<Object> {
    if (u == undefined) { return; }
    return this.http
      .post(this.url + 'resetPassword/', JSON.stringify(u))
      .toPromise();
  }

  public get(id: number): Observable<UserV2> {
    return this.http.get<UserV2>(this.url + `get/${id}`);
  }




}
