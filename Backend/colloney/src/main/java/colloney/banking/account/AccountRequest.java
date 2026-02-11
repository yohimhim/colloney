package colloney.banking.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccountRequest {

    @Schema(description = "Type of account to create", example = "INVESTMENT")
    private AccountType accountType;

    @Schema(description = "Optional initial deposit amount", example = "100.00")
    private Double initialDeposit;
}
