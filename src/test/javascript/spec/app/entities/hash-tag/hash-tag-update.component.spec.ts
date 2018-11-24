/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { HashTagUpdateComponent } from 'app/entities/hash-tag/hash-tag-update.component';
import { HashTagService } from 'app/entities/hash-tag/hash-tag.service';
import { HashTag } from 'app/shared/model/hash-tag.model';

describe('Component Tests', () => {
    describe('HashTag Management Update Component', () => {
        let comp: HashTagUpdateComponent;
        let fixture: ComponentFixture<HashTagUpdateComponent>;
        let service: HashTagService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [HashTagUpdateComponent]
            })
                .overrideTemplate(HashTagUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HashTagUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HashTagService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HashTag(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hashTag = entity;
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
                    const entity = new HashTag();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hashTag = entity;
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
