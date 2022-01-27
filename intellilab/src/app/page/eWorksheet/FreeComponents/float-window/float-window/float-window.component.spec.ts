import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FloatWindowComponent } from './float-window.component';

describe('FloatWindowComponent', () => {
  let component: FloatWindowComponent;
  let fixture: ComponentFixture<FloatWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FloatWindowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FloatWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
