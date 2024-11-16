package connectify.backend.controllers;

import connectify.backend.models.dto.Automation;
import connectify.backend.services.UserService;
import org.apache.catalina.User;
import org.apache.coyote.Response;
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

    @PostMapping("/add-automation")
    public ResponseEntity<Void> addAutomation(@RequestBody Automation automation){
        boolean status = userService.addAutomation(automation);

        if(status){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
