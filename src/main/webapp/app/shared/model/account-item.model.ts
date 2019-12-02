import { Moment } from 'moment';
import { Category } from 'app/shared/model/enumerations/category.model';

export interface IAccountItem {
  id?: number;
  date?: Moment;
  transactionType?: string;
  description?: string;
  amount?: number;
  currency?: string;
  category?: Category;
}

export class AccountItem implements IAccountItem {
  constructor(
    public id?: number,
    public date?: Moment,
    public transactionType?: string,
    public description?: string,
    public amount?: number,
    public currency?: string,
    public category?: Category
  ) {}
}
