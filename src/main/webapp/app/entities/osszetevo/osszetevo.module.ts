import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BubichefSharedModule } from 'app/shared';
import {
    OsszetevoComponent,
    OsszetevoDetailComponent,
    OsszetevoUpdateComponent,
    OsszetevoDeletePopupComponent,
    OsszetevoDeleteDialogComponent,
    osszetevoRoute,
    osszetevoPopupRoute
} from './';

const ENTITY_STATES = [...osszetevoRoute, ...osszetevoPopupRoute];

@NgModule({
    imports: [BubichefSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OsszetevoComponent,
        OsszetevoDetailComponent,
        OsszetevoUpdateComponent,
        OsszetevoDeleteDialogComponent,
        OsszetevoDeletePopupComponent
    ],
    entryComponents: [OsszetevoComponent, OsszetevoUpdateComponent, OsszetevoDeleteDialogComponent, OsszetevoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefOsszetevoModule {}
