import { Component, OnInit, Output } from '@angular/core';
import { Week } from '@models/week';
import { Process } from '@models/process';
import { Audit } from '@models/audit';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';
import { GenericService } from '@services/generic.service';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';

import { EventEmitter } from 'events';
import { AuditService } from '@models/audit-Service';
import { Project } from '@models/project';
import { PageClient } from '@models/page-client';

@Component({
  selector: 'app-audit-planning',
  templateUrl: './audit-planning.component.html',
  styleUrls: ['./audit-planning.component.css']
})
export class AuditPlanningComponent implements OnInit {

  somme: number;
  weeks: Week[] = [];
  processList: Process[];
  titles: string[] = [];
  // List of projects
  listOfProjects: Project[] = [];
  popverDisabled: boolean;

  // List of audits
  listAudits: any;
  constructor(private config: NgbPopoverConfig, private genericService: GenericService, private router: Router) {
    config.placement = 'right';
    config.triggers = 'hover';
    config.container = 'body';
  }

  ngOnInit() {
    this.getProcess();
    this.getWeeks();
    this.getAudits();

  }
  /** Get all audits */
  getAudits() {
    this.genericService.getGenericList('/audits/all')
      .subscribe(
        data => {
          this.listAudits = data;
          console.log(this.listAudits);
        });

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
  /** Get all projects*/
  getProjects(): void {
    this.genericService.getGenericList('/projects/all').subscribe(data => {
      this.listOfProjects = data;
    });
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
  /** Open audit detail component */
  openDetails(id: number) {
    this.router.navigate(['quality-management/audits/audit-detail', id]);
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
