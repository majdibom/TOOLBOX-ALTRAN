import { Component, OnInit } from '@angular/core';
import swal from 'sweetalert2';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import { Risk } from '@models/risk';

@Component({
  selector: 'app-risk-list',
  templateUrl: './risk-list.component.html',
  styleUrls: ['./risk-list.component.css']
})
export class RiskListComponent implements OnInit {

  // List of Risks
  listRisks: any;

  // Risk to edit
  editRisk: Risk = new Risk();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listRisks = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listRisks = this.getRisks();
  }

  /** Get all risks */
  getRisks() {
    this.genericService.getGenericPage('/risks', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listRisks = this.pageClient.content;
        });
  }

  /** Open risk detail component */
  openDetails(id: number) {
    this.router.navigate(['quality-management/risks/risk-detail', id]);
  }

  /** Send Risk object to edit modal */
  openEditModal(risk: any) {
    this.genericService.getGenericById('/risks', risk.id).subscribe(data => {
      if (data.error === false) {
        this.editRisk = data.value;
      }
    });
  }

  /** Delete Risk */
  deleteRisk(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer ce risque',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/risks', id)
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

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getRisks();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Risk */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/risks/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listRisks = this.pageClient.content;
            }
          });
    } else {
      this.getRisks();
    }
  }
}
