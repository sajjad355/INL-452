import { FocusMonitor } from '@angular/cdk/a11y'
import { BooleanInput, coerceBooleanProperty } from '@angular/cdk/coercion'
import {
  Component,
  ElementRef,
  Inject,
  Input,
  OnDestroy,
  Optional,
  Self, ViewChild
} from '@angular/core'
import {
  AbstractControl,
  ControlValueAccessor,
  FormBuilder,
  FormGroup,
  NgControl
} from '@angular/forms'
import { MatFormField, MatFormFieldControl, MAT_FORM_FIELD } from '@angular/material/form-field'
import { Subject } from 'rxjs'

/** Custom `MatFormFieldControl` for input. */
@Component({
  selector: 'toggle-edit-input',
  templateUrl: 'toggle-edit-input.component.html',
  styleUrls: ['toggle-edit-input.component.scss'],
  providers: [
    { provide: MatFormFieldControl, useExisting: ToggleEditInputComponent }
  ],
  host: {
    '[class.example-floating]': 'shouldLabelFloat',
    '[id]': 'id',
  }
})
export class ToggleEditInputComponent
  implements ControlValueAccessor, MatFormFieldControl<any>, OnDestroy {
  static nextId = 0
  @ViewChild('#input') input: HTMLInputElement
  form: FormGroup
  stateChanges = new Subject<void>()
  focused = false
  touched = false
  controlType = 'toggle-edit-input'
  id = `toggle-edit-input-${ToggleEditInputComponent.nextId++}`
  onChange = (_: any) => {}
  onTouched = () => {}

  get empty() {
    const {
      value: { input }
    } = this.form

    return !input
  }

  get shouldLabelFloat() {
    return this.focused || !this.empty
  }

  @Input('aria-describedby') userAriaDescribedBy: string
  @Input() label: string
  @Input() hint?: string
  @Input() errorMessage?: string

  @Input()
  get placeholder(): string {
    return this._placeholder
  }
  set placeholder(value: string) {
    this._placeholder = value
    this.stateChanges.next()
  }
  private _placeholder: string

  @Input()
  get required(): boolean {
    return this._required
  }
  set required(value: boolean) {
    this._required = coerceBooleanProperty(value)
    this.stateChanges.next()
  }
  private _required = false

  @Input() forceDisabled: boolean = false
  @Input()
  get disabled(): boolean {
    return this._disabled
  }
  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value) || this.forceDisabled
    this._disabled ? this.form.disable() : this.form.enable()
    this.stateChanges.next()
  }
  private _disabled = false

  @Input()
  get value(): string | null {
    const {
      value: { input }
    } = this.form

    this.form.get('input').updateValueAndValidity()

    return input
  }
  set value(val: string | null) {
    this.form.setValue({ input: val })
    this.stateChanges.next()
  }

  get errorState(): boolean {
    return this.ngControl.invalid && this.form.dirty
  }

  constructor(
    formBuilder: FormBuilder,
    private _focusMonitor: FocusMonitor,
    private _elementRef: ElementRef<HTMLElement>,
    @Optional() @Inject(MAT_FORM_FIELD) public _formField: MatFormField,
    @Optional() @Self() public ngControl: NgControl
  ) {

    this.form = formBuilder.group({
      input: [ngControl.value, ngControl.validator]
    })

    if (this.ngControl != null) {
      this.ngControl.valueAccessor = this
    }
  }

  ngOnInit() {
    const validator = this.ngControl.control.validator
    const asyncValidator = this.ngControl.control.asyncValidator

    this.form.get('input').setValidators(validator)
    this.form.get('input').setAsyncValidators(asyncValidator)
  }

  ngOnDestroy() {
    this.stateChanges.complete()
    this._focusMonitor.stopMonitoring(this._elementRef)
  }

  onFocusIn(event: FocusEvent) {
    if (!this.focused) {
      this.focused = true
      this.stateChanges.next()
    }
  }

  onFocusOut(event: FocusEvent) {
    if (!this._elementRef.nativeElement.contains(event.relatedTarget as Element)) {
      this.touched = true
      this.focused = false
      this.onTouched()
      this.stateChanges.next()
    }
  }

  setDescribedByIds(ids: string[]) {
    const controlElement = this._elementRef.nativeElement
      .querySelector('.toggle-edit-input-container')!
    controlElement.setAttribute('aria-describedby', ids.join(' '))
  }

  onContainerClick() {
    if (this.ngControl.valid) {
      this._focusMonitor.focusVia(this.input ,'program')
    }
  }

  writeValue(val: string | null): void {
    this.value = val
  }

  registerOnChange(fn: any): void {
    this.onChange = fn
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled
  }

  _handleInput(control: AbstractControl): void {
    this.onChange(this.value)
  }

  static ngAcceptInputType_disabled: BooleanInput
  static ngAcceptInputType_required: BooleanInput
}
