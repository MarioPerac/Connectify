import { A } from "@angular/cdk/keycodes";

export class AutomationRequest {
    jiraCloudId: string;
    slackWebhookUrl: string;
    jiraProject: string;
    types: string[];
    username: string;
    jiraWebhookId: string;
    jiraAccountId: string;
    accessToken: string;
    refreshToken: string;
    expiresIn: Date;
  
    constructor(
      jiraCloudId: string,
      slackWebhookUrl: string,
      jiraProject: string,
      types: string[],
      username: string,
      jiraWebhookId: string,
      jiraAccountId: string,
      accessToken: string,
      refreshToken: string,
      expiresIn: Date
    ) {
      this.jiraCloudId = jiraCloudId;
      this.slackWebhookUrl = slackWebhookUrl;
      this.jiraProject = jiraProject;
      this.types = types;
      this.username = username;
      this.jiraWebhookId = jiraWebhookId;
      this.jiraAccountId = jiraAccountId;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.expiresIn = expiresIn;
    }
  }
  