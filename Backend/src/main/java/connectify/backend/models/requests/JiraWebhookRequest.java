package connectify.backend.models.requests;

import lombok.Data;

import java.util.List;

@Data

public class JiraWebhookRequest {
    private String cloudId;
    private String project;
    private String accessToken;
    private List<String> types;
}
