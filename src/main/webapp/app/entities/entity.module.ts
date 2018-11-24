import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BubichefReceptModule } from './recept/recept.module';
import { BubichefKategoriaModule } from './kategoria/kategoria.module';
import { BubichefReceptToOsszetevoModule } from './recept-to-osszetevo/recept-to-osszetevo.module';
import { BubichefOsszetevoModule } from './osszetevo/osszetevo.module';
import { BubichefHashTagModule } from './hash-tag/hash-tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BubichefReceptModule,
        BubichefKategoriaModule,
        BubichefReceptToOsszetevoModule,
        BubichefOsszetevoModule,
        BubichefHashTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefEntityModule {}
