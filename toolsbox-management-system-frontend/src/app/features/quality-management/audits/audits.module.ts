import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuditsRoutingModule } from './audits-routing.module';
import { AuditComponent } from './audit/audit.component';
import { AuditPlanningComponent } from './audit-planning/audit-planning.component';
import { AuditAddComponent } from './audit-add/audit-add.component';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {ListboxModule} from 'primeng/listbox';

@NgModule({
  declarations: [AuditComponent, AuditPlanningComponent, AuditAddComponent],
  imports: [
    CommonModule,
    AuditsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    ListboxModule,
    TranslateModule
  ]
})
export class AuditsModule { }
