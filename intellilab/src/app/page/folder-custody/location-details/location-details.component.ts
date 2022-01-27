import {
  Component,
  OnInit,
  Input,
  SimpleChanges,
  Output,
  EventEmitter
} from '@angular/core';
import { FolderLocationV2 } from '../../../shared/objects2/FolderLocationV2';
import { OperationV2, UserRoleV2, UserV2 } from '../../../shared/objects2/UserV2';
import { UserService2 } from '../../../shared/services2/User2.service';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['../folder-custody.component.css']
})
export class LocationDetailsComponent implements OnInit {

  @Input() location: FolderLocationV2;
  @Input() enableEdit: boolean;
  @Input() isNew: boolean;

  @Output() updateLocation = new EventEmitter();
  @Output() cancel = new EventEmitter();

  edit: boolean = false;
  isRequesting: boolean = false;
  locationTitle: string;
  userList: UserV2[] = [];
  
  constructor(
    private userService: UserService2
  ) { }

  ngOnInit() {
    this.locationTitle = this.location.title;
    this.getUsers();
  }

  hide() {
    this.cancel.emit();
  }

  promptEdit() {
    this.edit === true ? this.edit = false : this.edit = true;
  }

  getUsers() {
    this.userService.loadUsers().subscribe(users => {
      this.userList = users;
      // console.log(users)
    }, error => {
      
    });
  }

  requestCustodianship() {
    this.isRequesting = true;
  }

}
