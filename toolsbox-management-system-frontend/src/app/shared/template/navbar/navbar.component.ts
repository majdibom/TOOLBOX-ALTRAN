import { Component, OnInit } from '@angular/core';
import { User } from '@models/user';
import { AuthenticationService } from '@services/authentication.service';
import { GenericService } from '@services/generic.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userInformation: User = new User();
  username: any;
  firstName: any;
  lastName: any;

  constructor(private authenticationService: AuthenticationService, private genericService: GenericService,
    private translate: TranslateService) {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('fr');
    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('fr');
  }

  ngOnInit() {
    this.getUsername();
    this.getUserConnectedInfo();
  }


  getUsername() {
    this.username = this.authenticationService.getUsername();
  }

  getUserConnectedInfo() {
    this.genericService.getUserByUsername('/users/username', this.username).subscribe(data => {
      this.userInformation = data.value;
      this.firstName = this.userInformation.firstName;
      this.lastName = this.userInformation.lastName;
    });
  }

  useLanguage(language: string) {
    this.translate.use(language);
  }

  logout() {
    this.authenticationService.logout();
  }


}
