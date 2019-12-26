import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccountItem } from 'app/shared/model/account-item.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountItemService } from './account-item.service';
import { AccountItemDeleteDialogComponent } from './account-item-delete-dialog.component';

@Component({
  selector: 'jhi-account-item',
  templateUrl: './account-item.component.html'
})
export class AccountItemComponent implements OnInit, OnDestroy {
  accountItems: IAccountItem[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected accountItemService: AccountItemService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.accountItems = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.accountItemService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAccountItem[]>) => this.paginateAccountItems(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.accountItems = [];
    this.loadAll();
  }
  
  load() {

    this.accountItemService.load().subscribe(() => {
      this.eventManager.broadcast({
        name: 'accountItemListModification',
        content: 'Loaded accountItems'
      });

    });
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
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
    this.eventSubscriber = this.eventManager.subscribe('accountItemListModification', () => this.reset());
  }

  delete(accountItem: IAccountItem) {
    const modalRef = this.modalService.open(AccountItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountItem = accountItem;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAccountItems(data: IAccountItem[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.accountItems.push(data[i]);
    }
  }
}
