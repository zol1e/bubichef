/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BubichefTestModule } from '../../../test.module';
import { KategoriaDeleteDialogComponent } from 'app/entities/kategoria/kategoria-delete-dialog.component';
import { KategoriaService } from 'app/entities/kategoria/kategoria.service';

describe('Component Tests', () => {
    describe('Kategoria Management Delete Component', () => {
        let comp: KategoriaDeleteDialogComponent;
        let fixture: ComponentFixture<KategoriaDeleteDialogComponent>;
        let service: KategoriaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [KategoriaDeleteDialogComponent]
            })
                .overrideTemplate(KategoriaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KategoriaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KategoriaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
