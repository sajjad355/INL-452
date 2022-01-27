import { HttpClient } from '@angular/common/http';
import { SimpleClientV2 } from '../objects2/SimpleClientV2';
import { ClientV2 } from '../objects2/ClientV2';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UrlService } from 'app/shared/services/url.service';
import { SearchUtils } from '../util/SearchUtil';


@Injectable({
  providedIn: 'root',
})
export class ClientService2 {

  url: string = UrlService.URL + `/clientv2/`;

  constructor(private http: HttpClient) { }

  public getAllClientsByPage(page: number, active: boolean): Observable<SimpleClientV2[]> {
    return this.http.get<SimpleClientV2[]>(this.url + `all/${active}/${page}`);
  }

  public getClientCount(active: boolean): Observable<number> {
    return this.http.get<number>(this.url + `count/${active}`);
  }

  public searchPageable(key: string, active: boolean, page: number): Observable<SimpleClientV2[]> {
    const st: string = SearchUtils.ReplaceSearchTerms(key);
    return this.http.get<SimpleClientV2[]>(this.url + 'search/' + st + '/' + active + '/' + page);
  }

  public search(key: string, active: boolean): Observable<SimpleClientV2[]> {
    const st: string = SearchUtils.ReplaceSearchTerms(key);
    return this.http.get<SimpleClientV2[]>(this.url + 'search/' + st + '/' + active);
  }

  public searchCount(key: string, active: boolean): Observable<number> {
    const st: string = SearchUtils.ReplaceSearchTerms(key);

    return this.http.get<number>(this.url + 'searchCount/' + st + '/' + active + '/');
  }

  public searchExactNameMatch(key: string): Observable<ClientV2[]> {
    const st: string = SearchUtils.ReplaceSearchTerms(key);
    return this.http.get<ClientV2[]>(this.url + 'searchExactNameMatch/' + st);
  }


  public save(client: ClientV2): Promise<Object> {
    if (!client) { return; }

    return this.http
      .post(this.url + 'save/', JSON.stringify(client))
      .toPromise();
  }

  public get(id: number): Observable<ClientV2> {
    return this.http.get<ClientV2>(this.url + `get/${id}`);
  }

}
