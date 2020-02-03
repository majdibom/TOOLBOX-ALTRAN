import { Component, OnInit, Output } from '@angular/core';
import { Week } from '@models/week';
import { Process } from '@models/process';
import { Audit } from '@models/audit';
import {NgbPopoverConfig} from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from '@services/generic.service';
import {ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';

import { EventEmitter } from 'events';

@Component({
  selector: 'app-audit-planning',
  templateUrl: './audit-planning.component.html',
  styleUrls: ['./audit-planning.component.css']
})
export class AuditPlanningComponent implements OnInit {

  somme: number;
  weeks: Week[] = [];
  processList: Process [];
  titles: string [] = [];
  popverDisabled: boolean;

  // @Output() submitted = new EventEmitter<boolean>();

  constructor(private config: NgbPopoverConfig, private genericService: GenericService,
              private route: ActivatedRoute,
              private router: Router) {
    config.placement = 'right';
    config.triggers = 'hover';
    config.container = 'body';
  }

  ngOnInit() {
    this.getProcess();
    this.getWeeks();

  }

  getWeeks(): void {
    this.genericService.getGenericList('/weeks/all').subscribe(
      data => {
        this.weeks = data;
      }
    );
  }

  getProcess(): void {
    this.genericService.getGenericList('/process/all').subscribe(
      data => {
        this.processList = data;
      }
    );
  }

  calculate(idProcess: number, idWeek: number): number {
    let total: number;
    let week: Week;
    let audit: Audit;
    let process: Process;
    total = 0;
    for (week of this.weeks) {
      if (week.id === idWeek) {
          for (audit of week.audits) {
            for (process of audit.processImpacts) {
              if (process.idProcess === idProcess) {
                total++;
                break;
              }
            }
          }
          this.somme = total;
          return total;
        }
    }
  }

  clickCheck(idProcess: number, idWeek: number): number {
    let total: number;
    let week: Week;
    let audit: Audit;
    let process: Process;
    total = 0;
    for (week of this.weeks) {
      if (week.id === idWeek) {
        for (audit of week.audits) {
          for (process of audit.processImpacts) {
            if (process.idProcess === idProcess) {
              total++;
              break;
            }
          }
        }
        if (!total) {
          swal({
            title: 'Erreur!',
            text: 'No Audit in this cell! Accessing Audit List',
            type: 'error',
            confirmButtonText: 'ok'
          });
        }
        return total;
      }
    }
  }

  load(submitted: boolean) {
    if (submitted) {
      this.getProcess();
      this.getWeeks();

    }
  }

//   loadList(idProcess: number, title: string, idWeek: number, semaineNumber: number) {

//     this.auditService.idProcess = idProcess;
//     this.auditService.title = title;
//     this.auditService.idSemaine = idWeek;
//     this.auditService.semaineNumber = semaineNumber;
//     this.router.navigate(['list'], { relativeTo: this.route } );
// }

  loadTitles(idProcess: number, idWeek: number) {

    let week: Week;
    let audit: Audit;
    let process: Process;
    for (week of this.weeks) {
      if (week.id === idWeek) {
        for (audit of week.audits) {
          for (process of audit.processImpacts) {
            if (process.idProcess === idProcess) {
              const title = (audit.project) ? audit.project.title : audit.process.title;
              this.titles.push(title);
              break;
            }
          }
        }
      }
    }
    this.popverDisabled = (this.titles.length) ? false : true;

}
  resetTitles() {
    this.titles = [];
  }

}
