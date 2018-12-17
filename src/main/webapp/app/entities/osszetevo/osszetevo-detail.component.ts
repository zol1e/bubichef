import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils, JhiAlertService } from 'ng-jhipster';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { IOsszetevo } from 'app/shared/model/osszetevo.model';
import { ReceptService } from '../recept/recept.service';
import { IRecept, Recept } from 'app/shared/model/recept.model';
import { ReceptToOsszetevoService } from '../recept-to-osszetevo/recept-to-osszetevo.service';
import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

@Component({
    selector: 'jhi-osszetevo-detail',
    templateUrl: './osszetevo-detail.component.html'
})
export class OsszetevoDetailComponent implements OnInit {
    osszetevo: IOsszetevo;
    
    allReceptToOsszetevo: IReceptToOsszetevo[];
    allRecepts: IRecept[];
    recepts: IRecept[];
    
    receptIds: Array<number>;

    constructor(
            private dataUtils: JhiDataUtils,
            private activatedRoute: ActivatedRoute,
            private receptService: ReceptService,
            private receptToOsszetevoService: ReceptToOsszetevoService,
            private jhiAlertService: JhiAlertService
    ) 
    {}

    ngOnInit() {
        this.receptIds = new Array<number>();
        this.recepts = new Array<Recept>();
        
        this.activatedRoute.data.subscribe(({ osszetevo }) => {
            this.osszetevo = osszetevo;
        });
        this.receptToOsszetevoService.query().subscribe(
                (res: HttpResponse<IReceptToOsszetevo[]>) => {
                    this.allReceptToOsszetevo = res.body;
                    
                    for (let i = 0; i < this.allReceptToOsszetevo.length; i++) {
                        if(this.allReceptToOsszetevo[i].osszetevoId === this.osszetevo.id) {
                            this.receptIds.push(this.allReceptToOsszetevo[i].receptId);
                        }
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.receptService.query().subscribe(
                (res: HttpResponse<IRecept[]>) => {
                    this.allRecepts = res.body;
                    
                    for (let i = 0; i < this.allRecepts.length; i++) {
                        if (this.receptIds.indexOf(this.allRecepts[i].id) > -1) {
                            this.recepts.push(this.allRecepts[i]);
                        }
                    }
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
    previousState() {
        window.history.back();
    }
    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
