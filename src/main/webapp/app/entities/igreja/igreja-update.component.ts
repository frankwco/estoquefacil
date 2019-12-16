import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IIgreja, Igreja } from 'app/shared/model/igreja.model';
import { IgrejaService } from './igreja.service';

@Component({
  selector: 'jhi-igreja-update',
  templateUrl: './igreja-update.component.html'
})
export class IgrejaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nomeLocalidade: [null, [Validators.required]],
    numeroLocalidade: [null, [Validators.required]]
  });

  constructor(protected igrejaService: IgrejaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ igreja }) => {
      this.updateForm(igreja);
    });
  }

  updateForm(igreja: IIgreja) {
    this.editForm.patchValue({
      id: igreja.id,
      nomeLocalidade: igreja.nomeLocalidade,
      numeroLocalidade: igreja.numeroLocalidade
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const igreja = this.createFromForm();
    if (igreja.id !== undefined) {
      this.subscribeToSaveResponse(this.igrejaService.update(igreja));
    } else {
      this.subscribeToSaveResponse(this.igrejaService.create(igreja));
    }
  }

  private createFromForm(): IIgreja {
    return {
      ...new Igreja(),
      id: this.editForm.get(['id']).value,
      nomeLocalidade: this.editForm.get(['nomeLocalidade']).value,
      numeroLocalidade: this.editForm.get(['numeroLocalidade']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIgreja>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
