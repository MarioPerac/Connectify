import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JiraService } from '../services/jira.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { JiraWebhookRequest } from '../models/jira_webhook.model';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-jira-projects',
  standalone: true,
  imports: [CommonModule, FormsModule, ToolbarComponent, MatButtonModule, MatSelectModule, MatFormFieldModule, MatCardModule],
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
           const refresthToken = response.refresh_token;
           sessionStorage.setItem("jira_refresh_token", refresthToken);
           const expiresIn = response.expires_in;
           const expirationTime = new Date().getTime() + expiresIn * 1000;
          const expirationDate = new Date(expirationTime);
           sessionStorage.setItem("expires_in", JSON.stringify(expirationDate) )

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
      const types = JSON.parse(sessionStorage.getItem("types")!); 
      const request = new JiraWebhookRequest(cloud_id,this.selectedProject, accessToken, types);
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
