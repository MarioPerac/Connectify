import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JiraProjectsComponent } from './jira-projects.component';

describe('JiraProjectsComponent', () => {
  let component: JiraProjectsComponent;
  let fixture: ComponentFixture<JiraProjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JiraProjectsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JiraProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
