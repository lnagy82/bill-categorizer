import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccountItem } from 'app/shared/model/account-item.model';

type EntityResponseType = HttpResponse<IAccountItem>;
type EntityArrayResponseType = HttpResponse<IAccountItem[]>;

@Injectable({ providedIn: 'root' })
export class AccountItemService {
  public resourceUrl = SERVER_API_URL + 'api/account-items';

  constructor(protected http: HttpClient) { }

  create(accountItem: IAccountItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountItem);
    return this.http
      .post<IAccountItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountItem: IAccountItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountItem);
    return this.http
      .put<IAccountItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  load(): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/load`, { observe: 'response' });
  }

  protected convertDateFromClient(accountItem: IAccountItem): IAccountItem {
    const copy: IAccountItem = Object.assign({}, accountItem, {
      date: accountItem.date != null && accountItem.date.isValid() ? accountItem.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountItem: IAccountItem) => {
        accountItem.date = accountItem.date != null ? moment(accountItem.date) : null;
      });
    }
    return res;
  }
}
