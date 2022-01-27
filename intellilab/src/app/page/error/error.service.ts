import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Error } from './error';

@Injectable({ providedIn: 'root' })

export class ErrorService {
  message$: Observable<Error[]>;
}
