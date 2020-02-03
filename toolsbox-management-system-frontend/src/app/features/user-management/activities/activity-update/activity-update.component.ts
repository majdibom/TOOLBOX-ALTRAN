import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Activity } from '@models/activity';
import { GenericService } from '@services/generic.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-activity-update',
  templateUrl: './activity-update.component.html',
  styleUrls: ['./activity-update.component.css']
})
export class ActivityUpdateComponent implements OnInit {

  // Get Activity to edit form actvities list component
  @Input() activityToEdit: Activity;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  constructor(private genericService: GenericService) { }

  ngOnInit() {
  }


  /** Update Activity */
  updateActivity() {
    this.genericService.updateGeneric('/activities', this.activityToEdit.id, this.activityToEdit)
      .subscribe(
        data => {
          this.activityToEdit = data as Activity;
          if (data.error === false) {
            Swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            });
            // reload table data
            this.reloadEvent.emit(null);
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

}
