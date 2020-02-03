import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ActionComponent } from './action/action.component';
import { ActionDetailComponent } from './action-detail/action-detail.component';

const routes: Routes = [
  { path: '', component: ActionComponent },
  { path: 'action-detail/:id', component: ActionDetailComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActionsRoutingModule { }
