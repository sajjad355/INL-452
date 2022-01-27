import { UserV2 } from './UserV2';
import { FolderLocationV2 } from './FolderLocationV2';

export class FolderV2 {

  public dbid: number;
	public numberOfFolder: number;
	public title: string;
	public studyNumber: string;
	public custodian: UserV2;
	public projectManager: UserV2;
	public location: FolderLocationV2;
	public createdBy: UserV2;
	public remarks: string;
	public barcode: string;
	public active: boolean = true;

  static copy(existing: FolderV2): FolderV2 {
    let copy : FolderV2  = new FolderV2();
    copy.dbid = existing.dbid;
    copy.numberOfFolder = existing.numberOfFolder;
    copy.title = existing.title;
    copy.studyNumber = existing.studyNumber;
    copy.custodian = existing.custodian;
    copy.projectManager = existing.projectManager;
    copy.location = existing.location;
    copy.createdBy = existing.createdBy
    copy.remarks = existing.remarks
    copy.barcode = existing.barcode
    copy.active = existing.active;
    return copy;
  }

}