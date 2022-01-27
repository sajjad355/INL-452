import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { KitV2 } from 'app/shared/objects2/KitV2';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';
import { SearchUtils } from '../util/SearchUtil';
@Injectable({
  providedIn: 'root',
})
export class KitService2 {
  url: string = UrlService.URL + `/kitv2/`;

  constructor(private http: HttpClient) {
  }

  public count(): Observable<number> {
    return this.http.get<number>(this.url + 'count/');
  }

  public loadKitByPage(page: number): Observable<KitV2[]> {
    return this.http.get<KitV2[]>(this.url + 'all/' + page);
  }

  public searchPageable(searchKey: string, active: boolean, page: number): Observable<KitV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<KitV2[]>(this.url + `search/${st}/${active}/${page}`);
  }

  public searchCount(searchKey: string, active: boolean): Observable<number> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<number>(this.url + 'searchCount/' + st + '/' + active);
  }

  public search(searchKey: string): Observable<KitV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<KitV2[]>(this.url + `search/${st}`);
  }

  public getKitsToReview(): Observable<KitV2[]> {
    return this.http.get<KitV2[]>(this.url + `allWithEditStatus/Entered`);
  }


  public save(kit: KitV2): Promise<Object> {
    if (!kit) return;
    
    return this.http
      .post(this.url + "save/", JSON.stringify(kit))
      .toPromise();
  }

  public get(id: number): Observable<KitV2> {
    return this.http.get<KitV2>(this.url + `get/${id}`);
  }

}
