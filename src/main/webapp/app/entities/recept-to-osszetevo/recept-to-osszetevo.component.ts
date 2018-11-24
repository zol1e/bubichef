import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';
import { Principal } from 'app/core';
import { ReceptToOsszetevoService } from './recept-to-osszetevo.service';

@Component({
    selector: 'jhi-recept-to-osszetevo',
    templateUrl: './recept-to-osszetevo.component.html'
})
export class ReceptToOsszetevoComponent implements OnInit, OnDestroy {
    receptToOsszetevos: IReceptToOsszetevo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private receptToOsszetevoService: ReceptToOsszetevoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.receptToOsszetevoService.query().subscribe(
            (res: HttpResponse<IReceptToOsszetevo[]>) => {
                this.receptToOsszetevos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReceptToOsszetevos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReceptToOsszetevo) {
        return item.id;
    }

    registerChangeInReceptToOsszetevos() {
        this.eventSubscriber = this.eventManager.subscribe('receptToOsszetevoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
