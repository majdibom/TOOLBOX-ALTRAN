import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Risk } from '@models/risk';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { SeverityLabel, Severity } from '@models/severity';
import { ProbabilityLabel, Probability } from '@models/probability';
import { RiskStatusLabel, RiskStatus } from '@models/risk-status';
import { Action } from '@models/action';
import { ActionstatusLabel, ActionStatus } from '@models/action-Status';
import { PriorityLabel, Priority } from '@models/priority';
import { TypeActionLabel, TypeAction } from '@models/type-action';
import { OriginLabel, Origin } from '@models/origin';
import { RiskNatureLabel, RiskNature } from '@models/risk-nature';




@Component({
  selector: 'app-risk-add',
  templateUrl: './risk-add.component.html',
  styleUrls: ['./risk-add.component.css']
})
export class RiskAddComponent implements OnInit {

  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();

  // Risk object for create action
  riskToAdd: Risk = new Risk();

  // List of actions for risk create
  listOfUsers: any = [];
  // Origin enum Label
  public OriginLabel = OriginLabel;
  public OriginEnum = Object.values(Origin);
  // typeAction enum Label
  public typeActionLabel = TypeActionLabel;
  public typeActionEnum = Object.values(TypeAction);
  // Probability enum Label
  public probabilityLabel = ProbabilityLabel;
  public probabilityEnum = Object.values(Probability);
  // Priority enum Label
  public priorityLabel = PriorityLabel;
  public priorityEnum = Object.values(Priority);

  // Severity enum Label
  public severityLabel = SeverityLabel;
  public severityEnum = Object.values(Severity);

  // Risk Status enum Label
  public riskStatusLabel = RiskStatusLabel;
  public riskStatusEnum = Object.values(RiskStatus);
 // Risk Nature enum Label
 public riskNatureLabel = RiskNatureLabel;
 public riskNatureEnum = Object.values(RiskNature);
  // Risk Priority enum Label
  public riskPriorityLabel = PriorityLabel;
  public riskPriorityEnum = Object.values(Priority);
   // Action Status enum Label
   public ActionstatusLabel = ActionstatusLabel;
   public ActionStatusEnum = Object.values(ActionStatus);

  // Action to create in risk form
  actionToAdd: Action = new Action();

  // Propostion to edit in risk form
  actionToEdit: Action = new Action();


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

  /** Create Risk */
  createRisk() {
    console.log("add object:   " + this.riskToAdd);

    this.genericService.createGeneric('/risks', this.riskToAdd)
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
    updatedDueDate: any, effMeasDate: any, realizationDate: any,  responsibleAction: any, ActionStatus: any, priority: any, typeAction: any, origin: any, comments: any, createdBy: any, createdAt: any, updatedAt: any) {
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
      comments: comments,
      createdBy:createdBy,
      createdAt:createdAt,
      updatedAt:updatedAt
    };
    this.riskToAdd.actions.push(action);
    this.actionToAdd = new Action();

  }

  /**Edit action */
  editAction(action: any) {
    action.editMode = false;
    this.actionToEdit = new Action();
    this.actionToEdit.description = action.description;
    const index = this.riskToAdd.actions.indexOf(action);
    this.riskToAdd.actions.splice(index, 1, this.actionToEdit);
  }

  /**Delete action */
  deleteAction(action: any) {
    this.riskToAdd.actions.forEach((element, index) => {
      if (action.description === element.description) {
        this.riskToAdd.actions.splice(index, 1);
      }
    });
  }

  /** Empty add form fields */
  emptyObject() {
    this.riskToAdd = new Risk();
  }

}
