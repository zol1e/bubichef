import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IKategoria } from 'app/shared/model/kategoria.model';
import { KategoriaService } from './kategoria.service';

@Component({
    selector: 'jhi-kategoria-update',
    templateUrl: './kategoria-update.component.html'
})
export class KategoriaUpdateComponent implements OnInit {
    kategoria: IKategoria;
    isSaving: boolean;

    constructor(private kategoriaService: KategoriaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kategoria }) => {
            this.kategoria = kategoria;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kategoria.id !== undefined) {
            this.subscribeToSaveResponse(this.kategoriaService.update(this.kategoria));
        } else {
            this.subscribeToSaveResponse(this.kategoriaService.create(this.kategoria));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IKategoria>>) {
        result.subscribe((res: HttpResponse<IKategoria>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
