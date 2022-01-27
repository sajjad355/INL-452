import { UserV2 } from './UserV2';
import { FolderV2 } from './FolderV2';

export class RequestV2 {
  public note: string;
  public folder: FolderV2;
  // public requestedBy: UserV2;
  // public tobeCustodian: UserV2
  // public requestStatus: string
  public sender: UserV2;
  public receiver: UserV2
  public requestStatus: string

  static copy( existing : RequestV2   ) : RequestV2 {
    let copy : RequestV2  = new RequestV2();
    copy.note = existing.note;
    copy.folder = existing.folder;
    copy.sender = existing.sender;
    copy.receiver = existing.receiver
    copy.requestStatus = existing.requestStatus
    return copy;
  }
}
