import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CarbrandTestModule } from '../../../test.module';
import { CarTypeDeleteDialogComponent } from 'app/entities/car-type/car-type-delete-dialog.component';
import { CarTypeService } from 'app/entities/car-type/car-type.service';

describe('Component Tests', () => {
  describe('CarType Management Delete Component', () => {
    let comp: CarTypeDeleteDialogComponent;
    let fixture: ComponentFixture<CarTypeDeleteDialogComponent>;
    let service: CarTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarbrandTestModule],
        declarations: [CarTypeDeleteDialogComponent]
      })
        .overrideTemplate(CarTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CarTypeService);
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
