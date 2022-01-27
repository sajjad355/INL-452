import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
// import { AppComponent } from '../../app.component';
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { Barcode } from "../objects/Barcode";

@Injectable()
export class BarcodeService {
  url: string = UrlService.URL + `/barcode/`;
  barcodes: Barcode[];

  constructor(private http: HttpClient) {}

  public loadBarcodes(): Observable<Barcode[]> {
    return this.http.get<Barcode[]>(this.url + "all/");
  }

  public getBarcodesCount(): Observable<number> {
    return this.http.get<number>(this.url + "count/");
  }

  public getBarcodesByItemdetailDbid(
    itemdetaildbid: number
  ): Observable<Barcode[]> {
    return this.http.get<Barcode[]>(
      this.url + `getBarcodesByItemdetailDbid/${itemdetaildbid}`
    );
  }

  public getBarcodesByBarcode(barcode: String): Observable<Barcode> {
    return this.http.get<Barcode>(this.url + `getBarcodesByBarcode/${barcode}`);
  }

  public getLatest(): Observable<Barcode> {
    return this.http.get<Barcode>(this.url + "latest/");
  }

  public addBarcode(b: Barcode): Promise<Object> {
    if (b == undefined) return;
    
    return this.http
      .post(this.url + "add/", JSON.stringify(b))
      .toPromise();
  }

  public updateBarcode(b: Barcode): Promise<Object> {
    if (b == undefined) return;
    
    return this.http
      .put(this.url + "update/" + b.dbid + "/", JSON.stringify(b))
      .toPromise();
  }

  public deleteBarcode(b: Barcode): Promise<Object> {
    if (b == undefined) return;
    return this.http
      .delete(this.url + "delete/" + b.dbid + "/" + b.editby + "/")
      .toPromise();
  }
}
