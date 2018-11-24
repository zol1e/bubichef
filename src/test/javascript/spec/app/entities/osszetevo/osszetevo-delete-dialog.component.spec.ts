/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BubichefTestModule } from '../../../test.module';
import { OsszetevoDeleteDialogComponent } from 'app/entities/osszetevo/osszetevo-delete-dialog.component';
import { OsszetevoService } from 'app/entities/osszetevo/osszetevo.service';

describe('Component Tests', () => {
    describe('Osszetevo Management Delete Component', () => {
        let comp: OsszetevoDeleteDialogComponent;
        let fixture: ComponentFixture<OsszetevoDeleteDialogComponent>;
        let service: OsszetevoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [OsszetevoDeleteDialogComponent]
            })
                .overrideTemplate(OsszetevoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OsszetevoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OsszetevoService);
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
