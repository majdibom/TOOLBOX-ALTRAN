import { Component, OnInit } from '@angular/core';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';
import { Audit } from '@models/audit';
import { Process } from '@models/process';
import { Project } from '@models/project';

@Component({
  selector: 'app-audit-list',
  templateUrl: './audit-list.component.html',
  styleUrls: ['./audit-list.component.css']
})
export class AuditListComponent implements OnInit {

  // List of audits
  listAudits: any;
  listOfProjects: Project[] = [];
  listOfProcess: Process[] = [];
  // audit to edit
  editAudit: Audit = new Audit();
  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';
  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listAudits = [];
    this.reloadData();
  }
  /** Reload data after every action */
  reloadData() {
    this.listAudits = this.getAudits();
  }
  /** Get all audits */
  getAudits() {
    this.genericService.getGenericPage('/audits', this.selectedPage, this.item)
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
  getProcess(): void {
    this.genericService.getGenericList('/process/all').subscribe(
      data => {
        this.listOfProcess = data;
      }
    );
  }
  /** Get all projects*/
  getProjects(): void {
    this.genericService.getGenericList('/projects/all').subscribe(data => {
      this.listOfProjects = data;
    });
  }
  /** Send audit object to edit modal */
  openEditModal(audit: any) {
    this.genericService.getGenericById('/audits', audit.id).subscribe(data => {
      if (data.error === false) {
        this.editAudit = data.value;
      }
    });
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
      this.getAudits();
    }
  }
  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }
  /** Search Action */
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
      this.getAudits();
    }
  }
}
