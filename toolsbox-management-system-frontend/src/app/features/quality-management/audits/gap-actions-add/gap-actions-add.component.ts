import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Gap } from '@models/gap';
import { Action } from '@models/action';
import { ActivatedRoute } from '@angular/router';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { OriginLabel, Origin } from '@models/origin';
import { TypeActionLabel, TypeAction } from '@models/type-action';
import { PriorityLabel, Priority } from '@models/priority';
import { ActionstatusLabel, ActionStatus } from '@models/action-Status';
@Component({
  selector: 'app-gap-actions-add',
  templateUrl: './gap-actions-add.component.html',
  styleUrls: ['./gap-actions-add.component.css']
})
export class GapActionsAddComponent implements OnInit {
  // Get audit to edit form audit details component
  @Input() gapToEdit: Gap;
  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();
  // action to create 
  actionToAdd: Action = new Action();
  // Initial gap id
  idGap: number;
  // Initial gap detail
  gap: Gap = new Gap();
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
  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getListOfUsers();
  }

  /** Get all users **/
  getListOfUsers() {
    this.genericService.getGenericList('/users/all').subscribe(data => {
      this.listOfUsers = data;
    });
  }
  /** Create AuditReport */
  createAction() {
    console.log(this.gapToEdit);
    this.actionToAdd.gap = this.gapToEdit;
    console.log(this.actionToAdd);
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
  /** Empty add form fields */
  emptyObject() {
    this.actionToAdd = new Action();
  }
}
