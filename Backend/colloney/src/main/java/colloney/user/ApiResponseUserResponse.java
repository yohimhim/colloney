package colloney.user;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseUserResponse", description = "API response containing a single user")
public class ApiResponseUserResponse extends ApiResponse<UserResponse> {
}
