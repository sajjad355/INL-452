import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RollerButtonComponent } from './roller-button.component';

describe('RollerButtonComponent', () => {
  let component: RollerButtonComponent;
  let fixture: ComponentFixture<RollerButtonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RollerButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RollerButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
