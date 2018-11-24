import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';
import { ReceptToOsszetevoService } from './recept-to-osszetevo.service';

@Component({
    selector: 'jhi-recept-to-osszetevo-delete-dialog',
    templateUrl: './recept-to-osszetevo-delete-dialog.component.html'
})
export class ReceptToOsszetevoDeleteDialogComponent {
    receptToOsszetevo: IReceptToOsszetevo;

    constructor(
        private receptToOsszetevoService: ReceptToOsszetevoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.receptToOsszetevoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'receptToOsszetevoListModification',
                content: 'Deleted an receptToOsszetevo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recept-to-osszetevo-delete-popup',
    template: ''
})
export class ReceptToOsszetevoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ receptToOsszetevo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReceptToOsszetevoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.receptToOsszetevo = receptToOsszetevo;
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
