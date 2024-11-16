import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JiraAuthCardComponent } from './jira-auth-card.component';

describe('JiraAuthCardComponent', () => {
  let component: JiraAuthCardComponent;
  let fixture: ComponentFixture<JiraAuthCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JiraAuthCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JiraAuthCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
