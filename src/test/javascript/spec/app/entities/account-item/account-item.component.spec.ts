import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillcategorizerTestModule } from '../../../test.module';
import { AccountItemComponent } from 'app/entities/account-item/account-item.component';
import { AccountItemService } from 'app/entities/account-item/account-item.service';
import { AccountItem } from 'app/shared/model/account-item.model';

describe('Component Tests', () => {
  describe('AccountItem Management Component', () => {
    let comp: AccountItemComponent;
    let fixture: ComponentFixture<AccountItemComponent>;
    let service: AccountItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BillcategorizerTestModule],
        declarations: [AccountItemComponent],
        providers: []
      })
        .overrideTemplate(AccountItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AccountItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accountItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
