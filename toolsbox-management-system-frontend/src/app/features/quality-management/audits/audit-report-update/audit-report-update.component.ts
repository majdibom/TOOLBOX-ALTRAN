import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AuditReport } from '@models/audit-report';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { Audit } from '@models/audit';

@Component({
  selector: 'app-audit-report-update',
  templateUrl: './audit-report-update.component.html',
  styleUrls: ['./audit-report-update.component.css']
})
export class AuditReportUpdateComponent implements OnInit {
 // List of users for audit report  edit
 listOfUsers: any = [];
 // Get auditReport to edit form users list component
 @Input() auditReportToEdit: AuditReport;
// Get audit to edit form audit details component
@Input() auditToEdit: Audit;
 // Event for reload table content after update
 @Output() reloadEvent = new EventEmitter();
  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.getListOfUsers();

  }
/** Get all users **/
getListOfUsers() {
  this.genericService.getGenericList('/users/all').subscribe(data => {
    this.listOfUsers = data;
    let i: number;
    for (i = 0; i < this.listOfUsers.length; i++) {
      this.listOfUsers[i].firstName += (' ' + this.listOfUsers[i].lastName);
    }
  });
}
 /** Update audit report */
 updateAuditReport() {
  console.log(this.auditToEdit);
  this.auditReportToEdit.audit=this.auditToEdit;
  console.log(this.auditReportToEdit);
  this.genericService.updateGeneric('/audit-reports', this.auditReportToEdit.id, this.auditReportToEdit)
    .subscribe(
      data => {
        this.auditReportToEdit = data as AuditReport;
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
}
