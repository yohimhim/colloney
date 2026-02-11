package colloney.banking.transaction;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colloney.banking.account.Account;
import colloney.banking.account.AccountRepository;
import colloney.banking.account.AccountType;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    @Transactional
    public ApiResponse<TransactionResponse> deposit(Long accountId, double amount, String description) {
        validateAmount(amount);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));
        account.setBalance(account.getBalance() + amount);

        Transaction transaction = TransactionMapper.toEntity(account, amount, TransactionType.DEPOSIT, null,
                description != null ? description : "Deposit");
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return ApiResponse.ok("Deposit successful", TransactionMapper.toResponse(transaction));
    }

    @Transactional
    public ApiResponse<TransactionResponse> withdraw(Long accountId, double amount, String description) {
        validateAmount(amount);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = TransactionMapper.toEntity(account, amount, TransactionType.WITHDRAWAL, null,
                description != null ? description : "Withdrawal");
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return ApiResponse.ok("Withdrawal successful", TransactionMapper.toResponse(transaction));
    }

    @Transactional
    public ApiResponse<TransactionResponse> transfer(Long fromAccountId, Long toAccountId, double amount,
            String description) {
        validateAmount(amount);
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        Account from = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Source account not found with id " + fromAccountId));
        Account to = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Target account not found with id " + toAccountId));

        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction outgoing = TransactionMapper.toEntity(from, amount, TransactionType.TRANSFER, toAccountId,
                description != null ? description : "Outgoing transfer to account " + toAccountId);
        transactionRepository.save(outgoing);

        Transaction incoming = TransactionMapper.toEntity(to, amount, TransactionType.TRANSFER, fromAccountId,
                description != null ? description : "Incoming transfer from account " + fromAccountId);
        transactionRepository.save(incoming);

        accountRepository.save(from);
        accountRepository.save(to);

        return ApiResponse.ok("Transfer successful", TransactionMapper.toResponse(outgoing));
    }

    public ApiResponse<List<TransactionResponse>> getTransactionHistory(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
        return buildListResponse("Transaction history retrieved successfully", transactions,
                TransactionMapper::toResponse);
    }

    public ApiResponse<List<TransactionResponse>> getUserTransactionHistory(Long userId) {
        List<Transaction> transactions = transactionRepository.findByAccount_UserIdOrderByTimestampDesc(userId);
        return buildListResponse("Transaction history retrieved successfully", transactions,
                TransactionMapper::toResponse);
    }

    @Transactional
    public void applyInterest(double rate) {
        List<Account> savingsAccounts = accountRepository.findByAccountType(AccountType.SAVINGS);

        for (Account account : savingsAccounts) {
            double interest = account.getBalance() * rate;
            account.setBalance(account.getBalance() + interest);

            Transaction transaction = TransactionMapper.toEntity(account, interest, TransactionType.DEPOSIT, null,
                    "Interest");

            transactionRepository.save(transaction);
            accountRepository.save(account);
        }
    }

    @Transactional
    public void logBuyTransaction(Long accountId, String symbol, int quantity, double price, double totalValue) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        Transaction transaction = TransactionMapper.toEntity(account, totalValue, TransactionType.BUY, null,
                "Bought " + quantity + " shares of " + symbol + " at " + price + " per share");

        transactionRepository.save(transaction);
    }

    @Transactional
    public void logSellTransaction(Long accountId, String symbol, int quantity, double price, double totalValue) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        Transaction transaction = TransactionMapper.toEntity(account, totalValue, TransactionType.SELL, null,
                "Sold " + quantity + " shares of " + symbol + " at " + price + " per share");

        transactionRepository.save(transaction);
    }

}
