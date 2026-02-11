package colloney.user.watchlist;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseWatchlistResponse", description = "API response containing a single watchlist entry")
public class ApiResponseWatchlistResponse extends ApiResponse<WatchlistResponse> {
}
