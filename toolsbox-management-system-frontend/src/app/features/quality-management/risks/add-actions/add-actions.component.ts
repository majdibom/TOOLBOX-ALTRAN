import { Component, OnInit, Input, Output, EventEmitter, AfterContentInit, AfterViewInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { PageClient } from '@models/page-client';
import { ActivatedRoute, Router } from '@angular/router';
import { Risk } from '@models/risk';
import swal from 'sweetalert2';
import { Action } from '@models/action';
import { RiskAction } from '@models/risk-Action';
import { RiskActionId } from '@models/risk-Action-Id';


@Component({
  selector: 'app-add-actions',
  templateUrl: './add-actions.component.html',
  styleUrls: ['./add-actions.component.css']
})
export class AddActionsComponent implements OnInit, AfterViewInit {
  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';
  // List of actions
  listActions: any;
  // Initial risk id
  idRisk: number;
  idAction: number;
  action: Action = new Action();

  // Get Risk to edit form risk details component
  @Input() riskToEdit: Risk;
  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // Action to create in risk form
  riskActiontoAdd: RiskAction = new RiskAction();
  // Action to create in risk form
  id: RiskActionId = new RiskActionId();

  constructor(private route: ActivatedRoute, private genericService: GenericService, private router: Router) { }

  ngOnInit() {
    this.reloadData();
    
    
  }

  ngAfterViewInit() {
    this.listActions = [];
  }


  /** Reload data after every action */
  reloadData() {
    this.listActions = this.getActions();
  }

  /** Get all actions */
  getActions() {
    this.genericService.getGenericPage('/actions', this.selectedPage, this.item)
      .subscribe(
        data => {
          this.pageClient = data;
          this.total = this.pageClient.totalElements;
          this.listActions = this.pageClient.content;
        });
  }
  /** Add Action to a Risk */
  addActionToRisk(action: Action) {
    this.id = new RiskActionId();
    this.riskActiontoAdd = new RiskAction();
    this.riskActiontoAdd.id.actionId = action.id;
    this.riskActiontoAdd.id.riskId = this.riskToEdit.id;
    this.riskActiontoAdd.action = action;
    this.riskActiontoAdd.risk = this.riskToEdit;
    this.genericService.createGeneric('/risk-action', this.riskActiontoAdd)
      .subscribe(
        data => {
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
  /** Reload data after every action added*/
  reloadRisk() {
    // reload table data
    this.reloadEvent.emit(null);
  }


  /** Pagination: Change number of elements in the table */
  onSelect(pageNumber: number) {
    this.selectedPage = pageNumber - 1;
    if (this.searchInput !== '') {
      this.onSearch(this.searchInput);
    } else {
      this.getActions();
    }
  }

  /** Pagination: Change page number */
  getItems(itemsNumber: number) {
    this.item = itemsNumber;
    this.onSelect(1);
  }

  /** Search Action */
  onSearch(search: String) {
    if (this.searchInput !== '') {
      this.genericService.searchGeneric('/actions/search', search, this.selectedPage, this.item)
        .subscribe(
          data => {
            if (this.selectedPage > (data.totalPages) - 1) {
              this.onSelect((data.totalPages));
            } else {
              this.pageClient = new PageClient();
              this.pageClient = data;
              this.total = this.pageClient.totalElements;
              this.listActions = this.pageClient.content;
            }
          });
    } else {
      this.getActions();
    }
  }
}

