package connectify.backend.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import connectify.backend.models.dto.Automation;
import connectify.backend.models.dto.JiraAuthTokens;
import connectify.backend.models.entities.AutomationsEntity;
import connectify.backend.models.requests.JiraWebhookRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class JiraService {
    @Value("${jira.webhook.url}")
    private String JIRA_API_URL;

    @Value("${jira.client.id}")
    private String clientId;

    @Value("${jira.client.secret}")
    private String clientSecret;

    @Value("${jira.test.auth.token}")
    private String testAuthToken;
    @Value("${jira.test.cloud.id}")
    private String testCloudId;

    @Value("${jira.test.project}")
    private String testProject;
    private RestTemplate restTemplate;

    public JiraService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String registerIssueCreatedWebhook(JiraWebhookRequest request, String webhookUrl) {

        String url = JIRA_API_URL + "/" + request.getCloudId() + "/rest/api/3/webhook";

        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();

        ArrayNode webhooks = jnf.arrayNode();

        ObjectNode webhook = jnf.objectNode();

        ArrayNode events = jnf.arrayNode();

        for(String type: request.getTypes())
            events.add(type);

        webhook.set("events", events);

        webhook.put("jqlFilter", "project =" + request.getProject());

        webhooks.add(webhook);

        payload.set("webhooks", webhooks);

        payload.put("url", webhookUrl);

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .header("Authorization", "Bearer " + request.getAccessToken())
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(payload.toString())
                    .asJson();

            if (response.getStatus() == 200) {
                System.out.println("Webhook successfully registered for Jira events: " + response.getBody());
                return response.getBody().toString();

            } else {
                System.out.println("Failed to register webhook for Jira events: " +
                        "status code: " + response.getStatus() + ", " + response.getBody());
                return  null;
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    public JiraAuthTokens refreshJiraToken(String refreshToken) {
        String url = "https://auth.atlassian.com/oauth/token";

        // Kreiranje tela zahteva
        String requestBody = String.format(
                "{ \"grant_type\": \"refresh_token\", \"client_id\": \"%s\", \"client_secret\": \"%s\", \"refresh_token\": \"%s\" }",
                clientId, clientSecret, refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<JiraAuthTokens> response = restTemplate.exchange(url, HttpMethod.POST, entity, JiraAuthTokens.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to refresh token: " + response.getStatusCode());
        }
    }

    public void createJiraTask(String summary) {
        String url = JIRA_API_URL + "/" + testCloudId + "/rest/api/3/issue";

        // JSON telo za kreiranje zadatka
        String requestBody = String.format(
                "{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"key\": \"OPS\"\n" +
                        "    },\n" +
                        "    \"summary\": \"%s\",\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Task\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}", summary);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",  "Bearer " + testAuthToken);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Jira task created successfully: " + response.getBody());
            } else {
                System.err.println("Failed to create Jira task: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error while creating Jira task: " + e.getMessage());
        }
    }

}
