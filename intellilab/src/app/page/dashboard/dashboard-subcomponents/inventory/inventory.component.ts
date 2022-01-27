import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Enum } from 'app/shared/objects/Enum';
import { DateFormatType, SettingService2 } from 'app/shared/services2/Setting2.service';
import { ANIMATIONS } from '../../../../shared/models/Constants';
import { InventoryDetailV2, InventoryV2 } from '../../../../shared/objects2/InventoryV2';
import { InventoryService2 } from '../../../../shared/services2/Inventory2.service';
import { InventoryDetailService2 } from '../../../../shared/services2/InventoryDetail2.service';


@Component({
  selector: 'dashboard-inventory',
  providers: [InventoryService2,
              InventoryDetailService2],
  templateUrl: './inventory.component.html',
  styleUrls: ['../../dashboard.component.scss'],
  animations: ANIMATIONS
})
export class InventoryComponent implements OnInit {
  @Output() routeDash = new EventEmitter();
  @Input() timeNow: number;

  // For use in html
  TABLE = Enum.getDashboardRouteDestinations();
  _array = Array;

  // Route variables
  animateTable = this.TABLE.none;
  animateRow = -1;

  lowInStock = [];
  isLoading: boolean = false

  /***************** Data Models *****************/
  inventory: InventoryV2[] = []; // Contains all inventory data, other models are a subset of this one
  expiredMaterialModel: InventoryDetailV2[]; // All Items (incl. non-active) with a valid expiryDate and are in-stock
  stockWithThresholdModel: InventoryV2[]; // SKUs that are active and have a threshold > 0
  stockNoThresholdModel: InventoryV2[]; // SKUs without a valid threshold

  /****************** UI Control *****************/
  /*   1) Data for the tables in the current view
  /*   2) Table titles tables
  /*   3) Collapse
  /***********************************************/
  expiredMaterialModelCurr: InventoryDetailV2[];
  stockStatusModelCurr: InventoryV2[];

  expiredMaterialTitle = 'Expired Materials';
  stockStatusTitle = 'Stock Status';

  expiredMaterialCollapsed = true;
  stockStatusCollapsed = true;
  dateFormat= '';
  dateFormatDisplay: string;

  constructor(private inventoryService: InventoryService2,
              private inventoryDetailService: InventoryDetailService2,
              private settingService: SettingService2) { }

   ngOnInit() {
     this.loadSetting();
     this.getStockStatus();
      // this.loadInventoryData().then(_ => {
      //    this.displayInventoryView();
      // });
    //  alert('need to load inventory data at some point');
   }

   loadSetting() {
      this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
      this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
   }

   private displayInventoryView() {
     // default: show materials that are at or under the stock requirement
     this.createStockModels();
     this.showStockStatus(true, 100);

     // default: show currently expired materials
     this.createExpiredMaterialsModel();
     this.showMaterialsExpiring(0);
   }

   // underPercentRequired = 0 will return Out of Stock materials (0 amount and inuse)
   public showStockStatus(withThreshold: boolean, underPercentRequired: number = 100) {
     this.stockStatusTitle = (!withThreshold) ? 'Stock Status (Missing Threshold)' :
     (underPercentRequired <= 0) ? 'Out of Stock' :
     (underPercentRequired == 100) ? 'Low in Stock' : 'Under Stock Requirement (' + underPercentRequired + '% Threshold)';

     // TODO: Fix 'amount' and 'inuse' access
     // const requirement = underPercentRequired / 100; this.stockStatusModelCurr = [];
     // let result = [];
     // if (withThreshold) {
     //   this.stockWithThresholdModel.forEach(SKU => {
     //     const amount = SKU.amount / +SKU.quantityThreshold;
     //     if (underPercentRequired <= 0 && SKU.inuse != 0) { return; } // For Out of Stock view only
     //     if (amount > requirement && Math.abs(amount - requirement) > 0.0001) { return; }
     //     this.stockStatusModelCurr.push(SKU);
     //     result.push(SKU);
     //   });
     // } else {
     //   result = this.stockNoThresholdModel;
     // }
     // this.stockStatusModelCurr = result;
   }

   // Update UI model and table name
   // Table Inventory, Subview Expired Materials
   public showMaterialsExpiring(daysFromToday: number) {
     this.expiredMaterialTitle = (daysFromToday == 0) ? 'Expired Materials' : (daysFromToday > 0) ?
     'Soon to Expire (' + daysFromToday + ' days)' : 'Already Expired (' + daysFromToday + ') days';

     // Update table data
     const futureDate = this.timeNow + daysFromToday * 1000 * 60 * 60 * 24; let done = false;
     this.expiredMaterialModelCurr = []; let index = 0; const result = [];
     while (!done && +this.expiredMaterialModel[index].expiryDate < futureDate) {
       this.expiredMaterialModelCurr.push(this.expiredMaterialModel[index]); // Update view instantly
       result.push(this.expiredMaterialModel[index]);
       index++;
       if (index >= this.expiredMaterialModel.length) { done = true; }
     }
     // Check up to 24 hours later
     const dayOfWeek = new Date(futureDate).getDay();
     while (!done && +this.expiredMaterialModel[index].expiryDate <= futureDate + 1000 * 60 * 60 * 24) {
       if (dayOfWeek == new Date(+this.expiredMaterialModel[index].expiryDate).getDay()) {
         this.expiredMaterialModelCurr.push(this.expiredMaterialModel[index]);
         result.push(this.expiredMaterialModel[index]);
       }
       index++;
       if (index >= this.expiredMaterialModel.length) { done = true; }
     }
     // Prevent errors caused by rapid successive user inputs
     this.expiredMaterialModelCurr = result;
   }

   public input_showStockStatus(event: any) {
      if (event && event.target && event.target.value && !isNaN(+event.target.value)) {
         this.showStockStatus(true, +event.target.value);
      }
   }

   public input_showMaterialsExpiring(event: any) {
      if (event && event.target && event.target.value && !isNaN(+event.target.value)) {
         this.showMaterialsExpiring(+event.target.value);
      }
   }

  /************************************************/
  /****************** Data Models *****************/
  /************************************************/


  // Returns a list of all SKUs that are at or under the inventory threshold
  // Only unopened inventory is use for comparison with threshold
  // Ignore SKUs that are not active
  private createStockModels() {
    const model: InventoryV2[] = [];
    const noThresholdModel: InventoryV2[] = [];

    this.inventory.forEach(SKU => {
      if (SKU == null) { return; }
      if (SKU.active !== true) { return; }
      (+SKU.quantityThreshold > 0) ? model.push(SKU) : noThresholdModel.push(SKU);
    });
    // Most important: amount/threshold, second: inuse, third: threshold
    model.sort(function(a, b){
      if (+a.quantityThreshold == +b.quantityThreshold) {return 0; }
      return ((+a.quantityThreshold < +b.quantityThreshold) ? 1 : -1);
    });
    // TODO: Fix 'amount' and 'inuse' access
    // model.sort(function(a, b){
    //   if (a.inuse == b.inuse) {return 0; }
    //   return ((a.inuse < b.inuse) ? -1 : 1);
    // });
    // model.sort(function(a, b){
    //   const _a = a.amount / +a.quantityThreshold; const _b = b.amount / +b.quantityThreshold;
    //   if (Math.abs(_a - _b) < 0.0001) {return 0; }
    //   return ((_a < _b) ? -1 : 1);
    // });
    this.stockWithThresholdModel = model;
    this.stockNoThresholdModel = noThresholdModel;
  }

  // Returns a list of all Items that have a valid expiryDate and in stock
  // Track non-active items too
  private createExpiredMaterialsModel() {
    const model: InventoryDetailV2[] = [];
    this.inventory.forEach(SKU => {
      SKU.inventoryDetails.forEach(item => {
        // Ignore items with invalid Date
        if (item.expiryDate == null || isNaN(+item.expiryDate) || +item.expiryDate == 0) { return; }

        // TODO: Fix 'amount' and 'inuse' access
        // Only include items that we have in stock (open and unopened)
        // if (+item.amount > 0 || +item.inuse > 0) {
        // model.push(item);
        // }
      });
    });
    // Sort by importance
    model.sort(function(a, b){
    return ((+a.expiryDate - +b.expiryDate > 0) ? 1 : -1);
    });
    this.expiredMaterialModel = model;
  }

   // This following 2 functions are called by parent component
   cancelRoute() {
     this.animateTable = this.TABLE.none;
   }

   getData(table: number, row: number): any {
     switch (table) {
       case this.TABLE.inv_expired:
         return this.expiredMaterialModelCurr[row];
       case this.TABLE.inv_stock:
         return this.lowInStock[row]
        //  return this.stockStatusModelCurr[row];
       default:
         return null;
     }
   }

   getStockStatus() {
    this.isLoading = true
    this.inventoryService.getLowInStock().toPromise().then(items => {
      console.log('returned data for low stock items')
      console.log(items)
      this.lowInStock = items;
      this.isLoading = false
    }).catch(error => {
      console.log(error);
      this.isLoading = false
    });
   }

   togglePanel(panel){
    this.stockStatusCollapsed = !this.stockStatusCollapsed
    if(!this.stockStatusCollapsed){
      panel.style.transition = 'all ease-out 0.3s'
      panel.style.height = 340 + 'px'
      panel.style.padding = 5 + 'px'
      panel.style['padding-left'] = 15 + 'px'
    }else{
      panel.style.transition = 'all ease-in 0.3s'
      panel.style.height = 0 + 'px'
      panel.style.padding = 0 + 'px'
    }
  }
}
