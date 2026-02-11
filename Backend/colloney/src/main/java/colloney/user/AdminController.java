package colloney.user;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.stock.quote.QuoteService;
import colloney.user.watchlist.WatchlistService;
import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin", description = "Endpoints for administrative actions")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final WatchlistService watchlistService;
    private final QuoteService quoteService;

    // @formatter:off
    @Operation(
        summary = "Get all users",
        description = "Retrieve a list of all users in the system.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of all users",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseListUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return wrapResponse(userService.getAllUsers());
    }

    // @formatter:off
    @Operation(
        summary = "Create a user",
        description = "Add a new user. Username and email must be unique.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Information about the newly created user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest userRequest) {
        return wrapResponse(userService.createUser(userRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Delete a user by ID",
        description = "Remove a user from the system.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the deleted user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        return wrapResponse(userService.deleteUserById(id));
    }

    // @formatter:off
    @Operation(
        summary = "Update user by ID",
        description = "Update the information of an existing user.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Information about the updated user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {
        return wrapResponse(userService.updateUserById(id, userRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Get system statistics",
        description = "Retrieve a summary of system statistics.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "A summary of system statistics",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                        {
                          "statusCode": 200,
                          "status": "success",
                          "message": "Statistics fetched successfully",
                          "metadata": {
                            "paginated": false,
                            "page": null,
                            "size": null,
                            "totalElements": null,
                            "totalPages": null
                          },
                          "data": {
                            "mostWatchedSymbol": "AAPL",
                            "userCount": 39,
                            "adminCount": 2,
                            "regularUserCount": 37
                          },
                          "description": null,
                          "timestamp": "2025-11-21T22:04:52.929001947Z"
                        }
                        """
                    )
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long userCount = userService.getUserCount();
        long adminCount = userService.getUserCountByRole(Role.ADMIN);
        long regularUserCount = userService.getUserCountByRole(Role.USER);

        stats.put("userCount", userCount);
        stats.put("adminCount", adminCount);
        stats.put("regularUserCount", regularUserCount);

        String mostWatchedSymbol = watchlistService.getMostWatchedSymbol();
        stats.put("mostWatchedSymbol", mostWatchedSymbol);

        return wrapResponse(ApiResponse.ok("Statistics fetched successfully", stats));
    }
}
