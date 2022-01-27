import { Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { ActivatedRoute, Event, NavigationStart, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Chicken } from 'app/shared/objects/Chicken';
import { Eggtransfer } from 'app/shared/objects/eggtransfer';
import { Enum } from 'app/shared/objects/Enum';
import { ResponsiveService } from 'app/shared/services/responsive.service';
import { InitService } from 'app/shared/services2/Init.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { take } from 'rxjs/operators';
import { OPERATION_NAMES } from '../shared/models/Constants';
import { item, itemdetail } from '../shared/objects/item';
import { KitV2 } from '../shared/objects2/KitV2';
import { OrderItemV2 } from '../shared/objects2/OrderItemV2';
import { ProductV2 } from '../shared/objects2/ProductV2';
import { SopV2 } from '../shared/objects2/SopV2';
import { UserV2 } from '../shared/objects2/UserV2';
import { AuthenticationService2 } from '../shared/services2/Authenticate2.service';
import { OrderItemService2 } from '../shared/services2/OrderItem2.service';

@Component({
  selector: 'app-main',
  providers: [ResponsiveService],
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, OnChanges {
  displayMore: boolean = false;
  displayMenuBar: boolean = false;
  dashboardQuote: any



  isLoading = false;
  routeDestinations = Enum.getDashboardRouteDestinations();
  showmultiplerequests: boolean;
  isShoworderItem: boolean;
  orderitem: OrderItemV2;
  showSop: boolean;
  displaySopId : any;
  displaySop: any

  showKit: boolean;
  kit: KitV2;
  showProduct: boolean;
  product: ProductV2;
  requests: OrderItemV2[];
  //catalog window
  catalogViewMode = 'search';
  // catFocus: boolean;
  enableEditCatSetting: boolean;
  // User
  user: UserV2;
  needChangePass: boolean;
  // User privileges
  canViewClients : boolean = false;
  canViewSales  : boolean= false;
  canViewUserHistory : boolean = false;
  canViewUsers : boolean = false;
  canGenerateCatalogs : boolean = false;
  canViewInventory : boolean = false;
  canViewImmunizingSchedules : boolean = false;
  canViewSops : boolean = false;
  canViewKits : boolean = false;
  canViewWorksheets: boolean = false;
  canViewProducts : boolean = false;
  canViewOrders : boolean = false;
  canViewEquipment : boolean = false;
  canViewConfig : boolean = false;
  canViewCatalogs: boolean = false;
  canViewFolder : boolean = false;
  canAccessFMS: boolean = false;
  showuser = false;
  currentTab = 'dboard';
  showAll = false;
  lock = false;
  item: item;
  newitemdetail: itemdetail;
  showItem: boolean;
  showItemdetail: boolean;
  edititem: boolean;
  edititemdetail: boolean;
  eggtransfer: Eggtransfer;
  // Egg Inventory input
  showChicken = false;
  chicken: Chicken;
  public isTablet: Boolean;
  showMenu= false;

  constructor(
    private auth: AuthenticationService2,
    private router: Router,
    private modalService: NgbModal,
    private responsiveService: ResponsiveService,
    private orderItemService : OrderItemService2,
    private route: ActivatedRoute,
    private initeService: InitService){}

  ngOnInit() {
    // Call APIs to initialize some sort of data before Intellilab's main page is loaded
    this.isLoading = true;
    this.initeService.initSetting().subscribe((settings)=>{
      this.initeService.saveSetting(settings)
      console.log('System succeeds in initializing settings')
      this.initeService.initLocation().subscribe((locations) => {
        this.initeService.saveLocation(locations)
        console.log('System succeeds in initializing locations')
        this.initeService.initEquipment().subscribe((equipments) => {
          this.initeService.saveEquipment(equipments)
          console.log('System succeeds in initializing equipments')
          console.log('IntelliLab data initialization is all done')
          this.isLoading = false;
        })
      })
    })

    if (window.innerWidth <= 1000 && window.innerWidth >= 768){
      this.isTablet = true;
    }
    this.checkLoggedIn();

    const orderId =  this.route.snapshot.params['id'];
    // if we have an order id, we arrived here from a deep link such as /main/orders/123
    if ( orderId ) {
      this.openOrder( orderId );
    }
  }


  checkLoggedIn() {
    const c = this.auth.getCurrentUser();
    if ( c !== undefined ) {
      c.then( aUser => {
        this.user = UserV2.copy(aUser);

        if (this.user && this.user.userRoles) {
          this.canViewClients = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_CLIENTS);
          this.canViewSales = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_SALES);
          this.canViewUserHistory = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_USER_HISTORY);
          this.canViewUsers = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_USERS);
          this.canGenerateCatalogs = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.GENERATE_CATAOLOGS);
          this.canViewInventory = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_INVENTORY);
          this.canViewImmunizingSchedules = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_IMMUNIZING_SCHEDULES);
          this.canViewSops = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_SOPS);
          this.canViewKits = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_KITS);
          this.canViewWorksheets = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_WORKSHEETS);
          this.canViewProducts = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_PRODUCTS);
          this.canViewOrders = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_ORDERS);
          this.canViewEquipment = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_EQUIPMENT);
          this.canViewConfig =  UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_CONFIG_AND_LOCATIONS);
          this.canViewCatalogs = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_CATALOGS);
          this.canViewFolder = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.VIEW_EQUIPMENT);
          this.canAccessFMS = UserV2.isAllowedToPerform(this.user, OPERATION_NAMES.ACCESS_FMS)
          console.log(`canAccessFMS: ${this.canAccessFMS}`)
        } else {
          console.log('The current user has no role(s)!');
        }
      });
    } else {
      this.router.navigate(['/login']).then(() => {});
    }
  }

  openOrder(orderId : number) {
    const getOrder = this.orderItemService.get(orderId).pipe(take(1));
    if (!getOrder) {
      alert( 'Order not found!');
      return;
    }

    getOrder.subscribe(anOrderedItem => {
      this.setsingleorder( anOrderedItem );
    }, error => {
      ErrorUtil.handleHttpError( error );
    });


  }





  // Routing from dashboard component to another component
  dashboardRoute(route: {fromTable: number, object: any}) {
    this.reset();
    switch (route.fromTable) {
      case this.routeDestinations.inv_expired:
      case this.routeDestinations.inv_stock:
        this.showItemdetail = true;
        this.newitemdetail = route.object;
        this.currentTab = 'inven';
        // console.log('Selected dashborad inven item object')
        // console.log(this.newitemdetail)
        break;
      // case this.routeDestinations.inv_stock:
      //   this.requests = [OrderItemV2.initFromItem(route.object)];
      //   this.showmultiplerequests = true;
      //   this.currentTab = 'order';
      //   break;
      case this.routeDestinations.imm_reminder:
        this.currentTab = 'egg inventory';
        break;
      case this.routeDestinations.quotes_expired:
      case this.routeDestinations.quotes_expireSoon:
        this.currentTab = 'sale';
        this.dashboardQuote = route.object
        break;
      case this.routeDestinations.SOP_toApprove:
        this.showSop = true;
        // this.displaySopId = route.object;
        this.displaySop = route.object;
        this.currentTab = 'sop';
        break;
      case this.routeDestinations.KIT_toReview:
      case this.routeDestinations.KIT_toApprove:
        this.showKit = true;
        this.kit = route.object;
        this.currentTab = 'kit';
        break;
      default:
        console.log('Failed to route from Main', route);
        break;
    }
  }

  ngOnChanges(change: SimpleChanges) {
  }

  onResize() {
    this.responsiveService.getTabletStatus().subscribe(isTablet => {
      this.isTablet = isTablet;
    });
  }

  changeMenuStatus(){
    if (this.showMenu == true){
      this.showMenu = false;
      this.hidemenu();
    }
    else{
      this.showMenu = true;
      this.showmenu();
    }
  }

  showmenu(){
    document.getElementById('menu').style.width = '90px';
    document.getElementById('menu').style.transition = 'linear 0.5s';
  }

  hidemenu(){
    document.getElementById('menu').style.width = '20px';
    document.getElementById('menu').style.transition = 'linear 0.5s';
  }

  open(content) {
    this.modalService.open(content, { size: 'sm' }).result.then((result) => {
    }, (reason) => {
    });
  }

  //get emit from dashboard by clicking on item of inventory to open this item
  setProduct(param) {
    const id = param.oldProductId
    this.router.navigate([`/main/products/${id}`])
  }

  clearProduct() {
    this.showProduct = false;
    this.product = undefined;
  }

  setKit(param) {
    const id = param.salesItemId
    this.router.navigate([`/main/kits/${id}`])
  }

  openorderrequests(requests) {
    if (requests == undefined) return;
    this.showmultiplerequests = true;
    this.requests = requests;
  }

  transtoitem(event){
    this.eggtransfer = event.eggtransfer;
    this.item = event.item;
    this.showItem = true;
    this.edititem = true;
  }
  transtoitemdetail(event){
    this.eggtransfer = event.eggtransfer;
    this.newitemdetail = event.itemdetail;
    this.showItemdetail = true;
    this.edititemdetail = true;
  }

  changetoSop(sop : SopV2) {
    this.showSop = true;
    this.displaySopId = sop.sopId;
  }

  reset() {
    this.showItem = false;
    this.item = undefined;
    this.showSop = false;
    this.displaySopId = undefined;
    this.isShoworderItem = false;
    this.orderitem = undefined;
    this.showmultiplerequests = false;
    this.requests = undefined;
    this.showItemdetail = false;
    this.newitemdetail = undefined;
    this.eggtransfer = undefined;
    this.showKit = false;
    this.kit = undefined;
    this.edititem = false;
    this.edititemdetail = false;
    this.showChicken = false;
    this.chicken = undefined;
  }

  getrequests(requests) {
    this.requests = requests;
  }

  //get emit from dashboard by clicking on orderItem of orders to open this orderItem
  setsingleorder(orderitem) {
    this.isShoworderItem = true;
    this.orderitem = orderitem;
    this.currentTab = 'order';
  }

  signout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  // expandMenuBar(menuBar, doubleArrowBtn){
  //   this.displayMenuBar = !this.displayMenuBar
  //   this.displayMore = this.displayMenuBar
  //   if(this.displayMenuBar){
  //     menuBar.style.transition = 'all ease-out 0.3s'
  //     menuBar.style.height = '80px'
  //     doubleArrowBtn.style.transform = 'rotate(180deg)'
  //     doubleArrowBtn.style['background-color'] = 'rgba(0, 0, 0, 0.7)'
  //     doubleArrowBtn.style.color = 'white'
  //     doubleArrowBtn.style.top = '90px'
  //     doubleArrowBtn.style['border-radius'] = '96px'
  //     doubleArrowBtn.style.height = '96px'
  //   }else{
  //     menuBar.style.transition = 'all ease-in 0.3s'
  //     menuBar.style.height = '0px'
  //     doubleArrowBtn.style.top = '0px'
  //     doubleArrowBtn.style.transform = 'rotate(0deg)'
  //     doubleArrowBtn.style.top = '0px'
  //     doubleArrowBtn.style['border-radius'] = '0  0 0.5rem 0.5rem'
  //     doubleArrowBtn.style.color = 'white'
  //     doubleArrowBtn.style.height = '20px'
  //   }

  // }

}
