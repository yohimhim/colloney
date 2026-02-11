package colloney.user.watchlist;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import colloney.util.ControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Watchlist", description = "Endpoints for managing users' watchlists")
@RestController
@RequestMapping("/users/{userId}/watchlist")
@RequiredArgsConstructor
public class WatchlistController {
    private final WatchlistService watchlistService;

    // @formatter:off
    @Operation(
        summary = "Get user's watchlist",
        description = "Retrieve the list of stocks in a user's watchlist with details.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of watchlist entries with detailed information",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListWatchlistDetailResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping
    public ResponseEntity<ApiResponse<List<WatchlistDetailResponse>>> getUserWatchlist(@PathVariable Long userId) {
        return ControllerUtils.wrapResponse(watchlistService.getUserWatchlist(userId));
    }

    // @formatter:off
    @Operation(
        summary = "Add stock to watchlist",
        description = "Add a stock symbol to the user's watchlist. Symbol must exist and not already be in the watchlist.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the watchlist entry added",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseWatchlistResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PostMapping
    public ResponseEntity<ApiResponse<WatchlistResponse>> addToWatchlist(@PathVariable Long userId,
            @Valid @RequestBody WatchlistRequest request) {
        return ControllerUtils.wrapResponse(watchlistService.addToWatchlist(userId, request));
    }

    // @formatter:off
    @Operation(
        summary = "Remove stock from watchlist",
        description = "Remove a stock symbol from the user's watchlist.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the watchlist entry removed",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseWatchlistResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @DeleteMapping("/{symbol}")
    public ResponseEntity<ApiResponse<WatchlistResponse>> removeFromWatchlist(@PathVariable Long userId,
            @PathVariable String symbol) {
        return ControllerUtils.wrapResponse(watchlistService.removeFromWatchlist(userId, symbol));
    }
}
