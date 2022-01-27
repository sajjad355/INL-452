import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WsDesignEntryComponent } from './ws-design-entry.component';

describe('WsDesignEntryComponent', () => {
  let component: WsDesignEntryComponent;
  let fixture: ComponentFixture<WsDesignEntryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WsDesignEntryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WsDesignEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
