package connectify.backend.services;

import connectify.backend.models.dto.Automation;
import connectify.backend.models.entities.AutomationsEntity;
import connectify.backend.repositories.AutomationRepository;
import connectify.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean addAutomation(Automation automation){
        AutomationsEntity entity = modelMapper.map(automation, AutomationsEntity.class);
        automationRepository.saveAndFlush(entity);
        return true;
    }
}
