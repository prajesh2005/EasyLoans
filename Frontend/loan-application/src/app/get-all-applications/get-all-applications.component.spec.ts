import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAllApplicationsComponent } from './get-all-applications.component';

describe('GetAllApplicationsComponent', () => {
  let component: GetAllApplicationsComponent;
  let fixture: ComponentFixture<GetAllApplicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GetAllApplicationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GetAllApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
