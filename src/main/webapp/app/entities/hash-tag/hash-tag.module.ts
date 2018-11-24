import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BubichefSharedModule } from 'app/shared';
import {
    HashTagComponent,
    HashTagDetailComponent,
    HashTagUpdateComponent,
    HashTagDeletePopupComponent,
    HashTagDeleteDialogComponent,
    hashTagRoute,
    hashTagPopupRoute
} from './';

const ENTITY_STATES = [...hashTagRoute, ...hashTagPopupRoute];

@NgModule({
    imports: [BubichefSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HashTagComponent,
        HashTagDetailComponent,
        HashTagUpdateComponent,
        HashTagDeleteDialogComponent,
        HashTagDeletePopupComponent
    ],
    entryComponents: [HashTagComponent, HashTagUpdateComponent, HashTagDeleteDialogComponent, HashTagDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefHashTagModule {}
