import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UserComponent } from './user/user.component';
import { UserAddComponent } from './user-add/user-add.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserUpdateComponent } from './user-update/user-update.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { TranslateModule } from '@ngx-translate/core';
import { ListboxModule } from 'primeng/listbox';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [UserComponent, UserAddComponent, UserListComponent, UserUpdateComponent],
  imports: [
    CommonModule,
    UsersRoutingModule,
    ListboxModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    NgbTooltipModule,
    TranslateModule
  ]
})
export class UsersModule { }
