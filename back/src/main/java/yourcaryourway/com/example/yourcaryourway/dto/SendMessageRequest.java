package yourcaryourway.com.example.yourcaryourway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the request to create a new session.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageRequest {
    private Long senderId;
    private String senderFirstName;
    private String senderLastName;
    private Long sessionId;
    private String content;
}
