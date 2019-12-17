import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPedido } from 'app/shared/model/pedido.model';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
  }

  previousState() {
    window.history.back();
  }
}
