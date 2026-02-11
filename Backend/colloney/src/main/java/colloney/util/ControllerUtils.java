package colloney.util;

import org.springframework.http.ResponseEntity;

public class ControllerUtils {
    public static <T> ResponseEntity<ApiResponse<T>> wrapResponse(ApiResponse<T> response) {
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
