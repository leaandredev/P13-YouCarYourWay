package yourcaryourway.com.example.yourcaryourway.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * DTO with credentials to log user
 */
@Data
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
