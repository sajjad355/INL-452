import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WorksheetMainComponent } from './worksheet-main.component';

describe('WorksheetMainComponent', () => {
  let component: WorksheetMainComponent;
  let fixture: ComponentFixture<WorksheetMainComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WorksheetMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorksheetMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
