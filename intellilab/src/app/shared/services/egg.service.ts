import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Egg } from 'app/shared/objects/Chicken';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';

@Injectable()
export class EggService {
  url: string = UrlService.URL + `/egg/`;

  constructor(private http: HttpClient) {
  }

  public loadEgg(): Observable<Egg[]> {
    
      return this.http.get<Egg[]>(this.url + 'all/');
  }

  public getEggWithDbid(id: number): Observable<Egg> {
    
    return this.http.get<Egg>(this.url + 'get/' + id + '/');
}

  public loadEggBychickenid(chickenid:string): Observable<Egg[]> {
    
      return this.http.get<Egg[]>(this.url + `getIEggByChickenId/${chickenid}`);
  }

  public searchEggsByChickenid(chickenid:string): Observable<Egg[]> {
    
      return this.http.get<Egg[]>(this.url + `searchEggsByChickenId/${chickenid}`);
  }

  public addEgg(e: Egg): Promise<Object> {
      if (e == undefined) return

      return this.http.post(this.url + 'add/', JSON.stringify(e)).toPromise();
  }

  public updateEgg(eggs: Egg[]): Promise<Object> {
    if (eggs == undefined || eggs.length < 1) return;
    
    eggs.forEach(egg => {
    if(egg.dbid == -1)
        return this.http.post(this.url + 'add/', JSON.stringify(egg)).toPromise();
    else
        return this.http.post(this.url + 'update/' + (egg.dbid) + '/', JSON.stringify(egg)).toPromise();
      });
  }

  public updateSingleEgg(e: Egg): Promise<Object> {
    if (e == undefined) return;

    return this.http.put(this.url + 'update/' + (e.dbid) + '/', JSON.stringify(e)).toPromise();
  }

  public deleteEgg(e: Egg): Promise<Object> {
    if (e == undefined) return;
        return this.http.delete(this.url + 'delete/' + (e.dbid) + '/' + e.editby + '/').toPromise();
  }
}
