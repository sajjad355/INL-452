import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from "app/shared/services/url.service";
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { RequestV2 } from "../objects2/RequestV2";
import { UserV2 } from "../objects2/UserV2";
import { SearchUtils } from '../util/SearchUtil';
import { AuthenticationService2 } from "./Authenticate2.service";

@Injectable({
  providedIn: "root",
})
export class TransferRequest2 {
  url: string = UrlService.URL + `/foldercustodian/`;

  private _requests = [];
  currentUser: UserV2;

  constructor(private http: HttpClient, private authenticationservice: AuthenticationService2) {
    this.getUserInfo()
  }

  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._requests || forceReload ) {
      return new Promise<string>((resolve, reject) => {
          this.loadRequests(this.currentUser?.userId).subscribe( l => {
            this._requests = l;
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

  getUserInfo(){
    let res = this.authenticationservice.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.currentUser = UserV2.copy(user)
      } )
    }
  }

  // Equipments only to be loaded by init or upon modifying by add/update/delete
  public loadRequests(userId: number): Observable<RequestV2[]> {
    return this.http.get<RequestV2[]>(this.url + `getTransferList/${userId}`);
  }

  public loadRequestByConditions(userId: number, requestStatus: string, page: number): Observable<RequestV2[]> {
    return this.http.get<RequestV2[]>(this.url + `getTransferListPageable/${userId}/${requestStatus}/${page}`);
  }

  public getAllTransfers(userId: number, requestStatus: string): Observable<RequestV2[]> {
    return this.http.get<RequestV2[]>(this.url + `getTransferListAll/${userId}/${requestStatus}`);
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

  public getCount(): number {
    if ( !this._requests) {
      alert( 'Equipments not propertly initialized - call init first');
    }
    return this._requests.filter(e => e.active).length;
  }

  public getAllEquipments(): RequestV2[] {
    if ( !this._requests) {
      alert( 'Equipments not propertly initialized - call init first');
    }
    return this._requests;
  }

  public searchEquipmentPageable(searchKey: string, active: boolean, page: number): Observable<RequestV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<RequestV2[]>(this.url + "searchEquipment2?searchKey=" + st + "&active=" + active + "&page=" + page);
  }

  public searchEquipment(searchKey: string): Observable<RequestV2[]> {
    let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
    return this.http.get<RequestV2[]>(this.url + "searchEquipment2/" + st);
  }

  public searchEquipmentCount(search: string, active: boolean): Observable<number> {
    let st : string = SearchUtils.ReplaceSearchTerms( search );
    return this.http.get<number>(this.url + "searchCount?searchKey=" + st + "&active=" + active);
  }

  public saveFolder(folder : RequestV2): Promise<Object> {
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

  public get(id:number): Observable<RequestV2>{
    return this.http.get<RequestV2>(this.url + 'getOneEquipment2?id=' + id);
  }

}
