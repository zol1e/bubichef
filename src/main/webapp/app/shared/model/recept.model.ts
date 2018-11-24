import { Moment } from 'moment';
import { IHashTag } from 'app/shared/model//hash-tag.model';
import { IReceptToOsszetevo } from 'app/shared/model//recept-to-osszetevo.model';

export interface IRecept {
    id?: number;
    nev?: string;
    leiras?: string;
    kepContentType?: string;
    kep?: any;
    feltoltve?: Moment;
    kategoriaId?: number;
    hashtageks?: IHashTag[];
    osszetevoks?: IReceptToOsszetevo[];
}

export class Recept implements IRecept {
    constructor(
        public id?: number,
        public nev?: string,
        public leiras?: string,
        public kepContentType?: string,
        public kep?: any,
        public feltoltve?: Moment,
        public kategoriaId?: number,
        public hashtageks?: IHashTag[],
        public osszetevoks?: IReceptToOsszetevo[]
    ) {}
}
