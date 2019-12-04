import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Indicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';
import { IndicatorComponent } from './indicator.component';
import { IndicatorDetailComponent } from './indicator-detail.component';
import { IndicatorUpdateComponent } from './indicator-update.component';
import { IIndicator } from 'app/shared/model/indicator.model';

@Injectable({ providedIn: 'root' })
export class IndicatorResolve implements Resolve<IIndicator> {
  constructor(private service: IndicatorService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicator> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((indicator: HttpResponse<Indicator>) => indicator.body));
    }
    return of(new Indicator());
  }
}

export const indicatorRoute: Routes = [
  {
    path: '',
    component: IndicatorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.indicator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IndicatorDetailComponent,
    resolve: {
      indicator: IndicatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.indicator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.indicator.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'billcategorizerApp.indicator.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
