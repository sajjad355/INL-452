import { SalesItemV2 } from './SalesItemV2';

export class EquipmentV2 {

  public equipmentId: number;
  public equipmentIdentifier: string;
  public serial: string;
  public name: string;
  public manufacturer: string;
  public model: string;
  public comment: string;
  public editedBy: string;
  public modifiedOn: Date;
  public calibrationPerformed: Date;
  public calibrationScheduleInterval: string;
  public maintenanceInterval: string;
  public lastMaintenancePerformedDate: Date;
  public image: string;
  public locationBuilding: string;
  public locationUnit: string;
  public locationRoom: string;
  public nonRoutineMaintenanceComment: string;
  // public nonRoutineMaintenanceAttachment: string;
  public active: boolean = true;
  public maintanenceDue: boolean;
  public calibrationDue: boolean;
  public qualified: boolean;
  
  // constructor() {
  //   super();
  // }

  static copy( existing : EquipmentV2   ) : EquipmentV2 {
    let copy : EquipmentV2  = new EquipmentV2();
    copy.equipmentId = existing.equipmentId;
    copy.equipmentIdentifier = existing.equipmentIdentifier;
    copy.serial = existing.serial;
    copy.name = existing.name;
    copy.manufacturer = existing.manufacturer;
    copy.model = existing.model;
    copy.comment = existing.comment;
    copy.editedBy = existing.editedBy;
    copy.modifiedOn = existing.modifiedOn;
    copy.calibrationPerformed = existing.calibrationPerformed;
    copy.calibrationScheduleInterval = existing.calibrationScheduleInterval;
    copy.maintenanceInterval = existing.maintenanceInterval;
    copy.lastMaintenancePerformedDate = existing.lastMaintenancePerformedDate;
    copy.image = existing.image;
    copy.locationBuilding = existing.locationBuilding;
    copy.locationUnit = existing.locationUnit;
    copy.locationRoom = existing.locationRoom;
    copy.nonRoutineMaintenanceComment = existing.nonRoutineMaintenanceComment;
    // // copy.nonRoutineMaintenanceAttachment = existing.nonRoutineMaintenanceAttachment;
    copy.active = existing.active;
    copy.maintanenceDue = existing.maintanenceDue;
    copy.calibrationDue = existing.calibrationDue;
    copy.qualified = existing.qualified;
    return copy;
  }

}