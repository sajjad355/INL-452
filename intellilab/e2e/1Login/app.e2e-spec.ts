// import { LoginPage } from './app.po';
import { LoginPage } from './app.po';
import { Inventory } from './inventory.po';

//import { SomrusoftPage } from './app.po';
import { browser, element, by } from 'protractor';

// ==========================================================================================================
// =========================================BEGIN OF LOGIN TEST============================================== 
// ==========================================================================================================
describe('IntelliLab Login page', () => {
  let page = new LoginPage();
  
  beforeAll(() => {
    page.navigateToLoginPage();
  });

  it('When users browse to our IntelliLab app, they should see the default “Login” screen', () => {
    expect(page.getLoginText()).toEqual('Login');
  });

  it('It should display login button', () => {
     expect(page.getLoginButton().getText()).toEqual('Login');
  });

  it('Login button should be active if user email format is correct', () => {
    let buttonstatus= page.activeLoginButton();
    expect(buttonstatus.isEnabled()).toBe(true);
  });

  it('Show alert if users login with wrong username', () => {
    let alert = page.getAlert()
    expect(alert.getText()).toMatch('You don\'t have permission to view this page');
    alert.accept();
  });

  it('Login button should navigate to inventory page if users enter correct username and password', () => {
    expect(page.fillLoginInfor()).toEqual('Inventory')
    
  });

});

// ==========================================================================================================
// =========================================BEGIN OF INVENTORY TEST==========================================
// ==========================================================================================================

describe('IntelliLab Inventory page', () => {
  let page = new Inventory();
  beforeEach(() => {
    
  });

  it('When users browse to Inventory component, they should see the default “Inventory” on screen', () => {
    expect(page.getInventoryText()).toEqual('Inventory');
  });

  it('The select all checkbox is selected default', () => {
    expect(page.checkAllDefault().isSelected()).toBeTruthy();
  });

  it('Browser should display search result', () => {
    expect(page.searchInventory()).toEqual('fdgdfa');
  });

  it('Press on New Item button should open new item window', () => {
    expect(page.createnewitem().getText()).toEqual('New Item');
  });

  it('Save new item information', () => {
    //expect(page.savenewitem().getText()).toEqual('e2etest');
    let array=page.savenewitem();
    Promise.all([
      expect(array[0].getText()).toEqual('e2etest'),
      expect(array[1].getText()).toEqual('e2etest'),
      expect(array[2].getText()).toEqual('e2etest'),
      expect(array[3].getText()).toEqual('µL'),
      expect(array[4].getText()).toEqual('Cell Line')])
  });

  it('Edit existing item information', () => {
    let array=page.edititem();
    Promise.all([
      expect(array[0].getText()).toEqual('e2etest2'),
      expect(array[1].getText()).toEqual('e2etest2'),
      expect(array[2].getText()).toEqual('e2etest2'),
      expect(array[3].getText()).toEqual('mL'),
      expect(array[4].getText()).toEqual('Media')])
  });

  it('Delete item', () => {
    expect(page.deleteitem().getText()).not.toEqual('e2etest2');
  });


  it('Create new itemdetail', () => {
    let array=page.createnewitemdetail();
    Promise.all([
      expect(array[0].getText()).toEqual('123456'),
      expect(array[1].getText()).toEqual('10 in stock, N/A in use'),
      expect(array[2].getText()).toEqual('µL'),
      expect(array[3].getText()).toEqual('-80 Freezer #1'),
      expect(array[4].getText()).toEqual('Mar 27, 2018 / Feb 4, 2019')])
  });

  it('Edit itemdetail', () => {
    let array=page.edititemdetail();
    Promise.all([
      expect(array[0].getText()).toEqual('567890'),
      expect(array[1].getText()).toEqual('20 in stock, N/A in use'),
      expect(array[2].getText()).toEqual('mL'),
      expect(array[3].getText()).toEqual('-20 Freezer #1'),
      expect(array[4].getText()).toEqual('May 27, 2018 / Feb 4, 2019')])
  });

  it('Delete itemdetail', () => {
    expect(page.deleteitemdetail().isPresent()).toBeFalsy();
  });

  it('Checkout itemdetail amount', () => {
    expect(page.checkoutitemdetail().getText()).toEqual('8');
  });
});



