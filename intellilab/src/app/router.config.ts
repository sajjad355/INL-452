import { MainComponent } from './main/main.component';
import { LoginComponent } from './login/login.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { Route } from "@angular/router";
import { RouterModule, Routes } from '@angular/router';
// import {AuthGuard} from './shared/services/AuthGuard'
import { WsDesignEntryComponent } from './page/eWorksheet/WorksheetDesign/ws-design-entry.component';
import { WsReviewComponent } from './page/eWorksheet/WorksheetReview/ws-review.component';
import { WsExperimentComponent } from './page/eWorksheet/WorksheetExperiment/ws-experiment.component';
import { AuthGuardService2 } from './shared/services2/AuthGuard2.service';
import { StepperComponent } from './page/eWorksheet/FreeComponents/stepper/stepper.component';
import { TextFieldComponent } from './page/eWorksheet/FreeComponents/text-field/text-field.component';
import { RollerButtonComponent } from './page/eWorksheet/FreeComponents/roller-button/roller-button.component';
import { ProgressTimerComponent } from './page/eWorksheet/FreeComponents/progress-timer/progress-timer.component';
import { PlayGroundComponent } from './page/eWorksheet/FreeComponents/play-ground/play-ground.component';
import { FileManagementSystemComponent } from './file-management-system/file-management-system.component';
import { LoaderComponent } from './file-management-system/file-explorer/modals/loader/loader.component';
import { FolderCustodyComponent } from './page/folder-custody/folder-custody.component';
import { FolderLocationComponent } from './page/folder-custody/folder-location/folder-location.component';
import { FolderDashboardComponent } from './page/folder-custody/folder-dashboard/folder-dashboard.component';
import { FolderRequestComponent } from './page/folder-custody/folder-request/folder-request.component';
import { FolderListComponent } from './page/folder-custody/folder-list/folder-list.component';

/* not necessary
const indexRoute: Route =
{
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
};

const fallbackRoute: Route =
{
    path: '**',
    redirectTo: '/login',
    pathMatch: 'full'
};*/

export const routerConfig: Route[] = [
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'resetpassword',
        children: [
            {
              path: ':token',
              component: ResetpasswordComponent
            }
          ]
    },
    {
        path: 'main',
        component: MainComponent,
        canActivate: [AuthGuardService2]
    },
    {
      path: 'folders',
      component: FolderCustodyComponent,
      canActivate: [AuthGuardService2],
      children: [
          { path: '',                         redirectTo: 'dashboard', pathMatch: 'full' },  
          { path: 'dashboard',                component: FolderDashboardComponent },
          { path: 'list',                     component: FolderListComponent },
          { path: 'locations',                component: FolderLocationComponent },
          { path: 'requests',                 component: FolderRequestComponent },
      ]
    },
    {
      path: 'main/fmc',
      component: FileManagementSystemComponent,

    },
    {
      path: 'main/fmc/loader',
      component: LoaderComponent,

    },
    {
      path: 'main/timer',
      component: ProgressTimerComponent,

    },
    {
      path: 'main/playground',
      component: PlayGroundComponent,

    },
    {
        path: 'main/orders/:id',
        component: MainComponent,
        canActivate: [AuthGuardService2]
    },
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: '/login',
        pathMatch: 'full'
    }
];
