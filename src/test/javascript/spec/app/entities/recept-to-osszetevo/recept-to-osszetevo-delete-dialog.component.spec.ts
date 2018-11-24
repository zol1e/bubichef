/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BubichefTestModule } from '../../../test.module';
import { ReceptToOsszetevoDeleteDialogComponent } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo-delete-dialog.component';
import { ReceptToOsszetevoService } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo.service';

describe('Component Tests', () => {
    describe('ReceptToOsszetevo Management Delete Component', () => {
        let comp: ReceptToOsszetevoDeleteDialogComponent;
        let fixture: ComponentFixture<ReceptToOsszetevoDeleteDialogComponent>;
        let service: ReceptToOsszetevoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptToOsszetevoDeleteDialogComponent]
            })
                .overrideTemplate(ReceptToOsszetevoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReceptToOsszetevoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceptToOsszetevoService);
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
