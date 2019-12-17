import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItensPedido } from 'app/shared/model/itens-pedido.model';
import { ItensPedidoService } from './itens-pedido.service';
import { ItensPedidoComponent } from './itens-pedido.component';
import { ItensPedidoDetailComponent } from './itens-pedido-detail.component';
import { ItensPedidoUpdateComponent } from './itens-pedido-update.component';
import { IItensPedido } from 'app/shared/model/itens-pedido.model';

@Injectable({ providedIn: 'root' })
export class ItensPedidoResolve implements Resolve<IItensPedido> {
  constructor(private service: ItensPedidoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItensPedido> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((itensPedido: HttpResponse<ItensPedido>) => itensPedido.body));
    }
    return of(new ItensPedido());
  }
}

export const itensPedidoRoute: Routes = [
  {
    path: '',
    component: ItensPedidoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'estoquefacilApp.itensPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ItensPedidoDetailComponent,
    resolve: {
      itensPedido: ItensPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.itensPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ItensPedidoUpdateComponent,
    resolve: {
      itensPedido: ItensPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.itensPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ItensPedidoUpdateComponent,
    resolve: {
      itensPedido: ItensPedidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.itensPedido.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
