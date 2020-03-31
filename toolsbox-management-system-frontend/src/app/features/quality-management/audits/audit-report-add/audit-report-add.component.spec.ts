import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditReportAddComponent } from './audit-report-add.component';

describe('AuditReportAddComponent', () => {
  let component: AuditReportAddComponent;
  let fixture: ComponentFixture<AuditReportAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditReportAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditReportAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
