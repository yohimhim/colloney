package colloney.banking.transaction;

import java.time.Instant;

import colloney.banking.account.Account;

public class TransactionMapper {
    public static Transaction toEntity(Account account, double amount, TransactionType transactionType,
            Long targetAccountId, String description) {
        return Transaction.builder()
    // @formatter:off
        .account(account)
        .amount(amount)
        .timestamp(Instant.now())
        .transactionType(transactionType)
        .targetAccountId(targetAccountId)
        .description(description)
        .build();
        // @formatter:on
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
    // @formatter:off
        .id(transaction.getId())
        .accountId(transaction.getAccount().getId())
        .accountType(transaction.getAccount().getAccountType())
        .amount(transaction.getAmount())
        .timestamp(transaction.getTimestamp())
        .transactionType(transaction.getTransactionType())
        .targetAccountId(transaction.getTargetAccountId())
        .description(transaction.getDescription())
        .build();
        // @formatter:on
    }
}
