import { ActivatedRoute, Router, Params } from '@angular/router';
import { Component, OnInit} from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthenticationService2 } from '../shared/services2/Authenticate2.service';
import { ResponsiveService } from 'app/shared/services/responsive.service';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {

  resetPasswordForm: FormGroup;
  resetpassword: FormControl;
  confirmpassword: FormControl;
  showResetPass: boolean;
  userId: string;

  constructor(private fb: FormBuilder,
              private router: Router,
              private auth: AuthenticationService2,
              private route: ActivatedRoute) {
   }

  ngOnInit() {
    this.resetPasswordForm=this.createResetPasswordForm();
    this.showResetPass = false;
    this.route.params.subscribe((param: Params) => {
      const token = param.token;
      this.auth.verifyResetPassToken(token).subscribe(result=> {
        let key = Object.keys(result)[0]
        this.userId = result[key];

        if(parseInt(this.userId)){
          this.showResetPass = true;
        }
        else{
          alert(result[key])
          this.router.navigate(['/login']);
        }
      })
    });
  }

  createResetPasswordForm(): FormGroup {
    return this.fb.group(
      {
        password: [ null, Validators.compose([
          // 1. Password Field is Required
          Validators.required,
          // 2. check whether the entered password has a number
          this.patternValidator(/\d/, { hasNumber: true }),
          // 3. check whether the entered password has upper case letter
          this.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          // 4. check whether the entered password has a lower-case letter
          this.patternValidator(/[a-z]/, { hasSmallCase: true }),
          // 5. check whether the entered password has a special character
          this.patternValidator(/\W/, { hasSpecialCharacters: true }),
         // 6. Has a minimum length of 8 characters
         Validators.maxLength(15),
          // 7. Has a minimum length of 8 characters
          Validators.minLength(8)])
        ],
        confirmPassword: [null, Validators.compose([Validators.required])]
     },
     {
        // check whether our password and confirm password match
        validator: this.passwordMatchValidator
     });
  }

  passwordMatchValidator(group: FormGroup) {
    const password: string = group.get('password').value; // get password from our password form control
    const confirmPassword: string = group.get('confirmPassword').value; // get password from our confirmPassword form control
    // compare is the password math
    if (password !== confirmPassword) {
      // if they don't match, set an error in our confirmPassword form control
      group.get('confirmPassword').setErrors({ PasswordMismatch: true });
    }
  }

  patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (!control.value) {
        // if control is empty return no error
        return null;
      }

      // test the value of the control against the regexp supplied
      const valid = regex.test(control.value);

      // if true, return no error (no error), else return error passed in the second parameter
      return valid ? null : error;
    };
  }

  changePassword() {
    this.route.params.subscribe((param: Params) => {
      const token = param.token;
      this.auth.updatePassword(token, parseInt(this.userId, 10), this. resetPasswordForm.controls['password'].value).then((user: any) => {  
        console.log(user)
        if(user.active==false){
          this.auth.updateStatus(token, user.userId, true).then(()=>{
            if(user.invalidlogincount>0){
              this.auth.updateInvalidLoginCount(token, user.userId, 0);
            }
          });
        }
        localStorage.setItem('user.user_id', this.userId)
        sessionStorage.setItem('user.userId', user.userId.toString());
        sessionStorage.setItem('jwt', user['jwt'].toString());
        delete user['jwt'];
        sessionStorage.setItem('user', JSON.stringify(user));
        this.router.navigate(['./main']);
      })
      // this.auth.updatePassword(token, parseInt(this.userId, 10), this. resetPasswordForm.controls['password'].value).then(user=>{
      //   console.log(user)
      //   if(user.active==false){
      //     this.auth.updateStatus(user.userId, true).then(()=>{
      //       if(user.invalidlogincount>0){
      //         this.auth.updateInvalidLoginCount(user.userId, 0);
      //       }
      //     });
      //   }
  
      //   localStorage.setItem('user.user_id', this.userId)
      //   this.router.navigate(['./main']);
      // })
    });
  }

  backToLogin(){
    this.resetPasswordForm.reset();
    this.router.navigate(['/loginV2']);
  }



}




