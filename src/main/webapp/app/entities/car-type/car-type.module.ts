import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarbrandSharedModule } from 'app/shared/shared.module';
import { CarTypeComponent } from './car-type.component';
import { CarTypeDetailComponent } from './car-type-detail.component';
import { CarTypeUpdateComponent } from './car-type-update.component';
import { CarTypeDeleteDialogComponent } from './car-type-delete-dialog.component';
import { carTypeRoute } from './car-type.route';

@NgModule({
  imports: [CarbrandSharedModule, RouterModule.forChild(carTypeRoute)],
  declarations: [CarTypeComponent, CarTypeDetailComponent, CarTypeUpdateComponent, CarTypeDeleteDialogComponent],
  entryComponents: [CarTypeDeleteDialogComponent]
})
export class CarbrandCarTypeModule {}
