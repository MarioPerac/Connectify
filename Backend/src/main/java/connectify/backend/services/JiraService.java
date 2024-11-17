package connectify.backend.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import connectify.backend.models.requests.JiraWebhookRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JiraService {
    @Value("${jira.webhook.url}")
    private String JIRA_API_URL;
    public String registerIssueCreatedWebhook(JiraWebhookRequest request, String webhookUrl) {

        String url = JIRA_API_URL + "/" + request.getCloudId() + "/rest/api/3/webhook";

        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();

        ArrayNode webhooks = jnf.arrayNode();

        ObjectNode webhook = jnf.objectNode();

        ArrayNode events = jnf.arrayNode();
        events.add("jira:issue_created");
        // events.add("jira:issue_updated");
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

}
