import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Audit } from '@models/audit';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { User } from '@models/user';
import { Project } from '@models/project';
import { Process } from '@models/process';
import { Week } from '@models/week';
import { AuditStatus } from '@models/audit-Status';

@Component({
  selector: 'app-audit-update',
  templateUrl: './audit-update.component.html',
  styleUrls: ['./audit-update.component.css']
})
export class AuditUpdateComponent implements OnInit {
  // List of users for Action edit
  listOfUsers: Array<User> = [];
  // List of projects
  listOfProjects: Project[] = [];
  // List of processes
  listOfProcesses: Process[] = [];
  // List of weeks
  listOfWeeks: Week[] = [];
  // Get audit to edit form audit list component
  @Input() auditToEdit: Audit;
  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();
  listOfAuditors: any = [];
  selectedLink = 'Process';
  listOfAuditStatus: string[];
  auditStatusValue: AuditStatus;
  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.getListOfUsers();
    this.getProcesses();
    this.getProjects();
    this.getWeeks();
    this.listOfAuditStatus = Object.keys(AuditStatus);
    this.listOfAuditStatus = this.listOfAuditStatus.slice(this.listOfAuditStatus.length / 5);
  }
  /** Enum selected value parsing */
  parseValue(value: string) {
    this.auditStatusValue = AuditStatus[value];
  }
  /** Get all users **/
  getListOfUsers() {
    this.genericService.getGenericList('/users/all').subscribe(data => {
      this.listOfUsers = data;
      // let i: number;
      // this.concatinate(i);
    });
  }
  /** Get all projects*/
  getWeeks(): void {
    this.genericService.getGenericList('/weeks/all/audits').subscribe(data => {
      this.listOfWeeks = data;
    });
  }
  /** Get all projects*/
  getProjects(): void {
    this.genericService.getGenericList('/projects/all').subscribe(data => {
      this.listOfProjects = data;
      console.log(this.listOfProjects)
    });
  }
  /** Get all processes*/
  getProcesses(): void {
    this.genericService.getGenericList('/process/all').subscribe(data => {
      this.listOfProcesses = data;
      console.log(this.listOfProcesses)
    });
  }
  concatinate(i: number) {
    for (i = 0; i < this.listOfUsers.length; i++) {
      this.listOfUsers[i].firstName += (' ' + this.listOfUsers[i].lastName);
    }
  }
  /** Update Audit */
  updateAudit() {
    this.genericService.updateGeneric('/audits', this.auditToEdit.id, this.auditToEdit)
      .subscribe(
        data => {
          this.auditToEdit = data as Audit;
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
          } else {
            swal({
              title: 'Erreur!',
              text: data.value,
              type: 'error',
              confirmButtonText: 'ok'
            });
          }
        });
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
