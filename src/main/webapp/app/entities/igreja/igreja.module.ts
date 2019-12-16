import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EstoquefacilSharedModule } from 'app/shared/shared.module';
import { IgrejaComponent } from './igreja.component';
import { IgrejaDetailComponent } from './igreja-detail.component';
import { IgrejaUpdateComponent } from './igreja-update.component';
import { IgrejaDeleteDialogComponent } from './igreja-delete-dialog.component';
import { igrejaRoute } from './igreja.route';

@NgModule({
  imports: [EstoquefacilSharedModule, RouterModule.forChild(igrejaRoute)],
  declarations: [IgrejaComponent, IgrejaDetailComponent, IgrejaUpdateComponent, IgrejaDeleteDialogComponent],
  entryComponents: [IgrejaDeleteDialogComponent]
})
export class EstoquefacilIgrejaModule {}
