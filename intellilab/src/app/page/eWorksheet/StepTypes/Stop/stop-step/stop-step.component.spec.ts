import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { StopStepComponent } from './stop-step.component';

describe('StopStepComponent', () => {
  let component: StopStepComponent;
  let fixture: ComponentFixture<StopStepComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ StopStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StopStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
