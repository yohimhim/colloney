package colloney.stock.symbol;

import java.util.List;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiResponseSymbolList", description = "API wrapper for a list of stock symbols")
public class ApiResponseListSymbolResponse extends ApiResponse<List<SymbolResponse>> {
}
