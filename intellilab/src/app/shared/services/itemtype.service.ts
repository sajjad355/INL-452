import { Injectable } from '@angular/core';
import { Itemtype } from '../objects/item';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UrlService } from 'app/shared/services/url.service';

@Injectable()
export class ItemtypeService {
  url: string = UrlService.URL + `/itemtype/`;
  itemtypes: Itemtype[];

  constructor(private http: HttpClient) { }
  public loadItemtype(): Observable<Itemtype[]> {
    return this.http.get<Itemtype[]>(this.url + 'all/');
  }
}

