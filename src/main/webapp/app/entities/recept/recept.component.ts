import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRecept } from 'app/shared/model/recept.model';
import { Principal } from 'app/core';
import { ReceptService } from './recept.service';

@Component({
    selector: 'jhi-recept',
    templateUrl: './recept.component.html'
})
export class ReceptComponent implements OnInit, OnDestroy {
    recepts: IRecept[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private receptService: ReceptService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.receptService.query().subscribe(
            (res: HttpResponse<IRecept[]>) => {
                this.recepts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecepts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecept) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInRecepts() {
        this.eventSubscriber = this.eventManager.subscribe('receptListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
