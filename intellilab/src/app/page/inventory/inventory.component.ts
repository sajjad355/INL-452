import { Component, ElementRef, OnChanges, OnInit, SimpleChanges, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import exportFromJSON from 'export-from-json';
import { Subject } from 'rxjs';
import * as XLSX from 'xlsx';
import { ErrorService } from '../../page/error/error.service';
import { COMMERCIAL_TYPES, INVENTORY_ITEM_TYPES, IN_HOUSE_TYPES, OPERATION_NAMES } from '../../shared/models/Constants';
import { InventoryDetailV2, InventoryV2 } from '../../shared/objects2/InventoryV2';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { InventoryService2 } from '../../shared/services2/Inventory2.service';
import { LocationService2 } from '../../shared/services2/Location2.service';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';







class SearchCriteria {
  searchValue: string;
  searchSubLocation: string;
  searchBy: string;
  active: boolean;
}

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class InventoryComponent implements OnInit, OnChanges {


  @ViewChild('checkItem') checkItem: ElementRef;
  @ViewChild('reconItem') reconItem: ElementRef;
  @ViewChild('ItemLog') ItemLog: ElementRef;
  @ViewChild('openconjugate') openconjugate: ElementRef;
  @ViewChild('compare') compare: ElementRef;

  @ViewChild('selectCommercialProteinSubtype') selectCommercialProteinSubtype: ElementRef;
  @ViewChild('selectInHouseProteinSubtype') selectInHouseProteinSubtype: ElementRef;

  search: SearchCriteria = { searchValue: '', active : true, searchBy : 'like', searchSubLocation : '' };
  // items: InventoryV2[] = [];
  items: any[] = [];
  importInven: any[] = []
  itemCount: number;
  currentUser: UserV2;
  page: number;
  viewColumn: boolean[];
  columnList: string[];
  inventoryDetailIdSelected : number = 0;
  subcolspan: number;
  isLoading: boolean;
  checkOutModal: any;
  reconModal : any;
  unitList: string[];
  locations: LocationV2[] = [];
  subLocations: LocationV2[] = [];
  projectNumbers: string[] = [];
  dateFormat: string;
  dateFormatDisplay: string;
  itemTypeOptions: string[];
  INVENTORY_ITEM_TYPES = INVENTORY_ITEM_TYPES;
  COMMERCIAL_TYPES = COMMERCIAL_TYPES;
  IN_HOUSE_TYPES  = IN_HOUSE_TYPES;
  commercialSubtypes = [
    COMMERCIAL_TYPES.UNCONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.CONJUGATED_PROTEIN,
    COMMERCIAL_TYPES.ENZYME,
    COMMERCIAL_TYPES.UNCONJUGATED_ANTIBODY,
    COMMERCIAL_TYPES.CONJUGATED_ANTIBODY
  ];
  inHouseSubtypes = [
    IN_HOUSE_TYPES.CONJUGATED_PROTEIN,
    IN_HOUSE_TYPES.UNCONJUGATED_ANTIBODY,
    IN_HOUSE_TYPES.CONJUGATED_ANTIBODY
  ];
  selectedItem: InventoryV2;
  selectedItemDetail : InventoryDetailV2;
  enableEditing = false;
  isNewItem: boolean;
  isNewItemDetail: boolean = false

  showItem = false;
  showItemDetail = false;
  companyName : string;
  canManageInventory: boolean;
  attSummary = []

  public ARO: Subject<{refresh: boolean}>; // ARO stands for attachment refresh observable, which will be responsible for refreshing the current inventory page after a new attachment is uploaded

  constructor(private modalService: NgbModal,
              private errorService: ErrorService,
              private inventoryService: InventoryService2,
              private settingService: SettingService2,
              private locationService: LocationService2,
              private authenticationservice: AuthenticationService2,
              public attService: AttachmentService,
              private route: ActivatedRoute
              ) { }



  ngOnInit() {
    this.handleParam()
    this.loadSetting();
    const getcurrentuser = this.authenticationservice.getCurrentUser();
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentUser = UserV2.copy(_);

        this.canManageInventory = false;
        if (this.currentUser && this.currentUser.userRoles) {
          this.canManageInventory = UserV2.isAllowedToPerform(this.currentUser, OPERATION_NAMES.MANAGE_INVENTORY);
        }
      });
    }
    this.page = 1;
    // this.loadPage();
    this.viewColumn = [true,
                       true,
                       true,
                       false,
                       true,
                       true,
                       true,
                       true,
                       false,
                       true,
                       false,
                       false,
                       false,
                       false,
                       false ];
    this.columnList = ['Item Name',
                       'Manufacturer Catalog #',
                       'Item Type',
                       'Supplier Name',
                       'Manufacturer',
                       'Amount/Quantity',
                       'Unit',
                       'Container Size',
                       'In Use',
                       'Unit Price (USD)',
                       'Quantity Threshold',
                       'Last Modify',
                       'Comment(s)',
                       'Active',
                       'Item Sub Type'];

    this.subcolspan = 2;
    this.viewColumn.forEach(v => {
      if (v) this.subcolspan++;
    });

    // this.getAttSummary()
    // this.ARO = this.attService.getARO()
    // this.ARO.subscribe(res => this.getAttSummary())

    this.loadPage()
  }

  handleParam() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")
      const inventoryId = Number(idParam)

      inventoryId !== 0 && this.loadItem(inventoryId)
    })
  }

  getAttSummary(){
    this.attService.getAttSummary('Inventory').subscribe(res => {
      this.attSummary = res.summary
      this.loadPage()
    }, err => {
      console.error(`Error message in the function call of getAttSummary() in ventory.component.ts: ${err.error.msg}`)
      console.log('Initialize attachment summary list with empty array')
      this.attSummary = []
      this.loadPage()
    })
  }

  displayFn(inventoryId){
    const result = this.attSummary.find(item => item.fileElement.name.split('-')[1] == inventoryId) // use == to compare number with string
    return result? result.numOfAtt : 0
  }

  loadSetting() {
    this.itemTypeOptions = this.settingService.getSettingValuesAsArray( 'inventoryItemType' );
    this.projectNumbers = this.settingService.getSettingValuesAsArray( 'ordersProjectList'  );
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
    this.unitList = [];
    const volUnit : string[] = this.settingService.getSettingValuesAsArray( 'volumeUnit' );
    volUnit.forEach(v => { this.unitList.push(v); });
    const massUnit : string[] = this.settingService.getSettingValuesAsArray(  'massUnit' );
    massUnit.forEach(m => { this.unitList.push(m); });
    const otherUnit : string[] = this.settingService.getSettingValuesAsArray( 'otherUnit' );
    otherUnit.forEach(o => { this.unitList.push(o); });
    this.locations = this.locationService.getAllLocations();
    this.subLocations = [];
    this.companyName = this.settingService.getSettingValuesAsArray( 'companyName')[0];
  }

  showSublocations(locationId: number){
    this.subLocations = [];
    const targetlocation = this.locations.find(x => x.locationId == locationId);
    if (targetlocation == undefined) return;
    targetlocation.subLocations.forEach(sl => {
      this.subLocations.push(sl);
    });
  }

  loadItem( inventoryId : number ) {
    this.inventoryService.get( inventoryId ).subscribe(result => {
        this.selectedItem = result;
        this.showItem = true;
        this.showItemDetail = false;
        this.isNewItem = false;
        this.enableEditing = false;
        this.inventoryDetailIdSelected = 0;
        this.attService.init('Inventory', 'inventoryId-' + inventoryId)
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  loadItemDetail( inventoryId : number, inventoryDetailId : number ) {
    this.isNewItemDetail = false
    this.inventoryService.get( inventoryId ).subscribe(result => {
      this.selectedItem = result;
      this.selectedItemDetail = this.selectedItem.inventoryDetails.find( x => x.inventoryDetailId == inventoryDetailId )
      this.showItem = false;
      this.showItemDetail = true;
      this.isNewItem = false;
      this.enableEditing = false;
      this.inventoryDetailIdSelected = 0;
  }, error => {
    this.isLoading = false;
    ErrorUtil.handleHttpError( error );
    this.errorService.message$ = error.error;
  });
  }


  ngOnChanges(change: SimpleChanges) {
  }

  ngAfterViewInit() {
  }


  loadPage() {
    this.isLoading = true;
    this.items = [];
    this.inventoryService.getAllItemsByPage(this.page - 1).subscribe(result => {
      const temp = []
          result.forEach(item => {
            if(item.inventoryDetails && item.inventoryDetails.length > 0){
              const filterResult = item.inventoryDetails.filter(detail => detail.amount > 0 || detail.numberInUse > 0)
              if(filterResult.length !== item.inventoryDetails.length){
                temp.push(item.name)
              }
              delete item.inventoryDetails
              item.inventoryDetails = filterResult
            }
          })
          this.items = result;
          console.log('Filter out used up LOT items for inventory: ')
          console.log(temp)
      this.inventoryService.getCount().subscribe(c => {
         this.itemCount = c;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
      this.isLoading = false;
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  exportAsCSV(){
    this.inventoryService.getAllItems().subscribe(items => {
      // remove item details for all items in the array

      const data = []
      items.forEach(item => {
        delete item.inventoryDetails
        data.push({
          "Active": item.active ? "true" : "false",
          "Amount": item.amount ? item.amount + '' : 'N/A',
          "Catalog Number": item.catalogNumber ? item.catalogNumber + '' : 'N/A',
          "Category": item.category ? item.category + '' : 'N/A',
          "Checkout Denomination": item.checkoutDenomination ? item.checkoutDenomination + '' : 'N/A',
          "Chemical Abstracts ServiceNumber": item.chemicalAbstractsServiceNumber ? item.chemicalAbstractsServiceNumber + '' : 'N/A',
          "Clonality": item.clonality ? item.clonality + '' : 'N/A',
          "Comment": item.comment ? item.comment + '' : 'N/A',
          "Compound Name": item.compoundName ? item.compoundName + '' : 'N/A',
          "Conjugation Type": item.conjugationType ? item.conjugationType + '' : 'N/A',
          "Container Size": item.containerSize ? item.containerSize + '' : 'N/A',
          "Denomination Unit": item.denominationUnit ? item.denominationUnit + '' : 'N/A',
          "Drug Type": item.drugType ? item.drugType + '' : 'N/A',
          "Edited By": item.editedBy ? item.editedBy + '' : 'N/A',
          "Host": item.host ? item.host + '' : 'N/A',
          "Inventory ID": item.inventoryId ? item.inventoryId + '' : 'N/A',
          "Manufacturer": item.manufacturer ? item.manufacturer + '' : 'N/A',
          "Manufacturer Link": item.manufacturerLink ? item.manufacturerLink + '' : 'N/A',
          "Modified On": item.modifiedOn ? this.dateStamp(item.modifiedOn) + `*${item.modifiedOn}` : 'N/A',
          "MolarChallengeRatio": item.molarChallengeRatio ? item.molarChallengeRatio + '' : 'N/A',
          "Molecular Weight": item.molecularWeight ? item.molecularWeight + '' : 'N/A',
          "Name": item.name ? item.name + '' : 'N/A',
          "Number In Use": item.numberInUse ? item.numberInUse + '' : 'N/A',
          "Old Inventory ID": item.oldInventoryId ? item.oldInventoryId + '' : 'N/A',
          "Pack Denomination": item.packDenomination ? item.packDenomination + '' : 'N/A',
          "Quantity Threshold": item.quantityThreshold ? item.quantityThreshold + '' : 'N/A',
          "Subtype": item.subtype ? item.subtype + '' : 'N/A',
          "Supplier": item.supplier ? item.supplier + '' : 'N/A',
          "Supplier Catalog Number": item.supplierCatalogNumber ? item.supplierCatalogNumber + '' : 'N/A',
          "Supplier Link": item.supplierLink ? item.supplierLink + '' : 'N/A',
          "Trade Name": item.tradeName ? item.tradeName + '' : 'N/A',
          "Type": item.type ? item.type + '' : 'N/A',
          "Unit": item.unit ? item.unit + '' : 'N/A',
          "Unit Price": item.unitPrice ? item.unitPrice + '' : 'N/A'

        })
        // data.push(JSON.stringify(item, this.replacer))
      })


      const fileName = 'Inventory_' + this.dateStamp()
      const exportType = 'csv'

      exportFromJSON({ data, fileName, exportType })

    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
    });
  }

  dateStamp(date?){
    let newDate
    if(date){
      newDate = new Date(date);
    }else{
      newDate = new Date()
    }

    // The month count starts with 0 (Jan), up to 11 (Dec).
    const month = newDate.getMonth() + 1;
    return newDate.getFullYear() + "/" + month + "/" + newDate.getDate();
  }

  // replacer(key, value){
  //   if (typeof value !== 'string') {
  //     console.log('not string value:')
  //     console.log(value)
  //     console.log('not string value type:')
  //     console.log(typeof value)

  //     if(value !== null && value !== undefined){
  //       switch(typeof value){
  //         case 'object':
  //           return value
  //           break;

  //         case 'number':
  //           return value.toString()
  //           break;

  //         case 'boolean':
  //           return value ? 'true' : 'false'
  //           break;
  //       }
  //     }else{
  //       return 'N/A'
  //     }

  //   }
  //   return value
  // }

  onFileChange(event: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(event.target);
    if (target.files.length !== 1) {
      throw new Error('Cannot use multiple files');
    }
    const reader: FileReader = new FileReader();
    reader.readAsBinaryString(target.files[0]);
    reader.onload = (e: any) => {
      /* create workbook */
      const binarystr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(binarystr, { type: 'binary' });

      /* selected the first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];

      /* save data */
      const data:any = XLSX.utils.sheet_to_json(ws); // to get 2d array pass 2nd parameter as object {header: 1}
      data.forEach(item => {
        this.importInven.push({
          active: item.Active === 'true' ? true : false,
          amount: item.Amount !== 'N/A' ? +item.Amount : null,
          catalogNumber: item['Catalog Number'] !== 'N/A' ? item['Catalog Number'] : null,
          category: item.Category !== 'N/A' ? item.Category : null,
          checkoutDenomination: item['Checkout Denomination'] !== 'N/A' ? +item['Checkout Denomination'] : null,
          chemicalAbstractsServiceNumber: item['Chemical Abstracts ServiceNumber'] !== 'N/A' ? item['Chemical Abstracts ServiceNumber'] : null,
          clonality: item['Clonality'] !== 'N/A' ? item['Clonality'] : null,
          comment: item['Comment'] !== 'N/A' ? item['Comment'] : null,
          compoundName: item['Compound Name'] !== 'N/A' ? item['Compound Name'] : null,
          conjugationType: item['Conjugation Type'] !== 'N/A' ? item['Conjugation Type'] : null,
          containerSize: item['Container Size'] !== 'N/A' ? +item['Container Size'] : null,
          denominationUnit: item['Denomination Unit'] !== 'N/A' ? item['Denomination Unit'] : null,
          drugType: item['Drug Type'] !== 'N/A' ? item['Drug Type'] : null,
          editedBy: item['Edited By'] !== 'N/A' ? item['Edited By'] : null,
          host: item['Host'] !== 'N/A' ? item['Host'] : null,
          inventoryId: item['Inventory ID'] !== 'N/A' ? +item['Inventory ID'] : null,
          manufacturer: item['Manufacturer'] !== 'N/A' ? item['Manufacturer'] : null,
          manufacturerLink: item['Manufacturer Link'] !== 'N/A' ? item['Manufacturer Link'] : null,
          modifiedOn: item['Modified On'] !== 'N/A' ? +(item['Modified On'].split('*')[1]) : null,
          molarChallengeRatio: item['MolarChallengeRatio'] !== 'N/A' ? item['MolarChallengeRatio'] : null,
          molecularWeight: item['Molecular Weight'] !== 'N/A' ? +item['Molecular Weight'] : null,
          name: item['Name'] !== 'N/A' ? item['Name'] : null,
          numberInUse: item['Number In Use'] !== 'N/A' ? +item['Number In Use'] : null,
          oldInventoryId: item['Old Inventory ID'] !== 'N/A' ? +item['Old Inventory ID'] : null,
          packDenomination: item['Pack Denomination'] !== 'N/A' ? +item['Pack Denomination'] : null,
          quantityThreshold: item['Quantity Threshold'] !== 'N/A' ? +item['Quantity Threshold'] : null,
          subtype: item['Subtype'] !== 'N/A' ? item['Subtype'] : null,
          supplier: item['Supplier'] !== 'N/A' ? item['Supplier'] : null,
          supplierCatalogNumber: item['Supplier Catalog Number'] !== 'N/A' ? item['Supplier Catalog Number'] : null,
          supplierLink: item['Supplier Link'] !== 'N/A' ? item['Supplier Link'] : null,
          tradeName: item['Trade Name'] !== 'N/A' ? item['Trade Name'] : null,
          type: item['Type'] !== 'N/A' ? item['Type'] : null,
          unit: item['Unit'] !== 'N/A' ? item['Unit'] : null,
          unitPrice: item['Unit Price'] !== 'N/A' ? +item['Unit Price'] : null,
        })
      })
      console.log('Converted data from excel')
      console.log(this.importInven); // Data will be logged in array format containing objects
    };
 }

  clearSearchCriteria() {
    this.search.searchValue = '';
    this.search.searchSubLocation = '';
  }

  searchInventory() {
    if ( this.search.searchValue == '' ) {
      this.page = 1;
      this.loadPage();
    }
    else if ( this.search.searchBy != 'subLocation' ) {
      this.isLoading = false;
      this.inventoryService.searchForCriterionByPage(
        this.search.searchBy, this.search.searchValue, this.page - 1, this.search.active ).subscribe(result => {
          const temp = []
          result.forEach(item => {
            if(item.inventoryDetails && item.inventoryDetails.length > 0){
              const filterResult = item.inventoryDetails.filter(detail => detail.amount > 0 || detail.numberInUse > 0)
              if(filterResult.length !== item.inventoryDetails.length){
                temp.push(item.name)
              }
              delete item.inventoryDetails
              item.inventoryDetails = filterResult
            }
          })
          this.items = result;
          console.log('Filter out used up LOT items for inventory: ')
          console.log(temp)
          this.inventoryService.getCountForCriterion(this.search.searchBy, this.search.searchValue, this.search.active).subscribe(count => {
            this.itemCount = count;
            this.isLoading = false;
          }, error => {
            this.isLoading = false;
            ErrorUtil.handleHttpError( error );
            this.errorService.message$ = error.error;
          });
      }, error => {
        this.isLoading = false;
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
    else {
      this.inventoryService.searchByLocationAndSubLocationByPage(
        this.search.searchValue, this.search.searchSubLocation, this.page - 1, this.search.active).subscribe(result => {
          // this.items = result;
          const temp = []
          result.forEach(item => {
            if(item.inventoryDetails && item.inventoryDetails.length > 0){
              const filterResult = item.inventoryDetails.filter(detail => detail.amount > 0 || detail.numberInUse > 0)
              if(filterResult.length !== item.inventoryDetails.length){
                temp.push(item.name)
              }
              delete item.inventoryDetails
              item.inventoryDetails = filterResult
            }
          })
          this.items = result;
          console.log('Filter out used up LOT items for inventory: ')
          console.log(temp)
          this.inventoryService.getCountForLocationAndSubLocation(
            this.search.searchValue, this.search.searchSubLocation, this.search.active).subscribe(count => {
              this.itemCount = count;
              this.isLoading = false;
          }, error => {
            this.isLoading = false;
            ErrorUtil.handleHttpError( error );
            this.errorService.message$ = error.error;
          });
      }, error => {
        this.isLoading = false;
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }

  reset() {
    this.enableEditing = false;
    this.showItem = false;
    this.selectedItem = undefined;
    this.selectedItemDetail = undefined;
    this.showItemDetail = false;
    this.isNewItem = false;
  }





  addNewItemDetail() {
    this.showItem = false;
    this.selectedItemDetail = new InventoryDetailV2();
    this.selectedItemDetail.name = this.selectedItem.name;
    this.selectedItemDetail.active = true;
    this.selectedItemDetail.editedBy = this.currentUser.name;
    let today = new Date();
    let year = today.getFullYear();
    let oneYearFromToday = new Date( year + 1, today.getMonth(), today.getDate() );
    this.selectedItemDetail.modifiedOn = today;
    this.selectedItemDetail.receivedDate = today;
    this.selectedItemDetail.expiryDate = oneYearFromToday;
    this.selectedItemDetail.reserve = false;
    this.selectedItemDetail.reconstituted = false;
    this.selectedItemDetail.amount = 0;
    this.selectedItemDetail.numberInUse = 0;

    this.enableEditing = true;
    this.showItemDetail = true;
    this.isNewItemDetail = true;
  }



  updateItem( item : any) {
    // go through details and tally amounts so as to keep amount on inventory in sync with
    // individual detail amount totals
    let amountTotal : number = 0;
    let numberInUse : number = 0;
    item.inventoryDetails.forEach(detail => {
      amountTotal = +amountTotal + (+detail.amount);    // without the + in front of variable is being treated as string
      numberInUse = +numberInUse + (+detail.numberInUse);
    });

    if(item.vialCheckOut){
      numberInUse = numberInUse + item.vialCheckOut
      const id = item.vialId
      console.log(`Record vial checkout amount for: ${item.vialCheckOut}`)

      item.inventoryDetails.forEach(element => {

        if(element.inventoryDetailId === id){
          element.numberInUse = element.numberInUse + item.vialCheckOut
          console.log(`Modify numberInUse for inventory with detailID: ${id} (update --> ${element.numberInUse})`)
        }
      })
      delete item.vialId
      delete item.vialCheckOut
    }
    item.amount = amountTotal;
    item.numberInUse = numberInUse;

    this.inventoryService.save( item ).then( savedItem => {
      item.inventoryId = (savedItem as InventoryV2).inventoryId;
      this.reset();
      this.searchInventory();
    }).catch( err => {
      ErrorUtil.handleHttpError( err );
      this.errorService.message$ = err.error;
    });
  }


  openCheckOut(){
    this.checkOutModal = this.modalService.open(this.checkItem, { backdrop: 'static', size: 'sm' });
  }

  openReconstitute() {
    this.reconModal = this.modalService.open(this.reconItem, { backdrop: 'static', size: 'sm' });
  }


  newItem(type: string) {
    switch (type) {
      case INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN:
      case INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN:
        this.showProteinSubtypes(type);
        break;
      default:
        this.showNewItemScreen(type, null);
        break;
    }
  }

  showProteinSubtypes(origin: string) {
    switch ( origin ) {
      case INVENTORY_ITEM_TYPES.COMMERCIAL_PROTEIN:
        this.modalService.open(this.selectCommercialProteinSubtype, { backdrop: 'static' }).result.then();
        break;
      case INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN:
        this.modalService.open(this.selectInHouseProteinSubtype, { backdrop: 'static' }).result.then();
        break;
    }
  }

  showNewItemScreen(type: string, subtype: string) {
    this.selectedItem = new InventoryV2({
      active: true,
      type: type,
      subtype: subtype,
      editedBy: this.currentUser.name,
      modifiedOn: new Date(),
      amount : 0,
      numberInUse : 0,
      quantityThreshold : 0
    });
    if ( type == this.INVENTORY_ITEM_TYPES.IN_HOUSE_PROTEIN ) {
      this.selectedItem.manufacturer = this.companyName;
    }
    this.enableEditing = true;
    this.isNewItem = true;
    this.showItem = true;
  }



  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
    this.subcolspan = 2;
    this.viewColumn.forEach(v => {
      if (v) this.subcolspan++;
    });
  }

  checkItemSelected(inventoryDetailId : number): boolean {
    if ( this.inventoryDetailIdSelected == inventoryDetailId ) {
      return true;
    }
    else {
      return false;
    }
  }

  selectForCheckout( inventoryId : number, inventoryDetailId : number ) {
    if (this.inventoryDetailIdSelected == inventoryDetailId ) {
      this.inventoryDetailIdSelected = 0;
      this.selectedItem = undefined;
    }
    else {
      this.inventoryDetailIdSelected = inventoryDetailId;
      this.selectedItem = this.items.find( x => x.inventoryId == inventoryId);
    }
  }

  expandSubTable(itemIndex){
    this.items.forEach((item, index) => {
      if(itemIndex === index){
        item.show = !item.show
      }else{
        item.show = false
      }
    })
  }

}


