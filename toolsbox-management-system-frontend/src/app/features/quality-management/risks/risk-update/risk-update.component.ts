import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Risk } from '@models/risk';
import { TypeAction } from '@models/type-action';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { Probability } from '@models/probability';
import { Severity } from '@models/severity';
import { RiskNature } from '@models/risk-nature';
import { Priority } from '@models/priority';
import { RiskStatus } from '@models/risk-status';
import { RiskStrategy } from '@models/risk-strategy';

@Component({
  selector: 'app-risk-update',
  templateUrl: './risk-update.component.html',
  styleUrls: ['./risk-update.component.css']
})
export class RiskUpdateComponent implements OnInit {
  // Get Risk to edit form users list component
  @Input() riskToEdit: Risk;
  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();
  // List of users for risk edit
  listOfUsers: any = [];
  // List of risk's types for risk update
  listOfTypes: string[];
  typeValue: TypeAction;
  listOfProbability: string[];
  probabilityValue: Probability;
  listOfSeverity: string[];
  severityValue: Severity;
  listOfRiskNature: string[];
  riskNatureValue: RiskNature;
  listOfPriority: string[];
  riskPriorityValue: Priority;
  listOfRiskStatus: string[];
  riskStatusValue: RiskStatus;
  listOfRiskStrategy: string[];
  riskStrategyValue: RiskStrategy;
  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.getListOfUsers();
    // Enum setting
    this.listOfTypes = Object.keys(TypeAction);
    this.listOfTypes = this.listOfTypes.slice(this.listOfTypes.length / 2);
    this.listOfProbability = Object.keys(Probability);
    this.listOfProbability = this.listOfProbability.slice(this.listOfProbability.length / 5);
    this.listOfSeverity = Object.keys(Severity);
    this.listOfSeverity = this.listOfSeverity.slice(this.listOfSeverity.length / 5);
    this.listOfRiskNature = Object.keys(RiskNature);
    this.listOfRiskNature = this.listOfRiskNature.slice(this.listOfRiskNature.length / 3);
    this.listOfPriority = Object.keys(Priority);
    this.listOfPriority = this.listOfPriority.slice(this.listOfPriority.length / 4);
    this.listOfRiskStatus = Object.keys(RiskStatus);
    this.listOfRiskStatus = this.listOfRiskStatus.slice(this.listOfRiskStatus.length / 4);
    this.listOfRiskStrategy = Object.keys(RiskStrategy);
    this.listOfRiskStrategy = this.listOfRiskStrategy.slice(this.listOfRiskStrategy.length / 4);
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
    this.probabilityValue = Probability[value];
    this.severityValue = Severity[value];
    this.riskNatureValue = RiskNature[value];
    this.riskPriorityValue = Priority[value];
    this.riskStatusValue = RiskStatus[value];
    this.riskStrategyValue = RiskStrategy[value];

  }
  /** Update Risk */
  updateRisk() {
    this.genericService.updateGeneric('/risks', this.riskToEdit.id, this.riskToEdit)
      .subscribe(
        data => {
          this.riskToEdit = data as Risk;
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
