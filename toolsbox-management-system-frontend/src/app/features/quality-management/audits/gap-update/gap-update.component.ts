import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Gap } from '@models/gap';
import { GenericService } from '@services/generic.service';
import swal from 'sweetalert2';
import { TypeGap } from '@models/type-gap';

@Component({
  selector: 'app-gap-update',
  templateUrl: './gap-update.component.html',
  styleUrls: ['./gap-update.component.css']
})
export class GapUpdateComponent implements OnInit {
 // Get Gap to edit form report component
 @Input() gapToEdit: Gap;
  // Event for reload table content after update
  @Output() reloadEvent = new EventEmitter();

  listOfTypeGap: string[];
 TypeGapValue: TypeGap;
  constructor(private genericService: GenericService) { }

  ngOnInit() {
    this.listOfTypeGap = Object.keys(TypeGap);
    this.listOfTypeGap = this.listOfTypeGap.slice(this.listOfTypeGap.length / 5);
  }
 /** Update Gap */
 updateGap() {
  this.genericService.updateGeneric('/gaps', this.gapToEdit.id, this.gapToEdit)
    .subscribe(
      data => {
        this.gapToEdit = data as Gap;
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
