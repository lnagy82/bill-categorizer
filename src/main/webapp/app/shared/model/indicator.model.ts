export interface IIndicator {
  id?: number;
  text?: string;
  categoryName?: string;
  categoryId?: number;
}

export class Indicator implements IIndicator {
  constructor(public id?: number, public text?: string, public categoryName?: string, public categoryId?: number) {}
}
