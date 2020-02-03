import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RolesRoutingModule } from './roles-routing.module';
import { RoleComponent } from './role/role.component';
import { RoleAddComponent } from './role-add/role-add.component';
import { RoleListComponent } from './role-list/role-list.component';
import { RoleUpdateComponent } from './role-update/role-update.component';
import { ListboxModule } from 'primeng/listbox';
import { NgxPaginationModule } from 'ngx-pagination';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [RoleComponent, RoleAddComponent, RoleListComponent, RoleUpdateComponent],
  imports: [
    CommonModule,
    RolesRoutingModule,
    ListboxModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    TranslateModule
  ]
})
export class RolesModule { }
