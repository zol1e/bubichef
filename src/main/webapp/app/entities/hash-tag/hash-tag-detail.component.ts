import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { IHashTag } from 'app/shared/model/hash-tag.model';
import { IRecept, Recept } from 'app/shared/model/recept.model';
import { ReceptService } from '../recept/recept.service';

@Component({
    selector: 'jhi-hash-tag-detail',
    templateUrl: './hash-tag-detail.component.html'
})
export class HashTagDetailComponent implements OnInit {
    hashTag: IHashTag;
    recepts: IRecept[];
    allRecepts: IRecept[];

    constructor(
            private activatedRoute: ActivatedRoute,
            private receptService: ReceptService,
            private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.recepts = new Array<Recept>();
        
        this.activatedRoute.data.subscribe(({ hashTag }) => {
            this.hashTag = hashTag;
        });
        
        this.receptService.query().subscribe(
                (res: HttpResponse<IRecept[]>) => {
                    this.allRecepts = res.body;
                    
                    for (let i = 0; i < this.allRecepts.length; i++) {
                        for(let j = 0; j < this.allRecepts[i].hashtageks.length; j++) {
                            if(this.allRecepts[i].hashtageks[j].id === this.hashTag.id) {
                                this.recepts.push(this.allRecepts[i]);
                                break;
                            }
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
