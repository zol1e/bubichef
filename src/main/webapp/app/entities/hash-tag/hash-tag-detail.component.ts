import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHashTag } from 'app/shared/model/hash-tag.model';

@Component({
    selector: 'jhi-hash-tag-detail',
    templateUrl: './hash-tag-detail.component.html'
})
export class HashTagDetailComponent implements OnInit {
    hashTag: IHashTag;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hashTag }) => {
            this.hashTag = hashTag;
        });
    }

    previousState() {
        window.history.back();
    }
}
