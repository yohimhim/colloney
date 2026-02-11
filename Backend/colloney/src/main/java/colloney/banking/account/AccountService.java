package colloney.banking.account;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import colloney.user.User;
import colloney.user.UserRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public ApiResponse<AccountResponse> createAccount(Long userId, AccountRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Account account = AccountMapper.toEntity(request, user);
        Account saved = accountRepository.save(account);

        return ApiResponse.ok("Account created successfully", AccountMapper.toResponse(saved));
    }

    public ApiResponse<List<AccountResponse>> getUserAccounts(Long userId, AccountType accountType) {
        List<Account> accounts = (accountType != null)
                ? accountRepository.findByUserIdAndAccountType(userId, accountType)
                : accountRepository.findByUserId(userId);

        return buildListResponse("Accounts retrieved successfully", accounts, AccountMapper::toResponse);
    }
}
