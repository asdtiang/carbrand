import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarbrandSharedModule } from 'app/shared/shared.module';
import { BrandTypeComponent } from './brand-type.component';
import { BrandTypeDetailComponent } from './brand-type-detail.component';
import { BrandTypeUpdateComponent } from './brand-type-update.component';
import { BrandTypeDeleteDialogComponent } from './brand-type-delete-dialog.component';
import { brandTypeRoute } from './brand-type.route';

@NgModule({
  imports: [CarbrandSharedModule, RouterModule.forChild(brandTypeRoute)],
  declarations: [BrandTypeComponent, BrandTypeDetailComponent, BrandTypeUpdateComponent, BrandTypeDeleteDialogComponent],
  entryComponents: [BrandTypeDeleteDialogComponent]
})
export class CarbrandBrandTypeModule {}
