import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-text-field',
  templateUrl: './text-field.component.html',
  styleUrls: ['./text-field.component.scss']
})
export class TextFieldComponent implements OnInit {

  @Input() text = '';
  @Input() tag = 'Undefined';
  @Input() infoTip;
  @Input() toolTipType;
  @Input() tagFullName;
  @Input() mode = 'input';
  constructor() { }

  ngOnInit() {
    // this.text = 'ry input texttrytrytry input texttrytrytry input texttrytry'
    // this.tag = 'ABC'
  }

}
