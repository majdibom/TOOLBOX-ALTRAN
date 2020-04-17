import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapActionsUpdateComponent } from './gap-actions-update.component';

describe('GapActionsUpdateComponent', () => {
  let component: GapActionsUpdateComponent;
  let fixture: ComponentFixture<GapActionsUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapActionsUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapActionsUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
