import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-drop-down-list',
  templateUrl: './drop-down-list.component.html',
  styleUrls: ['./drop-down-list.component.css']
})
export class DropDownListComponent implements OnInit {

  selected_ex_type:string;
  selected_unit: string;


  @Input() width;
  @Input() targetUnit: string;
  @Input() mode: string = 'regular'
  @Input() displayLabel: boolean = false;
  @Input() parentId:number = -1;
  @Input('listContent') ex_types:string[];
  @Input() index:number;
  @Input() placeHolderValue: string;
  @Input() defaultOption = null;
  @Input() displayRemoveButton:boolean = true;
  @Input() hideStepMove:boolean = false;
  @Output() selection = new EventEmitter<{parentId:number, index:number, option: string}>();
  @Output() unit = new EventEmitter();
  @Output() removedStep = new EventEmitter<{parentId:number, index:number}>();
  @Output() stepMove = new EventEmitter<{parentId:number, stepMove: string, stepIndex:number}>();
  constructor() { }

  ngOnInit(): void {
    if(this.defaultOption){
      // Match name "Test" to "Sample Preparation" for component initialization
      this.selected_ex_type = this.defaultOption === 'Test' ? 'Sample Preparation' : this.defaultOption;
      this.selected_unit = this.defaultOption;
    }

  }

//  setOption(event){
//    console.log(event)
//  }

setUnit(unit){
  this.selected_unit = unit
  console.log("unit select is changed by parent with value: " + this.selected_unit)
}

  removeStep(){
    this.removedStep.emit({
      parentId: this.parentId,
      index: this.index
    });
  }

  notifySelectionValue(){
    // Match name "Sample Preparation" to "Test" due to the name change requirement from Jessi
    let stepName;
    if(this.selected_ex_type === 'Sample Preparation'){
      stepName = 'Test'
    }else{
      stepName = this.selected_ex_type
    }


    this.selection.emit({
      parentId: this.parentId,
      index: this.index,
      option: stepName
    });
  }

  unitSelection(){
    this.unit.emit({
      targetUnit: this.targetUnit,
      option: this.selected_unit
    })
  }

  moveUp(){
    this.stepMove.emit({
      parentId: this.parentId,
      stepIndex: this.index,
      stepMove: "UP"
    })
  }

  moveDown(){
    this.stepMove.emit({
      parentId: this.parentId,
      stepIndex: this.index,
      stepMove:"DOWN"
    })
  }

}
