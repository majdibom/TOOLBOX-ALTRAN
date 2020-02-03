import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorsRoutingModule } from './errors-routing.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [NotFoundComponent],
  imports: [
    CommonModule,
    ErrorsRoutingModule,
    SharedModule
  ]
})
export class ErrorsModule { }
