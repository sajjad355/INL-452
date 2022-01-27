import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubDilutionComponent } from './sub-dilution.component';

describe('SubMaterialComponent', () => {
  let component: SubDilutionComponent;
  let fixture: ComponentFixture<SubDilutionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubDilutionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubDilutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
