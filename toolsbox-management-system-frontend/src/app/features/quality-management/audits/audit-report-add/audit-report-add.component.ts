import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { AuditReport } from '@models/audit-report';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { Audit } from '@models/audit';

@Component({
  selector: 'app-audit-report-add',
  templateUrl: './audit-report-add.component.html',
  styleUrls: ['./audit-report-add.component.css']
})
export class AuditReportAddComponent implements OnInit {
  // Get audit to edit form audit details component
@Input() auditToEdit: Audit;
// Event for reload table content
@Output() reloadEvent = new EventEmitter();
// auditreport to create 
auditReportToAdd: AuditReport = new AuditReport();
// List of users for report create
listOfUsers: any = [];
// Initial audit detail
audit: Audit = new Audit();
 // Initial audit id
 idAudit: number;
  constructor(private route: ActivatedRoute,private genericService: GenericService) { }

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
 /** Get audit detail */
 getAudit() {
  this.idAudit = this.route.snapshot.params.id;
  this.genericService.getGenericById('/audits', this.idAudit).subscribe(data => {
    this.audit = data.value;
    
    
  });
}
/** Create AuditReport */
createAuditReport() {
  console.log(this.auditToEdit);
this.auditReportToAdd.audit=this.auditToEdit;
console.log(this.auditReportToAdd);

  this.genericService.createGeneric('/audit-reports', this.auditReportToAdd,)
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
          confirmButtonText: 'ok'
        });
      }
    });
}
/** Empty add form fields */
emptyObject() {
  this.auditReportToAdd = new AuditReport();
}
}
