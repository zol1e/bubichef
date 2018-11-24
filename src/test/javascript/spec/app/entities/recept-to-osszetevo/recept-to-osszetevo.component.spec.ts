/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BubichefTestModule } from '../../../test.module';
import { ReceptToOsszetevoComponent } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo.component';
import { ReceptToOsszetevoService } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo.service';
import { ReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

describe('Component Tests', () => {
    describe('ReceptToOsszetevo Management Component', () => {
        let comp: ReceptToOsszetevoComponent;
        let fixture: ComponentFixture<ReceptToOsszetevoComponent>;
        let service: ReceptToOsszetevoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptToOsszetevoComponent],
                providers: []
            })
                .overrideTemplate(ReceptToOsszetevoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReceptToOsszetevoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceptToOsszetevoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ReceptToOsszetevo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.receptToOsszetevos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
