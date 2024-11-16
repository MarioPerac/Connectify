export class Automation {
    jiraCloudId: string;
    slackWebhookUrl: string;
    jiraProject: string;
    typesName: string;
    username: string;
    jiraWebhookId: string;
    jiraAccountId: string;
  
    constructor(
      jiraCloudId: string,
      slackWebhookUrl: string,
      jiraProject: string,
      typesName: string,
      username: string,
      jiraWebhookId: string,
      jiraAccountId: string
    ) {
      this.jiraCloudId = jiraCloudId;
      this.slackWebhookUrl = slackWebhookUrl;
      this.jiraProject = jiraProject;
      this.typesName = typesName;
      this.username = username;
      this.jiraWebhookId = jiraWebhookId;
      this.jiraAccountId = jiraAccountId;
    }
  }
  