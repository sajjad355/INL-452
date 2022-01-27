import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Itemarchive, Itemdetailarchive } from 'app/shared/objects/archives';
// import { AppComponent } from '../../app.component';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';
@Injectable()
export class ArchiveService {
  itemarchiveurl: string = UrlService.URL + `/itemarchive/`;
  itemdetailarchiveurl: string = UrlService.URL + `/itemdetailarchive/`;
  constructor(private http: HttpClient) { }

  public getItemarchiveByItemdbid(id:number):  Observable<Itemarchive[]>{
    return this.http.get<Itemarchive[]>(this.itemarchiveurl + `getItemarchiveByItemdbid/${id}`);
}

public getItemdetailarchiveByItemdetaildbid(id:number):  Observable<Itemdetailarchive[]>{
  return this.http.get<Itemdetailarchive[]>(this.itemdetailarchiveurl + `getItemdetailarchiveByItemdetaildbid/${id}`);
}

public addItemarchive(i: Itemarchive): Promise<Object> {
  if (i == undefined) return;
  
  return this.http.post(this.itemarchiveurl + 'add/', JSON.stringify(i)).toPromise();
}



}
