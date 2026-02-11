package colloney.stock.quote.latest;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseLatestQuote", description = "API wrapper for a single latest quote")
public class ApiResponseLatestQuoteResponse extends ApiResponse<colloney.stock.quote.QuoteResponse> {
}
