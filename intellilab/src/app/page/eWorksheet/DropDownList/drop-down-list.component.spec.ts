import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DropDownListComponent } from './drop-down-list.component';

describe('DropDownListComponent', () => {
  let component: DropDownListComponent;
  let fixture: ComponentFixture<DropDownListComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DropDownListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DropDownListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
