package yourcaryourway.com.example.yourcaryourway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing an error response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    private String message;

}
