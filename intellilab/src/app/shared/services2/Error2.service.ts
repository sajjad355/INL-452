import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UrlService } from 'app/shared/services/url.service';

@Injectable({
  providedIn: 'root',
})
export class ErrorService2 {
  url: string = UrlService.URL;

  constructor(private http: HttpClient) {
  }

  public email(error): Promise<Object> {
    if (!error) return;

    return this.http
      .post(this.url + "/exception/submit", JSON.stringify(error))
      .toPromise();
  }

}
