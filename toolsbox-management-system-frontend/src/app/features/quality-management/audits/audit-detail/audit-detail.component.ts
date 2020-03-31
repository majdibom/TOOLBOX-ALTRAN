import { Component, OnInit } from '@angular/core';
import { Audit } from '@models/audit';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { AuditReport } from '@models/audit-report';

@Component({
  selector: 'app-audit-detail',
  templateUrl: './audit-detail.component.html',
  styleUrls: ['./audit-detail.component.css']
})
export class AuditDetailComponent implements OnInit {
  // Initial audit id
  idAudit: number;
 // auditReport to edit
 auditReportEdit: AuditReport = new AuditReport();
  // Initial audit detail
  audit: Audit = new Audit();
  constructor(private route: ActivatedRoute, private genericService: GenericService,private router: Router) { }

  ngOnInit() {
    this.getAudit();
    this.reloadData();
  }
  /** Get audit detail */
  getAudit() {
    this.idAudit = this.route.snapshot.params.id;
    this.genericService.getGenericById('/audits', this.idAudit).subscribe(data => {
      this.audit = data.value;
    });
  }
 /** Reload data after every action */
 reloadData() {
 this.getAudit();
}
 /** Send audit report object to edit modal */
 openEditModal(auditReport: any) {
  this.genericService.getGenericById('/audit-reports', auditReport.id).subscribe(data => {
    if (data.error === false) {
      this.auditReportEdit = data.value;
    }
  });
}
/** Open audit report detail component */
openDetails(id: number) {
  this.router.navigate(['quality-management/audits/audit-report-detail', id]);
}
  /** Delete audit report */
  deleteAuditReport(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer ce rapport',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/audit-reports', id)
          .subscribe(data => {
            if (data.error === false) {
              this.ngOnInit();
              swal({
                position: 'top-end',
                type: 'success',
                title: data.value,
                showConfirmButton: false,
                timer: 1500
              });
              this.reloadData();
            } else {
              swal({
                title: 'Erreur!',
                text: data.value,
                type: 'error',
                confirmButtonText: 'Ok'
              });
            }
          });
      }
    });
  }
}
