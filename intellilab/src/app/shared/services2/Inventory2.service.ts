import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InventoryV2 } from '../objects2/InventoryV2';
import { UrlService } from '../services/url.service';
import { SearchUtils } from '../util/SearchUtil';

@Injectable({ providedIn: 'root' })
export class InventoryService2 {
  url: string = UrlService.URL + `/inventoryV2/`;
  items: InventoryV2[];

  constructor(private http: HttpClient) { }

  public getAllItems(): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(this.url + `loadAll`);
  }

  public getAllItemsByPage(page: number): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(this.url + `all/${page}`);
  }

  // Returns the number of *active* items
  public getCount(): Observable<number> {
    return this.http.get<number>(
      this.url + `count/`
    );
  }

  public search(key: string): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(
      this.url + `search/all/${SearchUtils.ReplaceSearchTerms(key)}`
    );
  }

  // Criterion must be one of
  // ['like', 'location', 'type']
  // like will search by comparing key to catalogNumber, name, manufacturer, locationId or type
  public searchForCriterionByPage(criterion: string, key: string, page: number, activeOnly: boolean): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(
      this.url + `search/${criterion}/${SearchUtils.ReplaceSearchTerms(key)}/${page}/${activeOnly}`
    );
  }

  // Criterion must be one of
  // ['like', 'location', 'type', 'catalogNumber']
  // like will search by comparing key to catalogNumber, name, manufacturer, locationId or type
  public getCountForCriterion(criterion: string, key: string, activeOnly: boolean): Observable<number> {
    return this.http.get<number>(
      this.url + `count/${criterion}/${SearchUtils.ReplaceSearchTerms(key)}/${activeOnly}`
    );
  }

  public searchByLocationAndSubLocationByPage(location: string, subLocation: string, page: number, activeOnly: boolean): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(
      this.url +
      `search/locationAndSubLocation/${SearchUtils.ReplaceSearchTerms(location)}/${SearchUtils.ReplaceSearchTerms(subLocation)}/${page}/${activeOnly}`
    );
  }

  public getCountForLocationAndSubLocation(location: string, subLocation: string, activeOnly: boolean): Observable<number> {
    return this.http.get<number>(
      this.url +
      `count/locationAndSubLocation/${SearchUtils.ReplaceSearchTerms(location)}/${SearchUtils.ReplaceSearchTerms(subLocation)}/${activeOnly}`
    );
  }

  public searchByNameAndCatalogNumberExactMatch(name: string, catalogNumber: string): Observable<InventoryV2[]> {
    return this.http.get<InventoryV2[]>(
      this.url +
      `searchByNameAndCatalogNumberExactMatch/${SearchUtils.ReplaceSearchTerms(name)}/${SearchUtils.ReplaceSearchTerms(catalogNumber)}`
    );
  }

  public validateCatalogNumber(identifier: number, catalogNumber: string) {
    return this.http.get<boolean>(this.url + `/catalogNumberValidator/${identifier}/${catalogNumber}`);
  }

  public getLowInStock() {
    return this.http.get<InventoryV2[]>(this.url + `loadLowInStock`);
  }

  public validateName(identifier: number, catalogNumber: string, supplierCatalogNumber: string, unit: string, containerSize: number) {
    return this.http.get<boolean>(this.url + `/nameValidator/${identifier}/${catalogNumber}/${supplierCatalogNumber}/${unit}/${containerSize}`);
  }

  public get(id: number): Observable<InventoryV2> {
    return this.http.get<InventoryV2>(this.url + `get/${id}`);
  }



  public save(item: InventoryV2): Promise<Object> {
    return this.http.post(
        this.url + `save/`,
        JSON.stringify(item)
      )
      .toPromise();
  }

  // returns an InventoryV2 object in the Promise that was either created or updated as part of receiving the order
  public checkout(inventory : InventoryV2,          // top level inv containing the detail item we are checking out
                  inventoryDetailId : number,       // id of detail item we are checkin gout
                  checkoutType : string,            //  'Unopened' or 'Inuse' are only allowed values
                  checkoutPurpose : string,         // 'Use' or 'Move' are only allowed values
                  checkoutUserName : string,        // username of user performing the checkout
                  requestedCheckoutAmount : number, // checkout amount. Must be greater than 0.
                  checkoutLocationId : number,      // the location id to where the item will be checkout to. Required  if checkout purpose is 'Move'. Provide 0 if NA
                  checkoutSubLocationId : number,   // the sub location id to where the item will be checkout to - optional. Provide 0 if NA
                  checkoutPurposeReason : string    // user given reason for the checkout purpose
                  ): Promise<Object> {
    const params = new HttpParams().append( 'inventoryDetailId', inventoryDetailId.toString())
                                  .append( 'checkoutType', checkoutType)
                                  .append( 'checkoutPurpose', checkoutPurpose)
                                  .append( 'checkoutUserName', checkoutUserName)
                                  .append( 'requestedCheckoutAmount', requestedCheckoutAmount.toString())
                                  .append( 'checkoutPurposeReason', checkoutPurposeReason )
                                  .append('checkoutLocationId', checkoutLocationId.toString() )
                                  .append('checkoutSubLocationId', checkoutSubLocationId.toString() );
    return this.http
    .post(this.url + 'checkout/', JSON.stringify(inventory), { params } )
    .toPromise();
  }





}
