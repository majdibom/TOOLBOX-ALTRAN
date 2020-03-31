import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Project } from '@models/project';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-project-update',
  templateUrl: './project-update.component.html',
  styleUrls: ['./project-update.component.css']
})
export class ProjectUpdateComponent implements OnInit {
  // Get project to edit form projects list component
  @Input() projectToEdit: Project;

  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();
  // List of activity for Project create
  listOfActivities: any = [];
  // List of activity for Project create
  listOfDeliveryModels: any = [];
  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.projectToEdit = new Project();
    this.getlistOfActivities();
    this.getlistOfDeliveryModels();
  }
  /** Get all activities **/
  getlistOfActivities() {
    this.genericService.getGenericList('/activities/all').subscribe(data => {
      this.listOfActivities = data;
    });
  }
  /** Get all Delivery Models **/
  getlistOfDeliveryModels() {
    this.genericService.getGenericList('/delivery-model/all').subscribe(data => {
      this.listOfDeliveryModels = data;
    });
  }

  /** Update Project */
  updateProject() {
    this.genericService.updateGeneric('/projects', this.projectToEdit.idProject, this.projectToEdit)
      .subscribe(
        data => {
          this.projectToEdit = data as Project;
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
