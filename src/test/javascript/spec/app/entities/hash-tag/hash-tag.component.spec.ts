/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BubichefTestModule } from '../../../test.module';
import { HashTagComponent } from 'app/entities/hash-tag/hash-tag.component';
import { HashTagService } from 'app/entities/hash-tag/hash-tag.service';
import { HashTag } from 'app/shared/model/hash-tag.model';

describe('Component Tests', () => {
    describe('HashTag Management Component', () => {
        let comp: HashTagComponent;
        let fixture: ComponentFixture<HashTagComponent>;
        let service: HashTagService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [HashTagComponent],
                providers: []
            })
                .overrideTemplate(HashTagComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HashTagComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HashTagService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HashTag(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hashTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
