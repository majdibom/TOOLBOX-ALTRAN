import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { HomeComponent } from './home/home.component';
import { TranslateModule } from '@ngx-translate/core';
import { UsersModule } from '../user-management/users/users.module';
import { AuditsModule } from '../quality-management/audits/audits.module';
import { ChartModule } from 'primeng/chart';
import { ActionsModule } from '../quality-management/actions/actions.module';
import { RisksModule } from '../quality-management/risks/risks.module';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    TranslateModule,
    UsersModule,
    AuditsModule,
    ChartModule,
    ActionsModule,
    RisksModule
  ]
})
export class DashboardModule { }
