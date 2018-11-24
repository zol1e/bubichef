/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BubichefTestModule } from '../../../test.module';
import { OsszetevoDetailComponent } from 'app/entities/osszetevo/osszetevo-detail.component';
import { Osszetevo } from 'app/shared/model/osszetevo.model';

describe('Component Tests', () => {
    describe('Osszetevo Management Detail Component', () => {
        let comp: OsszetevoDetailComponent;
        let fixture: ComponentFixture<OsszetevoDetailComponent>;
        const route = ({ data: of({ osszetevo: new Osszetevo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BubichefTestModule],
                declarations: [OsszetevoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OsszetevoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OsszetevoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.osszetevo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
