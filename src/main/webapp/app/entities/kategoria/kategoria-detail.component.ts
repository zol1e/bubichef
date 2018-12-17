import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { JhiAlertService } from 'ng-jhipster';

import { IKategoria } from 'app/shared/model/kategoria.model';
import { IRecept, Recept } from 'app/shared/model/recept.model';
import { ReceptService } from '../recept/recept.service';

@Component({
    selector: 'jhi-kategoria-detail',
    templateUrl: './kategoria-detail.component.html'
})
export class KategoriaDetailComponent implements OnInit {
    kategoria: IKategoria;
    allRecepts: IRecept[];
    recepts: IRecept[];

    constructor(
            private activatedRoute: ActivatedRoute,
            private receptService: ReceptService,
            private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.recepts = new Array<Recept>();
        
        this.activatedRoute.data.subscribe(({ kategoria }) => {
            this.kategoria = kategoria;
        });

        this.receptService.query().subscribe(
                (res: HttpResponse<IRecept[]>) => {
                    this.allRecepts = res.body;
                    
                    for (let i = 0; i < this.allRecepts.length; i++) {
                        if (this.allRecepts[i].kategoriaId === this.kategoria.id) {
                            this.recepts.push(this.allRecepts[i]);
                        }
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }
    
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
