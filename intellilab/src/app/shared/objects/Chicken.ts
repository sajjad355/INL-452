export class Chicken {
    constructor(
        public dbid: number,
        public chickenid: string,
        public dateofbirth: Date,
        public immunogen: string,
        public projectname: string,
        public chickenstatus: string,
        public totalegg: string,
        public eggused: string,
        public eggdiscard: string,
        public latesttiter: string,
        public titerdate: Date,
        public editby:string,
        public modify:Date,
        public sequence:number,
        public immunstatus:string
    ) 
        {}

    public static newChiken(c: Chicken) {
        let newchicken = new Chicken(c.dbid, c.chickenid, c.dateofbirth, c.immunogen, c.projectname, c.chickenstatus, c.totalegg, c.eggused, c.eggdiscard, c.latesttiter, c.titerdate,  c.editby, c.modify, c.sequence, c.immunstatus);
        return newchicken;
    }

    public static parseFromJSON(chicken: { dbid: number, chickenid: string, dateofbirth: Date, immunogen: string, projectname: string, chickenstatus: string, totalegg:string, eggdiscard:string, eggused:string, latesttiter:string, titerdate:Date, editby:string, modify:Date, sequence:number, immunstatus:string}) {
        return new Chicken(chicken.dbid, chicken.chickenid, chicken.dateofbirth, chicken.immunogen, chicken.projectname, chicken.chickenstatus, chicken.totalegg, chicken.eggused, chicken.eggdiscard, chicken.latesttiter, chicken.titerdate, chicken.editby, chicken.modify, chicken.sequence, chicken.immunstatus);
    }
}

export class Injection {
    constructor(
        public dbid: number, 
        public chickenid: string, 
        public adjuvant: string, 
        public injectdate: Date,
        public daysafterimmune:string,
        public drugamount: string,
        public unit:string,
        public bloodtiter:string,
        public complete:string,
        public editby:string,
        public modify:Date,
    ) { }
}

export class Egg {
    constructor(
        public dbid: number, 
        public chickenid: string, 
        public immunogen:string,
        public eggpart:string,
        public collectiondate: Date,
        public firstinjectdate:Date,
        public daysafterimmune:string,
        public firstlayeggdate: Date,  
        public lastlayeggdate: Date,              
        public amount: string, 
        public location: string, 
        public sublocation: string,
        public frozendate:Date,
        public addsucrose:string,
        public titer:string,
        public editby:string,
        public modify:Date
    ) { }

    public static newEgg(e: Egg) {
        return new Egg(e.dbid, e.chickenid, e.immunogen, e.eggpart, e.collectiondate, e.firstinjectdate, e.daysafterimmune, e.firstlayeggdate, e.lastlayeggdate, e.amount, e.location, e.sublocation, e.frozendate, e.addsucrose, e.titer, e.editby, e.modify);
    }
}

export class toggleEgg {
    constructor(
        public dbid: number,
        public chickenid: string, 
        public immunogen:string,
        public eggpart:string,
        public collectiondate: Date,
        public firstinjectdate:Date,
        public daysafterimmune:string,
        public firstlayeggdate: Date,  
        public lastlayeggdate: Date,              
        public amount: string, 
        public location: string, 
        public sublocation: string,
        public frozendate:Date,
        public addsucrose:string,
        public titer:string,
        public consumeeggnumber:string,
        public saveyolknumber:string
    ) { }
  }

export class ChickenWEggs{
    constructor(
        public chick:Chicken,
        public eggs:Egg[]
    ){}
}