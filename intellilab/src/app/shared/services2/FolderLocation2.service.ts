import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { FolderLocationV2 } from "../objects2/FolderLocationV2";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class FolderLocation2 {
  url: string = UrlService.URL + `/foldercustodian/`;
  
  private _folderLocations : FolderLocationV2[];
  
  constructor(private http: HttpClient) {
  }  
  
  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._folderLocations || forceReload ) {      
      return new Promise<string>((resolve, reject) => {
          this.loadLocations().subscribe( l => {
            this._folderLocations = l;
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

  // Equipments only to be loaded by init or upon modifying by add/update/delete
  private loadLocations(): Observable<FolderLocationV2[]> {  
    return this.http.get<FolderLocationV2[]>(this.url + "getFolderLocationList/");
  }

  public loadLocationsByPage(page: number): Observable<FolderLocationV2[]> {
    return this.http.get<FolderLocationV2[]>(this.url + "getFolderLocationList/");
  }

  public getCount(): number {
    if ( !this._folderLocations) {
      alert( 'Equipments not propertly initialized - call init first');      
    }
    return this._folderLocations.filter(e => e.active).length;
  }

  public getAllEquipments(): FolderLocationV2[] {
    if ( !this._folderLocations) {
      alert( 'Equipments not propertly initialized - call init first');      
    }
    return this._folderLocations;
  }
  
  public searchFolderLocationBy(searchKey: string, searchBy: string): Observable<FolderLocationV2[]> {
    return this.http.get<FolderLocationV2[]>(this.url + "searchFolderLocationByParam?"+searchBy+"="+searchKey);
  }

  public searchEquipmentPageable(searchKey: string, active: boolean, page: number): Observable<FolderLocationV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<FolderLocationV2[]>(this.url + "searchEquipment2?searchKey=" + st + "&active=" + active + "&page=" + page);
  }

  public searchEquipment(searchKey: string): Observable<FolderLocationV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<FolderLocationV2[]>(this.url + "searchEquipment2/" + st);
  }

  public searchEquipmentCount(search: string, active: boolean): Observable<number> {
    let st : string = SearchUtils.ReplaceSearchTerms( search );
    return this.http.get<number>(this.url + "searchCount?searchKey=" + st + "&active=" + active);
  }

  public saveLocation(location : FolderLocationV2): Promise<Object> {
    if(!location) return;
  
    return this.http
    .post(this.url + "addFolderLocation/", JSON.stringify(location))
    .pipe( 
      tap( () =>{
        this.init( true );
      })
    )
    .toPromise();    
  }

  public get(id:number): Observable<FolderLocationV2>{
    return this.http.get<FolderLocationV2>(this.url + 'getOneEquipment2?id=' + id);
  }

}