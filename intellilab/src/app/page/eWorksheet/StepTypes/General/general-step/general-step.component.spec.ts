import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { GeneralStepComponent } from './general-step.component';

describe('GeneralStepComponent', () => {
  let component: GeneralStepComponent;
  let fixture: ComponentFixture<GeneralStepComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneralStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneralStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
