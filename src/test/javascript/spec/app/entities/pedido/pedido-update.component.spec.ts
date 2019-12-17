import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EstoquefacilTestModule } from '../../../test.module';
import { PedidoUpdateComponent } from 'app/entities/pedido/pedido-update.component';
import { PedidoService } from 'app/entities/pedido/pedido.service';
import { Pedido } from 'app/shared/model/pedido.model';

describe('Component Tests', () => {
  describe('Pedido Management Update Component', () => {
    let comp: PedidoUpdateComponent;
    let fixture: ComponentFixture<PedidoUpdateComponent>;
    let service: PedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [PedidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pedido(123);
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
        const entity = new Pedido();
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
