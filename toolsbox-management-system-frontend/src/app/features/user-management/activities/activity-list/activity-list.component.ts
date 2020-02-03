import { Component, OnInit } from '@angular/core';
import { PageClient } from '@models/page-client';
import { Activity } from '@models/activity';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-activity-list',
  templateUrl: './activity-list.component.html',
  styleUrls: ['./activity-list.component.css']
})
export class ActivityListComponent implements OnInit {

  // List of activities
  listActivities: any;

  // Activity to edit
  editActivity: Activity = new Activity();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listActivities = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listActivities = this.getActivities();
  }

  /** Get all Activities */
  getActivities() {
    this.genericService.getGenericPage('/activities', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listActivities = this.pageClient.content;
        });
  }

  /** Send Activity object to edit modal */
  openEditModal(activity: any) {
    this.genericService.getGenericById('/activities', activity.id).subscribe(data => {
      if (data.error === false) {
        this.editActivity = data.value;
      }
    });
  }

  /** Delete Activity */
  deleteActivities(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette activité',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/activities', id)
          .subscribe(data => {
            if (data.error === false) {
              this.ngOnInit();
              Swal({
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
              Swal({
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
      this.getActivities();
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
      this.genericService.searchGeneric('/activities/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listActivities = this.pageClient.content;
            }
          });
    } else {
      this.getActivities();
    }
  }

}
