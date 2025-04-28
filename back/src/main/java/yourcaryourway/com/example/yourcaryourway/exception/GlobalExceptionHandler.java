package yourcaryourway.com.example.yourcaryourway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import yourcaryourway.com.example.yourcaryourway.dto.ErrorResponse;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle {@link MethodArgumentNotValidException}
     * 
     * @param exception the thrown exception
     * @return a {@link ResponseEntity} with all validation error in
     *         {@link ErrorResponse} request and a bad
     *         request status (400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(final MethodArgumentNotValidException exception) {
        String errors = "";
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors += error.getDefaultMessage() + ". ";
        }
        log.error(errors, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }

    /**
     * Handle {@link HttpMessageNotReadableException},
     * {@link DuplicateEntryException} and {@link IllegalArgumentException}
     * 
     * @param exception the thrown exception
     * @return a {@link ResponseEntity} with {@link ErrorResponse} request and a bad
     *         request status (400)
     */
    @ExceptionHandler({ HttpMessageNotReadableException.class,
            IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequest(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * Handle {@link NoEntryFoundException}
     * 
     * @param exception the thrown exception
     * @return a {@link ResponseEntity} with {@link ErrorResponse} request and a not
     *         found status (404)
     */
    @ExceptionHandler(NoEntryFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoEntryFoundExceptions(final NoEntryFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * Handle all other {@link Exception}
     * 
     * @param exception the thrown exception
     * @return a {@link ResponseEntity} with {@link ErrorResponse} request and a
     *         internal server error status (500)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGlobalException(final Exception exception) {
        log.error(exception.getStackTrace().toString(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(new ErrorResponse(exception.getMessage()));
    }

}
