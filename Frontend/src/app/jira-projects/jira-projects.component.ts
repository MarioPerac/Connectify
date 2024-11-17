import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JiraService } from '../services/jira.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { JiraWebhookRequest } from '../models/jira_webhook.model';
import { ToolbarComponent } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-jira-projects',
  standalone: true,
  imports: [CommonModule, FormsModule, ToolbarComponent],
  templateUrl: './jira-projects.component.html',
  styleUrl: './jira-projects.component.css'
})
export class JiraProjectsComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private jiraService: JiraService,
    private router: Router
  ) {}

  projects: string[]= [];
  selectedProject: string = '';

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      const code = params['code']; // Capture the 'code' from the URL

      if (code) {
        this.jiraService.getToken(code).subscribe(
          (response) => {
            const accessToken = response.access_token;
            sessionStorage.setItem('jira_access_token', accessToken);
           

            this.jiraService.getCloudId().subscribe(
              (cloudId) => {
                sessionStorage.setItem('cloud_id', cloudId);
                
                this.jiraService.getProjects(cloudId).subscribe(
                  (projectNames) => {
                    this.projects = projectNames;
                  }
                );
              }
            );

            this.jiraService.getAccountId().subscribe(
              (accessId) => {
                sessionStorage.setItem('jira_account_id', accessId);
              }
            )
          }
        );
        
      } else {
        console.error('No authorization code found in URL');
      }
    });
  }

  onNext(): void {
    const router = this.router;
    if (this.selectedProject) {
      sessionStorage.setItem("project", this.selectedProject);

      var cloud_id = sessionStorage.getItem("cloud_id");
      var accessToken = sessionStorage.getItem("jira_access_token"); 
  
      if (!cloud_id || !accessToken) {
        alert('Cloud ID or Access Token is missing.');
        return;
      }
  
      const request = new JiraWebhookRequest(cloud_id,this.selectedProject, accessToken);
      this.jiraService.createWebhook(request).subscribe({
        next(webhookId: string) {
            sessionStorage.setItem("webhook_id", webhookId);
            router.navigate(['slack-auth']);
        },
      });
    } else {
      alert('Please select a project first.');
    }
  }
}
