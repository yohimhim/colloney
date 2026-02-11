package colloney.stock.quote.latest;

import java.util.List;

import org.springframework.stereotype.Service;

import colloney.stock.quote.QuoteResponse;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LatestQuoteService {
    private final LatestQuoteRepository latestQuoteRepository;

    public ApiResponse<List<QuoteResponse>> getAllLatestQuotes() {
        List<QuoteResponse> quoteResponses = latestQuoteRepository.findAllQuoteResponses();
        return ApiResponse.ok("Latest quotes retrieved successfully", quoteResponses);
    }

    public ApiResponse<QuoteResponse> getLatestQuoteBySymbol(String symbol) {
        QuoteResponse quoteResponse = latestQuoteRepository.findQuoteResponseBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException("No latest quote found for symbol " + symbol));
        return ApiResponse.ok("Latest quote retrieved successfully", quoteResponse);
    }

}
