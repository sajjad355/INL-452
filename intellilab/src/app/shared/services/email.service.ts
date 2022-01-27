import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { UrlService } from "app/shared/services/url.service";

@Injectable()
export class EmailService {
  url: string = UrlService.URL + `/email/`;

  constructor(private http: HttpClient) {}

  public sendEmail(emaildetail: EmailDetail): Promise<Object> {
    if (emaildetail == undefined) return;
    
    return this.http
      .put(this.url + "sendemail/", JSON.stringify(emaildetail))
      .toPromise();
  }
}


export class EmailDetail{
  constructor(
    public emailAddressList: string[],
    public emailCcList : string[],
    public subject: string,
    public emailContent: string,
    public mimeType : string ) { }
}
