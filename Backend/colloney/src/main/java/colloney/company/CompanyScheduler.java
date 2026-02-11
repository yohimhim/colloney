package colloney.company;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.RateLimiter;

import colloney.stock.symbol.Symbol;
import colloney.stock.symbol.SymbolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("prod")
public class CompanyScheduler {
    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter;
    private final CompanyRepository companyRepository;
    private final SymbolRepository symbolRepository;

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.api.url}")
    private String apiUrl;

    @Scheduled(cron = "0 0 4 * * 6") // every saturday at 4am
    public void updateCompanyProfiles() {
        log.info("Starting company profile update...");

        List<Company> toSave = new ArrayList<>();
        List<Symbol> symbols = symbolRepository.findByTypeIn(List.of("Common Stock"));

        for (Symbol stockSymbol : symbols) {
            String symbol = stockSymbol.getSymbol();
            log.debug("Fetching company profile for {}", symbol);
            rateLimiter.acquire();

            String url = String.format("%s/stock/profile2?symbol=%s&token=%s", apiUrl, symbol, apiKey);
            try {
                CompanyDto companyDto = restTemplate.getForObject(url, CompanyDto.class);
                if (companyDto != null) {
                    Company company = CompanyMapper.toEntity(companyDto, symbol);
                    toSave.add(company);
                }
            } catch (RestClientException e) {
                log.error("Error fetching company data for symbol {}: {}", symbol, e.getMessage());
            }
        }

        if (!toSave.isEmpty()) {
            companyRepository.saveAll(toSave);
            log.info("Saved {} company profiles", toSave.size());
        } else {
            log.info("No new company profiles to save");
        }
    }

}
