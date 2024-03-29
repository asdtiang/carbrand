import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from './series.service';

@Component({
  templateUrl: './series-delete-dialog.component.html'
})
export class SeriesDeleteDialogComponent {
  series: ISeries;

  constructor(protected seriesService: SeriesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.seriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'seriesListModification',
        content: 'Deleted an series'
      });
      this.activeModal.dismiss(true);
    });
  }
}
