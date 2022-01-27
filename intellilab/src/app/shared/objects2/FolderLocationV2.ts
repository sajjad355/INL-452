import { UserV2 } from './UserV2';

export class FolderLocationV2 {

  public dbid: number;
  public title: string;
  public employee: UserV2;
  public projectManager: UserV2;
  public barcode: string;
  public active: boolean = true;

  static copy(existing: FolderLocationV2): FolderLocationV2 {
    let copy : FolderLocationV2  = new FolderLocationV2();
    copy.dbid = existing.dbid;
    copy.title = existing.title;
    copy.employee = existing.employee;
    copy.projectManager = existing.projectManager;
    copy.barcode = existing.barcode;
    copy.active = existing.active;
    return copy;
  }

}