import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EstoquefacilTestModule } from '../../../test.module';
import { ItensPedidoDeleteDialogComponent } from 'app/entities/itens-pedido/itens-pedido-delete-dialog.component';
import { ItensPedidoService } from 'app/entities/itens-pedido/itens-pedido.service';

describe('Component Tests', () => {
  describe('ItensPedido Management Delete Component', () => {
    let comp: ItensPedidoDeleteDialogComponent;
    let fixture: ComponentFixture<ItensPedidoDeleteDialogComponent>;
    let service: ItensPedidoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [ItensPedidoDeleteDialogComponent]
      })
        .overrideTemplate(ItensPedidoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItensPedidoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItensPedidoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
