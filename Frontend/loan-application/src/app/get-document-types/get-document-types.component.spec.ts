import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetDocumentTypesComponent } from './get-document-types.component';

describe('GetDocumentTypesComponent', () => {
  let component: GetDocumentTypesComponent;
  let fixture: ComponentFixture<GetDocumentTypesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GetDocumentTypesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetDocumentTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
