import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '@models/role';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';
import { Privilege } from '@models/privilege';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-role-update',
  templateUrl: './role-update.component.html',
  styleUrls: ['./role-update.component.css']
})
export class RoleUpdateComponent implements OnInit {

  // Get Role to edit form roles list component
  @Input() roleToEdit: Role;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // List of privileges by category
  listAllPrivileges: any;
  PrivilegesForMenus: any = [];
  PrivilegesForActivities: any = [];
  PrivilegesForRoles: any = [];
  PrivilegesForUsers: any = [];
  PrivilegesForAudits: any = [];
  PrivilegesForAuditReports: any = [];
  PrivilegesForActions: any = [];
  PrivilegesForGap: any = [];
  PrivilegesAllActivities: any;

  // List for displaying priveleges by category
  displayedMenus: any = [];
  displayedActivities: any = [];
  displayedRoles: any = [];
  displayedUsers: any = [];
  displayedAudits: any = [];
  displayedAuditReports: any = [];
  displayedActions: any = [];
  displayedGap: any = [];

  // List of selected privileges by category(for create role)
  selectedPrivilegesMenusToEdit: Privilege[] = [];
  selectedPrivilegesActivitiesToEdit: Privilege[] = [];
  selectedPrivilegesRolesToEdit: Privilege[] = [];
  selectedPrivilegesUsersToEdit: Privilege[] = [];
  selectedPrivilegesAuditsToEdit: Privilege[] = [];
  selectedPrivilegesAuditReportsToEdit: Privilege[] = [];
  selectedPrivilegesActionsToEdit: Privilege[] = [];
  selectedPrivilegesGapToEdit: Privilege[] = [];

  // List of titles of roles to check the existance of any role with the same title
  listTitlesRoles: any = [];
  existingRole: string;
  errorRole = false;
  // Access to all activites values
  activitiesValue: boolean;
  activitiesAccess = false;

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listPrivileges();
  }


  /** Compare existing title of roles with tapped title of role */
  onSearchChangeRole(searchValue: string) {
    function findRole(title) {
      return title === searchValue;
    }
    this.genericService.getGenericById('/roles', this.roleToEdit.id).subscribe(
      role => {
        const index = this.listTitlesRoles.indexOf(role.value.title);
        this.listTitlesRoles.splice(index, 1);
      });
    this.existingRole = this.listTitlesRoles.find(findRole);
    if (isNullOrUndefined(this.existingRole)) {
      this.errorRole = false;
    } else {
      this.errorRole = true;
    }
  }

  /** Update Role */
  updateRole() {
    this.errorRole = false;
    this.roleToEdit.privileges = [];
    // test if role to edit can access to all activities or not
    if (this.activitiesAccess === true) {
      this.roleToEdit.privileges = this.roleToEdit.privileges.concat(this.PrivilegesAllActivities);
    } else {
      const index = this.roleToEdit.privileges.indexOf(this.PrivilegesAllActivities);
      this.roleToEdit.privileges.splice(index, 1);
    }

    this.roleToEdit.privileges = this.roleToEdit.privileges.concat(this.selectedPrivilegesMenusToEdit).
      concat(this.selectedPrivilegesActivitiesToEdit).concat(this.selectedPrivilegesRolesToEdit).concat(this.selectedPrivilegesUsersToEdit).
      concat(this.selectedPrivilegesAuditsToEdit).concat(this.selectedPrivilegesAuditReportsToEdit).
      concat(this.selectedPrivilegesActionsToEdit).concat(this.selectedPrivilegesGapToEdit);
    this.genericService.updateGeneric('/roles', this.roleToEdit.id, this.roleToEdit).subscribe(
      data => {
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
        } else {
          Swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'ok'
          });
        }

      },
      error => console.log('Role update Error: ' + error));
  }

  /** Get all privileges of roles to display according to a category */
  listPrivileges() {
    this.genericService.getGenericList('/roles/titles').subscribe(
      data => {
        this.listTitlesRoles = data;
        this.genericService.getGenericList('/privileges').subscribe(
          data1 => {
            // Get all privileges
            this.listAllPrivileges = data1;
            this.listAllPrivileges.forEach(privilege => {
              // PUT privileges with category Menu in PrivilegesForMenus list
              if (privilege.category === 'MENU') {
                this.PrivilegesForMenus.push(privilege);
              }
              // PUT privileges with category ACTIVITY in PrivilegesForActivities list
              if (privilege.category === 'ACTIVITY') {
                this.PrivilegesForActivities.push(privilege);
              }
              // PUT privileges with category ROLE in PrivilegesForRoles list
              if (privilege.category === 'ROLE') {
                this.PrivilegesForRoles.push(privilege);
              }
              // PUT privileges with category USER in PrivilegesForUsers list
              if (privilege.category === 'USER') {
                this.PrivilegesForUsers.push(privilege);
              }
              // PUT privileges with category AUDIT in PrivilegesForAudits list
              if (privilege.category === 'AUDIT') {
                this.PrivilegesForAudits.push(privilege);
              }
              // PUT privileges with category AUDIT_REPORT in PrivilegesForAuditReports list
              if (privilege.category === 'AUDIT_REPORT') {
                this.PrivilegesForAuditReports.push(privilege);
              }
              // PUT privileges with category ACTION in PrivilegesForActions list
              if (privilege.category === 'ACTION') {
                this.PrivilegesForActions.push(privilege);
              }
              // PUT privileges with category GAP in PrivilegesForGap list
              if (privilege.category === 'GAP') {
                this.PrivilegesForGap.push(privilege);
              }
              // PUT privileges with category ALL_ACTIVITIES in PrivilegesAllActivities list
              if (privilege.category === 'ALL') {
                this.PrivilegesAllActivities = privilege;
              }
              this.displayedMenus = this.PrivilegesForMenus;
              this.displayedActivities = this.PrivilegesForActivities;
              this.displayedRoles = this.PrivilegesForRoles;
              this.displayedUsers = this.PrivilegesForUsers;
              this.displayedAudits = this.PrivilegesForAudits;
              this.displayedAuditReports = this.PrivilegesForAuditReports;
              this.displayedActions = this.PrivilegesForActions;
              this.displayedGap = this.PrivilegesForGap;
            });
            this.roleToEdit.privileges.forEach(privilege => {
              // List of selected privileges by category(for update role)
              if (privilege.category === 'MENU') {
                this.selectedPrivilegesMenusToEdit.push(privilege);
              }
              if (privilege.category === 'ACTIVITY') {
                this.selectedPrivilegesActivitiesToEdit.push(privilege);
              }
              if (privilege.category === 'ROLE') {
                this.selectedPrivilegesRolesToEdit.push(privilege);
              }
              if (privilege.category === 'USER') {
                this.selectedPrivilegesUsersToEdit.push(privilege);
              }
              if (privilege.category === 'AUDIT') {
                this.selectedPrivilegesAuditsToEdit.push(privilege);
              }
              if (privilege.category === 'AUDIT_REPORT') {
                this.selectedPrivilegesAuditReportsToEdit.push(privilege);
              }
              if (privilege.category === 'ACTION') {
                this.selectedPrivilegesActionsToEdit.push(privilege);
              }
              if (privilege.category === 'GAP') {
                this.selectedPrivilegesGapToEdit.push(privilege);
              }
              if (privilege.category === 'ALL') {
                this.activitiesValue = true;
              }
            });
          });
      });
  }
  /** Check if all activities is shared for role to edit or not (Check box value) */
  checkActivities() {
    if (this.activitiesValue) {
      this.activitiesAccess = true;
    } else {
      this.activitiesAccess = false;
    }
  }

}
