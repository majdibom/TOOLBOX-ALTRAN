import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapActionsListComponent } from './gap-actions-list.component';

describe('GapActionsListComponent', () => {
  let component: GapActionsListComponent;
  let fixture: ComponentFixture<GapActionsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapActionsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapActionsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
