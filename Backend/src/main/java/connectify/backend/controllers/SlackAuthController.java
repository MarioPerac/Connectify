package connectify.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/slack")
public class SlackAuthController {
    @Value("${slack.client.id}")
    private String clientId;

    @Value("${slack.client.secret}")
    private String clientSecret;
    @Value("${frontendUrl}")
    private String frontendUrl;
    private String redirectUri;

    @Value("${slack.token.url}")
    private String tokenUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/authorize")
    public void authorizeSlack(HttpServletResponse response) throws IOException {
        redirectUri = frontendUrl + "/api/slack/oauth/callback";
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());

        String authorizationUrl = "https://slack.com/oauth/v2/authorize" +
                "?client_id=" + clientId +
                "&scope=incoming-webhook" +
                "&response_type=code";

        response.sendRedirect(authorizationUrl);
    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<String> slackCallback(@RequestParam String code) {
        String tokenRequestUrl = tokenUrl +
                "?client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&redirect_uri=" + redirectUri;

        ResponseEntity<String> response = restTemplate.getForEntity(tokenRequestUrl, String.class);

        String responseBody = response.getBody();
        if (responseBody != null) {
            return ResponseEntity.ok(responseBody);
        }

        return ResponseEntity.status(500).body("Error retrieving Slack access token");
    }
}
