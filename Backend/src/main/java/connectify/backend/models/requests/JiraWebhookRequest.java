package connectify.backend.models.requests;

import lombok.Data;

@Data

public class JiraWebhookRequest {
    private String cloudId;
    private String project;
    private String accessToken;
}
