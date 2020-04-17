import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TypeAction } from '@models/type-action';
import { ActionStatus } from '@models/action-Status';
import { Priority } from '@models/priority';
import { Origin } from '@models/origin';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { Action } from '@models/action';

@Component({
  selector: 'app-gap-actions-update',
  templateUrl: './gap-actions-update.component.html',
  styleUrls: ['./gap-actions-update.component.css']
})
export class GapActionsUpdateComponent implements OnInit {
 // List of users for Action edit
 listOfUsers: any = [];
 // Get Action to edit form users list component
 @Input() actionToEdit: Action;

 // Event for reload table content after update
 @Output() reloadEvent = new EventEmitter();

 // List of action's types for action update
 listOfTypes: string[];
 typeValue: TypeAction;
 listOfActionStatus: string[];
 ActionStatusValue: ActionStatus;
 listOfPriority: string[];
 PriorityValue: Priority;
 listOfOrigin: string[];
 originValue: Origin;
 constructor(private genericService: GenericService) { }

 ngOnInit() {
   this.getListOfUsers();
   // Enum setting
   this.listOfTypes = Object.keys(TypeAction);
   this.listOfTypes = this.listOfTypes.slice(this.listOfTypes.length / 5);
   this.listOfActionStatus = Object.keys(ActionStatus);
   this.listOfActionStatus = this.listOfActionStatus.slice(this.listOfActionStatus.length / 7);
   this.listOfPriority = Object.keys(Priority);
   this.listOfPriority = this.listOfPriority.slice(this.listOfPriority.length / 5);
   this.listOfOrigin = Object.keys(Origin);
   this.listOfOrigin = this.listOfOrigin.slice(this.listOfOrigin.length / 5);
 }
/** Get all users **/
getListOfUsers() {
 this.genericService.getGenericList('/users/all').subscribe(data => {
   this.listOfUsers = data;
 });
}
 /** Enum selected value parsing */
 parseValue(value: string) {
   this.typeValue = TypeAction[value];
   this.ActionStatusValue = ActionStatus[value];
   this.PriorityValue = Priority[value];
   this.originValue = Origin[value];

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
