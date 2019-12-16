import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';

@Component({
  templateUrl: './produto-delete-dialog.component.html'
})
export class ProdutoDeleteDialogComponent {
  produto: IProduto;

  constructor(protected produtoService: ProdutoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produtoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'produtoListModification',
        content: 'Deleted an produto'
      });
      this.activeModal.dismiss(true);
    });
  }
}
