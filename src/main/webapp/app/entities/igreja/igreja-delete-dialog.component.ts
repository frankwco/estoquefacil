import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIgreja } from 'app/shared/model/igreja.model';
import { IgrejaService } from './igreja.service';

@Component({
  templateUrl: './igreja-delete-dialog.component.html'
})
export class IgrejaDeleteDialogComponent {
  igreja: IIgreja;

  constructor(protected igrejaService: IgrejaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.igrejaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'igrejaListModification',
        content: 'Deleted an igreja'
      });
      this.activeModal.dismiss(true);
    });
  }
}
