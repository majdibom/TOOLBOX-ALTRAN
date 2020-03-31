import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditUpdateComponent } from './audit-update.component';

describe('AuditUpdateComponent', () => {
  let component: AuditUpdateComponent;
  let fixture: ComponentFixture<AuditUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
