import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditReportUpdateComponent } from './audit-report-update.component';

describe('AuditReportUpdateComponent', () => {
  let component: AuditReportUpdateComponent;
  let fixture: ComponentFixture<AuditReportUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditReportUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditReportUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
