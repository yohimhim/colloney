package colloney.stock.quote;

import java.time.Instant;

public class QuoteMapper {
    public static Quote toEntity(QuoteDto quote, String symbol) {
        return Quote.builder()
      // @formatter:off
      .symbol(symbol)
      .currentPrice(quote.getC())
      .priceChange(quote.getD())
      .percentChange(quote.getDp())
      .highPrice(quote.getH())
      .lowPrice(quote.getL())
      .openPrice(quote.getO())
      .previousClosePrice(quote.getPc())
      .quoteTimestamp(Instant.ofEpochSecond(quote.getT()))
      .build();
      // @formatter:on
    }

    public static QuoteResponse toResponse(Quote quote) {
        return QuoteResponse.builder()
      // @formatter:off
      .symbol(quote.getSymbol())
      .currentPrice(quote.getCurrentPrice())
      .priceChange(quote.getPriceChange())
      .percentChange(quote.getPercentChange())
      .highPrice(quote.getHighPrice())
      .lowPrice(quote.getLowPrice())
      .openPrice(quote.getOpenPrice())
      .previousClosePrice(quote.getPreviousClosePrice())
      .quoteTimestamp(quote.getQuoteTimestamp())
      .build();
      // @formatter:on
    }
}
