import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Action } from '@models/action';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { OriginLabel, Origin } from '@models/origin';
import { TypeActionLabel, TypeAction } from '@models/type-action';
import { PriorityLabel, Priority } from '@models/priority';
import { ActionstatusLabel, ActionStatus } from '@models/action-Status';

@Component({
  selector: 'app-action-add',
  templateUrl: './action-add.component.html',
  styleUrls: ['./action-add.component.css']
})
export class ActionAddComponent implements OnInit {

 // Event for reload table content
 @Output() reloadEvent = new EventEmitter();
 // Action to create 
 actionToAdd: Action = new Action();


 // List of users for action create
 listOfUsers: any = [];
 // Origin enum Label
 public OriginLabel = OriginLabel;
 public OriginEnum = Object.values(Origin);
 // typeAction enum Label
 public typeActionLabel = TypeActionLabel;
 public typeActionEnum = Object.values(TypeAction);
 
 // Priority enum Label
 public priorityLabel = PriorityLabel;
 public priorityEnum = Object.values(Priority);

  // Action Status enum Label
  public ActionstatusLabel = ActionstatusLabel;
  public ActionStatusEnum = Object.values(ActionStatus);

 constructor(private genericService: GenericService) { }

 ngOnInit() {
   this.getListOfUsers();
 }

 /** Get all users **/
 getListOfUsers() {
   this.genericService.getGenericList('/users/all').subscribe(data => {
     this.listOfUsers = data;
   });
 }

 /** Create action */
 createAction() {
   console.log("add object:   " + this.actionToAdd);

   this.genericService.createGeneric('/actions', this.actionToAdd)
     .subscribe(data => {
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
         // empty add form fields
         this.emptyObject();
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

 /**Add action */
 addAction(description: any, effMeasCriterion: any, openDate: any, dueDate: any,
   updatedDueDate: any, effMeasDate: any, realizationDate: any,  responsibleAction: any, ActionStatus: any, priority: any, typeAction: any, origin: any, comments: any) {
   const action = {
     description: description,
     effMeasCriterion: effMeasCriterion,
     openDate: openDate,
     dueDate: dueDate,
     updatedDueDate: updatedDueDate,
     effMeasDate: effMeasDate,
     realizationDate: realizationDate,
     actionStatus: ActionStatus,
     priority: priority,
     responsibleAction: responsibleAction,
     typeAction: typeAction,
     origin: origin,
     comments: comments
   };
   this.actionToAdd = new Action();

 }


 /** Empty add form fields */
 emptyObject() {
   this.actionToAdd = new Action();
 }

}
