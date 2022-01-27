import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Eggtransfer } from "app/shared/objects/eggtransfer";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";

@Injectable()
export class EggtransferService {
  url: string = UrlService.URL + `/eggtransfer/`;
  constructor(private http: HttpClient) {}

  public loadEggtransfers(): Observable<Eggtransfer[]> {
    
    return this.http.get<Eggtransfer[]>(this.url + "all/");
  }

  public getEggtranferferByDbId(id: string): Observable<Eggtransfer> {
    
    return this.http.get<Eggtransfer>(this.url + "get/" + id + "/");
  }

  public getEggtransfersByChickenId(id: string): Observable<Eggtransfer[]> {
    
    return this.http.get<Eggtransfer[]>(
      this.url + `getEggtransfersByChickenId/${id}`
    );
  }

  public getCount(): Observable<number> {
    
    return this.http.get<number>(this.url + "count/");
  }

  public searchEggtransfersbyChickenIdCount(
    searchKey: string
  ): Observable<number> {
    
    return this.http.get<number>(
      this.url + `searchEggtransfersbyChickenIdCount/${searchKey}/`
    );
  }

  public addEggtransfer(c: Eggtransfer): Promise<Object> {
    if (c == undefined) return;
    
    return this.http
      .post(this.url + "add/", JSON.stringify(c))
      .toPromise();
  }

  public updateEggtransfer(c: Eggtransfer): Promise<Object> {
    if (c == undefined) return;
    return this.http
      .put(this.url + "update/" + c.dbid + "/", JSON.stringify(c))
      .toPromise();
  }

  public deleteEggtransfer(c: Eggtransfer): Promise<Object> {
    if (c == undefined) {
      return;
    }
    
    return this.http.delete(this.url + "delete/" + c.dbid + "/" + c.editby + '/').toPromise();
  }
}
