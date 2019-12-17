import { IIgreja } from 'app/shared/model/igreja.model';

export interface IPedido {
  id?: number;
  observacao?: string;
  igreja?: IIgreja;
}

export class Pedido implements IPedido {
  constructor(public id?: number, public observacao?: string, public igreja?: IIgreja) {}
}
