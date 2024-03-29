import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Series } from 'app/shared/model/series.model';
import { SeriesService } from './series.service';
import { SeriesComponent } from './series.component';
import { SeriesDetailComponent } from './series-detail.component';
import { SeriesUpdateComponent } from './series-update.component';
import { ISeries } from 'app/shared/model/series.model';

@Injectable({ providedIn: 'root' })
export class SeriesResolve implements Resolve<ISeries> {
  constructor(private service: SeriesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeries> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((series: HttpResponse<Series>) => series.body));
    }
    return of(new Series());
  }
}

export const seriesRoute: Routes = [
  {
    path: '',
    component: SeriesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'carbrandApp.series.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SeriesDetailComponent,
    resolve: {
      series: SeriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.series.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SeriesUpdateComponent,
    resolve: {
      series: SeriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.series.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SeriesUpdateComponent,
    resolve: {
      series: SeriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'carbrandApp.series.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
