package colloney.stock.quote;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, QuoteId> {
    @Query("""
                SELECT new colloney.stock.quote.QuoteResponse(
                    q.symbol, s.name, q.currentPrice, q.priceChange, q.percentChange,
                    q.highPrice, q.lowPrice, q.openPrice, q.previousClosePrice, q.quoteTimestamp, c.logo
                )
                FROM Quote q
                JOIN Symbol s ON s.symbol = q.symbol
                LEFT JOIN Company c ON c.symbol = q.symbol
            """)
    List<QuoteResponse> findAllQuoteResponses();

    @Query("""
                SELECT new colloney.stock.quote.QuoteResponse(
                    q.symbol, s.name, q.currentPrice, q.priceChange, q.percentChange,
                    q.highPrice, q.lowPrice, q.openPrice, q.previousClosePrice, q.quoteTimestamp, c.logo
                )
                FROM Quote q
                JOIN Symbol s ON s.symbol = q.symbol
                LEFT JOIN Company c ON c.symbol = q.symbol
                WHERE LOWER(q.symbol) = LOWER(:symbol)
                  AND q.quoteTimestamp BETWEEN :start AND :end
            """)

    List<QuoteResponse> findQuoteResponsesBySymbolAndDateRange(@Param("symbol") String symbol,
            @Param("start") Instant start, @Param("end") Instant end);
}
