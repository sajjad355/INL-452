import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SopV2 } from "app/shared/objects2/SopV2";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class SopService2 {
  url: string = UrlService.URL + `/sopv2/`;

  constructor(private http: HttpClient) { }

  public getCount(): Observable<number> {
    return this.http.get<number>(this.url + "count/");
  }

  public loadSopsInStatus(status: string): Observable<SopV2[]> {
    return this.http.get<SopV2[]>(this.url + 'allWithStatus/' + status);
  }

  public loadSopByPage(page: number): Observable<SopV2[]> {
    return this.http.get<SopV2[]>(this.url + 'all/' + page);
  }

  public searchSopPageable(searchKey: string, page: number, active: boolean): Observable<SopV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<SopV2[]>(this.url + 'search/' + st + '/' + active + '/' + page);
  }

  public searchSop(searchKey: string, active: boolean): Observable<SopV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<SopV2[]>(this.url + 'search/' + st + '/' + active);
  }

  public searchSopCount(searchKey: string, active: boolean): Observable<number> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<number>(this.url + 'searchCount/' + st + '/' + active);
  }

  public save(sop: SopV2): Promise<Object> {
    if (!sop) return;
    
    return this.http
      .post(this.url + "save/", JSON.stringify(sop))
      .toPromise();
  }

  public get(id: number): Observable<SopV2> {
    return this.http.get<SopV2>(this.url + `get/${id}`);
  }

}
