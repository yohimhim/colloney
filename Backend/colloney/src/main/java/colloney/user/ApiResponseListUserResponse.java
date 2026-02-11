package colloney.user;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseListUserResponse", description = "API response containing a list of users")
public class ApiResponseListUserResponse extends ApiResponse<List<UserResponse>> {
}
