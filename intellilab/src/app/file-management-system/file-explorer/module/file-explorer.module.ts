import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { FileExplorerComponent } from '../file-explorer.component';
import { FileManagementSystemComponent } from '../../file-management-system.component'
import { NewFolderDialogComponent } from '../modals/new-folder-dialog/new-folder-dialog.component';
import { RenameDialogComponent } from '../modals/rename-dialog/rename-dialog.component';
import { FmcDragDropDirective } from '../directives/fmc-drag-drop.directive';
import { LoaderComponent } from '../modals/loader/loader.component';
import { FmsAlertComponent } from '../modals/alert/fms-alert.component';




@NgModule({
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatGridListModule,
    MatMenuModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatTooltipModule
  ],
  declarations: [FileExplorerComponent, FileManagementSystemComponent, LoaderComponent, NewFolderDialogComponent, RenameDialogComponent, FmcDragDropDirective, FmsAlertComponent],
  exports: [FileExplorerComponent, FileManagementSystemComponent, LoaderComponent, FmcDragDropDirective, FmsAlertComponent],
  providers: [],
  entryComponents: [NewFolderDialogComponent, RenameDialogComponent]
})
export class FileExplorerModule {}
