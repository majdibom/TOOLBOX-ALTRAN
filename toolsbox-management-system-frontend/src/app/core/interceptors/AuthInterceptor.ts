import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Router } from '@angular/router';
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private router: Router) { }


    getTokenInterceptor(): String {
        const currentUser = JSON.parse(localStorage.getItem('currentUser'));
        const token = currentUser.token;
        return token ? token : '';
    }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        req = req.clone({
            setHeaders: {
                'Content-Type': 'application/json; charset=utf-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${this.getTokenInterceptor()}`,
            },
        });
        // return next.handle(req);
        return next.handle(req).do((event: HttpEvent<any>) => {


        }, (err: any) => {
            /**
             * Handle Error Here.
             */
            if (err instanceof HttpErrorResponse) {
                switch (err.status) {
                    case 403:
                        console.log('403 Error');
                        this.router.navigate(['administration/dashboard']);
                        break;
                    case 401:
                        console.log('401 Error');
                        break;
                    case 404:
                        console.log('404 Error');
                        break;
                    case 500:
                        console.log('500 Error');
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
