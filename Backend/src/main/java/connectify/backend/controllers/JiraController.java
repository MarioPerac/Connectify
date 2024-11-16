package connectify.backend.controllers;

import connectify.backend.models.requests.JiraWebhookRequest;
import connectify.backend.services.JiraService;
import connectify.backend.services.SlackService;
import org.json.JSONObject;
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
    private SlackService slackService;
    public JiraController(JiraService jiraService, SlackService slackService){
        this.jiraService = jiraService;
        this.slackService = slackService;
    }

    @PostMapping("/webhook-received")
    public ResponseEntity<String> handleIssueCreatedEvent(@RequestBody Map<String, Object> requestBody) {
        System.out.println("Received event: " + requestBody);
        JSONObject jsonObject = new JSONObject(requestBody);

        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");

        String accountId = jsonObject.getJSONObject("user").getString("accountId");

        int webhookId = jsonObject.getJSONArray("matchedWebhookIds").getInt(0);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        slackService.sendJiraStatusUpdate(accountId, webhookId, taskUrl, key, status, summary);
        return ResponseEntity.ok("Event processed successfully");
    }


    @PostMapping("/registerIssueCreatedWebhook")
    public ResponseEntity<String> registerIssueCreatedWebhook(@RequestBody JiraWebhookRequest request){
        String body = jiraService.registerIssueCreatedWebhook(request,baseUrl +"/api/jira/webhook-received");
        return ResponseEntity.ok(body);
    }

}
