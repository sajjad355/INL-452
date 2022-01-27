import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { KitService2 } from 'app/shared/services2/Kit2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { KitV2 } from '../../shared/objects2/KitV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { DateFormatType, SettingService2 } from '../../shared/services2/Setting2.service';


class SearchCriteria {
  searchValue : string;
  active : boolean;
}


@Component({
  selector: 'app-kit',
  templateUrl: './kit.component.html',
  styleUrls: ['./kit.component.scss']
})
export class KitComponent implements OnInit {

  @ViewChild('confirmation') confirmation: ElementRef;
  @Output() sendorder = new EventEmitter();

  search : SearchCriteria = { searchValue: "", active : true };
  kits : KitV2[] = [];
  displayKit: KitV2;
  buildKit: KitV2;
  viewColumn: boolean[];
  columnList: string[];
  page: number;
  kitCount: number;
  colCount: number;
  isLoading: boolean;
  buildKitModal: any;
  currentuser: UserV2;
  dateFormat: string;
  dateFormatDisplay: string;
  canManageKit: boolean = false;
  isNewKit: boolean;

  constructor(
      private modalService: NgbModal,
      private errorService: ErrorService,
      private kitService: KitService2,
      private settingService: SettingService2,
      private authenticationservice: AuthenticationService2,
      private router: Router,
      private route: ActivatedRoute,
      public attService: AttachmentService
  ) {}

  async ngOnInit() {
    this.loadSetting();
    this.page = 1;

    this.loadPage();

    this.viewColumn = [true, true, true, true, true, false, false, false, false,
      false, false, false, false, false, false, false];
    this.columnList = [
    'Cat #',
    'Name',
    'Type',
    'Size',
    'Price(USD)',
    'Edit Status',
    'Description',
    'Client Specific',
    'Molecule (Innovator)',
    'Biosimilar',
    'Status',
    'Entered By',
    'Reviewed By',
    'Review Comment',
    'Modified On',
    'Comment'];

    await this.loadUser()
    this.handleParam()
  }

  async loadUser() {
    const user = await this.authenticationservice.getCurrentUser();
    this.currentuser = UserV2.copy(user);

    if (this.currentuser && this.currentuser.userRoles) {
      this.canManageKit = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.EDIT_KITS);
    }

  }

  loadSetting() {
    this.dateFormat = this.settingService.getDateFormat( DateFormatType.DatePickerUsage );
    this.dateFormatDisplay = this.settingService.getDateFormat( DateFormatType.UserDisplay );
  }

  handleParam() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id')

      if (idParam === 'new') {
        this.createNewKit()
        return
      }

      const kitId = Number(idParam)
      kitId !== 0 && this.loadKit(kitId)
    })
  }

  loadPage() {
    this.isLoading = true;
    this.kitService.loadKitByPage(this.page - 1).subscribe(kits => {
      this.kits = kits.slice();
      this.kitService.count().subscribe(c => {
        this.kitCount = c;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
      this.isLoading = false;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  loadKit(id: number) {
    this.isLoading = true;
    this.kitService.get( id ).subscribe( kit => {
      this.displayKit = KitV2.copy( kit );
      this.isLoading = false;
      this.attService.init('Kit', 'kitId-' + id)
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  searchKit() {
    this.page = 1;
    if (this.search.searchValue == undefined || this.search.searchValue == '') {
      this.loadPage();
    }
    else {
      this.isLoading = true;
      this.kitService.searchPageable(this.search.searchValue, this.search.active, this.page - 1 ).subscribe(result => {
        this.kits = result.slice();
        this.kitService.searchCount(this.search.searchValue, this.search.active).subscribe(count => {
          this.kitCount = count;
        }, error => {
          this.isLoading = false;
          ErrorUtil.handleHttpError( error );
          this.errorService.message$ = error.error;
        });
        this.isLoading = false;
      }, error => {
        this.isLoading = false;
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }

  closeKitDisplay = () => {
    this.router.navigate(['/main/kits'])
  }

  createNewKit() {
    this.isNewKit = true
    this.displayKit = new KitV2({
      salesItemId: 0,
      editedBy: this.currentuser.name,
      enteredBy: this.currentuser.name,
      editStatus: 'Entered',
      active: true,
      enteredTime: new Date(),
      modifiedOn: new Date(),
    });
  }

  saveKit( kit : KitV2 ) {
    if (!kit) { return; }
    this.kitService.save(kit).then( s => {
      this.closeKitDisplay();
      this.searchKit();
    }).catch( error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }


  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
    this.colCount = 0;
    this.viewColumn.forEach(c => {
      if (c) { this.colCount++; }
    });
  }

  openBuildKitModal(modal : any, kitSalesItemId : number ) {
    this.kitService.get( kitSalesItemId ).subscribe( kit => {
      this.buildKit = KitV2.copy( kit );
      this.buildKitModal = this.modalService.open(modal, { backdrop: 'static', size: 'lg' });
    }, error => {
      this.isLoading = false;
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  buildThisKit() {
    alert( 'todo');

    // note - need to:
    // a) Redo Inventory data model
    // b) link KitComponent with Inventory item it derived from
    // c) for each KitComponent, see if we have enough Inventory in stock for this component
    // d) If not add an OrderItem to array to push back to main to then pass along to order item component
    //    by calling sendorder.emit with list of order items
  }


  convertunit(amount: number, unit: string) {
    unit = unit.trim();
    if (unit == 'mL') {
      amount = Math.pow(10, 3) * amount;
      unit = 'μL';
    }
    if (unit == 'L') {
      amount = Math.pow(10, 6) * amount;
      unit = 'μL';
    }

    if (unit == 'pg') {
      amount = Math.pow(10, -6) * amount;
      unit = 'μg';
    }
    if (unit == 'ng') {
      amount = Math.pow(10, -3) * amount;
      unit = 'μg';
    }
    if (unit == 'mg') {
      amount = Math.pow(10, 3) * amount;
      unit = 'μg';
    }
    if (unit == 'g') {
      amount = Math.pow(10, 6) * amount;
      unit = 'μg';
    }
    if (unit == 'kg') {
      amount = Math.pow(10, 9) * amount;
      unit = 'ug';
    }

    return {amount: amount, unit: unit};
  }



}
