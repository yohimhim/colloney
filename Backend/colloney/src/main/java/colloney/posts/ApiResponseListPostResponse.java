package colloney.posts;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseListPostResponse", description = "API response containing a list of posts")
public class ApiResponseListPostResponse extends ApiResponse<List<PostResponse>> {
}
