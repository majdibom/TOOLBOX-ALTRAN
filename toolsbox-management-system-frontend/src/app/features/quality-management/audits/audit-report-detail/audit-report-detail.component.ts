import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { AuditReport } from '@models/audit-report';

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

  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getAuditReport()
  }
/** Get action detail */
getAuditReport() {
  this.idAuditReport = this.route.snapshot.params.id;
  this.genericService.getGenericById('/audit-reports', this.idAuditReport).subscribe(data => {
    this.auditReport = data.value;
  });
}

}
