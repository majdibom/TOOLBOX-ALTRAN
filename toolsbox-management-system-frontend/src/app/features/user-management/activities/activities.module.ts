import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivitiesRoutingModule } from './activities-routing.module';
import { ActivityComponent } from './activity/activity.component';
import { ActivityListComponent } from './activity-list/activity-list.component';
import { ActivityAddComponent } from './activity-add/activity-add.component';
import { ActivityUpdateComponent } from './activity-update/activity-update.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [ActivityComponent, ActivityListComponent, ActivityAddComponent, ActivityUpdateComponent],
  imports: [
    CommonModule,
    ActivitiesRoutingModule,
    NgxPaginationModule,
    FormsModule,
    TranslateModule
  ]
})
export class ActivitiesModule { }
