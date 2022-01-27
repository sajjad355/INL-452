import { Component, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { FileElement } from './file-explorer/model/file-element';
import { FileExplorerService } from './file-explorer/service/file-explorer.service';
import * as lodash from "lodash";
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';
import { SearchObject } from './file-explorer/model/search-object';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { EventEmitter } from 'protractor';
import { FmsAlertService } from './file-explorer/service/alert.service';
@Component({
  selector: 'app-file-management-system',
  templateUrl: './file-management-system.component.html',
  styleUrls: ['./file-management-system.component.scss']
})
export class FileManagementSystemComponent implements OnInit {
  fileChunkSub: Subscription;

  public fileElements: Observable<FileElement[]>;
  public superFileElements: Observable<FileElement[]>;
  // fileEleUploaded: FileElement;
  fileReader: FileReader
  currentRoot: FileElement;
  // currentPath: string;
  currentPath: FileElement[] = [];
  canNavigateUp = false;
  conStr: string[];
  loginUser: UserV2

  alertOptions={
    id: 'fms-alert',
    autoClose: false,
    keepAfterRouteChange: false
  }

  msg: string[] = [];

  constructor(
    public fileService: FileExplorerService,
    private authService: AuthenticationService2,
    private alertService: FmsAlertService
    ) {
    // this.fileReader = new FileReader();
    // this.fileReader.onload = () => {
    //   const base64String = this.fileReader.result as string;

    //   this.fileService.addFC(base64String).subscribe(res => {
    //     console.log(`File chunk is saved successfully with _id: ${res._id}`)
    //     this.fileEleUploaded.info.address =res._id
    //     this.addFile(this.fileEleUploaded)
    //   }, (error) => {
    //     console.error(error)
    //     this.alert('System Errors')
    //     this.updateFileElementQuery()
    //   })
    // };
  }


  ngOnInit() {
    this.conStr = this.fileService.getConStr();
    this.fileElements = this.fileService.getQSO()
    this.superFileElements = this.fileService.getQSSO()
    this.getLoginUser_intellilab()

    this.updateFileElementQuery();
  }

  addFile(fileElements: FileElement[]) {
    this.fileService.addFE(fileElements).subscribe(res => {
      this.updateFileElementQuery();
    }, (error) => {
      console.error(error)
      this.alert('System Errors')
      this.updateFileElementQuery()
    })
  }

  addFolder(folder: { name: string }) {
    const info = {
      type: 'folder',
      size: 0,
      owner: this.loginUser.name,
      date: new Date(),
      version: 1,
      source: 'MongoDB',
      address: 'MongoDB'
    }
    const newFolder = {
      isFolder: true,
      name: folder.name,
      parent: this.currentRoot ? this.currentRoot._id : 'root',
      info
    }

    this.addFile([newFolder])
  }


  removeElement(event: {element:FileElement, postSearch?: SearchObject}) {
    this.fileService.deleteFE(event.element._id).subscribe(res => {
      if(event.postSearch){
        this.searchFile(event.postSearch)
      }else{
        this.updateFileElementQuery();
      }
    }, (error) => {
      console.error(error)
      this.alert('System Errors')
      this.updateFileElementQuery()
    })
  }

  navigateToFolder(event: {element: FileElement, initFilePath: boolean}) {
    this.currentRoot = event.element;
    this.canNavigateUp = true;

    if(event.initFilePath){
      this.fileService.initFilePath(event.element).subscribe(res => {
        if(res){
          this.currentPath = res.path
        }else{
          this.currentPath = []
          console.error(`Fail to initialize path for file element: ${event.element}`)
        }
        this.updateFileElementQuery();
      }, (error) => {
        console.error(error)
        this.alert('System Errors')
        this.updateFileElementQuery()
      })
    }else{
      this.updateFileElementQuery();
      this.pushToPath(event.element);
    }
  }

  navigateUp(element: FileElement) {
    this.currentRoot = element;
    this.updateFileElementQuery();
    this.popFromPath(element);
  }

  navQuick(element: FileElement){
    this.clearPath()
    this.navigateToFolder({element, initFilePath: false})
  }

  moveElement(event: { element: FileElement; moveTo: FileElement }) {
    // this.fileService.updateFE(event.element._id, { parent: event.moveTo._id });
    const update = lodash.cloneDeep(event.element)
    update.parent = event.moveTo._id

    this.fileService.updateFE(event.element._id, update).subscribe(res => {
      this.updateFileElementQuery();
    }, (error) => {
      console.error(error)
      this.alert('System Errors')
      this.updateFileElementQuery()
    })
  }


  renameElement(event: {element:FileElement, postSearch?: SearchObject}) {
    this.fileService.updateFE(event.element._id, event.element).subscribe(res => {
      if(event.postSearch){
        this.searchFile(event.postSearch)
      }else{
        this.updateFileElementQuery();
      }
    }, (error) => {
      console.error(error)
      this.alert('System Errors')
      if(event.postSearch){
        this.searchFile(event.postSearch)
      }else{
        this.updateFileElementQuery();
      }
    })
  }

  updateFileElementQuery() {
    // this.fileElements = this.fileService.queryInFolder(this.currentRoot ? this.currentRoot._id : 'root');
    this.fileService.queryInFolder(this.currentRoot ? this.currentRoot._id : 'root');
    this.updateSuperFileElementQuery()
  }

  updateSuperFileElementQuery(){
    this.fileService.querySuperFileElements('root')
  }

  pushToPath(folder: FileElement) {
    this.currentPath.push(folder)
  }

  popFromPath(folder: FileElement) {
    if(!folder){
      this.clearPath()
    }else{
      const index = this.currentPath.findIndex(file => file._id === folder._id)
      this.currentPath = this.currentPath.slice(0, index + 1)
    }

  }

  clearPath(){
    this.currentPath = []
  }

  uploadFile(event) {
      const fileElements = []
      this.fileService.addFC(event).subscribe(res => {
        if(res){
          res.files.forEach(file => {
            const info = {
              type: file.mimetype,
              size: file.size,
              owner: this.loginUser.name,
              date: new Date(),
              version: 1,
              source: 'MongoDB',
              address: file.id
            }

            const fileElement = {
              isFolder: false,
              name: file.originalname,
              parent: this.currentRoot ? this.currentRoot._id : 'root',
              info
            }

            fileElements.push(fileElement)
          })

          this.addFile(fileElements)
        }else{
          console.error('Fail to get response from file chunk saving')
        }
      })
  }

  downloadFile(file: FileElement){
    this.fileService.download(file)
  }

  searchFile(searchObject: SearchObject){
    this.clearPath()
    this.fileService.searchFile(searchObject)
  }

  getLoginUser_intellilab(){
    let res = this.authService.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.loginUser = user
        console.log('Login user:')
        console.log(this.loginUser)
      } )
    }
  }

  alert(msg: string){
    this.alertService.success(msg, this.alertOptions)
  }

  ngOnDestroy(){
    if(this.fileChunkSub){
      this.fileChunkSub.unsubscribe()
    }
  }
}
