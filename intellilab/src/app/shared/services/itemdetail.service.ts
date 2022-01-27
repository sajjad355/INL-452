import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { itemdetail } from "../objects/item";
import { UrlService } from "app/shared/services/url.service";

@Injectable()
export class ItemdetailService {

  url: string = UrlService.URL + `/Itemdetail/`;

  constructor(private http: HttpClient) {}

  public loadItemDetail(): Observable<itemdetail[]> {
    
    return this.http.get<itemdetail[]>(this.url + "all/");
  }

  public searchItemdetailsByItemdbid(
    itemdbid: number
  ): Observable<itemdetail[]> {
    
    return this.http.get<itemdetail[]>(
      this.url + `searchItemdetailsByItemid/${itemdbid}`
    );
  }

  public loadItemDetailByItemDbid(itemdbid: number): Observable<itemdetail[]> {
    
    return this.http.get<itemdetail[]>(
      this.url + `getItemDetailByItemDbid/${itemdbid}`
    );
  }

  public getItemdetailWithId(id: number): Observable<itemdetail> {
    
    return this.http.get<itemdetail>(this.url + "get/" + id + "/");
  }

  public addItemDetail(itemdetail: itemdetail): Promise<Object> {
    if (itemdetail == undefined) return;
    
    return this.http
      .post(this.url + "add/", JSON.stringify(itemdetail))
      .toPromise();
  }

  public updateItemDetail(itemdetails: itemdetail[]): Promise<Object> {
    if (itemdetails == undefined || itemdetails.length < 1) return;
    
    itemdetails.forEach((itemdetail) => {
      if (itemdetail.dbid !== -1)
        return this.http
          .post(
            this.url + "update/" + itemdetail.dbid + "/",
            JSON.stringify(itemdetail)            
          )
          .toPromise();
    });
  }

  public updateSingleItemDetail(itemdetail: itemdetail): Promise<Object> {
    if (itemdetail == undefined) return;
    
    return this.http
      .put(
        this.url + "update/" + itemdetail.dbid + "/",
        JSON.stringify(itemdetail)
      )
      .toPromise();
  }

  public deleteItemDetail(itemdetail: itemdetail): Promise<Object> {
    
    if (itemdetail == undefined) return;
    return this.http
      .delete(this.url + "delete/" + itemdetail.dbid + "/" + itemdetail.modifyperson + "/")
      .toPromise();
  }

  uploadFile(file: File, itemdbid: number): Promise<Object> {
    
    let formdata: FormData = new FormData();

    formdata.append("file", file);

    if (file == undefined) return;
    return this.http
      .put(this.url + "uploadfile/" + itemdbid + "/", formdata)
      .toPromise();
  }

  uploadRfFile(file: File, itemdbid: number): Promise<Object> {
    
    let formdata: FormData = new FormData();
    formdata.append("file", file);
    if (file == undefined) return;
    return this.http
      .put(this.url + "uploadrffile/" + itemdbid + "/", formdata)
      .toPromise();
  }

  getFilepath(itemdetaildbid: number): Observable<any> {
    let headers = new HttpHeaders({
      "MyApp-Application": "AppName",
      "Accept": "application/pdf",
    });

    return this.http.get(this.url + "getfile/" + itemdetaildbid + "/", {
      headers,
      responseType: "blob",
    });
  }

  getrfFilepath(itemdetaildbid: number): Observable<any> {
    let headers = new HttpHeaders({
      "MyApp-Application": "AppName",
      "Accept": "application/pdf",
    });

    return this.http.get(this.url + "getrffile/" + itemdetaildbid + "/", {
      headers,
      responseType: "blob",
    });
  }
}
