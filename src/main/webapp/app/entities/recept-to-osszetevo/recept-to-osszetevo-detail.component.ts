import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

@Component({
    selector: 'jhi-recept-to-osszetevo-detail',
    templateUrl: './recept-to-osszetevo-detail.component.html'
})
export class ReceptToOsszetevoDetailComponent implements OnInit {
    receptToOsszetevo: IReceptToOsszetevo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ receptToOsszetevo }) => {
            this.receptToOsszetevo = receptToOsszetevo;
        });
    }

    previousState() {
        window.history.back();
    }
}
