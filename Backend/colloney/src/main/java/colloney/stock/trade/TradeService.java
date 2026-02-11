package colloney.stock.trade;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colloney.banking.account.Account;
import colloney.banking.account.AccountRepository;
import colloney.banking.account.AccountType;
import colloney.banking.transaction.TransactionService;
import colloney.stock.quote.latest.LatestQuoteService;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final AccountRepository accountRepository;
    private final LatestQuoteService latestQuotes;
    private final PositionRepository positionRepository;
    private final TradeRepository tradeRepository;
    private final TransactionService transactionService;

    @Transactional
    public ApiResponse<TradeResponse> buy(Long accountId, TradeRequest request) {
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        if (account.getAccountType() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Trading allowed only in investment accounts");
        }

        double price = latestQuotes.getLatestQuoteBySymbol(request.getSymbol()).getData().getCurrentPrice();

        double totalValue = price * request.getQuantity();

        if (account.getBalance() < totalValue) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - totalValue);
        accountRepository.save(account);

        Trade trade = TradeMapper.toBuyEntity(account, price, request);
        tradeRepository.save(trade);

        Position position = positionRepository.findByAccountIdAndSymbol(accountId, request.getSymbol())
                .orElse(Position.builder()
                // @formatter:off
                        .account(account)
                        .symbol(request.getSymbol())
                        .averageCost(0)
                        .totalCost(0)
                        .totalShares(0)
                        .build());
                        // @formatter:on

        int newShares = position.getTotalShares() + request.getQuantity();
        double newTotalCost = position.getTotalCost() + totalValue;

        position.setTotalShares(newShares);
        position.setTotalCost(newTotalCost);
        position.setAverageCost(newTotalCost / newShares);
        position.setLastUpdated(Instant.now());
        positionRepository.save(position);

        transactionService.logBuyTransaction(accountId, request.getSymbol(), request.getQuantity(), price, totalValue);

        return ApiResponse.ok("Buy executed successfully", TradeMapper.toResponse(trade));
    }

    @Transactional
    public ApiResponse<TradeResponse> sell(Long accountId, TradeRequest request) {
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        if (account.getAccountType() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Trading allowed only in investment accounts");
        }

        Position position = positionRepository.findByAccountIdAndSymbol(accountId, request.getSymbol())
                .orElseThrow(() -> new IllegalArgumentException("No position available to sell"));

        if (position.getTotalShares() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough shares to sell");
        }

        double price = latestQuotes.getLatestQuoteBySymbol(request.getSymbol()).getData().getCurrentPrice();

        double totalValue = price * request.getQuantity();

        double realizedGain = (price - position.getAverageCost()) * request.getQuantity();

        account.setBalance(account.getBalance() + totalValue);
        accountRepository.save(account);

        Trade trade = TradeMapper.toSellEntity(account, price, request, realizedGain);
        tradeRepository.save(trade);

        int remainingShares = position.getTotalShares() - request.getQuantity();

        if (remainingShares == 0) {
            positionRepository.delete(position);
        } else {
            double newTotalCost = position.getAverageCost() * remainingShares;
            position.setTotalShares(remainingShares);
            position.setTotalCost(newTotalCost);
            position.setAverageCost(newTotalCost / remainingShares);
            position.setLastUpdated(Instant.now());
            positionRepository.save(position);
        }

        transactionService.logSellTransaction(accountId, request.getSymbol(), request.getQuantity(), price, totalValue);

        return ApiResponse.ok("Sell executed successfully", TradeMapper.toResponse(trade));
    }
}
