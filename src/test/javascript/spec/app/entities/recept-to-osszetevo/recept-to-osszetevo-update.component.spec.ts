/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { ReceptToOsszetevoUpdateComponent } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo-update.component';
import { ReceptToOsszetevoService } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo.service';
import { ReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

describe('Component Tests', () => {
    describe('ReceptToOsszetevo Management Update Component', () => {
        let comp: ReceptToOsszetevoUpdateComponent;
        let fixture: ComponentFixture<ReceptToOsszetevoUpdateComponent>;
        let service: ReceptToOsszetevoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptToOsszetevoUpdateComponent]
            })
                .overrideTemplate(ReceptToOsszetevoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReceptToOsszetevoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceptToOsszetevoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReceptToOsszetevo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.receptToOsszetevo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReceptToOsszetevo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.receptToOsszetevo = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
