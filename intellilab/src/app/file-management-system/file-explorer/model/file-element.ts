
export class FileElement {
  _id?: string;
  isFolder: boolean;
  name: string;
  parent: string;
  info?: {
    _id?: number;
    type: string;
    size: number;
    owner: string;
    date: Date;
    version: number;
    ancestor?: string;
    offspring?: string;
    source: string;
    address: string;
  }
}
