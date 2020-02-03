import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditAddComponent } from './audit-add.component';

describe('AuditAddComponent', () => {
  let component: AuditAddComponent;
  let fixture: ComponentFixture<AuditAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
