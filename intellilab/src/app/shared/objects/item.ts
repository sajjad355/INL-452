import { IAngularMyDpOptions, IMyDateModel } from 'angular-mydatepicker';

export class item {
    public amount: number = 0;
    public inuse: number = 0;
    constructor(
        public dbid: number,
        public category: string,
        public cat: string,//manufacturercat
        public suppliercat: string,
        public name: string,
        public type: string,
        public active: boolean,
        public clientspecific: string,
        public supplier: string,
        public manufacturer: string,
        public unit: string,
        public unitsize: string,
        public unitprice: string,
        public quantitythreshold: string,
        public supplylink: string,
        public manufacturerlink: string,
        public lastmodify: Date,
        public modifyperson: string,
        public comment: string

    ) {
        this.amount = 0;
        this.inuse = 0;
    }

    public static newItem(i: item): item {
        let x = new item(
            i.dbid,
            i.category,
            i.cat,
            i.suppliercat,
            i.name,
            i.type,
            i.active,
            i.clientspecific,
            i.supplier,
            i.manufacturer,
            i.unit,
            i.unitsize,
            i.unitprice,
            i.quantitythreshold,
            i.supplylink,
            i.manufacturerlink,
            i.lastmodify,
            i.modifyperson,
            i.comment
        );
        x.amount = i.amount
        x.inuse = i.inuse;
        return x;
    }
}

export class itemdetail {
    public reserveclientname: string;
    public receiveperson:string;
    constructor(
        public dbid: number,
        public itemdbid: number,
        public itemcategory: string,
        public itemname: string,
        public itemtype:string,
        public supplier:string,
        public parentlotnumber: string,
        public lotnumber: string,
        public receivedate: Date,
        public expiredate: Date,

        public retestdate: Date,
        public batchnumber: string,
        public storetemperature: string,
        public purity: string,
        public rfstatus: string,
        public rffile: string,

        public conjugatechemistry: string,
        public biotintobiomoleculeratio: string,
        public descriptionpreparation: string,
        public conjugateprepperson: string,
        public conjugateprepdate: Date,
        public moleculeweight: string,
        public bindingactivity: string,
        public conjugateincorporationratio: string,
        public constatus: string,
        public confile: string,
        public bca: string,
        public runnumber: string,
        public biomoleculeinfo: string,
        public conjugateinfo: string,

        public location: string,
        public sublocation: string,
        public amount: string,
        public inuse: string,
        public concentration: string,
        public concentrationunit: string,

        public species:string,
        public clonality:string,
        public host:string,
        public conjugate:string,
        public iggdepletion:string,
        public purification:string,
        public volume:string,
        public volumeunit:string,
        public weight:string,
        public weightunit:string,
        public apcolumnprepdate:Date,
        public usagecolumn: string,
        public columnnumber: string,

        public comment: string,
        public reserve:string,
        public reserveclientid:string,
        public projectnumber:string,
        public receivepersonid:string,
        public unit:string,
        public unitsize:string,
        public recon:string,
        public modifydate:Date,
        public modifyperson:string
    ) { }

    public static newitemdetail(i: itemdetail): itemdetail {
        let newitemdetail =  new itemdetail(
            i.dbid,
            i.itemdbid,
            i.itemcategory,
            i.itemname,
            i.itemtype,
            i.supplier,
            i.parentlotnumber,
            i.lotnumber,
            i.receivedate,
            i.expiredate,

            i.retestdate,
            i.batchnumber,
            i.storetemperature,
            i.purity,
            i.rfstatus,
            i.rffile,

            i.conjugatechemistry, 
            i.biotintobiomoleculeratio, 
            i.descriptionpreparation,
            i.conjugateprepperson, 
            i.conjugateprepdate, 
            i.moleculeweight,
            i.bindingactivity, 
            i.conjugateincorporationratio,
            i.constatus,
            i.confile,
            i.bca,
            i.runnumber,
            i.biomoleculeinfo,
            i.conjugateinfo,

            i.location,
            i.sublocation,
            i.amount,
            i.inuse,
            i.concentration,
            i.concentrationunit,
            i.species,
            i.clonality,
            i.host,
            i.conjugate,
            i.iggdepletion,
            i.purification,
            i.volume,
            i.volumeunit,
            i.weight,
            i.weightunit,
            i.apcolumnprepdate,
            i.usagecolumn,
            i.columnnumber,
            i.comment,
            i.reserve,
            i.reserveclientid,
            i.projectnumber,
            i.receivepersonid,
            i.unit,
            i.unitsize,
            i.recon,
            i.modifydate,
            i.modifyperson
        );
        newitemdetail.reserveclientname = i.reserveclientname;
        newitemdetail.receiveperson = i.receiveperson;
        return newitemdetail;
    }
}

export class Itemtype {
    constructor(
        public dbid: number,
        public itemtype: string
    ) { }
}

export class ItemWDetails{
    constructor(
        public item:item,
        public details:itemdetail[]
    ){}
}


export class toggleItem {
    public datedateoption: IAngularMyDpOptions
    public sublocationList:string[];
    constructor(
      public item:item,
      public itemdetail: itemdetail,
      public checkpurpose:string,
      public nonopencheckoutamount: string,
      public inusecheckoutamount: string,
      public checkoutLocation:string,
      public checkoutSublocation:string,
      public checkoutAmountValid:boolean,
      public checkoutAmmountErrorMessage:string,
      public reconstituttionInputValid:boolean,
      public reconstituttionInputErrorMessage:string,
      public reconstituion: reconstitution
    ) {    
      this.datedateoption = { 
    //   todayBtnTxt: 'Today',
      dateFormat: 'ddmmmyyyy',
      firstDayOfWeek: 'mo',
      sunHighlight: true,
      inline: false,
      disableUntil: {year: new Date().getFullYear(), month: new Date().getMonth()+1, day: new Date().getDate()-1}
     }
    }
  }

  export class reconstitution{
    constructor(
        public itemdetail:itemdetail, 
        public expiredatedisplay:{ date: { year: number, month: number, day: number } }, 
        public locations:reconLocation[]
    ){

    }
  }

  export class reconLocation{
    public checkoutamount:string=''
    public status:string
    public location:string
    public sublocation:string
    public sublocationList:string[]
      constructor(){}
  }

