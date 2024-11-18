package connectify.backend.controllers;

import connectify.backend.models.requests.JiraWebhookRequest;
import connectify.backend.services.JiraService;
import connectify.backend.services.SlackService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> handleWebhookReceived(@RequestBody Map<String, Object> requestBody) {
        System.out.println("Received event: " + requestBody);
        JSONObject jsonObject = new JSONObject(requestBody);

        String eventType = jsonObject.getString("webhookEvent");
        int webhookId = jsonObject.getJSONArray("matchedWebhookIds").getInt(0);

        switch (eventType) {
            case "jira:issue_created":
                return handleIssueCreatedEvent(jsonObject, webhookId);
            case "jira:issue_updated":
                return handleIssueUpdatedEvent(jsonObject, webhookId);
            case "jira:issue_deleted":
                return handleIssueDeletedEvent(jsonObject,  webhookId);
            case "comment_created":
                return handleCommentCreatedEvent(jsonObject, webhookId);
            case "comment_updated":
                return handleCommentUpdatedEvent(jsonObject, webhookId);
            case "comment_deleted":
                return handleCommentDeletedEvent(jsonObject,webhookId);
            default:
                return ResponseEntity.badRequest().body("Unknown event type");
        }
    }

    private ResponseEntity<String> handleIssueCreatedEvent(JSONObject jsonObject,  int webhookId) {
        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");
        String accountId =  taskUrl.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        String message = String.format(
                "Issue Created:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s",
                taskUrl, key, status, summary
        );

        slackService.sendJiraStatusUpdate(message, webhookId, accountId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> handleIssueUpdatedEvent(JSONObject jsonObject, int webhookId) {
        // Process issue update event
        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");
        String issueSelf = issue.getString("self");
        String accountId =  issueSelf.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        String message = String.format(
                "Issue Updated:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s",
                taskUrl, key, status, summary
        );

        slackService.sendJiraStatusUpdate(message, webhookId, accountId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> handleIssueDeletedEvent(JSONObject jsonObject, int webhookId) {
        JSONObject issue = jsonObject.getJSONObject("issue");
        String key = issue.getString("key");
        String issueSelf = issue.getString("self");
        String accountId =  issueSelf.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        String message = String.format(
                "Issue Deleted:\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s",
                     key, status, summary
        );
        slackService.sendJiraStatusUpdate(message, webhookId, accountId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> handleCommentCreatedEvent(JSONObject jsonObject, int webhookId) {
        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");
        String accountId =  taskUrl.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        JSONObject comment = jsonObject.getJSONObject("comment");
        String body = comment.getString("body");

        JSONObject author = comment.getJSONObject("author");
        String name = author.getString("displayName");

        String message = String.format(
                "Comment Created:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s\n" +
                        "Comment: %s\n" +
                        "Author: %s",
                taskUrl, key, status, summary, body, name
        );
        slackService.sendJiraStatusUpdate(message, webhookId, accountId);;
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> handleCommentUpdatedEvent(JSONObject jsonObject, int webhookId) {
        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");
        String accountId =  taskUrl.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        JSONObject comment = jsonObject.getJSONObject("comment");
        String body = comment.getString("body");

        JSONObject author = comment.getJSONObject("author");
        String name = author.getString("displayName");

        String message = String.format(
                "Comment Updated:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s\n" +
                        "Comment: %s\n" +
                        "Author: %s",
                taskUrl, key, status, summary, body, name
        );
        slackService.sendJiraStatusUpdate(message, webhookId, accountId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> handleCommentDeletedEvent(JSONObject jsonObject, int webhookId) {
        JSONObject issue = jsonObject.getJSONObject("issue");
        String taskUrl = issue.getString("self");
        String key = issue.getString("key");
        String accountId =  taskUrl.split("/")[2];
        System.out.println(accountId);

        JSONObject fields = issue.getJSONObject("fields");
        String status = fields.getJSONObject("status").getString("name");
        String summary = fields.getString("summary");

        JSONObject comment = jsonObject.getJSONObject("comment");
        String body = comment.getString("body");

        JSONObject author = comment.getJSONObject("author");
        String name = author.getString("displayName");

        String message = String.format(
                "Comment Deleted:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s\n" +
                        "Comment: %s\n" +
                        "Author: %s",
                taskUrl, key, status, summary, body, name
        );
        slackService.sendJiraStatusUpdate(message, webhookId, accountId);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/registerWebhook")
    public ResponseEntity<String> registerIssueCreatedWebhook(@RequestBody JiraWebhookRequest request){
        String body = jiraService.registerIssueCreatedWebhook(request,baseUrl +"/api/jira/webhook-received");
        return ResponseEntity.ok(body);
    }

}
