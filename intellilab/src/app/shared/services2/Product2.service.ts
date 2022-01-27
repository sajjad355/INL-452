import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { ProductV2 } from "../objects2/ProductV2";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class ProductService2 {
  url: string = UrlService.URL + `/productv2/`;

  constructor(private http: HttpClient) {
  }

  public loadProductByPage(page: number, active: boolean): Observable<ProductV2[]> {
    return this.http.get<ProductV2[]>(this.url + "all/" + page + "/" + active);
  }

  public countAllActive(active: boolean): Observable<number> {
    return this.http.get<number>(this.url + "count/" + active);
  }

  public searchProductPageable(searchKey: string, active: boolean, page: number): Observable<ProductV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<ProductV2[]>(this.url + "search/" + st + "/" + active + "/" + page + "/");
  }

  public searchProduct(searchKey: string): Observable<ProductV2[]> {
    let st: string = SearchUtils.ReplaceSearchTerms(searchKey);
    return this.http.get<ProductV2[]>(this.url + "search/" + st);
  }

  public searchProductCount(search: string, active: boolean): Observable<number> {
    let st: string = SearchUtils.ReplaceSearchTerms(search);
    return this.http.get<number>(this.url + "searchCount/" + st + "/" + active + "/");
  }

  public save(product: ProductV2): Promise<Object> {
    if (!product) return;
    
    return this.http
      .post(this.url + "save/", JSON.stringify(product))
      .toPromise();
  }

  public get(id: number): Observable<ProductV2> {
    return this.http.get<ProductV2>(this.url + `get/${id}`);
  }


}
