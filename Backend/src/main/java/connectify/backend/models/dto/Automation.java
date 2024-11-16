package connectify.backend.models.dto;

import lombok.Data;
@Data
public class Automation {
    private String jiraCloudId;
    private String slackWebhookUrl;
    private String jiraProject;
    private String typesName;
    private String username;
    private Integer jiraWebhookId;
    private String jiraAccountId;
}
