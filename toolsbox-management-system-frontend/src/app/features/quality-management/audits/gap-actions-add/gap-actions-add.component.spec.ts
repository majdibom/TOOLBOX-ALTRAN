import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapActionsAddComponent } from './gap-actions-add.component';

describe('GapActionsAddComponent', () => {
  let component: GapActionsAddComponent;
  let fixture: ComponentFixture<GapActionsAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapActionsAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapActionsAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
