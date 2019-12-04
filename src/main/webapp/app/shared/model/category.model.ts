export interface ICategory {
  id?: number;
  name?: string;
  parentId?: number;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public parentId?: number) {}
}
