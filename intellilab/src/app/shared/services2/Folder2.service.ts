import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { FolderV2 } from "../objects2/FolderV2";
import { SearchUtils } from '../util/SearchUtil';

@Injectable({
  providedIn: "root",
})
export class Folder2 {
  url: string = UrlService.URL + `/foldercustodian/`;
  
  private _folders : FolderV2[];
  
  constructor(private http: HttpClient) {
  }  
  
  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._folders || forceReload ) {      
      return new Promise<string>((resolve, reject) => {
          this.loadFolders().subscribe( l => {
            this._folders = l;
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
  private loadFolders(): Observable<FolderV2[]> {
    return this.http.get<FolderV2[]>(this.url + "getFolderList/0");
  }

  public loadFoldersByPage(page: number): Observable<FolderV2[]> {
    return this.http.get<FolderV2[]>(this.url + "getFolderList/"+page);
  }

  public getCount(): Observable<number> {
    return this.http.get<number>(this.url + "getFolderCount");
  }

  public getAllEquipments(): FolderV2[] {
    if ( !this._folders) {
      alert( 'Equipments not propertly initialized - call init first');      
    }
    return this._folders;
  }

  public searchFolderBy(searchKey: string, searchBy: string): Observable<FolderV2[]> {
    return this.http.get<FolderV2[]>(this.url + "searchFolderByParam?"+searchBy+"="+searchKey);
  }

  public searchFolderPageable(searchKey: string, active: boolean, page: number): Observable<FolderV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<FolderV2[]>(this.url + "searchEquipment2?searchKey=" + st + "&active=" + active + "&page=" + page);
  }

  public searchEquipment(searchKey: string): Observable<FolderV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<FolderV2[]>(this.url + "searchEquipment2/" + st);
  }

  public searchEquipmentCount(search: string, active: boolean): Observable<number> {
    let st : string = SearchUtils.ReplaceSearchTerms( search );
    return this.http.get<number>(this.url + "searchCount?searchKey=" + st + "&active=" + active);
  }

  public saveFolder(folder : FolderV2): Promise<Object> {
    if(!folder) return;
    
    return this.http
    .post(this.url + "addFolder/", JSON.stringify(folder))
    .pipe( 
      tap( () =>{
        this.init( true );
      })
    )
    .toPromise();    
  }

  public requestTransfer(request): Promise<Object> {
    if(!request) return;
    
    return this.http
    .post(this.url + "requestTransfer/", JSON.stringify(request))
    .pipe( 
      tap( () =>{
        this.init( true );
      })
    )
    .toPromise();
  }

  public get(id:number): Observable<FolderV2>{
    return this.http.get<FolderV2>(this.url + 'getOneEquipment2?id=' + id);
  }

}