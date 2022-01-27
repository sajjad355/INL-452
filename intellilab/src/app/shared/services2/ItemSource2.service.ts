import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { ItemSourceV2 } from "../objects2/ItemSourceV2";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class ItemSourceService2 {
  url: string = UrlService.URL + `/itemSource/`;

  private _sources : ItemSourceV2[];

  constructor(private http: HttpClient) {
  }

  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._sources || forceReload ) {
      return new Promise<string>((resolve, reject) => {
          this.loadItemSources().subscribe( l => {
            this._sources = l;
            resolve('complete');
          }, error => {
            reject( error );
          });
      });
    }
    else {
      return new Promise<string>((resolve, reject) => {
        resolve( 'allready loaded');
      });
    }
  }

  setEquipment(equipments){
    this._sources = equipments
  }

  // Equipments only to be loaded by init or upon modifying by add/update/delete
  public loadItemSources(): Observable<ItemSourceV2[]> {
    return this.http.get<ItemSourceV2[]>(this.url + "getAllItemSource/");
  }

  public loadItemSourcesBy(type, status): Observable<ItemSourceV2[]> {
    return this.http.get<ItemSourceV2[]>(this.url + "getItemSourcesBy?type="+type+'&status='+status);
  }

  public loadItemSourcesByPage(page: number): Observable<ItemSourceV2[]> {
    return this.http.get<ItemSourceV2[]>(this.url + "allActiveEquipment2?active=true&page="+page);
  }



  public getAllItemSource(): ItemSourceV2[] {
    if ( !this._sources) {
      alert( 'Item sources not propertly initialized - call init first');
    }
    return this._sources;
  }

  public searchItemSource(type: string, term: string): Observable<ItemSourceV2[]> {
    return this.http.get<ItemSourceV2[]>(this.url + "getItemSourcesByNameandType?type="+type+"&name="+term);
  }

  public searchEquipmentPageable(searchKey: string, active: boolean, page: number): Observable<ItemSourceV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<ItemSourceV2[]>(this.url + "searchEquipment2?searchKey=" + st + "&active=" + active + "&page=" + page);
  }

  public searchEquipment(searchKey: string): Observable<ItemSourceV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<ItemSourceV2[]>(this.url + "searchEquipment2/" + st);
  }

  public searchEquipmentCount(search: string, active: boolean): Observable<number> {
    let st : string = SearchUtils.ReplaceSearchTerms( search );
    return this.http.get<number>(this.url + "searchCount?searchKey=" + st + "&active=" + active);
  }

  public saveItemSource(source : ItemSourceV2): Promise<Object> {
    if (!source) return;

    return this.http
      .post(this.url + "addItemSource/", JSON.stringify(source))
      .pipe(
        tap( () =>{
          this.init( true ); // force reload after save
        })
      )
      .toPromise();
  }

  public get(id:number): Observable<ItemSourceV2>{
    return this.http.get<ItemSourceV2>(this.url + 'getOneEquipment2?id=' + id);
  }

}
