export class CatalogNumberV2 {
  request : CatalogNumberRequest;
  response : CatalogNumberResponse;
  constructor() {
    this.request = new CatalogNumberRequest();
    this.response = new CatalogNumberResponse(); 
  }
}

export class CatalogNumberRequest {
  clientAttribute : string;
  catalogCategoryTypeAttribute : string;
  speciesAttribute : string;
  kit : boolean;
  constructor() {
    this.clientAttribute = '';
    this.catalogCategoryTypeAttribute = '';
    this.speciesAttribute = '';
    this.kit = false;
  }
}

export class CatalogNumberResponse {
  catalogNumber : string;
  catalogNumberType : string;
  constructor() {
    this.catalogNumberType = '';
    this.catalogNumber = '';
  }
}


