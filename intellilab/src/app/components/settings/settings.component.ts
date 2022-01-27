import {
  animate, state,
  style, transition, trigger
} from '@angular/animations'
import { Component, ElementRef, OnInit, ViewChild, ViewEncapsulation } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import * as _ from 'lodash'
import { SettingV2 } from '../../shared/objects2/SettingV2'
import { SettingService2 } from '../../shared/services2/Setting2.service'
import { DeleteWarningDialogComponent } from './delete-warning-dialog/delete-warning-dialog.component'

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: [
    trigger('flyOut', [
      state('in', style({ transform: 'translateX(0)' })),
      transition('* => void', [
        animate(800, style({ backgroundColor: '#bdbdbd', position: 'absolute', left: '-100%', transform: 'translateX(-100%)' }))
      ])
    ])
  ]
})
export class SettingsComponent implements OnInit {
  _ = _
  settings: SettingV2[] = []
  displayedColumns: string[] = ['name', 'control']
  showSettingDetail = false

  @ViewChild('settingSelection') settingSelectionRef: ElementRef

  constructor(
    private settingService: SettingService2,
    private dialog: MatDialog
  ) { }

  async ngOnInit() {
    this.loadPage()
  }

  loadPage() {
    this.settings = this.settingService.getAllSettings()
  }

  toggleSidePanel(isOpened: boolean) {
    this.showSettingDetail = isOpened
  }

  deleteSetting(id: number) {
    const dialogRef = this.dialog.open(
      DeleteWarningDialogComponent,
      {
        data: {
          onConfirm: async () => {
            await this.settingService.delete(id)
            this.settings = this.settings.filter(setting => setting.settingId !== id)
          }
        }
      }
    )

    dialogRef.afterClosed().subscribe((data) => {
      if (_.isFunction(data?.onConfirm)) { data.onConfirm() }
    })
  }
}
