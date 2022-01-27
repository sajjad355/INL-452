import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-play-ground',
  templateUrl: './play-ground.component.html',
  styleUrls: ['./play-ground.component.css']
})
export class PlayGroundComponent implements OnInit {

  timerArr = [1,2,3,4,5]
  constructor() { }

  ngOnInit() {
  }

}
