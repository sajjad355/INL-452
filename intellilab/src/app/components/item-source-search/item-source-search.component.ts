import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { ItemSourceV2 } from '../../shared/objects2/ItemSourceV2';
import { ItemSourceService2 } from '../../shared/services2/ItemSource2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';

@Component({
  selector: 'app-item-source-search',
  templateUrl: './item-source-search.component.html',
  styleUrls: ['./item-source-search.component.css']
})
export class ItemSourceSearchComponent  {

  @Input() type: string;
  @Input() selected: string;
  @Output() sourceSelected = new EventEmitter();


  itemSources: ItemSourceV2[] = [];

  constructor( private ItemSourceService : ItemSourceService2) {}

  ngOnInit() {
    this.getAllSources();
  }

  selectSource(selectedItem) {
    let itemSource = this.itemSources.find(x => x.dbid == selectedItem);
    this.sourceSelected.emit(itemSource);
  }

  getAllSources() {
    this.ItemSourceService.loadItemSourcesBy(this.type, true).subscribe(sources => {
      this.itemSources = sources;
      if(this.selected) {
        let findSource = this.itemSources.find(x => x.name.toLowerCase() == this.selected.toLowerCase());
        if(findSource) {
          this.selected = findSource.name;
        }
        else {
          let newSource = new ItemSourceV2();
          newSource.name = this.selected;
          newSource.dbid = -1;
          this.itemSources.push(newSource);
          this.selected = this.selected;
        }
      }
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  ngOnChanges(change: SimpleChanges) {
    if(change.selected && change.selected.currentValue) {
      let findSource = this.itemSources.find(x => x.name.toLowerCase() == change.selected.currentValue.toLowerCase());
      if(findSource) {
        this.selected = findSource.name;
      }
      else {
        let newSource = new ItemSourceV2();
        newSource.name = change.selected.currentValue;
        newSource.dbid = -1;
        this.itemSources.push(newSource);
        this.selected = change.selected.currentValue;
      }
    }
  }

}