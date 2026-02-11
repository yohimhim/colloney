package colloney.stock.quote.latest;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseLatestQuoteList", description = "API wrapper for a list of latest quotes")
public class ApiResponseListLatestQuoteResponse extends ApiResponse<List<colloney.stock.quote.QuoteResponse>> {
}
