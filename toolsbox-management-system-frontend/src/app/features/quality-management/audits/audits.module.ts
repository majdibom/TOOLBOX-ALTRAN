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

@NgModule({
  declarations: [AuditComponent, AuditPlanningComponent, AuditAddComponent, AuditListComponent, AuditUpdateComponent, AuditDetailComponent, AuditReportAddComponent, AuditReportUpdateComponent, AuditReportDetailComponent],
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
