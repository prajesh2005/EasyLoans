import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessLoanApplicationComponent } from './process-loan-application.component';

describe('ProcessLoanApplicationComponent', () => {
  let component: ProcessLoanApplicationComponent;
  let fixture: ComponentFixture<ProcessLoanApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProcessLoanApplicationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProcessLoanApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
