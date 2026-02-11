package colloney.user;

import static colloney.util.ResponseBuilder.buildListResponse;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colloney.banking.account.Account;
import colloney.banking.account.AccountRepository;
import colloney.banking.account.AccountType;
import colloney.exception.InvalidCredentialsException;
import colloney.posts.PostRepository;
import colloney.user.financialdata.FinancialDataRepository;
import colloney.user.watchlist.WatchlistRepository;
import colloney.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    private final WatchlistRepository watchlistRepository;
    private final FinancialDataRepository financialDataRepository;
    private final PostRepository postRepository;

    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return buildListResponse("Users retrieved successfully", users, UserMapper::toResponse);
    }

    public ApiResponse<UserResponse> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        return ApiResponse.ok("User retrieved successfully", UserMapper.toResponse(user));
    }

    @Transactional
    public ApiResponse<UserResponse> createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DataIntegrityViolationException("Username '" + userRequest.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DataIntegrityViolationException("Email '" + userRequest.getEmail() + "' is already taken");
        }
        User user = UserMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepository.save(user);

        Account defaultAccount = Account.builder()
        // @formatter:off
            .user(savedUser)
            .accountType(AccountType.CHECKING)
            .balance(0.0)
            .build();
            // @formatter:on
        accountRepository.save(defaultAccount);

        return ApiResponse.created("User created successfully", UserMapper.toResponse(savedUser));
    }

    @Transactional
    public ApiResponse<UserResponse> updateUserById(Long id, UserRequest userRequest) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        User updated = existing.toBuilder()
      // @formatter:off
      .username(userRequest.getUsername())
      .email(userRequest.getEmail())
      .password(passwordEncoder.encode(userRequest.getPassword()))
      .build();
      // @formatter:on

        User savedUser = userRepository.save(updated);
        return ApiResponse.ok("User updated successfully", UserMapper.toResponse(savedUser));
    }

    @Transactional
    public ApiResponse<UserResponse> deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user with id " + id));
        userRepository.deleteById(id);
        return ApiResponse.ok("User deleted successfully", UserMapper.toResponse(user));
    }

    private void ensureCheckingAccount(User user) {
        List<Account> accounts = accountRepository.findByUserId(user.getId());
        boolean hasChecking = false;

        for (Account account : accounts) {
            if (account.getAccountType() == AccountType.CHECKING) {
                hasChecking = true;
                break;
            }
        }

        if (!hasChecking) {
            Account defaultAccount = Account.builder()
            // @formatter:off
                .user(user)
                .accountType(AccountType.CHECKING)
                .balance(0.0)
                .build();
                // @formatter:on
            accountRepository.save(defaultAccount);
        }
    }

    public ApiResponse<UserResponse> loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        ensureCheckingAccount(user);

        return ApiResponse.ok("Login successful", UserMapper.toResponse(user));
    }

    @Transactional
    public ApiResponse<UserResponse> patchUserById(Long id, UserPatchRequest userPatchRequest) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));

        if (userPatchRequest.getUsername() != null && !userPatchRequest.getUsername().isBlank()) {
            existing.setUsername(userPatchRequest.getUsername());
        }
        if (userPatchRequest.getEmail() != null && !userPatchRequest.getEmail().isBlank()) {
            existing.setEmail(userPatchRequest.getEmail());
        }
        if (userPatchRequest.getPassword() != null && !userPatchRequest.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(userPatchRequest.getPassword()));
        }
        if (userPatchRequest.getRole() != null) {
            existing.setRole(userPatchRequest.getRole());
        }

        return ApiResponse.ok("User updated succesfully", UserMapper.toResponse(existing));
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public long getUserCountByRole(Role role) {
        return userRepository.countByRole(role);
    }
}
