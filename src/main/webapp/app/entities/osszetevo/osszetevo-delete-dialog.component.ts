import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOsszetevo } from 'app/shared/model/osszetevo.model';
import { OsszetevoService } from './osszetevo.service';

@Component({
    selector: 'jhi-osszetevo-delete-dialog',
    templateUrl: './osszetevo-delete-dialog.component.html'
})
export class OsszetevoDeleteDialogComponent {
    osszetevo: IOsszetevo;

    constructor(private osszetevoService: OsszetevoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.osszetevoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'osszetevoListModification',
                content: 'Deleted an osszetevo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-osszetevo-delete-popup',
    template: ''
})
export class OsszetevoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ osszetevo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OsszetevoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.osszetevo = osszetevo;
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
