import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { InvoiceV2 } from "app/shared/objects2/InvoiceV2";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { SearchUtils } from '../util/SearchUtil';
@Injectable({
  providedIn: "root",
})
export class InvoiceService2 {
  url: string = UrlService.URL + `/invoicev2/`;

  constructor(private http: HttpClient) { }

  public getCount(active: boolean): Observable<number> {
    return this.http.get<number>(this.url + "count/" + active);
  }

  public loadInvoicesByPage(page: number, active: boolean): Observable<InvoiceV2[]> {
    return this.http.get<InvoiceV2[]>(this.url + 'all/' + page + '/' + active);
  }

  public getNextInvoiceNumber(): Observable<number> {
    return this.http.get<number>(this.url + 'nextInvoiceNumber/');
  }

  public searchPageable(searchKey: string, page: number, active: boolean): Observable<InvoiceV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<InvoiceV2[]>(this.url + 'search/' + st + '/' + active + '/' + page);
  }


  public searchQuoteCount(searchKey: string, active: boolean): Observable<number> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<number>(this.url + 'searchCount/' + st + '/' + active);
  }

  public get(id: number): Observable<InvoiceV2> {
    return this.http.get<InvoiceV2>(this.url + `get/${id}`);
  }

  public save(invoice: InvoiceV2): Promise<Object> {
    if (!invoice) return;
    
    return this.http
      .post(this.url + "save/", JSON.stringify(invoice))
      .toPromise();
  }

}
