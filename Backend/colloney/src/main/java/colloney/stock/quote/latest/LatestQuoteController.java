package colloney.stock.quote.latest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.stock.quote.QuoteResponse;
import colloney.util.ApiResponse;
import colloney.util.ControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Latest Stock Quotes", description = "Endpoints for retrieving the most recent stock quotes")
@RestController
@RequestMapping("/stock/quotes/latest")
@RequiredArgsConstructor
public class LatestQuoteController {
    private final LatestQuoteService latestQuoteService;

    // @formatter:off
    @Operation(
        summary = "Get all latest quotes",
        description = "Returns the latest quotes for all tracked symbols.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of latest quotes",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListLatestQuoteResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuoteResponse>>> getAllLatestQuotes() {
        return ControllerUtils.wrapResponse(latestQuoteService.getAllLatestQuotes());
    }

    // @formatter:off
    @Operation(
        summary = "Get the latest quote for a symbol",
        description = "Returns the most recent quote for a specific symbol.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Latest quote for the symbol",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseLatestQuoteResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<ApiResponse<QuoteResponse>> getLatestQuoteBySymbol(@PathVariable String symbol) {
        return ControllerUtils.wrapResponse(latestQuoteService.getLatestQuoteBySymbol(symbol));
    }
}
