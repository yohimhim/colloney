package colloney.stock.symbol;

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
public class SymbolScheduler {
    private final RestTemplate restTemplate;
    private final SymbolRepository symbolRepository;

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    @Scheduled(cron = "0 30 3 * * 6") // Every saturday at 3:30am
    public void updateStockSymbols() {
        log.info("Fetching all US stock symbols from Finnhub...");

        String url = String.format("%s/stock/symbol?exchange=US&token=%s", apiUrl, apiKey);

        try {
            SymbolDto[] symbolDtos = restTemplate.getForObject(url, SymbolDto[].class);

            if (symbolDtos == null || symbolDtos.length == 0) {
                log.warn("No symbols fetched from Finnhub");
                return;
            }

            Set<String> existingSymbols = new HashSet<>(symbolRepository.findAllSymbols());
            List<Symbol> toSave = new ArrayList<>();

            for (SymbolDto dto : symbolDtos) {
                Symbol symbol = SymbolMapper.toEntity(dto);

                if (!existingSymbols.contains(symbol.getSymbol())) {
                    toSave.add(symbol);
                }
            }

            if (!toSave.isEmpty()) {
                symbolRepository.saveAll(toSave);
                log.info("Saved {} new or updated symbols", toSave.size());
            } else {
                log.info("No new or updated symbols to save");
            }
        } catch (RestClientException e) {
            log.error("Error fetching stock symbols from Finnhub: {}", e.getMessage(), e);
        }

    }
}
