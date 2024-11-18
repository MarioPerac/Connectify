package connectify.backend.models.dto;

import lombok.Data;

@Data
public class JiraAuthTokens {
    private String access_token;
    private String refresh_token;
    private int expires_in;
}
