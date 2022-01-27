import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchData;
  searchBarInput;
  @Input() client: string;
  @Output() cancel = new EventEmitter();
  @Output() search = new EventEmitter();
  constructor() {}

  ngOnInit(): void {
    // const searchBarInput = document.getElementById("searchBarInput");
    // this.searchBarInput = document.getElementById("searchBarInput");
    // this.searchBarInput.addEventListener("keyup", function(event) {
    //   if (event.keyCode === 13) {
    //   event.preventDefault();
    //   document.getElementById("searchBtn").click();
    //   }
    // })

  }

  onSearch(){
    // window.alert("search data is: " + this.searchData);
    this.search.emit({
      client: this.client,
      searchData: this.searchData
    })

    console.log("search bar event:");
    console.log({
      client: this.client,
      searchData: this.searchData
    });
  }

  onCancel(){
    this.searchData = '';
    this.cancel.emit({
      client: this.client,
      cancel: true
    })
  }

  onKeyEvent(){
    this.onSearch();
  }

}
