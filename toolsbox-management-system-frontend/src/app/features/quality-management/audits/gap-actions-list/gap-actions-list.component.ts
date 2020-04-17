import { Component, OnInit, Input, Output, EventEmitter, AfterContentInit, AfterViewInit } from '@angular/core';
import { PageClient } from '@models/page-client';
import { Gap } from '@models/gap';
import { ActivatedRoute, Router } from '@angular/router';
import { GenericService } from '@services/generic.service';
import { Action } from '@models/action';
import swal from 'sweetalert2';

@Component({
  selector: 'app-gap-actions-list',
  templateUrl: './gap-actions-list.component.html',
  styleUrls: ['./gap-actions-list.component.css']
})
export class GapActionsListComponent implements OnInit, AfterViewInit {
  // Get Risk to edit form risk details component
  @Input() gapToEdit: Gap;
  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  // Pagination params
  pageClient: PageClient = new PageClient();
  total: number;
  selectedPage = 0;
  item = 5;
  searchInput: String = '';
  // List of actions
  listActions: any;
  action: Action = new Action();
  number=10 ;
  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.reloadData();

  }
  ngAfterViewInit() {
    this.listActions = [];
    console.log(this.number);
    
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
  /** Reload data after every action added*/
  reloadGap() {
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
