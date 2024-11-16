package connectify.backend.services;

import connectify.backend.repositories.TypesRepository;
import connectify.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private TypesRepository typesRepository;

    public UserService(UserRepository userRepository, TypesRepository typesRepository){
        this.userRepository = userRepository;
        this.typesRepository = typesRepository;
    }

    public List<String> getAvailableAutomations(String username){
        return userRepository.findUnusedTypesByUserId(username);
    }
}
