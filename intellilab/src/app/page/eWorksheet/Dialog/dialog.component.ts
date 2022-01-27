import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  disableChoice: boolean = false;

  constructor(public dialogRef: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    if(this.data.timerMessageArray){
      if(this.data.timerMessageArray.length > 0){
        this.disableChoice = true;
      }
    }

    if(this.data.disableChoice){
      this.disableChoice = this.data.disableChoice;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
