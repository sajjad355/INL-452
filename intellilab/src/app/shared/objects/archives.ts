export class Itemarchive {
    public amount: number = 0;
    public inuse: number = 0;
    constructor(
        public itemarchivedbid: number,
        public action: string,
        public createtime: Date,
        public createperson: string,
        public dbid: number,
        public category: string,//manufacturercat
        public cat: string,//manufacturercat
        public suppliercat: string,
        public name: string,
        public type: string,
        public active: boolean,
        public clientspecific: string,
        public supplier: string,
        public manufacturer: string,
        public unit: string,
        public unitprice: string,
        public unitsize: string,
        public quantitythreshold: string,
        public supplylink: string,
        public manufacturerlink: string,
        public lastmodify: Date,
        public modifyperson: string,
        public comment: string

    ) {}

    public static newItemarchive(i: Itemarchive): Itemarchive {
        let x = new Itemarchive(
            i.itemarchivedbid,
            i.action,
            i.createtime,
            i.createperson,
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
        return x;
    }
}

export class ItemChange {
    constructor(
        public cat: string,//manufacturercat
        public suppliercat: string,
        public name: string,
        public type: string,
        public active: string,
        public clientspecific: string,
        public supplier: string,
        public manufacturer: string,
        public unit: string,
        public unitprice: string,
        public unitsize: string,
        public quantitythreshold: string,
        public supplylink: string,
        public manufacturerlink: string,
        public lastmodify: string,
        public modifyperson: string,
        public comment: string
    ) {}

}



export class Itemdetailarchive {
    constructor(
        public itemdetailarchivedbid: number,
        public action: string,
        public createtime: Date,
        public createperson: string,
        public dbid: number,
        public itemdbid: number,
        public itemname: string,
        public itemtype:string,
        public supplier:string,
        public parentlotnumber: string,
        public lotnumber: string,
        public receivedate: Date,
        public expiredate: Date,
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
        public projectnumber:string,
        public receiveperson:string,
        public unit:string,
        public unitsize:string,
        public recon:string,
        public modifydate:Date,
        public modifyperson:string
    ) { }

    public static newItemdetailarchive(i: Itemdetailarchive): Itemdetailarchive {
        return new Itemdetailarchive(
            i.itemdetailarchivedbid,
            i.action,
            i.createtime,
            i.createperson,
            i.dbid,
            i.itemdbid,
            i.itemname,
            i.itemtype,
            i.supplier,
            i.parentlotnumber,
            i.lotnumber,
            i.receivedate,
            i.expiredate,
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
            i.projectnumber,
            i.receiveperson,
            i.unit,
            i.unitsize,
            i.recon,
            i.modifydate,
            i.modifyperson
        );
    }
}

export class ItemdetailChange {
    constructor(
        public itemdbid: string,
        public itemname: string,
        public itemtype:string,
        public supplier:string,
        public parentlotnumber: string,
        public lotnumber: string,
        public receivedate: string,
        public expiredate: string,
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
        public apcolumnprepdate:string,
        public usagecolumn:string,
        public columnnumber:string,
        public comment: string,
        public reserve:string,
        public projectnumber:string,
        public receiveperson:string,
        public unit:string,
        public unitsize:string,
        public recon:string,
        public modifydate:string,
        public modifyperson:string
    ) { }

}