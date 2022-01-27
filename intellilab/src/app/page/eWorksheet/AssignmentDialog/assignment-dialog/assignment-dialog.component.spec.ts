import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AssignmentDialogComponent } from './assignment-dialog.component';

describe('AssignmentDialogComponent', () => {
  let component: AssignmentDialogComponent;
  let fixture: ComponentFixture<AssignmentDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignmentDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignmentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
