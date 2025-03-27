import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PesquisaCreditosComponent } from './pesquisa-creditos.component';

describe('PesquisaCreditosComponent', () => {
  let component: PesquisaCreditosComponent;
  let fixture: ComponentFixture<PesquisaCreditosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PesquisaCreditosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PesquisaCreditosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
