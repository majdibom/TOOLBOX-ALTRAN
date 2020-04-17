import { Component, OnInit } from '@angular/core';
import { Gap } from '@models/gap';
import { GenericService } from '@services/generic.service';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { Action } from '@models/action';
import { PageClient } from '@models/page-client';

@Component({
  selector: 'app-gap-detail',
  templateUrl: './gap-detail.component.html',
  styleUrls: ['./gap-detail.component.css']
})
export class GapDetailComponent implements OnInit {
  // Initial gap id
  idGap: number;
  // Action to edit
  editAction: Action = new Action();
  // Initial action detail
  gap: Gap = new Gap();
  // Gap to edit
  editGap: Gap = new Gap();
  constructor(private route: ActivatedRoute, private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.getGap();
    this.reloadData();
  }
  /** Reload data after every action */
  reloadData() {
    this.getGap();
  }
  /** Get action detail */
  getGap() {
    this.idGap = this.route.snapshot.params.id;
    this.genericService.getGenericById('/gaps', this.idGap).subscribe(data => {
      this.gap = data.value;
      console.log(this.gap);

    });
  }
  /** Open action detail component */
  openDetails(id: number) {
    this.router.navigate(['quality-management/actions/action-detail', id]);
  }
  /** Open risk detail component */
  openActionsListModal(idGap: number) {
    this.genericService.getGenericById('/gaps', idGap).subscribe(data => {
      if (data.error === false) {
        this.editGap = data.value;
      }
    });
  }
  /** Send Action object to edit modal */
  openEditModal(action: any) {
    this.genericService.getGenericById('/actions', action.id).subscribe(data => {
      if (data.error === false) {
        this.editAction = data.value;
      }
    });
  }

  /** Delete action */
  deleteAction(id: number) {
    swal({
      title: 'Vous êtes Sur ?',
      text: 'Voulez vous vraiment supprimer cet action',
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

}
