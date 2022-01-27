export class SettingV2 {

  public settingId: number;
  public name: string;
  public description: string;
  public editedBy: string;
  public modifiedOn: Date;

  public settingValues: SettingValueV2[] = [];

  static copySettingV2( existing: SettingV2   ): SettingV2 {
    const copy: SettingV2  = new SettingV2();
    copy.settingId = existing.settingId;
    copy.editedBy = existing.editedBy;
    copy.name = existing.name;
    copy.modifiedOn = existing.modifiedOn;
    if ( existing.settingValues ) {
      existing.settingValues.forEach(existingSettingValue => {
        const copySettingValue: SettingValueV2 = SettingValueV2.copySettingValueV2( existingSettingValue );
        copy.settingValues.push( copySettingValue );
      });
    }
    return copy;
  }

  constructor() {}

}


export class SettingValueV2 {

  public settingValueId: number;
  public value: string;
  public editedBy: string;
  public modifiedOn: Date;
  public attribute: string;

  static copySettingValueV2( existing: SettingValueV2   ): SettingValueV2 {
    const copy: SettingValueV2  = new SettingValueV2();
    copy.settingValueId = existing.settingValueId;
    copy.editedBy = existing.editedBy;
    copy.value = existing.value;
    copy.modifiedOn = existing.modifiedOn;
    copy.attribute = existing.attribute;
    return copy;
  }

  constructor() { }

}

