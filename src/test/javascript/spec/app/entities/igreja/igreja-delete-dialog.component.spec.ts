import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EstoquefacilTestModule } from '../../../test.module';
import { IgrejaDeleteDialogComponent } from 'app/entities/igreja/igreja-delete-dialog.component';
import { IgrejaService } from 'app/entities/igreja/igreja.service';

describe('Component Tests', () => {
  describe('Igreja Management Delete Component', () => {
    let comp: IgrejaDeleteDialogComponent;
    let fixture: ComponentFixture<IgrejaDeleteDialogComponent>;
    let service: IgrejaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EstoquefacilTestModule],
        declarations: [IgrejaDeleteDialogComponent]
      })
        .overrideTemplate(IgrejaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IgrejaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IgrejaService);
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
