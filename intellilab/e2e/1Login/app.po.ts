import { browser, element, by } from 'protractor';

export class LoginPage {

  navigateToLoginPage() {
    return browser.get('/');
  }

  getLoginText() {
    return element(by.css('app-Login h1')).getText();
  }

  getLoginButton(){
    return element(by.css('.login-btn'));
  }

  getAlert(){
    element(by.css('[id="inputEmail"]')).clear();
    element(by.css('[id="inputEmail"]')).sendKeys('sdjalsd@sdafjldks.com');
    element(by.css('[id="loginbutton"]')).click().then(result=>{
      browser.wait(function() {
        return browser.switchTo().alert().then(
            function() { return true; }, 
            function() { return false; }
        );
    });
    }
      
    )
    return browser.switchTo().alert();
  }



  activeLoginButton() {
    element(by.css('[id="inputEmail"]')).sendKeys('moin@somrubioscience.com');
    return element(by.css('.login-btn'));
  }

  fillLoginInfor() {
    element(by.css('[id="inputEmail"]')).clear();
    element(by.css('[id="inputEmail"]')).sendKeys('moin@somrubioscience.com');
    element(by.css('.login-btn')).click();
    return element(by.css('app-Inventory h2')).getText();
  }



}


