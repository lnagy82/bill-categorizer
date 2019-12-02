import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BillcategorizerTestModule } from '../../../test.module';
import { AccountItemUpdateComponent } from 'app/entities/account-item/account-item-update.component';
import { AccountItemService } from 'app/entities/account-item/account-item.service';
import { AccountItem } from 'app/shared/model/account-item.model';

describe('Component Tests', () => {
  describe('AccountItem Management Update Component', () => {
    let comp: AccountItemUpdateComponent;
    let fixture: ComponentFixture<AccountItemUpdateComponent>;
    let service: AccountItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BillcategorizerTestModule],
        declarations: [AccountItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountItem(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountItem();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
