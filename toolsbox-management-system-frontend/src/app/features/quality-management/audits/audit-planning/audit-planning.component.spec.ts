import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditPlanningComponent } from './audit-planning.component';

describe('AuditPlanningComponent', () => {
  let component: AuditPlanningComponent;
  let fixture: ComponentFixture<AuditPlanningComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditPlanningComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditPlanningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
