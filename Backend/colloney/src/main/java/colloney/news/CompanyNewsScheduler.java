package colloney.news;

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

import com.google.common.util.concurrent.RateLimiter;

import colloney.stock.symbol.SymbolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class CompanyNewsScheduler {
    private final RestTemplate restTemplate;
    private final NewsRepository newsRepository;
    private final SymbolRepository symbolRepository;
    private final RateLimiter rateLimiter;

    @Value("${finnhub.api.key2}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    private Optional<NewsDto[]> getNewsByCompany(String symbol, LocalDate from, LocalDate to) {
        rateLimiter.acquire();
        String url = String.format("%s/company-news?symbol=%s&from=%s&to=%s&token=%s", apiUrl, symbol, from, to,
                apiKey);

        try {
            NewsDto[] newsDtos = restTemplate.getForObject(url, NewsDto[].class);
            return Optional.ofNullable(newsDtos);
        } catch (RestClientException e) {
            log.error("Error fetching company news for symbol {}: {}", symbol, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Scheduled(cron = "0 0 1 * * 6") // every saturday at 1am
    public void fetchCompanyNews() {
        log.info("Starting company news fetch...");

        Set<Long> existingIds = new HashSet<>(newsRepository.findAllIds());

        List<String> symbols = symbolRepository.findAllSymbols();

        List<News> toSave = new ArrayList<>();

        LocalDate to = LocalDate.now();
        LocalDate from = to.minusYears(1);

        for (String symbol : symbols) {
            log.debug("Fetching company news for symbol: {}", symbol);
            Optional<NewsDto[]> newsDtosOpt = getNewsByCompany(symbol, from, to);

            if (newsDtosOpt.isEmpty() || newsDtosOpt.get().length == 0) {
                log.debug("No news for symbol {}", symbol);
                continue;
            }

            for (NewsDto dto : newsDtosOpt.get()) {
                News incoming = NewsMapper.toEntity(dto);

                if (!existingIds.contains(incoming.getId())) {
                    toSave.add(incoming);
                }
            }
        }
        if (!toSave.isEmpty()) {
            newsRepository.saveAll(toSave);
            log.info("Saved/updated {} company news items", toSave.size());
        } else {
            log.info("No new or updated company news to save");
        }

    }
}
