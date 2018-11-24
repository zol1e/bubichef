import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRecept } from 'app/shared/model/recept.model';
import { ReceptService } from './recept.service';
import { IKategoria } from 'app/shared/model/kategoria.model';
import { KategoriaService } from 'app/entities/kategoria';
import { IHashTag } from 'app/shared/model/hash-tag.model';
import { HashTagService } from 'app/entities/hash-tag';

@Component({
    selector: 'jhi-recept-update',
    templateUrl: './recept-update.component.html'
})
export class ReceptUpdateComponent implements OnInit {
    recept: IRecept;
    isSaving: boolean;

    kategorias: IKategoria[];

    hashtags: IHashTag[];
    feltoltveDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private receptService: ReceptService,
        private kategoriaService: KategoriaService,
        private hashTagService: HashTagService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recept }) => {
            this.recept = recept;
        });
        this.kategoriaService.query().subscribe(
            (res: HttpResponse<IKategoria[]>) => {
                this.kategorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hashTagService.query().subscribe(
            (res: HttpResponse<IHashTag[]>) => {
                this.hashtags = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.recept, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recept.id !== undefined) {
            this.subscribeToSaveResponse(this.receptService.update(this.recept));
        } else {
            this.subscribeToSaveResponse(this.receptService.create(this.recept));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecept>>) {
        result.subscribe((res: HttpResponse<IRecept>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackKategoriaById(index: number, item: IKategoria) {
        return item.id;
    }

    trackHashTagById(index: number, item: IHashTag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
