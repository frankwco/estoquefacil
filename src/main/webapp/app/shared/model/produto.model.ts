export interface IProduto {
  id?: number;
  descricao?: string;
  codigo?: string;
  valor?: number;
  quantidadeEstoque?: number;
  imagemContentType?: string;
  imagem?: any;
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public descricao?: string,
    public codigo?: string,
    public valor?: number,
    public quantidadeEstoque?: number,
    public imagemContentType?: string,
    public imagem?: any
  ) {}
}
