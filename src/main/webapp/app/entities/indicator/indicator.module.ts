import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillcategorizerSharedModule } from 'app/shared/shared.module';
import { IndicatorComponent } from './indicator.component';
import { IndicatorDetailComponent } from './indicator-detail.component';
import { IndicatorUpdateComponent } from './indicator-update.component';
import { IndicatorDeleteDialogComponent } from './indicator-delete-dialog.component';
import { indicatorRoute } from './indicator.route';

@NgModule({
  imports: [BillcategorizerSharedModule, RouterModule.forChild(indicatorRoute)],
  declarations: [IndicatorComponent, IndicatorDetailComponent, IndicatorUpdateComponent, IndicatorDeleteDialogComponent],
  entryComponents: [IndicatorDeleteDialogComponent]
})
export class BillcategorizerIndicatorModule {}
