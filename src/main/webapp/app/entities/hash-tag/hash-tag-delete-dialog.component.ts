import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHashTag } from 'app/shared/model/hash-tag.model';
import { HashTagService } from './hash-tag.service';

@Component({
    selector: 'jhi-hash-tag-delete-dialog',
    templateUrl: './hash-tag-delete-dialog.component.html'
})
export class HashTagDeleteDialogComponent {
    hashTag: IHashTag;

    constructor(private hashTagService: HashTagService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hashTagService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hashTagListModification',
                content: 'Deleted an hashTag'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hash-tag-delete-popup',
    template: ''
})
export class HashTagDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hashTag }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HashTagDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.hashTag = hashTag;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
