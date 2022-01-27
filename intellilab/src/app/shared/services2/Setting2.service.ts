import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { UrlService } from 'app/shared/services/url.service'
import * as _ from 'lodash'
import { Observable } from 'rxjs'
import { tap } from 'rxjs/operators'
import { SettingV2, SettingValueV2 } from '../objects2/SettingV2'

export enum DateFormatType {
  UserDisplay = 1,
  DatePickerUsage
}

// do not add SettingService to any component providers list - it is meant to be a singleton initialized
// only from AppModule
@Injectable({
  providedIn: 'root',
})
export class SettingService2 {
  url: string = UrlService.URL + `/settingv2/`
  private _settings: SettingV2[]


  constructor(private http: HttpClient) {
  }



  init(forceReload: boolean = false): Promise<string> {
    if (!this._settings || forceReload) {
      return new Promise<string>((resolve, reject) => {
        this.loadSetting().subscribe(s => {
          this._settings = s
          resolve('complete')
        }, error => {
          reject(error)
        })
      })
    } else {
      return new Promise<string>((resolve, reject) => {
        resolve('allready loaded')
      })
    }
  }

  setSetting(settings) {
    this._settings = settings
  }

  // settings only to be loaded by init or upon modifying settings by add/update/delete
  public loadSetting(): Observable<SettingV2[]> {
    return this.http.get<SettingV2[]>(this.url + 'all/')
  }

  public getAllSettings(): SettingV2[] {
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
    }
    return this._settings
  }

  public getCatalogKitPointer(): SettingV2 {
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
    }
    return this._settings.find(x => x.name == 'catalogKitPointer')
  }

  public getCatalogProductPointer(): SettingV2 {
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
    }
    return this._settings.find(x => x.name == 'catalogProductPointer')
  }

  public getSettingValuesAsArray(settingName: string): string[] {
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
    }
    const setting: SettingV2 = this._settings.find(x => x.name == settingName)
    if (!setting || setting.settingValues.length == 0) {
      alert('setting for ' + settingName + ' not found - contact support')
      return
    }
    const values: string[] = []
    setting.settingValues.forEach(settingValue => {
      values.push(settingValue.value)
    })
    return values
  }

  public getSetting(settingName: string): SettingV2 {
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
    }
    const setting: SettingV2 = this._settings.find(x => x.name == settingName)
    if (!setting || setting.settingValues.length == 0) {
      alert('setting for ' + settingName + ' not found - contact support')
      return
    } else {
      return setting
    }
  }

  public get(id: number): Observable<any> {
    return this.http.get(`${this.url}${id}`)
  }

  public saveSetting(id: number, setting: SettingV2): Promise<Object> {
    if (!setting) { return }

    const httpMethod = _.isNumber(id) ? 'put' : 'post'
    const url = _.isNumber(id) ? `${this.url}${id}` : this.url

    return this.http
    [httpMethod](url, JSON.stringify(setting))
      .pipe(
        tap(() => {
          this.init(true) // force reload after save
        })
      )
      .toPromise()
  }

  public delete(id: number): Promise<any> {
    return this.http.delete(`${this.url}${id}`)
      .toPromise()
  }

  public getDateFormat(usage: DateFormatType): string {
    let dateFormat: string
    if (!this._settings) {
      alert('Settings not propertly initialized - call init first')
      return dateFormat
    }
    const setting: SettingV2 = this._settings.find(x => x.name == 'dateformat')
    if (!setting || setting.settingValues.length == 0) {
      alert('setting configuration for date format is missing - contact support')
      return
    }
    const settingValue: SettingValueV2 = setting.settingValues[0]
    dateFormat = settingValue.value
    if (usage == DateFormatType.UserDisplay) {
      dateFormat = SettingService2.reformatDateFormat(dateFormat)
    }
    return dateFormat
  }

  public checkSettingExists(name: string): Observable<number> {
    return this.http.get<number>(
      `${this.url}isExist`,
      {
        params: { name },
      }
    )
  }

  private static reformatDateFormat(format: string): string {
    const newformat = format.replace(/m/g, 'M')
    return newformat
  }
}
