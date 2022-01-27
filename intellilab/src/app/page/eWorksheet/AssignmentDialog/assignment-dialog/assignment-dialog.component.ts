import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription, Observable } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import * as lodash from "lodash";
import { WorksheetDesignService } from 'app/shared/services/eWorksheet.service';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
import { UserService2 } from 'app/shared/services2/User2.service';

@Component({
  selector: 'app-assignment-dialog',
  templateUrl: './assignment-dialog.component.html',
  styleUrls: ['./assignment-dialog.component.scss']
})
export class AssignmentDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AssignmentDialogComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any, private authenticationservice: AuthenticationService2, private user_service: UserService2,
     public ws_service: WorksheetDesignService) { }


  form: FormGroup;
  userInput;
  option;
  // myControl = new FormControl();
  filteredOptions=[];
  showThought: boolean = false;
  userlist = [];
  userListSub: Subscription;
  userNameList;
  receiverList = []
  commit: boolean = false;

  ngOnInit() {

    this.form = new FormGroup({
      //operation 1 form control variables
      "myControl": new FormControl(null)

    });
    //this subscription will return a user list for worksheet design assignment



    this.userListSub = this.user_service.loadUsers().subscribe(users=>{
      this.userlist = users;

      if(this.userlist.length > 0){
        this.userlist.sort(
          function (a, b){
            // let tempA = a.name.split(' ')[0]+(a.name.split(' ')[1] || '')
            // let tempB = b.name.split(' ')[0]+(b.name.split(' ')[1] || '')
            if(a.name.replace(/\s+/g, '').toLowerCase() < b.name.replace(/\s+/g, '').toLowerCase()) { return -1; }
            if(a.name.replace(/\s+/g, '').toLowerCase() > b.name.replace(/\s+/g, '').toLowerCase()) { return 1; }
            return 0;
          })

        this.userNameList = this.userlist.map(user => {
          return {
            id: user.userId,
            name: user.name
          }
        });
      }
      console.log("user name list content");
      console.log(this.userNameList)
      })

      this.form.get('myControl').valueChanges.subscribe(res => {
        this.filteredOptions = this.searchBinary(res, this.userNameList, true)
        if(this.filteredOptions.length === 0){
          this.showThought = false;
        }
      })
  }


  onChange(){
    // console.log("user input updated")
    // this.filteredOptions = this.searchBinary(this.userInput || '', this.userNameList, true)
    // console.log("filter options are: ");
    // console.log(this.filteredOptions)
  }

  onSelect(selection){
    this.showThought = false;
    const index = this.receiverList.findIndex(analyst => analyst.id === selection.id)
    if(index === -1){
      this.receiverList.push(selection);
      this.form.patchValue({myControl: ''});
      console.log("Add assignment receiver" + ", size: " + this.receiverList.length);
      console.log(this.receiverList);
    }else{
      this.form.patchValue({myControl: ''});
    }

  }

  onDelete(userId){
    // console.log("current receivers: ");
    // console.log(this.receiverList)
    // console.log("delete user id: " + userId);
    const temp = this.receiverList.filter(receiver => receiver.id !== userId);
    this.receiverList = lodash.cloneDeep(temp);
    console.log("remove user ID: " + userId + ", size: " + this.receiverList.length);
    console.log(this.receiverList);
  }

  onCancel(){
    this.commit = false;
  }

  onSubmit(){
    this.commit = true;
  }

  onProceed(){
    let analysts = [];
    this.receiverList.forEach(receiver => {
      analysts.push(receiver.id);
    })

    const assignment = {
      templateId: this.data.templateId,
      analysts: analysts
    }

    // console.log("Assignment is created: ");
    console.log(assignment);
    this.dialogRef.close();
    this.ws_service.addAssignment(assignment).subscribe(res => {
      console.log("response for saving new assignment: ");
      console.log(res);
    })
  }

  searchBinary(needle, haystack, case_insensitive) {
    // if(!needle || (needle && needle.length <= 0)){
    //   this.showThought = false;
    // }
    this.showThought = true;
    // console.log("Show thoughts")
    var haystackLength 	= haystack.length;
    var letterNumber 	= needle.length;
    case_insensitive 	= (typeof(case_insensitive) === 'undefined' || case_insensitive) ? true:false;
    needle = (case_insensitive) ? needle.toLowerCase():needle;
    console.log("needle value: " + needle)
    console.log("needle length: " + needle.length);

    /* start binary search, Get middle position */
    var getElementPosition = findElement()

    /* get interval and return result array */
    if(getElementPosition == -1) return [];

    let getRangeElement = findRangeElement();
    return getRangeElement;

    function findElement() {
      if (typeof(haystack) === 'undefined' || haystackLength === 0) return -1;

      var high = haystack.length - 1;
      var low = 0;

      while (low <= high) {
        let mid = Math.floor((low + high) / 2);
        var element = haystack[mid].name.replace(/\s+/g, '').substr(0,letterNumber);
        element = (case_insensitive) ? element.toLowerCase():element;

        if (element > needle) {
          // console.log(element + '>' + needle)
          high = mid - 1;
        } else if (element < needle) {
          // console.log(element + '<' + needle)
          low = mid + 1;
        } else {
          // console.log(element + '=' + needle)
          // console.log("element position: " + mid)
          return mid;
        }
      }
      console.log("element position: " + "not found")

      return -1;
    }
    function findRangeElement(){

      for(let i = getElementPosition; i >= 0; i--){
        var element =  (case_insensitive) ? haystack[i].name.replace(/\s+/g, '').substr(0,letterNumber).toLowerCase() : haystack[i].name.replace(/\s+/g, '').substr(0,letterNumber);
        if(element != needle){
          var start = i+1;
          break;
        }else{
          var start = 0;
        }
      }
      for(let i = getElementPosition; i < haystackLength; i++ ){
        var element =  (case_insensitive) ? haystack[i].name.replace(/\s+/g, '').substr(0,letterNumber).toLowerCase() : haystack[i].name.replace(/\s+/g, '').substr(0,letterNumber);
        if(element != needle){
          var end = i - 1;
          break;
        }else{
          var end = haystackLength -1;
        }
      }
      var result = [];
      for(let i = start; i <= end; i++){
        result.push(haystack[i])
      }

      if(needle.length === 0){
        result = [];
      }

      console.log("result array: ");
      console.log(result)
      return result;
    }

  };

  ngOnDestroy() {
    if(this.userListSub){
      this.userListSub.unsubscribe();
    }
  }

}
