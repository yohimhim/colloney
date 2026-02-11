package colloney.stock.symbol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a stock symbol")
public class SymbolResponse {

    @Schema(description = "The stock symbol", example = "AAPL")
    private String symbol;

    @Schema(description = "The full company name", example = "APPLE INC")
    private String name;

    @Schema(description = "Exchange where the stock is listed", example = "XNAS")
    private String exchange;

    @Schema(description = "The type of stock/security", example = "Common Stock")
    private String type;
}
