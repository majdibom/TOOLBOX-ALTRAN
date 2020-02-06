import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Risk } from '@models/risk';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { SeverityLabel, Severity } from '@models/severity';
import { ProbabilityLabel, Probability } from '@models/probability';
import { RiskStatusLabel, RiskStatus } from '@models/risk-status';
import { Action } from '@models/action';
import { Status, ActionstatusLabel } from '@models/status';
import { PriorityLabel, Priority } from '@models/priority';
import { TypeActionLabel, TypeAction } from '@models/type-action';
import { OriginLabel, Origin } from '@models/origin';




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
  // Action Status enum Label
  public actionstatusLabel = ActionstatusLabel;
  public ActionstatusEnum = Object.values(Status);

  // Severity enum Label
  public severityLabel = SeverityLabel;
  public severityEnum = Object.values(Severity);

  // Risk Status enum Label
  public statusLabel = RiskStatusLabel;
  public statusEnum = Object.values(RiskStatus);

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
    console.log(this.riskToAdd);

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
  addAction(Description: any, effMesCriterion: any, openDate: any, dueDate: any,
    updatedDueDate: any, effMesDate: any, responsibleAction: any, realisationDate: any, comments: any,
     efficiency: any, status: any, priority: any, typeAction: any, origin: any, processImpacts: any) {
    const action = {
      Description: Description,
      effMesCriterion: effMesCriterion,
      openDate: openDate,
      dueDate: dueDate,
      updatedDueDate: updatedDueDate,
      effMesDate: effMesDate,
      realisationDate: realisationDate,
      comments: comments,
      efficiency: efficiency,
      status: status,
      priority: priority,
      responsibleAction: responsibleAction,
      typeAction: typeAction,
      origin: origin,
      processImpacts: processImpacts
    };
    this.riskToAdd.actions.push(action);
    this.actionToAdd = new Action();

  }

  /**Edit action */
  editAction(action: any) {
    action.editMode = false;
    this.actionToEdit = new Action();
    this.actionToEdit.Description = action.cause;
    // this.propositionToEdit.title = proposition.title;
    // this.propositionToEdit.valid = proposition.valid;
    const index = this.riskToAdd.actions.indexOf(action);
    this.riskToAdd.actions.splice(index, 1, this.actionToEdit);
  }

  /**Delete action */
  deleteAction(action: any) {
    this.riskToAdd.actions.forEach((element, index) => {
      if (action.Description === element.Description) {
        this.riskToAdd.actions.splice(index, 1);
      }
    });
  }

  /** Empty add form fields */
  emptyObject() {
    this.riskToAdd = new Risk();
  }

}
