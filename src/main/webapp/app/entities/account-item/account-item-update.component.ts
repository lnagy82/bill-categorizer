import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountItem, AccountItem } from 'app/shared/model/account-item.model';
import { AccountItemService } from './account-item.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
  selector: 'jhi-account-item-update',
  templateUrl: './account-item-update.component.html'
})
export class AccountItemUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategory[];

  editForm = this.fb.group({
    id: [],
    date: [],
    transactionType: [],
    description: [],
    amount: [],
    currency: [],
    categoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected accountItemService: AccountItemService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountItem }) => {
      this.updateForm(accountItem);
    });
    this.categoryService
      .query()
      .subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(accountItem: IAccountItem) {
    this.editForm.patchValue({
      id: accountItem.id,
      date: accountItem.date != null ? accountItem.date.format(DATE_TIME_FORMAT) : null,
      transactionType: accountItem.transactionType,
      description: accountItem.description,
      amount: accountItem.amount,
      currency: accountItem.currency,
      categoryId: accountItem.categoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountItem = this.createFromForm();
    if (accountItem.id !== undefined) {
      this.subscribeToSaveResponse(this.accountItemService.update(accountItem));
    } else {
      this.subscribeToSaveResponse(this.accountItemService.create(accountItem));
    }
  }

  private createFromForm(): IAccountItem {
    return {
      ...new AccountItem(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      transactionType: this.editForm.get(['transactionType']).value,
      description: this.editForm.get(['description']).value,
      amount: this.editForm.get(['amount']).value,
      currency: this.editForm.get(['currency']).value,
      categoryId: this.editForm.get(['categoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountItem>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }
}
