import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { User } from '@models/user';
import { GenericService } from '@services/generic.service';
import { isNullOrUndefined } from 'util';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

// Event for reload table content
@Output() reloadEvent = new EventEmitter();

// Declaration of variables for unicity control of email and username
userEmail: String;
errorMail = false;
emails: any = [];

// User object for create action
userToAdd: User = new User();

// List of roles and activities for user create
listOfRoles: any = [];
listOfRolesWithoutAdmin: any = [];
listOfActivities: any = [];

constructor(private genericService: GenericService) { }

ngOnInit() {
  this.getListOfRoles();
  this.getListOfActivities();
}


/** Get E-mails list of existing users */
getListOfMails() {
  this.genericService.getGenericList('/users/emails')
    .subscribe(data2 => {
      this.emails = data2;
    });
}
/** Get all roles **/
getListOfRoles() {
  this.genericService.getGenericList('/roles/all').subscribe(data => {
    this.listOfRoles = data;
    // Removes Admin role
   /* this.listOfRoles.forEach(role => {
      if (role.title !== 'Admin') {
        this.listOfRolesWithoutAdmin.push(role);
      }
    });*/
  });
}

/** Get all activities **/
getListOfActivities() {
  this.genericService.getGenericList('/activities/all').subscribe(data => {
    this.listOfActivities = data;
  });
}

/** Create User */
createRecruiter() {
  this.errorMail = false;
  this.genericService.createGeneric('/users', this.userToAdd)
    .subscribe(data => {
      if (data.error === false) {
        Swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
        // reload table data
        this.reloadEvent.emit(null);
        // empty add form fields
        this.emptyObject();
      } else {
        Swal({
          title: 'Erreur!',
          text: data.value,
          type: 'error',
          confirmButtonText: 'ok'
        });
      }
    });
}

/** Compare existing emails of users with tapped email */
onSearchChangeEmail(searchValue: string) {
  this.genericService.getGenericList('/users/emails')
    .subscribe(data2 => {
      this.emails = data2;
      function findEmail(email) {
        return email === searchValue;
      }
      this.userEmail = this.emails.find(findEmail);
      if (isNullOrUndefined(this.userEmail)) {
        this.errorMail = false;
      } else {
        this.errorMail = true;
      }
    });
}

/** Empty add form fields */
emptyObject() {
  this.userToAdd = new User();
}

}
