import { item } from 'app/shared/objects/item';
import { UserV2 } from './UserV2';
import { ClientV2 } from './ClientV2';

export class OrderItemV2 {
  public orderItemId : number = -1;
  public catalogNumber : string;
  public category : string;
  public supplierCatalogNumber : string;
  public name : string;
  public type : string;
  public subtype : string;
  public supplier : string;
  public manufacturer : string;
  public amount : number;
  public backOrderedAmount : number;
  public unit : string;
  public containerSize : number;
  public unitPrice : number;
  public currency : string;
  public currencyRate : number;
  public project : string;
  public requestUser: UserV2;
  public purchaseOrder: number;
  public approveUser: UserV2;
  public orderUser: UserV2;
  public receiveUser: UserV2;
  public rejectUser: UserV2; 
  public status : string;
  public statusReason : string;
  public requestTime : Date;
  public approveTime: Date;
  public orderTime: Date;
  public receiveTime: Date;
  public rejectedTime: Date;
  public eta: Date;
  public urgent : boolean;
  public comment : string;
  public reserveForClient: ClientV2;
  public editedBy : string;
  public modifiedOn : Date;

  public static copy(orderItem: OrderItemV2) {
    let copyRequestUser : UserV2;
    if ( orderItem.requestUser )
      copyRequestUser = UserV2.copy(orderItem.requestUser);
    let copyApproveUser : UserV2;
    if ( orderItem.approveUser )
      copyApproveUser = UserV2.copy(orderItem.approveUser);
    let copyOrderUser : UserV2;
    if ( orderItem.orderUser )
      copyOrderUser = UserV2.copy(orderItem.orderUser);    
    let copyReceiveUser : UserV2;
    if ( orderItem.receiveUser )
      copyReceiveUser = UserV2.copy(orderItem.receiveUser); 
    let copyRejectUser : UserV2;
    if ( orderItem.rejectUser )
        copyRejectUser = UserV2.copy(orderItem.rejectUser);   
    let copyReserveForClient : ClientV2;
    if ( orderItem.reserveForClient )
      copyReserveForClient = ClientV2.copy(orderItem.reserveForClient); 
           

    return {
      ...orderItem,
      requestUser: copyRequestUser,
      approveUser: copyApproveUser,
      orderUser: copyOrderUser,
      receiveUser: copyReceiveUser,
      rejectUser: copyRejectUser,
      reserveForClient: copyReserveForClient
    };
  }

  public static initFromItem(item: item): OrderItemV2 {
    const orderItem = new OrderItemV2();
    for ( const property of Object.keys(item) ) {
      if ( !property.match('orderItemId|amount|comment') ) {
            orderItem[property] = item[property];
      }
    }
    return orderItem;
  }

  public constructor(init?: Partial<OrderItemV2>) { Object.assign(this, init); }
}
