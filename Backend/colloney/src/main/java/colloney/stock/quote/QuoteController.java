package colloney.stock.quote;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Stock Quotes", description = "Endpoints for retrieving stock quote information")
@RestController
@RequestMapping("/stock/quotes")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping
    @Deprecated
    // @formatter:off
    @Operation(
        summary = "Get all stock quotes (temporarily disabled)",
        description = """
            This endpoint is currently disabled because fetching all quotes at once
            can overload the system and potentially crash the application.
            Pagination needs to be implemented before this endpoint can be safely used.
            """,
        deprecated = true,
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "503",
                description = "Endpoint temporarily disabled due to potential performance issues",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = colloney.util.ApiResponse.class),
                    examples = @ExampleObject(
                        value = """
                        {
                          "statusCode": 503,
                          "status": "error",
                          "message": "This endpoint is temporarily disabled",
                          "metadata": {
                            "paginated": false,
                            "page": null,
                            "size": null,
                            "totalElements": null,
                            "totalPages": null
                          },
                          "data": null,
                          "description": "uri=/stock/quotes",
                          "timestamp": "2025-11-21T20:19:42.854103136Z"
                        }
                        """
                    )
                )
            )
        }
    )
    // @formatter:on
    public ResponseEntity<ApiResponse<List<QuoteResponse>>> getAllQuotes() {
        // return wrapResponse(quoteService.getAllQuotes());
        throw new UnsupportedOperationException("This endpoint is temporarily disabled");
    }

    // @formatter:off
    @Operation(
        summary = "Get stock quotes by symbol",
        description = """
            Returns stock quotes for a specific symbol within an optional date range.

            By default, if startDate and endDate are not provided, the API returns quotes for the previous day.
            """,
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of quotes for the symbol",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListQuoteResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<ApiResponse<List<QuoteResponse>>> getQuotesBySymbol(@PathVariable String symbol,
            @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return wrapResponse(quoteService.getQuotesBySymbol(symbol, startDate, endDate));
    }
}
