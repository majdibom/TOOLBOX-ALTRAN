import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { AuditReport } from '@models/audit-report';
import swal from 'sweetalert2';
import { Gap } from '@models/gap';

@Component({
  selector: 'app-audit-report-detail',
  templateUrl: './audit-report-detail.component.html',
  styleUrls: ['./audit-report-detail.component.css']
})
export class AuditReportDetailComponent implements OnInit {
// Initial action id
idAuditReport: number;

// Initial action detail
auditReport: AuditReport = new AuditReport();

 // Gap to edit
 editGap: Gap = new Gap();

  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getAuditReport();
    this.reloadData();
  }
/** Get action detail */
getAuditReport() {
  this.idAuditReport = this.route.snapshot.params.id;
  this.genericService.getGenericById('/audit-reports', this.idAuditReport).subscribe(data => {
    this.auditReport = data.value;
    console.log(this.auditReport);
    
  });
}
 /** Reload data after every action */
 reloadData() {
  this.getAuditReport();
 }
 /** Send gap object to edit modal */
 openEditModal(gap: any) {
  this.genericService.getGenericById('/gaps', gap.id).subscribe(data => {
    if (data.error === false) {
      this.editGap = data.value;
    }
  });
}
 /** Delete audit report */
 deleteGap(id: number) {
  swal({
    title: 'Vous êtes Sur ?',
    text: 'Voulez vous vraiment supprimer cet ecart',
    type: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Oui',
    cancelButtonText: 'Non'
  }).then((result) => {
    if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
      this.genericService.deleteGeneric('/gaps', id)
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
