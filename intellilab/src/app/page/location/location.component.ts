import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { LocationService2  } from '../../shared/services2/Location2.service';
import { LocationV2 } from '../../shared/objects2/LocationV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserV2 } from '../../shared/objects2/UserV2';
import { ErrorService } from '../../page/error/error.service';




@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})


export class LocationComponent implements OnInit {

  locations : LocationV2[] = [];
  selectedLocation : LocationV2;
  newLocation : boolean = false;
  inputWarning : String[] = [];
  hideWarning : boolean = true;
  locationState : String;
  subLocationState : String;
  currentUser: UserV2;

  @ViewChild('locationSelection') locationSelectionRef: ElementRef;

  constructor(private locationService: LocationService2,
              private errorService: ErrorService,
              private authenticationService: AuthenticationService2) { }

  ngOnInit() {
    this.loadPage();
  }

  loadPage() {
    // make a page specific copy of the globally cached locations so we only work with a local copy until we are done
    let locationsTemp : LocationV2[] = this.locationService.getAllLocations();
    locationsTemp.forEach( aLocation => {
      this.locations.push( LocationV2.copyLocationV2( aLocation ));
    });
    this.locationState = undefined;
    this.subLocationState = undefined;
    this.authenticationService.getCurrentUser().then( aUser => {
        this.currentUser = UserV2.copy( aUser );
    });
  }

  createNewLocation() {
    if ( this.areChangesPending() ) {
      this.showChangesPendingWarning();
    }
    else {
      this.selectedLocation = new LocationV2();
      this.selectedLocation.locationName = "Enter New Location Name";
      this.selectedLocation.locationId = -1;
      this.locationState = 'updated';
      this.newLocation = true;
    }
  }

  addSubLocation() {
    if ( !this.selectedLocation ) return;
    this.subLocationState = 'updated';
    let subLocation : LocationV2 = new LocationV2();
    subLocation.locationName = "Enter New Sub Location Name";
    this.selectedLocation.subLocations.push( subLocation );
  }

  selectLocation( locationId : number ) {
    if ( this.areChangesPending() ) {
      this.showChangesPendingWarning();
      //force the location selection back to selectedLocation.locationId
      this.locationSelectionRef.nativeElement.value = this.selectedLocation.locationId;
    }
    else {
      this.selectedLocation = this.locations.find( x => x.locationId == locationId );
    }
  }

  removeSubLocation( index : number ) {
    if ( !this.selectedLocation ) return;

    if(this.selectedLocation.subLocations[index].locationId){
      this.locationService.getReferenceCount( this.selectedLocation.subLocations[index].locationId ).subscribe( count  => {
        if ( count  >  0 ) {
          this.showRemoveWarning('Location is referenced by other database records and cannot be deleted');
          return;
        }

        this.subLocationState = 'updated';
        this.selectedLocation.subLocations.splice( index, 1 );
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }else{
      this.selectedLocation.subLocations.splice( index, 1 );
    }
  }

  showRemoveWarning( warning : string ) {
    this.inputWarning.push( warning );
    this.hideWarning = false;
    setTimeout(() => {
        this.inputWarning = [];
        this.hideWarning = true;
    }, 5000);
  }

  saveLocation() {
    if (!this.selectedLocation ) return;
    if (!this.validateLocation( this.selectedLocation )) return;
    this.selectedLocation.subLocations.forEach( aSubLocation => {
      if (!this.validateLocation( aSubLocation )) return;
    });


    this.updateActivityDetails( this.selectedLocation );
    this.locationService.saveLocation( this.selectedLocation ).then( ()  => {
      this.loadPage();
      this.showChangesSaved();
    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  addLocation() {
    if (!this.validateLocation( this.selectedLocation ) ) return;
    this.locations.push( this.selectedLocation );
    this.newLocation = false;
  }

  cancelAdd() {
    this.newLocation = false;
    this.locationState = undefined;
  }

  validateLocation( aLocation : LocationV2) : boolean {
    let result = true;
    if ( !aLocation.locationName ) {
      this.inputWarning.push('Location Name required');
      this.hideWarning = false;
      result = false;
    }
    if (!this.hideWarning ) {
      setTimeout(() => {
        this.inputWarning = [];
        this.hideWarning = true;
      }, 5000);
    }
    return result;
  }

  private updateActivityDetails( aLocation : LocationV2  ) {
    aLocation.modifiedOn = new Date();
    aLocation.editedBy = this.currentUser.name;
    aLocation.subLocations.forEach( aSubLocation => {
      aSubLocation.editedBy = this.currentUser.name;
      aSubLocation.modifiedOn = new Date();
    });
  }

  private areChangesPending() : boolean {
    if ( this.locationState == 'updated' || this.subLocationState == 'updated' )
      return true;
    else
      return false;
  }

  private showChangesPendingWarning() {
    this.inputWarning.push('You have unsaved changes for the current location');
    this.hideWarning = false;
    setTimeout(() => {
      this.inputWarning = [];
      this.hideWarning = true;
    }, 5000);
  }

  private showChangesSaved() {
    this.inputWarning.push('Changes saved successfully');
    this.hideWarning = false;
    setTimeout(() => {
      this.inputWarning = [];
      this.hideWarning = true;
    }, 5000);
  }



}
