import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';

@Component({
  templateUrl: './indicator-delete-dialog.component.html'
})
export class IndicatorDeleteDialogComponent {
  indicator: IIndicator;

  constructor(protected indicatorService: IndicatorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.indicatorService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'indicatorListModification',
        content: 'Deleted an indicator'
      });
      this.activeModal.dismiss(true);
    });
  }
}
