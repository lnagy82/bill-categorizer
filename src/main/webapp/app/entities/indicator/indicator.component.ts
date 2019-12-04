import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';
import { IndicatorDeleteDialogComponent } from './indicator-delete-dialog.component';

@Component({
  selector: 'jhi-indicator',
  templateUrl: './indicator.component.html'
})
export class IndicatorComponent implements OnInit, OnDestroy {
  indicators: IIndicator[];
  eventSubscriber: Subscription;

  constructor(protected indicatorService: IndicatorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll() {
    this.indicatorService.query().subscribe((res: HttpResponse<IIndicator[]>) => {
      this.indicators = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInIndicators();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIndicator) {
    return item.id;
  }

  registerChangeInIndicators() {
    this.eventSubscriber = this.eventManager.subscribe('indicatorListModification', () => this.loadAll());
  }

  delete(indicator: IIndicator) {
    const modalRef = this.modalService.open(IndicatorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indicator = indicator;
  }
}
