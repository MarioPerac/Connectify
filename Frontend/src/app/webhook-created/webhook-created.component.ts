import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SlackService } from '../services/slack.service';
import { LoginService } from '../services/login.service';
import { AutomationRequest } from '../models/automation_request.model';
import { UserService } from '../services/user.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-webhook-created',
  standalone: true,
  imports: [CommonModule, MatCardModule, ToolbarComponent],
  templateUrl: './webhook-created.component.html',
  styleUrls: ['./webhook-created.component.css']
})
export class WebhookCreatedComponent implements OnInit {
  statusMessage: string = ''; 
  statusClass: string = '';

  constructor(
    private route: ActivatedRoute, 
    private slackService: SlackService, 
    private loginService: LoginService, 
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const code = params['code'];

      if (code) {
        this.slackService.getToken(code).subscribe(
          (response) => {
            const slackWebhookUrl = response.incoming_webhook.url;
            const cloudId = sessionStorage.getItem("cloud_id");
            const jiraWebhookId = sessionStorage.getItem("webhook_id");
            const projectName = sessionStorage.getItem("project");
            const type = sessionStorage.getItem("type");
            const username = sessionStorage.getItem("username");
            const jiraAccountId = sessionStorage.getItem("jira_account_id");

            if (
              !cloudId ||
              !jiraWebhookId ||
              !projectName ||
              !type ||
              !username ||
              !slackWebhookUrl ||
              !jiraAccountId
            ) {
              console.error("One or more values are missing.");
              return;
            }

            const automatin: AutomationRequest = new AutomationRequest(
              cloudId, 
              slackWebhookUrl, 
              projectName, 
              type, 
              username, 
              jiraWebhookId, 
              jiraAccountId
            );

            this.userService.addAutomation(automatin).subscribe(
              (response) => {
                this.statusMessage = 'Automation successfully created!';
                this.statusClass = 'success';
              },
              (err) => {
                this.statusMessage = `Error: ${err.message}`;
                this.statusClass = 'error';
                console.error(err);
              }
            );
          },
          (err) => {
            this.statusMessage = `Error during token retrieval: ${err.message}`;
            this.statusClass = 'error';
            console.error(err);
          }
        );
      } else {
        console.error('No authorization code found in URL');
      }
    });
  }
}