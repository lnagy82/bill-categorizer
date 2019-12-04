import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BillcategorizerTestModule } from '../../../test.module';
import { AccountItemDeleteDialogComponent } from 'app/entities/account-item/account-item-delete-dialog.component';
import { AccountItemService } from 'app/entities/account-item/account-item.service';

describe('Component Tests', () => {
  describe('AccountItem Management Delete Component', () => {
    let comp: AccountItemDeleteDialogComponent;
    let fixture: ComponentFixture<AccountItemDeleteDialogComponent>;
    let service: AccountItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BillcategorizerTestModule],
        declarations: [AccountItemDeleteDialogComponent]
      })
        .overrideTemplate(AccountItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountItemService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
