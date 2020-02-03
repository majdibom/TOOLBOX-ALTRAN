import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Activity } from '@models/activity';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-activity-add',
  templateUrl: './activity-add.component.html',
  styleUrls: ['./activity-add.component.css']
})
export class ActivityAddComponent implements OnInit {

  // Declare object for Activity create
  activityToAdd: Activity = new Activity();

  // Event for table data
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }


  /** Create Activity */
  createActivity() {
    this.genericService.createGeneric('/activities', this.activityToAdd)
      .subscribe(data => {
        if (data.error === false) {
          Swal({
            position: 'top-end',
            type: 'success',
            title: data.value,
            showConfirmButton: false,
            timer: 1500
          }),
            // reload table data
            this.reloadEvent.emit(null);
          // emty add form fields
          this.emptyObject();
        } else {
          Swal({
            title: 'Erreur!',
            text: data.value,
            type: 'error',
            confirmButtonText: 'ok'
          });
          this.emptyObject();
        }
      });
  }

  /** Empty add form fields */
  emptyObject() {
    this.activityToAdd = new Activity();
  }

}
