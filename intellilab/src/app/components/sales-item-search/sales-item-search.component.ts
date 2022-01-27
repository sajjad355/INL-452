import { Component, Input, Output, EventEmitter } from '@angular/core';
import { SalesItemV2 } from '../../shared/objects2/SalesItemV2';
import { SalesItemService2 } from '../../shared/services2/SalesItem2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';

@Component({
  selector: 'app-sales-item-search',
  templateUrl: './sales-item-search.component.html',
  styleUrls: ['./sales-item-search.component.css']
})
export class SalesItemSearchComponent  {

  @Input()  currentCatalogNumber : string;
  @Output() itemSelected = new EventEmitter();
  searchResults : SalesItemV2[] = [];
  findItem : boolean = true;
  originalCatalogNumber : string;

  constructor( private errorService: ErrorService, private salesItemService : SalesItemService2) {}

  ngOnInit() {
    this.originalCatalogNumber = this.currentCatalogNumber;
  }

  

  selectItem( selectedItem : SalesItemV2 ) {
    this.itemSelected.emit( selectedItem );
    this.searchResults = [];
  }

  searchItem() {
    if (!this.currentCatalogNumber) return;
    this.searchResults = [];
    this.findItem = true;
    this.salesItemService.search( this.currentCatalogNumber  ).subscribe(result => {
      this.searchResults = result;
      if (this.searchResults.length < 1) {
          this.currentCatalogNumber = this.originalCatalogNumber;
          this.findItem = false;
          setTimeout(() => this.findItem = true, 5000);
      }
    }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
    });
  }

  

}