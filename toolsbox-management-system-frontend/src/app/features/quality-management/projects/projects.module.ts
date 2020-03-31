import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectComponent } from './project/project.component';
import { ProjectAddComponent } from './project-add/project-add.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectsRoutingModule } from './projects-routing.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { ListboxModule } from 'primeng/listbox';
import { ProjectUpdateComponent } from './project-update/project-update.component';

@NgModule({
  declarations: [ProjectComponent,ProjectAddComponent,ProjectListComponent, ProjectUpdateComponent],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    NgxPaginationModule,
    FormsModule,
    NgMultiSelectDropDownModule,
    NgbModule,
    TranslateModule,
    ListboxModule
  ]
})
export class ProjectsModule { }
