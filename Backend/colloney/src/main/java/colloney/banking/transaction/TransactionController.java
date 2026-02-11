package colloney.banking.transaction;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banking/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Create and view account transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit money into an account")
    public ResponseEntity<ApiResponse<TransactionResponse>> deposit(@RequestBody TransactionRequest request) {
        return wrapResponse(
                transactionService.deposit(request.getAccountId(), request.getAmount(), request.getDescription()));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money from an account")
    public ResponseEntity<ApiResponse<TransactionResponse>> withdraw(@RequestBody TransactionRequest request) {
        return wrapResponse(
                transactionService.withdraw(request.getAccountId(), request.getAmount(), request.getDescription()));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money between accounts")
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(@RequestBody TransactionRequest request) {
        if (request.getTargetAccountId() == null) {
            throw new IllegalArgumentException("targetAccountId is required for transfers");
        }
        return wrapResponse(transactionService.transfer(request.getAccountId(), request.getTargetAccountId(),
                request.getAmount(), request.getDescription()));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transaction history for an account")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionHistory(@PathVariable Long accountId) {
        return wrapResponse(transactionService.getTransactionHistory(accountId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get transaction history for a user across all accounts")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getUserTransactionHistory(@PathVariable Long userId) {
        return wrapResponse(transactionService.getUserTransactionHistory(userId));
    }
}
