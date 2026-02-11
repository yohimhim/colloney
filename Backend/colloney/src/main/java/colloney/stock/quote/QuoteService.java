package colloney.stock.quote;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;

import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;

    public ApiResponse<List<QuoteResponse>> getAllQuotes() {
        List<QuoteResponse> quoteResponses = quoteRepository.findAllQuoteResponses();
        return ApiResponse.ok("Quotes retrieved successfully", quoteResponses);
    }

    public ApiResponse<List<QuoteResponse>> getQuotesBySymbol(String symbol, String startDateStr, String endDateStr) {
        // Default end date = today
        LocalDate endDate = (endDateStr != null) ? LocalDate.parse(endDateStr) : LocalDate.now();

        // Default start date = 1 day before end date
        LocalDate startDate = (startDateStr != null) ? LocalDate.parse(startDateStr) : endDate.minusDays(1);

        // If start date is on a weekend, go back to last friday
        if (startDate.getDayOfWeek().getValue() >= 6) {
            startDate = startDate.minusDays(startDate.getDayOfWeek().getValue() - 5);
        }

        // Convert LocalDate to Instant for querying the database
        Instant startInstant = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        List<QuoteResponse> quoteResponses = quoteRepository.findQuoteResponsesBySymbolAndDateRange(symbol,
                startInstant, endInstant);

        if (quoteResponses.isEmpty()) {
            throw new EntityNotFoundException("No quotes found for symbol " + symbol + " in given date range");
        }
        return ApiResponse.ok("Quotes retrieved successfully", quoteResponses);

    }

}
