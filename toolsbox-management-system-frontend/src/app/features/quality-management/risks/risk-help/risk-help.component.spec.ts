import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskHelpComponent } from './risk-help.component';

describe('RiskHelpComponent', () => {
  let component: RiskHelpComponent;
  let fixture: ComponentFixture<RiskHelpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskHelpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskHelpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
