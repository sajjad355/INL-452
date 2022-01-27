import { browser, element, by } from 'protractor';
import protractor = require('protractor')
export class Inventory {

  getInventoryText() {
    return element(by.css('app-Inventory h2')).getText();
  }

  checkAllDefault(){
    return element(by.css('[id="all"]'));
  }

  checkCat(){
    return element(by.css('[id="cat"]')).click();
  }

  checkname(){
    element(by.css('[id="name"]')).click();
    return element(by.css('[id="name"]'))
  }

  checkLocation(){
    element(by.css('[id="location"]')).click();
    return element(by.css('[id="location"]'))
  }

  checkSublocation(){
    element(by.css('[id="sublocation"]')).click();
    return element(by.css('[id="sublocation"]'))
  }

  searchInventory() {
    element(by.css('[id="searchbar"]')).clear();
    element(by.css('[id="searchbar"]')).sendKeys('fdgdfa');
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    let itemname= element(by.css('[class="itemname master"]')).getText();
    return itemname;
  }

  createnewitem(){
    element(by.css('[id="createnewitem"]')).click();
    //browser.sleep(20000)
    //browser.wait(function() {return element(by.css('[id="newitem"]')).isPresent();}).then(function () {return element(by.css('[id="newitem"]'));});
    browser.wait(function () {return element(by.css('[id="newitem"]')).isDisplayed();}, 3000);
    // browser.wait(element(by.css('[id="newitem"]')).isPresent(), 2000);
    // let itemwindow = element(by.css('[id="newitem"]'));
    // return itemwindow;
    return element(by.css('[id="newitem"]'));
  }

  savenewitem(){
    element(by.css('[id="itemnameinput"]')).clear();
    element(by.css('[id="itemnameinput"]')).sendKeys('e2etest');
    element(by.css('[id="itemsupplierinput"]')).clear();
    element(by.css('[id="itemsupplierinput"]')).sendKeys('e2etest');
    element(by.css('[id="itemmanufacturercatinput"]')).clear();
    element(by.css('[id="itemmanufacturercatinput"]')).sendKeys('e2etest');
    element(by.css('[id="itemunitoption"]')).element(by.cssContainingText('option', 'µL')).click();
    element(by.css('[id="itemtypeoption"]')).element(by.cssContainingText('option', 'Cell Line')).click();
    element(by.css('[id="saveitem"]')).click();
    browser.wait(element(by.css('[class="itemname master"]')).isPresent(), 1000);
    element.all(by.css('[class="itemname master"]')).first().click();
    let elementarray=[];
    elementarray.push(element(by.css('[id="itemname"]')));
    elementarray.push(element(by.css('[id="itemsupplier"]')))
    elementarray.push(element(by.css('[id="itemmanufacturercat"]')))
    elementarray.push(element(by.css('[id="itemunit"]')))
    elementarray.push(element(by.css('[id="itemtype"]')))
    
    return elementarray;
  }

  openiteminfor(){
    element.all(by.css('[class="itemname master"]')).first().click();
    browser.wait(function () {return element(by.css('[id="itemname"]')).isPresent();}, 3000);
    return element.all(by.css('[id="itemname"]')).first()
  }

  edititem(){
    element.all(by.css('[class="itemname master"]')).first().click();
    element(by.css('[id="edititembutton"]')).click();
    element(by.css('[id="itemnameinput"]')).clear();
    element(by.css('[id="itemnameinput"]')).sendKeys('e2etest2');
    element(by.css('[id="itemsupplierinput"]')).clear();
    element(by.css('[id="itemsupplierinput"]')).sendKeys('e2etest2');
    element(by.css('[id="itemmanufacturercatinput"]')).clear();
    element(by.css('[id="itemmanufacturercatinput"]')).sendKeys('e2etest2');
    element(by.css('[id="itemunitoption"]')).element(by.cssContainingText('option', 'mL')).click();
    element(by.css('[id="itemtypeoption"]')).element(by.cssContainingText('option', 'Media')).click();
    element(by.css('[id="saveitem"]')).click();
    browser.wait(element(by.css('[class="itemname master"]')).isPresent(), 1000);
    element.all(by.css('[class="itemname master"]')).first().click();
    let elementarray=[];
    elementarray.push(element(by.css('[id="itemname"]')));
    elementarray.push(element(by.css('[id="itemsupplier"]')))
    elementarray.push(element(by.css('[id="itemmanufacturercat"]')))
    elementarray.push(element(by.css('[id="itemunit"]')))
    elementarray.push(element(by.css('[id="itemtype"]')))
  
    return elementarray;
  }

  deleteitem(){
    element(by.css('[id="deleteitembutton"]')).click();
    element(by.css('[id="confirmdelete"]')).click();
    return element.all(by.css('[class="itemname master"]')).first();

  }

  createnewitemdetail(){
    browser.sleep(2000)
    element.all(by.css('[class="itemname master"]')).first().click();
    browser.sleep(2000)
    element(by.css('[id="newitemdetail"]')).click();
    browser.sleep(2000)
    element(by.css('[id="itemdetaillotinput"]')).clear();
    element(by.css('[id="itemdetaillotinput"]')).sendKeys('123456');
    element(by.css('[id="itemdetailamountinput"]')).clear();
    element(by.css('[id="itemdetailamountinput"]')).sendKeys('10');
    element(by.css('[id="itemdetailunitoption"]')).element(by.cssContainingText('option', 'µL')).click();
    element(by.css('[id="itemdetaillocationoption"]')).element(by.cssContainingText('option', '-80 Freezer #1')).click();
    //element(by.css('[id="itemdetailpredate"]')).click();
    browser.sleep(2000)
    element.all(by.css('[class="mydpicon icon-mydpremove"]')).first().click();
    element.all(by.css('[class="selection ng-untouched ng-pristine ng-valid"]')).first().sendKeys('03/27/2018');
    browser.sleep(2000)
    element(by.css('[id="saveitemdetail"]')).click();
    browser.sleep(2000)
    element(by.css('[id="0 0"]')).click();
    browser.sleep(2000)

    let elementarray=[];
    elementarray.push(element(by.css('[id="itemdetaillot"]')));
    elementarray.push(element(by.css('[id="itemdetailamount"]')))
    elementarray.push(element(by.css('[id="itemdetailunit"]')))
    elementarray.push(element(by.css('[id="itemdetaillocation"]')))
    elementarray.push(element(by.css('[id="itemdetailpredate"]')))
  
    return elementarray;
  }

  edititemdetail(){
    browser.sleep(2000)
    element(by.css('[id="edititemdetail"]')).click();
    browser.sleep(2000)
    element(by.css('[id="itemdetaillotinput"]')).clear();
    element(by.css('[id="itemdetaillotinput"]')).sendKeys('567890');
    element(by.css('[id="itemdetailamountinput"]')).clear();
    element(by.css('[id="itemdetailamountinput"]')).sendKeys('20');
    element(by.css('[id="itemdetailunitoption"]')).element(by.cssContainingText('option', 'mL')).click();
    element(by.css('[id="itemdetaillocationoption"]')).element(by.cssContainingText('option', '-20 Freezer #1')).click();
    //element(by.css('[id="itemdetailpredate"]')).click();
    browser.sleep(2000)
    element.all(by.css('[class="mydpicon icon-mydpremove"]')).first().click();
    element.all(by.css('[class="selection ng-untouched ng-pristine ng-valid"]')).first().sendKeys('05/27/2018');
    browser.sleep(2000)
    element(by.css('[id="saveitemdetail"]')).click();
    browser.sleep(2000)
    element(by.css('[id="0 0"]')).click();
    browser.sleep(2000)

    let elementarray=[];
    elementarray.push(element(by.css('[id="itemdetaillot"]')));
    elementarray.push(element(by.css('[id="itemdetailamount"]')))
    elementarray.push(element(by.css('[id="itemdetailunit"]')))
    elementarray.push(element(by.css('[id="itemdetaillocation"]')))
    elementarray.push(element(by.css('[id="itemdetailpredate"]')))
  
    return elementarray;
  }

  deleteitemdetail(){
    browser.sleep(2000)
    element(by.css('[id="deleteitemdetail"]')).click();
    element(by.css('[id="confirmdelete"]')).click();
    browser.sleep(2000)
    return element.all(by.css('[id="0 0"]'));
  }

  checkoutitemdetail(){
    element(by.css('[id="show 1"]')).click();
    element(by.css('[id="select 1 0"]')).click();
    browser.sleep(20000)
    element(by.css('[id="checkinout"]')).click();
    element(by.css('[id="selectunopen 0"]')).click();
    element(by.css('[id="decreaseamount 0"]')).click();
    element(by.css('[id="savecheckoutchange"]')).click();
    return element(by.css('[id="itemdetailamount 1 0"]'));
  }

  reconitemdetail(){

  }

  openitemdetail(){
    let itemname=element(by.css('[class="itemname master"]')).getText();
    element(by.css('[class="itemname detail"]')).click();
  }





}




