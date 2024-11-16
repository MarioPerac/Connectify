package connectify.backend.controllers;

import connectify.backend.services.UserService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{username}/available-automations")
    public ResponseEntity<List<String>> getAutomationTypes(@PathVariable String username) {
        return ResponseEntity.ok(userService.getAvailableAutomations(username));
    }

}
