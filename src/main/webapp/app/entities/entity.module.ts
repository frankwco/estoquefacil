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
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EstoquefacilEntityModule {}
