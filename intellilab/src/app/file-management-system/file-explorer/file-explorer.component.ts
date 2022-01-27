import { Component, Input, Output, EventEmitter, ViewChildren, QueryList, ViewChild } from '@angular/core';
import { FileElement } from './model/file-element';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { Observable } from 'rxjs/Observable';
import { MatDialog } from '@angular/material/dialog';
import { NewFolderDialogComponent } from './modals/new-folder-dialog/new-folder-dialog.component';
import { RenameDialogComponent } from './modals/rename-dialog/rename-dialog.component';
import { FileExplorerService } from './service/file-explorer.service';
import { UserService2 } from 'app/shared/services2/User2.service';
import { UserV2 } from 'app/shared/objects2/UserV2';
import { SearchObject } from './model/search-object';
import * as lodash from "lodash";



@Component({
  selector: 'file-explorer',
  templateUrl: './file-explorer.component.html',
  styleUrls: ['./file-explorer.component.scss']
})
export class FileExplorerComponent {
  @Input()  fileElements: FileElement[];
  @Input()  superFileElements: FileElement[]
  @Input()  canNavigateUp: string;
  @Input()  path: FileElement[]
  @Output() fileDownload = new EventEmitter<FileElement>();
  @Output() fileUploaded = new EventEmitter();
  @Output() folderAdded = new EventEmitter<{ name: string }>();
  @Output() elementRemoved = new EventEmitter<{element:FileElement, postSearch?: SearchObject}>();
  @Output() elementRenamed = new EventEmitter<{element:FileElement, postSearch?: SearchObject}>();
  @Output() elementMoved = new EventEmitter<{ element: FileElement; moveTo: FileElement }>();
  @Output() navigatedDown = new EventEmitter<{element: FileElement, initFilePath: boolean}>();
  @Output() navigatedUp = new EventEmitter<FileElement>();
  @Output() navigateQuick = new EventEmitter<FileElement>();
  @Output() fileSearch = new EventEmitter<SearchObject>()


  selFileIndex: number = -1
  isLoading: boolean = false
  conStr: string[];
  sortStr: string[];
  fileType: {[key: string]: any}[]
  searchType: {[key: string]: any}[]
  sortStrIndex: number;
  dirDown: string = 'downward'
  dirUp: string = 'upward'
  clickCount: number = 0;
  searchbar_filter_display: boolean = false
  setting_panel_display: boolean = false
  searched: boolean = false
  listPanelDisplay: boolean[]=[false, false]
  sortPanelDisplay: boolean = false;
  infoElement: FileElement;
  QSO: Observable<FileElement[]>;
  users: UserV2[]
  searchObject: SearchObject = {
    key: null,
    value: null,
    algorithm: 'binary' // Use binary searching algorithm by default
  };

  constructor(
    public dialog: MatDialog,
    public fileService: FileExplorerService,
    private userService: UserService2
    ) {}

  ngOnInit(){
    this.conStr = this.fileService.getConStr();
    this.sortStr = this.fileService.getSortStr()
    this.fileType = this.fileService.getFileType()
    this.searchType = this.fileService.getFileType()
    this.searchType.pop()// users are not allowed to search for file type: "others"
    this.getAllUsers_intellilab()
    this.QSO = this.fileService.getQSO()
    this.QSO.subscribe(res => {
      this.setSelFileIndex(-1)
      this.setLoading(false)
      this.setSortStrIndex(-1) // reset sort selection to none
    })
  }

  setSelFileIndex(value: number){
    this.selFileIndex = value
  }

  setLoading(value: boolean){
    this.isLoading = value
  }

  setSortStrIndex(value: number){
    this.sortStrIndex = value
  }

  setSearchStatus(value: boolean){
    this.searched = value
  }

  triggerSettingPanel(settingPanel){
    this.setting_panel_display = !this.setting_panel_display
    if(this.setting_panel_display){
      settingPanel.style.transition = 'all ease-out 0.3s'
      settingPanel.style.width = 30 + '%'
      settingPanel.style.height = 50 + '%'
    }else{
      settingPanel.style.transition = 'all ease-in 0.3s'
      settingPanel.style.width = 0 + '%'
      settingPanel.style.height = 0 + '%'
    }
  }

  triggerFilterPanel(filterPanel){
    this.searchbar_filter_display = !this.searchbar_filter_display
    if(this.searchbar_filter_display){
      filterPanel.style.transition = 'all ease-out 0.3s'
      filterPanel.style.height = 50 + '%'
    }else{
      filterPanel.style.transition = 'all ease-in 0.3s'
      filterPanel.style.height = 0 + '%'
    }

  }

  triggerListPanel(listPanel, panelIndex, listItemNum){
    const panelDisplay: boolean = !this.listPanelDisplay[panelIndex]
    this.listPanelDisplay[panelIndex] = panelDisplay
    if(panelDisplay){
      listPanel.style.transition = 'all ease-out 0.3s'
      listPanel.style.height = 50 * (listItemNum + 1) + 'px'
    }else{
      listPanel.style.transition = 'all ease-in 0.3s'
      listPanel.style.height = '50px'
    }
  }

  triggerSortPanel(sortPanel){
    const panelDisplay = !this.sortPanelDisplay
    this.sortPanelDisplay = panelDisplay
    if(panelDisplay){
      sortPanel.style.transition = 'all ease-out 0.3s'
      sortPanel.style.border = '2px #e5e5e5 solid'
      sortPanel.style['border-top'] = 'none'
      sortPanel.style.height = this.sortStr.length * 84 + 'px'
      sortPanel.style.padding = '1rem'
    }else{
      sortPanel.style.transition = 'all ease-in 0.3s'
      sortPanel.style.border = '0px #e5e5e5 solid'
      sortPanel.style.height = '0px'
      sortPanel.style.padding = '0px'
    }
  }

  sortFile(index){
    this.sortStrIndex = index
    switch(this.sortStr[this.sortStrIndex]){
      case 'name':
        this.fileElements.sort( function(a, b){
          return (a.name < b.name ? -1 : 1)
        } )
        break;

      default:
        const property = this.sortStr[this.sortStrIndex].toLowerCase()
        this.fileElements.sort( function(a, b){
          return (a.info[property] < b.info[property] ? -1 : 1)
        } )
    }
  }

  openInfoPanel(infoPanel, explorerContainer, element){
    this.infoElement = element
    infoPanel.style.transition = 'all ease-out 0.3s'
    infoPanel.style.width = '25%'

    explorerContainer.style.transition = 'all ease-out 0.3s'
    explorerContainer.style.width = '50%'
  }

  closeInfoPanel(infoPanel, explorerContainer){
    infoPanel.style.transition = 'all ease-in 0.3s'
    infoPanel.style.width = '0%'

    explorerContainer.style.transition = 'all ease-in 0.3s'
    explorerContainer.style.width = '75%'
  }

  onSelect(element: FileElement, fileIndex) {
      this.clickCount++;
      setTimeout(() => {
          if (this.clickCount === 1) {
             this.setSelFileIndex(fileIndex)
          } else if (this.clickCount === 2) {
            if (element.isFolder) {
              this.navigate(element)
            }
          }
          this.clickCount = 0;
      }, 250)
  }

  deleteElement(element: FileElement) {
    if(this.searched){
      this.elementRemoved.emit({element, postSearch: this.searchObject});
    }else{
      this.elementRemoved.emit({element});
    }
    this.setLoading(true)
  }

  navigate(element: FileElement) {
    if (element.isFolder) {
      if(this.searched){
        this.navigatedDown.emit({element, initFilePath: true});
      }else{
        this.navigatedDown.emit({element, initFilePath: false});
      }

      this.setLoading(true)
      this.setSearchStatus(false)
    }
  }

  navigateUp(element: FileElement) {
    this.navigatedUp.emit(element);
    this.setLoading(true)
    this.setSearchStatus(false)
  }

  navQuick(element: FileElement){
    this.navigateQuick.emit(element)
    this.setLoading(true)
    this.setSearchStatus(false)
  }

  moveElement(element: FileElement, moveTo?: FileElement, directive: string = this.dirDown) {
    if(directive === this.dirDown){
      this.elementMoved.emit({ element: element, moveTo: moveTo });
      this.setLoading(true)
    }else{
      if(element.parent !== 'root'){
        this.fileService.getFE(element.parent).subscribe(res => {
          const parentElement = res
          if(parentElement){
            // POP stands for the parent of parent
            const POP: FileElement = {
              _id: parentElement.parent,
              isFolder : true,
              name: 'not required at this point',
              parent: 'not required at this point'
            }
            this.elementMoved.emit({ element: element, moveTo: POP });
            this.setLoading(true)

          }else{
            console.log(`Fail to fetch file element with _id (${element.parent})`)
          }
        })
      }else{
        console.log('Invalid path to move file')
      }

    }
  }

  openNewFolderDialog() {
    let dialogRef = this.dialog.open(NewFolderDialogComponent, { panelClass: 'dialog-customized' });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        this.folderAdded.emit({ name: res });
        this.setLoading(true)
      }
    });
  }

  openRenameDialog(element: FileElement) {
    let dialogRef = this.dialog.open(RenameDialogComponent, { panelClass: 'dialog-customized' });
    dialogRef.afterClosed().subscribe(res => {
      if (res) {
        element.name = res;
        if(this.searched){
          this.elementRenamed.emit({element, postSearch: this.searchObject})
        }else{
          this.elementRenamed.emit({element})
        }
        this.setLoading(true)
      }
    });
  }

  openMenu(event: MouseEvent, viewChild: MatMenuTrigger) {
    event.preventDefault();
    viewChild.openMenu()
  }

  uploadFile(event){
    this.fileUploaded.emit(event)
    this.setLoading(true)
  }

  downloadFile(file: FileElement){
    this.fileDownload.emit(file)
  }

  formatBytes(bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  formatDate(date){
    const newDate = new Date(date);

    // The month count starts with 0 (Jan), up to 11 (Dec).
    const month = newDate.getMonth() + 1;
    // return newDate.getFullYear() + "/" + month + "/" + newDate.getDate();
    return '' + newDate
  }

  getAllUsers_intellilab(){
    this.userService.loadUsers().subscribe(res=>{
      this.users = res;
      if(this.users.length > 0){
        this.users.sort(
          function (a, b){
            if(a.name.replace(/\s+/g, '').toLowerCase() < b.name.replace(/\s+/g, '').toLowerCase()) { return -1; }
            if(a.name.replace(/\s+/g, '').toLowerCase() > b.name.replace(/\s+/g, '').toLowerCase()) { return 1; }
            return 0;
          })
        }
        console.log('All users:')
        console.log(this.users)
      })
  }

  searchFile(key: string, value: string){
    console.log(`Searching key: ${key}, with value: ${value}`)
    if(key && value){
      this.setLoading(true)
      this.setSearchStatus(true)
      this.searchObject.key = key
      this.searchObject.value = value
      this.fileSearch.emit(this.searchObject)
    }else{
      console.error(`File searching operation is aborted, missing ${key?'':'searching type'}${value?'':' searching value'}`)
    }
  }

  setAlgorithm(algorithm: string){
    this.searchObject.algorithm = algorithm
  }

}
