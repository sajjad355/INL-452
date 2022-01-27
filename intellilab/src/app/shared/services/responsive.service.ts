import { Injectable } from '@angular/core';
import { Subject ,  Observable } from "rxjs";

@Injectable()
export class ResponsiveService {
  private isMobile = new Subject();
  private isTablet = new Subject();
  public screenWidth: string;
  constructor() {this.checkWidth(); }

  
  onTabletChange(status: boolean) {
    this.isTablet.next(status);
  }

  getTabletStatus(): Observable<any> {
    return this.isTablet.asObservable();
  }

  public checkWidth() {
    var width = window.innerWidth;
    var height =  window.innerHeight;
    if (width <= 768) {
        this.screenWidth = 'sm';
        this.onTabletChange(false);
    } else if (width > 768 && width <= 1280) {
        this.screenWidth = 'md';
        this.onTabletChange(true);
    } else {
        this.screenWidth = 'lg';
        this.onTabletChange(false);
    }
  }

}
