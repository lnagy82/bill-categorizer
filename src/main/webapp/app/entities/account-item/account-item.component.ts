import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountItem } from 'app/shared/model/account-item.model';
import { AccountItemService } from './account-item.service';
import { AccountItemDeleteDialogComponent } from './account-item-delete-dialog.component';

@Component({
  selector: 'jhi-account-item',
  templateUrl: './account-item.component.html'
})
export class AccountItemComponent implements OnInit, OnDestroy {
  accountItems: IAccountItem[];
  eventSubscriber: Subscription;

  constructor(
    protected accountItemService: AccountItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.accountItemService.query().subscribe((res: HttpResponse<IAccountItem[]>) => {
      this.accountItems = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAccountItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountItem) {
    return item.id;
  }

  registerChangeInAccountItems() {
    this.eventSubscriber = this.eventManager.subscribe('accountItemListModification', () => this.loadAll());
  }

  delete(accountItem: IAccountItem) {
    const modalRef = this.modalService.open(AccountItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountItem = accountItem;
  }
}
