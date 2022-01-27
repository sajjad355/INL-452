import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettingsComponent } from 'app/components/settings/settings.component';
import { FileManagementSystemComponent } from 'app/file-management-system/file-management-system.component';
import { ClientComponent } from 'app/page/client/client.component';
import { ConfigComponent } from 'app/page/config/config.component';
import { DashboardComponent } from 'app/page/dashboard/dashboard.component';
import { EgginventoryComponent } from 'app/page/egginventory/egginventory.component';
import { EquipmentComponent } from 'app/page/equipment/equipment.component';
import { FolderLocationComponent } from 'app/page/folder-custody/folder-location/folder-location.component';
import { InventoryComponent } from 'app/page/inventory/inventory.component';
import { ItemSourcesComponent } from 'app/page/item-sources/item-sources.component';
import { KitComponent } from 'app/page/kit/kit.component';
import { LocationComponent } from 'app/page/location/location.component';
import { ManageUsersComponent } from 'app/page/manage-users/manage-users.component';
import { OrderItemComponent } from 'app/page/order-item/order-item.component';
import { ProductComponent } from 'app/page/product/product.component';
import { SaleComponent } from 'app/page/sale/sale.component';
import { SopComponent } from 'app/page/sop/sop.component';
import { UserHistoryComponent } from 'app/page/userhistory/user-history.component';
import { MainComponent } from '../main.component';

const routes: Routes = [
  {
    path: 'main',
    component: MainComponent,
    children: [
      {
        path: '', component: DashboardComponent
      },
      {
        path: 'inventories', component: InventoryComponent
      },
      {
        path: 'inventories/:id', component: InventoryComponent
      },
      {
        path: 'order-items', component: OrderItemComponent
      },
      {
        path: 'sop', component: SopComponent
      },
      {
        path: 'sop/:id', component: SopComponent
      },
      {
        path: 'kits', component: KitComponent
      },
      {
        path: 'kits/:id', component: KitComponent
      },
      {
        path: 'egg-inventory', component: EgginventoryComponent
      },
      {
        path: 'clients', component: ClientComponent
      },
      {
        path: 'sales', component: SaleComponent
      },
      {
        path: 'sales/:id', component: SaleComponent
      },
      {
        path: 'products', component: ProductComponent
      },
      {
        path: 'products/:id', component: ProductComponent
      },
      {
        path: 'equipments', component: EquipmentComponent
      },
      {
        path: 'user-histories', component: UserHistoryComponent
      },
      {
        path: 'manage-users', component: ManageUsersComponent
      },
      {
        path: 'file-management', component: FileManagementSystemComponent
      },
      {
        path: 'config', component: ConfigComponent,
        children: [
          {
            path: 'settings',
            component: SettingsComponent,
          },
          {
            path: 'settings/:id',
            component: SettingsComponent,
          },
          {
            path: 'locations',
            component: LocationComponent,
          },
          {
            path: 'manufacturers_and_suppliers',
            component: ItemSourcesComponent,
          },
          {
            path: 'folder_locations',
            component: FolderLocationComponent,
          },
        ]
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }