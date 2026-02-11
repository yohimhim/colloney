package colloney.stock.quote.latest;

import colloney.stock.quote.Quote;
import colloney.stock.quote.QuoteResponse;

public class LatestQuoteMapper {
    public static LatestQuote toEntity(Quote quote) {
        return LatestQuote.builder()
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

    public static QuoteResponse toResponse(LatestQuote latestQuote) {
        return QuoteResponse.builder()
      // @formatter:off
      .symbol(latestQuote.getSymbol())
      .currentPrice(latestQuote.getCurrentPrice())
      .priceChange(latestQuote.getPriceChange())
      .percentChange(latestQuote.getPercentChange())
      .highPrice(latestQuote.getHighPrice())
      .lowPrice(latestQuote.getLowPrice())
      .openPrice(latestQuote.getOpenPrice())
      .previousClosePrice(latestQuote.getPreviousClosePrice())
      .quoteTimestamp(latestQuote.getQuoteTimestamp())
      .build();
      // @formatter:on
    }
}
