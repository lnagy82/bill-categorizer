export interface ICategory {
  id?: number;
  name?: string;
  parentName?: string;
  parentId?: number;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public parentName?: string, public parentId?: number) {}
}
