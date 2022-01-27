export class Barcode {
    constructor(
        public dbid: number,
        public itemdetaildbid: number,
        public barcode: string,
        public usestatus: string,
        public editby: string,
        public modify: Date,
    ) { }

    public static newhistory(b: Barcode): Barcode {
        return new Barcode(b.dbid, b.itemdetaildbid, b.barcode, b.usestatus, b.editby, b.modify);
    }
}