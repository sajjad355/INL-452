import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CatalogNumberV2 } from "app/shared/objects2/CatalogNumberV2";
import { UrlService } from "app/shared/services/url.service";

@Injectable({
  providedIn: "root",
})
export class CatalogService2 {
  url: string = UrlService.URL + `/catalogv2/`;

  constructor(private http: HttpClient) { }

  public getNextCatalogNumber(catalogNumberRequest: CatalogNumberV2): Promise<Object> {
    if (!catalogNumberRequest) return;
    
    return this.http
      .post(this.url + "request/", JSON.stringify(catalogNumberRequest))
      .toPromise();
  }



}
