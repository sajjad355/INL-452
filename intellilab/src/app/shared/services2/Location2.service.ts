import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from "rxjs";
import { tap } from 'rxjs/operators';
import { LocationV2 } from '../objects2/LocationV2';


// do not add LocationService to any component providers list - it is meant to be a singleton initialized
// only from AppModule
@Injectable({
  providedIn: 'root',
})
export class LocationService2 {
  url: string = UrlService.URL + `/locationv2/`;
  private _locations : LocationV2[];


  constructor(private http: HttpClient) {
  }



  init( forceReload : boolean = false ) : Promise<string> {
    if ( !this._locations || forceReload ) {
      return new Promise<string>((resolve, reject) => {
          this.loadLocation().subscribe( l => {
            this._locations = l;
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

  setLocation(locations){
    this._locations = locations
  }

  // locations only to be loaded by init or upon modifying locations by add/update/delete
  public loadLocation(): Observable<LocationV2[]> {
    return this.http.get<LocationV2[]>(this.url + "all/");
  }

  public getAllLocations(): LocationV2[] {
    if ( !this._locations) {
      alert( 'Locations not propertly initialized - call init first');
    }
    // console.log('Returned locations: ')
    // console.log(this._locations)
    return this._locations;
  }

  public saveLocation(location: LocationV2): Promise<Object> {
    if (!location) return;

    return this.http
      .post(this.url + "save/", JSON.stringify(location))
      .pipe(
        tap( () =>{
          this.init( true ); // force reload after save
        })
      )
      .toPromise();
  }

  // returns a count of other tables that have a link to this location - used when determining
  // whether a specific location can be deleted.
  public getReferenceCount(locationId: number): Observable<number> {
    return this.http.get<number>(this.url + `getReferenceCount/${locationId}` + '/');
  }




}
