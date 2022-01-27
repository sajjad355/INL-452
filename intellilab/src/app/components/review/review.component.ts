import { Component,  Input, Output, EventEmitter } from '@angular/core';
import { Review } from '../../shared/objects2/Review';
import { UserV2 } from '../../shared/objects2/UserV2';
import { OPERATION_NAMES } from '../../shared/models/Constants';


@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.scss']
})
export class ReviewComponent  {
  @Input() enableEdit: boolean;
  @Input() status: string;
  @Input() user: UserV2;
  @Input() reviewComment: string;
  @Input() reviewedBy: string;
  @Input() canReview: boolean;

  inputWarning: string = '';
  OPERATION_NAMES = OPERATION_NAMES;

  @Output() updateReview = new EventEmitter();

  approve() {
    let review : Review = new Review();
    review.reviewComment = this.reviewComment;
    review.reviewedBy = this.user.name;
    review.status = 'Approved';
    this.updateReview.emit( review );
  }

  reject() {
    let review : Review = new Review();
    review.reviewComment = this.reviewComment;
    review.reviewedBy = this.user.name;
    review.status = 'Rejected';
    if ( !review.reviewComment ) {
      this.inputWarning = 'A comment is required when rejecting.'
      setTimeout( () => {
        this.inputWarning = '';
      }, 5000);
    }
    else {
      this.updateReview.emit( review );
    }
  }


}
