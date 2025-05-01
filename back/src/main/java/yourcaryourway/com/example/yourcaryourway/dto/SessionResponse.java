package yourcaryourway.com.example.yourcaryourway.dto;

import java.time.LocalDateTime;

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
public class SessionResponse {
    @NotBlank
    private Long id;
    private String clientFirstName;
    private String clientLastName;
    private String supportFirstName;
    private String supportLastName;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

}
