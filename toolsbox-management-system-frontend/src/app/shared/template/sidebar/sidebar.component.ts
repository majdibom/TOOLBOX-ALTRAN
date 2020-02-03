import { Component, OnInit } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { GenericService } from '@services/generic.service';
import { Role } from '@models/role';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  displayQuestion: Boolean = false;
  displayQuiz: Boolean = false;
  displayUser: Boolean = false;
  displayDegree: Boolean = false;
  displayTechnology: Boolean = false;
  displayCandidate: Boolean = false;
  displayInterview: Boolean = false;
  displayActivity: Boolean = false;
  displayMessage: Boolean = false;
  displayAffectQuiz: Boolean = false;
  ListOfPrivilegesByUser: any = [];
  role: Role = new Role();
  testAdmin: Boolean = false;

  constructor(private sideBarService: GenericService) { }

  ngOnInit() {
    // this.PageAccess();
    // this.VerifyAdmin();
  }


  /* VerifyAdmin() {
     const currentUser = JSON.parse(localStorage.getItem('currentUser'));
     this.sideBarService.getUserByUsername('/users/username', currentUser.username)
       .subscribe(
         data => {
           this.role = data.value.role;
           if (this.role.title === 'Admin') {
             this.testAdmin = true;
           } else {
             this.testAdmin = false;
           }
         });
   }*/

  /* PageAccess() {
     // test the exist of SHOW_QUESTION privilege
     function findPrivilegeQuestion(privilege: any) {
       return privilege === 'SHOW_QUESTION';
     }
     // test the exist of SHOW_QUIZ privilege
     function findPrivilegeQuiz(privilege: any) {
       return privilege === 'SHOW_QUIZ';
     }
     // test the exist of SHOW_USER privilege
     function findPrivilegeUser(privilege: any) {
       return privilege === 'SHOW_USER';
     }
     // test the exist of SHOW_DEGREE privilege
     function findPrivilegeDegree(privilege: any) {
       return privilege === 'SHOW_DEGREE';
     }
     // test the exist of SHOW_DEGREE privilege
     function findPrivilegeTechnology(privilege: any) {
       return privilege === 'SHOW_TECHNOLOGY';
     }
     // test the exist of SHOW_INTERVIEW privilege
     function findPrivilegeInterview(privilege: any) {
       return privilege === 'SHOW_INTERVIEW';
     }
     // test the exist of SHOW_MESSAGE privilege
     function findPrivilegeMessage(privilege: any) {
       return privilege === 'SHOW_MESSAGE';
     }
     // test the exist of AFFECT_QUIZ privilege
     function findPrivilegeAffectationQUIZ(privilege: any) {
       return privilege === 'Affect_QUIZ';
     }
     // test the exist of SHOW_ACTIVITY privilege
     function findPrivilegeActivity(privilege: any) {
       return privilege === 'SHOW_ACTIVITY';
     }
     const currentUser = JSON.parse(localStorage.getItem('currentUser'));
     this.sideBarService.getUserByUsername('/users/username', currentUser.username)
       .subscribe(
         data => {
           // Gets list of privileges of current user and save titles of privileges in a list
           data.value.role.privileges.forEach(element => {
             this.ListOfPrivilegesByUser.push(element.title);
           });
           // return the first item respecting condition of findPrivilegeQuestion function
           const foundQuestion = this.ListOfPrivilegesByUser.find(findPrivilegeQuestion);
           if (isNullOrUndefined(foundQuestion)) {
             this.displayQuestion = false;
           } else {
             this.displayQuestion = true;
           }
           // return the first item respecting condition of findPrivilegeQuiz function
           const foundQuiz = this.ListOfPrivilegesByUser.find(findPrivilegeQuiz);
           if (isNullOrUndefined(foundQuiz)) {
             this.displayQuiz = false;
           } else {
             this.displayQuiz = true;
           }
           // return the first item respecting condition of findPrivilegeDregree function
           const foundDegree = this.ListOfPrivilegesByUser.find(findPrivilegeDegree);
           if (isNullOrUndefined(foundDegree)) {
             this.displayDegree = false;
           } else {
             this.displayDegree = true;
           }
           // return the first item respecting condition of findPrivilegeUser function
           const foundUser = this.ListOfPrivilegesByUser.find(findPrivilegeUser);
           if (isNullOrUndefined(foundUser)) {
             this.displayUser = false;
           } else {
             this.displayUser = true;
           }
           // return the first item respecting condition of findPrivilegeInterview function
           const foundInterview = this.ListOfPrivilegesByUser.find(findPrivilegeInterview);
           if (isNullOrUndefined(foundInterview)) {
             this.displayInterview = false;
           } else {
             this.displayInterview = true;
           }
           // return the first item respecting condition of findPrivilegeTechnology function
           const foundTechnology = this.ListOfPrivilegesByUser.find(findPrivilegeTechnology);
           if (isNullOrUndefined(foundTechnology)) {
             this.displayTechnology = false;
           } else {
             this.displayTechnology = true;
           }
           // return the first item respecting condition of findPrivilegeActivity function
           const foundActivity = this.ListOfPrivilegesByUser.find(findPrivilegeActivity);
           if (isNullOrUndefined(foundActivity)) {
             this.displayActivity = false;
           } else {
             this.displayActivity = true;
           }
           // return the first item respecting condition of findPrivilegeMessage function
           const foundMessage = this.ListOfPrivilegesByUser.find(findPrivilegeMessage);
           if (isNullOrUndefined(foundMessage)) {
             this.displayMessage = false;
           } else {
             this.displayMessage = true;
           }
           // return the first item respecting condition of findPrivilegeAffectationQUIZ function
           const foundAffectationQuiz = this.ListOfPrivilegesByUser.find(findPrivilegeAffectationQUIZ);
           if (isNullOrUndefined(foundAffectationQuiz)) {
             this.displayAffectQuiz = false;
           } else {
             this.displayAffectQuiz = true;
           }
         });
   }*/

}
