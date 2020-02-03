import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { FeaturesModule } from './features/features.module';
import { AuthGuard } from './core/guards/auth.guard';


const routes: Routes = [
  { path: 'login', component: AuthenticationComponent },
  { path: '', loadChildren: () => FeaturesModule, canActivate: [AuthGuard] }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
