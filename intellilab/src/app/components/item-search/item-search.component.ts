import { Component, Input, Output, EventEmitter } from '@angular/core';
import { InventoryV2 } from '../../shared/objects2/InventoryV2';
import { InventoryService2 } from '../../shared/services2/Inventory2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';

@Component({
  selector: 'app-item-search',
  templateUrl: './item-search.component.html',
  styleUrls: ['./item-search.component.css']
})
export class ItemSearchComponent  {

  @Input()  currentItemName: string;
  @Output() itemSelected = new EventEmitter();
  itemSearchResults : InventoryV2[] = [];
  findItem : boolean = true;
  originalName : string;

  constructor( private inventoryService : InventoryService2, 
    private errorService: ErrorService ) {}

  ngOnInit() {
    this.originalName = this.currentItemName;
  }

  selectItem( selectedItem : InventoryV2 ) {
    this.itemSelected.emit( selectedItem );
    this.itemSearchResults = [];
  }

  searchItem() {
    if (!this.currentItemName) return;
    this.itemSearchResults = [];
    this.findItem = true;
    this.inventoryService.search( this.currentItemName  ).subscribe(result => {
      this.itemSearchResults = result;
      if (this.itemSearchResults.length < 1) {
        this.currentItemName = this.originalName;
        this.findItem = false;
        setTimeout(() => this.findItem = true, 5000);
      }
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  join(separator) {
    var argsLength = arguments.length,
    strings = [];
    for (var i = 1, j = 0; i < argsLength; ++i) {
      var arg = arguments[i];
      if (arg) strings[j++] = arg;
    }
    return strings.join(separator);
  }

}