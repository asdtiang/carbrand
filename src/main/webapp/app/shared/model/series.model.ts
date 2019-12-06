import { IBrand } from 'app/shared/model/brand.model';
import { IBrandType } from 'app/shared/model/brand-type.model';

export interface ISeries {
  id?: number;
  name?: string;
  priceStart?: number;
  priceEnd?: number;
  hasPrice?: boolean;
  brand?: IBrand;
  brandType?: IBrandType;
}

export class Series implements ISeries {
  constructor(
    public id?: number,
    public name?: string,
    public priceStart?: number,
    public priceEnd?: number,
    public hasPrice?: boolean,
    public brand?: IBrand,
    public brandType?: IBrandType
  ) {
    this.hasPrice = this.hasPrice || false;
  }
}
