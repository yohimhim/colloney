package colloney.banking.account;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/banking/accounts")
@Tag(name = "Bank Accounts", description = "Manage user bank accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/{userId}")
    @Operation(summary = "Create a new bank account for a user")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@PathVariable Long userId,
            @RequestBody AccountRequest request) {
        return wrapResponse(accountService.createAccount(userId, request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get all bank accounts for a user, optionally filtered by account type")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getUserAccounts(@PathVariable Long userId,
            @RequestParam(required = false) AccountType accountType) {
        return wrapResponse(accountService.getUserAccounts(userId, accountType));
    }
}
