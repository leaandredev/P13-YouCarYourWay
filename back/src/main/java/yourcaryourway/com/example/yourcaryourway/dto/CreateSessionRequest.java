package yourcaryourway.com.example.yourcaryourway.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the request to create a new session.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSessionRequest {
@NotBlank
    private Long clientId;
}
