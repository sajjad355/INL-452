import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OperationV2 } from '../objects2/UserV2';
import { UrlService } from '../services/url.service';
@Injectable({
  providedIn: 'root',
})
export class OperationService2 {
  url: string = UrlService.URL + `/operationV2/`;

  constructor(private http: HttpClient) { }

  public loadOperations(): Observable<OperationV2[]> {
    return this.http.get<OperationV2[]>(this.url + 'all/');
  }
}
