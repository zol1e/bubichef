import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BubichefSharedModule } from 'app/shared';
import {
    ReceptToOsszetevoComponent,
    ReceptToOsszetevoDetailComponent,
    ReceptToOsszetevoUpdateComponent,
    ReceptToOsszetevoDeletePopupComponent,
    ReceptToOsszetevoDeleteDialogComponent,
    receptToOsszetevoRoute,
    receptToOsszetevoPopupRoute
} from './';

const ENTITY_STATES = [...receptToOsszetevoRoute, ...receptToOsszetevoPopupRoute];

@NgModule({
    imports: [BubichefSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReceptToOsszetevoComponent,
        ReceptToOsszetevoDetailComponent,
        ReceptToOsszetevoUpdateComponent,
        ReceptToOsszetevoDeleteDialogComponent,
        ReceptToOsszetevoDeletePopupComponent
    ],
    entryComponents: [
        ReceptToOsszetevoComponent,
        ReceptToOsszetevoUpdateComponent,
        ReceptToOsszetevoDeleteDialogComponent,
        ReceptToOsszetevoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefReceptToOsszetevoModule {}
