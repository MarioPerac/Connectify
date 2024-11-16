package connectify.backend.controllers;

import connectify.backend.models.requests.JiraWebhookRequest;
import connectify.backend.services.JiraService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/jira")
public class JiraController {

    @Value("${baseUrl}")
    private String baseUrl;

    private JiraService jiraService;

    public JiraController(JiraService jiraService){
        this.jiraService = jiraService;
    }

    @PostMapping("/webhook-received")
    public ResponseEntity<String> handleIssueCreatedEvent(@RequestBody Map<String, Object> requestBody) {
        // Log or process the event
        System.out.println("Received event: " + requestBody);

        // Check if the event contains the necessary data
        if (requestBody.containsKey("eventType")) {
            String eventType = (String) requestBody.get("eventType");

            // Process only "jira:issue_created" event type
            if ("jira:issue_created".equals(eventType)) {
                // Get the issue data from the event
                Map<String, Object> issueData = (Map<String, Object>) requestBody.get("issue");

                // Access the "fields" sub-map from the issue data
                Map<String, Object> fields = (Map<String, Object>) issueData.get("fields");

                // Extract specific fields from the "fields" sub-map
                String issueKey = (String) issueData.get("key");
                String summary = (String) fields.get("summary");
                Map<String, Object> assignee = (Map<String, Object>) fields.get("assignee");

                // Log the extracted information
                System.out.println("Issue Created: " + issueKey);
                System.out.println("Summary: " + summary);
                if (assignee != null) {
                    System.out.println("Assignee: " + assignee.get("displayName"));
                } else {
                    System.out.println("No assignee assigned.");
                }
            }
        }

        // Return an appropriate HTTP response
        return ResponseEntity.ok("Event processed successfully");
    }


    @PostMapping("/registerIssueCreatedWebhook")
    public ResponseEntity<String> registerIssueCreatedWebhook(@RequestBody JiraWebhookRequest request){
        String body = jiraService.registerIssueCreatedWebhook(request,baseUrl +"/api/jira/webhook-received");
        return ResponseEntity.ok(body);
    }

}
