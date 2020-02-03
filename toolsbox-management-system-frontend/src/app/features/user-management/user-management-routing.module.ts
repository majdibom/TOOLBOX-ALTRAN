import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ActivitiesModule } from './activities/activities.module';
import { RolesModule } from './roles/roles.module';
import { UsersModule } from './users/users.module';

const routes: Routes = [
  { path: 'users', loadChildren: () => UsersModule },
  { path: 'activities', loadChildren: () => ActivitiesModule },
  { path: 'roles', loadChildren: () => RolesModule }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserManagementRoutingModule { }
