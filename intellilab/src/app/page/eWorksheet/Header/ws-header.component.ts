import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
@Component({
  selector: 'app-ws-header',
  templateUrl: './ws-header.component.html',
  styleUrls: ['./ws-header.component.css']
})
export class WsHeaderComponent implements OnInit {
  @Input() logoText: string = 'eLabEx';
  @Input() tooBarTitle:string;
  @Input() backButtonSetting;
  @Input() buttonList;
  @Input() hideBackButton:boolean = false;
  hideButtons: boolean = false;
  constructor(private location: Location, public route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((paramMap: ParamMap) => {
      if (paramMap.has("templateId")) {
        this.hideButtons = true
      }
    });

    // console.log("back button setting:")
    // console.log(this.backButtonSetting)
  }

  goBack() {
    this.location.back();
  }

}
