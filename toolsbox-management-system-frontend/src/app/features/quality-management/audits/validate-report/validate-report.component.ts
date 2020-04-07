import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-validate-report',
  templateUrl: './validate-report.component.html',
  styleUrls: ['./validate-report.component.css']
})
export class ValidateReportComponent implements OnInit {
// Event for reload table content
@Output() reloadEvent = new EventEmitter();
idAuditReport: number;
validator: string;
  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }
  validate(idAuditReport: number, validator: string) {
    this.idAuditReport = idAuditReport;
    this.validator = validator;
  }
   /** Reload data after every action */
  submit(validation: string) {
    this.genericService.validateReport('/audit-reports/validate', this.idAuditReport,this.validator,validation).subscribe(
      data => {
        if (data === true) {
          swal({
            position: 'top-end',
            type: 'success',
            title: "Report Validated",
            text:"Success",
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
      }
    );
  }
}
