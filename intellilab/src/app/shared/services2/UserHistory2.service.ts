import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserHistoryV2 } from '../objects2/UserHistoryV2';
import { UrlService } from '../services/url.service';

@Injectable({
  providedIn: 'root',
})
export class UserHistoryService2 {
  url: string = UrlService.URL + `/userhistoryv2/`;

  constructor(private http: HttpClient) { }


  public getAllUserHistories(days: number, page: number): Observable<UserHistoryV2[]> {
    return this.http.get<UserHistoryV2[]>(this.url + `all/${days}/${page}`);
  }

  public getSpecificUserHistory(user: string, days: number, page: number): Observable<UserHistoryV2[]> {
    return this.http.get<UserHistoryV2[]>(this.url + `${user}/${days}/${page}`);
  }
}
