import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Router } from '@angular/router'
import { ErrorService } from 'app/page/error/error.service'
import { Observable, throwError } from 'rxjs'
import { catchError } from 'rxjs/operators'
import { v4 as uuidV4 } from 'uuid'

@Injectable()

export class RequestInterceptorService implements HttpInterceptor  {
  constructor(
    private router: Router,
    private errorService: ErrorService,
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(this.setHeader(req))
      .pipe(catchError((err: HttpErrorResponse) => {
        this.handleError(err)
        return throwError(err)
      }))
  }

  handleError(err: HttpErrorResponse): void {
    if (err.status === 401) {
      sessionStorage.removeItem('user.userId')
      sessionStorage.removeItem('jwt')

      this.router.navigate(['/login'])
    } else {
      this.errorService.message$ = err.error
    }
  }

  setHeader(req: HttpRequest<any>): HttpRequest<any> {
    const token = sessionStorage.getItem('jwt')
    const contentType = req.headers.get('content-type')

    // can't find a way to bypass or override the header,
    // hardcoded to skip content type for "file/chunk" for now
    const skipContentType = req.url.endsWith('/fms/file/chunk/db')

    return req.clone({
      setHeaders: {
        ...token && { Authorization: `Bearer ${token}` },
        ...contentType === null && !skipContentType && { 'content-type': 'application/json' },
        'DEBUG_BREADCRUMB': uuidV4()
      }
    })
  }
}

