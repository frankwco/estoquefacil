import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EstoquefacilTestModule } from '../../../test.module';
import { ItensPedidoUpdateComponent } from 'app/entities/itens-pedido/itens-pedido-update.component';
import { ItensPedidoService } from 'app/entities/itens-pedido/itens-pedido.service';
import { ItensPedido } from 'app/shared/model/itens-pedido.model';

describe('Component Tests', () => {
  describe('ItensPedido Management Update Component', () => {
    let comp: ItensPedidoUpdateComponent;
    let fixture: ComponentFixture<ItensPedidoUpdateComponent>;
    let service: ItensPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [ItensPedidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ItensPedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItensPedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItensPedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItensPedido(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItensPedido();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
