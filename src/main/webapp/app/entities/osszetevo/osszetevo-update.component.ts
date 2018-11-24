import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IOsszetevo } from 'app/shared/model/osszetevo.model';
import { OsszetevoService } from './osszetevo.service';

@Component({
    selector: 'jhi-osszetevo-update',
    templateUrl: './osszetevo-update.component.html'
})
export class OsszetevoUpdateComponent implements OnInit {
    osszetevo: IOsszetevo;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private osszetevoService: OsszetevoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ osszetevo }) => {
            this.osszetevo = osszetevo;
        });
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
        this.dataUtils.clearInputImage(this.osszetevo, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.osszetevo.id !== undefined) {
            this.subscribeToSaveResponse(this.osszetevoService.update(this.osszetevo));
        } else {
            this.subscribeToSaveResponse(this.osszetevoService.create(this.osszetevo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOsszetevo>>) {
        result.subscribe((res: HttpResponse<IOsszetevo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
