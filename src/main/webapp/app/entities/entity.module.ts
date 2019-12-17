import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'igreja',
        loadChildren: () => import('./igreja/igreja.module').then(m => m.EstoquefacilIgrejaModule)
      },
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.EstoquefacilProdutoModule)
      },
      {
        path: 'pedido',
        loadChildren: () => import('./pedido/pedido.module').then(m => m.EstoquefacilPedidoModule)
      },
      {
        path: 'itens-pedido',
        loadChildren: () => import('./itens-pedido/itens-pedido.module').then(m => m.EstoquefacilItensPedidoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EstoquefacilEntityModule {}
