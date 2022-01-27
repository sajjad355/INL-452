import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FolderLocation2  } from '../../../shared/services2/FolderLocation2.service';
import { FolderLocationV2 } from '../../../shared/objects2/FolderLocationV2';
import { AuthenticationService2 } from '../../../shared/services2/Authenticate2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../../shared/objects2/UserV2';
import { UserService2 } from '../../../shared/services2/User2.service';
import { OPERATION_NAMES } from '../../../shared/models/Constants';
declare var require: any
var JsBarcode = require('jsbarcode');

@Component({
  selector: 'app-folder-location',
  templateUrl: './folder-location.component.html',
  styleUrls: ['./folder-location.component.css']
})
export class FolderLocationComponent implements OnInit {

  locations : FolderLocationV2[];

  selectedLocation : FolderLocationV2;
  newLocation : FolderLocationV2;
  isNewLocation : boolean = false;
  inputWarning : String[] = [];
  hideWarning : boolean = true;
  showDetails : boolean = false;
  locationState : String;
  subLocationState : String;
  currentUser: UserV2;
  employeeList: UserV2[] = [];
  managerList: UserV2[] = [];

  @ViewChild('locationSelection', {static: false} ) locationSelectionRef: ElementRef;

  constructor(
    private folderLocationService: FolderLocation2,
    private authenticationService: AuthenticationService2,
    private userService: UserService2,
  ) { }

  ngOnInit() {
    this.loadPage();
    this.getUsers();
  }

  renderBarcode()	{
    let divDOM = document.getElementById("barcode");
    if(divDOM) divDOM.innerHTML = '';
    let svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute('jsbarcode-format', 'code128')
    svg.setAttribute('jsbarcode-value', this.selectedLocation.barcode)
    svg.setAttribute('jsbarcode-width', '2')
    svg.setAttribute('jsbarcode-height', '80')
    svg.setAttribute('jsbarcode-fontsize', '20')
    svg.className.baseVal = "barcode";
    document.getElementById("barcode") && divDOM.appendChild(svg);
    JsBarcode(".barcode").init();
  }

  printBarcode() {
    var disp_setting="toolbar=yes,location=no,";
    disp_setting+="directories=yes,menubar=yes,";
    disp_setting+="scrollbars=yes,width=650, height=600, left=100, top=25";
    var locationName = "<p>"+this.selectedLocation.title+"</p>";
    var barcode = document.getElementById('barcode').innerHTML;
    var docprint=window.open("","",disp_setting);
    docprint.document.open();
    docprint.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"');
    docprint.document.write('"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">');
    docprint.document.write('<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">');
    docprint.document.write('<head><title>Print Location Barcode</title>');
    docprint.document.write('<style type="text/css">body{ margin: 0; } p { padding: 0; margin: 10px 0 10px 10px; font-size: 18px; font-family: sans-serif; }</style>');
    docprint.document.write('</head><body onLoad="self.print()">');
    docprint.document.write(locationName);
    docprint.document.write(barcode);
    docprint.document.write('</body></html>');
    docprint.document.close();
    docprint.focus();
  }

  loadPage() {
    this.folderLocationService.loadLocationsByPage(0).subscribe(locations => {
      this.locations = locations;
    }, error => {
      ErrorUtil.handleHttpError( error );
    });
  }

  getUsers() {
    this.userService.loadUsers().subscribe(users => {
      this.employeeList = users;
      users.map(user => {
        if(UserV2.isAllowedToPerform(user, OPERATION_NAMES.MANAGE_FOLDERS)) {
          this.managerList.push(user)
        }
      })
    }, error => {

    });
  }

  createNewLocation() {
    this.newLocation = new FolderLocationV2();
    this.newLocation.title = "";
    this.newLocation.dbid = -1;
    this.isNewLocation = true;
  }

  selectLocation(dbid: number) {
    this.selectedLocation = new FolderLocationV2();
    this.selectedLocation = this.locations.find(x => x.dbid == dbid);
    // if(this.selectedLocation) this.renderBarcode();
    if(this.selectedLocation) {
      this.showDetails = true;
      setTimeout(() => this.renderBarcode(), 10);
    }
  }

  setEmployee(userId, status) {
    if(status === 'new') {
      let employee = this.employeeList.find(user => user.userId == userId);
      if(employee) this.newLocation.employee = employee;
    }
    else {
      let employee = this.employeeList.find(user => user.userId == userId);
      if(employee) this.selectedLocation.employee = employee;
    }
  }

  setProjectManager(userId, status) {
    if(status === 'new') {
      let manager = this.managerList.find(user => user.userId == userId);
      if(manager) this.newLocation.projectManager = manager;
    }
    else {
      let manager = this.managerList.find(user => user.userId == userId);
      if(manager) this.selectedLocation.projectManager = manager;
    }
  }

  saveLocation() {
    // if (!this.selectedLocation ) return;
    // if (!this.validateLocation( this.selectedLocation )) return;
    // this.selectedLocation.subLocations.forEach( aSubLocation => {
    //   if (!this.validateLocation( aSubLocation )) return;
    // });


    // this.updateActivityDetails( this.selectedLocation );
    // this.folderLocationService.saveLocation( this.selectedLocation ).then( ()  => {
    //   this.loadPage();
    //   this.showChangesSaved();
    // }).catch( error => {
    //   ErrorUtil.handleHttpError( error );
    // });

    // console.log(this.selectedLocation);
    this.folderLocationService.saveLocation(this.selectedLocation).then((location) => {
      this.loadPage();
      this.selectedLocation = location as FolderLocationV2;
      this.isNewLocation = false;
      this.showDetails = true;
      setTimeout(() => this.renderBarcode(), 10);
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
    });
  }

  addLocation() {
    this.folderLocationService.saveLocation(this.newLocation).then((location) => {
      this.loadPage();
      this.selectedLocation = location as FolderLocationV2;
      this.isNewLocation = false;
      this.showDetails = true;
      setTimeout(() => this.renderBarcode(), 10);
    }).catch(error => {
      ErrorUtil.handleHttpError(error);
    });
  }

  cancelAdd() {
    this.showDetails = false;
    this.isNewLocation = false;
    this.locationState = undefined;
  }

  hide() {
    this.showDetails = false;
    this.selectedLocation = new FolderLocationV2();
    this.isNewLocation = false;
  }

}
