import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IBrandType, BrandType } from 'app/shared/model/brand-type.model';
import { BrandTypeService } from './brand-type.service';
import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from 'app/entities/brand/brand.service';

@Component({
  selector: 'jhi-brand-type-update',
  templateUrl: './brand-type-update.component.html'
})
export class BrandTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  brands: IBrand[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    brand: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected brandTypeService: BrandTypeService,
    protected brandService: BrandService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ brandType }) => {
      this.updateForm(brandType);
    });
    this.brandService
      .query()
      .subscribe((res: HttpResponse<IBrand[]>) => (this.brands = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(brandType: IBrandType) {
    this.editForm.patchValue({
      id: brandType.id,
      name: brandType.name,
      brand: brandType.brand
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const brandType = this.createFromForm();
    if (brandType.id !== undefined) {
      this.subscribeToSaveResponse(this.brandTypeService.update(brandType));
    } else {
      this.subscribeToSaveResponse(this.brandTypeService.create(brandType));
    }
  }

  private createFromForm(): IBrandType {
    return {
      ...new BrandType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      brand: this.editForm.get(['brand']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBrandType>>) {
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
}
