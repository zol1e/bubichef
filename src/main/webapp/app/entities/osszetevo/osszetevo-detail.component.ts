import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOsszetevo } from 'app/shared/model/osszetevo.model';

@Component({
    selector: 'jhi-osszetevo-detail',
    templateUrl: './osszetevo-detail.component.html'
})
export class OsszetevoDetailComponent implements OnInit {
    osszetevo: IOsszetevo;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
