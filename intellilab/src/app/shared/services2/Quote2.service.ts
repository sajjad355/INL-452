import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { QuoteV2 } from "app/shared/objects2/QuoteV2";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class QuoteService2 {
  url: string = UrlService.URL + `/quotev2/`;

  constructor(private http: HttpClient) { }

  public getCount(active: boolean): Observable<number> {
    return this.http.get<number>(this.url + "count/" + active);
  }

  public loadQuotesByPage(page: number, active: boolean): Observable<QuoteV2[]> {
    return this.http.get<QuoteV2[]>(this.url + 'all/' + page + '/' + active);
  }

  public loadExpiredAndExpiring(expired: number, expiring: number): Observable<QuoteV2[]> {
    return this.http.get<QuoteV2[]>(this.url + 'searchExpiredAndExpiring/' + expired + '/' + expiring);
  }

  public getNextQuoteNumber(): Observable<number> {
    return this.http.get<number>(this.url + 'nextQuoteNumber/');
  }

  public searchPageable(searchKey: string, page: number, active: boolean): Observable<QuoteV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<QuoteV2[]>(this.url + 'searchPageable/' + st + '/' + active + '/' + page);
  }

  public search(searchKey: string, active: boolean, complete: boolean): Observable<QuoteV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<QuoteV2[]>(this.url + 'search/' + st + '/' + active + '/' + complete);
  }


  public searchQuoteCount(searchKey: string, active: boolean): Observable<number> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<number>(this.url + 'searchCount/' + st + '/' + active);
  }

  public save(quote: QuoteV2): Promise<Object> {
    if (!quote) return;
    
    return this.http
      .post(this.url + "save/", JSON.stringify(quote))
      .toPromise();
  }

  public get(id: number): Observable<QuoteV2> {
    return this.http.get<QuoteV2>(this.url + `get/${id}`);
  }

}
