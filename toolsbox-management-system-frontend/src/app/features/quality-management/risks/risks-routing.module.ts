import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RiskComponent } from './risk/risk.component';
import { RiskDetailComponent } from './risk-detail/risk-detail.component';
import { RiskAddComponent } from './risk-add/risk-add.component';

const routes: Routes = [
  { path: '', component: RiskComponent },
  { path: 'risk-add', component: RiskAddComponent },
  { path: 'risk-detail/:id', component: RiskDetailComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RisksRoutingModule { }
