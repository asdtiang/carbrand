import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CarbrandTestModule } from '../../../test.module';
import { BrandTypeDeleteDialogComponent } from 'app/entities/brand-type/brand-type-delete-dialog.component';
import { BrandTypeService } from 'app/entities/brand-type/brand-type.service';

describe('Component Tests', () => {
  describe('BrandType Management Delete Component', () => {
    let comp: BrandTypeDeleteDialogComponent;
    let fixture: ComponentFixture<BrandTypeDeleteDialogComponent>;
    let service: BrandTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarbrandTestModule],
        declarations: [BrandTypeDeleteDialogComponent]
      })
        .overrideTemplate(BrandTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BrandTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrandTypeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
