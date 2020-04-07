import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Gap } from '@models/gap';
import { AuditReport } from '@models/audit-report';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { TypeGapLabel, TypeGap } from '@models/type-gap';

@Component({
  selector: 'app-gap-add',
  templateUrl: './gap-add.component.html',
  styleUrls: ['./gap-add.component.css']
})
export class GapAddComponent implements OnInit {
 // Get audit to edit form audit details component
 @Input() auditReportToEdit: AuditReport;
  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();
  // gap to create 
  gapToAdd: Gap = new Gap();
  // Initial action id
  idAuditReport: number;
  // Initial audit report detail
  auditReport: AuditReport = new AuditReport();
   // Type Gap enum Label
 public TypeGapLabel = TypeGapLabel;
 public TypeGapEnum = Object.values(TypeGap);
  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
  }
 
  /** Create AuditReport */
  createGap() {
    console.log(this.auditReportToEdit);

    this.gapToAdd.auditReport = this.auditReportToEdit;
    console.log(this.gapToAdd);

    this.genericService.createGeneric('/gaps', this.gapToAdd)
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
    this.gapToAdd = new Gap();
  }
}
