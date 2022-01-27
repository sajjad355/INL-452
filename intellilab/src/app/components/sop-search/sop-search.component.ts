import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { SopV2 } from 'app/shared/objects2/SopV2';
import { SopService2 } from '../../shared/services2/Sop2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';


@Component({
  
  selector: 'app-sop-search',
  templateUrl: './sop-search.component.html',
  styleUrls: ['./sop-search.component.css']
})
export class SopSearchComponent implements OnInit {

  @Input()  currentSopName: string;
  @Output() sopSelected = new EventEmitter();
  sopSearchResults : SopV2[] = [];
  findSop : boolean = true;
  originalName : string;
  

  constructor( private errorService: ErrorService, private sopService : SopService2) {}

  ngOnInit() {
    this.originalName = this.currentSopName;
  }

  

  selectSop( selectedSop : SopV2 ) {
    this.sopSelected.emit( selectedSop );
    this.sopSearchResults = [];
  }

  searchSop() {
    if (!this.currentSopName) return;
    this.sopSearchResults = [];
    this.findSop = true;
    this.sopService.searchSop( this.currentSopName, true  ).subscribe(result => {
      this.sopSearchResults = result;
      if (this.sopSearchResults.length < 1) {
          this.currentSopName = this.originalName;
          this.findSop = false;
          setTimeout(() => this.findSop = true, 5000);
        }
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }

}