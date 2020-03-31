import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Project } from '@models/project';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-project-add',
  templateUrl: './project-add.component.html',
  styleUrls: ['./project-add.component.css']
})
export class ProjectAddComponent implements OnInit {
  // Event for reload table content
  @Output() reloadEvent = new EventEmitter();
  // Project to create 
  projectToAdd: Project = new Project();


  // List of activity for Project create
  listOfActivities: any = [];
  // List of activity for Project create
  listOfDeliveryModels: any = [];
  constructor(private genericService: GenericService) { }

  ngOnInit() {
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
  /** Create project */
  createProject() {
    console.log("add object:   " + this.projectToAdd);
    this.genericService.createGeneric('/projects', this.projectToAdd)
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
  this.projectToAdd = new Project();
}
}
