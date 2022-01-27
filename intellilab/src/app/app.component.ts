import { Component, Directive  } from '@angular/core';
import { ResponsiveService } from 'app/shared/services/responsive.service';
import { RequestInterceptorService } from 'app/shared/services2/request-interceptor.service';



@Directive({selector: '[windowevent]'})
export class windowevent {
  // not much going on here as well...
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [ResponsiveService, RequestInterceptorService],
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private responsiveService:ResponsiveService ){
    // The method below does not seem to do anything
  
    window.onbeforeunload = function(e) {
      if(localStorage.getItem('isR') == 'n'){
        sessionStorage.clear();
        localStorage.clear();
      }
    };

  }

  ngOnInit(){
    this.onResize();   
  }

  onResize(){
    this.responsiveService.checkWidth();
  }

  








}
