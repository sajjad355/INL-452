import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { UserV2 } from '../shared/objects2/UserV2';
import { AuthenticationService2 } from '../shared/services2/Authenticate2.service';
import { FormsModule } from '@angular/forms';
import { AngularMyDatePickerModule } from 'angular-mydatepicker';
import { HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import 'rxjs/add/observable/of';
import 'zone.js/dist/async-test';
import 'zone.js/dist/fake-async-test';

xdescribe('Login Component Integration Test', () => {
    // Arrange
    let loginComponent: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let authenticationService: AuthenticationService2;
    const loginSpyObject = jasmine.createSpyObj('authenticationService', ['login']);

    beforeAll(() => {
        TestBed.configureTestingModule({
            imports: [ FormsModule, AngularMyDatePickerModule, RouterTestingModule, ReactiveFormsModule, HttpClientModule, HttpClientTestingModule, NgbModule],
            declarations: [LoginComponent],
            providers: [AuthenticationService2, { provide: loginSpyObject, useValue: loginSpyObject }]
        });

        fixture =  TestBed.createComponent(LoginComponent);
        loginComponent = fixture.componentInstance;
        authenticationService = TestBed.get(AuthenticationService2);
    });

    afterEach(() => {
        authenticationService.logout();
    });

    it('Show wrong message if log in email is wrong, isR is true', waitForAsync(() => {
        loginComponent.user = new UserV2();
        loginComponent.user.email = 'email';
        loginComponent.isRem = true;
        // let loginSpy = loginSpyObject.login().and.callThrough();
        loginComponent.login();
        fixture.whenStable();
        expect(loginComponent.message).toBe('Fail to Login');
    }));

    // it('Should navigate to main page after user log in with correct email, isR is true', ()=>{
    //     loginComponent.user.email='chen.fei@somrubioscience.com'
    //     loginComponent.isRem=true;
    //     loginComponent.login()
    // })

    // it('Show wrong message if log in email is wrong, isR is false', ()=>{
    //     loginComponent.user.email='email'
    //     loginComponent.isRem=false;
    //     loginComponent.login()
    //     expect(loginComponent.message).toBe('Fail to Login');
    // })

    // it('Should navigate to main page after user log in with correct email, isR is false', ()=>{
    //     loginComponent.user.email='chen.fei@somrubioscience.com'
    //     loginComponent.isRem=false;
    //     loginComponent.login()
    // })
});
