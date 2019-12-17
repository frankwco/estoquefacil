import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EstoquefacilSharedModule } from 'app/shared/shared.module';
import { ItensPedidoComponent } from './itens-pedido.component';
import { ItensPedidoDetailComponent } from './itens-pedido-detail.component';
import { ItensPedidoUpdateComponent } from './itens-pedido-update.component';
import { ItensPedidoDeleteDialogComponent } from './itens-pedido-delete-dialog.component';
import { itensPedidoRoute } from './itens-pedido.route';

@NgModule({
  imports: [EstoquefacilSharedModule, RouterModule.forChild(itensPedidoRoute)],
  declarations: [ItensPedidoComponent, ItensPedidoDetailComponent, ItensPedidoUpdateComponent, ItensPedidoDeleteDialogComponent],
  entryComponents: [ItensPedidoDeleteDialogComponent]
})
export class EstoquefacilItensPedidoModule {}
