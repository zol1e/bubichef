export interface IReceptToOsszetevo {
    id?: number;
    mennyiseg?: string;
    megjegyzes?: string;
    receptId?: number;
    osszetevoId?: number;
}

export class ReceptToOsszetevo implements IReceptToOsszetevo {
    constructor(
        public id?: number,
        public mennyiseg?: string,
        public megjegyzes?: string,
        public receptId?: number,
        public osszetevoId?: number
    ) {}
}
