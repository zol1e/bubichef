import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BubichefSharedModule } from 'app/shared';
import {
    ReceptComponent,
    ReceptDetailComponent,
    ReceptUpdateComponent,
    ReceptDeletePopupComponent,
    ReceptDeleteDialogComponent,
    receptRoute,
    receptPopupRoute
} from './';

const ENTITY_STATES = [...receptRoute, ...receptPopupRoute];

@NgModule({
    imports: [BubichefSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReceptComponent, ReceptDetailComponent, ReceptUpdateComponent, ReceptDeleteDialogComponent, ReceptDeletePopupComponent],
    entryComponents: [ReceptComponent, ReceptUpdateComponent, ReceptDeleteDialogComponent, ReceptDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BubichefReceptModule {}
