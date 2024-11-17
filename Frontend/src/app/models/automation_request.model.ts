export class AutomationRequest {
    jiraCloudId: string;
    slackWebhookUrl: string;
    jiraProject: string;
    types: string[];
    username: string;
    jiraWebhookId: string;
    jiraAccountId: string;
  
    constructor(
      jiraCloudId: string,
      slackWebhookUrl: string,
      jiraProject: string,
      types: string[],
      username: string,
      jiraWebhookId: string,
      jiraAccountId: string
    ) {
      this.jiraCloudId = jiraCloudId;
      this.slackWebhookUrl = slackWebhookUrl;
      this.jiraProject = jiraProject;
      this.types = types;
      this.username = username;
      this.jiraWebhookId = jiraWebhookId;
      this.jiraAccountId = jiraAccountId;
    }
  }
  