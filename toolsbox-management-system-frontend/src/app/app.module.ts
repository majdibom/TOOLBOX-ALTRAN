import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';
import { SharedModule } from './shared/shared.module';
import { AuthGuard } from './core/guards/auth.guard';
import { GenericService } from './core/services/generic.service';
import { AuthInterceptor } from './core/interceptors/AuthInterceptor';
import { AuthenticationService } from './core/services/authentication.service';
import { AlertService } from './core/services/alert.service';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthenticationComponent } from './authentication/authentication/authentication.component';
import { AuthenticationAlertComponent } from './authentication/authentication-alert/authentication-alert.component';
import { NotFoundComponent } from './core/errors/not-found/not-found.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import ngx-translate and the http loader
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent, AuthenticationComponent, AuthenticationAlertComponent, NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    SharedModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    GenericService,
    AuthenticationService,
    AlertService,
    AuthGuard,
    AuthInterceptor, {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
