import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBrandType } from 'app/shared/model/brand-type.model';

@Component({
  selector: 'jhi-brand-type-detail',
  templateUrl: './brand-type-detail.component.html'
})
export class BrandTypeDetailComponent implements OnInit {
  brandType: IBrandType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ brandType }) => {
      this.brandType = brandType;
    });
  }

  previousState() {
    window.history.back();
  }
}
