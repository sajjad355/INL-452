import { Injectable } from '@angular/core';
import * as lodash from "lodash";
import { v4 } from 'uuid';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { FileElement } from '../model/file-element';
import { FileChunk } from '../model/file-chunk';
import { SearchObject } from '../model/search-object';
import { environment } from 'environments/environment';
declare var fmsDownload: any;
// const BACKEND_E_URL = 'http://localhost:5000/api';
const BACKEND_E_URL = environment.nodebd;

export interface IFileService {
  addFE(fileElements: FileElement[]);
  addFC(files: File[])
  getFC(chunk_id: string)
  getFE(file_id: string);
  deleteFE(file_id: string);
  updateFE(id: string, update: FileElement);
  queryInFolder(folderId: string);
  querySuperFileElements(folderId: string);
}

@Injectable({providedIn:"root"})
export class FileExplorerService implements IFileService {
  private map = new Map<string, FileElement>();
  private fb_map = new Map<string, FileChunk>()
  private conStr: string[] = ['Inventory', 'Sales', 'Orders']
  private sortStr: string[] = ['name', 'type', 'size', 'date']
  private fileType: any[]=[
    { type: 'doc', identifier: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' },
    { type: 'xls', identifier: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' },
    { type: 'ppt', identifier: 'application/vnd.openxmlformats-officedocument.presentationml.presentation' },
    { type: 'txt', identifier: 'text/plain' },
    { type: 'pdf', identifier: 'application/pdf' },
    { type: 'zip', identifier: 'application/x-zip-compressed' },
    { type: 'img', identifier: 'image' },
    { type: 'folder', identifier: 'folder' },
    { type: 'others', identifier: 'unknown' }
  ]

  constructor(private http: HttpClient) {}
  private querySubject: BehaviorSubject<FileElement[]> = new BehaviorSubject([])
  private querySuperSubject: BehaviorSubject<FileElement[]> = new BehaviorSubject([])

  getConStr(){
    return lodash.cloneDeep(this.conStr)
  }

  getSortStr(){
    return lodash.cloneDeep(this.sortStr)
  }

  getFileType(){
    return lodash.cloneDeep(this.fileType)
  }

  createHeader(){
    let header = new HttpHeaders()
    .set('Content-Type', 'application/json')
    // .set("Authorization", 'Bearer '+sessionStorage.getItem('jwt'))
    return header
  }

  addFC(files: File[]){
    let formData = new FormData()
    let i
    for( i = 0; i < files.length; i++ ){
      formData.append('file', files[i])
    }

    // formData.forEach(value => {
    //   console.log(value)
    // })

    return this.http.post<any>(BACKEND_E_URL + '/fms/file/chunk/db', formData);
  }

  getFC(shadow_file_id: string){
    window.open(BACKEND_E_URL + `/fms/file/chunk/db/${shadow_file_id}`)
    // this.http.get<FileElement>(BACKEND_E_URL + `/fms/file/chunk/db/${shadow_file_id}`, { responseType: 'arraybuffer' as 'json' }).subscribe(res => {
    //   console.log('getFC() response: ')
    //   console.log(res)
    // })
  }

  addFE(fileElements: FileElement[]) {
    const headers = this.createHeader()
    return this.http.post<FileElement>(BACKEND_E_URL + '/fms/file/element/db', JSON.stringify(fileElements), { headers });
  }

  getFE(file_id: string) {
    const headers = this.createHeader()
    return this.http.get<FileElement>(BACKEND_E_URL + `/fms/file/element/db/${file_id}`, { headers });
  }

  deleteFE(file_id: string) {
    const headers = this.createHeader()
    return this.http.delete(BACKEND_E_URL + `/fms/file/element/db/${file_id}`, { headers });
  }

  updateFE(id: string, update: FileElement) {
    const headers = this.createHeader()
    return this.http.put(BACKEND_E_URL + `/fms/file/element/db/${id}`, JSON.stringify(update), { headers });
  }


  // QSO stands for Query Subject Observable
  getQSO(){
    return this.querySubject
  }

  // QSSO stands for Query Super Subject Observable
  getQSSO(){
    return this.querySuperSubject
  }

  queryInFolder(folderId: string) {
    const headers = this.createHeader()
    this.http.get<FileElement[]>(BACKEND_E_URL + `/fms/file/element/db/queryInFolder/${folderId}`, { headers }).subscribe(res => {
        console.log('Query in folder is called with response: ')
        console.log(res)
        this.querySubject.next(res);
    })
  }

  querySuperFileElements(folderId: string) {
    const headers = this.createHeader()
    this.http.get<FileElement[]>(BACKEND_E_URL + `/fms/file/element/db/queryInFolder/root`, { headers }).subscribe(res => {
        console.log('Query super elements in folder is called with response: ')
        console.log(res)
        const filter = res.filter( element => element.isFolder )
        this.querySuperSubject.next(filter);
    })
  }

  searchFile(searchObject: SearchObject){
    const headers = this.createHeader()
    this.http.post<{results: FileElement[]}>(BACKEND_E_URL + `/fms/file/element/db/search`, JSON.stringify(searchObject), { headers }).subscribe(res => {
      console.log('File searching results: ')
      console.log(res.results)
      this.querySubject.next(res.results);
    })
  }

  initFilePath(fileElement: FileElement){
    const headers = this.createHeader()
    return this.http.get<{ path: FileElement[] }>(BACKEND_E_URL + `/fms/file/element/db/path/${fileElement._id}`, { headers })
  }

  download(fileElement: FileElement){
    if(!fileElement.isFolder){
      const shadow_file_id = fileElement.info.address
      this.getFC(shadow_file_id)
    }
  }

  dataURItoBlob(dataURI) {
    // convert base64/URLEncoded data component to raw binary data held in a string
    var byteString;
    if (dataURI.split(',')[0].indexOf('base64') >= 0){
      byteString = atob(dataURI.split(',')[1]);
    }else{
      byteString = decodeURI(dataURI.split(',')[1]);
    }

    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to a typed array
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return new Blob([ia], {type:mimeString});
  }

  clone(element: FileElement) {
    return JSON.parse(JSON.stringify(element));
  }

  clone_fb(element: FileChunk) {
    return JSON.parse(JSON.stringify(element));
  }
}
