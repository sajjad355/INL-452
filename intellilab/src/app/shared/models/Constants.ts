import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';


// Keep these names in sync with those in il_operation
export const OPERATION_NAMES = {
  VIEW_CATALOGS: 'View Catalogs',
  VIEW_CLIENTS : 'View Clients',
  MANAGE_CLIENTS : 'Manage Clients',
  VIEW_SALES : 'View Sales',
  MANAGE_SALES : 'Manage Sales',
  VIEW_USER_HISTORY : 'View User History',
  VIEW_USERS : 'View Users',
  MANAGE_USERS :  'Manage Users',
  GENERATE_CATAOLOGS : 'Generate Catalogs',
  VIEW_INVENTORY : 'View Inventory',
  MANAGE_INVENTORY : 'Manage Inventory',
  VIEW_IMMUNIZING_SCHEDULES : 'View Immunizing Schedules',
  MANAGE_IMMUNIZING_SCHEDULES : 'Manage Immunizing Schedules',
  VIEW_SOPS : 'View SOPs',
  EDIT_SOPS : 'Edit SOPs',
  REVIEW_SOPS : 'Review SOPs',
  VIEW_KITS : 'View Kits',
  EDIT_KITS : 'Edit Kits',
  REVIEW_KITS : 'Review Kits',
  VIEW_ORDERS :  'View Orders',
  REQUEST_ORDERS : 'Request Orders' ,
  MANAGE_ORDERS : 'Manage Orders',
  RECEIVE_ORDERS: 'Receive Orders',
  VIEW_PRODUCTS : 'View Products',
  MANAGE_PRODUCTS : 'Manage Products',
  REVIEW_PRODUCTS : 'Review Products',
  VIEW_EQUIPMENT : 'View Equipment',
  MANAGE_EQUIPMENT : 'Manage Equipment',
  REVIEW_EQUIPMENT : 'Review Equipment',
  VIEW_WORKSHEETS : 'View Worksheets', // see the link to worksheets and at least be able to read/view
  EDIT_WORKSHEET_DESIGN : 'Edit Worksheet Design', // create/update/delete worksheet designs
  EXECUTE_WORKSHEET_INSTANCE : 'Execute Worksheet Instance', // create and update worksheet instance
  REVIEW_WORKSHEETS : 'Review Worksheets', // approve or reject
  VIEW_CONFIG_AND_LOCATIONS : 'View Config and Locations',
  EDIT_CONFIG_AND_LOCATIONS : 'Edit Config and Locations',
  VIEW_FOLDERS : 'View Folders',
  MANAGE_FOLDERS : 'Manage Folders',
  ACCESS_FMS: 'Access FMS'
};

// A component animation array
export const ANIMATIONS = [
  trigger('routeAnimation', [
    state('true', style({
      backgroundColor: 'lightblue'
    })),
    transition('false => true', [
      animate('700ms 300ms', keyframes ([
        style({ opacity: 0.1, offset: 0.07 }),
        style({ opacity: 0.6, offset: 0.14 }),
        style({ opacity: 1,   offset: 0.35 }),
        style({ opacity: 0.2, offset: 0.49 }),
        style({ opacity: 1, backgroundColor: 'white', offset: 0.7}),
        style({ backgroundColor: 'lightblue', offset: 1})
      ]))
    ])
  ])
];

// File 'types' that can be uploaded to the inventory
export const enum INVENTORY_DETAIL_FILE_TYPES {
  FILE = 'file',
  RF_FILE = 'rfFile'
}

// Inventory item types
export const INVENTORY_ITEM_TYPES = {
  ANTI_SERA: 'Anti-Sera',
  SOLUTION: 'Solution',
  MATRIX: 'Matrix',
  CHEMICAL: 'Chemical',
  DRUG: 'Drug',
  CONSUMABLE: 'Consumable',
  MOLECULAR: 'Molecular',
  CELL_CULTURE: 'Cell Culture',
  KIT: 'Kit',
  COMMERCIAL_PROTEIN: 'Commercial Protein',
  IN_HOUSE_PROTEIN: 'In-House Protein'
};

export const COMMERCIAL_TYPES = {
  UNCONJUGATED_PROTEIN: 'Protein - Unconjugated',
  CONJUGATED_PROTEIN: 'Protein - Conjugated',
  ENZYME: 'Enzyme',
  UNCONJUGATED_ANTIBODY: 'Antibody - Unconjugated',
  CONJUGATED_ANTIBODY: 'Antibody - Conjugated'
};

export const IN_HOUSE_TYPES = {
  CONJUGATED_PROTEIN: 'Protein - Conjugated',
  UNCONJUGATED_ANTIBODY: 'Antibody - Unconjugated',
  CONJUGATED_ANTIBODY: 'Antibody - Conjugated'
};



export const CHECKOUT_TYPE = {
  UNOPENED : 'Unopened',
  INUSE : 'Inuse'
}

export const CHECKOUT_PURPOSE = {
  USE :  'Use',
  MOVE : 'Move',
  SHIP : 'Ship'
}


export const VIAL_DETAIL_STATUS = {
  UNOPENED : 'Unopened',
  INUSE : 'Inuse'
}

export const DRUG_LOT_TYPES = {
  MANUFACTURER_STOCK : 'Manufacturer Stock',
  INHOUSE_ALIQUOT : 'In-House Aliquot'
}

export const HAZARD_LEVELS = {
  UNKNOWN : 'unknown',
  LOW : 'low',
  MEDIUM : 'medium',
  HIGH : 'high'
}

export const ORDER_STATUS = {
  RECEIVED : 'Received',
  ORDERED  : 'Ordered',
  APPROVED : 'Approved',
  REQUESTED : 'Requested',
  CANCELLED : 'Cancelled',
  REJECTED  : 'Rejected'
}

export const INVOICE_TYPE = {
  PRODUCT_INVOICE : 'Product Invoice',
  SERVICE_INVOICE : 'Service Invoice',
  COMMERCIAL_INVOICE : 'Commercial Invoice'
}


