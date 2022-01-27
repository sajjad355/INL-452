export class Eggtransfer {
    constructor(
        public dbid: number,
        public chickenid: string,
        public eggdbid: string,
        public action: string,
        public egguseamount: string,
        public destinationtable: string,
        public destinationdbid: string,
        public editby:string,
        public modify:Date
    ) 
        {}

    public static newEggtransfer(e: Eggtransfer) {
        let neweggtransfer = new Eggtransfer(e.dbid, e.chickenid, e.eggdbid, e.action, e.egguseamount, e.destinationtable, e.destinationdbid, e.editby, e.modify);
        return neweggtransfer;
    }

    public static parseFromJSON(eggtransfer: { dbid: number, chickenid: string, eggdbid: string, action: string, egguseamount: string, destinationtable: string, destinationdbid: string, editby:string, modify:Date}) {
        return new Eggtransfer(eggtransfer.dbid, eggtransfer.chickenid, eggtransfer.eggdbid, eggtransfer.action, eggtransfer.egguseamount, eggtransfer.destinationtable, eggtransfer.destinationdbid, eggtransfer.editby, eggtransfer.modify);
    }
}