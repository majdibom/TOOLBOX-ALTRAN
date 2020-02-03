import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Role } from '@models/role';
import swal from 'sweetalert2';
import { isNullOrUndefined } from 'util';
import { Privilege } from '@models/privilege';

@Component({
  selector: 'app-role-add',
  templateUrl: './role-add.component.html',
  styleUrls: ['./role-add.component.css']
})
export class RoleAddComponent implements OnInit {

  // Role object for create action
  roleToAdd: Role = new Role();

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
  selectedPrivilegesMenus: Privilege[] = [];
  selectedPrivilegesActivities: Privilege[] = [];
  selectedPrivilegesRoles: Privilege[] = [];
  selectedPrivilegesUsers: Privilege[] = [];
  selectedPrivilegesAudits: Privilege[] = [];
  selectedPrivilegesAuditReports: Privilege[] = [];
  selectedPrivilegesActions: Privilege[] = [];
  selectedPrivilegesGap: Privilege[] = [];

  // List of titles of roles to check the existance of any role with the same title
  listTitlesRoles: any = [];
  existingRole: string;
  errorRole = false;
  // Access to all activites values
  activitiesValue: boolean;
  activitiesAccess = false;
  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  constructor(private roleService: GenericService) { }

  ngOnInit() {
    this.getTitlesOfRoles();
    this.getAllPrivileges();
  }


  /** Get all titles of roles to control the unicity of title's of role */
  getTitlesOfRoles() {
    this.roleService.getGenericList('/roles/titles').subscribe(
      data => {
        this.listTitlesRoles = data;
      }
    );
  }

  /** Compare existing title of roles with tapped title of role */
  onSearchChangeRole(searchValue: string) {
    function findRole(title) {
      return title === searchValue;
    }
    this.existingRole = this.listTitlesRoles.find(findRole);
    if (isNullOrUndefined(this.existingRole)) {
      this.errorRole = false;
    } else {
      this.errorRole = true;
    }
  }

  /** Create Role */
  createRole() {
    // test if role to add can access to all activities or not
    if (this.activitiesAccess === true) {
      this.roleToAdd.privileges.concat(this.PrivilegesAllActivities);
    } else {
      const index = this.roleToAdd.privileges.indexOf(this.PrivilegesAllActivities);
      this.roleToAdd.privileges.splice(index, 1);
    }
    this.roleToAdd.privileges = this.roleToAdd.privileges.concat(this.PrivilegesAllActivities).concat(this.selectedPrivilegesMenus).
      concat(this.selectedPrivilegesActivities).concat(this.selectedPrivilegesRoles).concat(this.selectedPrivilegesUsers).
      concat(this.selectedPrivilegesAudits).concat(this.selectedPrivilegesAuditReports).concat(this.selectedPrivilegesActions).
      concat(this.selectedPrivilegesGap);
    this.roleService.createGeneric('/roles', this.roleToAdd)
      .subscribe(data => {
        if (data.error === false) {
          swal({
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
          swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'Ok'
          });
          this.emptyObject();
        }
      }, err => { console.log(err); });
  }


  /** Get all privileges of roles to display according to a category */
  getAllPrivileges() {
    this.roleService.getGenericList('/privileges').subscribe(
      data => {
        // Get all privileges
        this.listAllPrivileges = data;
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
          }// PUT privileges with category ACTION in PrivilegesForActions list
          if (privilege.category === 'ACTION') {
            this.PrivilegesForActions.push(privilege);
          }// PUT privileges with category GAP in PrivilegesForGap list
          if (privilege.category === 'GAP') {
            this.PrivilegesForGap.push(privilege);
          }
          // PUT privileges with category ALL_ACTIVITIES in PrivilegesAllActivities list
          if (privilege.category === 'ALL') {
            this.PrivilegesAllActivities = privilege;
          }
        });

        this.displayedMenus = this.PrivilegesForMenus;
        this.displayedActivities = this.PrivilegesForActivities;
        this.displayedRoles = this.PrivilegesForRoles;
        this.displayedUsers = this.PrivilegesForUsers;
        this.displayedAudits = this.PrivilegesForAudits;
        this.displayedAuditReports = this.PrivilegesForAuditReports;
        this.displayedActions = this.PrivilegesForActions;
        this.displayedGap = this.PrivilegesForGap;
      }
    );
  }

  /** Empty add form fields */
  emptyObject() {
    this.roleToAdd = new Role();
    // Empty SelectedPrivileges
    this.selectedPrivilegesMenus = [];
    this.selectedPrivilegesActivities = [];
    this.selectedPrivilegesRoles = [];
    this.selectedPrivilegesUsers = [];
    this.selectedPrivilegesAudits = [];
    this.selectedPrivilegesAuditReports = [];
    this.selectedPrivilegesActions = [];
    this.selectedPrivilegesGap = [];
  }

  /** Check if all activities is shared for role user or not (Check box value) */
  checkActivities() {
    if (this.activitiesValue) {
      this.activitiesAccess = true;
    } else {
      this.activitiesAccess = false;
    }
  }

}
