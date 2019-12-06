import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CarbrandTestModule } from '../../../test.module';
import { BrandTypeDetailComponent } from 'app/entities/brand-type/brand-type-detail.component';
import { BrandType } from 'app/shared/model/brand-type.model';

describe('Component Tests', () => {
  describe('BrandType Management Detail Component', () => {
    let comp: BrandTypeDetailComponent;
    let fixture: ComponentFixture<BrandTypeDetailComponent>;
    const route = ({ data: of({ brandType: new BrandType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarbrandTestModule],
        declarations: [BrandTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BrandTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BrandTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.brandType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
