import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IItensPedido } from 'app/shared/model/itens-pedido.model';

type EntityResponseType = HttpResponse<IItensPedido>;
type EntityArrayResponseType = HttpResponse<IItensPedido[]>;

@Injectable({ providedIn: 'root' })
export class ItensPedidoService {
  public resourceUrl = SERVER_API_URL + 'api/itens-pedidos';

  constructor(protected http: HttpClient) {}

  create(itensPedido: IItensPedido): Observable<EntityResponseType> {
    return this.http.post<IItensPedido>(this.resourceUrl, itensPedido, { observe: 'response' });
  }

  update(itensPedido: IItensPedido): Observable<EntityResponseType> {
    return this.http.put<IItensPedido>(this.resourceUrl, itensPedido, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItensPedido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItensPedido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
