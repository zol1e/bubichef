export interface IKategoria {
    id?: number;
    nev?: string;
}

export class Kategoria implements IKategoria {
    constructor(public id?: number, public nev?: string) {}
}
