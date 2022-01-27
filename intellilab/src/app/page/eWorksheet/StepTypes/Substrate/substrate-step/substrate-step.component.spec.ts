import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubstrateStepComponent } from './substrate-step.component';

describe('SubstrateStepComponent', () => {
  let component: SubstrateStepComponent;
  let fixture: ComponentFixture<SubstrateStepComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubstrateStepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubstrateStepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
