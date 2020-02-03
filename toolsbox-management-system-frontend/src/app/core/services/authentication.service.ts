import { Injectable, EventEmitter } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { AlertService } from './alert.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // Login URL (Generate token)
  private service = 'http://localhost:8082/login';

  // Header of http request
  private headers = new Headers({ 'Content-Type': 'application/json' });

  constructor(private http: Http, private alertService: AlertService, private router: Router) { }


  login(username: String, password: String) {
    return this.http.post(this.service, JSON.stringify({ username: username, password: password }), { headers: this.headers })
      .map((response: Response) => {
        if (!(response.json().error)) {
          // login successful if there's a jwt token in the response
          const token = response.json() && response.json().message;

          // store username and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify({ username: username, token: token }));
          return true;
        } else {
          this.alertService.error(response.json().message);
          return false;
        }

      }
      ).catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }


  getToken(): String {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }


  logout(): void {
    // clear token remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.router.navigate(['login']);
  }


  getTokenLog(): String {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }


  isLoggedIn(): boolean {
    const token: String = this.getTokenLog();
    return token && token.length > 0;
  }


  getRole(): String {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const jwtData = currentUser.token.split('.')[1];
    const decodedJwtJsonData = window.atob(jwtData);
    const decodedJwtData = JSON.parse(decodedJwtJsonData);
    const role = decodedJwtData.scopes;
    return role;
  }


  getUsername(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const jwtData = currentUser.token.split('.')[1];
    const decodedJwtJsonData = window.atob(jwtData);
    const decodedJwtData = JSON.parse(decodedJwtJsonData);
    const username = decodedJwtData.sub;
    return username;
  }

}
