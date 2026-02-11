package colloney.stock.symbol;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Stock Symbols", description = "Endpoints for retrieving stock symbols")
@RestController
@RequestMapping("/stock/symbols")
@RequiredArgsConstructor
public class SymbolController {
    private final SymbolService symbolService;

    // @formatter:off
    @Operation(
        summary = "Get active stock symbols",
        description = """
            Returns stock symbols that are currently considered *active* by the system.

            Active symbols are those for which the system is fetching live stock quotes.
            Only symbols listed on the configured exchanges and matching the configured types are included.
            """,
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of active tracked symbols",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListSymbolResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping()
    public ResponseEntity<ApiResponse<List<SymbolResponse>>> getActiveSymbols() {
        return wrapResponse(symbolService.getActiveSymbols());
    }

    // @formatter:off
    @Operation(
        summary = "Get all stock symbols",
        description = "Returns every stock symbol stored in the database, regardless of tracking status.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of all symbols",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListSymbolResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SymbolResponse>>> getAllSymbols() {
        return wrapResponse(symbolService.getAllSymbols());
    }

}
