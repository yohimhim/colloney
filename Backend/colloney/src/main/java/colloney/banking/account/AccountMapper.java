package colloney.banking.account;

import colloney.user.User;

public class AccountMapper {
    public static Account toEntity(AccountRequest request, User user) {

        double initialDeposit = (request.getInitialDeposit() != null) ? request.getInitialDeposit() : 0.0;
        return Account.builder()
    // @formatter:off
      .user(user)
      .accountType(request.getAccountType())
      .balance(initialDeposit)
      .build();
    // @formatter:on
    }

    public static AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
    // @formatter:off
      .id(account.getId())
      .accountType(account.getAccountType())
      .balance(account.getBalance())
      .createdAt(account.getCreatedAt())
      .updatedAt(account.getUpdatedAt())
      .userId(account.getUser().getId())
      .build();
    // @formatter:on
    }
}
