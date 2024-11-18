package connectify.backend.controllers;

import connectify.backend.models.dto.Automation;
import connectify.backend.models.requests.AutomationRequest;
import connectify.backend.services.UserService;
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
    public ResponseEntity<Void> addAutomation(@RequestBody AutomationRequest automationRequest){
        boolean status = userService.addAutomation(automationRequest);

        if(status){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{username}/automations")
    public ResponseEntity<List<Automation>> getAutomations(@PathVariable String username){
        return ResponseEntity.ok(userService.getAutomations(username));
    }

    @DeleteMapping("/automation/{id}")
    public ResponseEntity<Void> deleteAutomation(@PathVariable Integer id) {

        if(userService.deleteAutomation(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
