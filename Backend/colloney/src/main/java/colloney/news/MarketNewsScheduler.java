package colloney.news;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class MarketNewsScheduler {
    private final RestTemplate restTemplate;
    private final NewsRepository newsRepository;

    @Value("${finnhub.api.key2}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    @Value("#{'${finnhub.news.categories}'.split(',')}")
    private List<String> categories;

    @Scheduled(cron = "0 0 22 * * 7") // runs sunday at 10pm
    public void fetchMarketNews() {
        log.info("Starting market news fetch...");

        List<News> toSave = new ArrayList<>();

        Set<Long> existingNewsIds = new HashSet<>(newsRepository.findAllIds());

        for (String category : categories) {
            log.info("Fetching {} news...", category);

            String url = String.format("%s/news?category=%s&token=%s", apiUrl, category, apiKey);

            try {
                NewsDto[] newsDtos = restTemplate.getForObject(url, NewsDto[].class);

                if (newsDtos == null || newsDtos.length == 0) {
                    log.info("No {} news fetched from Finnhub", category);
                    continue;
                }

                for (NewsDto dto : newsDtos) {
                    News incoming = NewsMapper.toEntity(dto);

                    if (!existingNewsIds.contains(incoming.getId())) {
                        toSave.add(incoming);
                    }
                }
            } catch (RestClientException e) {
                log.error("Error fetching {} news from Finnhub: {}", category, e.getMessage(), e);
            }
        }

        if (!toSave.isEmpty()) {
            newsRepository.saveAll(toSave);
            log.info("Saved/updated {} market news items", toSave.size());
        } else {
            log.info("No new or updated market news to save");
        }
    }
}
