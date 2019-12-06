import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { BrandType } from 'app/shared/model/brand-type.model';
import { BrandTypeService } from './brand-type.service';
import { BrandTypeComponent } from './brand-type.component';
import { BrandTypeDetailComponent } from './brand-type-detail.component';
import { BrandTypeUpdateComponent } from './brand-type-update.component';
import { IBrandType } from 'app/shared/model/brand-type.model';

@Injectable({ providedIn: 'root' })
export class BrandTypeResolve implements Resolve<IBrandType> {
  constructor(private service: BrandTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBrandType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((brandType: HttpResponse<BrandType>) => brandType.body));
    }
    return of(new BrandType());
  }
}

export const brandTypeRoute: Routes = [
  {
    path: '',
    component: BrandTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'carbrandApp.brandType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BrandTypeDetailComponent,
    resolve: {
      brandType: BrandTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.brandType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BrandTypeUpdateComponent,
    resolve: {
      brandType: BrandTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.brandType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BrandTypeUpdateComponent,
    resolve: {
      brandType: BrandTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.brandType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
