import { Component, Input } from '@angular/core';
import {Error} from './error';
// import { Location } from '@angular/common';
import { ErrorService2  } from '../../shared/services2/Error2.service';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { ErrorService } from './error.service';
import { of } from 'rxjs';

@Component({
  selector: 'error-item',
  templateUrl: 'error-item.component.html',
  styleUrls: ['error-item.component.scss'],
})
export class ErrorItemComponent {
  @Input()
  error: Error;

  copyToClipboardButton: string = "Click here";
  errors = {
    tag: '',
    timestamp: '',
    status: '',
    error: '',
    message: '',
    path: '',
  }

  constructor(
    // private location: Location,
    private errorService2: ErrorService2,
    public errorService: ErrorService
  ) { }

  renderMessage(message, length) {
    if(message && message !== undefined && message.length <= length) return message;
    else return message.slice(0, length) + '...';
  }

  copyToClipboard() {
    this.copyToClipboardButton = "Copied!";
    var range = document.createRange();
    range.selectNode(document.getElementById("error-console"));
    window.getSelection().removeAllRanges();
    window.getSelection().addRange(range);
    document.execCommand("copy");
    window.getSelection().removeAllRanges()
    setTimeout(() => {
      this.copyToClipboardButton = "Click here";
    }, 500);
  }

  goBack() {
    // this.location.back();
    const message = null;
    this.errorService.message$ = message;
  }

  email() {
    let error = this.error;
    // error['timestamp'] = 'aaa';
    // error['userInput'] = 'aaa';
    this.errorService2.email(error).then( ()  => {
      alert('Issue has been emailed to the developer team!')
    }).catch( error => {
      ErrorUtil.handleHttpError(error);
    });
  }

}
