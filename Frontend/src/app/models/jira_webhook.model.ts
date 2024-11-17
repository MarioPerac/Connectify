
export class JiraWebhookRequest {
    cloudId: string;
    project: string;
    accessToken: string;
    types: string[];
  
    constructor(cloudId: string, project: string, accessToken: string, types: string[]) {
      this.cloudId = cloudId;
      this.project = project;
      this.accessToken = accessToken;
      this.types = types;
    }
  }
  