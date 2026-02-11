package colloney.stock.trade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TradeRequest {

    @Schema(description = "Stock symbol to buy or sell", example = "AAPL")
    private String symbol;

    @Schema(description = "Number of shares", example = "5")
    private int quantity;
}
