import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GapAddComponent } from './gap-add.component';

describe('GapAddComponent', () => {
  let component: GapAddComponent;
  let fixture: ComponentFixture<GapAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GapAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GapAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
