import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { EquipmentV2 } from "../objects2/EquipmentV2";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class EquipmentService2 {
  url: string = UrlService.URL + `/equipmentV2/`;

  private _equipments : EquipmentV2[];

  constructor(private http: HttpClient) {
  }

  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._equipments || forceReload ) {
      return new Promise<string>((resolve, reject) => {
          this.loadEquipment().subscribe( l => {
            this._equipments = l;
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
    this._equipments = equipments
  }

  // Equipments only to be loaded by init or upon modifying by add/update/delete
  public loadEquipment(): Observable<EquipmentV2[]> {
    return this.http.get<EquipmentV2[]>(this.url + "getAllEquipment/");
  }

  public loadEquipmentByPage(page: number): Observable<EquipmentV2[]> {
    return this.http.get<EquipmentV2[]>(this.url + "allActiveEquipment2?active=true&page="+page);
  }

  public getCount(): number {
    if ( !this._equipments) {
      alert( 'Equipments not propertly initialized - call init first');
    }
    return this._equipments.filter(e => e.active).length;
  }

  public getAllEquipments(): EquipmentV2[] {
    if ( !this._equipments) {
      alert( 'Equipments not propertly initialized - call init first');
    }
    return this._equipments;
  }

  public searchEquipmentPageable(searchKey: string, active: boolean, page: number): Observable<EquipmentV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<EquipmentV2[]>(this.url + "searchEquipment2?searchKey=" + st + "&active=" + active + "&page=" + page);
  }

  public searchEquipment(searchKey: string): Observable<EquipmentV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<EquipmentV2[]>(this.url + "searchEquipment2/" + st);
  }

  public searchEquipmentCount(search: string, active: boolean): Observable<number> {
    let st : string = SearchUtils.ReplaceSearchTerms( search );
    return this.http.get<number>(this.url + "searchCount?searchKey=" + st + "&active=" + active);
  }

  public save(equipment : EquipmentV2): Promise<Object> {
    if (!equipment) return;
                                   
    return this.http
      .post(this.url + "addEquipment2/", JSON.stringify(equipment))
      .pipe(
        tap( () =>{
          this.init( true ); // force reload after save
        })
      )
      .toPromise();
  }

  public get(id:number): Observable<EquipmentV2>{
                                    
    return this.http.get<EquipmentV2>(this.url + 'getOneEquipment2?id=' + id);
  }

}
