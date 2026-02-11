package colloney.stock.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import colloney.banking.account.Account;
import colloney.banking.account.AccountRepository;
import colloney.banking.account.AccountType;
import colloney.stock.quote.QuoteResponse;
import colloney.stock.quote.latest.LatestQuoteRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PortfolioService {

    private final PositionRepository positionRepository;
    private final LatestQuoteRepository latestQuoteRepository;
    private final AccountRepository accountRepository;

    public ApiResponse<List<PositionResponse>> getPortfolio(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));
        if (account.getAccountType() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Positions are only available for investment accounts");
        }

        List<Position> positions = positionRepository.findByAccountId(accountId);

        List<PositionResponse> responses = new ArrayList<>();
        for (Position position : positions) {
            Optional<QuoteResponse> quoteResponseOpt = latestQuoteRepository
                    .findQuoteResponseBySymbol(position.getSymbol());

            if (quoteResponseOpt.isPresent()) {
                QuoteResponse quoteResponse = quoteResponseOpt.get();
                String name = quoteResponse.getName();
                double price = quoteResponse.getCurrentPrice();
                double priceChange = quoteResponse.getPriceChange();
                double percentChange = quoteResponse.getPercentChange();
                String logoUrl = quoteResponse.getLogoUrl();

                responses.add(PositionMapper.toResponse(position, name, price, priceChange, percentChange, logoUrl));
            } else {
                responses.add(PositionMapper.toResponse(position, "Unknown", 0.0, 0.0, 0.0, ""));
                log.warn("No quote found for symbol {}", position.getSymbol());
            }
        }

        return ApiResponse.ok("Portfolio retrieved successfully", responses);
    }
}
