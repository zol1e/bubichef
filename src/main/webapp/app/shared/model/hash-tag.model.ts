import { IRecept } from 'app/shared/model//recept.model';

export interface IHashTag {
    id?: number;
    nev?: string;
    recepteks?: IRecept[];
}

export class HashTag implements IHashTag {
    constructor(public id?: number, public nev?: string, public recepteks?: IRecept[]) {}
}
