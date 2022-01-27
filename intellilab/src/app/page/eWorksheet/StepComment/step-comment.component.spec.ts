import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { StepCommentComponent } from './step-comment.component';

describe('StepCommentComponent', () => {
  let component: StepCommentComponent;
  let fixture: ComponentFixture<StepCommentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ StepCommentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StepCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
