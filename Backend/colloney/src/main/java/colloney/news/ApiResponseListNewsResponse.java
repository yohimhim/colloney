package colloney.news;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseNewsList", description = "API wrapper for a list of news articles")
public class ApiResponseListNewsResponse extends ApiResponse<List<NewsResponse>> {
}
