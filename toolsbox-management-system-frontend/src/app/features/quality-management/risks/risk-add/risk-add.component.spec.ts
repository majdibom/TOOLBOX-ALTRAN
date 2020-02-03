import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskAddComponent } from './risk-add.component';

describe('RiskAddComponent', () => {
  let component: RiskAddComponent;
  let fixture: ComponentFixture<RiskAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RiskAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
