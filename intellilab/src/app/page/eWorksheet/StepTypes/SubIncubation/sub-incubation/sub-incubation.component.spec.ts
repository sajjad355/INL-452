import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubIncubationComponent } from './sub-incubation.component';

describe('SubIncubationComponent', () => {
  let component: SubIncubationComponent;
  let fixture: ComponentFixture<SubIncubationComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubIncubationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubIncubationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
