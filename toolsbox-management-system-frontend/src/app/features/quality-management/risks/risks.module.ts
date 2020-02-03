import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ListboxModule } from 'primeng/listbox';
import { RisksRoutingModule } from './risks-routing.module';
import { RiskComponent } from './risk/risk.component';
import { RiskDetailComponent } from './risk-detail/risk-detail.component';
import { RiskListComponent } from './risk-list/risk-list.component';
import { RiskUpdateComponent } from './risk-update/risk-update.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { RiskAddComponent } from './risk-add/risk-add.component';

@NgModule({
  declarations: [RiskComponent, RiskDetailComponent, RiskListComponent, RiskUpdateComponent, RiskAddComponent],
  imports: [
    CommonModule,
    RisksRoutingModule,
    NgxPaginationModule,
    FormsModule,
    TranslateModule,
    ListboxModule
  ]
})
export class RisksModule { }
