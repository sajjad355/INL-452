import { HttpClient, HttpParams } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { UrlService } from 'app/shared/services/url.service'
import { Observable } from 'rxjs'
import { OrderItemV2 } from '../objects2/OrderItemV2'
import { PurchaseOrderV2 } from '../objects2/PurchaseOrderV2'
import { UserV2 } from '../objects2/UserV2'
import { SearchUtils } from '../util/SearchUtil'

@Injectable({
  providedIn: 'root',
})
export class OrderItemService2 {
  url: string = UrlService.URL + `/orderitemv2/`
  poUrl: string = UrlService.URL + `/purchaseOrder/`
  invent: OrderItemV2[]

  constructor(private http: HttpClient) { }

  public get(orderItemId: number): Observable<OrderItemV2> {
    return this.http.get<OrderItemV2>(this.url + 'get/' + orderItemId + '/')
  }

  public getPurchaseOrder(purchaseOrderId: number): Observable<PurchaseOrderV2> {
    return this.http.get<PurchaseOrderV2>(this.poUrl + 'get/' + purchaseOrderId + '/')
  }

  // accepts ANY as requestedByUserName if you want to search all users
  public loadOrders(status: string,
    requestedByUserName: string,
    urgentOrdersOnly: boolean,
    page: number): Observable<OrderItemV2[]> {

    return this.http.get<OrderItemV2[]>(this.url + `loadOrders/${status}/${requestedByUserName}/${urgentOrdersOnly}/${page}`)
  }

  // accepts ANY as requestedByUserName if you want to search all users
  public countOrders(status: string,
    requestedByUserName: string,
    urgentOrdersOnly: boolean): Observable<number> {
    return this.http.get<number>(this.url + `count/${status}/${requestedByUserName}/${urgentOrdersOnly}`)
  }

  // accepts ANY as requestedByUserName if you want to search all users
  public search(status: string,
    searchKey: string,
    requestedByUserName: string,
    urgentOrdersOnly: boolean,
    page: number): Observable<OrderItemV2[]> {
    const st: string = SearchUtils.ReplaceSearchTerms(searchKey)
    return this.http.get<OrderItemV2[]>(this.url + `search/${status}/${st}/${requestedByUserName}/${urgentOrdersOnly}/${page}`)
  }

  // accepts ANY as requestedByUserName if you want to search all users
  public searchCount(status: string,
    searchKey: string,
    requestedByUserName: string,
    urgentOrdersOnly: boolean): Observable<number> {
    const st: string = SearchUtils.ReplaceSearchTerms(searchKey)
    return this.http.get<number>(this.url + `searchCount/${status}/${st}/${requestedByUserName}/${urgentOrdersOnly}`)
  }

  public save(orderItems: OrderItemV2[]): Promise<Object> {
    if (!orderItems) { return }
    console.log('save order item', orderItems)

    return this.http
      .post(this.url + 'save/', JSON.stringify(orderItems))
      .toPromise()
  }

  // returns an InventoryV2 object in the Promise that was either created or updated as part of receiving the order
  public receive(receivedUser: UserV2,
                 orderItemId: number,
                 receivedAmount: number): Promise<Object> {
    if (!receivedUser) { return }
    const params = new HttpParams().append( 'orderItemId', orderItemId.toString())
                                   .append( 'receivedAmount', receivedAmount.toString())

    return this.http
      .post(this.url + 'receive/', JSON.stringify(receivedUser), { params } )
      .toPromise()
  }

  public savePurchaseOrder(po: PurchaseOrderV2): Promise<Object> {
    if (!po) { return; }

    console.log('this is save po ', po)
    return this.http
      .post(this.poUrl + 'save/', JSON.stringify(po))
      .toPromise()
  }

}
