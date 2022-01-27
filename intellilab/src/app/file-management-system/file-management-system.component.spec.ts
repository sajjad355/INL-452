import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileManagementSystemComponent } from './file-management-system.component';

describe('FileManagementSystemComponent', () => {
  let component: FileManagementSystemComponent;
  let fixture: ComponentFixture<FileManagementSystemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileManagementSystemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileManagementSystemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
