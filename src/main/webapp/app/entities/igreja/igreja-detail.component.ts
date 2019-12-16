import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIgreja } from 'app/shared/model/igreja.model';

@Component({
  selector: 'jhi-igreja-detail',
  templateUrl: './igreja-detail.component.html'
})
export class IgrejaDetailComponent implements OnInit {
  igreja: IIgreja;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ igreja }) => {
      this.igreja = igreja;
    });
  }

  previousState() {
    window.history.back();
  }
}
