import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { ProdutoComponent } from './produto.component';
import { ProdutoDetailComponent } from './produto-detail.component';
import { ProdutoUpdateComponent } from './produto-update.component';
import { IProduto } from 'app/shared/model/produto.model';

@Injectable({ providedIn: 'root' })
export class ProdutoResolve implements Resolve<IProduto> {
  constructor(private service: ProdutoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((produto: HttpResponse<Produto>) => produto.body));
    }
    return of(new Produto());
  }
}

export const produtoRoute: Routes = [
  {
    path: '',
    component: ProdutoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'estoquefacilApp.produto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdutoDetailComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.produto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdutoUpdateComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.produto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdutoUpdateComponent,
    resolve: {
      produto: ProdutoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.produto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
