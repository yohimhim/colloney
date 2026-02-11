package colloney.stock.quote;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Represents a stock quote for a specific symbol")
public class QuoteResponse {

    @Schema(description = "Stock symbol", example = "AAPL")
    private String symbol;

    @Schema(description = "Full company name", example = "APPLE INC")
    private String name;

    @Schema(description = "Current stock price", example = "272.09")
    private double currentPrice;

    @Schema(description = "Change in price since previous close", example = "5.84")
    private double priceChange;

    @Schema(description = "Percent change since previous close", example = "2.1934")
    private double percentChange;

    @Schema(description = "Highest price for the day", example = "273.33")
    private double highPrice;

    @Schema(description = "Lowest price for the day", example = "265.67")
    private double lowPrice;

    @Schema(description = "Opening price for the day", example = "266.24")
    private double openPrice;

    @Schema(description = "Previous closing price", example = "266.25")
    private double previousClosePrice;

    @Schema(description = "Timestamp of the quote", example = "2025-11-21T20:04:21Z")
    private Instant quoteTimestamp;

    @Schema(description = "Company logo URL", example = "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.png")
    private String logoUrl;
}
