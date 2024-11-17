package connectify.backend.services;

import connectify.backend.models.dto.Automation;
import connectify.backend.models.requests.AutomationRequest;
import connectify.backend.models.entities.AutomationsEntity;
import connectify.backend.repositories.AutomationRepository;
import connectify.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private AutomationRepository automationRepository;
    private ModelMapper modelMapper;
    public UserService(UserRepository userRepository, AutomationRepository automationRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.automationRepository = automationRepository;
        this.modelMapper = modelMapper;
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
}
