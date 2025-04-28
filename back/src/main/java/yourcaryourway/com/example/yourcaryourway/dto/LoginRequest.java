package yourcaryourway.com.example.yourcaryourway.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * DTO with user ID (only for Chat POC)
 */
@Data
public class LoginRequest {
    @NotBlank
    private Long id;
}
