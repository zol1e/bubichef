/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { ReceptUpdateComponent } from 'app/entities/recept/recept-update.component';
import { ReceptService } from 'app/entities/recept/recept.service';
import { Recept } from 'app/shared/model/recept.model';

describe('Component Tests', () => {
    describe('Recept Management Update Component', () => {
        let comp: ReceptUpdateComponent;
        let fixture: ComponentFixture<ReceptUpdateComponent>;
        let service: ReceptService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptUpdateComponent]
            })
                .overrideTemplate(ReceptUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReceptUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceptService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Recept(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recept = entity;
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
                    const entity = new Recept();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recept = entity;
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
