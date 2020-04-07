import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditorSpaceComponent } from './auditor-space.component';

describe('AuditorSpaceComponent', () => {
  let component: AuditorSpaceComponent;
  let fixture: ComponentFixture<AuditorSpaceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuditorSpaceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditorSpaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
