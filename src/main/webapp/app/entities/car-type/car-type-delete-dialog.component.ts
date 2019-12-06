import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarType } from 'app/shared/model/car-type.model';
import { CarTypeService } from './car-type.service';

@Component({
  templateUrl: './car-type-delete-dialog.component.html'
})
export class CarTypeDeleteDialogComponent {
  carType: ICarType;

  constructor(protected carTypeService: CarTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'carTypeListModification',
        content: 'Deleted an carType'
      });
      this.activeModal.dismiss(true);
    });
  }
}
