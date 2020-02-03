import { Component, OnInit, ViewChild } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Role } from '@models/role';
import { RoleUpdateComponent } from '../role-update/role-update.component';
import Swal from 'sweetalert2';
import { PageClient } from '@models/page-client';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.css']
})
export class RoleListComponent implements OnInit {

  // Call child component method from parent class
  @ViewChild(RoleUpdateComponent) child: RoleUpdateComponent;

  // List of roles
  listRoles: any;

  // Role to edit
  editRole: Role = new Role();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private roleService: GenericService) { }

  ngOnInit() {
    this.listRoles = [];
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listRoles = this.getRoles();
  }

  /** Get all Roles*/
  getRoles() {
    this.roleService.getGenericPage('/roles', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listRoles = data.content;
        },
        error => console.log(error));
  }

  /** Send Role object to edit modal */
  openEditModal(role: any) {
    this.roleService.getGenericById('/roles', role.id).subscribe(data => {
      if (data.error === false) {
        this.editRole = data.value;
        // Pass selected privileges role to edit
        this.child.listPrivileges();
      } else {
        console.log(data.value);
      }
    });

  }

  /** Delete Role */
  deleteRole(id: number) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer ce rôle',
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then((result) => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        this.roleService.deleteGeneric('/roles', id)
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
          }, err => {
            console.log(err);
          });
      }
    });
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pagesNumber: number) {
    this.selectedPage = pagesNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getRoles();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Role */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.roleService.searchGeneric('/roles/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listRoles
                = this.pageClient.content;
            }
          });
    } else {
      this.getRoles();
    }
  }

}
