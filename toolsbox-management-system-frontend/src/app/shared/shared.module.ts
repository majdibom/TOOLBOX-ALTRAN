import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedRoutingModule } from './shared-routing.module';
import { FooterComponent } from './template/footer/footer.component';
import { NavbarComponent } from './template/navbar/navbar.component';
import { SidebarComponent } from './template/sidebar/sidebar.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [NavbarComponent, SidebarComponent, FooterComponent],
  imports: [
    CommonModule,
    SharedRoutingModule,
    TranslateModule
  ],
  exports: [NavbarComponent, SidebarComponent, FooterComponent]
})
export class SharedModule { }
