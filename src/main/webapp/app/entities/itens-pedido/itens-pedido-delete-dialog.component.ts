import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItensPedido } from 'app/shared/model/itens-pedido.model';
import { ItensPedidoService } from './itens-pedido.service';

@Component({
  templateUrl: './itens-pedido-delete-dialog.component.html'
})
export class ItensPedidoDeleteDialogComponent {
  itensPedido: IItensPedido;

  constructor(
    protected itensPedidoService: ItensPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.itensPedidoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'itensPedidoListModification',
        content: 'Deleted an itensPedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}
