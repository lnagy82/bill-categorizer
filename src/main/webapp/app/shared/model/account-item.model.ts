import { Moment } from 'moment';

export interface IAccountItem {
  id?: number;
  date?: Moment;
  transactionType?: string;
  description?: string;
  amount?: number;
  currency?: string;
  categoryName?: string;
  categoryId?: number;
}

export class AccountItem implements IAccountItem {
  constructor(
    public id?: number,
    public date?: Moment,
    public transactionType?: string,
    public description?: string,
    public amount?: number,
    public currency?: string,
    public categoryName?: string,
    public categoryId?: number
  ) {}
}
