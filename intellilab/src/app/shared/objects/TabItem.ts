
export class Tab {
  constructor(public index: number, public name: string) { }
}

export class PageTab {
  public page: Tab[] = [];

  constructor(role: string) {
    this.page = [
      //new Tab(0, 'My Dashboard'),
      new Tab(1, 'Item Inventory'),
      // new Tab(2, 'Equipment'),
      new Tab(2, 'SOP'),
      // new Tab(4, 'Order & Shipment'),
      new Tab(3, 'Kit Assembly'),
      new Tab(4, 'Order Item'),
      // new Tab(7, 'Team Member'),
      // new Tab(8, 'Administrator'),
      new Tab(5, 'Egg Inventory'),
      new Tab(6, 'Config'),

    ]
 
    if (role == 'Administrator' || role == 'Manager' || role == 'Senior Manager') {
      this.page.push(new Tab(7, 'Client'))
      this.page.push(new Tab(8, 'Sales'))
      this.page.push(new Tab(9, 'User History'))
    }
  }
 

  // static getTabByIndex(index: number): Tab {
  //   switch (index) {
  //     case 0:
  //       return new Tab(0, 'My Dashboard');
  //     case 1:
  //       return new Tab(1, 'Item Inventory');
  //     case 2:
  //       return new Tab(2, 'Equipment');
  //     case 3:
  //       return new Tab(3, 'SOP');
  //     case 4:
  //       return new Tab(4, 'Order & Shipment');
  //     case 5:
  //       return new Tab(5, 'Kit Assembly');
  //     case 6:
  //       return new Tab(6, 'Order Item');        
  //     case 7:
  //       return new Tab(7, 'Team Member');
  //     case 8:
  //     return new Tab(8, 'Administrator');    
  //     case 9:
  //     return new Tab(9, 'Egg Inventory'); 
  //     case 10:
  //     return new Tab(10, 'Config');  
  //     case 11:
  //     return new Tab(11, 'Client');  
  //     case 12:
  //     return new Tab(12, 'Sales'); 
  //     case 13:
  //     return new Tab(13, 'User History');              
  //     default:
  //       return;
  //   }
  // }

  static getTabByIndex(index: number): Tab {
    switch (index) {
      case 1:
        return new Tab(1, 'Item Inventory');
      case 2:
        return new Tab(2, 'SOP');
      case 3:
        return new Tab(3, 'Kit Assembly');
      case 4:
        return new Tab(4, 'Order Item');        
      case 5:
      return new Tab(5, 'Egg Inventory'); 
      case 6:
      return new Tab(6, 'Config');  
      case 7:
      return new Tab(7, 'Client');  
      case 8:
      return new Tab(8, 'Sales'); 
      case 9:
      return new Tab(9, 'User History');              
      default:
        return;
    }
  }



}