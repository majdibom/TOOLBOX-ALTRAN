import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapUpdateComponent } from './gap-update.component';

describe('GapUpdateComponent', () => {
  let component: GapUpdateComponent;
  let fixture: ComponentFixture<GapUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
