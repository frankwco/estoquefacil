import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstoquefacilTestModule } from '../../../test.module';
import { ItensPedidoDetailComponent } from 'app/entities/itens-pedido/itens-pedido-detail.component';
import { ItensPedido } from 'app/shared/model/itens-pedido.model';

describe('Component Tests', () => {
  describe('ItensPedido Management Detail Component', () => {
    let comp: ItensPedidoDetailComponent;
    let fixture: ComponentFixture<ItensPedidoDetailComponent>;
    const route = ({ data: of({ itensPedido: new ItensPedido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [ItensPedidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ItensPedidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItensPedidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itensPedido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
