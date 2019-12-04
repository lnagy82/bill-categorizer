import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IIndicator, Indicator } from 'app/shared/model/indicator.model';
import { IndicatorService } from './indicator.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
  selector: 'jhi-indicator-update',
  templateUrl: './indicator-update.component.html'
})
export class IndicatorUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategory[];

  editForm = this.fb.group({
    id: [],
    text: [],
    categoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected indicatorService: IndicatorService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ indicator }) => {
      this.updateForm(indicator);
    });
    this.categoryService
      .query()
      .subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(indicator: IIndicator) {
    this.editForm.patchValue({
      id: indicator.id,
      text: indicator.text,
      categoryId: indicator.categoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const indicator = this.createFromForm();
    if (indicator.id !== undefined) {
      this.subscribeToSaveResponse(this.indicatorService.update(indicator));
    } else {
      this.subscribeToSaveResponse(this.indicatorService.create(indicator));
    }
  }

  private createFromForm(): IIndicator {
    return {
      ...new Indicator(),
      id: this.editForm.get(['id']).value,
      text: this.editForm.get(['text']).value,
      categoryId: this.editForm.get(['categoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicator>>) {
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
