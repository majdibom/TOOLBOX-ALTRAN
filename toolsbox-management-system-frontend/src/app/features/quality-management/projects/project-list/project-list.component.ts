import { Component, OnInit } from '@angular/core';
import { Project } from '@models/project';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  // List of Projects
  listProjects: any;

  // Project to edit
  editProject: Project = new Project();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';
  constructor(private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.listProjects = [];
    this.reloadData();
    
  }
  /** Reload data after every action */
  reloadData() {
    this.listProjects = this.getProjects();
    
  }

  /** Get all Projects */
  getProjects() {
    this.genericService.getGenericPage('/projects', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listProjects = this.pageClient.content;
        });
  }
  /** Send Project object to edit modal */
  openEditModal(project: any) {
    this.genericService.getGenericById('/projects', project.id).subscribe(data => {
      if (data.error === false) {
        this.editProject = data.value;
      }
    });
  }
  /** Delete Project */
  deleteProject(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer ce projet',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== swal.DismissReason.cancel && result.dismiss !== swal.DismissReason.backdrop) {
        this.genericService.deleteGeneric('/projects', id)
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
      this.getProjects();
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
      this.genericService.searchGeneric('/projects/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listProjects = this.pageClient.content;
            }
          });
    } else {
      this.getProjects();
    }
  }
}
