import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { AuditReport } from '@models/audit-report';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { Audit } from '@models/audit';
import { Gap } from '@models/gap';
import { TypeGap, TypeGapLabel } from '@models/type-gap';

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
  // gap to create in report form
  gapToAdd: Gap = new Gap();
  // Initial audit id
  idAudit: number;
  // Type Gap enum Label
 public TypeGapLabel = TypeGapLabel;
 public TypeGapEnum = Object.values(TypeGap);

 listOfTypeGap: string[];
 TypeGapValue: TypeGap;
  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getListOfUsers();
    this.listOfTypeGap = Object.keys(TypeGap);
    this.listOfTypeGap = this.listOfTypeGap.slice(this.listOfTypeGap.length / 5);
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
    this.auditReportToAdd.audit = this.auditToEdit;
    this.gapToAdd.auditReport = this.auditToEdit.auditReport;
    console.log(this.auditReportToAdd);

    this.genericService.createGeneric('/audit-reports', this.auditReportToAdd)
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

  /**Add gap */
  addGap(description: any, justification: any, improvementClue: any, identifiedCauses: any,typeGap: any
  ) {
    const gap = {
      description: description,
      justification: justification,
      improvementClue: improvementClue,
      identifiedCauses: identifiedCauses,
      typeGap:typeGap
    };

    this.gapToAdd.description = description;
    this.gapToAdd.justification = justification;
    this.gapToAdd.improvementClue = improvementClue;
    this.gapToAdd.identifiedCauses = identifiedCauses;
    this.gapToAdd.typeGap = typeGap;
    this.auditReportToAdd.gaps.push(gap);
    console.log(this.gapToAdd)
    this.gapToAdd=new Gap();
  }
  /**Delete action */
  deleteGap(gap: any) {
    this.auditReportToAdd.gaps.forEach((element, index) => {
      if (gap.justification === element.justification) {
        this.auditReportToAdd.gaps.splice(index, 1);
      }
    });
  }
}
