import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
  ValidatorFn,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService2 } from '../shared/services2/Authenticate2.service';
import { UserV2, UserRoleV2 } from '../shared/objects2/UserV2';
import { ResponsiveService } from 'app/shared/services/responsive.service';
import { InitService } from 'app/shared/services2/Init.service';

@Component({
  providers: [ResponsiveService, AuthenticationService2],
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  isRem: boolean;
  form: FormGroup;
  user: UserV2;
  currentUser: UserV2;
  emailNotValid = true;
  public isTablet = false;
  resetPassword = false;
  resetNotification = 'You will receive an email to reset your password shortly.';
  loginForm: FormGroup;
  email: FormControl;
  password: FormControl;
  isRemember: FormControl;
  requestResetForm: FormGroup;
  resetEmail: FormControl;
  loginError : boolean = false;
  errorMessage : string  = '';
  showLoginError: boolean = false;
  showResetError: boolean = false;
  

  static passwordMatchValidator(control: AbstractControl) {
    const password: string = control.get('password').value; // get password from our password form control
    const confirmPassword: string = control.get('confirmPassword').value; // get password from our confirmPassword form control
    // compare is the password math
    if (password !== confirmPassword) {
      // if they don't match, set an error in our confirmPassword form control
      control.get('confirmPassword').setErrors({ PasswordMismatch: true });
    }
  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private auth: AuthenticationService2,
    private responsiveService: ResponsiveService,
    private initeService: InitService
  ) {
    if (this.auth.isLogin()) { this.router.navigate(['./main']).then(() => {}); }
  }

  ngOnInit() {
    this.resetError();
    this.loginForm = this.createLoginForm();
    this.requestResetForm = this.createRequestResetForm();
    this.resetPassword = false;
    this.isRem = false;
    if (window.innerWidth <= 1000 && window.innerWidth >= 768) {
      this.isTablet = true;
    }
    this.user = new UserV2();
    this.user.userRoles.push( new UserRoleV2() );
  }

  createLoginForm(): FormGroup {
    return this.fb.group({
      // email is required and must be valid
      email: [
        null,
        Validators.compose([
          Validators.email,
          Validators.required,
          this.patternValidator(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/, {
            hasPattern: true,
          }),
        ]),
      ],
      password: [null, Validators.compose([Validators.required])],
      isRemember: [null, Validators.compose([])],
    });
  }

  createRequestResetForm(): FormGroup {
    return this.fb.group({
      // email is required and must be valid
      resetEmail: [
        null,
        Validators.compose([
          Validators.email,
          Validators.required,
          this.patternValidator(/[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/, {
            hasPattern: true,
          }),
        ]),
      ],
    });
  }

  onResize() {
    this.responsiveService.getTabletStatus().subscribe((isTablet) => {
      this.isTablet = isTablet;
      this.responsiveService.checkWidth();
    });
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

  changeTable(tableName: string) {
    this.resetPassword = tableName != 'login';
    this.resetError();
    this.resetForms();
  }

  sendLink() {
    if (this.requestResetForm.invalid) {
      this.showResetError = true;
      return;
    }
    const email = this.requestResetForm.value.resetEmail;
    this.auth.requestResetPassword(email).subscribe(

      (result) => {
        console.log("login result: ")
        console.log(result)
        if (!result) {
          alert('This email address does not exist');
          return;
        } else {
          alert('You will receive an email to reset your password shortly.');
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  resetError() {
    this.loginError = false;
    this.showLoginError = false;
    this.showResetError = false;
  }

  resetForms() {
    if (this.loginForm) {
      this.loginForm.reset();
    }
    if (this.requestResetForm) {
      this.requestResetForm.reset();
    }
  }

  login() {
    if (this.loginForm.invalid) {
      this.showLoginError = true;
      return;
    }
    this.resetError();
    let email = this.loginForm.value.email;
    const password = this.loginForm.value.password;

    const loggedInUser: any = new UserV2({
      name: '--',
      email: email,
      password: password
    });

    this.auth
      .login(loggedInUser)
      .then((_) => {
        this.router.navigate(['./main']).then(() => {});
      })
      .catch((error) => {
        this.errorMessage = error;
        this.loginError = true;
        setTimeout( () => {
          this.loginError = false;
          this.errorMessage = '';
       }, 5000);
      });
  }


  
}
