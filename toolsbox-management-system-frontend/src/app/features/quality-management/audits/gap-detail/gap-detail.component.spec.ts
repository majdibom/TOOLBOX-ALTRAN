import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapDetailComponent } from './gap-detail.component';

describe('GapDetailComponent', () => {
  let component: GapDetailComponent;
  let fixture: ComponentFixture<GapDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
