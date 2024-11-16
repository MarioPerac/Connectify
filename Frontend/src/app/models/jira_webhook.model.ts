export class JiraWebhookRequest {
    cloudId: string;
    project: string;
    accessToken: string;
  
    constructor(cloudId: string, project: string, accessToken: string) {
      this.cloudId = cloudId;
      this.project = project;
      this.accessToken = accessToken;
    }
  }
  