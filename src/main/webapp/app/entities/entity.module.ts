import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.BillcategorizerCategoryModule)
      },
      {
        path: 'account-item',
        loadChildren: () => import('./account-item/account-item.module').then(m => m.BillcategorizerAccountItemModule)
      },
      {
        path: 'indicator',
        loadChildren: () => import('./indicator/indicator.module').then(m => m.BillcategorizerIndicatorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BillcategorizerEntityModule {}
