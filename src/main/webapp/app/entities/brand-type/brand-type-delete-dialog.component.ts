import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBrandType } from 'app/shared/model/brand-type.model';
import { BrandTypeService } from './brand-type.service';

@Component({
  templateUrl: './brand-type-delete-dialog.component.html'
})
export class BrandTypeDeleteDialogComponent {
  brandType: IBrandType;

  constructor(protected brandTypeService: BrandTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.brandTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'brandTypeListModification',
        content: 'Deleted an brandType'
      });
      this.activeModal.dismiss(true);
    });
  }
}
