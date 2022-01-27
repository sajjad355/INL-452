import { HttpErrorResponse } from "./HttpErrorResponse";

export class ErrorUtil {

  static handleHttpError(json: any ) {
    let errorResp : HttpErrorResponse = HttpErrorResponse.fromJson( json );  
    console.log( 'Error occured:', errorResp );
    console.dir( errorResp );
    // alert( 'An error occured - please contact support. Details: ' + errorResp.getUserMessage() );
  }  

  static handleError( message: string ) {
    alert( 'An error occured - please contact support. Details: ' + message );
  }
  
  static showError(json: any, router) {
    router.navigate(['error'], {state: {data: json.error}});
  }
  
}