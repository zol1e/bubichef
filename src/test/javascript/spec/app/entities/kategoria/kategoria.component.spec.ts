/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BubichefTestModule } from '../../../test.module';
import { KategoriaComponent } from 'app/entities/kategoria/kategoria.component';
import { KategoriaService } from 'app/entities/kategoria/kategoria.service';
import { Kategoria } from 'app/shared/model/kategoria.model';

describe('Component Tests', () => {
    describe('Kategoria Management Component', () => {
        let comp: KategoriaComponent;
        let fixture: ComponentFixture<KategoriaComponent>;
        let service: KategoriaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [KategoriaComponent],
                providers: []
            })
                .overrideTemplate(KategoriaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KategoriaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KategoriaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Kategoria(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.kategorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
