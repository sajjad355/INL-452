import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import * as lodash from "lodash";
import { EquipmentService2 } from 'app/shared/services2/Equipment2.service';

@Component({
  selector: 'app-equipment-ws',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.scss']
})
export class EquipmentComponent_WS implements OnInit {

  @Output() updateEquip = new EventEmitter();
  @Input() initData = [];
  @Input() stepInfo;


  equipmentList = []

  equipments = [];
  tableList = [];
  dataSource = [];
  displayedColumns: string[] = ['position', 'name','id', 'serial', 'manufacturer','model', 'action', 'warn']
  myControl = new FormControl();
  filteredOptions: Observable<any[]>;
  constructor(public equip_service: EquipmentService2) { }

  ngOnInit() {

    if(this.stepInfo){
      console.log("Step Info for equipment:")
      console.log(this.stepInfo)
    }

    this.equip_service.init();
    this.equipmentList = this.equip_service.getAllEquipments();
    console.log("Original equipment list data retrieved from the database:")
    console.log(this.equipmentList);


    if(this.initData.length > 0){
      this.equipments = this.initData;
      this.updateTableList();
      console.log(`Init equipment data for step ${this.stepInfo.stepType}:`);
      console.log(this.equipments)
    }else{
      console.log(`No init equipment data for step ${this.stepInfo.stepType}`);
    }

      this.equipmentList.sort(
        function (a, b){
          if(a.name.toLowerCase() < b.name.toLowerCase()) { return -1; }
          if(a.name.toLowerCase() > b.name.toLowerCase()) { return 1; }
          return 0;
        })

        console.log("Equipment List after sort: ");
        console.log(this.equipmentList)

      this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(''),
          map(value => this.searchBinary(value, this.equipmentList, true))
        )

  }

  onSelect(event){
    console.log("selected option is: ");
    console.log(event.option.value);
    this.equipments.push(event.option.value);
    this.updateTableList();
    this.updateEquip.emit({
      equipments: this.equipments
    })
    // this.myControl.setValue('');
  }

  onDelete(id){
    console.log("delete equipment ID: " + id)
    const temp = this.equipments.filter(equipment => equipment.equipmentId !== id);
    this.equipments = temp;
    this.updateEquip.emit({
      equipments: this.equipments
    })
    this.updateTableList();
    if(this.equipments.length === 0){
      this.myControl.setValue('');
    }
  }

  // displayFn(subject){
  //   return subject ? `${subject.serial}/${subject.item}` : ''
  // }
  displayFn(subject){
    return subject ? `${subject.name}` : ''
  }

  updateTableList(){
    const tempList = this.equipments.map((equipment, index) => {
      return{
        position: index + 1,
        name: equipment.name,
        id: equipment.equipmentId,
        serial: equipment.serial,
        manufacturer: equipment.manufacturer,
        model: equipment.model,
        action: equipment.equipmentId,
        warn: (equipment.active && !equipment.qualified)
      }
    })

    this.dataSource = lodash.cloneDeep(tempList);
    // this.tableList = lodash.cloneDeep(tempList);
    // this.splitPageList(this.pageIndex, this.pageSize)
    // console.log("Table list: ");
    // console.log(this.tableList);
  }

  sortData(sort){
    // this.sortState.active = sort.active;
    // this.sortState.direction = sort.direction;

    const data = lodash.cloneDeep(this.dataSource);
    if (!sort.active || sort.direction === '') {
      return;
    }

    this.dataSource = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'position': return this.compare(a.position, b.position, isAsc);
        case 'name': return this.compare(a.name, b.name, isAsc);
        case 'id': return this.compare(a.id, b.id, isAsc);
        case 'serial': return this.compare(a.serial, b.serial, isAsc);
        case 'manufacturer': return this.compare(a.manufacturer, b.manufacturer, isAsc);
        case 'model': return this.compare(a.model, b.model, isAsc);
        default: return 0;
      }
    });
    // console.log("sorted array is: ");
    // console.log(this.dataSource);
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  searchBinary(needle, haystack, case_insensitive) {
    if(needle.name){
      needle = needle.name;
    }
    if(needle == "") return [];
    var haystackLength 	= haystack.length;
    var letterNumber 	= needle.length;
    case_insensitive 	= (typeof(case_insensitive) === 'undefined' || case_insensitive) ? true:false;
    needle 				= (case_insensitive) ? needle.toLowerCase():needle;

    /* start binary search, Get middle position */
    var getElementPosition = findElement()

    /* get interval and return result array */
    if(getElementPosition == -1) return [];

    let getRangeElement = findRangeElement();
    return getRangeElement;

    function findElement() {
      if (typeof(haystack) === 'undefined' || !haystackLength) return -1;

      var high = haystack.length - 1;
      var low = 0;

      while (low <= high) {
        let mid = Math.round((low + high) / 2);
        var element = haystack[mid].name.substr(0,letterNumber);
        element = (case_insensitive) ? element.toLowerCase():element;

        if (element > needle) {
          high = mid - 1;
        } else if (element < needle) {
          low = mid + 1;
        } else {

          return mid;
        }
      }
      return -1;
    }
    function findRangeElement(){

      for(let i = getElementPosition; i >= 0; i--){
        var element =  (case_insensitive) ? haystack[i].name.substr(0,letterNumber).toLowerCase() : haystack[i].name.substr(0,letterNumber);
        if(element != needle){
          var start = i+1;
          break;
        }else{
          var start = 0;
        }
      }
      for(let i = getElementPosition; i < haystackLength; i++ ){
        var element =  (case_insensitive) ? haystack[i].name.substr(0,letterNumber).toLowerCase() : haystack[i].name.substr(0,letterNumber);
        if(element != needle){
          var end = i-1;
          break;
        }else{
          var end = haystackLength -1;
        }
      }
      var result = [];
      for(let i = start; i <= end; i++){
        result.push(haystack[i])
      }

      return result;
    }

  };

}
