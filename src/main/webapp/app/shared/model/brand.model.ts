export interface IBrand {
  id?: number;
  name?: string;
  firstLetter?: string;
  img?: string;
}

export class Brand implements IBrand {
  constructor(public id?: number, public name?: string, public firstLetter?: string, public img?: string) {}
}
