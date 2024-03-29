import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CarbrandTestModule } from '../../../test.module';
import { BrandDeleteDialogComponent } from 'app/entities/brand/brand-delete-dialog.component';
import { BrandService } from 'app/entities/brand/brand.service';

describe('Component Tests', () => {
  describe('Brand Management Delete Component', () => {
    let comp: BrandDeleteDialogComponent;
    let fixture: ComponentFixture<BrandDeleteDialogComponent>;
    let service: BrandService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarbrandTestModule],
        declarations: [BrandDeleteDialogComponent]
      })
        .overrideTemplate(BrandDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BrandDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BrandService);
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
