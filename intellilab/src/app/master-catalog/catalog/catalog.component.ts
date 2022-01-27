import { Component, OnInit } from '@angular/core';
import { SettingService2 } from '../../shared/services2/Setting2.service';
import { SettingV2, SettingValueV2 } from '../../shared/objects2/SettingV2';
import { KitService2 } from '../../shared/services2/Kit2.service';
import { ProductService2 } from '../../shared/services2/Product2.service';
import { CatalogService2 } from '../../shared/services2/Catalog2.service';
import { KitV2 } from '../../shared/objects2/KitV2';
import { AuthenticationService2 } from '../../shared/services2/Authenticate2.service';
import { ProductV2 } from '../../shared/objects2/ProductV2';
import { CatalogNumberV2 } from '../../shared/objects2/CatalogNumberV2';
import { ErrorUtil } from 'app/shared/util/ErrorUtil';
import { UserRoleV2, UserV2 } from '../../shared/objects2/UserV2';
import { ErrorService } from '../../page/error/error.service';
import { ThrowStmt } from '@angular/compiler';
import * as lodash from "lodash";



@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.scss']
})
export class CatalogComponent implements OnInit {

  currentUser: UserV2;
  currentUserName: string = '';
  currentUserRoles: UserRoleV2[] = [];

  catType: SettingValueV2[]=[];

  isClientSpecific : boolean = false;
  selectClientName : string = '';
  selectTypeName : string = '';
  hasSpecies : boolean = false;
  selectSpeciesName : string = '';
  isKit: boolean = true;
  showCatalog : boolean = false;
  catalogChangeValid = true;
  catalogNumber: string;
  editedCatalogNumber : string;
  message: string[]=[];



  selectedClient: any = null
  selectedInvenType: any = null
  selectedSpecies: any = null
  selectedCatType: any = null
  selectedKit: any = null
  newCatalogNum: any = null
  productAppendix: any = null
  clients: any[] = [];
  invenTypes: any[] = []
  kitAssociatedProductInvenTypes: any[] = [
    {value: 'ADA Kit or Component'},
    {value: 'PK Kit or Component'},
    {value: 'NAb Kit or Component'},
    {value: 'Characterization Kit'}
  ]
  species: any[];
  kits: any[] = []
  originalKits: any[] = []
  products: any[] = []
  searchResults: any[] = []
  isSearched: boolean = false
  savingItemName: any = null
  savingItemPrice: any = null
  duplicateItemNameCheck: boolean = false
  duplicateCatalogNumberCheck: boolean = false
  allKitsLoaded: boolean = false
  allProductsLoaded: boolean = false
  isLoading: boolean = true




  constructor(private settingService: SettingService2,
              private kitService: KitService2,
              private errorService: ErrorService,
              private authenticationservice: AuthenticationService2,
              private productService: ProductService2,
              private catalogService: CatalogService2) { }

  ngOnInit() {
    this.allKitsLoaded = false
    this.allProductsLoaded = false
    this.componentInitCheck()
    this.authenticationservice.getCurrentUser().then( user => {
        this.currentUser = UserV2.copy(user);
        this.currentUserName = this.currentUser.name;
        this.currentUserRoles = this.currentUser.userRoles;
    });
    this.loadSetting()
    this.initDataPayload()

  }

  initDataPayload(){
    this.resetKitArray()
    this.resetProductArray()
    this.getAllKits()
    this.getAllProducts()
  }

  componentInitCheck(){
    if(this.allKitsLoaded && this.allProductsLoaded){
      this.isLoading = false
      this.mergeProductsToKits()
    }else{
      this.isLoading = true
    }
  }

  mergeProductsToKits(){
    this.products.forEach(product => {
      this.kits.push(product)
    })
    // console.log(`Kits array length after merge: ${this.kits.length}`)
  }

  resetMessage(){
    this.message = []
  }

  resetKitArray(){
    this.kits = []
    this.allKitsLoaded = false
  }

  resetProductArray(){
    this.products = []
    this.allProductsLoaded = false
  }

  // getAllKitsAndProducts(){


  //   this.kitService.count().subscribe(kitCount => {

  //     let numOfKits = kitCount
  //     let totalPage = Math.floor(kitCount / 10)
  //     const remainder = kitCount % 10
  //     if(remainder > 0){
  //       totalPage++
  //     }
  //     console.log(`Kit Total Page: ${totalPage}`)

  //     for(let i = 0; i < totalPage; i++){
  //       const subscription = this.kitService.loadKitByPage(i).subscribe(kits => {
  //         if(subscription){
  //           subscription.unsubscribe()
  //         }
  //         console.log(`Kit Page(${i + 1}): ${kits.length}`)

  //         kits.forEach(kit => {
  //           this.kits.push(kit)
  //         })

  //         if( this.kits.length === numOfKits){
  //           this.originalKits = lodash.cloneDeep(this.kits)
  //           this.allKitsLoaded = true
  //           this.componentInitCheck()
  //           console.log('Original Kits array length: ' + this.originalKits.length)

  //           this.productService.countAllActive(true).subscribe(productCount => {
  //             let numOfProducts = productCount
  //             let totalPage = Math.floor(productCount / 10)
  //             const remainder = productCount % 10
  //             if(remainder > 0){
  //               totalPage++
  //             }
  //             console.log(`Product Total Page: ${totalPage}`)

  //             for(let i = 0; i < totalPage; i++){
  //               const subscription = this.productService.loadProductByPage(i, true).subscribe(products => {
  //                 if(subscription){
  //                   subscription.unsubscribe()
  //                 }
  //                 console.log(`Product Page(${i + 1}): ${products.length}`)
  //                 products.forEach(product => {
  //                   this.products.push(product)
  //                 })

  //                 if( this.products.length === numOfProducts){
  //                   this.allProductsLoaded = true
  //                   this.componentInitCheck()
  //                   console.log(`original products array length: ${this.products.length}`)
  //                 }
  //               })
  //             }
  //           })
  //         }
  //       })
  //     }
  //   })
  // }

  getAllKits(){
    this.resetKitArray()

    this.kitService.count().subscribe(kitCount => {
      let numOfKits = kitCount
      let totalPage = Math.floor(kitCount / 10)
      const remainder = kitCount % 10
      if(remainder > 0){
        totalPage++
      }

      for(let i = 0; i < totalPage; i++){
        const subscription = this.kitService.loadKitByPage(i).subscribe(kits => {
          if(subscription){
            subscription.unsubscribe()
          }

          kits.forEach(kit => {
            this.kits.push(kit)
          })

          if( this.kits.length === numOfKits ){
            this.originalKits = lodash.cloneDeep(this.kits)
            this.allKitsLoaded = true
            this.componentInitCheck()
            // console.log('Original Kits array length: ' + this.originalKits.length)
          }
        })
      }
    })
  }

  getAllProducts(){
    this.resetProductArray()

    this.productService.countAllActive(true).subscribe(productCount => {

      let numOfProducts = productCount
      let totalPage = Math.floor(productCount / 10)
      const remainder = productCount % 10
      if(remainder > 0){
        totalPage++
      }

      for(let i = 0; i < totalPage; i++){
        const subscription = this.productService.loadProductByPage(i, true).subscribe(products => {
          if(subscription){
            subscription.unsubscribe()
          }

          products.forEach(product => {
            this.products.push(product)
          })

          if( this.products.length === numOfProducts ){
            this.allProductsLoaded = true
            this.componentInitCheck()
            // console.log(`Original products array length: ${this.products.length}`)
          }
        })
      }
    })
  }

  checkUserSelectionIntegrity(){
    if(this.selectedClient && this.selectedInvenType && this.selectedSpecies){
      this.generateNewCatalogNum()
    }
  }

  insertNoneOption(array: any[]){
    array.unshift({value: 'None', attribute: -1})
  }

  resetSearchResults(){
    this.searchResults = []
  }

  setClient(client){
    this.selectedClient = client
    // console.log(this.selectedClient)
    this.checkUserSelectionIntegrity()
  }

  resetClient(){
    this.selectedClient = null
  }

  setCatType(changeEvent){
    this.resetUI()
    this.selectedCatType = changeEvent.value
  }

  resetCatType(){
    this.selectedCatType = null
  }

  resetUI(){
    // this.resetMessage()
    this.resetCatType()
    this.resetSearchResults()
    this.resetClient()
    this.resetInvenType()
    this.resetSpecies()
    this.resetNewCatalogNumStr()
    this.resetSearchStatus()
    this.clearSearchResults()
    this.resetKit()
    this.resetProductAppendix()
    this.resetSavingItem()
    this.resetDuplicateItemNameCheck()
    this.resetDuplciateCatalogNumberCheck()
  }

  setDuplciateCatalogNumberCheck(duplicate: boolean){
    this.duplicateCatalogNumberCheck = duplicate
  }

  resetDuplciateCatalogNumberCheck(){
    this.duplicateCatalogNumberCheck = false
  }

  setDuplicateItemNameCheck(duplicate: boolean){
    this.duplicateItemNameCheck = duplicate
  }

  resetDuplicateItemNameCheck(){
    this.duplicateItemNameCheck = false
  }

  setInvenType(changeEvent){
    this.selectedInvenType = changeEvent
    // console.log(this.selectedInvenType)
    this.checkUserSelectionIntegrity()
  }

  resetInvenType(){
    this.selectedInvenType = null
  }

  setSpecies(changeEvent){
    this.selectedSpecies = changeEvent
    // console.log(this.selectedSpecies)
    this.checkUserSelectionIntegrity()
  }

  resetSpecies(){
    this.selectedSpecies = null
  }

  setNewCatalogNumStr(catalogStr: string){
    this.newCatalogNum = catalogStr
  }

  resetNewCatalogNumStr(){
    this.newCatalogNum = ''
  }

  initNewCatalogNumStr(preset?: string){
    if(preset){
      this.newCatalogNum = preset
    }else{
      this.newCatalogNum = 'SB-0'
    }
  }

  setSearchStatus(status: boolean){
    this.isSearched = status
  }

  resetSearchStatus(){
    this.isSearched = false
  }

  clearSearchResults(){
    this.searchResults = []
  }

  setKit(kit){
    this.selectedKit = kit
    this.setNewCatalogNumStr(kit.catalogNumber)
  }

  resetKit(){
    this.selectedKit = null
  }

  resetSavingItem(){
    this.savingItemName = null
    this.savingItemPrice = null
  }

  appendixChange(appendix: string){
    this.setProductAppendix(appendix)
    this.setNewCatalogNumStr(this.selectedKit.catalogNumber)
    this.setNewCatalogNumStr(this.newCatalogNum + appendix)
  }

  setProductAppendix(appendix){
    this.productAppendix = appendix
  }

  resetProductAppendix(){
    this.productAppendix = null
  }


  generateNewCatalogNum(){

    if(this.selectedInvenType && this.selectedClient && this.selectedSpecies){
      this.initNewCatalogNumStr()
      const invenTypeAttribute = this.selectedInvenType.attribute
      const clientAttribute = this.selectedClient.attribute
      const speciesAttribute = this.selectedSpecies.attribute

      //@Desc append client number to catalog string
      if(clientAttribute !== -1){
        this.newCatalogNum = this.newCatalogNum + clientAttribute
      }

      //@Desc append inventory/category type number to catalog string
      this.newCatalogNum = this.newCatalogNum + invenTypeAttribute

      //@Desc append mandatory hyphen to catalog string
      this.newCatalogNum = this.newCatalogNum + '-'

      //@Desc append species number to catalog string
      if(speciesAttribute !== -1){
        this.newCatalogNum = this.newCatalogNum + speciesAttribute
      }else{
        this.newCatalogNum = this.newCatalogNum + '0' // 0 for the None option
      }

      //@Desc append new sequence number to catalog string
      const newSequence = this.detectLatestSequenceNumOfKit() + 1
      // console.log(`Current latest sequence number: ${newSequence - 1}`)
      let sequenceStr = ''
      if(newSequence < 10){
        sequenceStr = sequenceStr + '0' + newSequence
      }else{
        sequenceStr = sequenceStr + newSequence
      }

      // console.log(`New sequence string: ${sequenceStr}`)
      this.newCatalogNum = this.newCatalogNum + sequenceStr
    }
  }

  detectLatestSequenceNumOfKit(){
    let latestSequenceNum = 0
    let loopCount = 0
    this.kits.forEach((kit, index) => {
      loopCount = index
      const sequence = this.extractSequenceNumber(kit)
      if(sequence > latestSequenceNum){
        latestSequenceNum = sequence
      }
    })
    // console.log(`Loop through ${loopCount + 1} kits in total.`)
    return latestSequenceNum
  }

  extractSequenceNumber(kit){
    if(kit.catalogNumber){
      const arr = kit.catalogNumber.split('-')

      if(arr.length >= 3){
        const tempStr = arr[2]
        let numStr = '0'

        for(let i = 1; i < tempStr.length; i++){
          if(!isNaN(parseInt(tempStr.charAt(i)))){
            numStr = numStr + tempStr.charAt(i)
          }
        }

        // console.log(`Original catalog str: ${kit.catalogNumber}, extracted sequence number is: ${parseInt(numStr)}`)
        return parseInt(numStr)
      }else{
        // console.log(`!!! Original catalog str: ${kit.catalogNumber}, fail to extract sequence number`)
        return 0
      }
    }else{
      // console.log(`!!! Original catalog str: ${kit.catalogNumber}, fail to extract sequence number`)
      return 0
    }
  }

  sortKitArray(array: any[]){
    array.sort(
      function (a, b){
        if(a.name.trim().toLowerCase() < b.name.trim().toLowerCase()) { return -1; }
        if(a.name.trim().toLowerCase() > b.name.trim().toLowerCase()) { return 1; }
        return 0;
      }
    )
  }

  //@Desc array must be sorted before using the searchBinary() function
  searchKit(searchKey){
    this.resetKit()
    this.resetSearchResults()
    this.sortKitArray(this.originalKits)
    this.clearSearchResults()
    this.setSearchStatus(true)
    this.searchResults = this.searchBinary(searchKey, this.originalKits)
  }

  searchBinary(needle, array: any[]) {

    const haystack = array
    const case_insensitive = true
    if(needle == "") return [];

    var haystackLength 	= haystack.length;
    // console.log('heystack array')
    // console.log(haystack)
    var letterNumber 	= needle.length;
    needle = (case_insensitive) ? needle.toLowerCase():needle;

    /* start binary search, Get middle position */
    var getElementPosition = findElement()
    // console.log(`getElementPosition: ${getElementPosition}`)
    // console.log(`typeof(haystack) === 'undefined' : ${typeof(haystack) === 'undefined'}`)
    // console.log(`!haystackLength: ${!haystackLength}`)

    /* get interval and return result array */
    if(getElementPosition == -1) return [];

    let getRangeElement = findRangeElement();
    return getRangeElement;

    function findElement() {
      if (typeof(haystack) === 'undefined' || !haystackLength) return -1;

      var high = haystack.length - 1;
      var low = 0;
      // console.log(`low: ${low}, hight: ${high}`)
      while (low <= high) {
        let mid = Math.round((low + high) / 2);
        var element = haystack[mid].name.substr(0,letterNumber);
        element = (case_insensitive) ? element.toLowerCase():element;
        // console.log(`element: ${element}`)

        if (element > needle) {
          high = mid - 1;
          // console.log(`${element}>${needle} -> low(${low}) -  high(${high})`)
        } else if (element < needle) {
          low = mid + 1;
          // console.log(`${element}<${needle} -> low(${low}) -  high(${high})`)
        } else {
          // console.log(`${element}=${needle} -> mid(${mid})`)
          return mid;
        }
      }
      return -1;
    }
    function findRangeElement(){

      for(let i = getElementPosition; i >= 0; i--){
        var element =  (case_insensitive) ? haystack[i].name.substr(0,letterNumber).toLowerCase() : haystack[i].name.substr(0,letterNumber);
        if(element != needle){
          var start = i+1;
          break;
        }else{
          var start = 0;
        }
      }
      for(let i = getElementPosition; i < haystackLength; i++ ){
        var element =  (case_insensitive) ? haystack[i].name.substr(0,letterNumber).toLowerCase() : haystack[i].name.substr(0,letterNumber);
        if(element != needle){
          var end = i-1;
          break;
        }else{
          var end = haystackLength -1;
        }
      }
      var result = [];
      for(let i = start; i <= end; i++){
        result.push(haystack[i])
      }

      return result;
    }

  };

  checkItemNameDuplicate(){
    let duplicate = false
    this.kits.forEach(kit => {
      if(kit.name === this.savingItemName){
        duplicate = true
      }
    })

    // console.log('Kits array length in check item duplicate name:' + this.kits.length)
    // console.log(`duplicate item name check: ${duplicate}`)
    return duplicate
  }

  checkCatalogNumberDuplicate(){
    let duplicate = false
    this.kits.forEach(kit => {
      if(kit.catalogNumber === this.newCatalogNum){
        duplicate = true
      }
    })

    return duplicate
  }

  saveNewCatalog(){

    const itemNameDuplicate = this.checkItemNameDuplicate()
    const itemCatalogDuplicate = this.checkCatalogNumberDuplicate()

    if(this.selectedCatType === '1'){

      if(!itemNameDuplicate && !itemCatalogDuplicate){
        this.addNewKit()
      }else{
        this.setDuplicateItemNameCheck(itemNameDuplicate)
        this.setDuplciateCatalogNumberCheck(itemCatalogDuplicate)
      }

    }else{

      if(!itemNameDuplicate && !itemCatalogDuplicate){
        this.addNewProduct()
      }else{
        this.setDuplicateItemNameCheck(itemNameDuplicate)
        this.setDuplciateCatalogNumberCheck(itemCatalogDuplicate)
      }

    }
  }

  addNewKit() {

    if(this.savingItemName){
      let method='';

      switch (this.selectedInvenType.value) {
        case this.selectedInvenType.value.toLowerCase().includes('ada'):
          method='ADA';
        break;

        case this.selectedInvenType.value.toLowerCase().includes('pk'):
          method='PK';
        break;

        case this.selectedInvenType.value.toLowerCase().includes('antibody'):
          method='Antibody';
        break;

        default:
          method = this.selectedInvenType.value
          break;
      }

      let newKit = new KitV2();

      newKit.name = this.savingItemName;
      newKit.catalogNumber = this.newCatalogNum;
      newKit.editedBy = this.currentUserName;
      newKit.modifiedOn = new Date();
      newKit.enteredBy = this.currentUserName;
      newKit.enteredTime = new Date();
      newKit.active = true;
      if ( this.selectedClient.attribute !== -1 ){
        newKit.client = this.selectedClient.value
      }
      newKit.method = method;
      newKit.packSize = '2 X 96';
      newKit.status = 'Qualified with innovator';
      newKit.editStatus = 'Entered';
      // newKit.unitPrice = Number(this.savingItemPrice);
      newKit.unitPrice = this.savingItemPrice;
      newKit.salesItemId = 0;

      // console.log('New wayn New Kit:')
      // console.log(newKit)
      this.kitService.save(newKit).then(_ =>{
        this.message.push('New Kit is Saved');
        this.resetUI()
        this.initDataPayload()
        // setTimeout(_ => { this.message = [] }, 5000);
      }).catch( error => {
          ErrorUtil.handleHttpError( error );
          this.message.push('System errors, fail to save this item')
          // setTimeout(_ => { this.message = [] }, 5000);
          this.errorService.message$ = error.error;
      });
    }
  }


  addNewProduct() {

    if(this.savingItemName){
        let type='';


        if(this.selectedInvenType.value.toLowerCase().includes('ada') || this.selectedInvenType.value.toLowerCase().includes('pk')){
          type = 'Associated kit component';
        }
        else{
          type = this.selectedInvenType.value
        }

        let newProduct = new ProductV2();

        newProduct.name = this.savingItemName;
        newProduct.type = type;
        newProduct.catalogNumber = this.newCatalogNum;
        newProduct.editedBy = this.currentUserName;
        newProduct.modifiedOn = new Date();
        newProduct.enteredBy = this.currentUserName;
        newProduct.enteredTime = new Date();
        newProduct.active = true;
        newProduct.editStatus = 'Entered';
        newProduct.salesItemId = 0;
        newProduct.unitPrice = Number(this.savingItemPrice);

        // console.log('New Way Product')
        // console.log(newProduct)
        this.productService.save(newProduct).then(_ =>{
          if(this.selectedCatType === '2'){
            this.message.push('New Product is Saved');
          }else{
            this.message.push('New Kit Associated Product is Saved');
          }

            this.resetUI()
            this.initDataPayload()
            // setTimeout(_ => { this.message = [] }, 5000);
        }).catch( error => {
            ErrorUtil.handleHttpError( error );
            this.message.push('System errors, fail to save this item')
            // setTimeout(_ => { this.message = [] }, 5000);
            this.errorService.message$ = error.error;
        });
    }
  }

  loadSetting() {
    this.clients = this.settingService.getSetting( 'partnerClients' ).settingValues;
    this.invenTypes = this.settingService.getSetting( 'catalogCategoryType' ).settingValues;
    this.catType = this.settingService.getSetting( 'catalogCategoryType' ).settingValues;
    this.species = this.settingService.getSetting( 'species' ).settingValues;

    const tempArr = []
    // this.insertNoneOption(this.invenTypes)
    this.invenTypes.forEach((category) => {
      // console.log(category.value.toLowerCase())
      if(!category.value.toLowerCase().includes('kit')){
        // console.log(`pushed into temp arr`)
        tempArr.push(category)
      }
    })
    this.invenTypes = lodash.cloneDeep(tempArr)
    this.insertNoneOption(this.clients)
    this.insertNoneOption(this.species)
  }

  // clear() {
  //   this.selectClientName='';
  //   this.selectSpeciesName='';
  //   this.selectTypeName='';
  //   this.catalogNumber = '';
  //   this.isKit = true;
  //   this.hasSpecies=false;
  //   this.isClientSpecific=false;
  //   this.catalogChangeValid=true;

  //   console.log('Clients settings: ')
  //   console.log(this.clients)
  //   console.log('Catagory settings: ')
  //   console.log(this.catType)
  //   console.log('Species settings: ')
  //   console.log(this.species)
  // }

  // isValid(): boolean {
  //   if (!this.selectTypeName) {
  //     return false;
  //   }
  //   if (this.hasSpecies  && !this.selectSpeciesName) {
  //     return false;
  //   }
  //   if (this.isClientSpecific && !this.selectClientName ) {
  //     return false;
  //   }
  //   if(!this.catalogChangeValid){
  //     return false;
  //   }
  //   return true;
  // }

  // generateCatalog() {
  //   if (!this.isValid()) return;
  //   let catalogNumberRequest : CatalogNumberV2  = new CatalogNumberV2();
  //   console.log(`Init catalog number: ${catalogNumberRequest}`)

  //   if (this.isClientSpecific && this.selectClientName ) {
  //     let aClient : SettingValueV2 = this.clients.find(x=>x.value.trim()==this.selectClientName.trim());
  //     catalogNumberRequest.request.clientAttribute = aClient.attribute;
  //     console.log(`client selected(something or nothing): ${catalogNumberRequest}`)
  //   }
  //   let aCatalogType : SettingValueV2 = this.catType.find(x=>x.value.trim()==this.selectTypeName.trim());
  //   catalogNumberRequest.request.catalogCategoryTypeAttribute = aCatalogType.attribute;
  //   console.log(`category type selected(mandatory): ${catalogNumberRequest}`)

  //   if(this.hasSpecies && this.selectSpeciesName) {
  //     let aSpecies : SettingValueV2 = this.species.find(x=>x.value.trim()==this.selectSpeciesName.trim());
  //     catalogNumberRequest.request.speciesAttribute = aSpecies.attribute;
  //     console.log(`species selected(something or 0): ${catalogNumberRequest}`)
  //   }
  //   catalogNumberRequest.request.kit = this.isKit;


  //   this.catalogService.getNextCatalogNumber( catalogNumberRequest ).then( (catNo) =>{
  //     let catalogNumberResponse : CatalogNumberV2 = catNo as CatalogNumberV2;
  //     this.catalogNumber = catalogNumberResponse.response.catalogNumber;
  //     this.editedCatalogNumber = this.catalogNumber;


  //   }).catch( error => {
  //     ErrorUtil.handleHttpError( error );
  //     this.errorService.message$ = error.error;
  //   });



  // }

  // addKitWithCat(name: string, price: number) {

  //   if (name == undefined || name.trim() == '' || isNaN(price)) {
  //     this.message.push( 'Please make sure below fields are not empty or blank!' );
  //     return;
  //   }

  //   let method='';

  //   if(this.selectTypeName.includes('ADA')){
  //     method='ADA';
  //   }
  //   else if(this.selectTypeName.includes('PK')){
  //     method='PK';
  //   }
  //   else if(this.selectTypeName.includes('Antibody')){
  //     method='Antibody';
  //   }
  //   else{
  //     method=this.selectTypeName;
  //   }


  //   let newKit = new KitV2();

  //   newKit.name = name;
  //   newKit.catalogNumber = this.editedCatalogNumber;
  //   newKit.editedBy = this.currentUserName;
  //   newKit.modifiedOn = new Date();
  //   newKit.enteredBy = this.currentUserName;
  //   newKit.enteredTime = new Date();
  //   newKit.active = true;
  //   if ( this.selectClientName ) newKit.client = this.selectClientName;
  //   newKit.method = method;
  //   newKit.packSize = '2 X 96';
  //   newKit.status = 'Qualified with innovator';
  //   newKit.editStatus = 'Entered';
  //   newKit.unitPrice = price;
  //   newKit.salesItemId = 0;
  //   console.log('Old New Kit:')
  //   console.log(newKit)

  //   this.kitService.save(newKit).then(_ =>{
  //       this.message.push('Successly Added a New Kit!');
  //       setTimeout(_ => { this.message = [] }, 5000);
  //   }).catch( error => {
  //       ErrorUtil.handleHttpError( error );
  //       this.errorService.message$ = error.error;
  //   });


  //   this.clear();
  // }

  // checkCatalog(){
  //   if(!this.editedCatalogNumber.startsWith(this.catalogNumber)){
  //     this.catalogChangeValid=false;
  //   }
  //   else{
  //     this.catalogChangeValid=true;
  //   }
  // }

  // addProductWithCat(name: string, price: number) {
  //   if (name == undefined || name.trim() == '') {
  //     this.message.push('Please make sure below fields are not empty or blank!');
  //     return;
  //   }

  //   let type='';
  //   if(this.selectTypeName.includes('ADA') || this.selectTypeName.includes('PK')){
  //     type = 'Associated kit component';
  //   }
  //   else{
  //     type=this.selectTypeName;
  //   }

  //   let newProduct = new ProductV2();
  //   newProduct.name = name;
  //   newProduct.type = type;
  //   newProduct.catalogNumber = this.editedCatalogNumber;
  //   newProduct.catalogNumber = 'SB-01-0505'
  //   newProduct.editedBy = this.currentUserName;
  //   newProduct.modifiedOn = new Date();
  //   newProduct.enteredBy = this.currentUserName;
  //   newProduct.enteredTime = new Date();
  //   newProduct.active = true;
  //   newProduct.editStatus = 'Entered';
  //   newProduct.salesItemId = 0;
  //   newProduct.unitPrice = price;

  //   console.log('Old Way New Product')
  //   console.log(newProduct)
  //   this.productService.save(newProduct).then(_ =>{
  //       this.message.push('Successly Added a New Product!');
  //       setTimeout(_ => { this.message = [] }, 5000);
  //   }).catch( error => {
  //       ErrorUtil.handleHttpError( error );
  //       this.errorService.message$ = error.error;
  //   });

  //   this.clear();

  // }


}


