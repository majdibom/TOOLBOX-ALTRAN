import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RisksModule } from './risks/risks.module';
import { AuditsModule } from './audits/audits.module';
import { ActionsModule } from './actions/actions.module';
import { ProjectsModule } from './projects/projects.module';

const routes: Routes = [
  { path: 'actions', loadChildren: () => ActionsModule },
  { path: 'audits', loadChildren: () => AuditsModule },
  { path: 'risks', loadChildren: () => RisksModule },
  { path: 'projects', loadChildren: () => ProjectsModule }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QualityManagementRoutingModule { }
