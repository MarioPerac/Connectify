package connectify.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/jira")
public class JiraAuthController {
    @Value("${jira.client.id}")
    private String clientId;

    @Value("${jira.client.secret}")
    private String clientSecret;

    @Value("${frontendUrl}")
    private String frontendUrl;

    @Value("${jira.token.url}")
    private String tokenUrl;

    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/authorize")
    public void authorizeSlack(HttpServletResponse response) throws IOException {
        redirectUri = frontendUrl + "/jira/oauth/callback";
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());
        String scope = "read:jira-work manage:jira-webhook read:field:jira read:project:jira read:webhook:jira write:webhook:jira delete:webhook:jira read:jira-user offline_access read:jira-work";

        String encodedScopes = URLEncoder.encode(scope, "UTF-8");

        String authorizationUrl = "https://auth.atlassian.com/authorize" +
                "?audience=api.atlassian.com" +
                "&client_id=" + clientId +
                "&scope=" + encodedScopes +
                "&redirect_uri=" + encodedRedirectUri +
                "&response_type=code" +
                "&prompt=consent";

        response.sendRedirect(authorizationUrl);
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<String> jiraCallback(@RequestParam String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Send POST request to exchange the authorization code for an access token
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        String responseBody = response.getBody();
        if (responseBody != null) {
//            String accessToken = extractAccessToken(responseBody);
            return ResponseEntity.ok(responseBody);
        }

        return ResponseEntity.status(500).body("Error retrieving Jira access token");
    }
}
