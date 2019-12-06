import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'brand',
        loadChildren: () => import('./brand/brand.module').then(m => m.CarbrandBrandModule)
      },
      {
        path: 'brand-type',
        loadChildren: () => import('./brand-type/brand-type.module').then(m => m.CarbrandBrandTypeModule)
      },
      {
        path: 'series',
        loadChildren: () => import('./series/series.module').then(m => m.CarbrandSeriesModule)
      },
      {
        path: 'car-type',
        loadChildren: () => import('./car-type/car-type.module').then(m => m.CarbrandCarTypeModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CarbrandEntityModule {}
