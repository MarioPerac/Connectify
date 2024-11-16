package connectify.backend.services;

import connectify.backend.repositories.AutomationRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class SlackService {

    private AutomationRepository automationRepository;

    public SlackService(AutomationRepository automationRepository){
        this.automationRepository = automationRepository;
    }
    public void sendJiraStatusUpdate(String accountId, Integer webhookId, String jiraTaskUrl, String key, String status, String summary) {

        String webhookUrl= automationRepository.getSlackWebhookUrl(webhookId, accountId);
        JSONObject payload = new JSONObject();

        String message = String.format(
                "Jira Task Update:\n" +
                        "Task URL: %s\n" +
                        "Key: %s\n" +
                        "Status: %s\n" +
                        "Summary: %s",
                jiraTaskUrl, key, status, summary
        );

        payload.put("text", message);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println("Greška prilikom slanja zahteva: " + e.getMessage());
        }
    }
}
