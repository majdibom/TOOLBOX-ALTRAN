import { Component, OnInit } from '@angular/core';
import { GenericService } from '@services/generic.service';
import { Risk } from '@models/risk';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-risk-detail',
  templateUrl: './risk-detail.component.html',
  styleUrls: ['./risk-detail.component.css']
})
export class RiskDetailComponent implements OnInit {
 // Initial risk id
 idRisk: number;
 // Initial risk detail
 risk: Risk = new Risk();
  constructor(private route: ActivatedRoute, private genericService: GenericService) { }

  ngOnInit() {
    this.getRisk();
  }
  /** Get action detail */
  getRisk() {
    this.idRisk = this.route.snapshot.params.id;
    this.genericService.getGenericById('/risks', this.idRisk).subscribe(data => {
      this.risk = data.value;
    });
  }
}
