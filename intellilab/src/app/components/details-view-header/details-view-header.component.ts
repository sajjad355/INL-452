import { Component, EventEmitter, Input, Output } from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'app-details-view-header',
  templateUrl: './details-view-header.component.html',
  styleUrls: ['./details-view-header.component.scss']
})
export class DetailsViewHeaderComponent {
  @Input() type: string
  @Input() name: string
  @Input() isNew: boolean = false
  @Input() subHeader?: string
  @Input() close: () => void

  @Output() updateEditEnabled = new EventEmitter()

  // lodash
  _ = _
  editEnabled: boolean = false

  enableEdit() {
    this.updateEditEnabled.emit(!this.editEnabled)
    this.editEnabled = !this.editEnabled
  }
}
