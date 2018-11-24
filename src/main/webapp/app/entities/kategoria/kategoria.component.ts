import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKategoria } from 'app/shared/model/kategoria.model';
import { Principal } from 'app/core';
import { KategoriaService } from './kategoria.service';

@Component({
    selector: 'jhi-kategoria',
    templateUrl: './kategoria.component.html'
})
export class KategoriaComponent implements OnInit, OnDestroy {
    kategorias: IKategoria[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private kategoriaService: KategoriaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.kategoriaService.query().subscribe(
            (res: HttpResponse<IKategoria[]>) => {
                this.kategorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKategorias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKategoria) {
        return item.id;
    }

    registerChangeInKategorias() {
        this.eventSubscriber = this.eventManager.subscribe('kategoriaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
