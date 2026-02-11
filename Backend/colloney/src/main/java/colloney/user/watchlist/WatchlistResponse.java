package colloney.user.watchlist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents a stock in a user's watchlist.")
public class WatchlistResponse {

    @Schema(description = "ID of the user who owns this watchlist entry", example = "87")
    private Long userId;

    @Schema(description = "Stock symbol in the user's watchlist", example = "AAPL")
    private String symbol;
}
