import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKategoria } from 'app/shared/model/kategoria.model';

@Component({
    selector: 'jhi-kategoria-detail',
    templateUrl: './kategoria-detail.component.html'
})
export class KategoriaDetailComponent implements OnInit {
    kategoria: IKategoria;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kategoria }) => {
            this.kategoria = kategoria;
        });
    }

    previousState() {
        window.history.back();
    }
}
