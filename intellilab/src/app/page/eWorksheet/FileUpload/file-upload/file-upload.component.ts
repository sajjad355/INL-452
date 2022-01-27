import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import * as lodash from "lodash";
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../../Dialog/dialog.component';
// The source code for download() is located in the assests folder with the name of download.js
declare var download: any;

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {

  fileReader;
  files: any = [];
  // files_base64: any = [];
  fileConfig = {
    name: null,
    type: null,
    url: null
  };
  fileName;
  fileType;
  upload_view = true;
  image_view = false;

  @Input() initData = [];

  @Input() enableFileUpload = true;

  @Output() uploadChange = new EventEmitter();

  constructor(private dialog: MatDialog) {
    this.fileReader = new FileReader();

    this.fileReader.onload = () => {
      const base64String = this.fileReader.result as string;
      // users are allowed to upload no more than 3 files
      if(this.files.length < 6){
        switch(this.fileType){
          case 'image/gif':
          case 'image/jpeg':
          case 'image/png':
          case 'application/pdf':
            this.files.push({
              dbid: -1,
              name: this.fileName,
              type: this.fileType,
              data: base64String
            });
            this.uploadChange.emit({files: this.files});
            console.log("files_base64 array is updated for uploading new file.")
            break;
          default:
            console.log("uploaded file type is not valid: " + this.fileType);
            const message = 'File type is not valid, please upload files with one of the valid types: .pdf .png .jpg .jpeg .gif';
            this.openDialog(message);
        }
      }else{
        const message = 'Please upload no more than 6 files.';
        this.openDialog(message);
      }

    };
   }

  ngOnInit() {
    if(this.initData.length > 0){
      this.initData.forEach(filePayload => {
        this.files.push({
          dbid: filePayload.dbid,
          name: filePayload.name,
          type: filePayload.type,
          data: filePayload.data
        });
      })

      console.log("The edit data for file upload component is(through component initialization): ");
      console.log(this.files)
    }else{
      console.log("File upload component's init data is empty(through component initialization)")
    }
  }

  initFileData(payload){
    if(payload.length > 0){
      payload.forEach(filePayload => {
        this.files.push({
          dbid: filePayload.dbid,
          name: filePayload.name,
          type: filePayload.type,
          data: filePayload.data
        });
      })

      console.log("File init data payload content(through init function call): ");
      console.log(payload)
    }else{
      console.log("Empty file init payload(through init function call)")
    }
  }

  uploadFile(event) {
    for (let index = 0; index < event.length; index++) {
      const element = event[index];
      this.fileName = element.name;
      this.fileType = element.type;
      this.fileReader.readAsDataURL(element);
    }
  }


  downloadFile(dataURL, fileName, fileType){
    new download(dataURL, fileName, fileType)
    console.log(`Download file (${fileName}) to local storage.`)
  }


  deleteAttachment(index) {
    this.files.splice(index, 1);
    // this.files_base64.splice(index, 1);
    this.uploadChange.emit({files: this.files});
    console.log("files_base64 array is updated for deattching a file.")
  }



  fileReview(fileIndex){
        this.fileConfig.url = this.files[fileIndex].data;
        this.fileConfig.name = this.files[fileIndex].name;
        this.fileConfig.type = this.files[fileIndex].type;
  }

  viewSwitch(event){
    // console.log("slide toggle view change event's body: ");
    // console.log(event);
    this.upload_view = !this.upload_view;
    this.image_view = !this.image_view;
  }

  openDialog(message) {
    this.dialog.open(DialogComponent, {data: {message: message, disableChoice: true}});

  }

}
