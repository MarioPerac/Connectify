package connectify.backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class AutomationRequest {
    private String jiraCloudId;
    private String slackWebhookUrl;
    private String jiraProject;
    private List<String> types;
    private String username;
    private Integer jiraWebhookId;
    private String jiraAccountId;
}
