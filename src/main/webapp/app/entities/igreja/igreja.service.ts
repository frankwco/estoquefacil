import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIgreja } from 'app/shared/model/igreja.model';

type EntityResponseType = HttpResponse<IIgreja>;
type EntityArrayResponseType = HttpResponse<IIgreja[]>;

@Injectable({ providedIn: 'root' })
export class IgrejaService {
  public resourceUrl = SERVER_API_URL + 'api/igrejas';

  constructor(protected http: HttpClient) {}

  create(igreja: IIgreja): Observable<EntityResponseType> {
    return this.http.post<IIgreja>(this.resourceUrl, igreja, { observe: 'response' });
  }

  update(igreja: IIgreja): Observable<EntityResponseType> {
    return this.http.put<IIgreja>(this.resourceUrl, igreja, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIgreja>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIgreja[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
