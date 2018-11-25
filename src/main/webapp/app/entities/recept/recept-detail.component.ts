import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecept } from 'app/shared/model/recept.model';

@Component({
    selector: 'jhi-recept-detail',
    templateUrl: './recept-detail.component.html'
})
export class ReceptDetailComponent implements OnInit {
    recept: IRecept;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recept }) => {
            this.recept = recept;
            console.log(this.recept);
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
