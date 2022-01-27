import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { StepTimerComponent } from './step-timer.component';

describe('StepTimerComponent', () => {
  let component: StepTimerComponent;
  let fixture: ComponentFixture<StepTimerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ StepTimerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StepTimerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
