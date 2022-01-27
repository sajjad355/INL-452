import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Injection } from 'app/shared/objects/Chicken';
// import { AppComponent } from '../../app.component';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';
@Injectable()
export class InjectionService {
  url: string = UrlService.URL + `/injection/`;

  constructor(private http: HttpClient) {
  }

  public loadInjection(): Observable<Injection[]> {
    
      return this.http.get<Injection[]>(this.url + 'all/');
  }

  public loadInjectionBychickenid(chickenid:string): Observable<Injection[]> {
    
      return this.http.get<Injection[]>(this.url + `getInjectionByChickenId/${chickenid}`);
  }

  public searchInjectionsByChickenid(chickenid:string): Observable<Injection[]> {
    
      return this.http.get<Injection[]>(this.url + `searchInjectionByChickenId/${chickenid}`);
  }

  public addInjection(i: Injection): Promise<Object> {
      if (i == undefined) return;
      return this.http.post(this.url + 'add/', JSON.stringify(i)).toPromise();
  }

  public updateInjection(injections: Injection[]): Promise<Object> {
      if (injections == undefined || injections.length < 1) return;
      injections.forEach(injection => {
          if(injection.dbid == -1)
              return this.http.post(this.url + 'add/', JSON.stringify(injection)).toPromise();
          else
              return this.http.post(this.url + 'update/' + (injection.dbid) + '/', JSON.stringify(injection)).toPromise();
      });
  }

  public updateSingleInjection(i: Injection): Promise<Object> {
      if (i == undefined) return;
      return this.http.put(this.url + 'update/' + (i.dbid) + '/', JSON.stringify(i)).toPromise();

  }

  public deleteInjection(i: Injection): Promise<Object> {
    
      if (i == undefined) return;
          return this.http.delete(this.url + 'delete/' + (i.dbid) + '/' + i.editby + '/').toPromise();
  }
}
