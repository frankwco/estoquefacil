import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EstoquefacilTestModule } from '../../../test.module';
import { IgrejaUpdateComponent } from 'app/entities/igreja/igreja-update.component';
import { IgrejaService } from 'app/entities/igreja/igreja.service';
import { Igreja } from 'app/shared/model/igreja.model';

describe('Component Tests', () => {
  describe('Igreja Management Update Component', () => {
    let comp: IgrejaUpdateComponent;
    let fixture: ComponentFixture<IgrejaUpdateComponent>;
    let service: IgrejaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [IgrejaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IgrejaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IgrejaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IgrejaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Igreja(123);
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
        const entity = new Igreja();
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
