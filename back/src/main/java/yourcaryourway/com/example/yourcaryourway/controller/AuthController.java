package yourcaryourway.com.example.yourcaryourway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yourcaryourway.com.example.yourcaryourway.dto.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.findById(request.getId());

        String role;
        if (user instanceof Client) {
            role = "CLIENT";
        } else if (user instanceof Support) {
            role = "SUPPORT";
        } else {
            role = "UNKNOWN";
        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponse response = new LoginResponse(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), role);
        return ResponseEntity.ok(response);
    }

}
