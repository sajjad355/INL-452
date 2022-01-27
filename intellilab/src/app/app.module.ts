import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularMyDatePickerModule } from 'angular-mydatepicker';
import { ClickOutsideModule } from 'ng-click-outside';
import { ChartsModule } from 'ng2-charts';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { NgxBarcodeModule } from 'ngx-barcode';
import { NgxPrintModule } from 'ngx-print';
import { AppComponent, windowevent } from './app.component';
import { AngularMaterialModule } from './components/angular-material/angular-material.module';
import { CheckInComponent } from './components/check-in/check-in.component';
import { CheckOutComponent } from './components/check-out/check-out.component';
import { ClientSearchComponent } from './components/client-search/client-search.component';
import { DetailsViewHeaderComponent } from './components/details-view-header/details-view-header.component';
import { ItemSearchComponent } from './components/item-search/item-search.component';
import { ItemSourceSearchComponent } from './components/item-source-search/item-source-search.component';
import { QuoteSearchComponent } from './components/quote-search/quote-search.component';
import { ReconstitutionComponent } from './components/reconstitution/reconstitution.component';
import { ReviewComponent } from './components/review/review.component';
import { SalesItemSearchComponent } from './components/sales-item-search/sales-item-search.component';
// import {G_DialogComponent} from './page/dialog/dialog.component'
import { DeleteWarningDialogComponent } from './components/settings/delete-warning-dialog/delete-warning-dialog.component';
import { SettingDetailsComponent } from './components/settings/setting-details/setting-details.component';
import { SettingsComponent } from './components/settings/settings.component';
import { SimpleClientSearchComponent } from './components/simple-client-search/simple-client-search.component';
import { SopSearchComponent } from './components/sop-search/sop-search.component';
import { ToggleEditInputComponent } from './components/toggle-edit-input/toggle-edit-input.component';
import { VialCheckOutComponent } from './components/vial-check-out/vial-check-out.component';
import { ChickenDisplayComponent } from './display-forms/chicken-display/chicken-display.component';
import { ClientDisplayComponent } from './display-forms/client-display/client-display.component';
import { EggDisplayComponent } from './display-forms/egg-display/egg-display.component';
import { EquipmentDisplayComponent } from './display-forms/equipment-display/equipment-display.component';
import { InvoiceDisplayComponent } from './display-forms/invoice-display/invoice-display.component';
import { ItemDetailDisplayComponent } from './display-forms/item-detail-display/item-detail-display.component';
import { ItemDisplayComponent } from './display-forms/item-display/item-display.component';
import { KitDisplayComponent } from './display-forms/kit-display/kit-display.component';
import { OrderItemDisplayComponent } from './display-forms/orderitem-display/order-item-display.component';
import { ProductDisplayComponent } from './display-forms/product-display/product-display.component';
import { QuoteDisplayComponent } from './display-forms/quote-display/quote-display.component';
import { SopDisplayComponent } from './display-forms/sop-display/sop-display.component';
import { UserDisplayComponent } from './display-forms/user-display/user-display.component';
import { FileExplorerModule } from './file-management-system/file-explorer/module/file-explorer.module';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { MainRoutingModule } from './main/routes/main-routing.module';
import { CatalogSearchComponent } from './master-catalog/catalog-search/catalog-search.component';
import { CatalogComponent } from './master-catalog/catalog/catalog.component';
import { AdminComponent } from './page/admin/admin.component';
import { AttachmentComponent } from './page/attachment/attachment.component';
import { ClientComponent } from './page/client/client.component';
import { ConfigComponent } from './page/config/config.component';
import { ImmunizationComponent as DashImmunizationComponent } from './page/dashboard/dashboard-subcomponents/immunization/immunization.component';
import { InventoryComponent as DashInventoryComponent } from './page/dashboard/dashboard-subcomponents/inventory/inventory.component';
import { DashboardComponent } from './page/dashboard/dashboard.component';
import { EgginventoryComponent } from './page/egginventory/egginventory.component';
import { EquipmentComponent } from './page/equipment/equipment.component';
import { ErrorModule } from './page/error/error.module';
import { ErrorService } from './page/error/error.service';
import { AlertMessageComponent } from './page/eWorksheet/alert-message/alert-message.component';
import { AssignmentDialogComponent } from './page/eWorksheet/AssignmentDialog/assignment-dialog/assignment-dialog.component';
// eWorksheet Components
import { AutoCompleteComponent } from './page/eWorksheet/AutoCompleteInput/auto-complete.component';
import { DialogComponent } from './page/eWorksheet/Dialog/dialog.component';
import { DropDownListComponent } from './page/eWorksheet/DropDownList/drop-down-list.component';
import { EquipmentComponent_WS } from './page/eWorksheet/Equipment/equipment/equipment.component';
import { DragDropDirective } from './page/eWorksheet/FileUpload/file-upload/drag-drop.directive';
import { FileUploadComponent } from './page/eWorksheet/FileUpload/file-upload/file-upload.component';
import { PlayGroundComponent } from './page/eWorksheet/FreeComponents/play-ground/play-ground.component';
import { ProgressTimerComponent } from './page/eWorksheet/FreeComponents/progress-timer/progress-timer.component';
import { RollerButtonComponent } from './page/eWorksheet/FreeComponents/roller-button/roller-button.component';
import { StepperComponent } from './page/eWorksheet/FreeComponents/stepper/stepper.component';
import { TabComponent } from './page/eWorksheet/FreeComponents/tab/tab.component';
import { TextFieldComponent } from './page/eWorksheet/FreeComponents/text-field/text-field.component';
import { WsHeaderComponent } from './page/eWorksheet/Header/ws-header.component';
import { eWorksheetRoutingModule } from './page/eWorksheet/Routes/routing.module';
import { SearchBarComponent } from './page/eWorksheet/SearchBar/search-bar.component';
import { SignatureComponent } from './page/eWorksheet/Signature/signature/signature.component';
import { StepCommentComponent } from './page/eWorksheet/StepComment/step-comment.component';
import { CalibratorComponent } from './page/eWorksheet/StepTypes/Calibrator/calibrator.component';
import { GeneralStepComponent } from './page/eWorksheet/StepTypes/General/general-step/general-step.component';
import { StopStepComponent } from './page/eWorksheet/StepTypes/Stop/stop-step/stop-step.component';
import { SubDilutionComponent } from './page/eWorksheet/StepTypes/SubDilution/sub-dilution/sub-dilution.component';
import { SubIncubationComponent } from './page/eWorksheet/StepTypes/SubIncubation/sub-incubation/sub-incubation.component';
import { SubMaterialComponent } from './page/eWorksheet/StepTypes/SubMaterial/sub-material/sub-material.component';
import { SubstrateStepComponent } from './page/eWorksheet/StepTypes/Substrate/substrate-step/substrate-step.component';
import { WashingStepComponent } from './page/eWorksheet/StepTypes/Washing/washing-step/washing-step.component';
import { StepTimerComponent } from './page/eWorksheet/Timer/step-timer.component';
import { WsDesignEntryComponent } from './page/eWorksheet/WorksheetDesign/ws-design-entry.component';
import { WsExperimentComponent } from './page/eWorksheet/WorksheetExperiment/ws-experiment.component';
import { WorksheetMainComponent } from './page/eWorksheet/WorksheetMain/worksheet-main.component';
import { WsReviewComponent } from './page/eWorksheet/WorksheetReview/ws-review.component';
import { AlertModule } from './page/eWorksheet/_alert';
import { FolderCustodyComponent } from './page/folder-custody/folder-custody.component';
import { FolderDashboardComponent } from './page/folder-custody/folder-dashboard/folder-dashboard.component';
import { FolderDetailsComponent } from './page/folder-custody/folder-details/folder-details.component';
import { FolderDialogComponent } from './page/folder-custody/folder-dialog/folder-dialog/folder-dialog.component';
import { FolderListComponent } from './page/folder-custody/folder-list/folder-list.component';
import { FolderLocationComponent } from './page/folder-custody/folder-location/folder-location.component';
import { FolderRequestComponent } from './page/folder-custody/folder-request/folder-request.component';
import { InventoryComponent } from './page/inventory/inventory.component';
import { ItemSourcesComponent } from './page/item-sources/item-sources.component';
import { KitComponent } from './page/kit/kit.component';
import { LocationComponent } from './page/location/location.component';
import { ManageUsersComponent } from './page/manage-users/manage-users.component';
import { MemberComponent } from './page/member/member.component';
import { OrderItemComponent } from './page/order-item/order-item.component';
import { OrderShipmentComponent } from './page/order-shipment/order-shipment.component';
import { ProductComponent } from './page/product/product.component';
import { QualityControlComponent } from './page/quality-control/quality-control.component';
import { SaleComponent } from './page/sale/sale.component';
import { SopComponent } from './page/sop/sop.component';
import { UserHistoryComponent } from './page/userhistory/user-history.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { routerConfig } from './router.config';
import { QuantityPipe } from './shared/pipes/quantity';
import { InventoryService } from './shared/services/Inventory.Service';
// import { GravatarModule } from 'ngx-gravatar';
import { ItemdetailService } from './shared/services/itemdetail.service';
import { MockDataService } from './shared/services/ws-mock.service';
import { AuthenticationService2 } from './shared/services2/Authenticate2.service';
import { AuthGuardService2 } from './shared/services2/AuthGuard2.service';
import { RequestInterceptorService } from './shared/services2/request-interceptor.service';
import { FloatWindowComponent } from './page/eWorksheet/FreeComponents/float-window/float-window/float-window.component';









@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    DashboardComponent,
    InventoryComponent,
    KitComponent,
    OrderShipmentComponent,
    MemberComponent,
    AdminComponent,
    ItemDisplayComponent,
    ItemDetailDisplayComponent,
    KitDisplayComponent,
    SopDisplayComponent,
    SopComponent,
    SopSearchComponent,
    EgginventoryComponent,
    OrderItemComponent,
    OrderItemDisplayComponent,
    ClientComponent,
    SaleComponent,
    ClientDisplayComponent,
    ClientSearchComponent,
    SimpleClientSearchComponent,
    QuoteSearchComponent,
    QuoteDisplayComponent,
    InvoiceDisplayComponent,
    CatalogComponent,
    CatalogSearchComponent,
    CheckOutComponent,
    VialCheckOutComponent,
    ReviewComponent,
    ItemSearchComponent,
    SalesItemSearchComponent,
    ConfigComponent,
    LocationComponent,
    SettingsComponent,
    SettingDetailsComponent,
    ChickenDisplayComponent,
    EggDisplayComponent,
    ProductDisplayComponent,
    ProductComponent,
    EquipmentDisplayComponent,
    EquipmentComponent,
    UserHistoryComponent,
    ReconstitutionComponent,
    DashInventoryComponent,
    DashImmunizationComponent,
    DeleteWarningDialogComponent,
    ResetpasswordComponent,
    QuantityPipe,
    QualityControlComponent,
    CheckInComponent,
    windowevent,
    ManageUsersComponent,
    UserDisplayComponent,
    ItemSourcesComponent,


    // Components for eWorksheet
    AutoCompleteComponent,
    // G_DialogComponent,
    DialogComponent,
    DropDownListComponent,
    WsHeaderComponent,
    SearchBarComponent,
    StepCommentComponent,
    GeneralStepComponent,
    StopStepComponent,
    SubDilutionComponent,
    SubMaterialComponent,
    SubstrateStepComponent,
    WashingStepComponent,
    StepTimerComponent,
    WsDesignEntryComponent,
    WsReviewComponent,
    WsExperimentComponent,
    WorksheetMainComponent,
    FileUploadComponent,
    DragDropDirective,
    SubIncubationComponent,
    SignatureComponent,
    AssignmentDialogComponent,
    EquipmentComponent_WS,
    AlertMessageComponent,
    EquipmentDisplayComponent,
    StepperComponent,
    TabComponent,
    TextFieldComponent,
    RollerButtonComponent,
    ProgressTimerComponent,
    PlayGroundComponent,
    CalibratorComponent,
    // FileManagementSystemComponent,
    AttachmentComponent,

    // Folder Custody
    FolderCustodyComponent,
    FolderListComponent,
    FolderDetailsComponent,
    FolderLocationComponent,
    FolderDashboardComponent,
    FolderRequestComponent,
    ItemSourceSearchComponent,
    FolderDialogComponent,
    DetailsViewHeaderComponent,
    ToggleEditInputComponent,
    FloatWindowComponent
  ],
  imports: [
    // GravatarModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(routerConfig, { relativeLinkResolution: 'legacy' }),
    NgbModule,
    AngularMyDatePickerModule,
    ClickOutsideModule,
    ChartsModule,
    HttpClientModule,
    NgxBarcodeModule,
    NgxPrintModule,
    PdfViewerModule,
    ReactiveFormsModule,
    MainRoutingModule,
    eWorksheetRoutingModule,
    AngularMaterialModule,
    FileExplorerModule,
    CommonModule,
    AlertModule,
    // FlexLayoutModule
    ErrorModule,
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    FormBuilder,
    AuthGuardService2,
    AuthenticationService2,
    ItemdetailService,
    InventoryService,
    MockDataService,
    ErrorService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptorService,
      multi: true
    }
    // { provide: APP_INITIALIZER, useFactory: initializeSettings, deps: [SettingService2], multi: true},
    // { provide: APP_INITIALIZER, useFactory: initializeLocations, deps: [LocationService2], multi: true},
    // { provide: APP_INITIALIZER, useFactory: initializeEquipment, deps: [EquipmentService2], multi: true}
  ],
  entryComponents: [DialogComponent, SignatureComponent, AssignmentDialogComponent, AlertMessageComponent, FolderDialogComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }



// export function initializeSettings(settingService: SettingService2) {
//   return (): Promise<any> => {
//     return settingService.init().catch( error => {
//       console.log( 'Application failed to initialize settings properly');
//       ErrorUtil.handleHttpError( error );
//     });
//   }
// }

// export function initializeLocations(locationService: LocationService2) {
//   return (): Promise<any> => {
//     return locationService.init().catch( error => {
//       console.log( 'Application failed to initialize locations properly');
//       ErrorUtil.handleHttpError( error );
//     });
//   }
// }


// export function initializeEquipment(equipmentService: EquipmentService2) {
//   return (): Promise<any> => {
//     return equipmentService.init().catch( error => {
//       console.log( 'Application failed to initialize equipment properly');
//       ErrorUtil.handleHttpError( error );
//     });
//   }
// }
