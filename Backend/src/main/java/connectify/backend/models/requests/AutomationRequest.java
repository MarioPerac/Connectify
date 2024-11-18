package connectify.backend.models.requests;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
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
    private String accessToken;
    private String refreshToken;
}
