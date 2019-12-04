import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillcategorizerSharedModule } from 'app/shared/shared.module';
import { AccountItemComponent } from './account-item.component';
import { AccountItemDetailComponent } from './account-item-detail.component';
import { AccountItemUpdateComponent } from './account-item-update.component';
import { AccountItemDeleteDialogComponent } from './account-item-delete-dialog.component';
import { accountItemRoute } from './account-item.route';

@NgModule({
  imports: [BillcategorizerSharedModule, RouterModule.forChild(accountItemRoute)],
  declarations: [AccountItemComponent, AccountItemDetailComponent, AccountItemUpdateComponent, AccountItemDeleteDialogComponent],
  entryComponents: [AccountItemDeleteDialogComponent]
})
export class BillcategorizerAccountItemModule {}
