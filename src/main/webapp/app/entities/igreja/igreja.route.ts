import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Igreja } from 'app/shared/model/igreja.model';
import { IgrejaService } from './igreja.service';
import { IgrejaComponent } from './igreja.component';
import { IgrejaDetailComponent } from './igreja-detail.component';
import { IgrejaUpdateComponent } from './igreja-update.component';
import { IIgreja } from 'app/shared/model/igreja.model';

@Injectable({ providedIn: 'root' })
export class IgrejaResolve implements Resolve<IIgreja> {
  constructor(private service: IgrejaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIgreja> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((igreja: HttpResponse<Igreja>) => igreja.body));
    }
    return of(new Igreja());
  }
}

export const igrejaRoute: Routes = [
  {
    path: '',
    component: IgrejaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'estoquefacilApp.igreja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IgrejaDetailComponent,
    resolve: {
      igreja: IgrejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.igreja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IgrejaUpdateComponent,
    resolve: {
      igreja: IgrejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.igreja.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IgrejaUpdateComponent,
    resolve: {
      igreja: IgrejaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'estoquefacilApp.igreja.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
