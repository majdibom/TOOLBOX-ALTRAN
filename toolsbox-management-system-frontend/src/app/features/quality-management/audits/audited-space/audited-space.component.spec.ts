import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditedSpaceComponent } from './audited-space.component';

describe('AuditedSpaceComponent', () => {
  let component: AuditedSpaceComponent;
  let fixture: ComponentFixture<AuditedSpaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditedSpaceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditedSpaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
