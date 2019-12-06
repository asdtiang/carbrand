import { ISeries } from 'app/shared/model/series.model';

export interface ICarType {
  id?: number;
  name?: string;
  yearName?: string;
  hasProduction?: boolean;
  priceStart?: number;
  priceEnd?: number;
  hasPrice?: boolean;
  series?: ISeries;
}

export class CarType implements ICarType {
  constructor(
    public id?: number,
    public name?: string,
    public yearName?: string,
    public hasProduction?: boolean,
    public priceStart?: number,
    public priceEnd?: number,
    public hasPrice?: boolean,
    public series?: ISeries
  ) {
    this.hasProduction = this.hasProduction || false;
    this.hasPrice = this.hasPrice || false;
  }
}
