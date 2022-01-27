import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-step-comment',
  templateUrl: './step-comment.component.html',
  styleUrls: ['./step-comment.component.css']
})
export class StepCommentComponent implements OnInit {

// @Input() parentId:number;
@Input() rowSize: number = 6;
@Input() columnSize: number = 30;
@Input() initCommentData;
@Input() commentType:string;
@Input() commentTile;
@Input() hasTopBreak = true;
@Output() commentPayload = new EventEmitter<{commentType:string ,comment: string}>();
@Output() cancel = new EventEmitter<{commentType:string}>();

commentData:string = "";
isEmpty:boolean = true;
isSaved:boolean = false;
// commentTile: string;
  constructor() { }

  ngOnInit(): void {

    // if(this.commentType === 'experiment'){
    //   this.commentTile = 'Experiment Comment'
    // }else{
    //   this.commentTile = 'Review Comment'
    // }
    switch(this.commentType){
      case 'design':
        this.commentTile = 'Design Comment'
        break;
      case 'experiment':
        this.commentTile = 'Experiment Comment'
        break;

      case 'review':
        this.commentTile = 'Review Comment'
        break;
    }

    if(this.initCommentData){
      // console.log(`.......>>>>>> init comment data: ${this.initCommentData}`)
      // this.commentData = this.initCommentData;
      // this.checkEmpty();
      // this.isSaved = true;
      this.initComment(this.initCommentData)
    }else{
      console.log('.......>>>>>> no init comment data')
    }
  }

  initComment(payload){
      console.log(`.......>>>>>> init comment data: ${payload}`)
      this.commentData = payload;
      this.checkEmpty();
      this.isSaved = true;
  }

  onSave(){
    const payload = {
      // parentId: this.parentId,
      commentType: this.commentType,
      comment: this.commentData
    }
    this.commentPayload.emit(payload);
    // console.log("comment is saved");
    // console.log(payload);
    this.isSaved = true;
    this.checkEmpty();
  }

  checkEmpty(){
    if(this.commentData.length > 0){
      this.isEmpty = false;
    }else{
      this.isEmpty = true;
      this.cancelComment();
    }

    console.log("value for isEmpty: " + this.isEmpty);
  }

  cancelComment(){
    this.cancel.emit({
      commentType: this.commentType,
    })
  }
}
