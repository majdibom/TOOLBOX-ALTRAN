import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Project } from '@models/project';
import { Process } from '@models/process';
import { PageClient } from '@models/page-client';
import { User } from '@models/user';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '@services/authentication.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-audited-space',
  templateUrl: './audited-space.component.html',
  styleUrls: ['./audited-space.component.css']
})
export class AuditedSpaceComponent implements OnInit {
// List of audits
listAudits: any;
listOfProjects: Project[] = [];
listOfProcess: Process[] = [];
// Pagination params
pageClient: PageClient = new PageClient();
total: number;
selectedPage = 0;
item = 5;
searchInput: String = '';
username: any;
userInformation: User = new User();
firstName: any;
lastName: any;
// Event for reload table content
@Output() reloadEvent = new EventEmitter();
  constructor(private genericService: GenericService, private router: Router, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.getUsername();
    this.listAudits = [];
    this.reloadData();
    this.getUserConnectedInfo();
  }
  getUserConnectedInfo() {
    this.genericService.getUserByUsername('/users/username', this.username).subscribe(data => {
      this.userInformation = data.value;
      this.firstName = this.userInformation.firstName;
      this.lastName = this.userInformation.lastName;
    });
  }
  getUsername() {
    this.username = this.authenticationService.getUsername();
  }
  /** Reload data after every action */
  reloadData() {
    this.listAudits = this.getAuditsByAudited();
  }
  /** Get all audits */
  getAuditsByAudited() {
    this.genericService.getGenericByAuthPage('/audits/audited', this.username, this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listAudits = this.pageClient.content;
          console.log(this.listAudits);
          
        });
  }
   /** Open audit detail component */
   openDetails(id: number) {
    this.router.navigate(['quality-management/audits/audit-detail', id]);
  }
  /** Delete audit */
  deleteAudits(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cette audit',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/audits', id)
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
      this.getAuditsByAudited();
    }
  }
  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }
  /** Search audit */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/audits/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listAudits = this.pageClient.content;
            }
          });
    } else {
      this.getAuditsByAudited();
    }
  }
}
