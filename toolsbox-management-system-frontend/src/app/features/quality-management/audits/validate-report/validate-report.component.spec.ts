import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidateReportComponent } from './validate-report.component';

describe('ValidateReportComponent', () => {
  let component: ValidateReportComponent;
  let fixture: ComponentFixture<ValidateReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidateReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidateReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
