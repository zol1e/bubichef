import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';
import { ReceptToOsszetevoService } from './recept-to-osszetevo.service';
import { IRecept } from 'app/shared/model/recept.model';
import { ReceptService } from 'app/entities/recept';
import { IOsszetevo } from 'app/shared/model/osszetevo.model';
import { OsszetevoService } from 'app/entities/osszetevo';

@Component({
    selector: 'jhi-recept-to-osszetevo-update',
    templateUrl: './recept-to-osszetevo-update.component.html'
})
export class ReceptToOsszetevoUpdateComponent implements OnInit {
    receptToOsszetevo: IReceptToOsszetevo;
    isSaving: boolean;

    recepts: IRecept[];

    osszetevos: IOsszetevo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private receptToOsszetevoService: ReceptToOsszetevoService,
        private receptService: ReceptService,
        private osszetevoService: OsszetevoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receptToOsszetevo }) => {
            this.receptToOsszetevo = receptToOsszetevo;
        });
        this.receptService.query().subscribe(
            (res: HttpResponse<IRecept[]>) => {
                this.recepts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.osszetevoService.query().subscribe(
            (res: HttpResponse<IOsszetevo[]>) => {
                this.osszetevos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.receptToOsszetevo.id !== undefined) {
            this.subscribeToSaveResponse(this.receptToOsszetevoService.update(this.receptToOsszetevo));
        } else {
            this.subscribeToSaveResponse(this.receptToOsszetevoService.create(this.receptToOsszetevo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReceptToOsszetevo>>) {
        result.subscribe((res: HttpResponse<IReceptToOsszetevo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackReceptById(index: number, item: IRecept) {
        return item.id;
    }

    trackOsszetevoById(index: number, item: IOsszetevo) {
        return item.id;
    }
}
