import { IReceptToOsszetevo } from 'app/shared/model//recept-to-osszetevo.model';

export interface IOsszetevo {
    id?: number;
    nev?: string;
    leiras?: string;
    kepContentType?: string;
    kep?: any;
    recepteks?: IReceptToOsszetevo[];
}

export class Osszetevo implements IOsszetevo {
    constructor(
        public id?: number,
        public nev?: string,
        public leiras?: string,
        public kepContentType?: string,
        public kep?: any,
        public recepteks?: IReceptToOsszetevo[]
    ) {}
}
