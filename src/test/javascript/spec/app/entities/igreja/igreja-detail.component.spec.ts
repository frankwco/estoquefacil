import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstoquefacilTestModule } from '../../../test.module';
import { IgrejaDetailComponent } from 'app/entities/igreja/igreja-detail.component';
import { Igreja } from 'app/shared/model/igreja.model';

describe('Component Tests', () => {
  describe('Igreja Management Detail Component', () => {
    let comp: IgrejaDetailComponent;
    let fixture: ComponentFixture<IgrejaDetailComponent>;
    const route = ({ data: of({ igreja: new Igreja(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [IgrejaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IgrejaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IgrejaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.igreja).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
