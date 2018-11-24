/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { ReceptDetailComponent } from 'app/entities/recept/recept-detail.component';
import { Recept } from 'app/shared/model/recept.model';

describe('Component Tests', () => {
    describe('Recept Management Detail Component', () => {
        let comp: ReceptDetailComponent;
        let fixture: ComponentFixture<ReceptDetailComponent>;
        const route = ({ data: of({ recept: new Recept(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReceptDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReceptDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recept).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
