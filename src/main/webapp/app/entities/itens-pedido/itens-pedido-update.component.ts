import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IItensPedido, ItensPedido } from 'app/shared/model/itens-pedido.model';
import { ItensPedidoService } from './itens-pedido.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido/pedido.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';

@Component({
  selector: 'jhi-itens-pedido-update',
  templateUrl: './itens-pedido-update.component.html'
})
export class ItensPedidoUpdateComponent implements OnInit {
  isSaving: boolean;

  pedidos: IPedido[];

  produtos: IProduto[];

  editForm = this.fb.group({
    id: [],
    quantidadeProduto: [null, [Validators.required]],
    valorProduto: [null, [Validators.required]],
    pedido: [],
    produto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected itensPedidoService: ItensPedidoService,
    protected pedidoService: PedidoService,
    protected produtoService: ProdutoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ itensPedido }) => {
      this.updateForm(itensPedido);
    });
    this.pedidoService
      .query()
      .subscribe((res: HttpResponse<IPedido[]>) => (this.pedidos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.produtoService
      .query()
      .subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(itensPedido: IItensPedido) {
    this.editForm.patchValue({
      id: itensPedido.id,
      quantidadeProduto: itensPedido.quantidadeProduto,
      valorProduto: itensPedido.valorProduto,
      pedido: itensPedido.pedido,
      produto: itensPedido.produto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const itensPedido = this.createFromForm();
    if (itensPedido.id !== undefined) {
      this.subscribeToSaveResponse(this.itensPedidoService.update(itensPedido));
    } else {
      this.subscribeToSaveResponse(this.itensPedidoService.create(itensPedido));
    }
  }

  private createFromForm(): IItensPedido {
    return {
      ...new ItensPedido(),
      id: this.editForm.get(['id']).value,
      quantidadeProduto: this.editForm.get(['quantidadeProduto']).value,
      valorProduto: this.editForm.get(['valorProduto']).value,
      pedido: this.editForm.get(['pedido']).value,
      produto: this.editForm.get(['produto']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItensPedido>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPedidoById(index: number, item: IPedido) {
    return item.id;
  }

  trackProdutoById(index: number, item: IProduto) {
    return item.id;
  }
}
