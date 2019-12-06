import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CarType } from 'app/shared/model/car-type.model';
import { CarTypeService } from './car-type.service';
import { CarTypeComponent } from './car-type.component';
import { CarTypeDetailComponent } from './car-type-detail.component';
import { CarTypeUpdateComponent } from './car-type-update.component';
import { ICarType } from 'app/shared/model/car-type.model';

@Injectable({ providedIn: 'root' })
export class CarTypeResolve implements Resolve<ICarType> {
  constructor(private service: CarTypeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((carType: HttpResponse<CarType>) => carType.body));
    }
    return of(new CarType());
  }
}

export const carTypeRoute: Routes = [
  {
    path: '',
    component: CarTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'carbrandApp.carType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarTypeDetailComponent,
    resolve: {
      carType: CarTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.carType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarTypeUpdateComponent,
    resolve: {
      carType: CarTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.carType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarTypeUpdateComponent,
    resolve: {
      carType: CarTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.carType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
