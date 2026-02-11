package colloney.posts;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponsePostResponse", description = "API response containing a single post")
public class ApiResponsePostResponse extends ApiResponse<PostResponse> {
}
