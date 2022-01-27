import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core'
import { AbstractControl, AsyncValidatorFn, FormArray, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms'
import { ActivatedRoute, ActivatedRouteSnapshot, Params, Router } from '@angular/router'
import { ErrorService } from 'app/page/error/error.service'
import { SettingV2 } from 'app/shared/objects2/SettingV2'
import { UserV2 } from 'app/shared/objects2/UserV2'
import { SettingService2 } from 'app/shared/services2/Setting2.service'
import { Observable } from 'rxjs'
import { map } from 'rxjs/operators'

@Component({
  selector: 'app-setting-details',
  templateUrl: './setting-details.component.html',
  styleUrls: ['./setting-details.component.scss'],
})
export class SettingDetailsComponent implements AfterViewInit {
  @Input() currentUser: UserV2
  @Output() toggleSidePanel = new EventEmitter()

  settingId: number
  alerts: String[] = []
  editEnabled = false
  loadingDetail = false
  saveLoading = false
  setting: SettingV2 = new SettingV2()
  settingForm: FormGroup = this.fb.group({
    description: '',
    name: ['', [Validators.required]],
    settingValues: this.fb.array([])
  })
  _isNew = false

  get isNew() {
    return this._isNew
  }

  set isNew(isNew: boolean) {
    this._isNew = isNew
    this.initForm(isNew)
  }

  isSaved = false
  routeSnapshot: ActivatedRouteSnapshot

  constructor(
    private settingService: SettingService2,
    private errorService: ErrorService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
  ) {
    this.handleParam()
  }

  ngAfterViewInit() {
    // https://indepth.dev/posts/1001/everything-you-need-to-know-about-the-expressionchangedafterithasbeencheckederror-error
    setTimeout(() => this.setEditEnabled(false))
  }

  handleParam() {
    this.route.params.subscribe((params: Params) => {
      const { id } = params
      if (!id) { return }

      if (id === 'new') {
        this.isNew = true
        this.addSettingValue()
        setTimeout(() => {
          this.toggleSidePanel.emit(true)
        })

        setTimeout(() => {
          this.setEditEnabled(true)
        }, 100)

        return
      }

      const settingId = Number(id)

      if (settingId !== 0) {
        this.initForm()
        this.settingId = settingId
        this.settingService.get(settingId).subscribe((setting: SettingV2) => {

          setting.settingValues.forEach((settingValue) => {
            this.settingValues.push(this.fb.group(settingValue))
          })

          this.settingForm.patchValue(setting)
          this.toggleSidePanel.emit(true)
        })
      }
    })
  }

  initForm(isNew = false) {
    const asyncValidators = isNew ? [this.validateNameUnique(this.settingService)] : []

    this.settingForm = this.fb.group({
      description: '',
      name: ['', [Validators.required], asyncValidators],
      settingValues: this.fb.array([]),
      modifiedOn: new Date()
    })
  }

  validateNameUnique(settingService: SettingService2): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const error = settingService.checkSettingExists(control.value)
        .pipe(map(res => {
          return res > 0 ? { nameExists: 'Setting already exists' } : null
        }))

      return error
    }
  }

  async saveSetting() {
    const formValues = this.settingForm.value

    this.saveLoading = true
    await this.settingService.saveSetting(this.settingId, formValues)
    this.showChangesSaved()
  }

  setEditEnabled(enabled) {
    this.editEnabled = enabled
    if (this.editEnabled) {
      this.isSaved = false
      this.settingForm.enable()
    } else {
      this.settingForm.disable()
    }
  }

  get settingValues() {
    return this.settingForm.get('settingValues') as FormArray
  }

  addSettingValue() {
    const settingValue = this.fb.group({
      value: '',
      attribute: '',
      modifiedOn: new Date()
    })

    this.settingValues.push(settingValue)
  }

  removeSettingValues(index: number) {
    this.settingValues.removeAt(index)
  }

  close = () => {
    this.router.navigate(['/main/config/settings'])
    this.toggleSidePanel.emit(false)
  }

  private showChangesSaved() {
    this.isSaved = true
    this.saveLoading = false
    this.setEditEnabled(false)
  }
}
