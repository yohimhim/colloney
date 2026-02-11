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
@Schema(description = "Represents a stock in a user's watchlist with detailed information")
public class WatchlistDetailResponse {

    @Schema(description = "ID of the user who owns this watchlist entry", example = "87")
    private Long userId;

    @Schema(description = "Stock symbol in the user's watchlist", example = "AAPL")
    private String symbol;

    @Schema(description = "Full name of the company", example = "APPLE INC")
    private String name;

    @Schema(description = "Current stock price", example = "271.49")
    private Double currentPrice;

    @Schema(description = "Change in price since previous close", example = "5.24")
    private Double priceChange;

    @Schema(description = "Percent change in price since previous close", example = "1.9681")
    private Double percentChange;

    @Schema(description = "Company logo URL", example = "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.png")
    private String logoUrl;
}
