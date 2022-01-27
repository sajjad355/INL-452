import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FmsAlertComponent } from './fms-alert.component';

describe('FmsAlertComponent', () => {
  let component: FmsAlertComponent;
  let fixture: ComponentFixture<FmsAlertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FmsAlertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FmsAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
