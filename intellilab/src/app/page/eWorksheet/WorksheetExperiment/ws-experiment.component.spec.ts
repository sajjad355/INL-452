import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WsExperimentComponent } from './ws-experiment.component';

describe('WsExperimentComponent', () => {
  let component: WsExperimentComponent;
  let fixture: ComponentFixture<WsExperimentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WsExperimentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WsExperimentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
