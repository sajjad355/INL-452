import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubMaterialComponent } from './sub-material.component';

describe('SubMaterialComponent', () => {
  let component: SubMaterialComponent;
  let fixture: ComponentFixture<SubMaterialComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
