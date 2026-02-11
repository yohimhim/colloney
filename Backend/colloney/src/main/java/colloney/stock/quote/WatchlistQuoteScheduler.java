package colloney.stock.quote;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import colloney.user.watchlist.Watchlist;
import colloney.user.watchlist.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class WatchlistQuoteScheduler {
    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter;
    private final WatchlistRepository watchlistRepository;
    private final QuoteRepository quoteRepository;
    private final LatestQuoteRepository latestQuoteRepository;
    private final ObjectMapper objectMapper;

    @Value("${finnhub.api.key2}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    public Optional<QuoteDto> getStockQuote(String symbol) {
        rateLimiter.acquire();
        String url = String.format("%s/quote?symbol=%s&token=%s", apiUrl, symbol, apiKey);
        try {
            QuoteDto quote = restTemplate.getForObject(url, QuoteDto.class);
            log.debug("Fetched watchlist quote for symbol {}: {}", symbol, quote);
            return Optional.ofNullable(quote);
        } catch (RestClientException e) {
            log.error("Error fetching watchlist quote for symbol {}: {}", symbol, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void updateWatchlistStockQuotes() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        if (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY)
            return;

        log.info("Starting scheduled watchlist quote update...");
        List<Watchlist> watchlists = watchlistRepository.findAll();
        // use hashset to avoid having duplicate symbols
        Set<String> uniqueSymbols = new HashSet<>();
        for (Watchlist watchlist : watchlists) {
            uniqueSymbols.add(watchlist.getSymbol().toUpperCase());
        }

        if (uniqueSymbols.isEmpty()) {
            log.info("No symbols found in watchlist to update");
            return;
        }

        log.info("Updating watchlist quotes for {} symbols", uniqueSymbols.size());

        List<Quote> toSave = new ArrayList<>();
        List<LatestQuote> latestToSave = new ArrayList<>();

        for (String symbol : uniqueSymbols) {
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
                log.warn("No watchlist quote found for symbol {}", symbol);
            }
        }

        if (!toSave.isEmpty()) {
            quoteRepository.saveAll(toSave);
            latestQuoteRepository.saveAll(latestToSave);
            log.info("Saved {} watchlist quotes", toSave.size());
        } else {
            log.info("No new watchlist quotes to save");
        }
    }
}
