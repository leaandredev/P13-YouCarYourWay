package yourcaryourway.com.example.yourcaryourway.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yourcaryourway.com.example.yourcaryourway.models.User;
import yourcaryourway.com.example.yourcaryourway.repository.ClientRepository;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ClientRepository userRepository;

    UserController(ClientRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

}
