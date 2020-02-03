import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FeaturesComponent } from './features.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { UserManagementModule } from './user-management/user-management.module';
import { QualityManagementModule } from './quality-management/quality-management.module';

const routes: Routes = [
  {
    path: '',
    component: FeaturesComponent,
    children: [
      { path: '', loadChildren: () => DashboardModule },
      { path: 'user-management', loadChildren: () => UserManagementModule },
      { path: 'quality-management', loadChildren: () => QualityManagementModule }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
