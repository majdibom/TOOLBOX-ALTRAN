import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Action } from '@models/action';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { TypeAction } from '@models/type-action';

@Component({
  selector: 'app-action-update',
  templateUrl: './action-update.component.html',
  styleUrls: ['./action-update.component.css']
})
export class ActionUpdateComponent implements OnInit {

  // Get Action to edit form users list component
  @Input() actionToEdit: Action;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // List of action's types for action update
  listOfTypes: string[];
  typeValue: TypeAction;

  constructor(private genericService: GenericService) { }

  ngOnInit() {
    // Enum setting
    this.listOfTypes = Object.keys(TypeAction);
    this.listOfTypes = this.listOfTypes.slice(this.listOfTypes.length / 2);
  }

  /** Enum selected value parsing */
  parseValue(value: string) {
    this.typeValue = TypeAction[value];
  }

  /** Update Action */
  updateAction() {
    this.genericService.updateGeneric('/actions', this.actionToEdit.id, this.actionToEdit)
      .subscribe(
        data => {
          this.actionToEdit = data as Action;
          if (data.error === false) {
            swal({
              position: 'top-end',
              type: 'success',
              title: data.value,
              showConfirmButton: false,
              timer: 1500
            });
            // reload table data
            this.reloadEvent.emit(null);
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

}
