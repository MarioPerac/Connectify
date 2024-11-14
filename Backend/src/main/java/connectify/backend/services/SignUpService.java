package connectify.backend.services;

import connectify.backend.models.entities.UsersEntity;
import connectify.backend.models.requests.UserRequest;
import connectify.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public SignUpService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean signup(UserRequest userRequest){
        UsersEntity usersEntity = modelMapper.map(userRequest, UsersEntity.class);
        userRepository.saveAndFlush(usersEntity);
        return true;
    }
}
