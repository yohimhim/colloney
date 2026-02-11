package colloney.banking.account;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a bank account")
public class AccountResponse {

    @Schema(description = "ID of the bank account", example = "1")
    private Long id;

    @Schema(description = "The type of the bank account", example = "CHECKING")
    private AccountType accountType;

    @Schema(description = "The balance of the bank account", example = "1000000")
    private double balance;

    @Schema(description = "Timestamp when the bank account was created", example = "2025-12-06T12:32:50.150206Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the bank account was updated", example = "2025-12-06T23:32:50.150206Z")
    private Instant updatedAt;

    @Schema(description = "Owner user ID of the bank account", example = "39")
    private Long userId;
}
