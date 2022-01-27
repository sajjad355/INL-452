import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-folder-dialog',
  templateUrl: './folder-dialog.component.html',
  styleUrls: ['./folder-dialog.component.scss']
})
export class FolderDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<FolderDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
