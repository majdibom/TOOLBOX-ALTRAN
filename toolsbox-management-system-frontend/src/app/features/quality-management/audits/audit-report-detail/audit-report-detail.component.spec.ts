import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditReportDetailComponent } from './audit-report-detail.component';

describe('AuditReportDetailComponent', () => {
  let component: AuditReportDetailComponent;
  let fixture: ComponentFixture<AuditReportDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditReportDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditReportDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
