package connectify.backend.controllers;

import connectify.backend.models.requests.UserRequest;
import connectify.backend.services.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    private SignUpService signUpService;

    public SignUpController(SignUpService signUpService){
        this.signUpService = signUpService;
    }

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody UserRequest userRequest) {
        boolean isSignedUp = signUpService.signup(userRequest);
        if (isSignedUp) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
