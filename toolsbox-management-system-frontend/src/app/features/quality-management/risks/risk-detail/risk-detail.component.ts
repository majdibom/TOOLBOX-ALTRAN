import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Risk } from '@models/risk';
import { ActivatedRoute } from '@angular/router';
import { PageClient } from '@models/page-client';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Chart } from 'chart.js';
import { ChartModule } from 'primeng/chart';

@Component({
  selector: 'app-risk-detail',
  templateUrl: './risk-detail.component.html',
  styleUrls: ['./risk-detail.component.css']
})
export class RiskDetailComponent implements OnInit {
  // Initial risk id
  idRisk: number;
  // Initial risk detail
  risk: Risk = new Risk();
  // Risk to edit
  editRisk: Risk = new Risk();
  //Array of the chart
  ids: Array<number> = [];
  name: Array<String> = [];
  data: any;
  constructor(private route: ActivatedRoute, private genericService: GenericService, private router: Router) { }
  ngOnInit() {
    this.reloadData();
  }

  /** Reload data after every action */
  reloadData() {
    this.getRisk();
  }


  /** Get Risk detail */
  getRisk() {
    this.idRisk = this.route.snapshot.params.id;
    this.genericService.getGenericById('/risks', this.idRisk).subscribe(data => {
      this.risk = data.value;
      this.createChart();
    });
  }

  /** Open action detail component */
  openDetails(id: number) {
    this.router.navigate(['quality-management/actions/action-detail', id]);
  }
  /** Create chart method */
  createChart() {
    for (let a of this.risk.exposures) {
      this.ids.push(a.value);
      this.name.push(a.createdAt);
    }
    this.data = {
      labels: this.name,
      datasets: [
        {
          label: 'Criticité',
          data: this.ids,
          fill: false,
          borderColor: '#007EAF'
        }
      ]
    }
  }


  /** Open risk detail component */
  openActionsListModal(idRisk: number) {
    this.genericService.getGenericById('/risks', idRisk).subscribe(data => {
      if (data.error === false) {
        this.editRisk = data.value;
      }
    });
  }

  /** Delete Action */
  deleteActionsFromRisk(body: any) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette action',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteWithBody('/risks/delete-action', { body: body })
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
              // Pagination control while deleting an object in a page who contain one element
              if (this.total % this.item === 1) {
                this.selectedPage = this.selectedPage - 1;
              }
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
  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';


}
