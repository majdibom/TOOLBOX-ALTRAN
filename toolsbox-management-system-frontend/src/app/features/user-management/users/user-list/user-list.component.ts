import { Component, OnInit } from '@angular/core';
import { User } from '@models/user';
import { PageClient } from '@models/page-client';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  // List of users
  listRecruiters: any = [];

  // User to edit
  editUser: User = new User();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.reloadData();
  }


  /** Reload data after every action */
  reloadData() {
    this.listRecruiters = this.getRecruiters();
  }

  /** Get all Users*/
  getRecruiters() {
    this.genericService.getGenericPage('/users', this.selectedPage, this.item).subscribe(data => {
      this.pageClient = data;
      this.total = this.pageClient.totalElements;
      this.listRecruiters = data.content;
    });
  }

  /** Change state for user's account */
  changeStateUserAccount(id: number) {
    this.genericService.updateStateUserAccount('/users', id, '/account', null).subscribe(data => {
      if (data.error === false) {
        Swal({
          position: 'top-end',
          type: 'success',
          title: data.value,
          showConfirmButton: false,
          timer: 1500
        });
        this.reloadData();
      } else {
        Swal({
          title: 'Erreur!',
          text: data.value,
          type: 'error',
          confirmButtonText: 'ok'
        });
      }
    });
  }

  /** Send User object to edit modal */
  openEditModal(user: any) {
    this.genericService.getGenericById('/users', user.id).subscribe(data => {
      this.editUser = data.value;
    });
  }

  /** Delete User */
  deleteRecruiter(id: number, firstName, lastName) {
    Swal({
      title: 'Vous êtes Sur ?',
      text: 'voulez vous vraiment supprimer le compte de ' + firstName + ' ' + lastName,
      type: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Oui',
      cancelButtonText: 'Non'
    }).then(result => {
      if (result.dismiss !== Swal.DismissReason.cancel && result.dismiss !== Swal.DismissReason.backdrop) {
        return this.genericService.deleteGeneric('/users', id).subscribe(data => {
          if (data.error === false) {
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
              confirmButtonText: 'ok'
            });
          }
        });
      }
    });
  }

  /** Open new mail message with user's mail adresse as destination **/
  mailTo(adresse: any) {
    const mail = document.createElement('a');
    mail.href = 'mailto:' + adresse;
    mail.click();
  }

  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getRecruiters();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search User */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/users/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listRecruiters = this.pageClient.content;
            }
          });
    } else {
      this.getRecruiters();
    }
  }

}
