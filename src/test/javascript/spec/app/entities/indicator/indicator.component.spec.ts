import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillcategorizerTestModule } from '../../../test.module';
import { IndicatorComponent } from 'app/entities/indicator/indicator.component';
import { IndicatorService } from 'app/entities/indicator/indicator.service';
import { Indicator } from 'app/shared/model/indicator.model';

describe('Component Tests', () => {
  describe('Indicator Management Component', () => {
    let comp: IndicatorComponent;
    let fixture: ComponentFixture<IndicatorComponent>;
    let service: IndicatorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BillcategorizerTestModule],
        declarations: [IndicatorComponent],
        providers: []
      })
        .overrideTemplate(IndicatorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicatorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicatorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Indicator(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indicators[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
