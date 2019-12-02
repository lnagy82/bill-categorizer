import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BillcategorizerTestModule } from '../../../test.module';
import { AccountItemDetailComponent } from 'app/entities/account-item/account-item-detail.component';
import { AccountItem } from 'app/shared/model/account-item.model';

describe('Component Tests', () => {
  describe('AccountItem Management Detail Component', () => {
    let comp: AccountItemDetailComponent;
    let fixture: ComponentFixture<AccountItemDetailComponent>;
    const route = ({ data: of({ accountItem: new AccountItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BillcategorizerTestModule],
        declarations: [AccountItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
