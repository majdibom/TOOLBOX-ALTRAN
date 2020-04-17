import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // List of users
  listRecruiters: any;
  listActivities: any;
  listAudits: any;
  listProjects: any;
  listActions: any;
  listRisks: any;
  listGaps: any;
  totalGaps: number;
  listAuditReports: any;
  totalAuditReports: number;
  totalRisks: number;
  totalActions: number;
  totalUsers: number;
  totalActivities: number;
  totalAudits: number;
  totalProjects: number;

  ids: Array<number> = [];
  name: Array<number> = [];
  dates: Array<String> = [];
  data: any;
  dataline: any;
  now: String;
  previous: String;
  beforePrevious: String;
  currentDate = new Date();
  previousDate = new Date();
  beforPreviousDate = new Date();
  pipe = new DatePipe('en-US');
  constructor(private genericService: GenericService, private router: Router) {
  }

  ngOnInit() {
    this.getRecruiters();
    this.getActivities();
    this.getAudits();
    this.getProjects();
    this.getActions();
    this.getRisks();
    this.getGaps();
    this.getAuditReports();
    this.previousDate.setDate(this.previousDate.getDate() - 1);
    this.beforPreviousDate.setDate(this.beforPreviousDate.getDate() - 2);
    this.now = this.pipe.transform(this.currentDate, 'dd/MM/yyyy');
    this.previous = this.pipe.transform(this.previousDate, 'dd/MM/yyyy');
    this.beforePrevious = this.pipe.transform(this.beforPreviousDate, 'dd/MM/yyyy');
    this.dates.push(this.beforePrevious);
    this.dates.push(this.previous);
    this.dates.push(this.now);


  }

  /** Get all Users*/
  getRecruiters() {
    this.genericService.getGenericList('/users/all').subscribe(data => {
      this.listRecruiters = data;
      this.totalUsers = this.listRecruiters.length;
    });
  }
  /** Open users component */
  openUsers() {
    this.router.navigate(['user-management/users']);
  }
  /** Get all activities*/
  getActivities() {
    this.genericService.getGenericList('/activities/all').subscribe(data => {
      this.listActivities = data;
      this.totalActivities = this.listActivities.length;
      this.createChart();
    });
  }
  /** Open users component */
  openActivities() {
    this.router.navigate(['user-management/activities']);
  }
  /** Get all audits*/
  getAudits() {
    this.genericService.getGenericList('/audits/all').subscribe(data => {
      this.listAudits = data;
      this.totalAudits = this.listAudits.length;
      this.createChart();
    });
  }
  /** Open users component */
  openAudits() {
    this.router.navigate(['quality-management/audits/all']);
  }
  /** Get all projects*/
  getProjects(): void {
    this.genericService.getGenericList('/projects/all').subscribe(data => {
      this.listProjects = data;
      this.totalProjects = this.listProjects.length;
      this.createChart();
    });
  }
  /** Open project component */
  openProjects() {
    this.router.navigate(['quality-management/projects']);
  }
  /** Get all actions*/
  getActions(): void {
    this.genericService.getGenericList('/actions/all').subscribe(data => {
      this.listActions = data;
      this.totalActions = this.listActions.length;
      this.createChart();
    });
  }
  /** Open actions component */
  openActions() {
    this.router.navigate(['quality-management/actions']);
  }
  /** Open risks component */
  openRisks() {
    this.router.navigate(['quality-management/risks']);
  }
  /** Get all risks*/
  getRisks(): void {
    this.genericService.getGenericList('/risks/all').subscribe(data => {
      this.listRisks = data;
      this.totalRisks = this.listRisks.length;
      this.createChart();
    });
  }
  /** Get all risks*/
  getGaps(): void {
    this.genericService.getGenericList('/gaps/all').subscribe(data => {
      this.listGaps = data;
      this.totalGaps = this.listGaps.length;
      this.createChart();

    });
  }
  getAuditReports(): void {
    this.genericService.getGenericList('/audit-reports/all').subscribe(data => {
      this.listAuditReports = data;
      this.totalAuditReports = this.listAuditReports.length;
      console.log(this.listAuditReports);
      console.log(this.totalAuditReports);
      this.createChart();

    });
  }
  /** Create chart method */
  createChart() {
    this.data = {
      labels: ['Risques', 'Actions', 'Ecarts', 'Rapports'],
      datasets: [
        {
          data: [this.totalRisks
            , this.totalActions, this.totalGaps, this.totalAuditReports],
          backgroundColor: [
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
            "#36A2EB"
          ],
          hoverBackgroundColor: [
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
            "#36A2EB"
          ]
        }
      ]
    }
    for (let a of this.listActions) {
      this.ids.push(a.id);
      this.name.push(a.openDate);
    }
    this.dataline = {
      labels: this.dates,
      datasets: [
        {
          label: 'First Dataset',
          data: [this.totalActions,this.totalActions,this.totalActions],
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
        }
      ]
    }
  }

}
