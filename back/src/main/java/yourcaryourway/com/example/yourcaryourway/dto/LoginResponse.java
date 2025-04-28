package yourcaryourway.com.example.yourcaryourway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO with session information for authentificated user
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String type;
}
