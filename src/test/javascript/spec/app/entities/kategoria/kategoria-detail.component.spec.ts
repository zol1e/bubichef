/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { KategoriaDetailComponent } from 'app/entities/kategoria/kategoria-detail.component';
import { Kategoria } from 'app/shared/model/kategoria.model';

describe('Component Tests', () => {
    describe('Kategoria Management Detail Component', () => {
        let comp: KategoriaDetailComponent;
        let fixture: ComponentFixture<KategoriaDetailComponent>;
        const route = ({ data: of({ kategoria: new Kategoria(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [KategoriaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KategoriaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KategoriaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.kategoria).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
