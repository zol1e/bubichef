/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { ReceptToOsszetevoDetailComponent } from 'app/entities/recept-to-osszetevo/recept-to-osszetevo-detail.component';
import { ReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

describe('Component Tests', () => {
    describe('ReceptToOsszetevo Management Detail Component', () => {
        let comp: ReceptToOsszetevoDetailComponent;
        let fixture: ComponentFixture<ReceptToOsszetevoDetailComponent>;
        const route = ({ data: of({ receptToOsszetevo: new ReceptToOsszetevo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [ReceptToOsszetevoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReceptToOsszetevoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReceptToOsszetevoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.receptToOsszetevo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
