import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectiveComponent } from './elective.component';

describe('ElectiveComponent', () => {
  let component: ElectiveComponent;
  let fixture: ComponentFixture<ElectiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElectiveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
