import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticationAlertComponent } from './authentication-alert.component';

describe('AuthenticationAlertComponent', () => {
  let component: AuthenticationAlertComponent;
  let fixture: ComponentFixture<AuthenticationAlertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthenticationAlertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthenticationAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
