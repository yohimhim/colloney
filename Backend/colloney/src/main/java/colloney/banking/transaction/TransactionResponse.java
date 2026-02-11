package colloney.banking.transaction;

import java.time.Instant;

import colloney.banking.account.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    @Schema(description = "Unique ID of the transaction", example = "3")
    private Long id;

    @Schema(description = "ID of the account involved in the transaction", example = "1")
    private Long accountId;

    @Schema(description = "Amount of the transaction", example = "100.00")
    private double amount;

    @Schema(description = "Timestamp when the transaction occurred")
    private Instant timestamp;

    @Schema(description = "Type of transaction", example = "DEPOSIT")
    private TransactionType transactionType;

    @Schema(description = "Target account ID for transfers; null for deposits/withdrawals", example = "2")
    private Long targetAccountId;

    @Schema(description = "Optional description for the transaction", example = "Transfer to Roth IRA")
    private String description;

    @Schema(description = "Type of account involved in the transaction", example = "CHECKING")
    private AccountType accountType;
}
