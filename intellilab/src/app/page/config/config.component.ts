import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-config',
  templateUrl: './config.component.html',
  styleUrls: ['./config.component.scss']
})


export class ConfigComponent  {
  selectedConfigType: string = 'settings;'

  constructor(private route: ActivatedRoute) {
    this.handleParam()
  }

  handleParam() {
    const configType = this.route.snapshot.firstChild.routeConfig.path.replace("/:id", "")
    this.selectedConfigType = configType
  }
}
