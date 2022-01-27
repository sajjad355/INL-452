import { DatePipe } from '@angular/common'
import { Component, ElementRef, EventEmitter, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core'
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import { IAngularMyDpOptions } from 'angular-mydatepicker'
import { ItemSourceV2 } from 'app/shared/objects2/ItemSourceV2'
import { InventoryService2 } from 'app/shared/services2/Inventory2.service'
import { ItemSourceService2 } from 'app/shared/services2/ItemSource2.service'
import { ErrorUtil } from 'app/shared/util/ErrorUtil'
import { get, pick, values } from 'lodash'
import { ErrorService } from '../../page/error/error.service'
import { INVENTORY_ITEM_TYPES, OPERATION_NAMES, ORDER_STATUS } from '../../shared/models/Constants'
import { image } from '../../shared/objects/Image'
import { InventoryDetailV2, InventoryV2 } from '../../shared/objects2/InventoryV2'
import { OrderItemV2 } from '../../shared/objects2/OrderItemV2'
import { Address, PurchaseOrderV2 } from '../../shared/objects2/PurchaseOrderV2'
import { UserV2 } from '../../shared/objects2/UserV2'
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service'
import { OrderItemService2 } from '../../shared/services2/OrderItem2.service'
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service'

declare var jsPDF: any

class SearchCriteria {
  searchKey: string
  myRequestedOrdersOnly: boolean
  urgentOrdersOnly: boolean
}

@Component({
  providers: [DatePipe],
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.scss']
})
export class OrderItemComponent implements OnInit, OnChanges {

  // output an event to parent
  @Output() resetView = new EventEmitter()

  // create a reference of input of #displayitem in inventory.component.html
  @ViewChild('orderItemDisplay') orderItemDisplay: ElementRef
  @ViewChild('receiveItemDisplay') receiveItemDisplay: ElementRef
  @ViewChild('setReceivedAmt') setReceivedAmt: ElementRef
  @ViewChild('rejectOrder') rejectOrder: ElementRef
  @ViewChild('eta') eta: ElementRef

  disconnectSlidePanel = false
  searchStatus = false
  search: SearchCriteria = { searchKey: '', myRequestedOrdersOnly : false, urgentOrdersOnly : false }
  orderItemModal: any
  etaModal: any
  openReceiveItemModal: any
  receivedModal: any
  rejectModal: any

  orderItem: OrderItemV2
  editReceiveItem  = false
  // enable to edit the single order item in order item display
  editingOrderItemEnabled: boolean // it will be passed to order item display component
  viewColumn: boolean[]
  columnList: string[]



  // purchase order variables
  paymentTerms: string[] = []
  incoTerm: string[] = []
  purchaseOrder: PurchaseOrderV2
  inputWarning: string[] = []
  billingAndShippingAreEqual = false
  currencyOptions = ['USD', 'CAD', 'EURO', 'GBP']
  currencySigns = ['$', '$', '€', '£']
  companyHeader: string[]
  companyPhone: string[]
  companyEmail:  string[]
  companyHST: string[]
  companyAddress1: string[]
  companyAddress2: string[]
  companyAddress3: string[]
  // taxRates: SettingV2;
  termAndCond = 'akjsdhkjahsdkjahskjdhaksjd'



  // get the inventory data for requests, orders and receives
  displayOrders: OrderItemV2[] = []
  page: number
  orderItemsSelected: OrderItemV2[] = []
  selectedOrder: OrderItemV2 = undefined
  receivedInventoryDetail: InventoryDetailV2
  receivedInventory: InventoryV2
  currentUser: UserV2
  timeFormat = 'ddmmmyyyy'
  receivedDateOptions: IAngularMyDpOptions = { dateFormat: this.timeFormat  }
  today = new Date()
  etaOptions: IAngularMyDpOptions = {
    // todayBtnTxt: 'Today',
    dateFormat: this.timeFormat,
    firstDayOfWeek: 'mo',
    sunHighlight: true,
    inline: false,
    disableUntil: { year: this.today.getFullYear(), month: this.today.getMonth() + 1, day: this.today.getDate() - 1 }
   }
  tabs: string[] = [ORDER_STATUS.REQUESTED, ORDER_STATUS.APPROVED, ORDER_STATUS.ORDERED, ORDER_STATUS.RECEIVED, ORDER_STATUS.REJECTED ]
  currentTab = ''
  isLoading = false
  orderCount: number
  dateFormat: { date: { year: number, month: number, day: number } }
  receivedAmount = 0
  subtypeMissing = false
  rejectComment = ''
  dateFormatString: string
  dateFormatDisplay: string
  OPERATION_NAMES = OPERATION_NAMES
  ORDER_STATUS = ORDER_STATUS
  canManageOrders = false
  canRequestOrder = false
  canReceiveOrder = false

  constructor(public datepipe: DatePipe,
              private errorService: ErrorService,
              private modalService: NgbModal,
              private orderItemService: OrderItemService2,
              private inventoryService: InventoryService2,
              private itemSourceService: ItemSourceService2,
              private settingService: SettingService2,
              private authenticationService: AuthenticationService2) { }

  ngOnInit() {
    this.loadSetting()
    this.currentTab = ORDER_STATUS.REQUESTED
    this.viewColumn = [
      false, // name
      false, // catalog number
      false, // amount/quantity
      false, // backOrderedAmount
      false, // unit
      false, // container size
      false, // supplier
      false, // manufacturer
      false, // project number
      false,  // requester
      false, // receiver
      false,  // status
      false,  // time
      false,  // Urgency
      false, // Comments
      false // status reason
     ]
    this.authenticationService.getCurrentUser().then( currentUser => {
      this.currentUser = UserV2.copy(currentUser)
      this.canManageOrders = UserV2.isAllowedToPerform( this.currentUser, OPERATION_NAMES.MANAGE_ORDERS )
      this.canRequestOrder = UserV2.isAllowedToPerform( this.currentUser, OPERATION_NAMES.REQUEST_ORDERS )
      this.canReceiveOrder = UserV2.isAllowedToPerform( this.currentUser, OPERATION_NAMES.RECEIVE_ORDERS )

      // disable the receive button for not qualified users
      // if(!this.canReceiveOrder){
      //   this.tabs = [ORDER_STATUS.REQUESTED, ORDER_STATUS.APPROVED, ORDER_STATUS.ORDERED, ORDER_STATUS.REJECTED ];
      // }

      this.setupDefaultDisplayColumns()
      this.page = 1
      this.search.searchKey = ''
      this.search.myRequestedOrdersOnly = false
      this.search.urgentOrdersOnly = false
      this.loadPage()
    }, error => {
      ErrorUtil.handleHttpError( error )
      this.errorService.message$ = error.error
    })

  }

  setupDefaultDisplayColumns() {
    this.viewColumn = [true, // name
                       true, // catalog number
                       true, // amount/quantity
                       true, // backOrderedAmount
                       false, // unit
                       false, // container size
                       true, // supplier
                       false, // manufacturer
                       false, // project number
                       true,  // requester
                       false, // receiver
                       false,  // status
                       true,  // time
                       true,  // Urgency
                       false, // Comments
                       false // status reason
                      ]
    this.columnList = ['Name',
                       'Catalog Number',
                      'Amount/Quantity',
                      'Back Ordered Amount',
                      'Unit',
                      'Container Size',
                      'Supplier',
                      'Manufacturer',
                      'Project Number',
                      'Requester',
                      'Receiver',
                      'Status',
                       this.currentTab + ' Time',
                      'Urgent',
                      'Comment(s)',
                      'Status Reason']

  }

  loadSetting() {
    this.paymentTerms =  this.settingService.getSettingValuesAsArray( 'paymentTerms' )
    this.incoTerm = this.settingService.getSettingValuesAsArray( 'incoTerm' )
    this.dateFormatString = this.settingService.getDateFormat( DateFormatType.DatePickerUsage )
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay )
    this.receivedDateOptions.dateFormat = this.dateFormatString
    this.etaOptions.dateFormat = this.dateFormatString
    this.companyHeader = this.settingService.getSettingValuesAsArray('companyName')
    this.companyPhone = this.settingService.getSettingValuesAsArray('companyPhone')
    this.companyEmail = this.settingService.getSettingValuesAsArray('companyEmail')
    this.companyHST = this.settingService.getSettingValuesAsArray('companyHST')
    this.companyAddress1 = this.settingService.getSettingValuesAsArray('companyAddressLine1')
    this.companyAddress2 = this.settingService.getSettingValuesAsArray('companyAddressLine2')
    this.companyAddress3 = this.settingService.getSettingValuesAsArray('companyAddressLine3')
    // this.etaOptions.editableDateField = false;
  }

  asdasd(val) {
    alert(val)
  }

  // this one will run every time the @input changes
  ngOnChanges(change: SimpleChanges) {
    this.editingOrderItemEnabled = false
  }

  ngOnDestroy() {
  }

  getSupplier(purchaseOrder: PurchaseOrderV2): string {
    let supplier = ''
    if (purchaseOrder.orderItemArray && purchaseOrder.orderItemArray[0].supplier) { supplier += purchaseOrder.orderItemArray[0].supplier.trim() + '\n' } else { supplier += 'N/A' }
    if (purchaseOrder.orderItemArray && purchaseOrder.orderItemArray[0].supplierCatalogNumber) { supplier += 'Supplier Cat# ' + purchaseOrder.orderItemArray[0].supplierCatalogNumber + '\n' }
    return supplier
  }

  getAddress(sa: Address) {
    let shippingAddress: string
    shippingAddress = sa.addressLine1.trim() + '\n'
    if (sa.addressLine2 !== undefined && sa.addressLine2.trim() !== '') { shippingAddress += sa.addressLine2.trim() + '\n' }
    shippingAddress += sa.city.trim() + ''
    if (sa.province !== undefined && sa.province.trim() !== '') { shippingAddress += ', ' + sa.province.trim() }
    shippingAddress += '\n' + sa.country.trim()
    if (sa.postalCode !== undefined && sa.postalCode.trim() !== '') { shippingAddress += ' ' + sa.postalCode.trim() + '\n' }
    return shippingAddress
  }

  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index]
  }

  formatMoney(val: number): string {
    if ( !val ) { return '0.0' }
    let format = val.toFixed(2)
    format = format.replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    return format
  }

  loadPage() {
    this.displayOrders = []
    this.orderItemsSelected = []
    this.isLoading = true
    let requestUserParam = 'ANY'
    if ( this.search.myRequestedOrdersOnly) { requestUserParam = this.currentUser.name }
    // this.page = 1;
    this.orderItemService.loadOrders(this.currentTab,
                                      requestUserParam,
                                      this.search.urgentOrdersOnly,
                                      this.page - 1).subscribe(result => {
      console.log('Ordered Items:')
      console.log(result)
      this.displayOrders = result
      this.orderItemService.countOrders(this.currentTab,
                                        requestUserParam,
                                        this.search.urgentOrdersOnly ).subscribe(c => {
        this.orderCount = c
        this.isLoading = false
      }, error => {
        this.isLoading = false
        ErrorUtil.handleHttpError( error )
        this.errorService.message$ = error.error
      })
    }, error => {
      this.isLoading = false
      ErrorUtil.handleHttpError( error )
      this.errorService.message$ = error.error
    })
  }

  searchOrder() {
    this.isLoading = true
    this.searchStatus = true
    if ( this.search.searchKey == undefined || this.search.searchKey == '' ) {
      this.loadPage()
    } else {
      let requestUserParam = 'ANY'
      if ( this.search.myRequestedOrdersOnly) { requestUserParam = this.currentUser.name }

      this.orderItemService.search(this.currentTab,
                                   this.search.searchKey,
                                   requestUserParam,
                                   this.search.urgentOrdersOnly,
                                   this.page - 1).subscribe(result => {
        console.log('Order search results: ')
        console.log(result)
        this.displayOrders = result.slice()
        this.orderItemService.searchCount(this.currentTab,
                                          this.search.searchKey,
                                          requestUserParam,
                                          this.search.urgentOrdersOnly ).subscribe(count => {
          this.orderCount = count
          this.isLoading = false
        }, error => {
          this.isLoading = false
          ErrorUtil.handleHttpError( error )
          this.errorService.message$ = error.error
        })
      }, error => {
        this.isLoading = false
        ErrorUtil.handleHttpError( error )
        this.errorService.message$ = error.error
      })
    }
  }

  onMyRequest() {
    this.search.myRequestedOrdersOnly = !this.search.myRequestedOrdersOnly
    this.page = 1
    this.searchOrder()
  }

  onUrgent() {
    this.search.urgentOrdersOnly = !this.search.urgentOrdersOnly
    this.page = 1
    this.searchOrder()
  }

  openOrderItem(orderItemId: number) {
      this.orderItemService.get( orderItemId ).subscribe(result => {
        this.orderItem = result
        this.orderItemModal = this.modalService.open(this.orderItemDisplay, { backdrop: 'static', size: 'sm' })
      }, error => {
        ErrorUtil.handleHttpError( error )
        this.errorService.message$ = error.error
      })
  }

  reset() {
    this.editingOrderItemEnabled = false
    this.orderItem = undefined
    this.receivedInventoryDetail = undefined
    this.receivedInventory = undefined
    this.receivedAmount = 0
    this.resetView.emit()
    this.page = 1
    this.search.searchKey = ''
    this.search.myRequestedOrdersOnly = false
    this.search.urgentOrdersOnly = false
    this.orderItemsSelected = []
  }

  createNewOrder() {
    this.orderItem = new OrderItemV2()
    this.orderItem.editedBy = this.currentUser.name
    this.orderItem.currency = 'USD'
    this.orderItem.currencyRate = 1
    this.orderItem.modifiedOn = new Date()
    this.orderItem.status = this.ORDER_STATUS.REQUESTED
    this.orderItem.requestUser = this.currentUser
    this.orderItem.requestTime = new Date()
    this.editingOrderItemEnabled = true
    this.orderItemModal = this.modalService.open(this.orderItemDisplay, { backdrop: 'static', size: 'sm' })
  }

  saveOrder(orderItem: OrderItemV2[]) {
    const payload = orderItem
    this.orderItemService.save(payload).then((savedItem: OrderItemV2[]) => {
      let status = ''
      savedItem.map((item: OrderItemV2) => {
        status = item.status
      })
      if (status === ORDER_STATUS.ORDERED) { this.savePurchaseOrder(this.purchaseOrder) }
      this.reset()
      this.searchOrder()
    }).catch( err => {
      ErrorUtil.handleHttpError( err )
      this.errorService.message$ = err.error
    })
  }

  savePurchaseOrder(purchaseOrder: PurchaseOrderV2) {
    purchaseOrder.shippingAddress.locationName = purchaseOrder.shippingAddress.city
    delete purchaseOrder.shippingAddress.addressId
    delete purchaseOrder.billingAddress.addressId
    const payload = purchaseOrder
    this.orderItemService.savePurchaseOrder(payload).then(savedItem => {
      this.generatePurchaseOrder(savedItem)
    }).catch( err => {
      ErrorUtil.handleHttpError( err )
      this.errorService.message$ = err.error
    })
  }

  changeTab(tab: string) {
    this.page = 1
    this.displayOrders = []
    this.orderItemsSelected = []
    this.currentTab = tab
    this.columnList[12] = this.currentTab + ' Time'
    if (this.searchStatus) {
      this.searchOrder()
    } else {
      this.resetSearch()
      this.loadPage()
    }
  }

  resetSearch() {
    this.search.searchKey = ''
    this.search.myRequestedOrdersOnly = false
    this.search.urgentOrdersOnly = false
  }

  cancelSearch(orderSearchContainer) {
    let reload
    if (this.search.searchKey === '' && !this.search.myRequestedOrdersOnly && !this.search.urgentOrdersOnly) {
      reload = false
    } else {
      reload = true
    }
    this.resetSearch()

    if (reload) {
      this.loadPage()
    }

    this.shrinkSearchContainer(orderSearchContainer)
  }

  expandSearchContainer(orderSearchContainer) {
    orderSearchContainer.style.transition = 'all ease-out 0.3s'
    orderSearchContainer.style.width = '500px'
  }

  shrinkSearchContainer(orderSearchContainer) {
    orderSearchContainer.style.transition = 'all ease-in 0.3s'
    orderSearchContainer.style.width = '200px'
  }

  checkSelected(orderItem: OrderItemV2): boolean {
    if ( !orderItem || this.orderItemsSelected.length < 1 ) { return false } else {
      const exists = this.orderItemsSelected.find((item: OrderItemV2) => item.orderItemId === orderItem.orderItemId)
      if (exists) { return true } else { return false }
    }
  }

  toggleOrderItem(orderItem: OrderItemV2) {
    if (orderItem.status !== this.currentTab) { return }
    const checked = this.checkSelected(orderItem)
    if (checked) {
      let filteredItems = []
      filteredItems = this.orderItemsSelected.filter((item: OrderItemV2) => item.orderItemId !== orderItem.orderItemId)
      this.orderItemsSelected = filteredItems
    } else {
      this.orderItemsSelected.push(orderItem)
    }
  }

  setStatusReason(event: any) {
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      this.orderItemsSelected[i].statusReason = event.target.value
    }
  }

  approveRequest() {
    console.log(this.orderItemsSelected)
    if (!this.orderItemsSelected) { return }
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      if (this.orderItemsSelected[i].status != ORDER_STATUS.REQUESTED) { return }
    }
    this.orderItemsSelected.map((item: OrderItemV2) => {
      item.status = ORDER_STATUS.APPROVED
      item.approveTime = new Date()
      item.approveUser = this.currentUser
      item.editedBy = this.currentUser.name
      item.modifiedOn = new Date()
    })
    this.saveOrder(this.orderItemsSelected)
  }

  showRejectModal() {
    if (!this.orderItemsSelected) { return }
    this.rejectModal = this.modalService.open(this.rejectOrder, { backdrop: 'static', size: 'lg' })
  }

  rejectOrderItem() {
    if ( !this.orderItemsSelected ) { return }
    this.orderItemsSelected.map((item: OrderItemV2) => {
      item.status = ORDER_STATUS.REJECTED
      item.rejectUser = this.currentUser
      item.rejectedTime = new Date()
      item.editedBy = this.currentUser.name
      item.modifiedOn = new Date()
    })
    this.saveOrder( this.orderItemsSelected )
  }

  showETA() {
    this.purchaseOrder = new PurchaseOrderV2()
    this.purchaseOrder.orderItemArray = this.orderItemsSelected
    this.purchaseOrder.shipVia = 'FedEx'
    this.purchaseOrder.purchaseOrderId = -1
    this.purchaseOrder.editedBy = this.currentUser.name
    this.purchaseOrder.shippingAddress.editedBy = this.currentUser.name
    this.purchaseOrder.shippingAddress.addressId = -1
    this.purchaseOrder.billingAddress.editedBy = this.currentUser.name
    this.purchaseOrder.billingAddress.addressId = -1

    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      this.orderItemsSelected[i].eta = null
    }

    const supplier = []
    if (this.orderItemsSelected.length < 1) { return }
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      if (this.orderItemsSelected[i].status != ORDER_STATUS.APPROVED) { return }
      if (!supplier.includes(this.orderItemsSelected[i].supplier)) { supplier.push(this.orderItemsSelected[i].supplier) }
      if (this.orderItemsSelected[i].supplier == null && this.orderItemsSelected.length > 1) {
        alert('Multiple items can be marked as ordered at once only if all the items are from same supplier.')
        return
      }
    }
    if (supplier.length > 1) {
      alert('Multiple items can be marked as ordered at once only if all the items are from same supplier.')
      return
    }
    this.etaModal = this.modalService.open(this.eta, { backdrop: 'static', size: 'lg' })
  }

  changeETA(event: any) {
    if (this.orderItemsSelected.length < 1) { return }
    const {date, jsDate, formatted, epoc} = event.singleDate
    if (formatted !== '') {
      for (let i = 0; i < this.orderItemsSelected.length; i++) {
        this.orderItemsSelected[i].eta = new Date(formatted)
      }
    }
  }

  hasEta() {
    let status = true
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      if (!this.orderItemsSelected[i].eta) {
        status = false
        break
      }
    }
    return status
  }

  hasReason() {
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      if (!this.orderItemsSelected[i].statusReason) {
        return false
      }
    }
    return true
  }

  markItemOrdered() {
    if (!this.orderItemsSelected) { return }
    for (let i = 0; i < this.orderItemsSelected.length; i++) {
      if (this.orderItemsSelected[i].status != ORDER_STATUS.APPROVED) { return }
    }
    this.orderItemsSelected.map((item: OrderItemV2) => {
      item.status = ORDER_STATUS.ORDERED
      item.orderTime = new Date()
      item.orderUser = this.currentUser
      item.editedBy = this.currentUser.name
      item.modifiedOn = new Date()
    })

    this.saveOrder(this.orderItemsSelected)
  }

  cancelSelectedOrder() {
    if ( !this.orderItemsSelected ) { return }
    this.orderItemsSelected.map((item: OrderItemV2) => {
      item.status = ORDER_STATUS.CANCELLED
      item.editedBy = this.currentUser.name
      item.modifiedOn = new Date()
    })
    this.saveOrder( this.orderItemsSelected )
  }

  resubmitOrder() {
    if ( !this.orderItemsSelected ) { return }
    this.orderItemsSelected.map((item: OrderItemV2) => {
      item.status = ORDER_STATUS.REQUESTED
      item.requestUser = this.currentUser
      item.requestTime = new Date()
      item.editedBy = this.currentUser.name
      item.modifiedOn = new Date()
    })
    this.saveOrder( this.orderItemsSelected )
  }

  setReceivedAmount( orderItem: OrderItemV2 ) {
    this.selectedOrder = OrderItemV2.copy(orderItem)
    // ensure sub type has been populated for commercial and in house proteins - may not be the case for orders created
    // before sub type was added to OrderItem in release 1.4.9
    this.subtypeMissing = (
       (
          (this.selectedOrder.type == INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN) ||
          (this.selectedOrder.type == INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN)
       ) && (!this.selectedOrder.subtype))
    this.receivedModal = this.modalService.open(this.setReceivedAmt, { backdrop: 'static', size: 'lg' })
  }

  addReceivedItem() {
    if (!this.selectedOrder ) {
      alert( 'Error - received order item was not set!')
      return
    }
    const oi: OrderItemV2 = OrderItemV2.copy( this.selectedOrder )
    // keep a local variable in case the instance var gets reset before function is complete due to
    // all the async stuff happening here
    const rAmount: number = this.receivedAmount

    this.orderItemService.receive(
      this.currentUser,
      oi.orderItemId,
      rAmount
    ).then( savedItem => {
      this.receivedInventory = (savedItem as InventoryV2)
      const inventoryDetailIndex: number = this.receivedInventory.inventoryDetails.length - 1
      this.receivedInventoryDetail = this.receivedInventory.inventoryDetails[inventoryDetailIndex]
      this.openReceiveItemModal = this.modalService.open(this.receiveItemDisplay, { backdrop: 'static', size: 'xl' })
      this.editReceiveItem = true
      // update the display to reflect the order now in received status
      this.searchOrder()
    }).catch( err => {
      ErrorUtil.handleHttpError( err )
      this.errorService.message$ = err.error
    })
  }

  updateReceivedInventoryDetail( item: InventoryV2 ) {
    this.inventoryService.save( item ).then( savedItem => {
      item.inventoryId = (savedItem as InventoryV2).inventoryId
      this.reset()
    }).catch( err => {
      ErrorUtil.handleHttpError( err )
      this.errorService.message$ = err.error
    })
  }

  isAllowedToPerform(user: UserV2, requestedOperation: string) {
    if ( user == undefined ) {
      // The current user hasn't been loaded yet.
      this.authenticationService.getCurrentUser().then( currentUser => {
        user = UserV2.copy(currentUser)
        return UserV2.isAllowedToPerform(user, requestedOperation)
      } )
    } else {
      return UserV2.isAllowedToPerform(user, requestedOperation)
    }
  }

  generatePurchaseOrderId(id) {
    if (!id) { return '000000' } else {
      id = '000000' + id
      return id.slice(id.length - 6, id.length)
    }
  }

  getPurchaseOrder(item) {
    if (typeof item === 'object') { this.generatePurchaseOrder(item) } else { this.fetchPurchaseOrder(item) }
  }

  fetchPurchaseOrder(item) {
    this.orderItemService.getPurchaseOrder(item).subscribe(result => {
      this.generatePurchaseOrder(result)
    }, error => {
      ErrorUtil.handleHttpError( error )
      this.errorService.message$ = error.error
    })
  }

  getItemSourceDetails(purchaseOrder: PurchaseOrderV2) {
    let supplier = ''
    purchaseOrder.orderItemArray.map((item: OrderItemV2) => {
      supplier = item.supplier
    })
    let address = ''
    this.itemSourceService.searchItemSource('Supplier', supplier).subscribe((result: ItemSourceV2[]) => {
      if (result.length > 0) {
        address = this.getAddressString(result[0])
      }
    }, error => {
      ErrorUtil.handleHttpError( error )
      this.errorService.message$ = error.error
    })
    return address
  }

  generatePurchaseOrder(purchaseOrder) {
    const doc = new jsPDF()
    const imgData = image.logo
    const width = doc.internal.pageSize.getWidth()
    const height = doc.internal.pageSize.getHeight()
    const margin = 5
    const emptyHead = [{ title: '', dataKey: 'header' }, { title: '', dataKey: 'content' }]
    doc.addImage(imgData, 'JPEG', margin, 10, 60, 23)
    const headMargin = (((width - (margin * 2)) / 3) * 2) + margin
    const modificationDate = this.datepipe.transform(purchaseOrder.modifiedOn, this.dateFormatDisplay)
    const creationDate = this.datepipe.transform(purchaseOrder.modifiedOn, this.dateFormatDisplay)
    const headData = []

    headData.push({ header: 'Purchase Order #', content: this.generatePurchaseOrderId(purchaseOrder.purchaseOrderId) })
    // headData.push({ header: 'Version #', content: purchaseOrder.quoteNumber });
    headData.push({ header: 'Date Created', content:  creationDate})

    doc.autoTable(emptyHead, headData, {
      theme: 'plain',
      styles: {
        cellPadding: 0,
        fontSize: 9,
        textColor: [0, 132, 183]
      },
      headerStyles: {
        fontStyle: 'normal',
        fontSize: 0
      },
      columnStyles: {
        header: {
          fontStyle: 'bold'
        }
      },
      startY: 17, // false (indicates margin top value) or a number
      pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
      tableWidth: (width - (2 * margin)) / 3, // 'auto', 'wrap' or a number,
      tableLineColor: 200, // number, array (see color section below)
      tableLineWidth: 0,
      showHeader: 'never',
      margin: headMargin,
    })

    doc.setDrawColor(151, 202, 65)
    doc.lines([[width - (2 * margin), 0]], margin, 40)
    let linePointer = 43
    let nextPointer = 43

    const col1Margin = margin
    const col2Margin = margin + (width - (2 * margin)) / 3
    const col3Margin = margin + 2 * ((width - (2 * margin)) / 3)

    let clientBillingAddress = ''
    if ( purchaseOrder.billingAddress ) { clientBillingAddress  = this.getAddress( purchaseOrder.billingAddress ) }

    let clientShippingAddress = ''
    if ( purchaseOrder.shippingAddress ) { clientShippingAddress = this.getAddress( purchaseOrder.shippingAddress ) }

    let supplier: string = this.getSupplier(purchaseOrder)
    let supplierString = ''
    let supplierIsEmpty = true

    purchaseOrder.orderItemArray.map((item: OrderItemV2) => {
      supplierString = item.supplier
      if (item.supplier) { supplierIsEmpty = false }
    })
    this.itemSourceService.searchItemSource('Supplier', supplierString).subscribe((result: ItemSourceV2[]) => {
      if (!supplierIsEmpty) {
        if (result.length > 0) {
          supplier += this.getAddressString(result[0])
        }
      }
      const supplierInfo: string[][] = [ [supplier]]
      doc.autoTable(
        ['Supplier:'], supplierInfo,
        {
          theme: 'plain',
          styles: {
            fontSize: 9,
            textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col1Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2))
        }
      })

      const shipInfo: string[][] = [
        [clientShippingAddress]
      ]
      doc.autoTable(['Ship To:'], shipInfo, {
        theme: 'plain',
        styles: {
          fontSize: 9,
          textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col2Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2))
        }
      })


      doc.autoTable(['Bill To:'], [
        [clientBillingAddress],
      ], {
        theme: 'plain',
        styles: {
          fontSize: 9,
          textColor: 0,
        },
        headerStyles: {
          cellPadding: [2, 0],
          fontStyle: 'bold'
        },
        bodyStyles: {
          cellPadding: 0
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'avoid', // 'auto', 'avoid' or 'always'
        tableWidth: Math.floor((width - (2 * margin)) / 3), // 'auto', 'wrap' or a number,
        tableLineColor: 200, // number, array (see color section below)
        tableLineWidth: 0,
        margin: col3Margin,
        drawRow: function (row, data) {
          nextPointer = Math.max(nextPointer, row.y + (row.height / 2))
        }
      })



      const salesRep = purchaseOrder.salesPerson
      linePointer = nextPointer + 15
      const head = ['SALESPERSON', 'QUOTE NUMBER', 'REQUISITIONER', 'SHIP VIA', 'PAYMENT TERMS', 'SHIPPING TERMS']
      const data = [
        [salesRep,
        purchaseOrder.quoteNumber,
        purchaseOrder.requisitioner,
        purchaseOrder.shipVia,
        purchaseOrder.paymentTerms,
        purchaseOrder.shippingTerms]
      ]

      doc.autoTable(head, data, {
        theme: 'plain',
        styles: {
          halign: 'left',
          valign: 'middle',
          font: 'times',
          fontSize: 9,
          textColor: 0,
          overflow: 'linebreak',
        },
        headerStyles: {
          fillColor: [224, 234, 207]
        },
        // Properties
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
        tableLineColor: [151, 202, 65], // number, array (see color section below)
        tableLineWidth: 0.1,
        margin: margin,
        drawRow: function (row, data) {
          linePointer = row.y + row.height
        }
      })

      // const invoiceData: PrintedInvoice = this.getDataFromLink();

      const header: { title: string, dataKey: string }[] = []
      let totalPrice = 0

      const orderItems = []
      let currency = ''
      let currencySign = ''

      purchaseOrder.orderItemArray && purchaseOrder.orderItemArray.forEach(item => {
        let lineDescription = ''
        if (item.manufacturer) { lineDescription += 'Manufacturer: ' + item.manufacturer + '\n' }
        if (item.name) { lineDescription += 'Name: ' + item.name }
        if (item.containerSize) { lineDescription += '\nUnit Size: ' + item.containerSize }
        if (item.unit) { lineDescription += ' (' + item.unit + ')' }
        // if(item.category) lineDescription += '\nCategory: ' + item.category;
        if (item.type) { lineDescription += '\nItem Type: ' + item.type }
        currency = item.currency ? item.currency : 'USD'
        const currencyIndex = this.currencyOptions.indexOf(currency)
        currencySign = this.currencySigns[currencyIndex]
        orderItems.push({
          description: lineDescription,
          catalogNumber: item['catalogNumber'],
          amount: item['amount'],
          unitPrice: item.unitPrice ? (currencySign + item.unitPrice) : 'N/A',
          extendedPrice: item.unitPrice ? (currencySign + (item.unitPrice * item.amount)) : 'N/A'
        })
        totalPrice += (item.amount ? item.amount : 0) * (item.unitPrice ? item.unitPrice : 0)
      })

      header.push({ title: 'Description', dataKey: 'description' })
      header.push({ title: 'Catalog #', dataKey: 'catalogNumber' })
      header.push({ title: 'Qty', dataKey: 'amount' })
      header.push({ title: 'Price', dataKey: 'unitPrice' })
      header.push({ title: 'Extended Price', dataKey: 'extendedPrice' })

      doc.autoTable(header, orderItems, {
        theme: 'grid', // 'striped', 'grid' or 'plain'
        styles: {
          halign: 'center',
          valign: 'middle',
          font: 'times',
          fontSize: 9,
          textColor: 0,
          overflow: 'linebreak',
          lineColor: [151, 202, 65],
          fillColor: 255
        },
        columnStyles: {
          description: { columnWidth: 'auto', halign: 'left', fillColor: false },
          catalogNumber: { columnWidth: 'auto', halign: 'center', fillColor: false },
          amount: { columnWidth: 10, halign: 'center' },
          unitPrice: { columnWidth: 25, halign: 'center' },
        },
        headerStyles: {
          fillColor: [151, 202, 65]
        },
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
        tableLineColor: [151, 202, 65], // number, array (see color section below)
        tableLineWidth: 0,
        margin: margin,
        drawHeaderRow: function (row, data) {
          row.cells.description.styles.halign = 'left'
        },
        drawRow: function (row, data) {
          // row.cells.description.styles.fontStyle = 'bold';
          linePointer = row.y + row.height
        }
      })

      const totalHead = [{ title: '', dataKey: 'text' }, { title: '', dataKey: 'amount' }]
      const totalData = []
      let rowcount = 0

      totalData.push({ text: 'Subtotal:', amount: currencySign + '' + this.formatMoney(totalPrice) })
      totalData.push({ text: 'Total (' + currency + '):', amount: currencySign + '' + this.formatMoney(totalPrice) })
      rowcount += 2

      doc.autoTable(totalHead, totalData, {
        theme: 'plain',
        styles: {
          halign: 'right',
          valign: 'middle',
          font: 'times',
          fontSize: 9,
          textColor: 0,
          overflow: 'linebreak',
        },
        columnStyles: {
          text: {
            columnWidth: 2 * ((width - (2 * margin)) / 3),
            cellPadding: 0
          },
          amount: {
            columnWidth: ((width - (2 * margin)) / 3)
          }
        },
        // Properties
        startY: linePointer, // false (indicates margin top value) or a number
        pageBreak: 'auto', // 'auto', 'avoid' or 'always'
        tableWidth: width - (2 * margin), // 'auto', 'wrap' or a number,
        tableLineColor: [151, 202, 65], // number, array (see color section below)
        tableLineWidth: 0.1,
        showHeader: 'never',
        margin: margin,
        drawRow: function (row, data) {
          linePointer = row.y + row.height
          if (row.raw.text.startsWith( 'Total (') ) {
            row.cells.text.styles.fontStyle = 'bold'
            row.cells.amount.styles.fontStyle = 'bold'
          }
          if (row.index % 2 == 0) {
            row.cells.text.styles.fillColor = [224, 234, 207]
            row.cells.amount.styles.fillColor = [224, 234, 207]
          }
        }
      })

      doc.setFontSize(8)
      linePointer += 8

      if ( (height - linePointer)  < 50 ) {
        doc.addPage()
        linePointer = 15
      }

      doc.setTextColor(100)
      doc.setFontStyle('bold')
      doc.text('Payment Terms: ', margin, linePointer)

      doc.setFontSize(8)
      doc.setFontStyle('normal')
      doc.text(purchaseOrder.paymentTerms, margin + 25, linePointer)

      linePointer += 5

      doc.setFontSize(9)
      doc.setFontStyle('bold')
      // doc.text('Standard Purchase Order Terms and Conditions:', margin, linePointer);
      doc.textWithLink('Standard Purchase Order Terms and Conditions', margin, linePointer, {url: 'http://somrubioscience.com/page/standard-terms-and-conditions-of-sale'})
      linePointer += 4
      doc.setFontSize(8)
      doc.setFontStyle('normal')


      doc.setFontSize(8)
      doc.setFontStyle('normal')
      doc.text('Please visit our website (http://somrubioscience.com/page/standard-terms-and-conditions-of-sale) for our', margin, linePointer)
      linePointer += 3
      doc.text('standard purchase order terms and condition.', margin, linePointer)

      this.assignPageNum(doc)
      doc.save( this.companyHeader + ' Purchase Order ' + this.generatePurchaseOrderId(purchaseOrder.purchaseOrderId) + ' .pdf')
    }, error => {
      ErrorUtil.handleHttpError( error )
      this.errorService.message$ = error.error
    })
  }

  assignPageNum(doc: any) {
    const totalPages = doc.internal.getNumberOfPages()
    const timestamp = new Date().toString()
    for (let i = 1; i <= totalPages; i++) {
      doc.setPage(i)
      doc.setFontStyle('bold')
      doc.text(200, 295, i + ' of ' + totalPages)
    }
  }

  getAddressString = (address): string => {
    let addressString = ''
    const billingAddress = values(pick(get(address, 'billingAddress'),
    [
      'addressLine1',
      'addressLine2',
      'city',
      'province',
      'country',
      'postalCode'
    ]))

    addressString = billingAddress?.join('\n')

    return addressString
  }
}
