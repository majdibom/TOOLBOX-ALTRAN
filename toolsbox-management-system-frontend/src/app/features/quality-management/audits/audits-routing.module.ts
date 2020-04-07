import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuditComponent } from './audit/audit.component';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditDetailComponent } from './audit-detail/audit-detail.component';
import { AuditReportDetailComponent } from './audit-report-detail/audit-report-detail.component';
import { AuditorSpaceComponent } from './auditor-space/auditor-space.component';
import { AuditedSpaceComponent } from './audited-space/audited-space.component';

const routes: Routes = [
  { path: '', component: AuditComponent },
  { path: 'all', component: AuditListComponent},
  { path: 'audit-detail/:id', component: AuditDetailComponent },
  { path: 'audit-report-detail/:id', component: AuditReportDetailComponent },
  { path: 'auditor-space/:auditor', component: AuditorSpaceComponent },
  { path: 'audited-space/:audited', component: AuditedSpaceComponent }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuditsRoutingModule { }
