import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CalibratorComponent } from './calibrator.component';

describe('CalibratorComponent', () => {
  let component: CalibratorComponent;
  let fixture: ComponentFixture<CalibratorComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CalibratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalibratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
