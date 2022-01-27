import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';


@Injectable()
export class UrlService {

  public static URL: string;

  private static _initialize = (() => {
    let env = environment;
    UrlService.URL = env.apiURL;
  })();

  

}
