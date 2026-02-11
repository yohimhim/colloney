package colloney.stock.quote.latest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import colloney.stock.quote.QuoteResponse;

@Repository
public interface LatestQuoteRepository extends JpaRepository<LatestQuote, String> {
    @Query("""
            SELECT new colloney.stock.quote.QuoteResponse(
              q.symbol, s.name, q.currentPrice, q.priceChange, q.percentChange,
              q.highPrice, q.lowPrice, q.openPrice, q.previousClosePrice, q.quoteTimestamp, c.logo
              )
            FROM LatestQuote q
            JOIN Symbol s ON s.symbol = q.symbol
            LEFT JOIN Company c ON c.symbol = q.symbol
            """)
    List<QuoteResponse> findAllQuoteResponses();

    @Query("""
            SELECT new colloney.stock.quote.QuoteResponse(
              q.symbol, s.name, q.currentPrice, q.priceChange, q.percentChange,
              q.highPrice, q.lowPrice, q.openPrice, q.previousClosePrice, q.quoteTimestamp, c.logo
              )
            FROM LatestQuote q
            JOIN Symbol s ON s.symbol = q.symbol
            LEFT JOIN Company c ON c.symbol = q.symbol
            WHERE LOWER(q.symbol) = LOWER(:symbol)
            """)
    Optional<QuoteResponse> findQuoteResponseBySymbol(@Param("symbol") String symbol);
}
