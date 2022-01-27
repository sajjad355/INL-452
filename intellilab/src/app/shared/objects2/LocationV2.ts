export class LocationV2 {
  public locationId : number;
  public locationName : string;
  public editedBy : string;
  public modifiedOn : Date;

  public subLocations: LocationV2[] = [];
   
  constructor() {}

  static copyLocationV2( existing : LocationV2   ) : LocationV2 {
    let copy : LocationV2  = new LocationV2();
    copy.locationId = existing.locationId;
    copy.editedBy = existing.editedBy;
    copy.locationName = existing.locationName;
    copy.modifiedOn = existing.modifiedOn;
    if ( existing.subLocations ) {
      existing.subLocations.forEach(existingSubLocation => {
        let copySubLocation : LocationV2 = LocationV2.copyLocationV2( existingSubLocation );
        copy.subLocations.push( copySubLocation );
      });
    }
    
    return copy;
  }
    
}