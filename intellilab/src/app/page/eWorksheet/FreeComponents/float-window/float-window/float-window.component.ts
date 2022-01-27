import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-float-window',
  templateUrl: './float-window.component.html',
  styleUrls: ['./float-window.component.scss']
})
export class FloatWindowComponent implements OnInit {

  @Input() message: any;
  constructor() { }

  ngOnInit(): void {
  }

}
