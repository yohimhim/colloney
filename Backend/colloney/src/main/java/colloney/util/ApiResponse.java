package colloney.util;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard API wrapper used for all responses.")
public class ApiResponse<T> {

    @Schema(description = "HTTP status code", example = "200")
    private int statusCode;

    @Schema(description = "Status string (success or error)", example = "success")
    private String status;

    @Schema(description = "Human-readable message", example = "Operation completed sucessfully")
    private String message;

    @Schema(description = "Pagination metadata if applicable")
    private Metadata metadata;

    @Schema(description = "Response payload")
    private T data;

    @Schema(description = "Error description (populated for errors)", example = "null")
    private String description;

    @Schema(description = "Timestamp of the response")
    private Instant timestamp;

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), "success", message, Metadata.notPaginated(), data, null,
                Instant.now());
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "success", message, Metadata.notPaginated(), data, null,
                Instant.now());
    }

    // Shouldn't be directly created, created by GlobalExceptionHandler
    public static ApiResponse<Void> error(int statusCode, String message, String description) {
        return new ApiResponse<>(statusCode, "error", message, Metadata.notPaginated(), null, description,
                Instant.now());
    }

    public static <T> ApiResponse<T> paginated(String message, T data, int page, int size, long totalElements,
            int totalPages) {
        return new ApiResponse<>(HttpStatus.OK.value(), "success", message,
                Metadata.paginated(page, size, totalElements, totalPages), data, null, Instant.now());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "Pagination metadata for paginated responses")
    public static class Metadata {

        @Schema(description = "Whether the response is paginated", example = "false")
        private boolean paginated;

        @Schema(description = "Current page number (0-indexed)", example = "null")
        private Integer page;

        @Schema(description = "Page size", example = "null")
        private Integer size;

        @Schema(description = "Total number of elements", example = "null")
        private Long totalElements;

        @Schema(description = "Total number of pages", example = "null")
        private Integer totalPages;

        public static Metadata notPaginated() {
            return new Metadata(false, null, null, null, null);
        }

        public static Metadata paginated(int page, int size, long totalElements, int totalPages) {
            return new Metadata(true, page, size, totalElements, totalPages);
        }
    }
}
