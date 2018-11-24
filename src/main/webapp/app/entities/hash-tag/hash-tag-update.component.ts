import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHashTag } from 'app/shared/model/hash-tag.model';
import { HashTagService } from './hash-tag.service';
import { IRecept } from 'app/shared/model/recept.model';
import { ReceptService } from 'app/entities/recept';

@Component({
    selector: 'jhi-hash-tag-update',
    templateUrl: './hash-tag-update.component.html'
})
export class HashTagUpdateComponent implements OnInit {
    hashTag: IHashTag;
    isSaving: boolean;

    recepts: IRecept[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private hashTagService: HashTagService,
        private receptService: ReceptService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hashTag }) => {
            this.hashTag = hashTag;
        });
        this.receptService.query().subscribe(
            (res: HttpResponse<IRecept[]>) => {
                this.recepts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hashTag.id !== undefined) {
            this.subscribeToSaveResponse(this.hashTagService.update(this.hashTag));
        } else {
            this.subscribeToSaveResponse(this.hashTagService.create(this.hashTag));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHashTag>>) {
        result.subscribe((res: HttpResponse<IHashTag>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
