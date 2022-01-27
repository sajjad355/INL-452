import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FileElement } from 'app/file-management-system/file-explorer/model/file-element';
import { SearchObject } from 'app/file-management-system/file-explorer/model/search-object';
import { FileExplorerService } from 'app/file-management-system/file-explorer/service/file-explorer.service';
import { environment } from 'environments/environment';
import * as lodash from "lodash";
import { Subject } from 'rxjs/internal/Subject';
import { v4 as uuidv4 } from 'uuid';
import { UserV2 } from '../objects2/UserV2';
import { AuthenticationService2 } from '../services2/Authenticate2.service';
// const BACKEND_E_URL = 'http://localhost:5000/api';
const BACKEND_E_URL = environment.nodebd;

@Injectable({
  providedIn: 'root'
})
export class AttachmentService {

  private querySubject: Subject<FileElement[]> = new Subject()
  private refreshObservable: Subject<{refresh: boolean}> = new Subject()
  private superFolders: FileElement[]
  private loginUser: UserV2
  private fileEleUploaded: FileElement
  private fileReader: FileReader
  private superFolder_id: string
  private folder_id: string
  private observableCounter: number = 0

  constructor(
    private http: HttpClient,
    private fileService: FileExplorerService,
    private authService: AuthenticationService2) {
      this.getLoginUser_intellilab()
      // this.queryInFolder('root').subscribe(res => this.superFolders = res)
  }

  private getLoginUser_intellilab(){
    let res = this.authService.getCurrentUser();
    if(res !== undefined){
      res.then( (user) => {
        this.loginUser = user
        console.log('Login user:')
        console.log(this.loginUser)
      } )
    }
  }

  registerObserver(){
    const observerId = uuidv4()
    console.log(`New observer ID has been issued: ${observerId}`)
    this.observableCounter++
    console.log(`Observable counter: ${this.observableCounter}`)
    return observerId
  }

  init(superFolderName: string, folderName: string){
    this.clearCacheId()
    console.log(`Attachment service init parameters, superFolderName: ${superFolderName}, folderName: ${folderName}`)
    let searchObject: SearchObject = {
      key: 'name',
      value: folderName,
      algorithm: 'binary'
    }

    // Fetch storage folder meta data
    this.searchFile(searchObject).subscribe(res => {
      const results = res.results

      if(results.length > 0){
        results.forEach(result => {
          if(result['name'] === folderName){
            this.folder_id = result._id
            this.superFolder_id = result.parent
            console.log(`Successfully initialize storage folder ID and super folder ID`)
            this.updateAttachments(this.folder_id)
          }
        })
      }

      // If fail to fetch storage folder then check the existence of the storage folder's parent
      if(!this.folder_id){
        searchObject.value = superFolderName
        this.searchFile(searchObject).subscribe(res => {
          const results = res.results

              if(results.length > 0){
                results.forEach(result => {
                  if(result['name'] === superFolderName){
                    this.superFolder_id = result._id
                  }
                })
              }

              // If passed-in super folder not existed, then create the super folder and storage folder
              if(!this.superFolder_id){
                this.createFolder(superFolderName, 'root').subscribe(res => {
                  this.superFolder_id = res[0]._id
                  console.log(`New super folder is created: ${superFolderName}`)
                  this.createFolder(folderName, this.superFolder_id).subscribe(res => {
                    this.folder_id = res[0]._id
                    console.log(`New storage folder is created: ${folderName}`)
                    this.updateAttachments(this.folder_id)
                  })
                })
              }else{
                this.createFolder(folderName, this.superFolder_id).subscribe(res => {
                  this.folder_id = res[0]._id
                  console.log(`New storage folder is created: ${folderName}`)
                  this.updateAttachments(this.folder_id)
                })
              }
          })
      }

    })
  }

  clearCacheId(){
    this.folder_id = null
    this.superFolder_id = null
  }

  getARO(){
    return this.refreshObservable
  }

  getQSQ(){
    return this.querySubject;
  }

  getRootFolders(){
    return lodash.cloneDeep(this.superFolders)
  }

  delete(file_id: string){
    this.fileService.deleteFE(file_id).subscribe(res => {
      this.updateAttachments(this.folder_id)
      this.refreshObservable.next({refresh: true})
    })
  }

  update(id: string, update: FileElement) {
    const headers = this.createHeader()
    this.http.put(BACKEND_E_URL + `/fms/file/element/db/${id}`, JSON.stringify(update), { headers }).subscribe(res => {
      this.updateAttachments(this.folder_id)
    })
  }

  download(fileElement: FileElement){
      this.fileService.download(fileElement)
  }

  private createHeader(){
    let header = new HttpHeaders()
    .set('Content-Type', 'application/json')
    // .set("Authorization", 'Bearer '+sessionStorage.getItem('jwt'))
    return header
  }


  private queryInFolder(folderId: string) {
    const headers = this.createHeader()
    return this.http.get<FileElement[]>(BACKEND_E_URL + `/fms/file/element/db/queryInFolder/${folderId}`, { headers })
  }

  private updateAttachments(folderId: string){
    this.queryInFolder(folderId).subscribe(res => {
        this.querySubject.next(res);
    })
  }

  private createFolder(name: string, root_id: string){
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
      name: name,
      parent: root_id,
      info
    }
   return this.fileService.addFE([newFolder])
  }

  private searchFile(searchObject: SearchObject){
    const headers = this.createHeader()
    return this.http.post<{results: FileElement[]}>(BACKEND_E_URL + `/fms/file/element/db/search`, JSON.stringify(searchObject), { headers })
  }

  getAttSummary(componentName: string){
    const searchObject = {
      key: 'name',
      value: componentName,
      algorithm: 'binary'
    }
    const headers = this.createHeader()
    return this.http.post<{summary: any[]}>(BACKEND_E_URL + `/fms/file/element/db/special/attachment/summary`, JSON.stringify(searchObject), { headers })
  }


attachFile(event) {
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
              parent: this.folder_id,
              info
            }

            fileElements.push(fileElement)
          })

          console.log(`folder_id in function attachFile: ${this.folder_id}`)

          this.fileService.addFE(fileElements).subscribe(res => {
            this.updateAttachments(this.folder_id)
            this.refreshObservable.next({refresh: true})
          })
        }else{
          console.error('Fail to get response from file chunk saving')
        }
      })
}
}
