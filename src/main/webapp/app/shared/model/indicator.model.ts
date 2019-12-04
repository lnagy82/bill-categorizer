export interface IIndicator {
  id?: number;
  text?: string;
  categoryId?: number;
}

export class Indicator implements IIndicator {
  constructor(public id?: number, public text?: string, public categoryId?: number) {}
}
