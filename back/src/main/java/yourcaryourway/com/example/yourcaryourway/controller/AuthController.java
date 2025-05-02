package yourcaryourway.com.example.yourcaryourway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yourcaryourway.com.example.yourcaryourway.dto.LoginResponse;
import yourcaryourway.com.example.yourcaryourway.models.Client;
import yourcaryourway.com.example.yourcaryourway.models.Support;
import yourcaryourway.com.example.yourcaryourway.models.User;
import yourcaryourway.com.example.yourcaryourway.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<LoginResponse> login(@PathVariable("id") String id) {
        User user = this.userService.findUserById(Long.valueOf(id));

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String role;
        if (user instanceof Client) {
            role = "CLIENT";
        } else if (user instanceof Support) {
            role = "SUPPORT";
        } else {
            role = "UNKNOWN";
        }

        LoginResponse response = new LoginResponse(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), role);
        return ResponseEntity.ok(response);
    }

}