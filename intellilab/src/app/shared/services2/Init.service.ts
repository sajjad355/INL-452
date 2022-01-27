
import { Injectable } from '@angular/core'
import { ErrorUtil } from '../util/ErrorUtil'
import { EquipmentService2 } from './Equipment2.service'
import { ItemSourceService2 } from './ItemSource2.service'
import { LocationService2 } from './Location2.service'
import { SettingService2 } from './Setting2.service'


@Injectable({
  providedIn: 'root',
})
export class InitService {


  constructor(public settingService: SettingService2,
    public locationService: LocationService2,
    public equipmentService: EquipmentService2,
    public itemSourceService: ItemSourceService2) {
  }

  // Initialize all the presettings for IntelliLab launch
  initAll() {
    this.settingService.init().catch(error => {
      console.log('Application failed to initialize settings properly')
      ErrorUtil.handleHttpError(error)
    })

    this.locationService.init().catch(error => {
      console.log('Application failed to initialize locations properly')
      ErrorUtil.handleHttpError(error)
    })

    this.equipmentService.init().catch(error => {
      console.log('Application failed to initialize equipment properly')
      ErrorUtil.handleHttpError(error)
    })

    this.itemSourceService.init().catch(error => {
      console.log('Application failed to initialize item sources properly')
      ErrorUtil.handleHttpError(error)
    })
  }

  initSetting() {
    return this.settingService.loadSetting()
  }

  saveSetting(settings) {
    this.settingService.setSetting(settings)
  }


  initEquipment() {
    return this.equipmentService.loadEquipment()
  }

  saveEquipment(equipments) {
    this.equipmentService.setEquipment(equipments)
    console.log('Init data of equipments preview')
    console.log(equipments)
  }


  initLocation() {
    return this.locationService.loadLocation()
  }

  saveLocation(locations) {
    this.locationService.setLocation(locations)
    console.log('Init data of locations preview:')
    console.log(locations)
  }

  initItemSources() {
    // return this.itemSourceService.loadSources()
  }

  saveItemSource(source) {
    // this.itemSourceService.setLocation(source)
    // console.log('Init data of item source preview:')
    // console.log(source)
  }


}
