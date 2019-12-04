import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccountItem } from 'app/shared/model/account-item.model';
import { AccountItemService } from './account-item.service';

@Component({
  templateUrl: './account-item-delete-dialog.component.html'
})
export class AccountItemDeleteDialogComponent {
  accountItem: IAccountItem;

  constructor(
    protected accountItemService: AccountItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.accountItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'accountItemListModification',
        content: 'Deleted an accountItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}
