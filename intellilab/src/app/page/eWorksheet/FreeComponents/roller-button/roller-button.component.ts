import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-roller-button',
  templateUrl: './roller-button.component.html',
  styleUrls: ['./roller-button.component.scss']
})
export class RollerButtonComponent implements OnInit {

  @Input() leftLabel = 'Previous'
  @Input() rightLabel = 'Next'
  @Output() rollerMove = new EventEmitter();
  constructor() { }

  ngOnInit() {
  }

  goLeft(){
    this.rollerMove.emit({
      roll:'left'
    })
    console.log('Rolls left')
  }

  goRight(){
    this.rollerMove.emit({
      roll:'right'
    })
    console.log('Rolls right')
  }

}
