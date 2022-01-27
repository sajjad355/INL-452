import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SalesItemV2 } from "app/shared/objects2/SalesItemV2";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { SearchUtils } from "../util/SearchUtil";

@Injectable({
  providedIn: "root",
})
export class SalesItemService2 {
  url: string = UrlService.URL + `/salesitemv2/`;

  constructor(private http: HttpClient) { }

  public search(searchKey: string): Observable<SalesItemV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<SalesItemV2[]>(this.url + "search/" + st);
  }


  public countCat(catalogNum: string): Observable<any>{
    return this.http.get<SalesItemV2[]>(this.url + `countByCatalogNumber/${catalogNum}`);
  }
}
