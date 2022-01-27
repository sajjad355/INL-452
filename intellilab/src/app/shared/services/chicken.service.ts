import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  Chicken, ChickenWEggs
} from "app/shared/objects/Chicken";
// import { AppComponent } from '../../app.component';
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";

@Injectable()
export class ChickenService {
  url: string = UrlService.URL + `/chicken/`;
  constructor(private http: HttpClient) {}

  public loadChicken(): Observable<Chicken[]> {
    
    return this.http.get<Chicken[]>(this.url + "all/");
  }

  public getChickenByDbid(dbid: number): Observable<Chicken> {
    
    return this.http.get<Chicken>(this.url + "get/" + dbid + "/");
  }

  public getChickenByChickenId(id: string): Observable<Chicken> {
    
    return this.http.get<Chicken>(this.url + `getChickenByChickenId/${id}`);
  }

  public getChickenByImmunogen(immunogen: string): Observable<Chicken[]> {
    
    return this.http.get<Chicken[]>(
      this.url + `getChickenByImmunogen/${immunogen}`
    );
  }

  public loadChickenWEggsPage(page: number): Observable<ChickenWEggs[]> {
    
    return this.http.get<ChickenWEggs[]>(this.url + "allWEggsByPage/" + page);
  }

  public getCount(): Observable<number> {
    
    return this.http.get<number>(this.url + "count/");
  }

  public searchChickenbyChickenId(
    searchKey: string,
    page: number
  ): Observable<ChickenWEggs[]> {
    
    return this.http.get<ChickenWEggs[]>(
      this.url + `searchChickenById/${searchKey}/${page}`
    );
  }

  public searchChickenbyChickenIdCount(searchKey: string): Observable<number> {
    
    return this.http.get<number>(
      this.url + `searchChickenByIdCount/${searchKey}/`
    );
  }



  public addChicken(c: Chicken): Promise<Object> {
    if (c == undefined) return;
    
    return this.http
      .post(this.url + "add/", JSON.stringify(c))
      .toPromise();
  }

  public updateChickenInfo(c: Chicken): Promise<Object> {
    if (c == undefined) return;
    
    return this.http
      .put(this.url + "update/" + c.dbid + "/", JSON.stringify(c))
      .toPromise();
  }

  public deleteChicken(c: Chicken ) : Promise<Object> {
    if (c == undefined) {
      return;
    }
    
    return this.http.delete(this.url + "delete/" + c.dbid + "/" + c.editby + "/").toPromise();
  }
}
