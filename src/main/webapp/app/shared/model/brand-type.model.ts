import { IBrand } from 'app/shared/model/brand.model';

export interface IBrandType {
  id?: number;
  name?: string;
  brand?: IBrand;
}

export class BrandType implements IBrandType {
  constructor(public id?: number, public name?: string, public brand?: IBrand) {}
}
