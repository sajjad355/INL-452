import { NgModule } from '@angular/core';
import { ErrorComponent } from './error.component';
import { CommonModule } from '@angular/common';
import { ErrorItemComponent } from './error-item.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [CommonModule, BrowserAnimationsModule],
  declarations: [ErrorComponent, ErrorItemComponent],
  exports: [ErrorComponent, ErrorItemComponent],
})

export class ErrorModule {

}
