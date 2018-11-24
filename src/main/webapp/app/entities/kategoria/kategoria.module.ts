import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BubichefSharedModule } from 'app/shared';
import {
    KategoriaComponent,
    KategoriaDetailComponent,
    KategoriaUpdateComponent,
    KategoriaDeletePopupComponent,
    KategoriaDeleteDialogComponent,
    kategoriaRoute,
    kategoriaPopupRoute
} from './';

const ENTITY_STATES = [...kategoriaRoute, ...kategoriaPopupRoute];

@NgModule({
    imports: [BubichefSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KategoriaComponent,
        KategoriaDetailComponent,
        KategoriaUpdateComponent,
        KategoriaDeleteDialogComponent,
        KategoriaDeletePopupComponent
    ],
    entryComponents: [KategoriaComponent, KategoriaUpdateComponent, KategoriaDeleteDialogComponent, KategoriaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefKategoriaModule {}
