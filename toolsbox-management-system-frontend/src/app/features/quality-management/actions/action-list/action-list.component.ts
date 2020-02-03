import { Component, OnInit } from '@angular/core';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { Action } from '@models/action';
import { Router } from '@angular/router';

@Component({
  selector: 'app-action-list',
  templateUrl: './action-list.component.html',
  styleUrls: ['./action-list.component.css']
})
export class ActionListComponent implements OnInit {

  // List of actions
  listActions: any;

  // Action to edit
  editAction: Action = new Action();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listActions = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listActions = this.getActions();
  }

  /** Get all actions */
  getActions() {
    this.genericService.getGenericPage('/actions', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listActions = this.pageClient.content;
        });
  }

  /** Open action detail component */
  openDetails(id: number) {
    this.router.navigate(['quality-management/actions/action-detail', id]);
  }

  /** Send Action object to edit modal */
  openEditModal(action: any) {
    this.genericService.getGenericById('/actions', action.id).subscribe(data => {
      if (data.error === false) {
        this.editAction = data.value;
      }
    });
  }

  /** Delete Action */
  deleteActions(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette action',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/actions', id)
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
      this.getActions();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Activity */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/actions/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listActions = this.pageClient.content;
            }
          });
    } else {
      this.getActions();
    }
  }
}
