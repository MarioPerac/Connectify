import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebhookCreatedComponent } from './webhook-created.component';

describe('WebhookCreatedComponent', () => {
  let component: WebhookCreatedComponent;
  let fixture: ComponentFixture<WebhookCreatedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WebhookCreatedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WebhookCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
