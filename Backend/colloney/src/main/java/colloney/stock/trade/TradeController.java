package colloney.stock.trade;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stock/trade")
@RequiredArgsConstructor
@Tag(name = "Trades", description = "Endpoint for executing and viewing trades")
public class TradeController {

    private final TradeService tradeService;
    private final PortfolioService portfolioService;

    @PostMapping("/{accountId}/buy")
    @Operation(summary = "Buy shares")
    public ResponseEntity<ApiResponse<TradeResponse>> buy(@PathVariable Long accountId,
            @RequestBody TradeRequest request) {

        return wrapResponse(tradeService.buy(accountId, request));
    }

    @PostMapping("/{accountId}/sell")
    @Operation(summary = "Sell shares")
    public ResponseEntity<ApiResponse<TradeResponse>> sell(@PathVariable Long accountId,
            @RequestBody TradeRequest request) {

        return wrapResponse(tradeService.sell(accountId, request));
    }

    @GetMapping("/{accountId}/positions")
    @Operation(summary = "Get all positions for an investment account")
    public ResponseEntity<ApiResponse<List<PositionResponse>>> positions(@PathVariable Long accountId) {
        return wrapResponse(portfolioService.getPortfolio(accountId));
    }
}
