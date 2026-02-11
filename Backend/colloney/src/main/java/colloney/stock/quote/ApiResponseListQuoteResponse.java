package colloney.stock.quote;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseQuoteList", description = "API wrapper for a list of stock quotes")
public class ApiResponseListQuoteResponse extends ApiResponse<List<QuoteResponse>> {
}
