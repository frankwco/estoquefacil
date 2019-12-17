import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { PedidoComponent } from './pedido.component';
import { PedidoDetailComponent } from './pedido-detail.component';
import { PedidoUpdateComponent } from './pedido-update.component';
import { IPedido } from 'app/shared/model/pedido.model';

@Injectable({ providedIn: 'root' })
export class PedidoResolve implements Resolve<IPedido> {
  constructor(private service: PedidoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPedido> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((pedido: HttpResponse<Pedido>) => pedido.body));
    }
    return of(new Pedido());
  }
}

export const pedidoRoute: Routes = [
  {
    path: '',
    component: PedidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'estoquefacilApp.pedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PedidoDetailComponent,
    resolve: {
      pedido: PedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.pedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PedidoUpdateComponent,
    resolve: {
      pedido: PedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.pedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PedidoUpdateComponent,
    resolve: {
      pedido: PedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.pedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
