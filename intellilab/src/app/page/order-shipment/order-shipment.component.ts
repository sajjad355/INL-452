import { item } from '../../shared/objects/item';
import { Component, OnInit, OnChanges, Input, SimpleChanges, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-order-shipment',
  templateUrl: './order-shipment.component.html',
  styleUrls: ['./order-shipment.component.css']
})
export class OrderShipmentComponent implements OnInit, OnChanges {
  @Input() isShowItem: boolean;
  @Input() item: item

  @Output() resetView = new EventEmitter();

  order: any[];
  viewColumn;
  columnList;
  
  constructor() { }

  ngOnInit() {
    this.viewColumn = [true, true, true, true, true, true,
      true, true, true, true, true, true];
    this.columnList = ['Order ID','Status','Order Date','Order Detail','Client','Shipment Carrier','QC By', 'Monitor By'];
  }

  ngOnChanges(change: SimpleChanges) {

  }

  newItem() {
    alert('Code not expected to be active/called - contact support');
  }

}
