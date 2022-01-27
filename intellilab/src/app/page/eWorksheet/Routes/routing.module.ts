import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EquipmentComponent_WS } from '../Equipment/equipment/equipment.component';
import { FileUploadComponent } from '../FileUpload/file-upload/file-upload.component';
import { WsDesignEntryComponent } from '../WorksheetDesign/ws-design-entry.component';
import { WsExperimentComponent } from '../WorksheetExperiment/ws-experiment.component';
import { WsReviewComponent } from '../WorksheetReview/ws-review.component';




const routes: Routes = [
  { path: "platemap", pathMatch: 'full',component: FileUploadComponent },
  { path: "equipment_ws", pathMatch: 'full',component: EquipmentComponent_WS},
  { path: "eworksheet", pathMatch: 'full',redirectTo:'ws_review_copy' },
  { path: "ws_design", pathMatch: 'full',component: WsDesignEntryComponent },
  { path: "ws_design/:templateId", pathMatch: 'full',component: WsDesignEntryComponent },
  { path: "ws_review_copy", pathMatch: 'full', component: WsReviewComponent },
  { path: "ws_review_copy/:templateId",pathMatch: 'full', component: WsReviewComponent},
  { path: "ws_experiment/analyst/:analystId",pathMatch: 'full', component: WsExperimentComponent},
  { path: "ws_experiment/scientist/:scientistId",pathMatch: 'full', component: WsExperimentComponent},
  { path: "ws_experiment/execution/:templateId",pathMatch: 'full', component: WsExperimentComponent},
  { path: "ws_experiment/incomplete/:worksheetId",pathMatch: 'full', component: WsExperimentComponent},
  { path: "ws_experiment/complete/:reviewId",pathMatch: 'full', component: WsExperimentComponent},

//route for testing only, will be deleted later
  { path: "ws_experiment/:analystId",pathMatch: 'full', component: WsExperimentComponent},

  { path: "ws_experiment/worksheet/:worksheetId",pathMatch: 'full', component: WsExperimentComponent},
  // {
  //   path: 'create_ws_design',
  //   pathMatch: 'full',
  //   component: WsDesignEntryComponent
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class eWorksheetRoutingModule { }
