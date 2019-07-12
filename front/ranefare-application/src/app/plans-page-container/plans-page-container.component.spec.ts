import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlansPageContainerComponent } from './plans-page-container.component';

describe('PlansPageContainerComponent', () => {
  let component: PlansPageContainerComponent;
  let fixture: ComponentFixture<PlansPageContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlansPageContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlansPageContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
