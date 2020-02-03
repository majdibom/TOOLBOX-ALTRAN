import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { ActivatedRoute } from '@angular/router';
import { Action } from '@models/action';

@Component({
  selector: 'app-action-detail',
  templateUrl: './action-detail.component.html',
  styleUrls: ['./action-detail.component.css']
})
export class ActionDetailComponent implements OnInit {

  // Initial action id
  idAction: number;

  // Initial action detail
  action: Action = new Action();

  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getAction();
  }

  /** Get action detail */
  getAction() {
    this.idAction = this.route.snapshot.params.id;
    this.genericService.getGenericById('/actions', this.idAction).subscribe(data => {
      this.action = data.value;
    });
  }
}
