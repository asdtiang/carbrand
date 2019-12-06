import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CarbrandTestModule } from '../../../test.module';
import { BrandTypeUpdateComponent } from 'app/entities/brand-type/brand-type-update.component';
import { BrandTypeService } from 'app/entities/brand-type/brand-type.service';
import { BrandType } from 'app/shared/model/brand-type.model';

describe('Component Tests', () => {
  describe('BrandType Management Update Component', () => {
    let comp: BrandTypeUpdateComponent;
    let fixture: ComponentFixture<BrandTypeUpdateComponent>;
    let service: BrandTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarbrandTestModule],
        declarations: [BrandTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BrandTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BrandTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrandTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BrandType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BrandType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
