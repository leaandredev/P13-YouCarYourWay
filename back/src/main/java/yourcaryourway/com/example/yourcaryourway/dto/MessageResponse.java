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
public class MessageResponse {
    @NotBlank
    private Long id;
    private String senderFirstName;
    private String senderLastName;
    private Long sessionId;
    private String content;
    private LocalDateTime createdAt;

}
