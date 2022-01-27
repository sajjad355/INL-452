
  /* Example JSON Error message from back end might look like this
     Note that we are not concerned at the moment with the headers - but only in trying to create 
     a somewhat informative message to indicate a problem when something has gone wrong.
{  
   "headers": {
		"normalizedNames": {},
		"lazyUpdate": null
	},
	"status": 400,
	"statusText": "OK",
	"url": "http://localhost:8080/SaleLink/add/",
	"ok": false,
	"name": "HttpErrorResponse",
	"message": "Http failure response for http://localhost:8080/SaleLink/add/: 400 OK",
	"error": {
		"apierror": {
			"status": "BAD_REQUEST",
			"timestamp": "29-04-2020 11:19:28",
			"message": "Validation error",
			"debugMessage": null,
			"subErrors": [
				{
					"object": "saleLink",
					"field": "may not be empty",
					"rejectedValue": " ",
					"message": "size"
				}
			]
		}
	}
   */
export class HttpErrorResponse {

  status : string;
  statusText : string;
  url : string;
	ok: boolean;
	name: string;
	message: string;
	error: Error;

  constructor() {}


  public static fromJson(json: any ) : HttpErrorResponse {
    let err : HttpErrorResponse  = new HttpErrorResponse();
    if ( json.status ) err.statusText = json.status;
    if ( json.statusText ) err.statusText = json.statusText;
    if ( json.url ) err.url = json.url;
    if ( json.ok ) err.ok = json.ok;
    if ( json.name ) err.name = json.name;
    if ( json.message ) err.message = json.message;
    if ( json.error ) {
      err.error = new Error();
      if ( json.error.apierror ) {
        err.error.apierror = new ApiError();
        if ( json.error.apierror.status ) err.error.apierror.status = json.error.apierror.status;
        if ( json.error.apierror.timestamp ) err.error.apierror.timestamp = json.error.apierror.timestamp;
        if ( json.error.apierror.message ) err.error.apierror.message = json.error.apierror.message;
        if ( json.error.apierror.debugMessage ) err.error.apierror.debugMessage = json.error.apierror.debugMessage;
        if ( json.error.apierror.subErrors ) {
          json.error.apierror.subErrors.forEach(jsonSubError => {
             let subError : SubError = new SubError();
             if ( jsonSubError.object ) subError.object = jsonSubError.object;
             if ( jsonSubError.field ) subError.field = jsonSubError.field;
             if ( jsonSubError.rejectedValue ) subError.rejectedValue = jsonSubError.rejectedValue;
             if ( jsonSubError.message ) subError.message = jsonSubError.message;
             err.error.apierror.subErrors.push( subError );        
          });
        }
        
      }
    }
    return err;
  }
    
  getUserMessage() : string {
    let msg : string = "";
    if ( this.error && this.error.apierror ) {
      msg += this.error.apierror.message + "\n";
      this.error.apierror.subErrors.forEach(subError => {
        msg += subError.getDetails() + "\n";
      });
    }
    else {
      msg += "Message not available"
    } 
    return msg;
  }
}

class Error {
  apierror : ApiError;
  constructor() {}
}

class ApiError {
  status: string;
  timestamp: string;
  message: string;
  debugMessage: string;
  subErrors : SubError[] = [];

  constructor() { }
}

class SubError {
  object: string;
  field: string;
  rejectedValue: string;
  message: string;

  constructor() { }

  getDetails() : string {
    return "Object: " + this.object + 
           ", field: " + this.field + 
           ", rejectedValue: " + this.rejectedValue +
           ", message: " + this.message;
  }
  
}