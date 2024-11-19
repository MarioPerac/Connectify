package connectify.backend.controllers;

import connectify.backend.services.JiraService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/slack")
public class SlackController {

    private JiraService jiraService;

    public SlackController(JiraService jiraService) {
        this.jiraService = jiraService;
    }


    @PostMapping("/webhook-received")
    public ResponseEntity<String> handleWebhookReceived(@RequestBody Map<String, Object> requestBody) {
        System.out.println("Received event: " + requestBody);

        JSONObject jsonObject = new JSONObject(requestBody);

        // Provera za Slack challenge
        if (jsonObject.has("challenge")) {
            String challenge = jsonObject.getString("challenge");
            System.out.println("Responding to Slack challenge: " + challenge);
            return ResponseEntity.ok(challenge);
        }

        if (jsonObject.has("event")) {
            JSONObject event = jsonObject.getJSONObject("event");
            String eventType = event.optString("type", "unknown");

            if ("message".equals(eventType)) {
                String text = event.optString("text", "");
                System.out.println("Received message: " + text);

                if (text.contains("JiraTask")) {
                    System.out.println("Keyword 'JiraTask' found, creating Jira task...");
                    jiraService.createJiraTask( text.replace("JiraTask", ""));
                }
            }
        }

        return ResponseEntity.ok("Event received");
    }
}
