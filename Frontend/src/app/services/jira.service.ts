import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, tap } from 'rxjs';
import { JiraWebhookRequest } from '../models/jira_webhook.model';

@Injectable({
  providedIn: 'root'
})
export class JiraService {

  private apiUrl = 'http://localhost:8080/api/jira';
  constructor(private http: HttpClient) { }

  public authorize() {
    const url = this.apiUrl + "/authorize"; // URL to your backend's authorize endpoint
    window.location.href = url;  // Redirect the user to the backend which redirects to Jira
  }

  public getToken(code: string){
    const url = this.apiUrl + "/oauth/callback?code=" + code;
    return this.http.get<any>(url);
  }

 public getCloudId(): Observable<string> {
    const url = 'https://api.atlassian.com/oauth/token/accessible-resources'
    const token = sessionStorage.getItem('jira_access_token');

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/json',
    });

    return this.http.get<any>(url, { headers }).pipe(
      map(response => {
        console.log('Response from accessible-resources:', response); 
        const firstResource = response[0];
        return firstResource ? firstResource.id : '';
      }),
      catchError(error => {
        console.error('Error fetching Jira Cloud ID:', error);
        throw error;
      })
    );
  }

  public getAccountId(): Observable<string> {
    const url = 'https://api.atlassian.com/me'
    const token = sessionStorage.getItem('jira_access_token');

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/json',
    });

    return this.http.get<any>(url, { headers }).pipe(
      map(response => response.account_id),
      catchError(error => {
        console.error('Error fetching Jira Cloud ID:', error);
        throw error;
      })
    );
  }
  
  public getProjects(cloudId: string): Observable<string[]> {

    const token = sessionStorage.getItem('jira_access_token'); 
  
    if (!token) {
      throw new Error('Jira access token is not available.');
    }
  
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Accept': 'application/json',
    });

    return this.http.get<any[]>(`https://api.atlassian.com/ex/jira/${cloudId}/rest/api/3/project`, { headers }).pipe(
      map(response => {
        return response.map(project => project.name);
      }),
      catchError(error => {
        console.error('Error fetching Jira projects:', error);
        throw error;
      })
    );
  }

  public createWebhook(request: JiraWebhookRequest){
    const url = this.apiUrl + "/registerIssueCreatedWebhook";
    return this.http.post<any>(url, request).pipe(
      map(response => {
        if ( response!= null && response.webhookRegistrationResult) {
          const webhookId = response.webhookRegistrationResult[0].createdWebhookId;
          return webhookId;
        } else {
          return null;
        }
      })
    );
  }
  
}
