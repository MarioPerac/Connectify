package connectify.backend.models.requests;

import lombok.Data;
@Data
public class AutomationRequest {
    private String jiraCloudId;
    private String slackWebhookUrl;
    private String jiraProject;
    private String typesName;
    private String username;
    private Integer jiraWebhookId;
    private String jiraAccountId;
}
