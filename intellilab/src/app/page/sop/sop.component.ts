import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { SopService2 } from 'app/shared/services2/Sop2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from '../../page/error/error.service';
import { OPERATION_NAMES } from '../../shared/models/Constants';
import { SopV2 } from '../../shared/objects2/SopV2';
import { UserV2 } from '../../shared/objects2/UserV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';

class SearchCriteria {
  searchValue : string;
  active : boolean;
}

@Component({
  selector: 'app-sop',
  templateUrl: './sop.component.html',
  styleUrls: ['./sop.component.scss']
})
export class SopComponent implements OnInit {
  @Input() isShowSop: boolean;
  @Input() displaySop: SopV2;
  @Output() resetView = new EventEmitter();

  search : SearchCriteria = { searchValue: "", active : true };

  isNewSop: boolean;
  enableEditSop: boolean;
  displaySops: SopV2[] = [];
  rightComponents = [];
  newSop: SopV2;
  viewColumn: boolean[] = [true, true, true, true, true, true, false, false, false, false, false]
  columnList: string[] = [
    'SOP Id',
    'Name',
    'Volume',
    'Unit',
    'Description',
    'Entered Person',
    'Reviewed Person',
    'Review Comment',
    'Edit Status',
    'Active',
    'Direction'
  ]
  page: number = 1


  confirmationmessage: string = '';
  toconfirm: string = '';
  sopModal: any;
  warnModal: any;
  isLoading: boolean = true
  count:number;
  currentuser: UserV2;
  canManageSOP: boolean;

  constructor(
    private modalService: NgbModal,
    private errorService: ErrorService,
    private sopService: SopService2,
    private authenticationservice: AuthenticationService2,
    private route: ActivatedRoute,
    public attService: AttachmentService
  ) { }

  ngOnInit() {
    this.handleParam()
    let getcurrentuser = this.authenticationservice.getCurrentUser()
    if (getcurrentuser !== undefined) {
      getcurrentuser.then(_ => {
        this.currentuser = UserV2.copy(_);

        this.canManageSOP = false;
        if (this.currentuser && this.currentuser.userRoles) {
          this.canManageSOP = UserV2.isAllowedToPerform(this.currentuser, OPERATION_NAMES.EDIT_SOPS);
        }
      });
    }

    this.sopService.getCount().subscribe(c=>{
      this.count = c;
      this.loadPage();
      this.isLoading = false;
    })

    if(this.displaySop){
      this.loadItem(this.displaySop.sopId)
    }
  }

  handleParam() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")
      const inventoryId = Number(idParam)

      inventoryId !== 0 && this.loadItem(inventoryId)
    })
  }

  ngOnChanges(change: SimpleChanges) {
  }

  toggleViewCol(index: number) {
    this.viewColumn[index] = !this.viewColumn[index];
  }

  loadPage() {
    this.isLoading = true;
    this.sopService.loadSopByPage(this.page - 1).subscribe(sops => {
      this.displaySops = sops.slice();
      this.isLoading = false;
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });

  }

  loadItem(id : number ) {
    this.sopService.get(id).subscribe( s => {
      this.displaySop = SopV2.copy( s );
      this.isShowSop = true;
      this.attService.init('SOP', 'sopId-' + id)
    }, error => {
      ErrorUtil.handleHttpError( error );
      this.errorService.message$ = error.error;
    });
  }

  open(content, type?: string) { // pass modal type
    // default can not edit
    this.enableEditSop = false;
    this.isShowSop = true;

    // tell it is a new recipe
    if (type == 'nr') {
      // if a new recipe, then create a new recipe and assign to displayRecipe
      this.isNewSop = true;
      this.createNewSop();
      this.displaySop = this.newSop;
    }

    this.sopModal = this.modalService.open(content, { backdrop: "static", size: 'lg' });
    this.sopModal.result.then((result) => {
      this.enableEditSop = false;
      this.isNewSop = false;
      this.reset();
    }, () => {
      // close with X
      this.reset();
    });
  }

  createNewSop() {
    this.newSop = new SopV2();
    this.newSop.editedBy = this.currentuser.name;
    this.newSop.enteredBy = this.currentuser.name;
    this.newSop.modifiedOn = new Date();
    this.newSop.enteredTime = new Date();
    this.newSop.editStatus = 'Entered';
    this.newSop.active = true;
    this.enableEditSop = true;
    this.isShowSop = true;
    this.displaySop = this.newSop;
    this.isNewSop = true;
  }

  reset() {
    this.enableEditSop = false;
    this.isShowSop = false;
    this.displaySop = undefined;
    this.resetView.emit();
    this.isNewSop = false;
    this.page = 1;

  }

  searchSop() {
    this.isLoading = true;
    if (this.search.searchValue == undefined || this.search.searchValue == '') {
      this.sopService.getCount().subscribe(c => {
        this.count = c;
        this.page = 1;
        this.isLoading = true;
        this.loadPage();
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    } else {
      this.displaySops = [];
      this.sopService.searchSopPageable(this.search.searchValue, this.page - 1, this.search.active  ).subscribe(result => {
        this.displaySops = result.slice();
        this.sopService.searchSopCount(this.search.searchValue, this.search.active ).subscribe(count => {
          this.count = count;
        });
        this.isLoading = false;
      }, error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
      });
    }
  }



  saveSop( sop: SopV2 ) {
    if (!sop) { return; }
    this.sopService.save(sop).then( s => {
      this.reset();
      this.loadPage();
    }).catch( error => {
        ErrorUtil.handleHttpError( error );
        this.errorService.message$ = error.error;
    });
  }

  resetSopDisplay(s: SopV2) {
    this.displaySop = s;
    this.enableEditSop = false;
    this.isNewSop = false;
  }


}

