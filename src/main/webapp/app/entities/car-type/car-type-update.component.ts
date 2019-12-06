import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ICarType, CarType } from 'app/shared/model/car-type.model';
import { CarTypeService } from './car-type.service';
import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from 'app/entities/series/series.service';

@Component({
  selector: 'jhi-car-type-update',
  templateUrl: './car-type-update.component.html'
})
export class CarTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  series: ISeries[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    yearName: [null, [Validators.required]],
    hasProduction: [null, [Validators.required]],
    priceStart: [],
    priceEnd: [],
    hasPrice: [null, [Validators.required]],
    series: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carTypeService: CarTypeService,
    protected seriesService: SeriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carType }) => {
      this.updateForm(carType);
    });
    this.seriesService
      .query()
      .subscribe((res: HttpResponse<ISeries[]>) => (this.series = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carType: ICarType) {
    this.editForm.patchValue({
      id: carType.id,
      name: carType.name,
      yearName: carType.yearName,
      hasProduction: carType.hasProduction,
      priceStart: carType.priceStart,
      priceEnd: carType.priceEnd,
      hasPrice: carType.hasPrice,
      series: carType.series
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carType = this.createFromForm();
    if (carType.id !== undefined) {
      this.subscribeToSaveResponse(this.carTypeService.update(carType));
    } else {
      this.subscribeToSaveResponse(this.carTypeService.create(carType));
    }
  }

  private createFromForm(): ICarType {
    return {
      ...new CarType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      yearName: this.editForm.get(['yearName']).value,
      hasProduction: this.editForm.get(['hasProduction']).value,
      priceStart: this.editForm.get(['priceStart']).value,
      priceEnd: this.editForm.get(['priceEnd']).value,
      hasPrice: this.editForm.get(['hasPrice']).value,
      series: this.editForm.get(['series']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarType>>) {
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

  trackSeriesById(index: number, item: ISeries) {
    return item.id;
  }
}
