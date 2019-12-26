import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AccountItem } from 'app/shared/model/account-item.model';
import { AccountItemService } from './account-item.service';
import { AccountItemComponent } from './account-item.component';
import { AccountItemDetailComponent } from './account-item-detail.component';
import { AccountItemUpdateComponent } from './account-item-update.component';
import { IAccountItem } from 'app/shared/model/account-item.model';

@Injectable({ providedIn: 'root' })
export class AccountItemResolve implements Resolve<IAccountItem> {
  constructor(private service: AccountItemService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountItem> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((accountItem: HttpResponse<AccountItem>) => accountItem.body));
    }
    return of(new AccountItem());
  }
}

export const accountItemRoute: Routes = [
  {
    path: '',
    component: AccountItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.accountItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountItemDetailComponent,
    resolve: {
      accountItem: AccountItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.accountItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountItemUpdateComponent,
    resolve: {
      accountItem: AccountItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.accountItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountItemUpdateComponent,
    resolve: {
      accountItem: AccountItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.accountItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':load',
    component: AccountItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.accountItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
