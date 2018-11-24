/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { KategoriaUpdateComponent } from 'app/entities/kategoria/kategoria-update.component';
import { KategoriaService } from 'app/entities/kategoria/kategoria.service';
import { Kategoria } from 'app/shared/model/kategoria.model';

describe('Component Tests', () => {
    describe('Kategoria Management Update Component', () => {
        let comp: KategoriaUpdateComponent;
        let fixture: ComponentFixture<KategoriaUpdateComponent>;
        let service: KategoriaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [KategoriaUpdateComponent]
            })
                .overrideTemplate(KategoriaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KategoriaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KategoriaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Kategoria(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kategoria = entity;
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
                    const entity = new Kategoria();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.kategoria = entity;
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
