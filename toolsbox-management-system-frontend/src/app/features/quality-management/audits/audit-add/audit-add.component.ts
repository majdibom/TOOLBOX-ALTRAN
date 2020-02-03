import { Component, OnInit, Output } from '@angular/core';
import { Audit } from '@models/audit';
import { Process } from '@models/process';
import { Project } from '@models/project';
import { GenericService } from '@services/generic.service';
import { AuditReference } from '@models/audit-reference';
import { Week } from '@models/week';
import { User } from '@models/user';
import swal from 'sweetalert2';
import { EventEmitter } from 'events';
import { Activity } from '@models/activity';

@Component({
  selector: 'app-audit-add',
  templateUrl: './audit-add.component.html',
  styleUrls: ['./audit-add.component.css']
})
export class AuditAddComponent implements OnInit {
  // Declare object for audit to create
  activityToAdd: Activity = new Activity();

  auditToAdd: Audit = new Audit();

  // List of users
  listOfUsers: any = [];
  // List of projects
  listOfProjects: Project[] = [];
  // List of processes
  listOfProcesses: Process[] = [];
  // List of weeks
  listOfWeeks: Week[] = [];

  selectedLink = 'Project';

  // Event for table data
  @Output() reloadEvent = new EventEmitter();
  listOfAuditors: any = [];

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.getAuditors();
    this.getUsers();
    this.getProcesses();
    this.getProjects();
    this.getWeeks();
  }


  /** Create Audit */
  createAudit() {
    this.genericService.createGeneric('/audits', this.auditToAdd)
        .subscribe(data => {
          if (data.error === false) {
            swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            }),
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
        });
  }

  /** Empty add form fields */
  emptyObject() {
    this.activityToAdd = new Activity();
  }

  /** Get all auditors **/
  getAuditors() {
    this.genericService.getGenericList('/users/auditors').subscribe(data => {
      this.listOfAuditors = data;
      let i: number;
      for (i = 0; i < this.listOfAuditors.length; i++) {
        this.listOfAuditors[i].lastName += (' ' + this.listOfAuditors[i].firstName);
      }
    });
  }

  /** Get all Users*/
  getUsers() {
    this.genericService.getGenericList('/users/all').subscribe(data => {
      this.listOfUsers = data;
      let i: number;
      for (i = 0; i < this.listOfUsers.length; i++) {
        this.listOfUsers[i].lastName += (' ' + this.listOfUsers[i].firstName);
      }
    });
  }
  /** Get all projects*/
  getProjects(): void {
    this.genericService.getGenericList('/project/all').subscribe(data => {
      this.listOfProjects = data;
    });
  }
  /** Get all processes*/
  getProcesses(): void {
    this.genericService.getGenericList('/process/all').subscribe(data => {
      this.listOfProcesses = data;
    });
  }
  /** Get all weeks*/
  getWeeks(): void {
    this.genericService.getGenericList('/weeks/all').subscribe(
      data => {
        this.listOfWeeks = data;
      }
    );
  }
  /** Set radio value */
  setradio(selectedLink: string): void {

    this.selectedLink = selectedLink;

  }

  /** Check selected value */
  isSelected(name: string): boolean {

    if (!this.selectedLink) { // if no radio button is selected, always return false so every nothing is shown
      return false;
    }

    return (this.selectedLink === name); // if current radio button is selected, return true, else return false  
  }

}
