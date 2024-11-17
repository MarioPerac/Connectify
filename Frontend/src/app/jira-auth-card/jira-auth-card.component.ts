import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { JiraService } from '../services/jira.service';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-jira-auth-card',
  standalone: true,
  imports: [MatButtonModule, MatCardModule, ToolbarComponent],
  templateUrl: './jira-auth-card.component.html',
  styleUrl: './jira-auth-card.component.css'
})
export class JiraAuthCardComponent {

  constructor(private jiraService: JiraService){

  }
  onAuthorizeClick(): void {
    this.jiraService.authorize();
  }
}
