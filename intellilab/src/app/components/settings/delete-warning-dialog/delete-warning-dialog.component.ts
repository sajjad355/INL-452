import { Component, Inject } from '@angular/core'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'

@Component({
  selector: 'app-delete-warning-dialog',
  templateUrl: 'delete-warning-dialog.component.html',
  styleUrls: ['./delete-warning-dialog.component.scss']
})
export class DeleteWarningDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: object,
    public dialogRef: MatDialogRef<DeleteWarningDialogComponent>
  ) { }

  onConfirm() {
    this.dialogRef.close(this.data)
  }

  onCloseDialog() {
    this.dialogRef.close()
  }
}
