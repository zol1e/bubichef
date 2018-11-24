/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BubichefTestModule } from '../../../test.module';
import { OsszetevoComponent } from 'app/entities/osszetevo/osszetevo.component';
import { OsszetevoService } from 'app/entities/osszetevo/osszetevo.service';
import { Osszetevo } from 'app/shared/model/osszetevo.model';

describe('Component Tests', () => {
    describe('Osszetevo Management Component', () => {
        let comp: OsszetevoComponent;
        let fixture: ComponentFixture<OsszetevoComponent>;
        let service: OsszetevoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [OsszetevoComponent],
                providers: []
            })
                .overrideTemplate(OsszetevoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OsszetevoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OsszetevoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Osszetevo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.osszetevos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
