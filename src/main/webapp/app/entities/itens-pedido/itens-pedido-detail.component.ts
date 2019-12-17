import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItensPedido } from 'app/shared/model/itens-pedido.model';

@Component({
  selector: 'jhi-itens-pedido-detail',
  templateUrl: './itens-pedido-detail.component.html'
})
export class ItensPedidoDetailComponent implements OnInit {
  itensPedido: IItensPedido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itensPedido }) => {
      this.itensPedido = itensPedido;
    });
  }

  previousState() {
    window.history.back();
  }
}
