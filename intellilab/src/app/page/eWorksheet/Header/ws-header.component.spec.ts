import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WsHeaderComponent } from './ws-header.component';

describe('WsHeaderComponent', () => {
  let component: WsHeaderComponent;
  let fixture: ComponentFixture<WsHeaderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WsHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
