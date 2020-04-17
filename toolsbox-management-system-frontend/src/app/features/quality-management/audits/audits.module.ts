import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { AuditsRoutingModule } from './audits-routing.module';
import { AuditComponent } from './audit/audit.component';
import { AuditPlanningComponent } from './audit-planning/audit-planning.component';
import { AuditAddComponent } from './audit-add/audit-add.component';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {ListboxModule} from 'primeng/listbox';
import { AuditListComponent } from './audit-list/audit-list.component';
import { AuditUpdateComponent } from './audit-update/audit-update.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuditDetailComponent } from './audit-detail/audit-detail.component';
import { AuditReportAddComponent } from './audit-report-add/audit-report-add.component';
import { AuditReportUpdateComponent } from './audit-report-update/audit-report-update.component';
import { AuditReportDetailComponent } from './audit-report-detail/audit-report-detail.component';
import { GapUpdateComponent } from './gap-update/gap-update.component';
import { GapAddComponent } from './gap-add/gap-add.component';
import { AuditorSpaceComponent } from './auditor-space/auditor-space.component';
import { ValidateReportComponent } from './validate-report/validate-report.component';
import { AuditedSpaceComponent } from './audited-space/audited-space.component';
import { GapDetailComponent } from './gap-detail/gap-detail.component';
import { GapActionsAddComponent } from './gap-actions-add/gap-actions-add.component';
import { GapActionsUpdateComponent } from './gap-actions-update/gap-actions-update.component';
import { GapActionsListComponent } from './gap-actions-list/gap-actions-list.component';

@NgModule({
  declarations: [AuditComponent, AuditPlanningComponent, AuditAddComponent, AuditListComponent, AuditUpdateComponent, AuditDetailComponent, AuditReportAddComponent, AuditReportUpdateComponent, AuditReportDetailComponent, GapUpdateComponent, GapAddComponent, AuditorSpaceComponent, ValidateReportComponent, AuditedSpaceComponent, GapDetailComponent, GapActionsAddComponent, GapActionsUpdateComponent, GapActionsListComponent],
  imports: [
    CommonModule,
    AuditsRoutingModule,
    NgxPaginationModule,
    NgMultiSelectDropDownModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    ListboxModule,
    TranslateModule
  ]
})
export class AuditsModule { }
