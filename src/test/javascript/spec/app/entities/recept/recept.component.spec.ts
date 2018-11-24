/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BubichefTestModule } from '../../../test.module';
import { ReceptComponent } from 'app/entities/recept/recept.component';
import { ReceptService } from 'app/entities/recept/recept.service';
import { Recept } from 'app/shared/model/recept.model';

describe('Component Tests', () => {
    describe('Recept Management Component', () => {
        let comp: ReceptComponent;
        let fixture: ComponentFixture<ReceptComponent>;
        let service: ReceptService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptComponent],
                providers: []
            })
                .overrideTemplate(ReceptComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReceptComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceptService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Recept(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.recepts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
