package colloney.user;

import static colloney.util.ControllerUtils.wrapResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import colloney.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // @formatter:off
    @Operation(
        summary = "Get all users",
        description = "Retrieve a list of all users.",
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
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return wrapResponse(userService.getAllUsers());
    }

    // @formatter:off
    @Operation(
        summary = "Get user by ID",
        description = "Get details for a specific user by their ID.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Details of the user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return wrapResponse(userService.getUserById(id));
    }

    // @formatter:off
    @Operation(
        summary = "Create a new user",
        description = "Creates a new user. Username and email must be unique.",
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
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return wrapResponse(userService.createUser(userRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Update user by ID",
        description = "Updates all fields for an existing user.",
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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {
        return wrapResponse(userService.updateUserById(id, userRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Delete user by ID",
        description = "Deletes a user and all associated data.",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable Long id) {
        return wrapResponse(userService.deleteUserById(id));
    }

    // @formatter:off
    @Operation(
        summary = "Login user",
        description = "Log in a user with username and password",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Information about the logged-in user",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        return wrapResponse(userService.loginUser(loginRequest));
    }

    // @formatter:off
    @Operation(
        summary = "Patch user by ID",
        description = "Update one or more fields of a user. Only the provided fields are updated.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "User details after the updates",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseUserResponse.class)
                )
            )
        }
    )
    // @formatter:on
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> patchUserById(@PathVariable Long id,
            @Valid @RequestBody UserPatchRequest userPatchRequest) {
        return wrapResponse(userService.patchUserById(id, userPatchRequest));
    }
}
