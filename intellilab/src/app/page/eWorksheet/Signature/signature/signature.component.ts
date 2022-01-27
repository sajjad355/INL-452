import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthenticationService2 } from 'app/shared/services2/Authenticate2.service';

@Component({
  selector: 'app-signature',
  templateUrl: './signature.component-aegyris-style.html',
  styleUrls: ['./signature.component-aegyris-style.scss']
})
export class SignatureComponent implements OnInit {
  user_email = null;
  user_password = null;
  emailPattern = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
  user_valid = false;
  statement = 'Please privide your account email and password in below to verify your credential';

  constructor(public dialogRef: MatDialogRef<SignatureComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private auth: AuthenticationService2) { }

  ngOnInit() {
  }

  cancel(){
    this.dialogRef.close({user_valid: this.user_valid});
  }

  // verifyCredential(){
  //   if(this.user_email && this.user_password && this.user_email.match(this.emailPattern)){
  //     this.auth.verifyCredential(this.user_email, this.user_password).subscribe(res => {
  //       console.log("Verify user credential response: ");
  //       console.log(res);

  //       if(!res){
  //           this.user_valid = false;
  //           this.statement = 'Invalid email or password. Please try again, or click cancel.'
  //       }else if(res.password === null){
  //           this.user_valid = false;
  //           this.statement = 'Invalid email or password. Please try again, or click cancel.'
  //       }else{
  //         this.user_valid = true;
  //         this.cancel();
  //       }


  //     }, err => console.log('HTTP Error', err))
  //   }else if(!this.user_email || !this.user_password){
  //     this.statement = 'Please provide both your email and password, or click cancel.'
  //   }else if(this.user_email && !this.user_email.match(this.emailPattern)){
  //     this.statement = 'Please provide a valid email, or click cancel.'
  //   }
  // }

  verifyCredential(){
    if(this.user_email && this.user_password && this.user_email.match(this.emailPattern)){
      this.auth.verifyCredential(this.user_email, this.user_password).subscribe(res => {
        console.log("Verify user credential response: ");
        console.log(res);

        if(res && res['jwt']){
          this.user_valid = true;
          this.cancel();
        }


      }, err => {
        this.user_valid = false;
        this.statement = 'Invalid email or password. Please try again, or click cancel.'
      })
    }else if(!this.user_email || !this.user_password){
      this.statement = 'Please provide both your email and password, or click cancel.'
    }else if(this.user_email && !this.user_email.match(this.emailPattern)){
      this.statement = 'Please provide a valid email, or click cancel.'
    }
  }

}
