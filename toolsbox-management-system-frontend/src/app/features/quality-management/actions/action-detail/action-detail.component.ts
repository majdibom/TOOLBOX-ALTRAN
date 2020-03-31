import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { ActivatedRoute } from '@angular/router';
import { Action } from '@models/action';
import { Comment } from '@models/comment';
import swal from 'sweetalert2';

@Component({
  selector: 'app-action-detail',
  templateUrl: './action-detail.component.html',
  styleUrls: ['./action-detail.component.css']
})
export class ActionDetailComponent implements OnInit {

  // Initial action id
  idAction: number;

  // Initial action detail
  action: Action = new Action();

  // Comment to add
  commentToAdd: Comment = new Comment();

  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getAction();
  }

  /** Get action detail */
  getAction() {
    this.idAction = this.route.snapshot.params.id;
    this.genericService.getGenericById('/actions', this.idAction).subscribe(data => {
      this.action = data.value;
    });
  }

  /**Add action */
  addComment(message: any) {
    const comment = {

      message: message
    };
    this.commentToAdd = new Comment();

  }
  /** Add coment to the action */
  createComment() {
    this.genericService.updateGeneric('/actions/comments', this.idAction, this.commentToAdd).subscribe(
      data => {
        if (data.error === false) {
          swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          });
          // Reload action object to display the new comment
          this.getAction();
        } else {
          swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'ok'
          });
        }
      });
  }
  /** Empty add form fields */
  emptyObject() {
    this.commentToAdd = new Comment();
  }
}
