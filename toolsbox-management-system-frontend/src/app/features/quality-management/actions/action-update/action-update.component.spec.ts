import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionUpdateComponent } from './action-update.component';

describe('ActionUpdateComponent', () => {
  let component: ActionUpdateComponent;
  let fixture: ComponentFixture<ActionUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActionUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActionUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
