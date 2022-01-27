import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { KitService2 } from 'app/shared/services2/Kit2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ISubscription } from "rxjs/Subscription";
import { ErrorService } from '../../page/error/error.service';
import { KitV2 } from '../../shared/objects2/KitV2';
import { ProductV2 } from '../../shared/objects2/ProductV2';
import { ProductService2 } from '../../shared/services2/Product2.service';


@Component({
  selector: 'app-catalog-search',
  templateUrl: './catalog-search.component.html',
  styleUrls: ['./catalog-search.component.css']
})
export class CatalogSearchComponent implements OnInit {
  @Output() onClose = new EventEmitter()

  searchKitSubscription : ISubscription;
  searchProductSubscription : ISubscription;

  kitList: KitV2[] = [];
  productList : ProductV2[]=[];

  searchResult: { id: number, type: string, name: string, cat: string, size: string, unit: string, price: string }[];
  searchForm: FormGroup;
  searchKey:string;
  noInput:boolean=false;

  constructor(private formBuilder: FormBuilder,
              private errorService: ErrorService,
              private kitService: KitService2,
              private productService: ProductService2) {
  }

  ngOnInit() {
    this.searchForm=this.createSearchForm();
    this.noInput=false;
  }

  ngOnDestroy() {
    if(this.searchKitSubscription){this.searchKitSubscription.unsubscribe()};
    if(this.searchProductSubscription){this.searchProductSubscription.unsubscribe()};
  }

  createSearchForm(): FormGroup {
    return this.formBuilder.group(
      {
        search: [ null, Validators.compose([Validators.required])]
     });
  }

  search() {
    if (this.searchForm.controls['search'].value == undefined || this.searchForm.controls['search'].value.trim() == '') { this.noInput=true;return; }
    let key = this.searchForm.controls['search'].value.trim();
    this.noInput=false;
    this.searchResult = [];

    this.searchKitSubscription = this.kitService.search( key ).subscribe(result => {
      console.log("Kit searching results: ");
      console.log(result)
      this.kitList = result;
      this.kitList.forEach(k => {
          this.searchResult.push({ id: k.salesItemId,
                                   type: 'Kit',
                                   name: k.name,
                                   cat: k.catalogNumber,
                                   size: k.packSize,
                                   unit: '',
                                   price: k.unitPrice + '' })
      });
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

    this.searchProductSubscription = this.productService.searchProduct( key ).subscribe(result => {
      console.log("Product searching results: ");
      console.log(result)
      this.productList = result;
      this.productList.forEach(p => {
          this.searchResult.push({ id: p.salesItemId,
                                   type: 'Product',
                                   name: p.name,
                                   cat: p.catalogNumber,
                                   size: p.unitSize + '',
                                   unit: p.unit + '',
                                   price: p.unitPrice + '' })
      });
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  closeSearchPanel() {
    this.onClose.emit()
  }
}
