import { Component, ElementRef, OnInit, ViewChild } from '@angular/core'
import { Address } from 'app/shared/objects2/PurchaseOrderV2'
import { ErrorUtil } from 'app/shared/util/ErrorUtil'
import { ItemSourceV2 } from '../../shared/objects2/ItemSourceV2'
import { UserRoleV2, UserV2 } from '../../shared/objects2/UserV2'
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service'
import { ItemSourceService2 } from '../../shared/services2/ItemSource2.service'
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service'

@Component({
  selector: 'app-item-sources',
  templateUrl: './item-sources.component.html',
  styleUrls: ['./item-sources.component.css']
})


export class ItemSourcesComponent implements OnInit {
  sources: ItemSourceV2[] = []
  itemSourceType = 'Supplier'
  dateFormatDisplay: string

  selectedSource: ItemSourceV2
  newSource: ItemSourceV2
  newLocation = false
  inputWarning: String[] = []
  hideWarning = true
  locationState: String
  subLocationState: String
  currentuser: UserV2
  currentusername = ''
  currentUserRoles: UserRoleV2[] = []

  @ViewChild('locationSelection', {static: false} ) locationSelectionRef: ElementRef

  constructor(private itemSourceService: ItemSourceService2,
              private authenticationService: AuthenticationService2,
              private settingService: SettingService2,
              private authenticationservice: AuthenticationService2, ) { }

  ngOnInit() {
    const getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then((_) => {
        this.currentuser = UserV2.copy(_)
        this.currentusername = this.currentuser.name
      })
    }
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay )
  }

  loadPage(type) {
    this.itemSourceService.loadItemSourcesBy(type, true).subscribe((sources: ItemSourceV2[]) => {
      this.sources = sources
    }, error => {
      ErrorUtil.handleHttpError( error )
    })
  }

  createNewLocation() {
    if (this.areChangesPending()) {
      this.showChangesPendingWarning()
    } else {
      this.selectedSource = new ItemSourceV2()
      this.selectedSource.name = ''
      this.selectedSource.type = ''
      this.selectedSource.approvalType = ''
      this.selectedSource.dbid = -1
      this.selectedSource.modifiedOn = new Date()
      this.selectedSource.editedBy = this.currentusername
      this.locationState = 'updated'
      this.newLocation = true
    }
  }

  selectSource( dbid: number ) {
    if ( this.areChangesPending() ) {
      this.showChangesPendingWarning()
      this.locationSelectionRef.nativeElement.value = this.selectedSource.dbid
    } else {
      this.selectedSource = this.sources.find( x => x.dbid == dbid ) as ItemSourceV2
      if (!this.selectedSource.billingAddress) { this.selectedSource.billingAddress = new Address() }
    }
  }

  showRemoveWarning( warning: string ) {
    this.inputWarning.push( warning )
    this.hideWarning = false
    setTimeout(() => {
      this.inputWarning = []
      this.hideWarning = true
    }, 5000)
  }

  saveLocation() {
    if (!this.selectedSource ) { return }
    if (!this.validateLocation(this.selectedSource)) { return }
    this.selectedSource.modifiedOn = new Date()
    this.selectedSource.editedBy = this.currentusername
    this.itemSourceService.saveItemSource(this.selectedSource).then( ()  => {
      this.loadPage(this.selectedSource.type)
      this.showChangesSaved()
    }).catch( error => {
      ErrorUtil.handleHttpError( error )
    })
  }

  addLocation() {
    if (!this.validateLocation(this.selectedSource)) { return }
    this.sources.push(this.selectedSource)
    this.newLocation = false
  }

  cancelAdd() {
    this.newLocation = false
    this.selectedSource = undefined
    this.locationState = undefined
  }

  validateLocation( aSource: ItemSourceV2): boolean {
    this.inputWarning = []
    let result = true
    if (!aSource.name) {
      this.inputWarning.push('Item source name is required')
      this.hideWarning = false
      result = false
    }
    if (!aSource.type) {
      this.inputWarning.push('Item source type is required')
      this.hideWarning = false
      result = false
    }
    if (!aSource.approvalType) {
      this.inputWarning.push('Approval type is required')
      this.hideWarning = false
      result = false
    }
    if (!this.hideWarning ) {
      setTimeout(() => {
        this.inputWarning = []
        this.hideWarning = true
      }, 5000)
    }
    return result
  }

  private areChangesPending(): boolean {
    if ( this.locationState == 'updated' || this.subLocationState == 'updated' ) {
      return true
    } else {
      return false
    }
  }

  private showChangesPendingWarning() {
    this.inputWarning.push('You have unsaved changes for the current location')
    this.hideWarning = false
    setTimeout(() => {
      this.inputWarning = []
      this.hideWarning = true
    }, 5000)
  }

  private showChangesSaved() {
    this.inputWarning.push('Changes saved successfully')
    this.hideWarning = false
    setTimeout(() => {
      this.inputWarning = []
      this.hideWarning = true
    }, 5000)
  }



}
