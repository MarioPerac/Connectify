package connectify.backend.services;

import connectify.backend.models.dto.Automation;
import connectify.backend.models.requests.AutomationRequest;
import connectify.backend.models.entities.AutomationsEntity;
import connectify.backend.repositories.AutomationRepository;
import connectify.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private AutomationRepository automationRepository;
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;
    public UserService(UserRepository userRepository, AutomationRepository automationRepository, ModelMapper modelMapper, RestTemplate restTemplate){
        this.userRepository = userRepository;
        this.automationRepository = automationRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    public List<String> getAvailableAutomations(String username){
        return userRepository.findUnusedTypesByUserId(username);
    }

    public boolean addAutomation(AutomationRequest automationRequest){
        AutomationsEntity entity = modelMapper.map(automationRequest, AutomationsEntity.class);
        automationRepository.saveAndFlush(entity);
        return true;
    }

    public List<Automation> getAutomations(String username){
        return automationRepository.getAllByUsername(username).stream().map(a -> modelMapper.map(a, Automation.class)).collect(Collectors.toList());
    }

    public boolean deleteAutomation(Integer id, String accessToken){
        AutomationsEntity automationsEntity = automationRepository.findById(id).get();

        String deleteUrl = "https://api.atlassian.com/ex/jira/" + automationsEntity.getJiraCloudId()+ "/rest/api/3/webhook";

        String requestBody = "{\"webhookIds\": [" + String.join(",", automationsEntity.getJiraWebhookId().toString()) + "]}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                deleteUrl,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            automationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
