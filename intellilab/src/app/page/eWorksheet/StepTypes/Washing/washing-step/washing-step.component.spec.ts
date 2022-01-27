import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WashingStepComponent } from './washing-step.component';

describe('WashingStepComponent', () => {
  let component: WashingStepComponent;
  let fixture: ComponentFixture<WashingStepComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WashingStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WashingStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
