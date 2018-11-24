import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKategoria } from 'app/shared/model/kategoria.model';
import { KategoriaService } from './kategoria.service';

@Component({
    selector: 'jhi-kategoria-delete-dialog',
    templateUrl: './kategoria-delete-dialog.component.html'
})
export class KategoriaDeleteDialogComponent {
    kategoria: IKategoria;

    constructor(private kategoriaService: KategoriaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.kategoriaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'kategoriaListModification',
                content: 'Deleted an kategoria'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kategoria-delete-popup',
    template: ''
})
export class KategoriaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kategoria }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(KategoriaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.kategoria = kategoria;
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
