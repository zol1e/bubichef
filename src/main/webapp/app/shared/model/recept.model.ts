import { Moment } from 'moment';
import { IReceptToOsszetevo } from 'app/shared/model//recept-to-osszetevo.model';
import { IHashTag } from 'app/shared/model//hash-tag.model';

export interface IRecept {
    id?: number;
    nev?: string;
    leiras?: string;
    kepContentType?: string;
    kep?: any;
    feltoltve?: Moment;
    osszetevoks?: IReceptToOsszetevo[];
    kategoriaId?: number;
    hashtageks?: IHashTag[];
}

export class Recept implements IRecept {
    constructor(
        public id?: number,
        public nev?: string,
        public leiras?: string,
        public kepContentType?: string,
        public kep?: any,
        public feltoltve?: Moment,
        public osszetevoks?: IReceptToOsszetevo[],
        public kategoriaId?: number,
        public hashtageks?: IHashTag[]
    ) {}
}
