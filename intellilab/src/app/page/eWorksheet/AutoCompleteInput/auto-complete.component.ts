import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {map, startWith} from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-auto-complete',
  templateUrl: './auto-complete.component.html',
  styleUrls: ['./auto-complete.component.css']
})
export class AutoCompleteComponent implements OnInit {
  options = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

  displayPrimaryText: boolean = false;
  inputHistory;
  enableFilter:boolean = false;
  myControl = new FormControl();
  filteredOptions: Observable<any[]>;
  warn:boolean = false;
  toolTipMessage = 'Input value does not exist in the database'

  @Input() saveWarnStatus: boolean = false;
  @Input() matLabel: string;
  @Input() placeHolder:string;
  @Input() inputWidth:string = '500px';
  @Input() controllerName: string;
  @Input() initSelection: string = "";
  @Input() listContent = [];
  @Input() listContentType: string = 'object';
  @Output() userSelection = new EventEmitter();
  @Output() warnData = new EventEmitter();
  constructor() { }

  ngOnInit(): void {

    this.inputHistory = this.initSelection;
    this.myControl.setValue(this.initSelection);

    if(this.listContent.length > 0 && this.listContentType === "object"){
      this.listContent.sort(
        function (a, b){
          if(a.itemName.toLowerCase() < b.itemName.toLowerCase()) { return -1; }
          if(a.itemName.toLowerCase() > b.itemName.toLowerCase()) { return 1; }
          return 0;
        })
        console.log("list content array after sorted: ");
        console.log(this.listContent)
    }



    //if(this.listContent.length > 0){
      if(this.listContentType === "object"){
        this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(this.initSelection),
          map(value => this.searchBinary(value, this.listContent, true))
          // map(value => this.filter(value, this.enableFilter))
        )
      }


      // initialize lot number list content immediately once the auto complete component is created
      if(this.listContentType === "string"){
        this.filteredOptions = this.myControl.valueChanges.pipe(
          startWith(this.initSelection),
          map(value => this.filter(value, true))
        )
      }

      // need to reset the value for initSelection variable because the pipe will use this variable's value for the startWith() function
      this.initSelection = "";
    //}
    // console.log(`list content value for auto complete input for ${this.controllerName} with length ${this.listContent.length}: `);
    // console.log(this.listContent)
    if(this.saveWarnStatus = true){
      this.initWarn();
    }

  }



  // private filter(value: string, enableFilter:boolean){
  //   const filterValue = value.toLowerCase()
  //   if(this.listContentType == 'object' && enableFilter){
  //     const itemArray = this.listContent.filter(objectOption =>
  //       objectOption.itemName.toLowerCase().includes(filterValue) || objectOption.itemCatNum.toLowerCase().includes(filterValue)
  //    )

  //   const strArray = itemArray.map(item => {
  //     const splitCatNum = item.itemCatNum.trim();
  //     if(splitCatNum !== 'NA' && splitCatNum !== '?' && splitCatNum !== null && splitCatNum !== undefined && splitCatNum.length > 0){
  //       return item.itemCatNum.trim() + '/' + item.itemName.trim()
  //     }else{
  //       return item.itemName.trim()
  //     }

  //   });
  //    return strArray;
  //   }

  //   else if(this.listContentType == 'string' && enableFilter){
  //     const strArray = this.listContent.filter(strOption =>
  //       strOption.toLowerCase().includes(filterValue)
  //    )

  //    return strArray;
  //   }

  //   else{
  //     return [];
  //   }

  // }

  private filter(value, enableFilter:boolean){
    console.log("value for filter function is: ");
    console.log(value);
    const filterValue = value.toLowerCase()
    if(enableFilter){
      const lotArray = this.listContent.filter(option =>
        option.lotnumber.toLowerCase().includes(filterValue)
     )

     return lotArray;
    }

    else{
      return [];
    }

  }


  emitChange(event){
    // must manipulate warn's value before calling onChange() function
    this.warn = false;
    this.myControl.setValue(event.option.value);
    console.log("mycontrol value: " + this.myControl.value);
    this.onChange();
  }

  triggerFilter(){
    this.enableFilter = true;
  }


  onChange(){
    let supplier;
    let ID;
    let match:boolean = false;
    this.inputHistory = this.myControl.value;
    let lotConfig;
    if(this.listContentType === 'string'){
      lotConfig = {
        // lotnumber: this.myControl.value.lotnumber,
        lotnumber: this.myControl.value,
        expired: false
      };
    }

    // The most priority is to fetch item supplier against item's cat number, and if there is no valid cat number,then the system will fetch item supplier against item name
    if(this.listContentType == 'object'){

      let splitName;
      let splitCatNum;
        if(this.myControl.value.includes('/')){
          const splitArray = this.myControl.value.split('/');
          splitName = splitArray[1];
          splitCatNum = splitArray[0];
        }else{
          splitName = this.myControl.value;
          splitCatNum = null;
        }

        console.log("split name: " + splitName);
        console.log("split cat number: " + splitCatNum);

        if(splitCatNum !== 'NA' && splitCatNum !== '?' && splitCatNum !== null && splitCatNum !== undefined && splitCatNum.length > 0){
          this.listContent.forEach(option => {
            if(option.itemCatNum){
              if(option.itemCatNum.toLowerCase().trim() == splitCatNum.toLowerCase().trim()){
                match = true;
                ID = option.itemId;
                supplier = option.itemSupplier;
                console.log("find supplier by cat number: " + option.itemSupplier)
              }
            }
          })
        }else if(splitName !== undefined){
          this.listContent.forEach(option => {
            if(option.itemName.toLowerCase().trim() == splitName.toLowerCase().trim()){
              match = true;
              ID = option.itemId;
              supplier = option.itemSupplier;
              console.log("find supplier by item name: " + option.itemSupplier)
            }
          })
        }

    }else{
      this.listContent.forEach(option => {
        if(option.lotnumber.toLowerCase().trim() == this.myControl.value.toLowerCase().trim()){
          match = true;
          const date = new Date();
          if(option.expiredate){
            if(date.getTime() > option.expiredate){
              lotConfig.expired = true;
            }
          }

          console.log("today's date: " + date.getTime());
          console.log("expire date: " + option.expiredate);
          console.log("is expired: " + lotConfig.expired)
        }
      })
    }


    if(match === false){
      this.warn = true;
    }else{
      this.warn = false;
    }

    let data;
    if(this.listContentType == 'object'){
      data = {
        itemId: ID,
        controllerName: this.controllerName,
        selection: this.myControl.value,
        itemSupplier: supplier,
        warn: this.warn
      }
    }

    if(this.listContentType == 'string'){
      data = {
        controllerName: this.controllerName,
        selection: this.myControl.value,
        expired: lotConfig.expired,
        warn: this.warn
      }
    }


  // console.log(`auto-complete selection for controller ${this.controllerName} is: `);
  // console.log(data);

  this.userSelection.emit(data);
  }




  initWarn(){
    let match:boolean = false;
    let supplier;
    let ID;

    if(this.listContentType == 'object'){
      let splitName;
      let splitCatNum;
        if(this.myControl.value.includes('/')){
          const splitArray = this.myControl.value.split('/');
          splitName = splitArray[1];
          splitCatNum = splitArray[0];
        }else{
          splitName = this.myControl.value;
          splitCatNum = null;
        }
      console.log("split name: " + splitName);
      console.log("split cat number: " + splitCatNum);

      if(splitCatNum !== 'NA' && splitCatNum !== '?' && splitCatNum !== null && splitCatNum !== undefined && splitCatNum.length > 0){
        this.listContent.forEach(option => {
          if(option.itemCatNum){
            if(option.itemCatNum.toLowerCase().trim() == splitCatNum.toLowerCase().trim()){
              match = true;
              ID = option.itemId;
              supplier = option.itemSupplier;
              console.log("find supplier by cat number: " + option.itemSupplier)
            }
          }
        })

      }else if(splitName !== undefined){
        this.listContent.forEach(option => {
          if(option.itemName.trim() == splitName.trim()){
            match = true;
            ID = option.itemId;
            supplier = option.itemSupplier;
            console.log("find supplier by item name: " + option.itemSupplier)
          }
        })
      }

  }else{
      this.listContent.forEach(option => {
        if(option.lotnumber.toLowerCase().trim() == this.myControl.value.toLowerCase().trim()){
          match = true;
        }
      })
    }


    if(match === false){
      this.warn = true;
    }

    if(this.listContentType == 'object'){
      this.warnData.emit({itemId: ID, itemSupplier: supplier})
    }

  }


  displayFn(content, parseType){
    let primaryText;
    let secondaryText;
    if(parseType === 'primary'){
      if(content.includes('/')){
        primaryText = content.split('/')[0];
        this.displayPrimaryText = true;
      }else{
        primaryText = '';
      }
      return primaryText;
    }

    if(parseType === 'secondary'){
      if(content.includes('/')){
        secondaryText = content.split('/')[1];
      }else{
        secondaryText = content;
      }
      return secondaryText
    }
  }





	searchBinary(needle, haystack, case_insensitive) {
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
        var element = haystack[mid].itemName.substr(0,letterNumber);
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
        var element =  (case_insensitive) ? haystack[i].itemName.substr(0,letterNumber).toLowerCase() : haystack[i].itemName.substr(0,letterNumber);
        if(element != needle){
          var start = i+1;
          break;
        }else{
          var start = 0;
        }
      }
      for(let i = getElementPosition; i < haystackLength; i++ ){
        var element =  (case_insensitive) ? haystack[i].itemName.substr(0,letterNumber).toLowerCase() : haystack[i].itemName.substr(0,letterNumber);
        if(element != needle){
          var end = i-1;
          break;
        }else{
          var end = haystackLength -1;
        }
      }
      var result = [];
      for(let i = start; i <= end; i++){
        result.push(haystack[i].itemInfo)
      }

      return result;
    }

  };


} // End of component
