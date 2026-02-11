package colloney.banking.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TransactionRequest {
    @Schema(description = "ID of the account performing the transaction", example = "1")
    private Long accountId;

    @Schema(description = "ID of the target account for transfer only; should be null for deposits or withdrawals", example = "2")
    private Long targetAccountId;

    @Schema(description = "Amount of the transaction", example = "100.00")
    private double amount;

    @Schema(description = "Optional description for the transaction", example = "Rent payment")
    private String description;
}
