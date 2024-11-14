package connectify.backend.services;

import connectify.backend.models.entities.UsersEntity;
import connectify.backend.models.requests.UserRequest;
import connectify.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserRepository userRepository;

    public LoginService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean login(UserRequest userRequest){
        if(!userRepository.existsById(userRequest.getUsername()))
            return false;
        UsersEntity usersEntity = userRepository.findById(userRequest.getUsername()).get();

        return usersEntity.getPassword().equals(userRequest.getPassword());
    }
}
