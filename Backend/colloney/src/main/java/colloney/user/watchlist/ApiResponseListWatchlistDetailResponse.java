package colloney.user.watchlist;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseListWatchlistDetailResponse", description = "API response containing a list of watchlist entries with details")
public class ApiResponseListWatchlistDetailResponse extends ApiResponse<List<WatchlistDetailResponse>> {
}
