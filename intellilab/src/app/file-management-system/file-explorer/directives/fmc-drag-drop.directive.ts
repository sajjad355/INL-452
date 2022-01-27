import { Directive, ElementRef, EventEmitter, HostBinding, HostListener, Input, Output } from '@angular/core';

@Directive({
  selector: '[appFmcDragDrop]'
})
export class FmcDragDropDirective {
  bg_image_url = "../../../../assets/images/file-upload.png"
  @Input() enableFileUpload: boolean = true;
  @Output() onFileDropped = new EventEmitter<any>();

  @HostBinding('style.background-color') private background = 'white'
  @HostBinding('style.opacity') private opacity = '1'
  // @HostBinding('style. background-image') private bg_image = 'url(../../../../assets/images/file-upload.png)'
  // @HostBinding('style.cursor') private cursor = 'pointer'

  //Dragover listener
  @HostListener('dragover', ['$event']) onDragOver(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    if(this.enableFileUpload){
      this.background = '#9ecbec';
      this.opacity = '0.8'
    }
  }
  //Dragleave listener
  @HostListener('dragleave', ['$event']) public onDragLeave(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    if(this.enableFileUpload){
      this.background = 'white'
      this.opacity = '1'
    }
  }
  //Drop listener
  @HostListener('drop', ['$event']) public ondrop(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    if(this.enableFileUpload){
      this.background = 'white'
      this.opacity = '1'
      let files = evt.dataTransfer.files;
      if (files.length > 0) {
        this.onFileDropped.emit(files)
        console.log('Files:')
        console.log(files)
      }
    }
  }

}
