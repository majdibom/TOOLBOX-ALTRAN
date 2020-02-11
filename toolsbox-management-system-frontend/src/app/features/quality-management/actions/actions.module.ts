import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActionsRoutingModule } from './actions-routing.module';
import { ActionComponent } from './action/action.component';
import { ActionListComponent } from './action-list/action-list.component';
import { ActionUpdateComponent } from './action-update/action-update.component';
import { ActionDetailComponent } from './action-detail/action-detail.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { ActionAddComponent } from './action-add/action-add.component';

@NgModule({
  declarations: [ActionComponent, ActionListComponent, ActionUpdateComponent, ActionDetailComponent,ActionAddComponent],
  imports: [
    CommonModule,
    ActionsRoutingModule,
    NgxPaginationModule,
    FormsModule,
    TranslateModule
  ]
})
export class ActionsModule { }
