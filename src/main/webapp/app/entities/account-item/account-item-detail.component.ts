import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountItem } from 'app/shared/model/account-item.model';

@Component({
  selector: 'jhi-account-item-detail',
  templateUrl: './account-item-detail.component.html'
})
export class AccountItemDetailComponent implements OnInit {
  accountItem: IAccountItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountItem }) => {
      this.accountItem = accountItem;
    });
  }

  previousState() {
    window.history.back();
  }
}
