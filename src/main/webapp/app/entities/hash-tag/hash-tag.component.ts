import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHashTag } from 'app/shared/model/hash-tag.model';
import { Principal } from 'app/core';
import { HashTagService } from './hash-tag.service';

@Component({
    selector: 'jhi-hash-tag',
    templateUrl: './hash-tag.component.html'
})
export class HashTagComponent implements OnInit, OnDestroy {
    hashTags: IHashTag[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hashTagService: HashTagService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.hashTagService.query().subscribe(
            (res: HttpResponse<IHashTag[]>) => {
                this.hashTags = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHashTags();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHashTag) {
        return item.id;
    }

    registerChangeInHashTags() {
        this.eventSubscriber = this.eventManager.subscribe('hashTagListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
