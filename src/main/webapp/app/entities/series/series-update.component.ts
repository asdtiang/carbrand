import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ISeries, Series } from 'app/shared/model/series.model';
import { SeriesService } from './series.service';
import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from 'app/entities/brand/brand.service';
import { IBrandType } from 'app/shared/model/brand-type.model';
import { BrandTypeService } from 'app/entities/brand-type/brand-type.service';

@Component({
  selector: 'jhi-series-update',
  templateUrl: './series-update.component.html'
})
export class SeriesUpdateComponent implements OnInit {
  isSaving: boolean;

  brands: IBrand[];

  brandtypes: IBrandType[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    priceStart: [],
    priceEnd: [],
    hasPrice: [null, [Validators.required]],
    brand: [],
    brandType: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected seriesService: SeriesService,
    protected brandService: BrandService,
    protected brandTypeService: BrandTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ series }) => {
      this.updateForm(series);
    });
    this.brandService
      .query()
      .subscribe((res: HttpResponse<IBrand[]>) => (this.brands = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.brandTypeService
      .query()
      .subscribe((res: HttpResponse<IBrandType[]>) => (this.brandtypes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(series: ISeries) {
    this.editForm.patchValue({
      id: series.id,
      name: series.name,
      priceStart: series.priceStart,
      priceEnd: series.priceEnd,
      hasPrice: series.hasPrice,
      brand: series.brand,
      brandType: series.brandType
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const series = this.createFromForm();
    if (series.id !== undefined) {
      this.subscribeToSaveResponse(this.seriesService.update(series));
    } else {
      this.subscribeToSaveResponse(this.seriesService.create(series));
    }
  }

  private createFromForm(): ISeries {
    return {
      ...new Series(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      priceStart: this.editForm.get(['priceStart']).value,
      priceEnd: this.editForm.get(['priceEnd']).value,
      hasPrice: this.editForm.get(['hasPrice']).value,
      brand: this.editForm.get(['brand']).value,
      brandType: this.editForm.get(['brandType']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeries>>) {
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

  trackBrandById(index: number, item: IBrand) {
    return item.id;
  }

  trackBrandTypeById(index: number, item: IBrandType) {
    return item.id;
  }
}
