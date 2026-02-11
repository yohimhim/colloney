package colloney.exception;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResponse<Void>> buildErrorMessage(HttpStatus status, Exception ex, WebRequest request) {
        ApiResponse<Void> errorResponse = ApiResponse.error(
        // @formatter:off
            status.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        // @formatter:on
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<ApiResponse<Void>> buildErrorMessage(HttpStatus status, String message, WebRequest request) {
        ApiResponse<Void> errorResponse = ApiResponse.error(
        // @formatter:off
            status.value(),
            message,
            request.getDescription(false)
        );
        // @formatter:on
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unhandled exception of type {} occurred: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return buildErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        String errorMessage = String.join(", ", errors);
        return buildErrorMessage(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(NoResourceFoundException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentialsException(InvalidCredentialsException ex,
            WebRequest request) {
        // if message wasn't set, use the default
        String errorMessage = (ex.getMessage() != null && !ex.getMessage().isBlank()) ? ex.getMessage()
                : "Invalid username or password";
        return buildErrorMessage(HttpStatus.UNAUTHORIZED, errorMessage, request);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnsupportedOperationException(UnsupportedOperationException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, ex, request);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse<Void>> handleDateTimeParseException(DateTimeParseException ex,
            WebRequest request) {
        return buildErrorMessage(HttpStatus.BAD_REQUEST, "Invalid date format, use yyyy-MM-dd", request);
    }
}
