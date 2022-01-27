import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
@Component({
  selector: 'app-worksheet-main',
  templateUrl: './worksheet-main.component.html',
  styleUrls: ['./worksheet-main.component.css']
})
export class WorksheetMainComponent implements OnInit {

  currentuser: any;
  id;
  name;
  role;
  roleTag;
  opened: boolean = false;
  constructor(private location: Location) { }

  goBack() {
    this.location.back();
  }

  ngOnInit(): void {
    // let getcurrentuser = this.authenticationservice.getCurrentUser();
    // if (getcurrentuser !== undefined) {
    //   getcurrentuser.then(_ => {
    //     this.currentuser = User.fromJson(_);
    //     this.name = this.currentuser.name;
    //     this.role = this.currentuser.role;
    //     this.roleTag = this.role.charAt(0).toUpperCase();
    //   });
    // }
    this.id = 1;
    this.name = 'Wayne';
    this.role = 'Developer';
    this.roleTag = "D"
  }

  onOpen(){
    this.opened = true
  }

  onClose(){
    this.opened = false;
  }

}
