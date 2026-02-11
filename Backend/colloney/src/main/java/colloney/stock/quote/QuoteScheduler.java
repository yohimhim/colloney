package colloney.stock.quote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;

import colloney.stock.quote.latest.LatestQuote;
import colloney.stock.quote.latest.LatestQuoteMapper;
import colloney.stock.quote.latest.LatestQuoteRepository;
import colloney.stock.symbol.Symbol;
import colloney.stock.symbol.SymbolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class QuoteScheduler {
    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter;
    private final SymbolRepository symbolRepository;
    private final QuoteRepository quoteRepository;
    private final LatestQuoteRepository latestQuoteRepository;
    private final ObjectMapper objectMapper;

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    @Value("#{'${finnhub.stocks.exchanges}'.split(',')}")
    private List<String> exchanges;

    @Value("#{'${finnhub.stocks.types}'.split(',')}")
    private List<String> types;

    public Optional<QuoteDto> getStockQuote(String symbol) {
        rateLimiter.acquire();
        String url = String.format("%s/quote?symbol=%s&token=%s", apiUrl, symbol, apiKey);
        try {
            QuoteDto quote = restTemplate.getForObject(url, QuoteDto.class);
            log.debug("Fetched quote for symbol {}: {}", symbol, quote);
            return Optional.ofNullable(quote);
        } catch (RestClientException e) {
            log.error("Error fetching stock quote for symbol {}: {}", symbol, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Scheduled(cron = "0 30 9 * * MON-FRI", zone = "America/New_York")
    public void updateStockQuotesAtOpen() {
        runQuoteUpdate("Market Open");
    }

    @Scheduled(cron = "0 0 16 * * MON-FRI", zone = "America/New_York")
    public void updateStockQuotesAtClose() {
        runQuoteUpdate("Market Close");
    }

    private void runQuoteUpdate(String runType) {
        // DayOfWeek today = LocalDate.now().getDayOfWeek();
        // if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY) {
        // log.info("Skipping quote update on weekend ({})", runType);
        // return;
        // }

        log.info("Starting scheduled quote update ({})...", runType);
        List<Symbol> symbols = symbolRepository.findByExchangeInAndTypeIn(exchanges, types);
        log.info("Updating stock quotes for {} symbols", symbols.size());

        List<Quote> toSave = new ArrayList<>();
        List<LatestQuote> latestToSave = new ArrayList<>();
        for (Symbol stockSymbol : symbols) {
            String symbol = stockSymbol.getSymbol();
            Optional<QuoteDto> quoteDtoOpt = getStockQuote(symbol);

            if (quoteDtoOpt.isPresent()) {
                Quote stockQuote = QuoteMapper.toEntity(quoteDtoOpt.get(), symbol);
                LatestQuote latestQuote = LatestQuoteMapper.toEntity(stockQuote);

                toSave.add(stockQuote);
                latestToSave.add(latestQuote);

                try {
                    String json = objectMapper.writeValueAsString(latestQuote);
                    QuoteWebSocket.broadcast(symbol, json);
                } catch (Exception e) {
                    log.error("Error broadcasting quote for symbol {}: {}", symbol, e.getMessage(), e);
                }

            } else {
                log.warn("No quote found for symbol {}", symbol);
            }
        }

        if (!toSave.isEmpty()) {
            quoteRepository.saveAll(toSave);
            latestQuoteRepository.saveAll(latestToSave);
            log.info("Saved {} stock quotes ({})", toSave.size(), runType);
        } else {
            log.info("No new stock quotes to save ({})", runType);
        }
    }
}
