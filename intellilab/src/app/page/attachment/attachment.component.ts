import { Component, ComponentRef, ElementRef, EventEmitter, HostBinding, Input, OnInit, Output, ViewChild } from '@angular/core';
import { RenameDialogComponent } from 'app/file-management-system/file-explorer/modals/rename-dialog/rename-dialog.component';
import { FileElement } from 'app/file-management-system/file-explorer/model/file-element';
import { AttachmentService } from 'app/shared/services/attachment.service';
import { MatDialog } from '@angular/material/dialog';
import { Subject, Subscription } from 'rxjs';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';

@Component({
  selector: 'app-attachment',
  templateUrl: './attachment.component.html',
  styleUrls: ['./attachment.component.scss']
})
export class AttachmentComponent implements OnInit {
  public QSQ: Subject<FileElement[]>;
  private attSub: Subscription
  private hostRef
  isLoading: boolean = false
  numOfAtt: number = 0
  @Input() padding = '0px';


  constructor(
    private attachmentService: AttachmentService,
    private domRef:ElementRef,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.hostRef = this.domRef.nativeElement.children[0]
    this.QSQ = this.attachmentService.getQSQ()
    this.attSub = this.QSQ.subscribe(res => {
        console.log('List of attachments is updated:')
        console.log(res)
        this.numOfAtt = 0
        if(res) this.numOfAtt = res.length
        this.setLoading(false)
        this.showPanel()
    })
  }

  setLoading(value: boolean){
    this.isLoading = value
  }

  uploadFile(event) {
    this.setLoading(true)
    this.attachmentService.attachFile(event)
  }

  deleteFile(fileElement: FileElement){
    this.setLoading(true)
    this.attachmentService.delete(fileElement._id)
  }

  downloadFile(fileElement: FileElement){
    this.attachmentService.download(fileElement)
  }

  updateFile(fileElement: FileElement){
    this.setLoading(true)
    this.attachmentService.update(fileElement._id, fileElement)
  }

  openRenameDialog(element: FileElement) {
    let dialogRef = this.dialog.open(RenameDialogComponent, { panelClass: 'dialog-customized' });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        element.name = res;
        this.updateFile(element)
      }
    });
  }

  showPanel(){
    this.hostRef.style.transition = 'all ease-out 0.3s'
    this.hostRef.style.top = '0px'
    this.hostRef.style.height = '100%'
  }

  closePanel(){
    this.hostRef.style.transition = 'all ease-in 0.3s'
    this.hostRef.style.top = '-120%'
    this.hostRef.style.height = '0px'
  }

  openMenu(event: MouseEvent, viewChild: MatMenuTrigger) {
    event.preventDefault();
    viewChild.openMenu()
  }

  ngOnDestroy(){
    if(this.attSub){
      this.attSub.unsubscribe()
    }
  }
}
