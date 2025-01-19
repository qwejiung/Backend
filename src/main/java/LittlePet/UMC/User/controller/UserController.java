package LittlePet.UMC.User.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/test")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }
}
