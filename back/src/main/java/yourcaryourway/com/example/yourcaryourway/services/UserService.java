package yourcaryourway.com.example.yourcaryourway.services;

import org.springframework.stereotype.Service;

import yourcaryourway.com.example.yourcaryourway.exception.NoEntryFoundException;
import yourcaryourway.com.example.yourcaryourway.models.Client;
import yourcaryourway.com.example.yourcaryourway.models.User;
import yourcaryourway.com.example.yourcaryourway.repository.ClientRepository;
import yourcaryourway.com.example.yourcaryourway.repository.UserRepository;

@Service
public class UserService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository, ClientRepository clientRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Find a user by its id
     * 
     * @param id The id of the user to find
     * @return The User entity found
     */
    public User findUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NoEntryFoundException("The user does not exist"));
    }

    /**
     * Find a client by its id
     * 
     * @param id The id of the user to find
     * @return The User entity found
     */
    public Client findClientById(Long id) {
        return this.clientRepository.findById(id)
                .orElseThrow(() -> new NoEntryFoundException("The client does not exist"));
    }

}
