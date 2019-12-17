import { IPedido } from 'app/shared/model/pedido.model';
import { IProduto } from 'app/shared/model/produto.model';

export interface IItensPedido {
  id?: number;
  quantidadeProduto?: number;
  valorProduto?: number;
  pedido?: IPedido;
  produto?: IProduto;
}

export class ItensPedido implements IItensPedido {
  constructor(
    public id?: number,
    public quantidadeProduto?: number,
    public valorProduto?: number,
    public pedido?: IPedido,
    public produto?: IProduto
  ) {}
}
